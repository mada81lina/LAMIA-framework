package testng;

import framework.test.TestBase;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.GooglePage;

/**
 * Created by IlieV on 24.05.2016.
 * dud.
 */
@Test(threadPoolSize = 5)
public class FirstTest extends TestBase {

    @Parameters({"testGlobalSuffix", "testClassSuffix"})
    public FirstTest(@Optional String testGlobalSuffix, @Optional String testClassSuffix) {
        super(testGlobalSuffix, testClassSuffix);
    }

    @Test
    public void first_test() {
        new GooglePage()
                .open()
                .search("Commuter 4.0")
                .validateSearch("Canyon")
                .clickResult("Commuter 4.0");
    }
}
