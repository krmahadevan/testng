package test.configuration.issue2448;

import org.testng.annotations.BeforeMethod;

public class PapaBearGithubTest extends GrandpaBearGitHubTest {

  @BeforeMethod(alwaysRun = true, dependsOnMethods = {"mobileDriverCreation"})
  public void makeMostPages() {
    System.err.println(getClass().getName() + ".makeMostPages() invoked");
  }
}
