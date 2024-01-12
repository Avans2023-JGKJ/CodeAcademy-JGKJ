import Validatie.DataValidatie;
import org.junit.Test;
import static org.junit.Assert.*;

public class CheckURLTest {

    @Test //Valid URL Test
    public void testCheckURLWithValidURLs() {
        assertTrue(DataValidatie.checkURL("https://www.example.com"));
        assertTrue(DataValidatie.checkURL("http://subdomain.domain.com"));
    }

    @Test //Invalid URL test
    public void testCheckURLWithInvalidURLs() {
        assertFalse(DataValidatie.checkURL("invalid-url")); // No protocol and domain
        assertFalse(DataValidatie.checkURL("ftp://www.example.com")); // Unsupported protocol
        assertFalse(DataValidatie.checkURL("http://www.example")); // Missing top-level domain
        assertFalse(DataValidatie.checkURL("http://www.example@domain.com")); // @ symbol in the path
        assertFalse(DataValidatie.checkURL("http://www.example..com")); // Double dot in domain
        assertFalse(DataValidatie.checkURL("http://www.example@domain.com")); // @ symbol in domain
        assertFalse(DataValidatie.checkURL("http://www..example.com")); // Double dot after www
        assertFalse(DataValidatie.checkURL("http://www.example.com/path//file")); // Double slash in path
        assertFalse(DataValidatie.checkURL(null)); // Null input
        assertFalse(DataValidatie.checkURL("")); // Empty string
    }

}

