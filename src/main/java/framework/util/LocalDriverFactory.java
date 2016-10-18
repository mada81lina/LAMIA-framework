package framework.util;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by MadalinaLu on 20.07.2015
 */
public class LocalDriverFactory {
    public static String hubUrl;
    public static String browserName;

    static WebDriver createInstance() {
        WebDriver driver;
        if (browserName.toLowerCase().contains("firefox")) {
            System.setProperty("webdriver.gecko.driver", "geckodriver.exe");
            FirefoxProfile fp = new FirefoxProfile();
            fp.setPreference("browser.startup.homepage", "about:blank");
            fp.setPreference("startup.homepage_welcome_url", "about:blank");
            fp.setPreference("startup.homepage_welcome_url.additional", "about:blank");
            DesiredCapabilities capabilities = DesiredCapabilities.firefox();
            capabilities.setCapability(FirefoxDriver.PROFILE, fp);
            capabilities.setCapability(CapabilityType.OVERLAPPING_CHECK_DISABLED, true);

            driver = new FirefoxDriver(capabilities);
            //driver.manage().window().setSize(new Dimension(1280, 1024));
            driver.manage().window().maximize();
            return driver;
        }
        if (browserName.toLowerCase().contains("internet")) {
            System.setProperty("webdriver.ie.driver", "IEDriverServer.exe");
            driver = new InternetExplorerDriver();
            driver.manage().window().maximize();
            return driver;
        }

        if (browserName.toLowerCase().contains("chrome")) {
            System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            return driver;
        }

        if (browserName.toLowerCase().contains("docker-ffx")) {
            if (hubUrl == null) throw new AssertionError("hubUrl parameter is null");
            try {
                FirefoxProfile fp = new FirefoxProfile();
                fp.setPreference("browser.startup.homepage", "about:blank");
                fp.setPreference("startup.homepage_welcome_url", "about:blank");
                fp.setPreference("startup.homepage_welcome_url.additional", "about:blank");
                DesiredCapabilities capabilities = DesiredCapabilities.firefox();
                capabilities.setCapability(FirefoxDriver.PROFILE, fp);
                capabilities.setCapability(CapabilityType.OVERLAPPING_CHECK_DISABLED, true);
                RemoteWebDriver remoteWebDriver = new RemoteWebDriver(
                        new URL(hubUrl),
                        capabilities);
                remoteWebDriver.setFileDetector(new LocalFileDetector());
                remoteWebDriver.manage().window().setSize(new Dimension(1280, 1024));
                return remoteWebDriver;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

        }
        if (browserName.toLowerCase().contains("docker-chr")) {
            if (hubUrl == null) throw new AssertionError("hubUrl parameter is null");
            try {
                DesiredCapabilities capabilities = DesiredCapabilities.chrome();
                RemoteWebDriver remoteWebDriver = new RemoteWebDriver(
                        new URL(hubUrl),
                        capabilities);
                remoteWebDriver.setFileDetector(new LocalFileDetector());
                remoteWebDriver.manage().window().setSize(new Dimension(1280, 1024));
                return remoteWebDriver;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

        }

        return null;
    }
}
