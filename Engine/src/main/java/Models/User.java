/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.util.Random;

/**
 * Abstract class to by inherited by the AI and Human Users.
 * Tracks attributes for the player hand and score.
 * Provides methods for player hand actions.
 * @author Rick
 */
public abstract class User {
    
    // <editor-fold defaultstate="collapsed" desc="Private Instance Variables">
    private Tile[] hand;
    private int numTilesInHand;
    private final int maxTilesInHand = 7;
    private int score;
    private String username;
    private Random r = new Random();
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructor">
    public User()
    {
        hand = generateRandomHand();
        score = 0;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Abstract Methods">
    /* This method takes in a word as a string as well as the start and end coordinate.
    The method then calls the board’s updateBoard method.
    The method then returns the move’s score.
     */
    abstract int playWord(String word, int startX, int startY,
            int endX, int endY, Board board);

    /* Return the User's type. */
    abstract  String getType();
    
    // </editor-fold>

    //<editor-fold desc="Public Methods">
    /* Check the hand size and add tiles when necessary. */
    public void draw()
    {
        // If hand is smaller than maxTilesInHand, add tiles
        while (numTilesInHand < maxTilesInHand)
        {
            // then a letter is generated and added to the hand
            hand[numTilesInHand - 1] = new Tile(GameConstants.ALPHABET.charAt(r.nextInt(GameConstants.ALPHABET.length())), 1);
            numTilesInHand++;
        }
    }

    /* Return the current hand. */
    public synchronized Tile[] getHand()
    {
        return hand;
    }

    /* Return the current user's score.*/
    public synchronized int getScore()
    {
        return score;
    }

    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Helper Methods">
    /* Create a char array filling it with random tiles. */
    private Tile[] generateRandomHand()
    {
        Tile[] hand = new Tile[maxTilesInHand];
        for (int i = 0; i < hand.length; ++i)
        {
            hand[i] = new Tile(GameConstants.ALPHABET.charAt(r.nextInt(GameConstants.ALPHABET.length())), 1);
        }
        return hand;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }



    //</editor-fold>
    
}
