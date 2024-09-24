package com.game.scrabble.controllers;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.game.scrabble.utilities.GameException;

@ControllerAdvice
public class GameExceptionController {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@ExceptionHandler(value = GameException.class)
	public ResponseEntity<Object> exception(GameException exception) {
		logger.error("exception : " + exception.getMessage());
		JSONObject response = new JSONObject();
		response.put("message", exception.getMessage());
		return ResponseEntity.status(exception.getStatusCode()).body(response);
	}

	@ExceptionHandler(value = Exception.class) // default exception
	public ResponseEntity<Object> exception(Exception exception) {
		logger.error("exception : " + exception.getMessage());
		JSONObject response = new JSONObject();
		response.put("message", exception.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	}
}
