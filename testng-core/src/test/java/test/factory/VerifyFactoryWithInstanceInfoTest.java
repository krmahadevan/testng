package test.factory;

import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;

public class VerifyFactoryWithInstanceInfoTest {

  @Test(dependsOnGroups = {"first"})
  public void mainCheck() {
    List<Integer> numbers = FactoryWithInstanceInfo2Sample.getNumbers();
    Assert.assertTrue(numbers.contains(42), "Didn't find 42");
    Assert.assertTrue(numbers.contains(43), "Didn't find 43");
    Assert.assertEquals(numbers.size(), 2);
  }
}
