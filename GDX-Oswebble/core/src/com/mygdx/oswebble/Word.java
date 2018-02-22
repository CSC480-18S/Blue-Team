/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.oswebble;

import java.util.ArrayList;

/**
 *
 * @author mide_
 */
public class Word {

    enum Direction {
        UP(0, 1), RIGHT(1, 0), DOWN(0, -1), LEFT(-1, 0);
        final int x;
        final int y;
        final Vector2X vec;

        Direction(int x, int y) {
            this.x = x;
            this.y = y;
            this.vec = new Vector2X(x, y);
        }

        static Direction fromXY(int x, int y) {
            Direction[] directions = {UP, RIGHT, DOWN, LEFT};
            for (Direction d : directions) {
                if (d.x == x && d.y == y) {
                    return d;
                }
            }
            return null;
        }

        static Direction fromVec(Vector2X v) {
            return Direction.fromXY(v.x, v.y);
        }

        Vector2X movePosTimes(Vector2X pos, int times) {
            return new Vector2X(x * times, y * times).add(pos);
        }
    }

    String word;
    Vector2X start;
    Direction direction;
    private Board board;
    ArrayList<Integer> alreadyExistingLetters = new ArrayList<Integer>();

    Word(Field field, Board board) {
        start = new Vector2X(field.x, field.y);
        word = field.currentChar.toString();
        this.board = board;

        Direction[] dc = {Direction.DOWN, Direction.RIGHT};
        for (Direction check : dc) {
            Field test = board.getField(start.add(check.vec).x, start.add(check.vec).y);
            if (test != null && test.hasChar()) {
                this.direction = check;
                checkNextOnBoard();
                break;
            }
        }
    }

    public Field[] getFields() {
        Field[] fields = new Field[word.length()];
        for (int i = 0; i < word.length(); i++) {
            Vector2X pos = start.add(direction.vec.mul(i));
            Field f = new Field(pos.x, pos.y, null);
            f.currentChar = new Character(word.charAt(i));
            fields[i] = f;
        }
        return fields;
    }

    @SuppressWarnings("incomplete-switch")
    public int getScore() {
        Field[] fields = getFields();
        int score = 0;
        for (Field field : fields) {
            score += field.getLetterScore(true);
        }
        for (Field field : fields) {
            switch (field.getBonus()) {
                case DOUBLE_WORD:
                    score *= 2;
                case TRIPLE_WORD:
                    score *= 3;
            }
        }
        return score;
    }

    public void checkNextOnBoard() {
        while (true) {
            Vector2X next = start.add(direction.vec.mul(word.length()));
            Field f = board.getField(next.x, next.y);
            if (f == null || !f.hasChar()) {
                return;
            }
            word += (char) f.currentChar;
            alreadyExistingLetters.add(word.length() - 1);
        }
    }

    public boolean add(Field field, char character) {
        if (direction == null) {
            direction = Direction.fromXY(field.x - start.x, field.y - start.y);
            if (direction == null) {
                return false;
            }
        } else {
            Vector2X next = start.add(direction.vec.mul(word.length()));
            if (field.x != next.x || field.y != next.y) {
                return false;
            }
        }

        word += character;
        checkNextOnBoard();
        return true;
    }

    public CharActor[] clearWord(Player player) {
//		Field snapField = board.getSnapField(new Vector2(getX(), getY()));
        int x = start.x + board.size / 2;
        int y = start.y + board.size / 2;
        for (int i = 0; i < word.length(); i++) {
            if (!alreadyExistingLetters.contains(i)) {
                board.fields[x][y].currentChar = null;
                board.fields[x][y].player = null;
            }

            if (direction != null) {
                switch (direction) {
                    case DOWN:
                        y--;
                        break;
                    case UP:
                        y++;
                        break;
                    case LEFT:
                        x--;
                        break;
                    case RIGHT:
                        x++;
                        break;
                    default:
                        break;
                }
            } else {
                break;
            }

        }

        char[] c = word.toCharArray();
        ArrayList<CharActor> ca = new ArrayList<CharActor>();
        for (int i = 0; i < c.length; i++) {
            if (!alreadyExistingLetters.contains(i)) {
                ca.add(new CharActor(c[i], player));
            }
        }
        return ca.toArray(new CharActor[0]);
    }
}
