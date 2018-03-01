/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.oswebble;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
/**
 *
 * @author mide_
 */
public class Player {
	ArrayList<Word> words = new ArrayList<Word>();
	List<CharActor> chars = new ArrayList<CharActor>();
	List<CharActor> currentTrashChars = new ArrayList<CharActor>();
	int score;
	boolean pass;
	boolean isActive;
	Board board;
	Stage stage;
	Word currentWord;
	private static int players = 0; //temp
	private String playerName;
	private static BitmapFont font;
	public enum Position {
		LEFT, RIGHT
	}
	Position position;
	
	public Player(Position position, Board board, Stage stage){
		this.board = board;
		this.stage = stage;
		this.position = position;
		score = 0;
		pass = false;
		if (font == null) {
			font = new BitmapFont();
			font.setColor(0, 0, 0, 1);
		}
		players++;
		playerName = "Player " + players;  //Player Name
		
	}
	
	public void start() {
		currentTrashChars = new ArrayList<CharActor>();
	}
	
	public void draw(SpriteBatch batch, Board board) {
		Vector2 v = board.getPlayerScorePos(position);
		String text = playerName + ": " + String.valueOf(score);
		if (isActive) {
			text += " (Current Player)"; //Player at the moment
			if (position == Position.RIGHT)
				v.x -= 65;
		}
		font.draw(batch, text, v.x, v.y);
	}
	
	public void pass() {
		pass = true;
	}
	
	public void end(Oswebble sc) {
		if (pass = false) {
			sc.consecutivePasses = 0;
		}
		currentWord = null;
		fillChars(sc);
	}
	
	public void setActive(boolean active) {
		isActive = active;
		for (CharActor charActor : chars) {
			charActor.setDraggable(active);
		}
		if (active) {
			start();
		}
	}
	
	public void checkWord(boolean checkNot) {
		String word = currentWord.word;
		if (WordChecker.check(word) || checkNot) {
			score += currentWord.getScore();
		} else {
			pass();
			CharActor[] ca = currentWord.clearWord(this);
			for (CharActor tCA : ca) {
				chars.add(tCA);
				this.stage.addActor(tCA);
			}
			updateChars();
			currentWord = null;
			
		}
	}

	public void fillChars(Oswebble game) {
		if (chars.size() < 7) {
			for(int i = chars.size(); i < 7; i++){
				CharActor ca = new CharActor(game.popChar(), this);
				chars.add(ca);
				this.stage.addActor(ca);
			}
			updateChars();
		}
		pass = false;
	}

	public void replaceChar(Oswebble sc, CharActor[] c) {
		for (CharActor ch : c) {		score = 0;
		pass = false;

			chars.remove(ch);
		}
		fillChars(sc);
		updateChars();
	}
	
	public void updateChars() {
		for (int i = 0; i < chars.size(); i++) {
			chars.get(i).index = i;	
			chars.get(i).resetPos();
			for (CharActor charActor : chars) {
				charActor.setDraggable(isActive);
			}
		}
	}

	public void delChar(CharActor actor) {
		chars.remove(actor);
		actor.remove();
		updateChars();
	}
}