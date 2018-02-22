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
public class Vector2X {
	int x;
	int y;

	Vector2X (int x, int y) {
		this.x = x;
		this.y = y;
	}

	Vector2X add(Vector2X other) {
		return new Vector2X(x + other.x, y + other.y);
	}

	Vector2X mul(int n) {
		return new Vector2X(x*n, y*n);
	}
}