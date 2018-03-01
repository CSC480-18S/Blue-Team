/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.oswebble;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

/**
 *
 * @author mide_
 */
class Field {

    public enum Bonus {
        NONE,
        DOUBLE_LETTER, DOUBLE_WORD,
        TRIPLE_LETTER, TRIPLE_WORD
    }

    Character currentChar;
    public Player player;
    Board board;
    int x;
    int y;

    public Field(int x, int y, Board board) {
        this.x = x;
        this.y = y;
        this.board = board;
    }

    Bonus getBonus() {
        int tmpX = Math.abs(x);
        int tmpY = Math.abs(y);
        if (tmpX == tmpY) {
            switch (tmpX) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
            }
        }

        if (tmpX == 0 && tmpY == 1 || tmpX == 1 && tmpY == 0) {
            return Bonus.TRIPLE_WORD;
        }

        if (tmpX == 1 && tmpY == 3 || tmpX == 3 && tmpY == 1) {
            return Bonus.DOUBLE_LETTER;
        }

        if (tmpX == 3 && tmpY == 3 || tmpX == 3 && tmpY == 3) {
            return Bonus.DOUBLE_LETTER;
        }

        if (tmpX == 2 && tmpY == 4) {
            return Bonus.DOUBLE_WORD;
        }

        if (tmpX == 4 && tmpY == 2) {
            return Bonus.TRIPLE_LETTER;
        }

        if (tmpX == 5 && tmpY == 1) {
            return Bonus.DOUBLE_WORD;
        }

        if (tmpX == 1 && tmpY == 5) {
            return Bonus.TRIPLE_LETTER;
        }

        if (tmpX == 5 && tmpY == 4) {
            return Bonus.DOUBLE_LETTER;
        }

        if (tmpX == 4 && tmpY == 5) {
            return Bonus.TRIPLE_WORD;
        }

        return Bonus.NONE;
    }

    public boolean hasChar() {
        return this.currentChar != null;
    }

    public int getLetterScore(boolean bonus) {
        int score = CharacterInfo.getLetterScore(currentChar);
        if (!bonus) {
            return score;
        }

        switch (getBonus()) {
            case DOUBLE_LETTER:
                return score * 2;
            case TRIPLE_LETTER:
                return score * 2;
            default:
                return score;
        }
    }

    public void draw(ShapeRenderer shapeRenderer) {
        switch (getBonus()) {
            case DOUBLE_WORD:
                shapeRenderer.setColor(74f / 255, 164f / 255, 52f / 255, 1);
                break;
            case TRIPLE_WORD:
                shapeRenderer.setColor(19f / 255, 84f / 255, 19f / 255, 1);
                break;
            case DOUBLE_LETTER:
                shapeRenderer.setColor(247f / 255, 242f / 255, 82f / 255, 1);
                break;
            case TRIPLE_LETTER:
                shapeRenderer.setColor(255f / 255, 215f / 255, 0f / 255, 1);
                break;
            default:
                if (x == 0 && y == 0) {
                    shapeRenderer.setColor(97f / 255, 97f / 255, 97f / 255, 1);
                } else {
                    shapeRenderer.setColor(Color.WHITE);
                }
        }
        Vector2 pos = board.getFieldPos(x, y, Board.PositionType.BOTTOM_LEFT);
        shapeRenderer.rect(pos.x, pos.y, board.fieldSize, board.fieldSize);
    }

    public void drawText(SpriteBatch batch, BitmapFont font) {
        if (hasChar()) {
            Vector2 pos = board.getFieldPos(x, y, Board.PositionType.FIELD_CHAR);
            font.draw(batch, currentChar.toString(), pos.x, pos.y);
            Vector2 scorePos = board.getFieldPos(x, y, Board.PositionType.FIELD_SCORE);
            if (getLetterScore(false) != 10) {
                font.getData().setScale(0.75f);
            } else {
                font.getData().setScale(0.65f, 0.75f);
            }
            font.draw(batch, String.valueOf(getLetterScore(false)), scorePos.x, scorePos.y);
            font.getData().setScale(1);

        }
    }
}
