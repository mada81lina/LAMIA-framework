package pages;

import framework.util.UIActions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by IlieV on 24.05.2016.
 * google page.
 */
public class GooglePage extends PageBase {

    @FindBy(className = "gsfi")
    private WebElement searchBox;
    @FindBy(name = "btnG")
    private WebElement searchButton;

    public GooglePage() {
        pageUrl = "/";
    }

    @Override
    public GooglePage open() {
        super.open();
        return this;
    }

    public GoogleResultPage search(String s) {
        UIActions.waitAndSetValue(searchBox, s);
        UIActions.click(searchButton);
        return new GoogleResultPage();
    }
}
