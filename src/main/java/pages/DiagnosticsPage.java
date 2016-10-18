package pages;

/**
 * Created by mlukacs on 9/26/2016.
 */
import framework.util.Constants;
import framework.util.Log;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Reporter;
import org.testng.asserts.Assertion;
import org.testng.asserts.SoftAssert;
import static java.lang.Thread.sleep;

/**
 * Created by mlukacs on 9/29/2016.
 */
public class DiagnosticsPage extends PageBase {
        private static Assertion hardAssert = new Assertion();
        private static SoftAssert softAssert = new SoftAssert();

        @FindBy(id = "sidebarTopDashboard")
        private WebElement dashboardButton;
        @FindBy(xpath = "//*[@id=\'contentRightBottomMain\']/div[2]/div/div/div[1]/div/div")
        private WebElement hardwareHealthStatusIndicator;
        @FindBy(xpath = "//*[@id=\'contentRightBottomMain\']/div[2]/div/div/div[1]/div/div/h3/strong")
        private WebElement hardwareStatusIndicatorText1;
        @FindBy(xpath = "//*[@id=\'contentRightBottomMain\']/div[2]/div/div/div[2]/div/a/div")
        private WebElement robotStatusIndicator;
        @FindBy(xpath = "//*[@id=\'contentRightBottomMain\']/div[2]/div/div/div[3]/div/a/div")
        private WebElement controlerStatusIndicator;
        @FindBy(xpath = "//*[@id=\'contentRightBottomMain\']/div[2]/div/div/div[4]/div/a/div")
        private WebElement hardwareStatusIndicator;
        @FindBy(xpath = "//*[@id=\'contentRightBottomMain\']/div[2]/div/div/div[5]/div/a/div")
        private WebElement vibrationStatusIndicator;
        @FindBy(xpath = "//*[@id='contentRightBottomMain'']/div[1]/ul/li[1]/a")
        private WebElement homeButton;
        @FindBy(xpath = "//*[@id='contentRightBottomMain']/div[2]/div/div[1]/div/div")
        private WebElement systemInformation;
        @FindBy(xpath = "//*[@id=\"contentRightBottomMain\"]/div[1]/ul")
        private WebElement homePath;

        public DiagnosticsPage() {
            pageUrl = "/";
        }
        @Override
        public DiagnosticsPage open() {
            super.open();
            return this;
        }

        public DiagnosticsPage openDiagnosticPage(String url) throws InterruptedException {
               driver.get(url);
               sleep(10000);
               return this;

        }
        public DiagnosticsPage waitPage() throws InterruptedException {
            sleep (10000);
            return this;
        }

        public DashboardPage openDashboardPage() throws InterruptedException {
            sleep(10000);
            try{
                dashboardButton.click();
                sleep(10000);
                System.out.println("Test 12 PASS Dashboard Page Open");
                Reporter.log("Test 12 PASS Dashboard Page Open");
            }catch(AssertionError e) {
                softAssertionCheck = false;
                System.out.println("Test 12 FAIL Dashboard Page Open");
                Reporter.log("Test 12 FAIL Dashboard Page Open");
            }
            return new DashboardPage();
        }

        public DiagnosticsPage checkHomePath() {
            try {
                if (homePath.isDisplayed()) {
                    hardAssert.assertTrue(true);
                    System.out.println("Test 11 PASS breadcrumb menu is displayed");
                    Reporter.log("Test 11 PASS breadcrumb menu is displayed");
                }
            }catch (AssertionError e){
                softAssertionCheck = false;
                System.out.println("Test 11 FAIL breadcrumb menu is displayed");
                Reporter.log("Test 11 FAIL breadcrumb menu is displayed");
            }
            return this;
        }

