/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.oswebble;

/**
 *
 * @author mide_
 */
class CharacterInfo {
    public static int getLetterScore (Character currentChar) {
		int score = 1;
		if ("ENSIRTUAD".indexOf(currentChar.charValue()) != -1) {
			score = 1;
		} else if ("HGLO".indexOf(currentChar.charValue()) != -1) {
			score = 2;
		} else if ("MBWZ".indexOf(currentChar.charValue()) != -1) {
			score = 3;
		} else if ("CFKP".indexOf(currentChar.charValue()) != -1) {
			score = 4;
		} else if ("JV".indexOf(currentChar.charValue()) != -1) {
			score = 6;
		} else if ("X".indexOf(currentChar.charValue()) != -1) {
			score = 8;
		} else if ("QY".indexOf(currentChar.charValue()) != -1) {
			score = 10;
		}

		return score;
	}
}