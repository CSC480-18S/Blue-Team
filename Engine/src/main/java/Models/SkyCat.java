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
import java.util.Collections;
import java.util.HashSet;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 */
public class SkyCat extends User {

    private static int aiCount = 0;
    private Space[][] boardLocal;
    private ArrayList<Move> moveList;

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
    public Move chooseMove(String difficulty) {
        Move[] possibleMoves = getAllMoves();
        int upperBound = possibleMoves.length;
        int lowerBound = 0;
        if(possibleMoves.length >= 3)
            switch(difficulty){
                case "EASY": upperBound = (possibleMoves.length/3); lowerBound = 0; break;
                case "MEDIUM": upperBound = possibleMoves.length; lowerBound = 0; break;
                case "HARD": upperBound = possibleMoves.length; lowerBound = (possibleMoves.length/3)*2; break;
                default: upperBound = possibleMoves.length; lowerBound = 0; break;
            }
        if(possibleMoves.length == 0){
            return null;
        }
        int indx = ThreadLocalRandom.current().nextInt(lowerBound,upperBound);
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
        HashSet<String> aiWords = dictionary.getaiWords();
        Validator validator = new Validator();
        // Check if first move
        if (boardLocal[boardLocal.length / 2][boardLocal[0].length / 2].getTile()
                == null) {
            for (String w : aiWords) {
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

            Move toReturn[] = new Move[possibleMoves.size()];
            possibleMoves.toArray(toReturn);
            return toReturn;
        }

        // Not the first move
        for (int x = 0;  x < boardLocal.length; x++) {
            for (int y = 0; y < boardLocal[0].length; y++) {
                if (boardLocal[x][y].getTile() != null) {
                    String forwardTiles = getTilesDirection(Direction.FORWARD,
                            x, y);
                    String backwardTiles = getTilesDirection(Direction.BACKWARD,
                            x, y);
                    String upwardTiles = getTilesDirection(Direction.UPWARD,
                            x, y);
                    String downwardTiles = getTilesDirection(Direction.DOWNWARD,
                            x, y);
                    String horizontalString = backwardTiles + boardLocal[x][y].getTile().getLetter() + forwardTiles;
                    String verticalString = upwardTiles + boardLocal[x][y].getTile().getLetter() + downwardTiles;
                    for (String w : aiWords) {
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

                            if(x - remainingStart.length() >= 0 && x + remainingEnd.length() < boardLocal[0].length){
                                boolean inHand = true;
                                Tile[] handCopy = hand.clone();
                                ArrayList<Tile> tilesFromHand = new ArrayList<>();
                                int xIndex = x - remainingStart.length();
                                int trueX = xIndex;
                                int handCount = handCopy.length;

                                for(char letterInWord : w.toCharArray()){
                                    if(xIndex < boardLocal[0].length && boardLocal[xIndex][y].getTile() != null && boardLocal[xIndex][y].getTile().getLetter() == letterInWord)
                                        xIndex++;
                                    else if(xIndex < boardLocal[0].length && boardLocal[xIndex][y].getTile() != null && boardLocal[xIndex][y].getTile().getLetter() != letterInWord){
                                        inHand = false;
                                        break;
                                    }
                                    else if(xIndex < boardLocal[0].length && boardLocal[xIndex][y].getTile() == null && handCount > 0){
                                        boolean found = false;
                                        for(int z = 0; z < handCopy.length; z++){
                                            if(handCopy[z] != null && handCopy[z].getLetter() == letterInWord){
                                                if(handCount == 7) {
                                                    trueX = xIndex;
                                                }
                                                tilesFromHand.add(handCopy[z]);
                                                handCopy[z] = null;
                                                handCount--;
                                                found = true;
                                                xIndex++;
                                                break;
                                            }
                                        }
                                        for(int z = 0; z < handCopy.length; z++){
                                            if(handCopy[z] != null && handCopy[z].getLetter() == '-'){
                                                if(handCount == 7) {
                                                    trueX = xIndex;
                                                }
                                                tilesFromHand.add(new Tile(letterInWord, 0));
                                                handCopy[z] = null;
                                                handCount--;
                                                found = true;
                                                xIndex++;
                                                break;
                                            }
                                        }
                                        if(!found) {
                                            inHand = false;
                                            break;
                                        }
                                    }
                                    else {
                                        inHand = false;
                                        break;
                                    }
                                }
                                if (inHand == true && handCount < hand.length) {
                                    Tile[] word = new Tile[tilesFromHand.size()];
                                    for(int i = 0; i < tilesFromHand.size(); i ++){
                                        word[i] = tilesFromHand.get(i);
                                    }
                                    Tile[]wordForValidation = word.clone();
                                    Move move = new Move(trueX, y, true, word, this);
                                    Move validatorMove = new Move(trueX, y, true, wordForValidation, this);
                                    Object[] result = validator.isValidPlay(validatorMove);
                                    if ((int) result[0] == 1
                                            || (int) result[0] == 2) {
                                        move.setScore(Session.getSession().calculateMovePoints(validatorMove));
                                        possibleMoves.add(move);
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
                            if (index + horizontalString.length() < w.length()) {
                                remainingEnd += w.substring(index
                                        + horizontalString.length());
                            }

                            if(y - remainingStart.length() >= 0 && y + remainingEnd.length() < boardLocal[0].length) {
                                boolean inHand = true;
                                Tile[] handCopy = hand.clone();
                                ArrayList<Tile> tilesFromHand = new ArrayList<>();
                                int yIndex = y - remainingStart.length();
                                int trueY = yIndex;
                                int handCount = handCopy.length;

                                for(char letterInWord : w.toCharArray()){
                                    if(yIndex < boardLocal[0].length && boardLocal[x][yIndex].getTile() != null && boardLocal[x][yIndex].getTile().getLetter() == letterInWord)
                                        yIndex++;
                                    else if(yIndex < boardLocal[0].length && boardLocal[x][yIndex].getTile() != null && boardLocal[x][yIndex].getTile().getLetter() != letterInWord){
                                        inHand = false;
                                        break;
                                    }
                                    else if(yIndex < boardLocal[0].length && boardLocal[x][yIndex].getTile() == null && handCount > 0){
                                        boolean found = false;
                                        for(int z = 0; z < handCopy.length; z++){
                                            if(handCopy[z] != null && handCopy[z].getLetter() == letterInWord){
                                                if(handCount == 7) {
                                                    trueY = yIndex;
                                                }
                                                tilesFromHand.add(handCopy[z]);
                                                handCopy[z] = null;
                                                handCount--;
                                                found = true;
                                                yIndex++;
                                                break;
                                            }
                                        }
                                        for(int z = 0; z < handCopy.length; z++){
                                            if(handCopy[z] != null && handCopy[z].getLetter() == '-'){
                                                if(handCount == 7) {
                                                    trueY = yIndex;
                                                }
                                                tilesFromHand.add(new Tile(letterInWord, 0));
                                                handCopy[z] = null;
                                                handCount--;
                                                found = true;
                                                yIndex++;
                                                break;
                                            }
                                        }
                                        if(!found) {
                                            inHand = false;
                                            break;
                                        }
                                    }
                                    else {
                                        inHand = false;
                                        break;
                                    }
                                }
                                if (inHand == true && handCount < hand.length ) {
                                    Tile[] word = new Tile[tilesFromHand.size()];
                                    for(int i = 0; i < tilesFromHand.size(); i ++) {
                                        word[i] = tilesFromHand.get(i);
                                    }
                                    Tile[] wordForValidation = word.clone();
                                    Move move = new Move(x, trueY, false, word, this);
                                    Move validatorMove = new Move(x, trueY, false, wordForValidation, this);
                                    Object[] result = validator.isValidPlay(validatorMove);
                                    if ((int) result[0] == 1
                                            || (int) result[0] == 2) {
                                        move.setScore(Session.getSession().calculateMovePoints(validatorMove));
                                        possibleMoves.add(move);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        sortMoveListByScore(possibleMoves);
        Move toReturn[] = new Move[possibleMoves.size()];

        possibleMoves.toArray(toReturn);
        return toReturn;
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

    private void sortMoveListByScore(ArrayList<Move> possibleMoves){
        Collections.sort(possibleMoves);
    }

}
