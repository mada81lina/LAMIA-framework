package framework.ui;

import framework.util.UIActions;
import org.openqa.selenium.By;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ByAll;

import java.util.List;

public class Table extends CustomControl {
    private By tableEmptyConnector = By.cssSelector("[id^=connectortableempty_]");

    private By nextPageArrow = By.cssSelector("a[id^='tablenav_top_nextlink_']");

    private By rowSelector = new ByAll(By.className("table_multiaction"),   // checkbox
            By.className("table_singleactionradiobutton"));         // radio button


    public Table(WebElement element) {
        super(element);
    }

    public void checkRowDoesNotExistByPartialString(String rowPartialText) {
        if (driver.findElements(tableEmptyConnector).size() == 0) {
            Boolean found = false;
            List<WebElement> tableRows = UIActions.waitForElement(parentElement)
                    .findElements(By.tagName("tr"));
            for (WebElement tableRow : tableRows) {
                if (tableRow.getText().contains(rowPartialText)) {
                    found = true;
                    break;
                }
            }
            if (found) {
                throw new InvalidElementStateException("Row with text: " + rowPartialText + " was found!");
            }
        }
    }

    public void checkRowExistsByString(String partialString) {
        getRowByPartialString(partialString);
    }

    /**
     * @param particularRowStringIdentifier is used to find the row where to verify a string
     * @param stringToFind                  string you want to check in the row
     */
    public void checkStringInOneParticularRow(String particularRowStringIdentifier, String stringToFind) {
        if (!getRowByPartialString(particularRowStringIdentifier).getText().contains(stringToFind)) {
            throw new NoSuchElementException("Row: " + "\"" + particularRowStringIdentifier + "\"" + " not contains string: " + "\"" + stringToFind + "\"" + "!");
        }
    }

    public void clickLink(String partialLinkText) {
        UIActions.waitAndClick(getCellLink(partialLinkText));
    }

    public void clickLinkInParticularRow(String particularRowStringIdentifier, By link) {
        getRowByPartialString(particularRowStringIdentifier).findElement(link).click();
    }

    public WebElement getRowByPartialString(String rowPartialText) {
        return getElement(rowPartialText, By.tagName("tr"));
    }

    public void refreshUntilTableIsNotVisible(int maxWaitTime) {
        UIActions.refreshPageUntilElementExists(tableEmptyConnector, maxWaitTime);
    }

    /**
     * Selects the row by partial row string
     *
     * @param rowPartialText the partial text in the row
     */
    public void selectRowByPartialString(String rowPartialText) {
        UIActions.waitForElement(rowSelector);
        UIActions.waitAndClick(getRowByPartialString(rowPartialText).findElement(rowSelector));
    }

    public void setRadioButton(String rowText, String radioValue) {
        Boolean found = false;
        WebElement radio = null;
        List<WebElement> radioInputsLabels = getRowByStringInHtml(rowText).findElements(By.className("form_element_radiobutton"));
        for (WebElement radioInputLabel : radioInputsLabels) {
            List<WebElement> radioInputs = radioInputLabel.findElements(By.tagName("input"));
            for (WebElement radioInput : radioInputs) {
                if (radioInput.getAttribute("value").contains(radioValue)) {
                    found = true;
                    radio = radioInputLabel;
                    break;
                }
            }
        }
        if (found) {
            radio.click();
        } else {
            throw new IllegalStateException("Radio with value: " + "\"" + radioValue + "\"" + " don't exist!");
        }
    }

    public void setValueInTextareaFieldType(String rowText, String value) {
        String elementId = getRowByStringInHtml(rowText).findElement(By.tagName("textarea")).getAttribute("id");
        getRowByStringInHtml(rowText).findElement(By.id(elementId)).clear();
        getRowByStringInHtml(rowText).findElement(By.id(elementId)).sendKeys(value);
    }

    /**
     * Returns the link containing specified text as WebElement
     *
     * @param linkText part of the text in the link.
     * @return the link from the table
     * @see WebElement
     */
    private WebElement getCellLink(String linkText) {
        return getElement(linkText, By.className("HSTableLink"));
    }

    private WebElement getElement(String partialString, By locator) {
        Boolean found = false;
        WebElement row = null;

        while (true) {
            UIActions.waitForElement(parentElement);
            List<WebElement> tableElements = parentElement.findElements(locator);

            for (WebElement tableElement : tableElements) {
                String tableText = tableElement.getText();
                if (tableText.toLowerCase().contains(partialString.toLowerCase())) {
                    row = tableElement;
                    found = true;
                    break;
                }
            }

            if (found) {
                return row;
            } else { // check next pages of the table for this value.
                try {
                    UIActions.click(parentElement.findElement(nextPageArrow));
                } catch (NoSuchElementException e) {
                    throw new NoSuchElementException("Element with text: " + partialString + " was not found!");
                }
            }
        }
    }

    private WebElement getRowByStringInHtml(String rowPartialText) {
        Boolean found = false;
        WebElement row = null;


        while (true) {
            List<WebElement> tableRows = UIActions.waitForElement(parentElement)
                    .findElements(By.tagName("tr"));

            for (WebElement tableRow : tableRows) {
                if (tableRow.getAttribute("innerHTML").contains(rowPartialText)) {
                    row = tableRow;
                    found = true;
                    break;
                }
            }

            if (found) {
                return row;
            } else { // check next pages of the table for this value.
                try {
                    UIActions.click(parentElement.findElement(nextPageArrow));
                } catch (NoSuchElementException e) {
                    throw new NoSuchElementException("Row with text: " + rowPartialText + " was not found!");
                }
            }
        }
    }

}
