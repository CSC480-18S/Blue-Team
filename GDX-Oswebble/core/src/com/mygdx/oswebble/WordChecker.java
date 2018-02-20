/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.oswebble;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

/**
 *
 * @author mide_
 */
public class WordChecker {

    static BufferedReader file;

    public static boolean check(String word) {
        boolean found = false;
        try {
            file = new BufferedReader(Gdx.files.internal("words.txt").reader());
            String line;

            while ((line = file.readLine()) != null) {
                if (word.equalsIgnoreCase(line)) {
                    found = true;
                    break;
                }
            }
            file.close();

            if (found) {
                Gdx.app.log("Word", "Word found");
            } else {
                Gdx.app.log("Word", "Word not found");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return true; // FIXME
        } catch (IOException e) {
            e.printStackTrace();
        }

        return found;

    }

}
