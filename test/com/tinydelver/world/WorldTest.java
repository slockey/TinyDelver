package com.tinydelver.world;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
		
		assertNotNull(world);
		assertEquals(80, world.getWidth());
		assertEquals(21, world.getHeight());
		assertEquals(Tile.BOUNDS, world.getTile(0, 0));
	}

	@Test
	public void smallestWorldShouldInitialize() {
		World world = new World(1,1);
		
		assertNotNull(world);
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
		
		assertNotNull(world);
		for (int idx = 0; idx < world.getWidth(); idx++) {
			for (int idy = 0; idy < world.getHeight(); idy++) {
				assertEquals(Tile.BOUNDS.getGlyph(), world.getTile(idx, idy).getGlyph());
			}
		}
	}
	
}
