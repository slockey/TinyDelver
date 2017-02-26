package com.tinydelver.world;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import com.tinydelver.creature.Actor;


/**
 * World is something to walk around in.
 *
 *	Possible later we will want multiple worlds and link them.
 *
 */
public class World {

	private final int width;
	private final int height;
	private final Tile[][] tiles;
	
	private HashMap<String,Actor> actors;
	
	public World() {
		// default to size of asciiPanel w/h
		this(80, 21);
	}
	
	public World(int width, int height) throws IllegalArgumentException {

		if ((width > Integer.MAX_VALUE) || (width < 1)) {
			throw new IllegalArgumentException("World width size should be between 1 and Integer.MAX_VALUE");
		}
		if ((height > Integer.MAX_VALUE) || (height < 1)) {
			throw new IllegalArgumentException("World height size should be between 1 and Integer.MAX_VALUE");
		}

		this.width = width;
		this.height = height;
		this.actors = new HashMap<String,Actor>();
		
		this.tiles = new Tile[width][height];
		// initialize the world
		initTiles(tiles, Tile.BOUNDS);
	}
	
	public World(Tile[][] tiles) {
		this.width = tiles.length;
		this.height = tiles[0].length;
		this.tiles = tiles;
		this.actors = new HashMap<String,Actor>();
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Tile[][] getTiles() {
		return tiles;
	}

	/**
	 * Retrieve tile at specified location. If location is out of bounds then return BOUND tile.
	 * 
	 * @param x as int
	 * @param y as int
	 * @return Tile at location
	 */
	public Tile getTile(int x, int y) {
		if (((x < 0) || x >= width) || ((y < 0) || (y >= height))) {
			return Tile.BOUNDS;
		} else {
			return tiles[x][y];
		}
	}
	
	/**
	 * Initialize the world array with the provided tile.
	 * 
	 * @param tiles array representing the world
	 * @param tile default tile to fill the world
	 */
	protected void initTiles(Tile[][] tiles, Tile tile) {
		for (int idx = 0; idx < tiles.length; idx++) {
			for (int idy = 0; idy < tiles[0].length; idy++) {
				tiles[idx][idy] = tile;
			}
		}
	}
	
	/**
	 * Actors can dig wall tiles and turn them into floor
	 * @param x
	 * @param y
	 */
	public void dig(int x, int y) {
	    if (tiles[x][y].isDiggable())
	        tiles[x][y] = Tile.FLOOR;
	}

	/**
	 * Add an actor to the world actors list.
	 * 
	 * @param actor
	 */
	public void addActorToWorld(Actor actor) {
		if (actor == null) {
			throw new IllegalArgumentException("Actor cannot be null");
		}
		actors.put(actor.getId(), actor);
	}

	public void removeActorFromWorld(Actor actor) {
		if (actor == null) {
			throw new IllegalArgumentException("Actor cannot be null");
		}
		actors.remove(actor.getId());
	}
	
	public final Collection<Actor> getActors() {
		return actors.values();
	}

	/**
	 * Place the provided actor in the world at a random location that is
	 * classified as 'ground'.
	 * 
	 * @param creature
	 */
	public void determineEmptyStartLocation(Actor creature) {
		int x = 0;
		int y = 0;
		// determine target x,y location
		boolean foundPosition = false;
		while (!foundPosition) {
			// TODO: this loop could be infinite if there are no walkable tiles
			//		 in the world map!!! Need a unit test and solution
			x = (int)(Math.random() * width);
			y = (int)(Math.random() * height);

			// test we are within bounds
			if ((x >= 0 && x < width) && (y >= 0 && y < height)) {
				// test this location
				if (tiles[x][y].isWalkable() && getActorAtLocation(x, y) == null) {
					// set creature location
					creature.setXPos(x);
					creature.setYPos(y);
					// flag found position
					foundPosition = true;
				}
				
			}
		}
	}

	/**
	 * Retrieves the actor at the specified world location. May return null
	 * if no actor.
	 * 
	 * @param x
	 * @param y
	 * @return Actor or null;
	 */
	public Actor getActorAtLocation(int x, int y) {
		Actor result = null;
		Iterator<String> it = actors.keySet().iterator();
		while (it.hasNext()) {
			Actor actor = actors.get(it.next());
			if ((actor.getXPos() == x) && (actor.getYPos() == y)) {
				result = actor;
				break;
			}
		}
		return result;
	}

	/**
	 * Initiate an update on each actor
	 */
	public void update() {
		ArrayList<Actor> copy = new ArrayList<Actor>(actors.values());
		Iterator<Actor> it = copy.iterator();
		while (it.hasNext()) {
			Actor actor = it.next();
			actor.onUpdate();
		}
	}
}
