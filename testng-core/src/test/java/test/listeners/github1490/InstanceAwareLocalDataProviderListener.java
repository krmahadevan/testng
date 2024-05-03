package test.listeners.github1490;

import java.util.Set;
import org.testng.IDataProviderMethod;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.collections.Sets;

public class InstanceAwareLocalDataProviderListener extends LocalDataProviderListener {
  public static Set<Object> instanceCollectionBeforeExecution = Sets.newHashSet();
  public static Set<Object> instanceCollectionAfterExecution = Sets.newHashSet();

  public InstanceAwareLocalDataProviderListener() {
    instanceCollectionBeforeExecution.clear();
    instanceCollectionAfterExecution.clear();
  }

  @Override
  public void beforeDataProviderExecution(
      IDataProviderMethod dataProviderMethod, ITestNGMethod method, ITestContext iTestContext) {
    super.beforeDataProviderExecution(dataProviderMethod, method, iTestContext);
    if (dataProviderMethod.getInstanceInfo().getInstance() != null) {
      instanceCollectionBeforeExecution.add(dataProviderMethod.getInstanceInfo().getInstance());
    }
  }

  @Override
  public void afterDataProviderExecution(
      IDataProviderMethod dataProviderMethod, ITestNGMethod method, ITestContext iTestContext) {
    super.afterDataProviderExecution(dataProviderMethod, method, iTestContext);
    if (dataProviderMethod.getInstanceInfo().getInstance() != null) {
      instanceCollectionAfterExecution.add(dataProviderMethod.getInstanceInfo().getInstance());
    }
  }
}
