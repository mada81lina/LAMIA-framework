package pages;

import framework.util.LocalWebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public abstract class PageBase {

    protected WebDriver driver = LocalWebDriverManager.getDriver();

    protected String pageUrl = "/";

    @FindBy(id = "messagetext_1")
    protected WebElement messageInfo;

    public PageBase() {
        PageFactory.initElements(driver, this);
    }

    /**
     * Opens the current page in the browser.
     *
     * @return current page
     */
    protected PageBase open() {
        LocalWebDriverManager.openWithBaseUrl(pageUrl);
        return this;
    }
}
