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
    
    private Tile[] hand;
    private final int maxTilesInHand = 7;
    private int score;
    private String username;

    public User(String username)
    {
        score = 0;
        hand = new Tile[maxTilesInHand];
        this.username = username;
        draw();
    }

    /* This method takes in a word as a string as well as the start and end coordinate.
    The method then calls the board’s updateBoard method.
    The method then returns the move’s score.
     */
    abstract int playWord(String word, int startX, int startY,
            int endX, int endY, Board board);

    /* Return the User's type. */
    abstract  String getType();
    

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
