import Validatie.DataValidatie;
import org.junit.Test;
import static org.junit.Assert.*;

public class ContainsUnicodeCharacterTest {

    @Test //String MET unicode test
    public void testContainsUnicodeCharacterWithUnicodeCharacter() {
        assertTrue(DataValidatie.containsUnicodeCharacter("Hello😊"));
    }

    @Test //String zonder Unicode test
    public void testContainsUnicodeCharacterWithoutUnicodeCharacter() {
        assertFalse(DataValidatie.containsUnicodeCharacter("Hello"));
    }

    @Test //String met NULL
    public void testContainsUnicodeCharacterWithNullInput() {
        assertFalse(DataValidatie.containsUnicodeCharacter(null));
    }

    @Test //Een lege String test
    public void testContainsUnicodeCharacterWithEmptyString() {
        assertFalse(DataValidatie.containsUnicodeCharacter(""));
    }

}
