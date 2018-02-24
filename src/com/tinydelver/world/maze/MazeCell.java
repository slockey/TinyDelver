package com.tinydelver.world.maze;

import java.util.ArrayList;
import java.util.HashMap;

import com.tinydelver.utils.Tuple;
import com.tinydelver.world.Tile;

public class MazeCell {
	
	private Tile tile;
	private String id;
	private Tuple<Integer,Integer> coords;

	private HashMap<String,MazeCell> links = new HashMap<String,MazeCell>(4);
	private MazeCell north;
	private MazeCell south;
	private MazeCell east;
	private MazeCell west;

	public MazeCell(Tile tile, int x, int y) {
		this.tile = tile;
		this.id = new String(Integer.toString(x) + ":" + Integer.toString(y));
		this.coords = new Tuple<Integer,Integer>(x, y);
	}

	public Tile getTile() {
		return tile;
	}

	public void setTile(Tile tile) {
		this.tile = tile;
	}

	public String getId() {
		return id;
	}

	public Tuple<Integer,Integer> getCoords() {
		return coords;
	}
	
	public ArrayList<String> getLinks() {
		return new ArrayList<String>(links.keySet());
	}
	
	public boolean isLinked(MazeCell cell) {
		return links.containsKey(cell.getId());
	}

	public void initNeighbors(MazeCell north, MazeCell south, MazeCell east, MazeCell west) {
		this.north = north;
		this.south = south;
		this.east = east;
		this.west = west;
	}
	
	public ArrayList<MazeCell> getNeighbors() {
		ArrayList<MazeCell> neighbors = new ArrayList<MazeCell>();
		if (north != null) neighbors.add(north);
		if (south != null) neighbors.add(south);
		if (east != null) neighbors.add(east);
		if (west != null) neighbors.add(west);
		return neighbors;
	}

	public void link(MazeCell cell, boolean bidirectional) {
		links.put(cell.getId(), cell);
		if (bidirectional) {
			cell.link(this, false);
		}
	}
	
	public void unlink(MazeCell cell, boolean bidirectional) {
		if(links.containsKey(cell.getId())) {
			links.remove(cell.getId());
			if (bidirectional) {
				cell.unlink(this, false);
			}
		}
	}
	
	public MazeCell getNorth() {
		return north;
	}

	public MazeCell getSouth() {
		return south;
	}

	public MazeCell getEast() {
		return east;
	}

	public MazeCell getWest() {
		return west;
	}

	public boolean isLinkedNorth() {
		return (north != null && links.get(north.getId()) != null);
	}

	public boolean isLinkedWest() {
		return (west != null && links.get(west.getId()) != null);
	}

	public boolean isLinkedEast() {
		return (east != null && links.get(east.getId()) != null);
	}

	public boolean isLinkedSouth() {
		return (south != null && links.get(south.getId()) != null);
	}

}
