package framework.test;

import framework.data.TestData;
import framework.listeners.TestReporter;
import framework.util.LocalDriverFactory;
import framework.util.LocalWebDriverManager;
import org.apache.commons.io.FileUtils;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;

@Listeners({TestReporter.class})
public class TestBase {


    public static String globalSuffix = TestData.generateRandomSuffix();
    protected String classSuffix = TestData.generateRandomSuffix();

    @Parameters({"testGlobalSuffix", "testClassSuffix"})
    public TestBase(@Optional String testGlobalSuffix, @Optional String testClassSuffix) {
        if (testGlobalSuffix != null) globalSuffix = testGlobalSuffix;
        if (testClassSuffix != null) classSuffix = testClassSuffix;
    }

    @AfterMethod(groups = "config.default")
    public void methodCleanup(ITestResult iTestResult) {
        LocalWebDriverManager.quit();
    }

    @BeforeMethod(groups = "config.default")
    public void methodSetup() {
        LocalWebDriverManager.createDriver();
    }

    @BeforeClass(alwaysRun = true)
    public void reportClassSuffix(ITestContext iTestContext) {
        iTestContext.setAttribute("ClassSuffix", classSuffix);
    }

    @Parameters({"browserName", "hubUrl"})
    @BeforeSuite(alwaysRun = true)
    public void suiteSetup(@Optional("firefox") String browserName, @Optional String hubURL, ITestContext iTestContext) {
        LocalDriverFactory.hubUrl = hubURL;
        LocalDriverFactory.browserName = browserName;
        String testOutputFolder = iTestContext.getOutputDirectory();
        TestData.DOCUMENT_LOCATION = Paths.get(String.format("%s/TestFiles/", testOutputFolder)).toString();

        if (Files.isDirectory(Paths.get(testOutputFolder), LinkOption.NOFOLLOW_LINKS)) {
            try {
                FileUtils.deleteDirectory(new File(testOutputFolder));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Parameters({"appUrl"})
    @BeforeTest(alwaysRun = true)
    public void testSetup(String appUrl, ITestContext iTestContext) {
        LocalWebDriverManager.baseUrl = appUrl;
    }
}
