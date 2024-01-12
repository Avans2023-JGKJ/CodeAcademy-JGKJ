/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Validatie;

import org.junit.Test;
import Validatie.DataValidatie;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author gijsv
 */
public class CheckVarCharTest {
    
    @Test
    public void testCheckVarCharWithValidInput() {
        assertTrue(DataValidatie.checkVarChar("abc", 5));
        assertTrue(DataValidatie.checkVarChar("123", 3));
        assertTrue(DataValidatie.checkVarChar("Hello", 10));
    }

    @Test
    public void testCheckVarCharWithNullInput() {
        assertFalse(DataValidatie.checkVarChar(null, 5));
    }

    @Test
    public void testCheckVarCharWithEmptyString() {
        assertTrue(DataValidatie.checkVarChar("", 0)); // Assuming 0 is always a valid length for an empty string
        assertFalse(DataValidatie.checkVarChar("", 5));
    }

    @Test
    public void testCheckVarCharWithTooLongString() {
        assertFalse(DataValidatie.checkVarChar("ThisIsTooLong", 5));
    }

    @Test
    public void testCheckVarCharWithUnicodeCharacter() {
        assertFalse(DataValidatie.checkVarChar("Hello😊", 10));
    }
    
}

