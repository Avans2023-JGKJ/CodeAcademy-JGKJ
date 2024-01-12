package Validatie;

import Validatie.DataValidatie;
import org.junit.Test;
import static org.junit.Assert.*;

public class CheckNVarCharTest {

    @Test //Valide Test
    public void testCheckNVarCharWithValidInput() {
        assertTrue(DataValidatie.checkNVarChar("abc", 5));
        assertTrue(DataValidatie.checkNVarChar("123", 3));
        assertTrue(DataValidatie.checkNVarChar("Hello", 10));
    }

    @Test //Lege String test
    public void testCheckNVarCharWithEmptyString() {
        assertFalse(DataValidatie.checkNVarChar("", 5));
    }

    @Test //NULL value test
    public void testCheckNVarCharWithNullInput() {
        assertFalse(DataValidatie.checkNVarChar(null, 5));
    }

    @Test //String is te lang
    public void testCheckNVarCharWithTooLongString() {
        assertFalse(DataValidatie.checkNVarChar("ThisIsTooLong", 5));
    }

    @Test //Test met Unicode
    public void testCheckNVarCharWithUnicodeCharacter() {
        assertTrue(DataValidatie.checkNVarChar("Hello😊", 10)); // Assuming it's valid for this method
    }

}