        public DiagnosticsPage gotoHomeButton() throws InterruptedException {
            homeButton.click();
            sleep(5000);
            try {
                if (systemInformation.isDisplayed()) {
                    hardAssert.assertTrue(true);
                    System.out.println("PASS Diagnostics pages is displayed after Home Button click ");
                    Reporter.log("PASS Diagnostics pages is displayed after Home Button click ");
                }
            }catch (AssertionError e){
                softAssertionCheck = false;
                System.out.println("FAIL Diagnostics pages is displayed after Home Button click ");
                Reporter.log("FAIL Diagnostics pages is displayed after Home Button click ");
            }
            return this;
        }

        public DiagnosticsPage checkHardwareHealthStatusIndicator(){
            String color1 = hardwareHealthStatusIndicator.getCssValue("background-color");
            try{
                if (hardwareStatusIndicatorText1.getText().equalsIgnoreCase("NORMAL")) {
                    hardAssert.assertTrue(color1.contains(Constants.green));
                    System.out.println("Test 6 PASS Diagnostics check HardwareHealthStatusIndicator GREEN");
                    Reporter.log("Test 6 PASS Diagnostics check HardwareHealthStatusIndicator GREEN");
                }
            }catch (AssertionError e){
                softAssertionCheck = false;
                System.out.println("Test 6 FAIL Diagnostics check BackgroundHardwareStatusIndicator actual "+ color1 + " expected GREEN" );
                Reporter.log("Test 6 FAIL Diagnostics check BackgroundHardwareStatusIndicator actual "+ color1 + " expected GREEN" );
            }
            try{
                if (hardwareStatusIndicatorText1.getText().equalsIgnoreCase("WARNING")) {
                    hardAssert.assertTrue(color1.contains(Constants.orange));
                    System.out.println("Test 6 PASS Diagnostics check BackgroundHardwareStatusIndicator ORANGE");
                    Reporter.log("Test 6 PASS Diagnostics check BackgroundHardwareStatusIndicator ORANGE");
                }
            }catch (AssertionError e){
                softAssertionCheck = false;
                System.out.println("Test 6 FAIL Diagnostics check BackgroundHardwareStatusIndicator actual "+ color1 + " expected ORANGE" );
                Reporter.log("Test 6 FAIL Diagnostics check BackgroundHardwareStatusIndicator actual "+ color1 + " expected ORANGE" );
            }
            try{
                if (hardwareStatusIndicatorText1.getText().equalsIgnoreCase("ERROR")) {
                    hardAssert.assertTrue(color1.contains(Constants.red));
                    System.out.println("Test 6 PASS Diagnostics check BackgroundHardwareStatusIndicator RED");
                    Reporter.log("Test 6 PASS Diagnostics check BackgroundHardwareStatusIndicator RED");
                }
            }catch (AssertionError e){
                softAssertionCheck = false;
                System.out.println("Test 6 FAIL Diagnostics check BackgroundHardwareStatusIndicator actual "+ color1 + " expected RED" );
                Reporter.log("Test 6 FAIL Diagnostics check BackgroundHardwareStatusIndicator actual "+ color1 + " expected RED" );
            }
            return this;
        }

