package com.game.scrabble.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.game.scrabble.model.Bag;
import com.game.scrabble.model.Letter;
import com.game.scrabble.model.Tile;
import com.game.scrabble.utilities.JsonReader;

public class BagSevice {
	
	public Bag createAndInitialize() {
		Bag bag = new Bag();
		bag.setTiles(getTiles());
		return bag;
	}
	
	private List<Tile> getTiles(){
		JsonReader reader = new JsonReader();
		List<Tile> tiles = new ArrayList<Tile>();
		Map<String, Letter> letters = reader.readLetters();
		for(Letter letter: letters.values())
		{
			Tile tile = new Tile(letter.getKey(),letter.getPoints());
			tiles.addAll(Collections.nCopies(letter.getQuantity(), tile));
		}
		return tiles;
	}
	

}


