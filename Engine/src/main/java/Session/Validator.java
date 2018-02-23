package main.java.Session;

import main.java.Models.*;

/**
 * @Author Bohdan Yevdokymov
 *
 * Class to validate word and placement
 */
public class Validator {
    private Board board;

    public Validator(){
        board = new Board();
    }

    /**
     *
     * @param startX
     * @param startY
     * @param horizontal
     * @param word
     * @return 1 - Valid play, 0 - invalid, -1 - swear word
     */
    public int isValidPlay(int startX, int startY, boolean horizontal, String word){
        board.placeWord(startX,startY,horizontal, word);
        //logic to be implemented
        return 1;
    }

}
