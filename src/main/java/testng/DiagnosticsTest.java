package testng;

import framework.test.TestBase;
import framework.util.Constants;
import framework.util.LocalWebDriverManager;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.*;

import static java.lang.Thread.sleep;

/**
 * Created by mlukacs on 9/26/2016.
 */

public class DiagnosticsTest extends TestBase {
    @Parameters({"testGlobalSuffix", "testClassSuffix"})
    public DiagnosticsTest(@Optional String testGlobalSuffix, @Optional String testClassSuffix) {
        super(testGlobalSuffix, testClassSuffix);
    }

    @Test
    @Parameters("appUrl2")
    public void Diagnostics(@Optional (Constants.urlDiagnostics) String appUrl2) throws InterruptedException {
        sleep(10000);
        LocalWebDriverManager.baseUrl = Constants.urlDiagnostics;
        DiagnosticsPage diagnosticsPage = new DiagnosticsPage().openDiagnosticPage(Constants.urlDiagnostics);
        diagnosticsPage
                .waitPage()
                .checkHardwareHealthStatusIndicator()
                .checkRobotStatusIndicator()
                .checkControlerStatusIndicator()
                .checkHardwareStatusIndicator()
                .checkVibrationStatusIndicator()
                .checkHomePath()
                .openDashboardPage()
                .waitPage();
        SoftAssertion.softAssertion();
    }
}