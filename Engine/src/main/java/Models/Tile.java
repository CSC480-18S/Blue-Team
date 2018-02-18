/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backendEngine;

/**
 *  Scrabble Tile Instance
 * @author Bill Cook
 */
public class Tile {
    
    private char letter;
    private int val;
    
    /*
        Creates a new tile given a letter and point value
        Blank tile is designated as *
    */
    public Tile(char l, int v) {
        letter = l;
        val = v;
    }
    
    /*
        Returns a simple String representation of the tile
    */
    @Override
    public String toString() {
        return letter + ", value = " + val;
    }
    
    /*
        Returns tile's letter character
    */
    public char getLetter() {
        return letter;
    }
    
    /*
        Returns tile's letter value
    */
    public int getValue() {
        return val;
    }
}
