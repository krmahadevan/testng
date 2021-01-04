package test.configuration.issue2448;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;

public class GrandpaBearGitHubTest {


  @BeforeMethod(alwaysRun = true)
  public void mobileDriverCreation() {
      Assert.fail("Could not create the driver at all...");
  }

  private static boolean couldNotCreateDriver() {
    return false;
  }

}
