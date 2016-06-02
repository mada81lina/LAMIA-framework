package framework.ui;

import framework.util.LocalWebDriverManager;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import pages.PageBase;

public abstract class CustomControl extends PageBase {

    protected WebElement parentElement;

    public CustomControl(WebElement element) {
        PageFactory.initElements(LocalWebDriverManager.getDriver(), this);
        this.parentElement = element;
    }

    public CustomControl() {
        PageFactory.initElements(LocalWebDriverManager.getDriver(), this);
    }
}
