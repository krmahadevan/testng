package org.testng;

/** This interface captures a test method along with all the instances it should be run on. */
public interface IMethodInstance {

  ITestNGMethod getMethod();

  @Deprecated
  default Object getInstance() {
    return getInstanceInfo().getInstance();
  }

  IInstanceInfo<?> getInstanceInfo();
}
