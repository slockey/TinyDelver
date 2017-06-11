package com.tinydelver.world;

import java.util.ArrayList;
import java.util.HashMap;

import com.tinydelver.utils.Tuple;

public class MazeWorldBuilder implements WorldBuilder {

	private final int width;
	private final int height;
	private Tile[][] tiles;
	private MazeCell[][] maze;

	public MazeWorldBuilder(int width, int height) {
		this.width = width;
		this.height = height;
		// urgh - this is kinda messed up
		// tiles array contains BOUNDS tiles - impassable/undiggable
		this.tiles = new World(width, height).getTiles();

		this.maze = new MazeCell[width][height];
		// convert original tiles array to mazecell array
		for (int idx = 0; idx < width; idx ++) {
			for (int idy = 0; idy < height; idy++) {
				maze[idx][idy] = new MazeCell(tiles[idx][idy], idx, idy);
			}
		}
		// now we can init the neighbors
		for (int idx = 0; idx < width; idx ++) {
			for (int idy = 0; idy < height; idy++) {
				MazeCell currentCell = maze[idx][idy];
				MazeCell north = null;
				if (idx - 1 >= 0) {
					north = maze[idx - 1][idy];
				}
				MazeCell south = null;
				if (idx + 1 <= width - 1) {
					south = maze[idx + 1][idy];
				}
				MazeCell east = null;
				if (idy + 1 <= height - 1) {
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
		// TODO: generate the world
		
		// TODO: populate the world???

		// return a world obj
		World world = new World(tiles);
		return world;
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
		for (int idx = 0; idx < width; idx++) {
			StringBuilder builderSouth = new StringBuilder();
			builderSouth.append(cellWallIntersect);
			builder.append(cellVertWall);
			// we only care about east and south links
			for(int idy = 0; idy < height; idy++) {
				// figure out bounds around each cell in this row
				String tempBody = Integer.toString(idx) + "," + Integer.toString(idy);
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

	/**
	 * Cell should be aware of adjacent cells
	 */
	class MazeCell {
		
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
		
		public boolean isLinkedEast() {
			return (east != null);
		}
		
		public boolean isLinkedSouth() {
			return (south != null);
		}
	}
}
