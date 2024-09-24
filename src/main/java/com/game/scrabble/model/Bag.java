package com.game.scrabble.model;

import java.util.List;

import org.springframework.data.annotation.Id;

public class Bag extends GameObject {
	@Override
	public String toString() {
		return "Bag [id=" + id + ", tiles=" + tiles + "]";
	}

	public Bag() {
		// set id and initialize tiles
		this.id = generateUniqueId();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setTiles(List<Tile> list) {
		this.tiles = list;
	}

	public List<Tile> getTiles() {
		return this.tiles;
	}

	@Id
	private String id;
	private List<Tile> tiles;
}
