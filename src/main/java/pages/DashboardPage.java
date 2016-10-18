package pages;

import framework.util.Constants;
import framework.util.Log;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Reporter;
import org.testng.asserts.Assertion;
import org.testng.asserts.SoftAssert;

import static java.lang.Thread.sleep;

/**
 * Created by Madalina-PC on 9/24/2016.
 */
public class DashboardPage extends PageBase {
    private static Assertion hardAssert = new Assertion();
    private static SoftAssert softAssert = new SoftAssert();

    @FindBy(id = "sidebarTopDiagnosticsOverview")
    private WebElement diagnosticsButton1;
    @FindBy(xpath = "//*[@id=\'contentRightBottomMain\']/div[2]/div/div[2]/div[3]/div/a/div")
    private WebElement diagnosticsButton2;
    @FindBy(xpath = "//*[@id=\'contentRightBottomMain\']/div[2]/div/div[2]/div[1]/div/div")
    private WebElement backgroundStatusIndicator1;
    @FindBy(xpath = "//*[@id=\'contentRightBottomMain\']/div[2]/div/div[2]/div[2]/div/div")
    private WebElement backgroundStatusIndicator2;
    @FindBy(xpath = "//*[@id=\'contentRightBottomMain\']/div[2]/div/div[2]/div[3]/div/div")
    private WebElement backgroundStatusIndicator3;
    @FindBy(xpath = "//*[@id=\"contentRightBottomMain\"]/div[2]/div/div[2]/div[2]/div/div/strong")
    private WebElement StatusIndicatorText2;
    @FindBy(xpath = "//*[@id=\"contentRightBottomMain\"]/div[2]/div/div[2]/div[3]/div/div/strong")
    private WebElement StatusIndicatorText3;
    @FindBy(xpath = "//*[@id='contentRightBottomMain']/div[2]/div/div[1]/div/div")
    private WebElement systemInformation;

    public DashboardPage() {
        pageUrl = "/";
    }

    @Override
    public DashboardPage open() {
        super.open();
        return this;
    }

    public DashboardPage waitPage() throws InterruptedException {
        sleep(15000);
        return this;
    }

    public DashboardPage openDashboardPage(String url) throws InterruptedException {
        driver.get(url);
        sleep(10000);
        return this;

    }

    public DashboardPage checkSystemInformation() {
        try {
            hardAssert.assertTrue(systemInformation.getText() != "");
            System.out.println("Test 1 PASS System Information check");
            Reporter.log("Test 1 PASS -  System Information check");
        } catch (AssertionError e) {
            softAssertionCheck = false;
            System.out.println("Test 1 FAIL System Information check - field empty");
            Reporter.log("Test 1 - FAIL System Information check - field empty");
        }
        return this;
    }

    public DashboardPage checkBackgroundColorLabel1() {
        String color1 = backgroundStatusIndicator1.getCssValue("background-color");
        try {
            hardAssert.assertTrue(color1.contains(Constants.blueP1));
            System.out.println("Test 2 PASS - Dashboard check BackgroundColorLabel1 BLUE");
            Reporter.log("Test 2 PASS Dashboard check BackgroundColorLabel1 BLUE");
        } catch (AssertionError e) {
            softAssertionCheck = false;
            System.out.println("Test 2 FAIL Dashboard check BackgroundColorLabel1 actual " + color1 + " expected BLUE");
            Reporter.log("Test 2 FAIL Dashboard check BackgroundColorLabel1 actual " + color1 + " expected BLUE");
        }
        return this;
    }

