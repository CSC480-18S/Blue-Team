package main.java.Components;

import main.java.Models.*;
import static main.java.Session.Session.LogWarning;

import java.io.BufferedReader;
import java.io.FileReader;


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
    public int isValidPlay(int startX, int startY, boolean horizontal, String word)
    {
        // Check if the user has entered a bad word
        int valid = isProfane(word);
        if (valid == -1)
        {
            return valid;
        }
        // Check if word is in dictionary
        valid = isDictionaryWord(word);
        if (valid == 0)
        {
            return valid;
        }

        // Check for valid placement on the board


        return valid;
    }

    /// Check if word is a dictionary word
    private static int isDictionaryWord(String word)
    {
        try {
            BufferedReader in = new BufferedReader(new FileReader(
                    "../resources/11charwords.txt"));
            String str;
            while ((str = in.readLine()) != null) {
                if (str.contains(word)) {
                    return 1;
                }
            }
            in.close();
        } catch (Exception e) {

            LogWarning(e.getMessage() + "\n" + e.getStackTrace());
        }

        return 0;
    }

    /// Check if word is a curse word
    private int isProfane(String word)
    {
        try {
            BufferedReader in = new BufferedReader(new FileReader(
                    "/resources/profanity.txt"));
            String str;
            while ((str = in.readLine()) != null) {
                if (str.contains(word)) {
                    return -1;
                }
            }
            in.close();
        } catch (Exception e) {
            LogWarning(e.getMessage() + "\n" + e.getStackTrace());
        }

        return 1;
    }

}
