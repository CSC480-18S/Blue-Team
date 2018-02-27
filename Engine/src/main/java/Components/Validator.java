package main.java.Components;

import static main.java.Session.Session.LogWarning;

import java.io.BufferedReader;
import java.io.FileReader;


/**
 * @Author Bohdan Yevdokymov
 *
 * Class to validate word and placement
 */
public class Validator {
    // This board is not used currently.
    // We either need to make a copy of the board in session
    // or keep this one updated to reflect the board in session
    //private Board board;

    public Validator(){
        //board = new Board();
    }

    /**
     *
     * @param startX
     * @param startY
     * @param horizontal
     * @param word
     * @return 1 - Valid play, 0 - invalid, -1 - swear word, 2 - bonus word
     */
    public int isValidPlay(int startX, int startY, boolean horizontal, String word)
    {
        // Check if the user has entered a bad word
        int valid = isProfane(word);
        if (valid == -1)
        {
            return valid;
        }
        // Check if the user entered a bonus word
        valid = isBonus(word);
        if (valid != 2)
        {
            // If not a bonus, Check if word is in dictionary
            valid = isDictionaryWord(word);
        }

        // Check for valid placement on the board


        return valid;
    }

    /// Check if word is a dictionary word
    private static int isDictionaryWord(String word)
    {
        try {
            if (Dictionaries.getDictionaries().getEnglishWords().contains(word))
            {
                return 1;
            }
        } catch (Exception e) {

            LogWarning(e.getMessage() + "\n" + e.getStackTrace());
        }

        return 0;
    }

    /// Check if word is a curse word
    private int isProfane(String word)
    {
        try {
            if (Dictionaries.getDictionaries().getBadWords().contains(word))
            {
                return -1;
            }
        } catch (Exception e) {
            LogWarning(e.getMessage() + "\n" + e.getStackTrace());
        }

        return 1;
    }

    /// Check if word is a bonus word
    private int isBonus(String word)
    {
        try {
            if (Dictionaries.getDictionaries().getSpecialWords().contains(word))
            {
                return 2;
            }
        } catch (Exception e) {
            LogWarning(e.getMessage() + "\n" + e.getStackTrace());
        }

        return 1;
    }

}
