package com.game.scrabble.utilities;
import jakarta.validation.constraints.NotBlank;

public class GameRequestParams {
	public GameRequestParams(String userName1, String userName2) {
		super();
		this.userName1 = userName1;
		this.userName2 = userName2;
	}

	public void setUserName1(String userName1) {
		this.userName1 = userName1;
	}
	public void setUserName2(String userName2) {
		this.userName2 = userName2;
	}
	
	@NotBlank
	private String userName1;
	public String getUserName1() {
		return userName1;
	}

	public String getUserName2() {
		return userName2;
	}

	@NotBlank
	private String userName2;
}
