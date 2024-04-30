package org.testng.internal;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;
import org.testng.IInstanceInfo;
import org.testng.ITestClassInstance;

/**
 * Represents the associations of a class with one or more instances. Relevant with <code>@Factory
 * </code> annotation.
 */
public interface IObject {

  /**
   * Returns all the instances the methods will be invoked upon. This will typically be an array of
   * one object in the absence of a @Factory annotation.
   *
   * @param create - <code>true</code> if objects should be created before returning.
   * @param errorMsgPrefix - Text that should be prefixed to the error message when there are
   *     issues. Can be empty.
   * @return - An array of {@link ITestClassInstance} objects
   */
  IInstanceInfo<?>[] getObjects(boolean create, String errorMsgPrefix);

  /** @return - An array representing the hash codes of the corresponding instances. */
  long[] getInstanceHashCodes();

  /** @param instance - The instance that should be added to the list of instances. */
  void addObject(IInstanceInfo<?> instance);

  /**
   * @param object - The object that should be inspected for its compatibility with {@link IObject}.
   * @return - An array representing the hash codes of the corresponding instances.
   */
  static long[] instanceHashCodes(Object object) {
    return cast(object).map(IObject::getInstanceHashCodes).orElse(new long[] {});
  }

  /**
   * @param object - The object that should be inspected for its compatibility with {@link IObject}.
   * @param create - <code>true</code> if objects should be created before returning.
   * @return - An array (can be empty is instance compatibility fails) of {@link ITestClassInstance}
   *     objects.
   */
  static IInstanceInfo<?>[] objects(Object object, boolean create) {
    return objects(object, create, "");
  }

  /**
   * @param object - The object that should be inspected for its compatibility with {@link IObject}.
   * @param create - <code>true</code> if objects should be created before returning.
   * @param errorMsgPrefix - Text that should be prefixed to the error message when there are
   *     issues. Can be empty.
   * @return - An array (can be empty is instance compatibility fails) of {@link ITestClassInstance}
   *     objects.
   */
  static IInstanceInfo<?>[] objects(Object object, boolean create, String errorMsgPrefix) {
    return cast(object)
        .map(it -> it.getObjects(create, errorMsgPrefix))
        .orElse(new ITestClassInstance<?>[0]);
  }

  /**
   * @param object - The object that should be inspected for its compatibility with {@link IObject}.
   * @return - If the incoming object is an instance of {@link IObject} then the cast instance is
   *     wrapped within {@link Optional} else it would be an {@link Optional#empty()}
   */
  static Optional<IObject> cast(Object object) {
    if (object instanceof IObject) {
      return Optional.of((IObject) object);
    }
    return Optional.empty();
  }

  /**
   * A wrapper class that wraps around an array and associates a unique Id that can be used as a key
   * for the array.
   */
  class IdentifiableArrayObject {

    private final String instanceId = UUID.randomUUID().toString();

    private final Object[] parameters;

    public IdentifiableArrayObject(Object[] parameters) {
      this.parameters = parameters;
    }

    public String getInstanceId() {
      return instanceId;
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) return true;
      if (object == null || getClass() != object.getClass()) return false;
      IdentifiableArrayObject that = (IdentifiableArrayObject) object;
      return Arrays.equals(parameters, that.parameters);
    }

    @Override
    public int hashCode() {
      return Arrays.hashCode(parameters);
    }
  }
}
