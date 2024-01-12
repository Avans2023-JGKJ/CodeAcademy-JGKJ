import org.junit.Test;
import static org.junit.Assert.*;
import Validatie.DataValidatie;

public class CheckPercentageTest {

    @Test //Valid Test
    public void testCheckPercentageWithValidValues() {
        assertTrue(DataValidatie.checkPercentage((short) 0));
        assertTrue(DataValidatie.checkPercentage((short) 50));
        assertTrue(DataValidatie.checkPercentage((short) 100));
    }

    @Test //Invalid Test
    public void testCheckPercentageWithInvalidValues() {
        assertFalse(DataValidatie.checkPercentage((short) -1)); // Below 0
        assertFalse(DataValidatie.checkPercentage((short) 101)); // Above 100
    }

}

