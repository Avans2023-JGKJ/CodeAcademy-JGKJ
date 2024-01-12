import org.junit.Test;
import static org.junit.Assert.*;
import Validatie.DataValidatie;

public class FormatDateTest {

    @Test //Valide waarde test
    public void testFormatDateWithValidDate() {
        String validDate = "2022-01-01";
        assertTrue(DataValidatie.formatDate(validDate));
    }

    @Test //Niet valide waarde test
    public void testFormatDateWithInvalidDate() {
        String invalidDate = "2022-13-01"; // Invalid month (greater than 12)
        assertFalse(DataValidatie.formatDate(invalidDate));

        invalidDate = "2022-00-01"; // Invalid month (less than 1)
        assertFalse(DataValidatie.formatDate(invalidDate));
    }

    @Test //Een NULL test
    public void testFormatDateWithNullDate() {
        assertFalse(DataValidatie.formatDate(null));
    }

}

