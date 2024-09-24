package com.game.scrabble.utilities;

import org.springframework.http.HttpStatus;

public class GameException extends RuntimeException {
	private HttpStatus statusCode;
	
	public HttpStatus getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(HttpStatus statusCode) {
		this.statusCode = statusCode;
	}

	public GameException(HttpStatus statusCode, String error) {
		super(error);
		this.statusCode = statusCode;
	}

}
