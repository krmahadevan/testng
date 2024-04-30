package org.testng.internal;

import java.lang.reflect.Method;
import java.util.List;
import org.testng.IDataProviderMethod;
import org.testng.IInstanceInfo;
import org.testng.IRetryDataProvider;
import org.testng.annotations.IDataProviderAnnotation;

/** Represents an @{@link org.testng.annotations.DataProvider} annotated method. */
class DataProviderMethod implements IDataProviderMethod {

  protected IInstanceInfo<?> instance;
  protected Method method;
  private final IDataProviderAnnotation annotation;

  DataProviderMethod(IInstanceInfo<?> instance, Method method, IDataProviderAnnotation annotation) {
    this.instance = instance;
    this.method = method;
    this.annotation = annotation;
  }

  @Override
  public Object getInstance() {
    // TODO return instance
    return instance.getInstance();
  }

  @Override
  public Method getMethod() {
    return method;
  }

  @Override
  public String getName() {
    return annotation.getName();
  }

  @Override
  public boolean isParallel() {
    return annotation.isParallel();
  }

  @Override
  public List<Integer> getIndices() {
    return annotation.getIndices();
  }

  @Override
  public boolean propagateFailureAsTestFailure() {
    return annotation.isPropagateFailureAsTestFailure();
  }

  @Override
  public Class<? extends IRetryDataProvider> retryUsing() {
    return annotation.retryUsing();
  }

  @Override
  public boolean cacheDataForTestRetries() {
    return annotation.isCacheDataForTestRetries();
  }
}
