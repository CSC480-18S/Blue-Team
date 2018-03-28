/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Session.Session;
import java.util.Random;

/**
 * Abstract class to by inherited by the AI and Human Users.
 * Tracks attributes for the player hand and score.
 * Provides methods for player hand actions.
 * @author Rick
 */
public abstract class User {
    
    private Tile[] hand;
    private final int maxTilesInHand = 7;
    protected int score;
    private String username;
    protected final Session thisSession;

    public User(String username, Session session)
    {
        thisSession = session;
        score = 0;
        hand = new Tile[maxTilesInHand];
        this.username = username;
        draw();
    }

    /* 
       Update's the user's score for the move successfully played
       @return the value of the play
     */
    public int updateScore(Move move) {
        // Calculate Score
        int points = thisSession.calculateMovePoints(move);
        score += points;
        
        // Update score via SQL?
        
        return score;
    }

    /* Return the User's type. */
    abstract String getType();
    

    /* Check the hand size and add tiles when necessary. */
    public void draw()
    {
        TileGenerator tg = TileGenerator.getInstance();
        for(int i =0; i< hand.length; i ++){
            if(hand[i] == null){
                hand[i] = tg.getRandTile();
            }
        }
    }

    /* Return the current hand. */
    public synchronized Tile[] getHand()
    {
        return hand;
    }
    public synchronized void setHand(Tile[] hand)
    {
        this.hand = hand;
    }

    /* Return the current user's score.*/
    public synchronized int getScore()
    {
        return score;
    }


    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

}
