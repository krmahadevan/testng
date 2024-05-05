package test.listeners.issue3082;

import org.testng.IInstanceInfo;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

public class ObjectTrackingMethodListener implements IInvokedMethodListener {

  @Override
  public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
    String methodName = method.getTestMethod().getMethodName();
    IInstanceInfo<?> instance = method.getTestMethod().getInstanceInfo();
    if (instance.getInstance() instanceof IUniqueObject) {
      ObjectRepository.add(((IUniqueObject) instance.getInstance()).id(), methodName);
    }
  }
}
