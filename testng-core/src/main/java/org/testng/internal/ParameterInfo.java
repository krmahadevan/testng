package org.testng.internal;

import java.util.Arrays;
import org.testng.IFactoryMethod;
import org.testng.ITestClassInstance;

public class ParameterInfo<T> extends InstanceInfo<T>
    implements ITestClassInstance<T>, IParameterInfo<T> {
  private final int index;
  private final IFactoryMethod factoryMethod;
  private final int invocationIndex;

  public ParameterInfo(T instance, int index, IFactoryMethod factoryMethod, int invocationIndex) {
    super(instance);
    this.index = index;
    this.factoryMethod = factoryMethod;
    this.invocationIndex = invocationIndex;
  }

  @Override
  public Class<T> getInstanceClass() {
    return (Class<T>) getInstance().getClass();
  }

  @Override
  public int getIndex() {
    return index;
  }

  @Override
  public int getInvocationIndex() {
    return invocationIndex;
  }

  @Override
  public IFactoryMethod getFactoryMethod() {
    return factoryMethod;
  }

  @Override
  public Object[] getParameters() {
    return factoryMethod.getParameters();
  }

  @Override
  public String toString() {
    return super.toString() + ", instance params:" + Arrays.toString(factoryMethod.getParameters());
  }
}
