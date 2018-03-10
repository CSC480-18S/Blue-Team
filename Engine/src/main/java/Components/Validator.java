package Components;

import Models.Space;
import Session.Session;

import static Session.Session.LogWarning;



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
        //valid = checkPlacement(startX, startY, horizontal, word);

        return valid;
    }

    /// Check if word is a dictionary word
    private static int isDictionaryWord(String word)
    {
        try {
            if (Dictionaries.getDictionaries().getEnglishWords().contains(word.toUpperCase()))
            {
                return 1;
            }
        } catch (Exception e) {

            //LogWarning(e.getMessage() + "\n" + e.getStackTrace());
        }

        return 0;
    }

    /// Check if word is a curse word
    private int isProfane(String word)
    {
        try {
            if (Dictionaries.getDictionaries().getBadWords().contains(word.toUpperCase()))
            {
                return -1;
            }
        } catch (Exception e) {
            //LogWarning(e.getMessage() + "\n" + e.getStackTrace());
        }

        return 1;
    }

    /// Check if word is a bonus word
    private int isBonus(String word)
    {
        try {
            if (Dictionaries.getDictionaries().getSpecialWords().contains(word.toUpperCase()))
            {
                return 2;
            }
        } catch (Exception e) {
            //LogWarning(e.getMessage() + "\n" + e.getStackTrace());
        }

        return 1;
    }

    /*
        Recursive method that checks validity of word placement
        -Bill Cook
    */
    private int checkPlacement(int x, int y, boolean horizontal,
                               String remaining) {
        if (remaining.length() == 0) {
            return 1;
        } else {
            String leftChars = "";
            String rightChars = "";
            boolean finished = false;
            Space[][] boardLocal = Session.getSession().getBoardAsSpaces();
            while (!finished) {
                if (horizontal) {
                    if (y >= 0 && boardLocal[x][y-1].getTile() != null)
                        leftChars = boardLocal[x][y+1].getTile().getLetter()
                                + leftChars;
                    else
                        finished = true;
                } else {
                    if (y < boardLocal[0].length && boardLocal[x+1][y].getTile()
                            != null)
                        rightChars += boardLocal[x-1][y].getTile().getLetter();
                    else
                        finished = true;
                }
            }
            String word = leftChars + remaining.charAt(0) + rightChars;
            if (isProfane(word) == -1) {
                return -1;
            } else if (isDictionaryWord(word) == 1) {
                return checkPlacement(horizontal ? x+1 : x, horizontal ?
                        y : y+1, horizontal, remaining.length() > 0 ?
                        remaining.substring(1) : "");
            } else {
                return 0;
            }
        }

    }

}
