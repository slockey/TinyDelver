package com.tinydelver.world;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MazeWorldBuilderTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void builderShouldBuildSimpleMazeWorld() throws Exception {
		MazeWorldBuilder builder = new MazeWorldBuilder(12, 12);
		World world = builder.buildWorld();
		
		assertNotNull(world);
		assertEquals(world.getHeight(), 12);
		assertEquals(world.getWidth(), 12);
		// XXX: testing output
		System.out.println(builder.toAsciiString());
		System.out.println(builder.toTileString());
	}

}