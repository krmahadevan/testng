package org.testng.internal.dynamicgraph;

import org.testng.IInstanceInfo;
import org.testng.ITestNGMethod;
import org.testng.internal.InstanceInfo;
import org.testng.internal.WrappedTestNGMethod;

public class FakeWrappedFactoryMethod extends WrappedTestNGMethod {

  private final IInstanceInfo<?> instance;

  public FakeWrappedFactoryMethod(ITestNGMethod testNGMethod, InstanceInfo<?> instance) {
    super(testNGMethod);
    this.instance = instance;
  }

  @Override
  public IInstanceInfo<?> getInstanceInfo() {
    return instance;
  }
}
