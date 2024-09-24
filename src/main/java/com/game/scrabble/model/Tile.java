package com.game.scrabble.model;

public class Tile {
	@Override
	public String toString() {
		return "Tile [value=" + value + ", points=" + points + "]";
	}
	public Tile() {}
	public Tile(char key, int points) {
		// TODO Auto-generated constructor stub
		this.value = key;
		this.points = points;
	}
	public char getValue() {
		return value;
	}
	public void setValue(char value) {
		this.value = value;
	}
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
	private char value;
	private int points;
}
