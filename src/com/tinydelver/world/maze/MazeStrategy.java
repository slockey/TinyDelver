package com.tinydelver.world.maze;

public interface MazeStrategy {

	public MazeCell[][] generateMaze(MazeCell[][] original, int height, int width);

}