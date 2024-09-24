package com.game.scrabble.model;

import org.springframework.data.annotation.Id;

public class Player extends GameObject{
	public Player() {}
	public Player(String userId, String userName) {
		// TODO Auto-generated constructor stub
		this.userName = userName;
		this.id = userId;
	}
	
	public Player(String userName) {
		// TODO Auto-generated constructor stub
		this.userName = userName;
		this.id = generateUniqueId();
	}

	@Override
	public String toString() {
		return "Player [id=" + id + "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	@Id
	private String id;
	private String userName;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
