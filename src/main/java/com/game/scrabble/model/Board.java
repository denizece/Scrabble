package com.game.scrabble.model;

import java.util.Arrays;

import org.springframework.data.annotation.Id;

public class Board extends GameObject {

	public Board() {
		this.id = generateUniqueId();
		// initialize board
		this.boardVals = new Tile[BOARD_SIZE][BOARD_SIZE];
	}

	@Override
	public String toString() {
		return "Board [id=" + id + ", boardVals=\n" + Arrays.deepToString(boardVals) + "\n]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Tile[][] getBoardVals() {
		return boardVals;
	}

	public void setBoardVals(Tile[][] boardVals) {
		this.boardVals = boardVals;
	}

	@Id
	private String id;
	private Tile[][] boardVals;
	private static final int BOARD_SIZE = 15;
}
