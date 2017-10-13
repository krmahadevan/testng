package test.issue1390;

import org.testng.Reporter;
import org.testng.TestNG;
import org.testng.annotations.Test;
import test.SimpleBaseTest;

public class Issue1390TestRunner extends SimpleBaseTest {
    @Test
    public void testMethod() {
        TestNG testng = create(TestInstance.class);
        long start = System.currentTimeMillis();
        testng.run();
        long end = System.currentTimeMillis();
        Reporter.log("Total execution time in ms : " + (end - start), true);
    }
}
