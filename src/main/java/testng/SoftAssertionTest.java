package testng;

import framework.test.TestBase;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.SoftAssertion;

/**
 * Created by mlukacs on 10/6/2016.
 */
public class SoftAssertionTest extends TestBase {
    @Parameters({"testGlobalSuffix", "testClassSuffix"})
    public SoftAssertionTest(@Optional String testGlobalSuffix, @Optional String testClassSuffix) {
        super(testGlobalSuffix, testClassSuffix);
    }

    @Test
    public void softAssertion() throws InterruptedException {
        SoftAssertion.softAssertion();
    }
}