    public DiagnosticsPage checkRobotStatusIndicator(){
        String color2 = robotStatusIndicator.getCssValue("background-color");
        String text2 = robotStatusIndicator.getText().toUpperCase();
        try{
            if (text2.contains("NORMAL")) {
                hardAssert.assertTrue(color2.contains(Constants.green));
                System.out.println("Test 7 PASS Diagnostics check RobotStatusIndicator GREEN");
                Reporter.log("Test 7 PASS Diagnostics check RobotStatusIndicator GREEN");
            }
        }catch (AssertionError e){
            softAssertionCheck = false;
            System.out.println("Test 7 FAIL Diagnostics check RobotStatusIndicator actual "+ color2 + " expected GREEN" );
            Reporter.log("Test 7 FAIL Diagnostics check RobotStatusIndicator actual "+ color2 + " expected GREEN" );
        }
        try{
            if (text2.contains("WARNING")) {
                hardAssert.assertTrue(color2.contains(Constants.orange));
                System.out.println("Test 7 Diagnostics check RobotStatusIndicator ORANGE");
                Reporter.log("Test 7 Diagnostics check RobotStatusIndicator ORANGE");
            }
        }catch (AssertionError e){
            softAssertionCheck = false;
            System.out.println("Test 7 FAIL Diagnostics check RobotStatusIndicator actual "+ color2 + " expected ORANGE" );
            Reporter.log("Test 7 FAIL Diagnostics check RobotStatusIndicator actual "+ color2 + " expected ORANGE" );
        }
        try{
            if (text2.contains("ERROR")) {
                hardAssert.assertTrue(color2.contains(Constants.red));
                System.out.println("Test 7 PASS Diagnostics check RobotStatusIndicator RED");
                Reporter.log("Test 7 PASS Diagnostics check RobotStatusIndicator RED");
            }
        }catch (AssertionError e){
            softAssertionCheck = false;
            System.out.println("Test 7 FAIL Diagnostics check RobotStatusIndicator actual "+ color2 + " expected RED" );
            Reporter.log("Test 7 FAIL Diagnostics check RobotStatusIndicator actual "+ color2 + " expected RED" );
        }
        return this;
    }

    public DiagnosticsPage checkControlerStatusIndicator(){
        String color3 =controlerStatusIndicator.getCssValue("background-color");
        String text3 = controlerStatusIndicator.getText().toUpperCase();
        try{
            if (text3.contains("NORMAL")) {
                hardAssert.assertTrue(color3.contains(Constants.green));
                System.out.println("Test 8 PASS Diagnostics check ControlerStatusIndicator GREEN");
                Reporter.log("Test 8 PASS Diagnostics check ControlerStatusIndicator GREEN");
            }
        }catch (AssertionError e){
            softAssertionCheck = false;
            System.out.println("Test 8 FAIL Diagnostics check ControlerStatusIndicator actual "+ color3 + " expected GREEN" );
            Reporter.log("Test 8 FAIL Diagnostics check ControlerStatusIndicator actual "+ color3 + " expected GREEN" );
        }
        try{
            if (text3.contains("WARNING")) {
                hardAssert.assertTrue(color3.contains(Constants.orange));
                System.out.println("Test 8 PASS Diagnostics check ControlerStatusIndicator ORANGE");
                Reporter.log("Test 8 PASS Diagnostics check ControlerStatusIndicator ORANGE");
            }
        }catch (AssertionError e){
            softAssertionCheck = false;
            System.out.println("Test 8 FAIL Diagnostics check ControlerStatusIndicator actual "+ color3 + " expected ORANGE" );
            Reporter.log("Test 8 FAIL Diagnostics check ControlerStatusIndicator actual "+ color3 + " expected ORANGE" );
        }
        try{
            if (text3.contains("ERROR")) {
                hardAssert.assertTrue(color3.contains(Constants.red));
                System.out.println("Test 8 PASS Diagnostics check ControlerStatusIndicator RED");
                Reporter.log("Test 8 PASS Diagnostics check ControlerStatusIndicator RED");
            }
        }catch (AssertionError e){
            softAssertionCheck = false;
            System.out.println("Test 8 FAIL Diagnostics check ControlerStatusIndicator actual "+ color3 + " expected RED" );
            Reporter.log("Test 8 FAIL Diagnostics check ControlerStatusIndicator actual "+ color3 + " expected RED" );
        }
        return this;
    }

