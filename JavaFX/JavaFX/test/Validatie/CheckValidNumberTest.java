import Validatie.DataValidatie;
import org.junit.Test;
import static org.junit.Assert.*;

public class CheckValidNumberTest {

    @Test
    public void testCheckValidNumberWithValidInput() {
        assertTrue(DataValidatie.checkValidNumber("5", 10));
        assertTrue(DataValidatie.checkValidNumber("10", 10));
    }

    @Test
    public void testCheckValidNumberWithInvalidInput() {
        assertFalse(DataValidatie.checkValidNumber("0", 10)); // Zero is not allowed
        assertFalse(DataValidatie.checkValidNumber("-5", 10)); // Negative number is not allowed
        assertFalse(DataValidatie.checkValidNumber("abc", 10)); // Non-numeric input
        assertFalse(DataValidatie.checkValidNumber(null, 10)); // Null input
        assertFalse(DataValidatie.checkValidNumber("", 10)); // Empty string
        assertFalse(DataValidatie.checkValidNumber("11", 10)); // Exceeds the limit
    }

    // You can add more specific tests based on your requirements.
}

