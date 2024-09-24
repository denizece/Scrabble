package com.game.scrabble.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.http.HttpStatus;

import com.game.scrabble.utilities.GameException;
import com.game.scrabble.utilities.GameStatusResponse;
import com.game.scrabble.utilities.UserStatusResponse;

public class Game extends GameObject {
	public Game() {
	}

	public Game(String _id, Bag bag, Board board, Deck deck1, Deck deck2) {
		this._id = _id;
		this.bag = bag;
		this.board = board;
		this.deck1 = deck1;
		this.deck2 = deck2;
		turn = null;
		this.ended = false;
	}

	public Game(Bag bag, Board board) {
		// TODO Auto-generated constructor stub
		this._id = generateUniqueId();
		this.bag = bag;
		this.board = board;
		this.turn = null;
		this.ended = false;
	}

	public Game(Bag bag) {
		// TODO Auto-generated constructor stub
		this._id = generateUniqueId();
		this.bag = bag;
		this.turn = null;
		this.ended = false;
	}

	public Deck getUserDeck(String userName) {
		if (deck1.getOwner().getUserName().equals(userName))
			return deck1;
		else if (deck2.getOwner().getUserName().equals(userName))
			return deck2;
		throw new GameException(HttpStatus.BAD_REQUEST, "user with name" + userName + "is not a player in the game " + "\n current users : "
				+ deck1.getOwner().getUserName() + ", " + deck2.getOwner().getUserName());
	}

	public Deck setUserDeck(String userName, Deck deck) {
		if (deck1.getOwner().getUserName().equals(userName))
			return deck1;
		else if (deck2.getOwner().getUserName().equals(userName))
			return deck2;
		throw new GameException(HttpStatus.BAD_REQUEST,"no deck for user with id " + userName);
	}

	public void changeUserTurn(String userName) {
		if (!deck1.getOwner().getUserName().equals(userName))
			setTurn(deck1.getOwner().getUserName());
		else if (!deck2.getOwner().getUserName().equals(userName))
			setTurn(deck2.getOwner().getUserName());
	}

	public boolean fillInUserDeck(String userName){
		boolean success = false;
		int quantity;
		Deck deck = getUserDeck(userName);
		if (deck.getElements().size() < 7) {
			List<Tile> deckElements = deck.getElements();
			quantity = 7 - deckElements.size();
			Bag bag = getBag();
			List<Tile> bagTiles = bag.getTiles();
			if (bagTiles.isEmpty())
				return true;
			Collections.shuffle(bagTiles);
			ArrayList deckTilesToAdd = new ArrayList<>();
			for (int i = 0; i < quantity; i++) {
				Tile tile = bagTiles.remove(0);
				deckTilesToAdd.add(tile);
				Collections.shuffle(bagTiles);
			}
			// update bagtiles and games bag
			bag.setTiles(bagTiles);
			setBag(bag);
			// ------
			deckElements.addAll(deckTilesToAdd);
			deck.setElements(deckElements);
			setUserDeck(userName, deck);
			success = true;
		}
		return success;

	}

	@Override
	public String toString() {
		return "Game [id=" + _id + ", bag=" + bag + ", deck1=" + deck1 + ", deck2=" + deck2 + "]";
	}

	public Bag getBag() {
		return bag;
	}

	public void setBag(Bag bag) {
		this.bag = bag;
	}

	public Deck getDeck1() {
		return deck1;
	}

	public void setDeck1(Deck deck1) {
		this.deck1 = deck1;
	}

	public Deck getDeck2() {
		return deck2;
	}

	public void setDeck2(Deck deck2) {
		this.deck2 = deck2;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getTurn() {
		return turn;
	}

	public void setTurn(String turn) {
		this.turn = turn;
	}

	public boolean isEnded() {
		return ended;
	}

	public boolean getEnded() {
		return this.ended;
	}

	public void setEnded(boolean ended) {
		this.ended = ended;
	}

	public GameStatusResponse gameStatus() {
		UserStatusResponse one = new UserStatusResponse(this.getDeck1().getScore(), this.getDeck1().getElements().size() ,this.getDeck1().getOwner().getUserName());
		UserStatusResponse two = new UserStatusResponse(this.getDeck2().getScore(), this.getDeck2().getElements().size() ,this.getDeck2().getOwner().getUserName());
		GameStatusResponse gameStats = new GameStatusResponse(one, two, this.getBag().getTiles().size());
		return gameStats;
	}

	@Id
	private String _id;
	private Bag bag;
	private Board board;
	private Deck deck1;
	private Deck deck2;
	private String turn;
	private boolean ended;

}
