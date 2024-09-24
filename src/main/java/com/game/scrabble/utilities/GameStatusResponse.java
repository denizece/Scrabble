package com.game.scrabble.utilities;

public class GameStatusResponse {
	public GameStatusResponse(UserStatusResponse userStats1, UserStatusResponse userStats2, int tilesLeft) {
		super();
		this.userStats1 = userStats1;
		this.userStats2 = userStats2;
		this.tilesLeft = tilesLeft;
	}
	public UserStatusResponse getUserStats1() {
		return userStats1;
	}
	public void setUserStats1(UserStatusResponse userStats1) {
		this.userStats1 = userStats1;
	}
	public UserStatusResponse getUserStats2() {
		return userStats2;
	}
	public void setUserStats2(UserStatusResponse userStats2) {
		this.userStats2 = userStats2;
	}
	public int getTilesLeft() {
		return tilesLeft;
	}
	public void setTilesLeft(int tilesLeft) {
		this.tilesLeft = tilesLeft;
	}
	private UserStatusResponse userStats1;
	private UserStatusResponse userStats2;
	private int tilesLeft;
}
