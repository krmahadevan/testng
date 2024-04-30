package org.testng;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.testng.collections.ListMultiMap;
import org.testng.collections.Maps;
import org.testng.internal.MethodHelper;
import org.testng.internal.RuntimeBehavior;

/** Helper class to keep track of dependencies. */
public class DependencyMap {
  private final ListMultiMap<String, ITestNGMethod> m_dependencies = Maps.newListMultiMap();
  private final ListMultiMap<String, ITestNGMethod> m_groups = Maps.newListMultiMap();

  public DependencyMap(ITestNGMethod[] methods) {
    for (ITestNGMethod m : methods) {
      m_dependencies.put(m.getQualifiedName(), m);
      for (String g : m.getGroups()) {
        m_groups.put(g, m);
      }
    }
  }

  public List<ITestNGMethod> getMethodsThatBelongTo(String group, ITestNGMethod fromMethod) {
    Set<String> uniqueKeys = m_groups.keySet();
    Pattern pattern = Pattern.compile(group);

    List<ITestNGMethod> result =
        m_groups.keySet().stream()
            .parallel()
            .filter(k -> pattern.matcher(k).matches())
            .flatMap(k -> m_groups.get(k).stream())
            .collect(Collectors.toList());

    for (String k : uniqueKeys) {
      if (Pattern.matches(group, k)) {
        result.addAll(m_groups.get(k));
      }
    }

    if (result.isEmpty() && !fromMethod.ignoreMissingDependencies()) {
      throw new TestNGException(
          "DependencyMap::Method \""
              + fromMethod
              + "\" depends on nonexistent group \""
              + group
              + "\"");
    } else {
      return result;
    }
  }

  public ITestNGMethod getMethodDependingOn(String methodName, ITestNGMethod fromMethod) {
    List<ITestNGMethod> l = m_dependencies.get(methodName);
    if (l.isEmpty()) {
      ITestNGMethod[] array =
          m_dependencies.values().stream()
              .flatMap(Collection::stream)
              .toArray(ITestNGMethod[]::new);
      l = Arrays.asList(MethodHelper.findDependedUponMethods(fromMethod, array));
    }
    if (l.isEmpty()) {
      // Try to fetch dependencies by using the test class in the method name.
      // This is usually needed in scenarios wherein a child class overrides a base class method.
      // So the dependency name needs to be adjusted to use the test class name instead of using the
      // declared class.
      l = m_dependencies.get(constructMethodNameUsingTestClass(methodName, fromMethod));
    }
    if (l.isEmpty() && fromMethod.ignoreMissingDependencies()) {
      return fromMethod;
    }
    Optional<ITestNGMethod> found =
        l.stream()
            .parallel()
            .filter(
                m ->
                    isSameInstance(fromMethod, m)
                        || belongToDifferentClassHierarchy(fromMethod, m)
                        || hasInstance(fromMethod, m))
            .findFirst();
    if (found.isPresent()) {
      return found.get();
    }

    throw new TestNGException(
        "Method \""
            + fromMethod.getQualifiedName()
            + "()\" depends on nonexistent method \""
            + methodName
            + "\"");
  }

  private static boolean belongToDifferentClassHierarchy(
      ITestNGMethod baseClassMethod, ITestNGMethod derivedClassMethod) {
    Class<?> clazz = baseClassMethod.getRealClass();
    return !clazz.isAssignableFrom(derivedClassMethod.getRealClass());
  }

  private static boolean hasInstance(
      ITestNGMethod baseClassMethod, ITestNGMethod derivedClassMethod) {
    IInstanceInfo<?> baseInstance = (IInstanceInfo<?>) baseClassMethod.getInstance();
    IInstanceInfo<?> derivedInstance = (IInstanceInfo<?>) derivedClassMethod.getInstance();
    boolean result = derivedInstance != null || baseInstance != null;
    if (RuntimeBehavior.enforceThreadAffinity()
        && result
        && baseClassMethod instanceof ITestClassInstance
        && derivedClassMethod instanceof ITestClassInstance) {
      return hasSameParameters(
          (ITestClassInstance<?>) baseClassMethod, (ITestClassInstance<?>) derivedClassMethod);
    }
    return result;
  }

  private static boolean isSameInstance(
      ITestNGMethod baseClassMethod, ITestNGMethod derivedClassMethod) {
    IInstanceInfo<?> baseInstance = (IInstanceInfo<?>) baseClassMethod.getInstance();
    IInstanceInfo<?> derivedInstance = (IInstanceInfo<?>) derivedClassMethod.getInstance();
    boolean nonNullInstances = derivedInstance != null && baseInstance != null;
    if (!nonNullInstances) {
      return false;
    }
    if (RuntimeBehavior.enforceThreadAffinity()
        && baseClassMethod instanceof ITestClassInstance
        && derivedClassMethod instanceof ITestClassInstance) {
      return baseInstance.getClass().isAssignableFrom(derivedInstance.getClass())
          && hasSameParameters(
              (ITestClassInstance<?>) baseClassMethod, (ITestClassInstance<?>) derivedClassMethod);
    }
    return baseInstance.getClass().isAssignableFrom(derivedInstance.getClass());
  }

  private static boolean hasSameParameters(
      ITestClassInstance<?> baseClassMethod, ITestClassInstance<?> derivedClassMethod) {
    Object[] firstParams = baseClassMethod.getFactoryMethod().getParameters();
    Object[] secondParams = derivedClassMethod.getFactoryMethod().getParameters();
    if (firstParams.length == 0 || secondParams.length == 0) {
      return false;
    }
    return firstParams[0].equals(secondParams[0]);
  }

  private static String constructMethodNameUsingTestClass(
      String currentMethodName, ITestNGMethod m) {
    int lastIndex = currentMethodName.lastIndexOf('.');
    if (lastIndex != -1) {
      return m.getTestClass().getRealClass().getName() + currentMethodName.substring(lastIndex);
    }
    return currentMethodName;
  }
}
