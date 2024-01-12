import org.junit.Test;
import static org.junit.Assert.*;
import java.time.LocalDate;
import Validatie.DataValidatie;

public class CheckLegalDateTest {

    @Test //Valide Datum
    public void testCheckLegalDateWithValidDate() {
        LocalDate validDate = LocalDate.now().minusDays(1);
        assertTrue(DataValidatie.checkLegalDate(validDate));
    }

    @Test //Invalide NULL Test
    public void testCheckLegalDateWithNullDate() {
        assertFalse(DataValidatie.checkLegalDate(null));
    }

    @Test //Invalide Toekomst test
    public void testCheckLegalDateWithFutureDate() {
        LocalDate futureDate = LocalDate.now().plusDays(1);
        assertFalse(DataValidatie.checkLegalDate(futureDate));
    }

}

