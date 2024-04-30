package org.testng.internal;

import java.util.UUID;
import org.testng.IInstanceInfo;

public class InstanceInfo<T> implements IInstanceInfo<T> {
  private final UUID uuid;
  private final Class<T> m_instanceClass;
  private final T m_instance;

  public InstanceInfo(T instance) {
    this(instance == null ? null : (Class<T>) instance.getClass(), instance);
  }

  public InstanceInfo(Class<T> cls, T instance) {
    uuid = UUID.randomUUID();
    m_instanceClass = cls;
    m_instance = instance;
  }

  @Override
  public UUID getUid() {
    return uuid;
  }

  @Override
  public T getInstance() {
    return m_instance;
  }

  @Override
  public Class<T> getInstanceClass() {
    return m_instanceClass;
  }
}
