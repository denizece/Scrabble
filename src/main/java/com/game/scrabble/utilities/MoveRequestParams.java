package com.game.scrabble.utilities;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class MoveRequestParams {
	public MoveRequestParams(String word, int row, int col, String userName, boolean horizontal) {
		super();
		this.word = word;
		this.row = row;
		this.col = col;
		this.userName = userName;
		this.horizontal = horizontal;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getCol() {
		return col;
	}
	public void setCol(int col) {
		this.col = col;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public boolean getHorizontal() {
		return horizontal;
	}
	public void setHorizontal(boolean horizontal) {
		this.horizontal = horizontal;
	}
	@Size(min=2, max=15)
	private String word;

	@Min(value = 1)
	private int row;

	@Min(value = 1)
	private int col;
	@NotNull
	private String userName;
	@NotNull
	private boolean horizontal;
}