    public DiagnosticsPage checkHardwareStatusIndicator(){
        String color4 = hardwareStatusIndicator.getCssValue("background-color");
        String text4 = hardwareStatusIndicator.getText().toUpperCase();
        try{
            if (text4.contains("NORMAL")) {
                hardAssert.assertTrue(color4.contains(Constants.green));
                System.out.println("Test 9 PASS Diagnostics check HardwareStatusIndicator GREEN");
                Reporter.log("Test 9 PASS Diagnostics check HardwareStatusIndicator GREEN");
            }
        }catch (AssertionError e){
            softAssertionCheck = false;
            System.out.println("Test 9 FAIL Diagnostics check HardwareStatusIndicator actual "+ color4 + " expected GREEN" );
            Reporter.log("Test 9 FAIL Diagnostics check HardwareStatusIndicator actual "+ color4 + " expected GREEN" );
        }
        try{
            if (text4.contains("WARNING")) {
                hardAssert.assertTrue(color4.contains(Constants.orange));
                System.out.println("Test 9 PASS Diagnostics check HardwareStatusIndicator ORANGE");
                Reporter.log("Test 9 PASS Diagnostics check HardwareStatusIndicator ORANGE");
            }
        }catch (AssertionError e){
            softAssertionCheck = false;
            System.out.println("Test 9 FAIL Diagnostics check HardwareStatusIndicator actual "+ color4 + " expected ORANGE" );
            Reporter.log("Test 9 FAIL Diagnostics check HardwareStatusIndicator actual "+ color4 + " expected ORANGE" );
        }
        try{
            if (text4.contains("ERROR")) {
                hardAssert.assertTrue(color4.contains(Constants.red));
                System.out.println("Test 9 PASS Diagnostics check HardwareStatusIndicator RED");
                Reporter.log("Test 9 PASS Diagnostics check HardwareStatusIndicator RED");
            }
        }catch (AssertionError e){
            softAssertionCheck = false;
            System.out.println("Test 9 FAIL Diagnostics check HardwareStatusIndicator actual "+ color4 + " expected RED" );
            Reporter.log("Test 9 FAIL Diagnostics check HardwareStatusIndicator actual "+ color4 + " expected RED" );
        }
        return this;
    }

    public DiagnosticsPage checkVibrationStatusIndicator(){
        String color5 = vibrationStatusIndicator.getCssValue("background-color");
        String text5 = vibrationStatusIndicator.getText().toUpperCase();
        try{
            if (text5.contains("NORMAL")) {
                hardAssert.assertTrue(color5.contains(Constants.green));
                System.out.println("Test 10 PASS Diagnostics check VibrationStatusIndicator GREEN");
                Reporter.log("Test 10 PASS Diagnostics check VibrationStatusIndicator GREEN");
            }
        }catch (AssertionError e){
            softAssertionCheck = false;
            System.out.println("Test 10 FAIL Diagnostics check VibrationStatusIndicator actual "+ color5 + " expected GREEN" );
            Reporter.log("Test 10 FAIL Diagnostics check VibrationStatusIndicator actual "+ color5 + " expected GREEN" );
        }
        try{
            if (text5.contains("WARNING")) {
                hardAssert.assertTrue(color5.contains(Constants.orange));
                System.out.println("Test 10 PASS Diagnostics check VibrationStatusIndicator ORANGE");
                Reporter.log("Test 10 PASS Diagnostics check VibrationStatusIndicator ORANGE");
            }
        }catch (AssertionError e){
            softAssertionCheck = false;
            System.out.println("Test 10 FAIL Diagnostics check VibrationStatusIndicator actual "+ color5 + " expected ORANGE" );
            Reporter.log("Test 10 FAIL Diagnostics check VibrationStatusIndicator actual "+ color5 + " expected ORANGE" );
        }
        try{
            if (text5.contains("ERROR")) {
                hardAssert.assertTrue(color5.contains(Constants.red));
                System.out.println("Test 10 PASS Diagnostics check VibrationStatusIndicator RED");
                Reporter.log("Test 10 PASS Diagnostics check VibrationStatusIndicator RED");
            }
        }catch (AssertionError e){
            softAssertionCheck = false;
            System.out.println("Test 10 FAIL Diagnostics check VibrationStatusIndicator actual "+ color5 + " expected RED" );
            Reporter.log("Test 10 FAIL Diagnostics check VibrationStatusIndicator actual "+ color5 + " expected RED" );
        }
        return this;
    }
}

