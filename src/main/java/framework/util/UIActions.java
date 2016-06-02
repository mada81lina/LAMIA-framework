package framework.util;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Reporter;

import java.util.List;
import java.util.Objects;

/**
 * Created by TironM on 23.07.2015
 */
public class UIActions {

    private UIActions() {
    }


    public static void checkElementAttributeValue(WebElement element, String attribute, String value) {
        waitForElement(element);
        String actualValue = element.getAttribute(attribute);
        if (!actualValue.contains(value)) {
            throw new InvalidElementStateException(
                    String.format("Element has the wrong value for attribute <%s>. Expected <%s>. Got <%s>!",
                            attribute, value, actualValue));
        }
    }

    public static void checkElementDoesNotExist(By element) {
        if (LocalWebDriverManager.getDriver().findElements(element).size() > 0) {
            throw new IllegalStateException("Element " + element.toString() + " still displayed in page!");
        }
    }

    public static void checkElementIsVisible(WebElement webElement) {
        UIActions.waitForElement(webElement);
    }

    public static void checkText(WebElement element, String expectedText) {
        if (expectedText == null) {
            return;
        }
        UIActions.waitForElement(element);
        String actualText = element.getText();
        if (!actualText.toLowerCase().contains(expectedText.toLowerCase())) {
            throw new IllegalStateException(
                    String.format("Element does not contain expected text. Got: %s. Expected: %s",
                            actualText, expectedText));
        }
    }

    public static void checkText(List<WebElement> webElements, String jobName) {
        Boolean elementFound = false;
        LocalWebDriverManager.waitForElementWithDefaultTimeout()
                .until(ExpectedConditions.visibilityOfAllElements(webElements));

        for (WebElement element : webElements) {
            if (element.getText().contains(jobName)) {
                elementFound = true;
                break;
            }
        }
        if (!elementFound) {
            throw new IllegalStateException(
                    String.format("Element list does not contain any element with string: %s", jobName));
        }
    }


    public static void checkTextIsNotPresent(WebElement element, String textToCheck) {
        if (textToCheck == null) {
            return;
        }
        waitForElement(element);
        String actualText = element.getText();
        if (actualText.contains(textToCheck)) {
            throw new IllegalStateException(
                    String.format("Element contains unexpected text. Got: %s.",
                            actualText));
        }
    }

    public static void checkTextIsNotPresentInPage(String text) {
        if (LocalWebDriverManager.getDriver().findElements(
                By.xpath("//*[contains(text(),'" + text + "')]")).size() > 0) {
            throw new IllegalStateException("Text '%s' was found on page!");
        }
    }

    public static void click(WebElement element) {
        element.click();
    }

    public static void clickFirstLinkInParticularDiv(String titleDiv, List<WebElement> list) {
        for (WebElement div : list)
            if (div.getText().contains(titleDiv)) {
                div.findElement(By.tagName("a")).click();
                break;
            }
    }

    public static String downloadFile(WebElement element, String documentTitle) {
        FileDownloader fileDownloader = new FileDownloader(LocalWebDriverManager.getDriver());

        try {
            return fileDownloader.downloadFile(element, documentTitle);
        } catch (Exception e) {
            e.printStackTrace();
            Reporter.log(String.format("Download failed for %s", documentTitle));
        }

        return null;
    }

    public static void mouseOverAndClick(WebElement elementToHover, WebElement elementToClick) {
        UIActions.waitForElement(elementToHover);
        Actions builder = new Actions(LocalWebDriverManager.getDriver());
        builder.moveToElement(elementToHover).perform();
        UIActions.waitAndClick(elementToClick);
    }

