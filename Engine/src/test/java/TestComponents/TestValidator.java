/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestComponents;

import Components.Validator;
import Models.Player;
import Session.Session;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author wcook
 */
public class TestValidator {
    
    Validator val;
    
    public TestValidator() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        Player p = new Player("jim", "fakeMac");
        Session.getSession().playWord(0, 0, true, "wine", p);
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        val = new Validator();
    }
    
    @After
    public void tearDown() {
    }

    // Test a valid multi-word play
    @Test
    public void testIsValidPlay1() {
        int result = val.isValidPlay(1, 1, true, "no");
        assertEquals(1, result);
    }
    
    // Test an invalid multi-word play
    @Test
    public void testIsValidPlay2() {
        int result = val.isValidPlay(1, 1, true, "on");
        assertEquals(0, result);
    }
}
