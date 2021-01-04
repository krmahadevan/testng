package test.configuration.issue2448;

import org.testng.annotations.Test;

public class GrandsonBearGithubTest extends PapaBearGithubTest {

  @Test(groups = "yogi-bear")
  public void testMethod() {
    System.err.println("Running test method");
  }

}
