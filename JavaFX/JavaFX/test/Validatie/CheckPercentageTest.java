import org.junit.Test;
import static org.junit.Assert.*;
import Validatie.DataValidatie;

public class CheckPercentageTest {

    @Test
    public void testCheckPercentageWithValidValues() {
        assertTrue(DataValidatie.checkPercentage((short) 0));
        assertTrue(DataValidatie.checkPercentage((short) 50));
        assertTrue(DataValidatie.checkPercentage((short) 100));
    }

    @Test
    public void testCheckPercentageWithInvalidValues() {
        assertFalse(DataValidatie.checkPercentage((short) -1)); // Below 0
        assertFalse(DataValidatie.checkPercentage((short) 101)); // Above 100
    }

    // You can add more specific tests based on your requirements.
}

