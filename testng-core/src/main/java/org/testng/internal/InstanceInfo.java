package org.testng.internal;

import java.util.Objects;
import java.util.UUID;
import org.testng.IInstanceInfo;

public class InstanceInfo<T> implements IInstanceInfo<T> {
  // NULL_INSTANCE is only used by static data providers
  public static final InstanceInfo<?> NULL_INSTANCE = new InstanceInfo<>();

  private final UUID uuid;
  private final Class<T> m_instanceClass;
  private final T m_instance;

  private InstanceInfo() {
    uuid = UUID.randomUUID();
    m_instanceClass = null;
    m_instance = null;
  }

  public InstanceInfo(T instance) {
    this((Class<T>) instance.getClass(), instance);
  }

  public InstanceInfo(Class<T> cls, T instance) {
    uuid = UUID.randomUUID();
    m_instanceClass = Objects.requireNonNull(cls);
    m_instance = Objects.requireNonNull(instance);
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
