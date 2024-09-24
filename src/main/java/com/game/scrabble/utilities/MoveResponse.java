package com.game.scrabble.utilities;

import com.game.scrabble.model.Board;
import com.game.scrabble.model.Deck;

public class MoveResponse {
	public Board getBoard() {
		return board;
	}
	public void setBoard(Board board) {
		this.board = board;
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
	public String getTurn() {
		return turn;
	}
	public void setTurn(String turn) {
		this.turn = turn;
	}
	public MoveResponse(Board board, Deck deck1, Deck deck2, String turn) {
		super();
		this.board = board;
		this.deck1 = deck1;
		this.deck2 = deck2;
		this.turn = turn;
	}
	private Board board;
	private Deck deck1;
	private Deck deck2;
	private String turn;
}
