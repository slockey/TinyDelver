package com.tinydelver.world;

import com.tinydelver.creature.Actor;
import com.tinydelver.creature.CreatureFactory;

public class CaveWorldBuilder implements WorldBuilder {

	private final int width;
	private final int height;
	private Tile[][] tiles;
	
	public CaveWorldBuilder(int height, int width) {
		this.width = width;
		this.height = height;
		this.tiles = new World(height, width).getTiles();
	}
	
	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	/**
	 * Builds a random cave world with multi-pass smoothing to create caverns.
	 * 
	 * @return World
	 */
	@Override
	public World buildWorld() {
		// build world tiles
		randomizeTiles();
		smooth(8);
		// create world
		World world = new World(tiles);
		// add stuff to world
		populateWorld(world);

		return world;
	}

	/**
	 * Add creatures, objects and items to the world as part of the build process.
	 * 
	 */
	private void populateWorld(World world) {
		for (int idx = 0; idx < 8; idx++) {
			Actor fungus = CreatureFactory.newFungusInstance(world);
			world.addActorToWorld(fungus);
		}
	}

	/**
	 * Randomly fill the tiles map with either floor or wall tiles.
	 */
	private void randomizeTiles() {
		for (int idx = 0; idx < height; idx++) {
			for (int idy = 0; idy < width; idy++) {
				tiles[idx][idy] = Math.random() < 0.5 ? Tile.FLOOR : Tile.WALL;
			}
		}
	}

	/**
	 * Iterate over the random tiles map to create interesting shapes and spaces.
	 * 
	 * @param iterations as the number of times to process
	 */
	private void smooth(int iterations) {
		Tile[][] working = new Tile[height][width];
		
		for (int iteration = 0; iteration < iterations; iteration++) {
			smooth(height, width, working);
		}
		// replace original world tiles with smoothed
		tiles = working;
	}
	
	private void smooth(int height, int width, Tile[][] toSmooth) {
		for (int idx = 0; idx < height; idx++) {
			for (int idy = 0; idy < width; idy++) {
				int floorCount = 0;
				int notFloorCount = 0;
				
				for (int ox = -1; ox < 2; ox++) {
					for (int oy = -1; oy < 2; oy++) {
						
						if ((idx + ox < 0) || (idx + ox >= height) || (idy + oy < 0) || (idy + oy >=width)) {
							continue;
						}
						
						if (tiles[idx + ox][idy + oy] == Tile.FLOOR) {
							floorCount++;
						} else {
							notFloorCount++;
						}

					}
				}
				// update the current tile
				toSmooth[idx][idy] = floorCount >= notFloorCount ? Tile.FLOOR : Tile.WALL;
			}
		}
	}
}