    /**
     * <B>Function starts with a refresh. Make sure that you are on the correct page when using this.
     * To do that, check that a specific element that should be on that page is visible!!!</B>
     *
     * @param locator          - the By of the element to wait for.
     * @param maxSecondsToWait - number of tries before throwing NoSuchElementException
     * @return the element that was searched.
     */
    public static WebElement refreshPageUntilElementExists(By locator, int maxSecondsToWait) {
        int numberOfTries = 0;
        int intervalToWait = 5;
        WebElement element = null;
        do {
            numberOfTries++;
            LocalWebDriverManager.getDriver().navigate().refresh();
            try {
                element = LocalWebDriverManager.waitForElement(intervalToWait)
                        .until(ExpectedConditions.elementToBeClickable(locator));
            } catch (TimeoutException ignored) {
            }
        } while (numberOfTries <= maxSecondsToWait / intervalToWait && element == null);

        if (element == null) {
            throw new NoSuchElementException(String.format("Element with locator %s was not found after %d tries",
                    locator.toString(), maxSecondsToWait));
        }

        return element;
    }

    public static void refreshPageUntilTextExists(WebElement locator, String text, int maxSecondsToWait) {
        int numberOfTries = 0;
        int intervalToWait = 5;
        Boolean element = false;
        do {
            numberOfTries++;
            LocalWebDriverManager.getDriver().navigate().refresh();
            try {
                element = LocalWebDriverManager.waitForElement(intervalToWait)
                        .until(ExpectedConditions.textToBePresentInElement(locator, text));
            } catch (TimeoutException ignored) {
            }
        } while (numberOfTries <= maxSecondsToWait / intervalToWait && !element);

        if (!element) {
            throw new NoSuchElementException(String.format("Element with locator %s was not found after %d tries",
                    locator.toString(), maxSecondsToWait));
        }
    }

    public static void sendLongString(WebElement element, String longString) {
        if (longString != null) {
            ((JavascriptExecutor) LocalWebDriverManager.getDriver())
                    .executeScript("arguments[0].value = arguments[1];", element, longString);
        }
    }

    /**
     * @param element the web element containing the date fields.
     * @param date    Date format parameter: MM.DD.YYY or if you have hour and minutes: MM:DD:YYYY hh:mm
     */
    public static void setDate(WebElement element, String date) {
        setValue(element, date);
    }

    public static void setValue(WebElement element, String value) {
        if (value == null || Objects.equals(value, "null")) {
            return;
        }
        try {
            element.clear();
        } catch (Exception ignored) {
        }
        element.sendKeys(value);
    }

    public static void setValue(Select select, String value) {
        if (value != null) {
            select.selectByValue(value);
        }
    }

    public static void unckeckCheckbox(WebElement webElement) {
        String checkboxHtml = webElement.getAttribute("outerHTML");
        if (checkboxHtml.contains("selected")) {
            UIActions.click(webElement);
        }
    }

    public static void waitAndClick(WebElement element) {
        LocalWebDriverManager.waitForElementWithDefaultTimeout()
                .until(ExpectedConditions.elementToBeClickable(element));
        click(element);
    }

    public static void waitAndSetValue(WebElement element, String value) {
        if (value == null) {
            return;
        }

        UIActions.waitForElement(element);
        setValue(element, value);
    }

    public static WebElement waitForElement(WebElement webElement) {
        LocalWebDriverManager.disableImplicitWait();
        WebElement element = LocalWebDriverManager.waitForElementWithDefaultTimeout()
                .until(ExpectedConditions.visibilityOf(webElement));
        LocalWebDriverManager.enableImplicitWait();
        return element;
    }

    public static WebElement waitForElement(By locator) {
        LocalWebDriverManager.disableImplicitWait();
        WebElement element = LocalWebDriverManager.waitForElementWithDefaultTimeout()
                .until(ExpectedConditions.elementToBeClickable(locator));
        LocalWebDriverManager.enableImplicitWait();
        return element;
    }

    public static void waitForInvisibilityOfElement(By locator) {
        LocalWebDriverManager.disableImplicitWait();
        LocalWebDriverManager.waitForElementWithDefaultTimeout()
                .until(ExpectedConditions.invisibilityOfElementLocated(locator));
        LocalWebDriverManager.enableImplicitWait();
    }

    public static void waitForSaveToFinish() {
        waitForInvisibilityOfElement(By.id("loadingIconGif"));
    }
}
