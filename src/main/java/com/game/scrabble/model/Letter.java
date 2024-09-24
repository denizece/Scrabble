package com.game.scrabble.model;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Letter {
	private static final Logger logger = LoggerFactory.getLogger(Letter.class);

	public Letter(Map value) {
		try {
			if (value.containsKey("points"))
				this.points = (int) value.get("points");
			if (value.containsKey("tiles"))
				this.tiles = (int) value.get("tiles");
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}
	}

	public char getKey() {
		return key;
	}

	public void setKey(char key) {
		this.key = key;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public int getQuantity() {
		return tiles;
	}

	public void setQuantity(int quantity) {
		this.tiles = quantity;
	}

	private char key;
	private int points;
	private int tiles;
}
