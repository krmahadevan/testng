package org.testng.internal;

import java.lang.reflect.Method;
import org.testng.IInstanceInfo;
import org.testng.annotations.IDataProviderAnnotation;

/** Represents an @{@link org.testng.annotations.DataProvider} annotated method. */
class DataProviderMethodRemovable extends DataProviderMethod {

  DataProviderMethodRemovable(
      IInstanceInfo<?> instance, Method method, IDataProviderAnnotation annotation) {
    super(instance, method, annotation);
  }

  public void setInstance(IInstanceInfo<?> instance) {
    this.instance = instance;
  }

  public void setMethod(Method method) {
    this.method = method;
  }
}
