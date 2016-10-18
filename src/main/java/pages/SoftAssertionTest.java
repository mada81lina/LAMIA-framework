package pages;

import org.testng.asserts.Assertion;
import org.testng.asserts.SoftAssert;

/**
 * Created by mlukacs on 10/5/2016.
 */
public class SoftAssertionTest extends PageBase {
    private static Assertion hardAssert = new Assertion();
    private static SoftAssert softAssert = new SoftAssert();

    public static void softAssertion() {
        if (softAssertionCheck == false) {
            System.out.println("TEST");
            softAssert.assertTrue(false);
            softAssert.assertEquals(1, 2);
            softAssert.assertAll();
        }
    }
}
