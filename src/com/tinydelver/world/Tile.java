package com.tinydelver.world;

import java.awt.Color;

import asciiPanel.AsciiPanel;

public enum Tile {

	PLAYER('@', AsciiPanel.white),

	FLOOR((char)250, AsciiPanel.yellow),
	WALL((char)177, AsciiPanel.yellow),
	BOUNDS('X', AsciiPanel.brightBlack),
	
	FUNGUS('f', AsciiPanel.green);
	
	private final char glyph;
	private final Color color;
	
	Tile(char glyph, Color color) {
		this.glyph = glyph;
		this.color = color;
	}
	
	public char getGlyph() {
		return glyph;
	}
	
	public Color getColor() {
		return color;
	}
	
	public boolean isWalkable() {
		return (this == Tile.FLOOR);
	}
	
	public boolean isDiggable() {
		return (this == Tile.WALL);
	}
}
