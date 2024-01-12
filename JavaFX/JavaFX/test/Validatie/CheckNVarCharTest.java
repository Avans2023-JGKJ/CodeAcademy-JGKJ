/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Validatie;

import Validatie.DataValidatie;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author gijsv
 */
public class CheckNVarCharTest {

    @Test
    public void testCheckNVarCharWithValidInput() {
        assertTrue(DataValidatie.checkNVarChar("abc", 5));
        assertTrue(DataValidatie.checkNVarChar("123", 3));
        assertTrue(DataValidatie.checkNVarChar("Hello", 10));
    }

    @Test
    public void testCheckNVarCharWithEmptyString() {
        assertFalse(DataValidatie.checkNVarChar("", 5));
    }

    @Test
    public void testCheckNVarCharWithNullInput() {
        assertFalse(DataValidatie.checkNVarChar(null, 5));
    }

    @Test
    public void testCheckNVarCharWithTooLongString() {
        assertFalse(DataValidatie.checkNVarChar("ThisIsTooLong", 5));
    }

    @Test
    public void testCheckNVarCharWithUnicodeCharacter() {
        assertTrue(DataValidatie.checkNVarChar("Hello😊", 10)); // Assuming it's valid for this method
    }

}