    public DashboardPage checkBackgroundColorLabel2() {
        String color2 = backgroundStatusIndicator2.getCssValue("background-color");
        try {
            if (StatusIndicatorText2.getText().equalsIgnoreCase("On")) {
                hardAssert.assertTrue(color2.contains(Constants.greenP1));
                System.out.println("Test 3 PASS Dashboard check BackgroundColorLabel2 GREEN");
                Reporter.log("Test 3 PASS Dashboard check BackgroundColorLabel2 GREEN");
            }
        } catch (AssertionError e) {
            softAssertionCheck = false;
            System.out.println("Test 3 FAIL Dashboard check BackgroundColorLabel2 actual " + color2 + " expected GREEN");
            Reporter.log("Test 3 FAIL Dashboard check BackgroundColorLabel2 actual " + color2 + " expected GREEN");
        }
        try {
            if (StatusIndicatorText2.getText().equalsIgnoreCase("Off")) {
                hardAssert.assertTrue(color2.contains(Constants.redP1));
                System.out.println("Test 3 PASS Dashboard check BackgroundColorLabel2 RED");
                Reporter.log("Test 3 PASS Dashboard check BackgroundColorLabel2 RED");
            }
        } catch (AssertionError e) {
            softAssertionCheck = false;
            System.out.println("Test 3 FAIL Dashboard check BackgroundColorLabel2 actual " + color2 + " expected RED");
            Reporter.log("Test 3 FAIL Dashboard check BackgroundColorLabel2 actual " + color2 + " expected RED");
        }
        return this;
    }

    public DashboardPage checkBackgroundColorLabel3() {
        String color3 = backgroundStatusIndicator3.getCssValue("background-color");
        try {
            if (StatusIndicatorText3.getText().equalsIgnoreCase("NORMAL")) {
                hardAssert.assertTrue(color3.contains(Constants.greenP1));
                System.out.println("Test 4 PASS Dashboard check BackgroundColorLabel3 GREEN");
                Reporter.log("Test 4 PASS Dashboard check BackgroundColorLabel3 GREEN");
            }
        } catch (AssertionError e) {
            softAssertionCheck = false;
            System.out.println("Test 4 FAIL Dashboard check BackgroundColorLabel2 actual " + color3 + " expected GREEN");
            Reporter.log("Test 4 FAIL Dashboard check BackgroundColorLabel2 actual " + color3 + " expected GREEN");
        }
        try {
            if (StatusIndicatorText3.getText().equalsIgnoreCase("WARNING")) {
                hardAssert.assertTrue(color3.contains(Constants.orangeP1));
                System.out.println("Test 4 PASS Dashboard check BackgroundColorLabel3 ORANGE");
                Reporter.log("Test 4 PASS Dashboard check BackgroundColorLabel3 ORANGE");
            }
        } catch (AssertionError e) {
            softAssertionCheck = false;
            System.out.println("Test 4 FAIL Dashboard check BackgroundColorLabel3 actual " + color3 + " expected ORANGE");
            Reporter.log("Test 4 FAIL Dashboard check BackgroundColorLabel3 actual " + color3 + " expected ORANGE");
        }
        try {
            if (StatusIndicatorText3.getText().equalsIgnoreCase("ERROR")) {
                hardAssert.assertTrue(color3.contains(Constants.redP1));
                System.out.println("Test 4 PASS Dashboard check BackgroundColorLabel3 RED");
                Reporter.log("Test 4 PASS Dashboard check BackgroundColorLabel3 RED");
            }
        } catch (AssertionError e) {
            softAssertionCheck = false;
            System.out.println("Test 4 FAIL Dashboard check BackgroundColorLabel3 actual " + color3 + " expected RED");
            Reporter.log("Test 4 FAIL Dashboard check BackgroundColorLabel3 actual " + color3 + " expected RED");
        }
        return this;
    }

    public DiagnosticsPage openDiagnosticsPage() throws InterruptedException {
        sleep(10000);
        try {
            diagnosticsButton1.click();
            sleep(10000);
            System.out.println("Test 5 PASS Diagnostic Page Open");
            Reporter.log("Test 5 PASS Diagnostic Page Open");
        } catch (AssertionError e) {
            softAssertionCheck = false;
            System.out.println("Test 5 FAIL Diagnostic Page Open");
            Reporter.log("Test 5 FAIL Diagnostic Page Open");
        }
        return new DiagnosticsPage();
    }
    public DiagnosticsPage openDiagnosticsPageFromBotton() throws InterruptedException {
        sleep(10000);
        try {
            diagnosticsButton2.click();
            sleep(10000);
            System.out.println("Test 5 PASS Diagnostic Page Open");
            Reporter.log("Test 5 PASS Diagnostic Page Open");
        } catch (AssertionError e) {
            softAssertionCheck = false;
            System.out.println("Test 5 FAIL Diagnostic Page Open");
            Reporter.log("Test 5 FAIL Diagnostic Page Open");
        }
        return new DiagnosticsPage();
    }
}