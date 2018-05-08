/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 *
 * @author Bill Cook
 */
public interface GameConstants {
    
    final int BOARD_WIDTH = 11;
    final int NUMBER_OF_VARIATIONS = 1;
    /*Board Variation follows the logic that inside each innermost list the first int corresponds to multiplier
    *1 is 2l bonus, 2 is 3l bonus, 3 is 2w bonus and 4 is 3w
    */
    final int[][][] BOARD_VARIATIONS =
            {{{0,0,4,0,0,2,0,0,4,0,0},{0,1,0,0,3,0,3,0,0,1,0},{4,0,0,1,0,0,0,1,0,0,4},{0,0,1,0,0,0,0,0,1,0,0}
             ,{0,3,0,0,0,1,0,0,0,3,0},{2,0,0,0,1,0,1,0,0,0,2},{0,3,0,0,0,1,0,0,0,3,0},{0,0,1,0,0,0,0,0,1,0,0}
             ,{4,0,0,1,0,0,0,1,0,0,4},{0,1,0,0,3,0,3,0,0,1,0},{0,0,4,0,0,2,0,0,4,0,0}}
            };

    String ALPHABET =
            "abcdefghijklmnopqrstuvwxyz";
    
}
