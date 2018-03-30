package Components;

import Models.GameConstants;
import Models.Move;
import Models.Space;
import Models.Tile;
import Models.TileGenerator;
import Session.Session;

import static Session.Session.LogWarning;

/**
 * @Author Bohdan Yevdokymov, Bill Cook
 *
 * Class to validate word and placement
 */
public class Validator {

    public Validator() {
    }

    /**
     *
     * @param startX
     * @param startY
     * @param horizontal
     * @param word
     * @return An array containing an int:
     *      1 - Valid play, 0 - invalid, -1 - swear word, 2 - bonus word
     *         as well as the updated Move
     */
    public Object[] isValidPlay(Move move) {
        int startX = move.getStartX();
        int startY = move.getStartY();
        boolean horizontal = move.isHorizontal();
        
        // Check that move connects to existing tiles
        if (!connectsToTiles(move)) {
            return new Object[] {0, move};
        }
        
        // Get full word, appending any characters on the ends due to placement
        move.setWord(getFullWord(startX, startY, horizontal, 
                move.getWordString()));
        String word = move.getWordString();

        // Check if the user has entered a bad word
        int valid = isProfane(word);

        if (valid == -1) {
            return new Object[] {valid, move};
        }
        // Check if the user entered a bonus word
        valid = isBonus(word);
        if (valid != 2) {
            // If not a bonus, Check if word is in dictionary
            valid = isDictionaryWord(word);
        }

        // Check for valid placement on the board
        if (valid <= 0 || checkPlacement(startX, startY, horizontal, word) == 0)
            return new Object[] {0, move};

        return new Object[] {valid, move};
    }
    
    // Check if move connects to existing tiles
    private boolean connectsToTiles(Move move) {
        // Starts from center of board - valid
        if (move.getStartX() == GameConstants.BOARD_WIDTH/2
                && move.getStartY() == GameConstants.BOARD_WIDTH/2)
            return true;
        else {
            int remaining = move.getWordString().length();
            boolean hor = move.isHorizontal();
            Space boardLocal[][] = Session.getSession().getBoardAsSpaces();
            int x = move.getStartX();
            int y = move.getStartY();
            while (remaining > 0) {
                if (boardLocal[x][y].getTile() != null
                        || (x > 0 && boardLocal[x-1][y].getTile() != null)
                        || (x < GameConstants.BOARD_WIDTH-1 
                        && boardLocal[x+1][y].getTile() != null)
                        || (y > 0 && boardLocal[x][y-1].getTile() != null)
                        || (y < GameConstants.BOARD_WIDTH-1 
                        && boardLocal[x][y+1].getTile() != null))
                    return true;
                else if (hor && x < GameConstants.BOARD_WIDTH)
                    x++;
                else if (!hor && y < GameConstants.BOARD_WIDTH)
                    y++;
                // Move extends off board
                else
                    return false;
                remaining--;
            }
            // Move does not connect
            return false;
        }
    }

    /// Check if word is a dictionary word
    private static int isDictionaryWord(String word) {
        try {
            if (Dictionaries.getDictionaries().getEnglishWords().contains(word.toUpperCase())) {
                return 1;
            }
        } catch (Exception e) {

            //LogWarning(e.getMessage() + "\n" + e.getStackTrace());
        }

        return 0;
    }

    /// Check if word is a curse word
    private int isProfane(String word) {
        try {
            if (Dictionaries.getDictionaries().getBadWords().contains(word.toUpperCase())) {
                return -1;
            }
        } catch (Exception e) {
            //LogWarning(e.getMessage() + "\n" + e.getStackTrace());
        }

        return 1;
    }

    /// Check if word is a bonus word
    private int isBonus(String word) {
        try {
            if (Dictionaries.getDictionaries().getSpecialWords().contains(word.toUpperCase())) {
                return 2;
            }
        } catch (Exception e) {
            //LogWarning(e.getMessage() + "\n" + e.getStackTrace());
        }

        return 1;
    }

    /*
        Appends any extra characters on the end of the word that may have
        been overlooked when submitting a word for validation
        -Bill Cook
     */
    private Tile[] getFullWord(int startX, int startY, boolean horizontal,
            String word) {
        String leftChars = "";
        String rightChars = "";
        boolean finished = false;
        Space[][] boardLocal = Session.getSession().getBoardAsSpaces();

        int x = startX;
        int y = startY;
        if (horizontal) {
            while (x > 0 && boardLocal[x - 1][y].getTile() != null) {
                leftChars = boardLocal[x - 1][y].getTile().getLetter()
                        + leftChars;
                x--;
            }
            x = startX + word.length()-1;
            while (x < boardLocal.length
                    && boardLocal[x + 1][y].getTile() != null) {
                rightChars += boardLocal[x + 1][y].getTile().getLetter();
                x++;
            }
            finished = true;
        } else {
            while (y > 0 && boardLocal[x][y - 1].getTile() != null) {
                leftChars = boardLocal[x][y - 1].getTile().getLetter()
                        + leftChars;
                y--;
            }
            y = startY + word.length()-1;
            while (y < boardLocal[0].length
                    && boardLocal[x][y + 1].getTile() != null) {
                rightChars += boardLocal[x][y + 1].getTile().getLetter();
                y++;
            }
            finished = true;
        }
        word = leftChars + word + rightChars;
        Tile[] wordTiles = new Tile[word.length()];
        for (int i = 0; i < word.length(); i++)
            wordTiles[i] = TileGenerator.getTile(word.charAt(i));
        
        return wordTiles;
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

            int x2 = x;
            int y2 = y;
            if (horizontal) {
                while (y2 > 0 && boardLocal[x][y2 - 1].getTile() != null) {
                    leftChars = boardLocal[x][y2 - 1].getTile().getLetter()
                            + leftChars;
                    y2--;
                }
                y2 = y;
                while (y2 < boardLocal[0].length
                        && boardLocal[x][y2 + 1].getTile() != null) {
                    rightChars += boardLocal[x][y2 + 1].getTile().getLetter();
                    y2++;
                }
                finished = true;
            } else {
                while (x2 > 0 && boardLocal[x2 - 1][y].getTile() != null) {
                    leftChars = boardLocal[x2 - 1][y].getTile().getLetter()
                            + leftChars;
                    x2--;
                }
                x2 = x;
                while (x2 < boardLocal.length
                        && boardLocal[x2 + 1][y].getTile() != null) {
                    rightChars += boardLocal[x2 + 1][y].getTile().getLetter();
                    x2++;
                }
                finished = true;
            }
            String word = leftChars + remaining.charAt(0) + rightChars;
            if (isProfane(word) == -1) {
                return -1;
            } else if (word.length() == 1 || isDictionaryWord(word) == 1) {
                return checkPlacement(horizontal ? x + 1 : x, horizontal
                        ? y : y + 1, horizontal, remaining.length() > 0
                        ? remaining.substring(1) : "");
            } else {
                return 0;
            }
        }

    }

}
