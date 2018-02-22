/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.oswebble;

import java.util.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 *
 * @author mide_
 */
class Oswebble {

    Board board;
    List<Player> players = new ArrayList<Player>();
    Queue<Character> charPool = new LinkedList<Character>();
    int currentPlayer;
    int consecutivePasses = 0;
    boolean gameFinished = false;
    boolean checkNot = false;

    public Oswebble() {
        board = new Board();
        currentPlayer = 0;

        fcp('E', 10);
        fcp('N', 9);
        fcp('S', 7);
        fcp('I', 6);
        fcp('T', 6);
        fcp('U', 6);
        fcp('A', 5);
        fcp('D', 4);

        fcp('H', 4);
        fcp('G', 3);
        fcp('L', 3);
        fcp('O', 3);

        fcp('M', 4);
        fcp('B', 2);
        fcp('W', 2);
        fcp('Z', 2);

        fcp('C', 2);
        fcp('F', 2);
        fcp('K', 2);
        fcp('P', 2);

        fcp('J', 2);
        fcp('V', 2);

        fcp('X', 2);
        fcp('Q', 2);
        fcp('Y', 2);
        shuffle();

    }

    public void fcp(char c, int amount) {
        for (int i = 0; i < amount; i++) {
            charPool.add(new Character(c));
        }
    }

    @SuppressWarnings("unchecked")
    public void shuffle() {
        Collections.shuffle((List<Character>) charPool);
    }

    public void addChar(char[] c) {
        for (char ch : c) {
            charPool.add(ch);
        }
        shuffle();
    }

    public Character popChar() {
        if (charPool.size() < 20) {
            System.out.println("Chars left: " + (charPool.size() - 1));
        }
        return charPool.isEmpty() ? null : charPool.remove();
    }

    public void addPlayer(Player p) {
        p.fillChars(this);
        players.add(p);
    }

    public void nextPlayer() {
        boolean noChars = charPool.isEmpty() && players.get(currentPlayer).chars.size() == 0;
        boolean passed = consecutivePasses >= players.size() * 2;

        if (noChars || passed) {
            endGame();
            return;
        }
        players.get(currentPlayer).end(this);
        if (currentPlayer < players.size() - 1) {
            currentPlayer++;
        } else {
            currentPlayer = 0;
        }
        update();
    }

    public void draw(BitmapFont font, SpriteBatch batch) {
        for (int i = 0; i < players.size(); i++) {
            //draw score von den playern
            players.get(i).draw(batch, board);
        }
    }

    public void pass() {
        consecutivePasses++;
        players.get(currentPlayer).pass();
    }

    public void checkWord() {
        players.get(currentPlayer).checkWord(checkNot);
        checkNot = false;
    }

    public void replaceChar(CharActor[] c) {
        if (charPool.size() >= 7) {
            players.get(currentPlayer).replaceChar(this, c);
        } else {
            Gdx.app.log("Oswebble", "Remaining letters < 7 | Replacing not allowed");
        }

    }

    public void endGame() {
        gameFinished = true;
        for (Player p : players) {
            p.isActive = false;
        }
    }

    public void update() {
        for (int i = 0; i < players.size(); i++) {
            players.get(i).setActive(i == currentPlayer);
        }
    }

}
