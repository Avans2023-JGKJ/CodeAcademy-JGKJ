import org.junit.Test;
import static org.junit.Assert.*;
import java.time.LocalDate;
import Validatie.DataValidatie;

public class CheckBirthDateTest {

    @Test
    public void testCheckBirthDateWithValidDate() {
        LocalDate validDate = LocalDate.now().minusYears(DataValidatie.getMinimaleLeeftijdCursist() + 1);
        assertTrue(DataValidatie.checkBirthDate(validDate));
    }

    @Test
    public void testCheckBirthDateWithInvalidDate() {
        LocalDate futureDate = LocalDate.now().plusYears(1);
        assertFalse(DataValidatie.checkBirthDate(futureDate)); // Date in the future

        LocalDate underageDate = LocalDate.now().minusYears(DataValidatie.getMinimaleLeeftijdCursist() - 1);
        assertFalse(DataValidatie.checkBirthDate(underageDate)); // Underage date
    }

    // You can add more specific tests based on your requirements.
}

