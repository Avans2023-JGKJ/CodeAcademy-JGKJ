package Validatie;

import org.junit.Test;
import Validatie.DataValidatie;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class CheckVarCharTest {
    
    @Test //Valide tests
    public void testCheckVarCharWithValidInput() {
        assertTrue(DataValidatie.checkVarChar("abc", 5));
        assertTrue(DataValidatie.checkVarChar("123", 3));
        assertTrue(DataValidatie.checkVarChar("Hello", 10));
    }

    @Test //NULL test
    public void testCheckVarCharWithNullInput() {
        assertFalse(DataValidatie.checkVarChar(null, 5));
    }

    @Test //Lege String
    public void testCheckVarCharWithEmptyString() {
        assertTrue(DataValidatie.checkVarChar("", 0)); // Assuming 0 is always a valid length for an empty string
        assertFalse(DataValidatie.checkVarChar("", 5));
    }

    @Test //String te lang!
    public void testCheckVarCharWithTooLongString() {
        assertFalse(DataValidatie.checkVarChar("ThisIsTooLong", 5));
    }

    @Test //String met UniCode
    public void testCheckVarCharWithUnicodeCharacter() {
        assertFalse(DataValidatie.checkVarChar("Hello😊", 10));
    }
    
}

