/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 *  Contains the state of one space on the gameboard
 *  Thread safe
 * @author Bill Cook
 */
public class Space {
    
    private Tile contents;
    private Multiplier multi;
    private boolean used;
    /*
        Creates a new Space given a Multiplier.
    */
    public Space(Multiplier m) {
        multi = m;
        contents = null;
        used = false;
    }
    
    /*
        Returns a simple String representation of the space
    */
    @Override
    public synchronized String toString() {
        String toReturn = "";
        switch (multi) {
            case DOUBLE_LETTER: toReturn += "Double Letter "; break;
            case DOUBLE_WORD: toReturn += "Double Word "; break;
            case TRIPLE_LETTER: toReturn += "Triple Letter "; break;
            case TRIPLE_WORD: toReturn += "Triple Word "; break;
            default: break;
        }
        if (contents != null) {
            toReturn += contents.toString();
        } else {
            toReturn += "Unoccupied";
        }
        return toReturn;
    }
    
    /*
        Returns occupying Tile. If unoccupied, returns null.
    */
    public synchronized Tile getTile() {
        return contents;
    }
    
    /*
        Sets the occupying Tile to be the Tile passed in.
    */
    public synchronized void setTile(Tile t) {
        contents = t;
    }

    /*
        setting the used flag for the multiplier
     */
    public synchronized void setUsed(){
        if(used == false)
            used = true;
    }

    /*
        Getting the the used flag for the spaces multiplier
     */
    public synchronized boolean getUsed(){return this.used;}

    /*
        Returns space's Multiplier.
    */
    public Multiplier getMultiplier() {
        return multi;
    }
}
