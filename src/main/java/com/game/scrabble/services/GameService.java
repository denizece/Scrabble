package com.game.scrabble.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.game.scrabble.model.Bag;
import com.game.scrabble.model.Board;
import com.game.scrabble.model.Deck;
import com.game.scrabble.model.Game;
import com.game.scrabble.model.Tile;
import com.game.scrabble.repositories.GameRepository;
import com.game.scrabble.utilities.GameException;
import com.game.scrabble.utilities.GameStatusResponse;
import com.game.scrabble.utilities.MoveResponse;

@Service
public class GameService {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private GameRepository repository;

	public GameService() {
		super();
	}

	public Game getGame(String id) {
		try {
			Game game = repository.findById(id);
			if (game == null) {
				throw new GameException(HttpStatus.BAD_REQUEST, "No game with ID " + id);

			}
			return game;
		} catch (JsonProcessingException e) {
			throw new GameException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	public boolean addGame(Game game) {
		try {
			repository.save(game);
			return true;
		} catch (JsonProcessingException e) {
			throw new GameException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	public List<Game> getAllGames() {
		// TODO Auto-generated method stub
		try {
			return repository.findAll();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			throw new GameException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	public Game createAndInitialize(String userName1, String userName2) {
		if (userName1.equals(userName2))
			throw new GameException(HttpStatus.BAD_REQUEST, "use different names for 2 users");

		// TODO Auto-generated method stub
		BagSevice bs = new BagSevice();

		Bag bag = bs.createAndInitialize();
		Board board = new Board();

		DeckService ds = new DeckService();
		Game game = ds.createAndInitialize(bag, board, userName1, userName2);

		return game;
	}

	public void checkNeighbors(Game curGame, String word, boolean horizontal, int row, int col) {
		int row_index = row - 1;
		int col_index = col - 1;
		Tile[][] boardVals = curGame.getBoard().getBoardVals();
		if (curGame.getBag().getTiles().size() == 84)
			return; // its the first move
		else {
			for (int i = 0; i < word.length(); i++) {
				if (boardVals[row_index][col_index] != null)
					return;
				if (row_index > 0 && boardVals[row_index - 1][col_index] != null)
					return;// check up
				if (row_index < 14 && boardVals[row_index + 1][col_index] != null)
					return;// check up
				if (col_index > 0 && boardVals[row_index][col_index - 1] != null)
					return;// check left
				if (col_index < 14 && boardVals[row_index][col_index + 1] != null)
					return;// check right
				
				if (horizontal)
					col_index++;
				else
					row_index++;
			}
		}
		throw new GameException(HttpStatus.BAD_REQUEST, "invalid move - place word neighboring others"); // no neighbors
	}

	public boolean isValidMove(Game curGame, String userName, String word, boolean horizontal, int row, int col) { // TODO
																													// stub
		boolean valid = true;
		// ------ check word less than 2
		if (word.length() < 2)
			throw new GameException(HttpStatus.BAD_REQUEST, "invalid move - use word with length of min 2");
		// ------ check turn
		if (curGame.getTurn() == null)
			curGame.setTurn(userName);
		if (curGame.getTurn() != null && !curGame.getTurn().equals(userName)) {
			throw new GameException(HttpStatus.BAD_REQUEST,
					"invalid move - waiting for user " + curGame.getTurn() + " to play first");
		}
		Tile[][] board = curGame.getBoard().getBoardVals();
		if (row - 1 < 0 && col - 1 < 0)
			throw new GameException(HttpStatus.BAD_REQUEST, "invalid move - out of board");
		if (horizontal && col - 1 + word.length() >= board[0].length)
			throw new GameException(HttpStatus.BAD_REQUEST, "invalid move - out of board");
		if (curGame.getEnded())
			throw new GameException(HttpStatus.BAD_REQUEST, "invalid move - Game ended already");
		checkNeighbors(curGame, word, horizontal, row, col);
		Deck userDeck = curGame.getUserDeck(userName);

		return valid;
	}

	private List<Integer> getSharedLetterIndex(Board board, boolean horizontal, String word, int row, int col) {
		List sharedIndexes = new ArrayList<Integer>();
		Tile[][] vals = board.getBoardVals();
		int row_index = row - 1;
		int col_index = col - 1;
		char[] chars = word.toCharArray();

		for (int i = 0; i < chars.length; i++) {
			if (vals[row_index][col_index] != null) {
				// in the case where there is a tile on the board already
				// either it is a shared tile, or its a mistaken move
				if (vals[row_index][col_index].getValue() == chars[i])
					sharedIndexes.add(i);
				// shared char index
				else
					throw new GameException(HttpStatus.BAD_REQUEST, "invalid move");
			}
			// else the word will be from vals
			if (horizontal)
				col_index++;
			else
				row_index++;
		}
		return sharedIndexes;
	}

	public GameStatusResponse getGameStatus(String gameId) throws Exception {
		Game curGame = getGame(gameId);
		return curGame.gameStatus();
	}

	public MoveResponse doMove(String gameId, String userName, String word, boolean horizontal, int row, int col) {
		// TODO Auto-generated method stub
		Map response = new HashMap<String, Object>();
		Game curGame = getGame(gameId);
		if (isValidMove(curGame, userName, word, horizontal, row, col)) {
			List<Integer> sharedLetterIndexes = getSharedLetterIndex(curGame.getBoard(), horizontal, word, row, col);
			List<Tile> tiles = curGame.getUserDeck(userName).takeTiles(word, sharedLetterIndexes);
			// if success fill in the deck
			curGame.fillInUserDeck(userName);

			Board board = curGame.getBoard();
			Tile[][] boardTiles = board.getBoardVals();
			int row_index = row - 1;
			int col_index = col - 1;

			char[] chars = word.toCharArray();
			int move_score = 0;
			int tileIndex = 0;
			for (int charInd = 0; charInd < chars.length; charInd++) {
				if (sharedLetterIndexes.contains(charInd)) {
					char c = chars[charInd];
					// use the board value
					// check if it is valid char
					if (c != chars[charInd])
						throw new GameException(HttpStatus.BAD_REQUEST,
								"invalid move, problem at index " + charInd + 1);
					else
						move_score += boardTiles[row_index][col_index].getPoints();
				} else {
					// get the char from tiles in your hand
					if (tileIndex >= tiles.size())
						throw new GameException(HttpStatus.BAD_REQUEST,
								"invalid move, problem at index " + charInd + 1);
					// check if it is the valid char
					Tile tileAtHand = tiles.get(tileIndex);
					if (tileAtHand.getValue() != chars[charInd])
						throw new GameException(HttpStatus.BAD_REQUEST,
								"invalid move, problem at index " + charInd + 1);
					boardTiles[row_index][col_index] = tileAtHand;
					move_score += boardTiles[row_index][col_index].getPoints();
					tileIndex++;
				}
				// set board indexes
				if (horizontal)
					col_index++;
				else
					row_index++;
			}
			
			//add additional scores
			int additionalScore = calculateAdditionalScore(boardTiles, row-1, col-1, horizontal, chars.length);
			move_score += additionalScore;
			
			Deck deck = curGame.getUserDeck(userName);
			deck.setScore(deck.getScore() + move_score);
			
			
			curGame.setUserDeck(userName, deck);

			// check if Game has ended
			if (deck.getElements().isEmpty())
				curGame.setEnded(true);
			// users deck is just finished so the game has ended

			curGame.changeUserTurn(userName);
			try {
				repository.save(curGame);
			} catch (JsonProcessingException e) {
				throw new GameException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
			}
			MoveResponse moveResp = new MoveResponse(board, curGame.getDeck1(), curGame.getDeck2(), curGame.getTurn());
			return moveResp;
		} else {
			throw new GameException(HttpStatus.BAD_REQUEST, "invalid move");
		}
	}

	private int calculateAdditionalScore(Tile[][] boardVals, int row, int col, boolean horizontal, int length) {
		// TODO Auto-generated method stub
		int rowInd = row; int colInd = col;
		int score = 0;
		for (int i = 0; i < length; i++) {
			//if horizontal for each tile check vertical score
			if(horizontal) {
				// if there are tiles upwards add points
				while (rowInd > 0 && boardVals[rowInd-1][colInd] !=null)
					score += boardVals[rowInd--][colInd].getPoints();
				// if there are tiles downwards add points
				
				// first reset rowInd
				rowInd = row;
				
				//then check downwards now
				while (rowInd < 14 && boardVals[rowInd+1][colInd] !=null)
					score += boardVals[rowInd++][colInd].getPoints();				
			}
			else {
				// if there are tiles left add points
				while (colInd > 0 && boardVals[rowInd][colInd-1] !=null)
					score += boardVals[rowInd][colInd--].getPoints();
				
				// first reset colInd
				colInd = col;
				
				//then check to the right now
				while (rowInd < 14 && boardVals[rowInd][colInd+1] !=null)
					score += boardVals[rowInd][colInd++].getPoints();
			}
		}
		return score;
	}

	public long deleteGame(String gameId) {
		// TODO Auto-generated method stub
		return repository.deleteById(gameId);
	}

}
