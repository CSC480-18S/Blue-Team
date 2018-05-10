/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;
import Models.*;
import java.util.concurrent.ThreadLocalRandom;

import static java.util.concurrent.ThreadLocalRandom.*;

/**
 *  Contains the state of the Game board
 * @author Bill Cook
 */
public class Board implements GameConstants {
    
    private Space[][] board;
    
    /*
        The constructor initializes the board state and establishes which 
        tiles have bonus multipliers.
    */
    public Board() {
        board = new Space[BOARD_WIDTH][BOARD_WIDTH];
        // Select and load a random board layout
        int variation =
                current().nextInt(NUMBER_OF_VARIATIONS);
        int[][] layout = BOARD_VARIATIONS[variation];
        // Initialize board
        int multIndex = 0;
        for (int r = 0; r < BOARD_WIDTH; r++) {
            System.out.print("\nRow: " + r + " ");
            for (int c = 0; c < BOARD_WIDTH; c++) {
                Multiplier mult = Multiplier.NONE;
                // If Space in layout has a multiplier THIS IF STATEMENT LOGIC IS NOT CORRECT
//                if (multIndex < layout.length && r == layout[multIndex][c]
//                        && c == layout[r][multIndex]) {
//                    // Generate Random Multiplier
//                    int multType = current().nextInt(5);
//                    switch (multType) {
//                        case 0: break;
//                        case 1: mult = Multiplier.DOUBLE_LETTER; break;
//                        case 2: mult = Multiplier.DOUBLE_WORD; break;
//                        case 3: mult = Multiplier.TRIPLE_LETTER; break;
//                        case 4: mult = Multiplier.TRIPLE_WORD; break;
//                    }
//                    multIndex++;
//                }
                int mulType = layout[c][r];
                System.out.print(mulType + " ");
                switch(mulType){
                    case 0: break;
                    case 1: mult = Multiplier.DOUBLE_LETTER; break;
                    case 2: mult = Multiplier.TRIPLE_LETTER; break;
                    case 3: mult = Multiplier.DOUBLE_WORD; break;
                    case 4: mult = Multiplier.TRIPLE_WORD; break;
                }
                board[c][r] = new Space(mult);
            }
        }
    }

    /*
        This method returns the current board.
    */
    public synchronized Space[][] getBoard() {
        return board;
    }

    public synchronized boolean placeWord(int startX, int startY, boolean horizontal, String word){

        for (char c : word.toCharArray()){
            board[startX][startY].setTile(new Tile(c, 1));
            if(horizontal){
                startX++;
            } else {
                startY++;}
        }

        return true;
    }
}
