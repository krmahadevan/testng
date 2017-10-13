package test.issue1390;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

public class TestInstance {
    private int id;

    @Factory(dataProvider = "getData")
    public TestInstance(int id) {
        this.id = id;
    }

    @DataProvider(name = "getData")
    public static Object[][] getData() {
        Object[][] data = new Object[1000][1];
        for (int i = 0; i < 1000; i++) {
            data[i] = new Object[]{i};
        }
        return data;
    }

    @BeforeMethod
    public void beforeMethod() {

    }

    @Test
    public void something() {

    }

    @Test(dependsOnMethods = "something")
    public void somethingElse() {

    }

    @AfterMethod
    public void afterMethod() {
    }
}
