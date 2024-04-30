package org.testng.internal.invokers;

import java.util.List;
import java.util.Map;
import org.testng.IInstanceInfo;
import org.testng.IMethodInstance;
import org.testng.ITestNGMethod;
import org.testng.collections.ListMultiMap;
import org.testng.collections.Lists;
import org.testng.collections.Maps;
import org.testng.internal.MethodHelper;
import org.testng.thread.IWorker;

class InstanceBasedParallelParallelWorker extends AbstractParallelWorker {
  @Override
  public List<IWorker<ITestNGMethod>> createWorkers(Arguments arguments) {
    ListMultiMap<IInstanceInfo<?>, ITestNGMethod> lmm = Maps.newSortedListMultiMap();
    for (ITestNGMethod m : arguments.getMethods()) {
      lmm.put((IInstanceInfo<?>) m.getInstance(), m);
    }
    List<IWorker<ITestNGMethod>> result = Lists.newArrayList();
    IInvoker invoker = arguments.getInvoker();
    ITestInvoker testInvoker = invoker.getTestInvoker();
    IConfigInvoker configInvoker = invoker.getConfigInvoker();
    for (Map.Entry<IInstanceInfo<?>, List<ITestNGMethod>> es : lmm.entrySet()) {
      List<IMethodInstance> methodInstances = MethodHelper.methodsToMethodInstances(es.getValue());
      TestMethodWorker tmw =
          new TestMethodWorker(
              testInvoker,
              configInvoker,
              methodInstances,
              arguments.getTestContext().getCurrentXmlTest().getAllParameters(),
              arguments.getConfigMethods(),
              arguments.getClassMethodMap(),
              arguments.getTestContext(),
              arguments.getListeners());
      result.add(tmw);
    }

    return result;
  }
}
