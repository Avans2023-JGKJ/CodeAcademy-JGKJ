import Validatie.DataValidatie;
import org.junit.Test;
import static org.junit.Assert.*;

public class CheckPostcodeTest {

    @Test
    public void testCheckPostcodeWithValidPostcode() {
        assertTrue(DataValidatie.checkPostcode("1234 AB"));
        assertTrue(DataValidatie.checkPostcode("5678 CD"));
    }

    @Test
    public void testCheckPostcodeWithInvalidPostcode() {
        assertFalse(DataValidatie.checkPostcode("12345 ABC")); // Too many digits
        assertFalse(DataValidatie.checkPostcode("123 AB")); // Too few digits
        assertFalse(DataValidatie.checkPostcode("abcd EF")); // Non-numeric digits
        assertFalse(DataValidatie.checkPostcode("1234 ef")); // Lowercase letters
        assertFalse(DataValidatie.checkPostcode(null)); // Null input
        assertFalse(DataValidatie.checkPostcode("")); // Empty string
    }

    // You can add more specific tests based on your requirements.
}

