package test.configuration.issue2448;

import java.util.Collections;
import java.util.List;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

public class RunnerTestExample {

  public static void main(String[] args) {
    XmlSuite xmlSuite = new XmlSuite();
    xmlSuite.setVerbose(2);
    xmlSuite.setName("2448_suite");
    XmlTest xmlTest = new XmlTest(xmlSuite);
    xmlTest.setName("2448_test");
    xmlTest.setXmlClasses(Collections.singletonList(new XmlClass(GrandsonBearGithubTest.class)));
    xmlTest.addIncludedGroup("yogi-bear");
    System.err.println("Suite file that will be executed");
    System.err.println(xmlSuite.toXml());
    TestListenerAdapter adapter = new TestListenerAdapter();
    TestNG testNG = new TestNG();
    testNG.setXmlSuites(Collections.singletonList(xmlSuite));
    testNG.addListener(adapter);
    testNG.run();
    List<ITestResult> skippedConfigs = adapter.getConfigurationSkips();
    List<ITestResult> failedConfigs = adapter.getConfigurationFailures();
    Assert.assertEquals(skippedConfigs.size(), 1);
    Assert.assertEquals(failedConfigs.size(), 1);
  }

}
