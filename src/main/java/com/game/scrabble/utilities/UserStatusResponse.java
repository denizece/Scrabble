package com.game.scrabble.utilities;

public class UserStatusResponse {
public String getUserName() {
	return userName;
}
public void setUserName(String userName) {
	this.userName = userName;
}
public int getScore() {
	return score;
}
public void setScore(int score) {
	this.score = score;
}
public int getDeckItemsLeft() {
	return deckItemsLeft;
}
public void setDeckItemsLeft(int deckItemsLeft) {
	this.deckItemsLeft = deckItemsLeft;
}

public UserStatusResponse(int score, int deckItemsLeft, String userName) {
	super();
	this.score = score;
	this.deckItemsLeft = deckItemsLeft;
	this.userName = userName;
}

private int score;
private int deckItemsLeft;
private String userName;

}
