/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 * Its a record of a move played (Or attempted to be played)
 * @author wcook
 */
public class Move {

    private int startX;
    private int startY;
    private boolean horizontal;
    private String word;
    private User user;

    public Move(int startX, int startY, boolean horizontal, String word, User user) {
        this.user = user;
        this.startX = startX;
        this.startY = startY;
        this.horizontal = horizontal;
        this.word = word;
        this.user = user;
    }

    /**
     * @return the startX
     */
    public int getStartX() {
        return startX;
    }

    /**
     * @return the startY
     */
    public int getStartY() {
        return startY;
    }

    /**
     * @return the horizontal
     */
    public boolean isHorizontal() {
        return horizontal;
    }

    /**
     * @return the word
     */
    public String getWord() {
        return word;
    }

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param word the word to set
     * @param word that it is set to
     */
    public String setWord(String word) {
        this.word = word;
        return word;
    }

}
