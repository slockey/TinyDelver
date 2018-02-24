package com.tinydelver.world;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import com.tinydelver.creature.Actor;
import com.tinydelver.utils.Tuple;


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
		this(21, 80);
	}
	
	public World(int height, int width) throws IllegalArgumentException {

		if ((width > Integer.MAX_VALUE) || (width < 1)) {
			throw new IllegalArgumentException("World width size should be between 1 and Integer.MAX_VALUE");
		}
		if ((height > Integer.MAX_VALUE) || (height < 1)) {
			throw new IllegalArgumentException("World height size should be between 1 and Integer.MAX_VALUE");
		}

		this.width = width;
		this.height = height;
		this.actors = new HashMap<String,Actor>();
		
		this.tiles = new Tile[height][width];
		// initialize the world
		initTiles(tiles, Tile.BOUNDS);
	}
	
	public World(Tile[][] tiles) {
		this.height = tiles.length;
		this.width = tiles[0].length;
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
		if (((x < 0) || x >= height) || ((y < 0) || (y >= width))) {
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
	public boolean determineEmptyStartLocation(Actor creature) {
		boolean foundPosition = false;
		
		// verify that there are walkable tiles before continuing
		ArrayList<Tuple<Integer,Integer>> walkable = getEmptyWalkableTiles();
		if (!walkable.isEmpty()) {
			// randomly select start location based on collection
			int pos = (int)(Math.random() * walkable.size()-1);
			Tuple<Integer, Integer> selectedPosition = walkable.get(pos);
			// set creature location
			creature.setXPos(selectedPosition.getLeft());
			creature.setYPos(selectedPosition.getRight());
			// flag found position
			foundPosition = true;
		}
		
		return foundPosition;
	}
	
	/**
	 * Test the tiles array to confirm that there are walkable tiles. These are
	 * required to determine start locations.
	 *
	 * @return boolean showing if there are walkable tiles
	 */
	protected ArrayList<Tuple<Integer, Integer>> getEmptyWalkableTiles() {
		ArrayList<Tuple<Integer, Integer>> walkableTiles = new ArrayList<Tuple<Integer, Integer>>();

		for (int idx = 0; idx < tiles.length; idx++) {
			for (int idy = 0; idy < tiles[0].length; idy++) {
				if (tiles[idx][idy].isWalkable() && getActorAtLocation(idx, idy) == null) {
					walkableTiles.add(new Tuple<Integer, Integer>(idx, idy));
				}
			}
		}

		return walkableTiles;
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
