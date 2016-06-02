package pages;

import framework.util.UIActions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Created by IlieV on 24.05.2016.
 */
public class GoogleResultPage extends PageBase {

    @FindBy(className = "rc")
    List<WebElement> results;

    public void clickResult(String s) {
    }

    public GoogleResultPage validateSearch(String s) {
        UIActions.checkText(results, s);
        return this;
    }
}
