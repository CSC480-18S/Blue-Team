/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.oswebble;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

import com.mygdx.oswebble.Player.Position;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author mide_
 */
public class Board {

    int size = 11;
    Field[][] fields;
    Word[] words;
    Viewport viewport;

    int fieldSize = 22;
    int fieldGap = 2;

    public Board() {
        fields = new Field[size][size];
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                fields[x][y] = new Field(x - size / 2, y - size / 2, this);
            }
        }
    }

    public void drawRand(SpriteBatch batch, BitmapFont font) {
        for (int i = 1; i < 12; i++) {
            int a = 0;
            if (i > 7) {
                a = 3;
            }
            font.setColor(0, 0, 0, 1);
            Vector2 pos = getFieldPos(-6, -i + 6, PositionType.FIELD_CHAR);
            //font.draw(batch, String.valueOf(i), 17 - a, 56+32*(15-i));
            font.draw(batch, String.valueOf(i), pos.x - a, pos.y);
            pos = getFieldPos(6, 6 - i, PositionType.FIELD_CHAR);
            font.draw(batch, String.valueOf(i), pos.x - a, pos.y);

            pos = getFieldPos(i - 6, 6, PositionType.FIELD_CHAR);
            font.draw(batch, String.valueOf((char) (64 + i)), pos.x, pos.y);
            pos = getFieldPos(i - 6, -6, PositionType.FIELD_CHAR);
            font.draw(batch, String.valueOf((char) (64 + i)), pos.x, pos.y);
        }
    }

    public Field getField(int x, int y) {
        if (x < -size / 2 || y < -size / 2 || x > size / 2 || y > size / 2) {
            return null;
        }
        return fields[x + size / 2][y + size / 2];
    }

    public enum PositionType {
        TOP_LEFT, BOTTOM_LEFT,
        BOTTOM_RIGHT, MIDDLE,
        FIELD_CHAR, FIELD_SCORE
    }

    // Mit x und y von -size/2 bis size/2 (-7 und 7)
    public Vector2 getFieldPos(int x, int y, PositionType type) {
        int width = viewport.getScreenWidth();
        int height = viewport.getScreenHeight();
        int boardSize = (int) (Math.min(width, height) * 0.8);
        fieldSize = (boardSize / size) - fieldGap;
        switch (type) {
            case TOP_LEFT:
                y++;
                break;
            case BOTTOM_LEFT:
                break;
            case BOTTOM_RIGHT:
                x++;
                break;
            default:
                break;
        }
        int xP = (width - boardSize) / 2 + (x + size / 2) * (fieldSize + fieldGap);
        int yP = (height - boardSize) / 2 + (y + size / 2) * (fieldSize + fieldGap);
        if (type == PositionType.MIDDLE) {
            xP += fieldSize / 2;
            yP += fieldSize / 2;
        } else if (type == PositionType.FIELD_SCORE) {
            xP += fieldSize * 2 / 3;
            yP += fieldSize * 0.4f;
        } else if (type == PositionType.FIELD_CHAR) {
            xP += fieldSize / 3;
            yP += fieldSize * 0.7f;
        }
        return new Vector2(xP, yP);
    }

    public Vector2 getSidebarTextPos(Vector2 pos, PositionType type) {
        int xP = (int) pos.x;
        int yP = (int) pos.y;
        if (type == PositionType.MIDDLE) {
            xP += fieldSize / 2;
            yP += fieldSize / 2;
        } else if (type == PositionType.FIELD_SCORE) {
            xP += fieldSize / 6;
            yP += fieldSize * 0.4f;
        } else if (type == PositionType.FIELD_CHAR) {
            xP += fieldSize / 3;
            yP += fieldSize * 0.7f;
        }
        return new Vector2(xP, yP);
    }

    public Vector2 getSidebarPos(int index, int total, Player.Position playerPos) {
        int yP = viewport.getScreenHeight() / 2 + (total / 2 - index) * (fieldSize + fieldGap);
        int xP = 0;
        if (playerPos == Player.Position.RIGHT) {
            xP = viewport.getScreenWidth() - fieldSize;
        }
        return new Vector2(xP, yP);
    }

    public Vector2 getPlayerScorePos(Player.Position playerPos) {
        int xP = 2;
        if (playerPos == Player.Position.RIGHT) {
            xP = viewport.getScreenWidth() - 100; //75
        }
        return new Vector2(xP, viewport.getScreenHeight() - 2);
    }

    public boolean isInChangingArea(Vector2 v2) {
        boolean a = v2.x > viewport.getScreenWidth() / 2 - 100;
        boolean b = v2.x < viewport.getScreenWidth() / 2 + 100;
        boolean c = v2.y > viewport.getScreenHeight() - 60;
        boolean d = v2.y < viewport.getScreenHeight();
        if (a && b && c && d) {
            return true;
        }
        return false;
    }

    public boolean hasCharNeighbour(Field field) {
        int x = field.x + size / 2;
        int y = field.y + size / 2;
        if ((x > 0 && fields[x - 1][y].hasChar()) || (x < size - 1 && fields[x + 1][y].hasChar())) {
            return true;
        }
        if ((y > 0 && fields[x][y - 1].hasChar()) || (y < size - 1 && fields[x][y + 1].hasChar())) {
            return true;
        }
        return false;
    }

    public Stream<Field> fields() {
        Field[] fields = new Field[size * size];
        int index = 0;
        for (Field[] rows : this.fields) {
            for (Field field : rows) {
                fields[index++] = field;
            }
        }
        return Arrays.stream(fields);
    }

    public Field getNearest(List<Field> f, Vector2 point) {

        class FieldDistPair {

            public Field field;
            public float dist;

            public FieldDistPair(Field f, Vector2 point) {
                field = f;
                dist = getFieldPos(field.x, field.y, Board.PositionType.BOTTOM_LEFT).sub(point).len2();
            }
        }

        List<FieldDistPair> pairs = f.stream()
                .map(field -> new FieldDistPair(field, point))
                .filter(pair -> pair.dist < fieldSize * fieldSize)
                .collect(Collectors.toCollection(ArrayList::new));

        if (pairs.size() < 1) {
            return null;
        }

        float dist = pairs.stream()
                .map(pair -> pair.dist)
                .min(Float::compare).get();

        return pairs.stream()
                .filter(pair -> pair.dist == dist)
                .findFirst().get().field;
    }

    public boolean testCharInDir(com.mygdx.oswebble.Vector2X pos, com.mygdx.oswebble.Vector2X dir) {
        for (int i = 0; i < size; i++) {
            com.mygdx.oswebble.Vector2X v = pos.add(dir.mul(i));
            Field f = getField(v.x, v.y);
            if (f == null) {
                return false;
            } else if (f.hasChar()) {
                return true;
            }
        }
        return false;
    }

    public Field getSnapField(Vector2 pos) {

        List<Field> fieldList = fields()
                .filter(field -> !field.hasChar())
                .filter(field -> {
                    if (hasCharNeighbour(field) || (field.x == 0 && field.y == 0)) {
                        return true;
                    }
                    com.mygdx.oswebble.Vector2X p = new com.mygdx.oswebble.Vector2X(field.x, field.y);
                    return testCharInDir(p, new com.mygdx.oswebble.Vector2X(1, 0)) || testCharInDir(p, new com.mygdx.oswebble.Vector2X(0, -1));
                })
                .collect(Collectors.toCollection(ArrayList::new));

        return getNearest(fieldList, pos);
    }

    public Vector2 getSnapPoint(Vector2 pos) {
        Field f = getSnapField(pos);
        if (f != null) {
            return getFieldPos(f.x, f.y, Board.PositionType.BOTTOM_LEFT);
        }
        return null;
    }

    public Vector2 getWordSnap(Vector2 pos, Word word) {
        if (word == null) {
            return null;
        }

        List<Field> fieldList = fields()
                .filter(field -> !field.hasChar())
                .filter(f -> {
                    if (word.direction == null) {
                        int manhattanDist = Math.abs(word.start.x - f.x) + Math.abs(word.start.y - f.y);
                        return manhattanDist == 1;
                    } else {
                        if (word.direction.x == 0 && word.start.x != f.x) {
                            return false;
                        }
                        if (word.direction.y == 0 && word.start.y != f.y) {
                            return false;
                        }

                        com.mygdx.oswebble.Vector2X p = word.direction.movePosTimes(word.start, word.word.length());
                        if (f.x != p.x || f.y != p.y) {
                            return false;
                        }
                    }
                    return true;
                })
                .collect(Collectors.toCollection(ArrayList::new));

        Field f = getNearest(fieldList, pos);
        if (f == null) {
            return null;
        }
        return getFieldPos(f.x, f.y, Board.PositionType.BOTTOM_LEFT);
    }

    public void drawBackground(ShapeRenderer shapeRenderer) {
        Vector2 from = getFieldPos(-size / 2, -size / 2, PositionType.BOTTOM_LEFT);
        int width = (fieldSize + fieldGap) * size + fieldGap;
        shapeRenderer.rect(from.x - fieldGap, from.y - fieldGap, width, width);
    }

    public void drawTrashArea(ShapeRenderer shapeRenderer) {
        shapeRenderer.rect(viewport.getScreenWidth() / 2 - 100, viewport.getScreenHeight() - 60, 200, 60);
    }
}
