import org.junit.Test;
import static org.junit.Assert.*;
import Validatie.DataValidatie;

public class CheckCijferTest {

    @Test
    public void testCheckCijferWithValidCijfer() {
        short validCijfer = 5;
        assertTrue(DataValidatie.checkCijfer(validCijfer));
    }

    @Test
    public void testCheckCijferWithInvalidCijfer() {
        short invalidCijfer = 0; // Less than the minimum allowed value
        assertFalse(DataValidatie.checkCijfer(invalidCijfer));

        invalidCijfer = 11; // Greater than the maximum allowed value
        assertFalse(DataValidatie.checkCijfer(invalidCijfer));
    }

    @Test
    public void testCheckCijferWithBoundaryValues() {
        short minValue = 1;
        short maxValue = 10;

        assertTrue(DataValidatie.checkCijfer(minValue));
        assertTrue(DataValidatie.checkCijfer(maxValue));
    }

    // You can add more specific tests based on your requirements.
}

