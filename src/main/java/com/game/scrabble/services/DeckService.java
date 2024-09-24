package com.game.scrabble.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.game.scrabble.model.Bag;
import com.game.scrabble.model.Board;
import com.game.scrabble.model.Deck;
import com.game.scrabble.model.Game;
import com.game.scrabble.model.Tile;

public class DeckService {
	
	
	public Game createAndInitialize(Bag bag, Board board, String userId1, String userId2) {
		Game game = new Game(bag, board);
		List<Tile> bagTiles = bag.getTiles();
				
		Deck deck1 = new Deck(userId1);
		Deck deck2 = new Deck(userId2);
		List<Tile>deck1Tiles = new ArrayList<Tile>();
		List<Tile>deck2Tiles = new ArrayList<Tile>();
		
		Collections.shuffle(bagTiles);
		for (int i = 0; i < 14; i++) {
			 Tile tile = bagTiles.remove(0);
			 if (i % 2 == 0)
				 deck1Tiles.add(tile);
			 else
				 deck2Tiles.add(tile);
			 Collections.shuffle(bagTiles);
		}
		
		bag.setTiles(bagTiles);// update bag with the removed tiles
		game.setBag(bag); // update game with the updated bag
		
		deck1.setElements(deck1Tiles);
		deck2.setElements(deck2Tiles);
		game.setDeck1(deck1);
		game.setDeck2(deck2);
		return game;
	}
	
}
