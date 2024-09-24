package com.game.scrabble.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.annotation.Id;
import org.springframework.http.HttpStatus;

import com.game.scrabble.utilities.GameException;

public class Deck extends GameObject {

	@Override
	public String toString() {
		return "Deck [id=" + id + ", elements=" + elements + ", owner=" + owner + ", score=" + score + "]";
	}

	public Deck() {
	}

	public Deck(String userId) {
		// TODO Auto-generated constructor stub
		this.id = generateUniqueId();
		this.owner = new Player(userId);
		this.elements = new ArrayList<>();
		// fill in the deck now
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Tile> getElements() {
		return elements;
	}

	public void setElements(List<Tile> elements) {
		this.elements = elements;
	}

	public Player getOwner() {
		return owner;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public List<Tile> takeTiles(String word, List<Integer> sharedLetterIndexes) 
	{
			List<Tile> word_tiles = new ArrayList<Tile>();
			char[] chars = word.toCharArray();
			int index = 0;
			for (char c : chars) {
				if (!sharedLetterIndexes.contains(index)) {
					// this letter will be used from the deck
					word_tiles.add(takeTile(c));
				} else {
					// else it will be used from the board
				}
				index++;
			}
			// it was successful so remove used tiles from deck
			removeTilesFromDeck(word_tiles);
			return word_tiles;
	}

	private void removeTilesFromDeck(List<Tile> tilesToRemove) {
		List<Tile> deckTiles = getElements();
		for (Tile toRemove : tilesToRemove) {
			for (Tile deckTile : deckTiles) {
				if (deckTile.getValue() == toRemove.getValue()) {
					deckTiles.remove(toRemove);
					break;
				}
			}
		}
	}

	private Tile takeTile(char c) {
		// TODO Auto-generated method stub
			List<Tile> deckTiles = getElements();
			Optional<Tile> tileIf = deckTiles.stream().filter(item -> c == (item.getValue())).findFirst();
			if (tileIf.isPresent()) {
				Tile tile = tileIf.get();
				return tile;
			}
			else
				throw new GameException(HttpStatus.BAD_REQUEST,"invalid move"); 
	}

	@Id
	private String id;
	private List<Tile> elements;
	private Player owner;
	private static final int DECK_SIZE = 7;
	private int score = 0;
}
