import Validatie.DataValidatie;
import org.junit.Test;
import static org.junit.Assert.*;

public class CheckEmailAddressTest {

    @Test
    public void testCheckEmailAddressWithValidEmail() {
        assertTrue(DataValidatie.checkEmailAddress("test@example.com"));
        assertTrue(DataValidatie.checkEmailAddress("user123@gmail.com"));
    }

    @Test
    public void testCheckEmailAddressWithInvalidEmail() {
        assertFalse(DataValidatie.checkEmailAddress("invalid.email@")); // Missing top-level domain
        assertFalse(DataValidatie.checkEmailAddress("user@.com")); // Missing domain
        assertFalse(DataValidatie.checkEmailAddress("user@domain.")); // Missing top-level domain
        assertFalse(DataValidatie.checkEmailAddress("user@domain@.com")); // Multiple @ symbols
        assertFalse(DataValidatie.checkEmailAddress("user@domain..com")); // Double dot in domain
        assertFalse(DataValidatie.checkEmailAddress("user@domain@com")); // Multiple @ symbols without a dot
        assertFalse(DataValidatie.checkEmailAddress("user@123")); // Numeric domain
        assertFalse(DataValidatie.checkEmailAddress("user@domain..com")); // Double dot in domain
        assertFalse(DataValidatie.checkEmailAddress("user@domain@.com")); // Multiple @ symbols
        assertFalse(DataValidatie.checkEmailAddress("user@.domain.com")); // Dot after @
        assertFalse(DataValidatie.checkEmailAddress(null)); // Null input
        assertFalse(DataValidatie.checkEmailAddress("")); // Empty string
    }

    // You can add more specific tests based on your requirements.
}

