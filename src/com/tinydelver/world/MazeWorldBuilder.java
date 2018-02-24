package com.tinydelver.world;

import com.tinydelver.world.maze.BTreeMazeStrategy;
import com.tinydelver.world.maze.MazeCell;

public class MazeWorldBuilder implements WorldBuilder {

	private final int width;
	private final int height;
	private Tile[][] tiles;
	private MazeCell[][] maze;

	public MazeWorldBuilder(int height, int width) {
		this.width = width;
		this.height = height;
		// urgh - this is kinda messed up
		// tiles array contains BOUNDS tiles - impassable/undiggable
		this.tiles = new World(height, width).getTiles();
		this.maze = new MazeCell[height][width];
		initialize(height, width);
	}

	/**
	 * Convert the Tile array into a MazeCell array. Difference between the two 
	 * is that Tiles are simple and MazeCells wrap Tiles to make them neighbor aware, 
	 * which will help when implementing various maze generation algorithms.
	 * 
	 * @param height
	 * @param width
	 */
	private void initialize(int height, int width) {
		// convert original tiles array to mazecell array
		for (int idx = 0; idx < height; idx ++) {
			for (int idy = 0; idy < width; idy++) {
				maze[idx][idy] = new MazeCell(tiles[idx][idy], idx, idy);
			}
		}
		// now we can init the neighbors
		for (int idx = 0; idx < height; idx ++) {
			for (int idy = 0; idy < width; idy++) {
				MazeCell currentCell = maze[idx][idy];
				MazeCell north = null;
				if (idx - 1 >= 0) {
					north = maze[idx - 1][idy];
				}
				MazeCell south = null;
				if (idx + 1 <= height - 1) {
					south = maze[idx + 1][idy];
				}
				MazeCell east = null;
				if (idy + 1 <= width - 1) {
					east = maze[idx][idy + 1];
				}
				MazeCell west = null;
				if (idy - 1 >= 0) {
					west = maze[idx][idy -1];
				}
				currentCell.initNeighbors(north, south, east, west);
			}
		}
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public World buildWorld() {
		// generate the maze
		BTreeMazeStrategy btree = new BTreeMazeStrategy();
		maze = btree.generateMaze(maze, height, width);
		// convert maze to tiles
		tiles = convertMazeToTiles(maze);
		// create the world using the tile array maze
		World world = new World(tiles);
		return world;
	}

	protected Tile[][] convertMazeToTiles(MazeCell[][] maze) {
		Tile[][] tiles = new Tile[height][width];

		for (int idx = 0; idx < height; idx++) {
			for (int idy = 0; idy < width; idy++) {
				tiles[idx][idy] = maze[idx][idy].getTile();
			}
		}

		return tiles;
	}

	public String toTileString() {
		StringBuilder builder = new StringBuilder();

		for (int idx = 0; idx < height; idx++) {
			for (int idy = 0; idy < width; idy++) {
				builder.append(tiles[idx][idy].getGlyph());
			}
			builder.append("\n");
		}

		return builder.toString();
	}

	/**
	 * Convert the maze world into ASCII version of world for initial testing and verification.
	 * @return String representation of the maze in ASCII format
	 */
	public String toAsciiString() {
		String cellBody = "   ";
		String cellVertWall = "|";
		String cellHorzWall = "---";
		String cellWallIntersect = "+";
		
		StringBuilder builder = new StringBuilder();

		// step 1 (top) - for each column draw intersecting points '+' and lines '-'
		builder.append(cellWallIntersect);
		for (int idCols = 0; idCols < width; idCols++) {
			builder.append(cellHorzWall);
			builder.append(cellWallIntersect);
		}
		builder.append("\n");
		
		// step 2 - for each row draw appropriate vertical lines '|', etc.
		for (int idx = 0; idx < height; idx++) {
			StringBuilder builderSouth = new StringBuilder();
			builderSouth.append(cellWallIntersect);
			builder.append(cellVertWall);
			// we only care about east and south links
			for(int idy = 0; idy < width; idy++) {
				// figure out bounds around each cell in this row
				String tempBody = "   "; //Integer.toString(idx) + "," + Integer.toString(idy);
				builder.append(tempBody);

				MazeCell currentCell = maze[idx][idy];
				// check east
				if (currentCell.isLinkedEast()) {
					builder.append(" ");
				} else {
					builder.append(cellVertWall);
				}
				
				// check south
				if (currentCell.isLinkedSouth()) {
					builderSouth.append(cellBody);
				} else {
					builderSouth.append(cellHorzWall);
				}
				builderSouth.append(cellWallIntersect);

			}

			builder.append("\n");
			// append the south side
			builderSouth.append("\n");
			builder.append(builderSouth.toString());

		}
		// final break
		builder.append("\n");
		
		return builder.toString();
	}

}
