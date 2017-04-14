package com.tinydelver.world;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.tinydelver.utils.Tuple;

public class WorldTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void defaultWorldInitializationShouldProvideViableWorld() {
		World world = new World();
		
		assertNotNull(world.getTiles());
		assertEquals(80, world.getWidth());
		assertEquals(21, world.getHeight());
		assertEquals(Tile.BOUNDS, world.getTile(0, 0));
	}

	@Test
	public void smallestWorldShouldInitialize() {
		World world = new World(1,1);
		
		assertNotNull(world.getTiles());
		assertEquals(1, world.getWidth());
		assertEquals(1, world.getHeight());
		assertEquals(Tile.BOUNDS, world.getTile(0, 0));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void worldInstantiationShouldFailIfTooLarge() {
		new World(Integer.MAX_VALUE + 1, Integer.MAX_VALUE);
	}

	@Test
	public void shouldVerifyAllTilesInitialized() {
		World world = new World(5, 5);
		
		assertNotNull(world.getTiles());
		for (int idx = 0; idx < world.getWidth(); idx++) {
			for (int idy = 0; idy < world.getHeight(); idy++) {
				assertEquals(Tile.BOUNDS.getGlyph(), world.getTile(idx, idy).getGlyph());
			}
		}
	}
	
	@Test
	public void shouldFailToFindWalkableTileInDefaultWorld() {
		World world = new World(5, 5);
		
		assertNotNull(world.getTiles());
		ArrayList<Tuple<Integer, Integer>> walkable = world.getEmptyWalkableTiles();
		assertNotNull(walkable);
		assertEquals(0, walkable.size());
	}
	
	@Test
	public void shouldFindWalkableTile() {
		Tile[][] tiles = new Tile[3][3];
		// init the tiles
		for (int idx = 0; idx < tiles.length; idx++) {
			for (int idy = 0; idy < tiles[0].length; idy++) {
				tiles[idx][idy] = Tile.BOUNDS;
			}
		}
		// place a single walkable tile in the center position
		tiles[1][1] = Tile.FLOOR;
		// create world
		World world = new World(tiles);
		
		ArrayList<Tuple<Integer, Integer>> walkable = world.getEmptyWalkableTiles();
		assertNotNull(walkable);
		assertEquals(1, walkable.size());
		Tuple<Integer, Integer> theTile = walkable.get(0);
		assertEquals(1, (int)theTile.getLeft());
		assertEquals(1, (int)theTile.getRight());
	}
}
