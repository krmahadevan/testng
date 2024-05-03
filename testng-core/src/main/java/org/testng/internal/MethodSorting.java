package org.testng.internal;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;
import java.util.UUID;
import org.testng.ITestClassInstance;
import org.testng.ITestNGMethod;

public enum MethodSorting implements Comparator<ITestNGMethod> {
  METHOD_NAMES("methods") {
    @Override
    public int compare(ITestNGMethod o1, ITestNGMethod o2) {
      String n1 = o1.getMethodName();
      String n2 = o2.getMethodName();
      return n1.compareTo(n2);
    }

    @Override
    public String toString() {
      return "Method_Names";
    }
  },
  INSTANCES("instances") {
    @Override
    public int compare(ITestNGMethod o1, ITestNGMethod o2) {
      Comparator<ITestNGMethod> comparator =
          Comparator.comparingInt(ITestNGMethod::getPriority)
              .thenComparing(method -> method.getRealClass().getName())
              .thenComparing(ITestNGMethod::getMethodName)
              .thenComparing(Object::toString)
              .thenComparing(
                  method -> {
                    if (!(method instanceof ITestClassInstance)) {
                      return "";
                    }
                    return Arrays.toString(
                        ((ITestClassInstance<?>) method).getFactoryMethod().getParameters());
                  })
              .thenComparing(this::objectEquality);
      return comparator.compare(o1, o2);
    }

    private int objectEquality(ITestNGMethod a, ITestNGMethod b) {
      UUID one = a.getInstanceInfo().getUid();
      UUID two = b.getInstanceInfo().getUid();
      return one.compareTo(two);
    }

    @Override
    public String toString() {
      return "Instance_Names";
    }
  },
  NONE("none") {
    @Override
    public int compare(ITestNGMethod o1, ITestNGMethod o2) {
      return 0;
    }

    @Override
    public String toString() {
      return "No_Sorting";
    }
  };

  MethodSorting(String value) {
    this.value = value;
  }

  private final String value;

  public static Comparator<ITestNGMethod> basedOn() {
    String text = RuntimeBehavior.orderMethodsBasedOn();
    return MethodSorting.parse(text);
  }

  private static MethodSorting parse(String input) {
    String text = Optional.ofNullable(input).orElse("");
    return Arrays.stream(values())
        .filter(it -> it.value.equalsIgnoreCase(text))
        .findFirst()
        .orElse(INSTANCES);
  }
}
