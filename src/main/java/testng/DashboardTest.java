package testng;

import framework.test.TestBase;
import framework.util.LocalWebDriverManager;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import framework.util.Constants;
import pages.*;


/**
 * Created by mlukacs on 9/26/2016.
 */

public class DashboardTest extends TestBase {

    @Parameters({"testGlobalSuffix", "testClassSuffix"})
    public DashboardTest(@Optional String testGlobalSuffix, @Optional String testClassSuffix) {
        super(testGlobalSuffix, testClassSuffix);
    }

    @Test
    @Parameters("appUrl")
    public void Dashboard(@Optional (Constants.urlDashboard) String appUrl) throws InterruptedException {
        LocalWebDriverManager.baseUrl = Constants.urlDashboard;
        DashboardPage dashboardPage = new DashboardPage().openDashboardPage(Constants.urlDashboard);
        dashboardPage
                .waitPage()
                .checkSystemInformation()
                .checkBackgroundColorLabel1()
                .checkBackgroundColorLabel2()
                .checkBackgroundColorLabel3()
                .openDiagnosticsPageFromBotton();
      //          .openDiagnosticsPage();
    }
}
