import org.junit.Test;
import static org.junit.Assert.*;
import java.time.LocalDate;
import Validatie.DataValidatie;

public class CheckDateTest {

    @Test //Valide Test
    public void testCheckDateWithValidMonth() {
        LocalDate validDate = LocalDate.of(2022, 6, 15); // June is a valid month
        assertTrue(DataValidatie.checkDate(validDate));
    }

    @Test //Invalide Test
    public void testCheckDateWithInvalidMonth() {
        LocalDate invalidDate = LocalDate.of(2022, 13, 15); // Invalid month (greater than 12)
        assertFalse(DataValidatie.checkDate(invalidDate));

        invalidDate = LocalDate.of(2022, 0, 15); // Invalid month (less than 1)
        assertFalse(DataValidatie.checkDate(invalidDate));
    }

}

