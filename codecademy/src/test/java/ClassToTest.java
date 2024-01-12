import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.Month;

import org.junit.Test;

import com.example.ValidateFunctions.ValidateFunctions;


public class ClassToTest{
    @Test
    public void testFormatPostalCodeWithValidPostalCodeReturnsABSpaceOneTwoThreeFour() {

        String postalCode = ValidateFunctions.formatPostalCode("1234 AB");

        assertEquals("1234 AB", postalCode);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFormatPostalCodeWithInvalidPostalCodeReturnsIllegalArgumentException() {

        String postalCode = ValidateFunctions.formatPostalCode("fj4442");

        assertEquals(IllegalArgumentException.class, postalCode);
    }

    @Test(expected = NullPointerException.class)
    public void testFormatPostalCodeWithNullReturnsNullPointerException() {

        String postalCode = ValidateFunctions.formatPostalCode(null);

        assertEquals(NullPointerException.class, postalCode);
    }

    @Test
    public void testValidateDateWithThirtyFirstOfJanuaryTwoThousandReturnTrue() {

        boolean validDate = ValidateFunctions.validateDate(31, 1, 2000);

        assertTrue(validDate);
    }

    @Test
    public void testValidateDateWithThirtiethOfAprilTwoThousandReturnTrue() {

        boolean validDate = ValidateFunctions.validateDate(30, 4, 2000);

        assertTrue(validDate);
    }

    @Test
    public void testValidateDateWithTwentyNinthOfFebruaryTwoThousandAndTwelveReturnTrue() {

        boolean validDate = ValidateFunctions.validateDate(29, 2, 2012);

        assertTrue(validDate);
    }

    @Test
    public void testValidateDateWithTwentyNinthOfFebruaryTwoThousandAndFourReturnTrue() {

        boolean validDate = ValidateFunctions.validateDate(29, 2, 2004);

        assertTrue(validDate);
    }

    @Test
    public void testValidateDateWithInvalidDateReturnsFalse() {

        boolean validDate = ValidateFunctions.validateDate(32, 8, 203040);

        assertFalse(validDate);
    }

    @Test
    public void testIsValidPercentageWithNineReturnsTrue() {

        boolean isValidPercentage = ValidateFunctions.isValidPercentage(99);

        assertTrue(isValidPercentage);

    }

    @Test
    public void testIsValidPercentageWithMinusFiveReturnsFalse() {

        boolean isValidPercentage = ValidateFunctions.isValidPercentage(-5);

        assertFalse(isValidPercentage);
    }

    @Test
    public void testIsValidPercentageWithMinusOneHundredAndOneReturnsFalse() {

        boolean isValidPercentage = ValidateFunctions.isValidPercentage(101);

        assertFalse(isValidPercentage);
    }

    @Test
    public void testValidateMailAddressWithAtTestDotNlReturnsFalse() {

        boolean isValidEmail = ValidateFunctions.validateMailAddress("@test.nl");

        assertFalse(isValidEmail);
    }

    @Test
    public void testValidateMailAddressWithTestAtTestDotTestDotNlReturnsFalse() {

        boolean isValidEmail = ValidateFunctions.validateMailAddress("test@test.test.nl");

        assertFalse(isValidEmail);
    }

    @Test
    public void testValidateMailAddressWithTestAtDotNlReturnsFalse() {

        boolean isValidEmail = ValidateFunctions.validateMailAddress("test@.nl");

        assertFalse(isValidEmail);
    }

    @Test
    public void testValidateMailAddressWithTestAtTestReturnsFalse() {

        boolean isValidEmail = ValidateFunctions.validateMailAddress("test@test");

        assertFalse(isValidEmail);
    }

    @Test
    public void testValidateMailAddressWithTestAtTestDotNlReturnsTrue() {

        boolean isValidEmail = ValidateFunctions.validateMailAddress("test@test.nl");

        assertTrue(isValidEmail);
    }



    //de nieuwe testsets voor formatURL
    
    @Test
    public void testValidateFormatURLWithHttpsWwwDotTestDotCom() {
        String url = "https://www.test.com";
        assertTrue(ValidateFunctions.validateFormatURL(url));
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestValidateFormatURLWithWwwDotTestDotCom() {
        String url = "www.test.com";
        assertEquals(IllegalArgumentException.class, ValidateFunctions.validateFormatURL(url));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testvalidateFormatURLWithHttps() {
        String url = "https://";
        assertEquals(IllegalArgumentException.class, ValidateFunctions.validateFormatURL(url));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateFormatUrlWithHttpsWwwDotTestDotCom() {
        String url = "https:/www.test.com";
        assertEquals(IllegalArgumentException.class, ValidateFunctions.validateFormatURL(url));
    }

     @Test(expected = IllegalArgumentException.class)
    public void testValidateFormatUrlWithHttpsDotTestDotCom() {
        String url = "https:/.test.com";
        assertEquals(IllegalArgumentException.class, ValidateFunctions.validateFormatURL(url));
    }

     @Test(expected = IllegalArgumentException.class)
    public void testValidateFormatUrlWithHttpsWwwDotDotCom() {
        String url = "https:/www..com";
        assertEquals(IllegalArgumentException.class, ValidateFunctions.validateFormatURL(url));
    }

     @Test(expected = IllegalArgumentException.class)
    public void testValidateFormatUrlWithHttpsWwwDotTestDot() {
        String url = "https:/www.test.";
        assertEquals(IllegalArgumentException.class, ValidateFunctions.validateFormatURL(url));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateFormatUrlWithHttpsWwwDotDotTestDotCom() {
        String url = "https://www..test.com";
        assertEquals(IllegalArgumentException.class, ValidateFunctions.validateFormatURL(url));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateFormatUrlWithHttpsWwwDotTestDotDotCom() {
        String url = "https://www.test..com";
        assertEquals(IllegalArgumentException.class, ValidateFunctions.validateFormatURL(url));
    }


    @Test(expected = IllegalArgumentException.class)
    public void testValidateFormatUrlWithHttpsWwwDotTestDotCoDotUk() {
        String url = "https://www.test.co..uk";
        assertEquals(IllegalArgumentException.class, ValidateFunctions.validateFormatURL(url));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateFormatUrlWithHttpsWwwDotDotCoDotUk() {
        String url = "https://www..co.uk";
        assertEquals(IllegalArgumentException.class, ValidateFunctions.validateFormatURL(url));
    }

    @Test           
    public void testIfValidateRatingReturnsATrueIfTheNumberIsBetweenOneAndTen(){
        boolean isValidRating = ValidateFunctions.validateRating(2); 

        assertTrue(isValidRating);
    }

    @Test
    public void testIfValidateRatingReturnsAFalseIfTheNumberIsAboveTenOrUnderOne(){
        boolean isValidRating = ValidateFunctions.validateRating(22);

        assertFalse(isValidRating);
    }

    @Test
    public void testValidtateRatingWithTestAtTestReturnsFalseUnderOne(){
        boolean isValidRating = ValidateFunctions.validateRating(0);

        assertFalse(isValidRating);
    }


    //functienamen goedzetten
    //test maken voor 1 en 10 in validaterating
    //test maken voor onder de 1
}