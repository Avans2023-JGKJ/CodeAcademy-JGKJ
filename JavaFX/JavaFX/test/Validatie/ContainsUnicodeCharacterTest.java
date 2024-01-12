import Validatie.DataValidatie;
import org.junit.Test;
import static org.junit.Assert.*;

public class ContainsUnicodeCharacterTest {

    @Test
    public void testContainsUnicodeCharacterWithUnicodeCharacter() {
        assertTrue(DataValidatie.containsUnicodeCharacter("Hello😊"));
    }

    @Test
    public void testContainsUnicodeCharacterWithoutUnicodeCharacter() {
        assertFalse(DataValidatie.containsUnicodeCharacter("Hello"));
    }

    @Test
    public void testContainsUnicodeCharacterWithNullInput() {
        assertFalse(DataValidatie.containsUnicodeCharacter(null));
    }

    @Test
    public void testContainsUnicodeCharacterWithEmptyString() {
        assertFalse(DataValidatie.containsUnicodeCharacter(""));
    }

    // You can add more specific tests based on your requirements.
}
