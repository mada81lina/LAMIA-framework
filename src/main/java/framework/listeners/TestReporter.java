package framework.listeners;


import framework.data.TestData;
import framework.test.TestBase;
import framework.util.LocalWebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * Created by IlieV on 12.08.2015.
 * used for passing reports on failures.
 */
public class TestReporter implements ITestListener {

    @Override
    public void onFinish(ITestContext iTestContext) {
        long msDiff = iTestContext.getEndDate().getTime() - iTestContext.getStartDate().getTime();
        Reporter.log(String.format("\nTest run finished in: %d seconds.\n", msDiff / 1000), true);
    }

    @Override
    public void onStart(ITestContext iTestContext) {
        Reporter.log("===============================================", true);
        Reporter.log(String.format("Suite name: %s", iTestContext.getSuite().getName()), true);
        Reporter.log(String.format("Test output: %s", iTestContext.getOutputDirectory()), true);
        Reporter.log("===============================================\n", true);
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        String testName = iTestResult.getName();
        File screenShoot = ((TakesScreenshot) LocalWebDriverManager.getDriver()).getScreenshotAs(OutputType.FILE);

        try {
            String screenShotPath = Paths.get(String.format("%s/screenShots/%s_%d.png", TestData.DOCUMENT_LOCATION,
                    testName, System.currentTimeMillis() / 1000)).toString();
            String pageHtmlPath = Paths.get(String.format("%s/screenShots/%s_%d.html", TestData.DOCUMENT_LOCATION,
                    testName, System.currentTimeMillis() / 1000)).toString();
            FileUtils.copyFile(screenShoot, new File(screenShotPath));
            FileUtils.writeStringToFile(new File(pageHtmlPath), LocalWebDriverManager.getDriver().getPageSource());
            Reporter.log("\n-----------------------------------------------", true);
            Reporter.log(String.format("Test: \"%s\" has failed. Check screenshot at: %s", testName, screenShotPath), true);
            Reporter.log(String.format("Page URL where error occurred: %s", LocalWebDriverManager.getDriver().getCurrentUrl()), true);
            Reporter.log(String.format("HTML File can be found here: %s", pageHtmlPath), true);
            Reporter.log(String.format("Global suffix is: %s", TestBase.globalSuffix), true);
            Reporter.log(String.format("Class suffix is: %s", (iTestResult.getTestContext().getAttribute("ClassSuffix"))), true);
            Reporter.log("\n-----------------------------------------------", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        Reporter.log(String.format("%s - skipped!", iTestResult.getName()), true);
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {
        Reporter.log(iTestResult.getName() + " - started...", true);
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        Reporter.log(String.format("%s - passed!", iTestResult.getName()), true);
    }
}
