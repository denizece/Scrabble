package com.game.scrabble.controllers;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.game.scrabble.model.Game;
import com.game.scrabble.services.GameService;
import com.game.scrabble.utilities.GameException;
import com.game.scrabble.utilities.GameRequestParams;
import com.game.scrabble.utilities.GameStatusResponse;
import com.game.scrabble.utilities.MoveRequestParams;
import com.game.scrabble.utilities.MoveResponse;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

@RestController
@RequestMapping("/scrabble/game")
public class ScrabbleGameController {
	private static final Logger logger = LoggerFactory.getLogger(ScrabbleGameController.class);
	@Autowired
	private GameService gameService;

	public ScrabbleGameController() {
	}

	@GetMapping("/")
	public Collection<Game> getAllGames() throws Exception {
		return gameService.getAllGames();
	}

	@GetMapping("/{gameId}")
	public Game getGame(@PathVariable String gameId) throws Exception {
		return gameService.getGame(gameId);

	}

	@PostMapping("/{gameId}/move")
	public MoveResponse move(@PathVariable String gameId, @RequestBody @Valid MoveRequestParams params)
			throws Exception {

		String userName = params.getUserName();
		String word = params.getWord();
		boolean horizontal = params.getHorizontal();
		int row = params.getRow();
		int col = params.getCol();
		return gameService.doMove(gameId, userName, word, horizontal, row, col);

	}

	@GetMapping("/{gameId}/status")
	public GameStatusResponse status(@PathVariable String gameId) throws Exception {

		return gameService.getGameStatus(gameId);

	}

	@PostMapping
	public Game addGame(@RequestBody @Valid GameRequestParams params) {
		String userName1, userName2;

		userName1 = params.getUserName1();
		userName2 = params.getUserName2();

		Game game = gameService.createAndInitialize(userName1, userName2);
		boolean success = gameService.addGame(game);
		if (!success)
			throw new GameException(HttpStatus.INTERNAL_SERVER_ERROR, "game could not be saved successfully");
		return game;

	}

	@DeleteMapping("/{gameId}")
	public long deleteUser(@PathVariable String gameId) {
		return gameService.deleteGame(gameId);
	}
}
