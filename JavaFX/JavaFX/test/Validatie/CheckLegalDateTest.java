import org.junit.Test;
import static org.junit.Assert.*;
import java.time.LocalDate;
import Validatie.DataValidatie;

public class CheckLegalDateTest {

    @Test
    public void testCheckLegalDateWithValidDate() {
        LocalDate validDate = LocalDate.now().minusDays(1);
        assertTrue(DataValidatie.checkLegalDate(validDate));
    }

    @Test
    public void testCheckLegalDateWithNullDate() {
        assertFalse(DataValidatie.checkLegalDate(null));
    }

    @Test
    public void testCheckLegalDateWithFutureDate() {
        LocalDate futureDate = LocalDate.now().plusDays(1);
        assertFalse(DataValidatie.checkLegalDate(futureDate));
    }

    // You can add more specific tests based on your requirements.
}

