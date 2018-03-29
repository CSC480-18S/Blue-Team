/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestComponents;

import Components.Validator;
import static Models.GameConstants.BOARD_WIDTH;
import Models.Move;
import Models.Player;
import Models.Tile;
import Models.TileGenerator;
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
    
    static Player p;
    static Validator val;
    
    public TestValidator() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        p = new Player("jim","fakeMac", "test");
        Session.getSession().playWord(BOARD_WIDTH/2, BOARD_WIDTH/2, 
                true, "wine", p);
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        val = new Validator();
        p = new Player("jim", "fakeMac", "test");
    }
    
    @After
    public void tearDown() {
    }

    // Test a valid multi-word play
    @Test
    public void testIsValidPlay1() {
        int result = (int) val.isValidPlay(new Move(BOARD_WIDTH/2+1, 
                BOARD_WIDTH/2+1, true, new Tile[] {TileGenerator.getTile('n'), 
                    TileGenerator.getTile('o')}, p))[0];
        assertEquals(1, result);
    }
    
    // Test an invalid multi-word play
    @Test
    public void testIsValidPlay2() {
        int result = (int) val.isValidPlay(new Move(BOARD_WIDTH/2+1, 
                BOARD_WIDTH/2+1, true, new Tile[] {TileGenerator.getTile('o'), 
                    TileGenerator.getTile('n')}, p))[0];
        assertEquals(0, result);
    }
}
