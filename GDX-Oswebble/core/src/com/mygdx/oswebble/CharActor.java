/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.oswebble;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
/**
 *
 * @author mide_
 */

public class CharActor extends Actor {
    TextureRegion region;
    char c;
    ShapeRenderer renderer = new ShapeRenderer();
    Board board;
    Player player;
    int index;
    boolean isDraggable;
    static BitmapFont font;

    public CharActor (final char c, final Player player) {
    	if (font == null) {
    		font = new BitmapFont();
    	}
        this.board = player.board;
        this.player = player;
        this.c = c;
        region = new TextureRegion();
        final CharActor actor = this;
        this.addListener(new DragListener() {
            public void drag(InputEvent event, float x, float y, int pointer) {
            	actor.moveBy(x - actor.getWidth() / 2, y - actor.getHeight() / 2);
            	Vector2 newpos = new Vector2(getX(), getY());
            	Vector2 snapPoint;
            	if (player.currentWord != null) {
            		snapPoint = board.getWordSnap(newpos, player.currentWord);
            	} else {
            		snapPoint = board.getSnapPoint(newpos);
            	}
            	
            	if (snapPoint != null) {
            		newpos = snapPoint;
            		actor.setPosition(newpos.x, newpos.y);
            	}
            }
            
            public void dragStart(InputEvent event, float x, float y, int pointer)  {
            	if (!isDraggable) {
            		this.cancel();
            	}
            	//actor.debug();
            }
            
            public void dragStop(InputEvent event, float x, float y, int pointer)  {
            	actor.place();
            }
        });
        this.resetPos();
    }
    
    public void resetPos() {
    	Vector2 pos = board.getSidebarPos(index, player.chars.size(), player.position);
    	setPosition(pos.x, pos.y);
    }
    
    public void place() {
    	Field snapField = board.getSnapField(new Vector2(getX(), getY()));
    	if (snapField != null) {
    		if (player.currentWord == null) {
        		snapField.currentChar = c;
        		player.currentWord = new Word(snapField, board);	
    		} else {
    			if (!player.currentWord.add(snapField, c)) {
    				resetPos();
    				return;
    			}
    		}
    		snapField.currentChar = c;
    		snapField.player = player;
    		board.fields[snapField.x+board.size/2][snapField.y+board.size/2] = snapField;
        	player.delChar(this);
    	} else {
    		if (board.isInChangingArea(new Vector2(getX(), getY()))) {
    			player.currentTrashChars.add(this);
    			player.delChar(this);
    		}
    	}
    	resetPos();
    }

    @Override
    public void draw (Batch batch, float parentAlpha) {
    	if (getWidth() != board.fieldSize) {
            setWidth(board.fieldSize);
            setHeight(board.fieldSize);
            setBounds(0, 0, getWidth(), getHeight());
            resetPos();
    	}
        batch.end();
        renderer.setProjectionMatrix(batch.getProjectionMatrix());
        renderer.setTransformMatrix(batch.getTransformMatrix());
        renderer.translate(getX(), getY(), 0);

        renderer.begin(ShapeType.Filled);
        if (isDraggable) {
        	renderer.setColor(74f / 255, 164f / 255, 52f / 255, 1);
        } else {
            renderer.setColor(255f / 255, 215f / 255, 0f / 255, 1);
        }
        renderer.rect(0, 0, getWidth(), getHeight());
        renderer.end();
    	
        batch.begin();
        
       	//Vector2 pos = board.getSidebarPos((int)getX(), (int)getY(), player.position);
        Vector2 pos = new Vector2(getX(), getY());
       	pos = board.getSidebarTextPos(pos, Board.PositionType.FIELD_CHAR);
       	font.draw(batch, String.valueOf(c),pos.x, pos.y);
        Vector2 pos2 = new Vector2(getX(), getY());
       	Vector2 scorePos = board.getSidebarTextPos(pos2, Board.PositionType.FIELD_SCORE);
       	if (CharacterInfo.getLetterScore(new Character(c)) != 10){
       		font.getData().setScale(0.75f);
       	} else {
       		font.getData().setScale(0.65f, 0.75f);
       	}
       	
       	font.draw(batch, String.valueOf(CharacterInfo.getLetterScore(new Character(c))), scorePos.x, scorePos.y);
       	font.getData().setScale(1);  	
    }

	public void setDraggable(boolean draggable) {
		isDraggable = draggable;
	}
}