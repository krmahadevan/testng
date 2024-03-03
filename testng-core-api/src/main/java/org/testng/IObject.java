package org.testng;

import java.util.Objects;
import java.util.UUID;

/** Represents the associations of a class with one or more instances. */
public interface IObject {

  default IdentifiableObject[] getObjects(boolean create) {
    return getObjects(create, "");
  }

  IdentifiableObject[] getObjects(boolean create, String errorMsgPrefix);

  long[] getInstanceHashCodes();

  void addObject(IdentifiableObject instance);

  /** A wrapper object that associates a unique id to every unique test class object. */
  class IdentifiableObject {
    private final Object instance;
    private final UUID instanceId;

    public IdentifiableObject(Object instance) {
      this(instance, UUID.randomUUID());
    }

    public IdentifiableObject(Object instance, UUID instanceId) {
      this.instance = instance;
      this.instanceId = instanceId;
    }

    public static Object unwrap(IdentifiableObject object) {
      if (object == null) {
        return null;
      }
      return object.getInstance();
    }

    public UUID getInstanceId() {
      return instanceId;
    }

    public Object getInstance() {
      return instance;
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) {
        return true;
      }
      if (object == null || getClass() != object.getClass()) {
        return false;
      }
      IdentifiableObject that = (IdentifiableObject) object;
      return Objects.equals(instance, that.instance);
    }

    @Override
    public int hashCode() {
      return Objects.hash(instance);
    }
  }
}
