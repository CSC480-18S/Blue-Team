/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Components.Dictionaries;
import Components.Validator;
import Session.Session;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author wcook
 */
public class SkyCat extends User {

    private static int aiCount = 0;
    private Space[][] boardLocal;

    public SkyCat(Session session) {
        super("SkyCat" + ++aiCount);
    }

    @Override
    public int updateScore(Move move) {
        // Calculate Score
        int points = Session.getSession().calculateMovePoints(move);
        score += points;

        // Update score via SQL?
        return score;
    }

    @Override
    public String getType() {
        return "AI";
    }

    /*
        Chooses a move to play randomly
        @return the coshosen Move
     */
    public Move chooseMove() {
        Move[] possibleMoves = getAllMoves();
        if(possibleMoves.length == 0){
            return null;
        }
        int indx = ThreadLocalRandom.current().nextInt(0, possibleMoves.length);
        return possibleMoves[indx];
    }

    private enum Direction {
        FORWARD, BACKWARD, UPWARD, DOWNWARD
    }

    /*
        Method to generate all possible moves
        @return an array containing all possible moves
     */
    private Move[] getAllMoves() {
        ArrayList<Move> possibleMoves = new ArrayList();
        boardLocal = Session.getSession().getBoardAsSpaces();
        Tile[] hand = this.getHand();
        Dictionaries dictionary = Dictionaries.getDictionaries();
        HashSet<String> engWords = dictionary.getEnglishWords();
        HashSet<String> specWords = dictionary.getSpecialWords();
        Validator validator = new Validator();
        // Check if first move
        if (boardLocal[boardLocal.length / 2][boardLocal[0].length / 2].getTile()
                == null) {
            for (String w : engWords) {
                boolean inHand = false;
                Tile[] handCopy = hand.clone();
                if(w.length() <= handCopy.length)
                for (char c : w.toCharArray()) {
                    inHand = false;
                    for (int i = 0; i < handCopy.length; i++) {
                        if (handCopy[i] != null
                                && handCopy[i].getLetter() == c) {
                            inHand = true;
                            handCopy[i] = null;
                            break;
                        }
                    }
                    if (inHand == false) {
                        break;
                    }
                }
                if (inHand == true) {
                    Tile[] wordTiles = stringToTiles(w);
                    for (int i = 0; i < w.length(); i++) {
                        if ((boardLocal.length / 2) - i <= 0) {
                            break;
                        }
                        Move move = new Move((boardLocal.length / 2) - i, boardLocal[0].length / 2, true, wordTiles, this);
                        Object[] result = validator.isValidPlay(move);
                        if ((int) result[0] == 1
                                || (int) result[0] == 2) {
                            possibleMoves.add((Move) result[1]);
                        }
                    }
                }
            }
            for (String w : specWords) {
                boolean inHand = false;
                Tile[] handCopy = hand.clone();
                if(w.length() <= hand.length)
                for (char c : w.toCharArray()) {
                    inHand = false;
                    for (int i = 0; i < handCopy.length; i++) {
                        if (handCopy[i] != null
                                && handCopy[i].getLetter() == c) {
                            inHand = true;
                            handCopy[i] = null;
                            break;
                        }
                    }
                    if (inHand == false) {
                        break;
                    }


                }
                if (inHand == true) {
                    Tile[] wordTiles = stringToTiles(w);
                    for (int i = 0; i < w.length(); i++) {
                        if ((boardLocal.length / 2) - i <= 0) {
                            break;
                        }
                        Move move = new Move(boardLocal.length / 2 - i, (boardLocal[0].length / 2), true, wordTiles, this);
                        Object[] result = validator.isValidPlay(move);
                        if ((int) result[0] == 1
                                || (int) result[0] == 2) {
                            possibleMoves.add((Move) result[1]);
                        }
                    }
                }
            }
            Move toReturn[] = new Move[possibleMoves.size()];
            possibleMoves.toArray(toReturn);
            return toReturn;
        }

        // Not the first move
        for (int x = 0;  x < boardLocal.length; x++) {
            for (int y = 0; y < boardLocal[0].length; y++) {
                if (boardLocal[x][y].getTile() != null) {
                    int clearForward = countClearSpaces(Direction.FORWARD,
                            x, y);
                    int clearBackward = countClearSpaces(Direction.BACKWARD,
                            x, y);
                    int clearUpward = countClearSpaces(Direction.UPWARD,
                            x, y);
                    int clearDownward = countClearSpaces(Direction.DOWNWARD,
                            x, y);
                    String forwardTiles = getTilesDirection(Direction.FORWARD,
                            x, y);
                    String backwardTiles = getTilesDirection(Direction.BACKWARD,
                            x, y);
                    String upwardTiles = getTilesDirection(Direction.UPWARD,
                            x, y);
                    String downwardTiles = getTilesDirection(Direction.DOWNWARD,
                            x, y);
                    String horizontalString = backwardTiles
                            + boardLocal[x][y].getTile().getLetter()
                            + forwardTiles;
                    String verticalString = upwardTiles
                            + boardLocal[x][y].getTile().getLetter()
                            + downwardTiles;
                    for (String w : engWords) {
                        if (w.contains(horizontalString)) {
                            int index = w.indexOf(horizontalString);
                            String remainingStart = "";
                            String remainingEnd = "";
                            if (index > 0) {
                                remainingStart += w.substring(0, index);
                            }
                            if (index + horizontalString.length() < w.length()) {
                                remainingEnd += w.substring(index
                                        + horizontalString.length());
                            }
                            if (remainingStart.length() <= clearBackward
                                    && remainingEnd.length() <= clearForward && (remainingStart.length() + remainingEnd.length()) <= hand.length) {
                                boolean inHand = false;
                                Tile[] handCopy = hand.clone();
                                int handCount = handCopy.length;
                                int xIndex = x - backwardTiles.length() - remainingStart.length();
                                if (remainingStart.length() <= handCount && xIndex >= 0)
                                    for (char c : remainingStart.toCharArray()) {
                                        inHand = false;
                                        for (int i = 0; i < handCopy.length; i++) {
                                            if (handCopy[i] != null
                                                    && handCopy[i].getLetter() == c
                                                    && boardLocal[xIndex][y].getTile() == null) {
                                                inHand = true;
                                                handCopy[i] = null;
                                                handCount--;
                                                break;
                                            }
                                        }
                                        if (inHand == false) {
                                            break;
                                        }
                                        xIndex++;
                                    }
                                xIndex = x + forwardTiles.length();
                                if (inHand == true){
                                    for (char c : remainingEnd.toCharArray()) {
                                        inHand = false;
                                        for (int i = 0; i < handCopy.length; i++) {
                                            if (handCopy[i] != null
                                                    && handCopy[i].getLetter() == c
                                                    && boardLocal[xIndex][y].getTile() == null) {
                                                inHand = true;
                                                handCopy[i] = null;
                                                handCount--;
                                                break;
                                            }
                                        }
                                        if (inHand == false) {
                                            break;
                                        }
                                        if(xIndex == boardLocal[0].length - 1)
                                            break;
                                        xIndex++;
                                    }
                                }
                                if (inHand == true) {
                                    Tile[] wordTiles = stringToTiles(w);
                                    Move move = new Move(x - remainingStart.length(), y, true, wordTiles, this);
                                    Object[] result = validator.isValidPlay(move);
                                    if ((int) result[0] == 1
                                            || (int) result[0] == 2) {
                                        possibleMoves.add((Move) result[1]);
                                        }
                                }
                            }
                        }
                        if (w.contains(verticalString)) {
                            int index = w.indexOf(verticalString);
                            String remainingStart = "";
                            String remainingEnd = "";
                            if (index > 0) {
                                remainingStart += w.substring(0, index);
                            }
                            if (index + verticalString.length() < w.length()) {
                                remainingEnd += w.substring(index
                                        + verticalString.length());
                            }
                            if (remainingStart.length() <= clearUpward
                                    && remainingEnd.length() <= clearDownward && (remainingStart.length() + remainingEnd.length()) <= hand.length) {
                                boolean inHand = false;
                                Tile[] handCopy = hand.clone();
                                int handCount = handCopy.length;
                                int yIndex = y - upwardTiles.length() - remainingStart.length();
                                if(remainingStart.length() <= handCount && yIndex >= 0)
                                    for (char c : remainingStart.toCharArray()) {
                                        inHand = false;
                                        for (int i = 0; i < handCopy.length; i++) {
                                            if (handCopy[i] != null
                                                    && handCopy[i].getLetter() == c
                                                    && boardLocal[x][yIndex].getTile() == null) {
                                                inHand = true;
                                                handCopy[i] = null;
                                                handCount--;
                                                break;
                                            }
                                        }
                                        if (inHand == false) {
                                            break;
                                        }
                                        yIndex++;
                                    }
                                yIndex = y + downwardTiles.length();
                                if(inHand == true)
                                    for(char c : remainingEnd.toCharArray()){
                                        inHand = false;
                                        for(int i = 0; i < handCopy.length; i++){
                                            if(handCopy[i] != null
                                                    && handCopy[i].getLetter() == c ){
                                                inHand = true;
                                                handCopy[i] = null;
                                                handCount--;
                                                break;
                                            }
                                        }
                                        if(inHand == false){
                                            break;
                                        }
                                        if(yIndex == boardLocal[0].length - 1)
                                            break;
                                        yIndex++;
                                    }
                                if (inHand == true) {
                                    Tile[] wordTiles = stringToTiles(w);
                                    Move move = new Move(x, y - remainingStart.length(), false, wordTiles, this);
                                    Object[] result = validator.isValidPlay(move);
                                    if ((int) result[0] == 1
                                            || (int) result[0] == 2) {
                                        possibleMoves.add((Move) result[1]);
                                        }
                                }
                            }
                        }
                    }
                    for (String w : specWords) {
                        if (w.contains(horizontalString)) {
                            int index = w.indexOf(horizontalString);
                            String remainingStart = "";
                            String remainingEnd = "";
                            if (index > 0) {
                                remainingStart += w.substring(0, index);
                            }
                            if (index + horizontalString.length() < w.length()) {
                                remainingEnd += w.substring(index
                                        + horizontalString.length());
                            }
                            if (remainingStart.length() <= clearBackward
                                    && remainingEnd.length() <= clearForward && (remainingStart.length() + remainingEnd.length()) <= hand.length) {
                                boolean inHand = false;
                                Tile[] handCopy = hand.clone();
                                int handCount = handCopy.length;
                                int xIndex = x - backwardTiles.length() - remainingStart.length();
                                if(remainingStart.length() <= handCount && xIndex >= 0)
                                    for (char c : remainingStart.toCharArray()) {
                                        inHand = false;
                                        for (int i = 0; i < handCopy.length; i++) {
                                            if (handCopy[i] != null
                                                    && handCopy[i].getLetter() == c
                                                    && boardLocal[xIndex][y].getTile() == null) {
                                                inHand = true;
                                                handCopy[i] = null;
                                                handCount--;
                                                break;
                                            }
                                        }
                                        if (inHand == false) {
                                            break;
                                        }
                                        xIndex++;
                                    }
                                xIndex = x + forwardTiles.length() + 1;
                                if(inHand == true && remainingEnd.length() <= handCount)
                                    for(char c : remainingEnd.toCharArray()){
                                        inHand = false;
                                        for(int i = 0; i < handCopy.length; i++){
                                            if(handCopy[i] != null
                                                    && handCopy[i].getLetter() == c){
                                                inHand = true;
                                                handCopy[i] = null;
                                                handCount--;
                                                break;
                                            }
                                        }
                                        if(inHand == false){
                                            break;
                                        }
                                        if(xIndex == boardLocal[0].length - 1)
                                            break;
                                        xIndex++;
                                    }
                                if (inHand == true) {
                                    Tile[] wordTiles = stringToTiles(w);
                                    Move move = new Move(x - remainingStart.length(), y, true, wordTiles, this);
                                    Object[] result = validator.isValidPlay(move);
                                    if ((int) result[0] == 1
                                            || (int) result[0] == 2) {
                                        possibleMoves.add((Move) result[1]);
                                    }
                                }
                            }
                        }
                        if (w.contains(verticalString)) {
                            int index = w.indexOf(verticalString);
                            String remainingStart = "";
                            String remainingEnd = "";
                            if (index > 0) {
                                remainingStart += w.substring(0, index);
                            }
                            if (index + verticalString.length() < w.length()) {
                                remainingEnd += w.substring(index
                                        + verticalString.length());
                            }
                            if (remainingStart.length() <= clearUpward
                                    && remainingEnd.length() <= clearDownward && (remainingStart.length() + remainingEnd.length()) <= hand.length) {
                                boolean inHand = false;
                                Tile[] handCopy = hand.clone();
                                int handCount = handCopy.length;
                                int yIndex = y - upwardTiles.length() - remainingStart.length();
                                if(remainingStart.length() <= handCount && yIndex >= 0)
                                    for (char c : remainingStart.toCharArray()) {
                                        inHand = false;
                                        for (int i = 0; i < handCopy.length; i++) {
                                            if (handCopy[i] != null
                                                    && handCopy[i].getLetter() == c
                                                    && boardLocal[x][yIndex].getTile() == null) {
                                                inHand = true;
                                                handCopy[i] = null;
                                                handCount--;
                                                break;
                                            }
                                        }
                                        if (inHand == false) {
                                            break;
                                        }
                                        yIndex++;
                                    }
                                yIndex = y + downwardTiles.length() + 1;
                                if(inHand == true && remainingEnd.length() <= handCount)
                                    for(char c : remainingEnd.toCharArray()){
                                        inHand = false;
                                        for(int i = 0; i < handCopy.length; i++){
                                            if(handCopy[i] != null
                                                    && handCopy[i].getLetter() == c){
                                                inHand = true;
                                                handCopy[i] = null;
                                                handCount--;
                                                break;
                                            }
                                        }
                                        if(inHand == false){
                                            break;
                                        }
                                        if(yIndex == boardLocal[0].length - 1)
                                            break;
                                        yIndex++;
                                    }
                                if (inHand == true) {
                                    Tile[] wordTiles = stringToTiles(w);
                                    Move move = new Move(x, y - remainingStart.length(), false, wordTiles, this);
                                    Object[] result = validator.isValidPlay(move);
                                    if ((int) result[0] == 1
                                            || (int) result[0] == 2) {
                                        possibleMoves.add((Move) result[1]);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        Move toReturn[] = new Move[possibleMoves.size()];

        possibleMoves.toArray(toReturn);
        return toReturn;
    }

    /*
        Method to count clear spaces
        @return number of clear spaces
     */
    private int countClearSpaces(Direction d, int x, int y) {
        int count = 0;
        switch (d) {
            case FORWARD:
                x++;
                break;
            case BACKWARD:
                x--;
                break;
            case UPWARD:
                y++;
                break;
            case DOWNWARD:
                y--;
                break;
        }
        while (x < boardLocal.length && x >= 0
                && y < boardLocal[0].length && y >= 0) {
            if (boardLocal[x][y].getTile() != null) {
                break;
            } else {
                count++;
            }
            switch (d) {
                case FORWARD:
                    x++;
                    break;
                case BACKWARD:
                    x--;
                    break;
                case UPWARD:
                    y++;
                    break;
                case DOWNWARD:
                    y--;
                    break;
            }
        }

        return count;
    }

    /*
        Method to get String created by tiles in specified direction
        @return String created by tiles in specified direction
     */
    private String getTilesDirection(Direction d, int x, int y) {
        String word = "";
        switch (d) {
            case FORWARD:
                x++;
                break;
            case BACKWARD:
                x--;
                break;
            case UPWARD:
                y++;
                break;
            case DOWNWARD:
                y--;
                break;
        }
        while (x < boardLocal.length && x >= 0
                && y < boardLocal[0].length && y >= 0) {
            if (boardLocal[x][y].getTile() == null) {
                break;
            } else {
                word += boardLocal[x][y].getTile().getLetter();
            }
            switch (d) {
                case FORWARD:
                    x++;
                    break;
                case BACKWARD:
                    x--;
                    break;
                case UPWARD:
                    y++;
                    break;
                case DOWNWARD:
                    y--;
                    break;
            }
        }

        return word;
    }

    /*
        Converts string to Tiles
        @return and Array of Tiles
     */
    private Tile[] stringToTiles(String word) {
        Tile[] toReturn = new Tile[word.length()];
        for (int i = 0; i < word.length(); i++) {
            toReturn[i] = TileGenerator.getInstance().getTile(word.charAt(i));
        }
        return toReturn;
    }

}
