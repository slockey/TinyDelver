package com.tinydelver.world.maze;

import java.util.ArrayList;

import com.tinydelver.utils.dice.DiceRoller;
import com.tinydelver.utils.dice.DiceValue;
import com.tinydelver.world.Tile;

public class BTreeMazeStrategy implements MazeStrategy {

	@Override
	public MazeCell[][] generateMaze(MazeCell[][] original, int height, int width) {

		for (int idx = 0; idx < height; idx++) {
			for (int idy = 0; idy < width; idy++) {
				// determine links
				linkSouthOrEastTile(original[idx][idy]);
			}
		}

		return original;
	}

	/**
	 * Alternate btree implementation that uses neighbor cells as walls.
	 * @param cell
	 */
	protected void linkSouthOrEastTile(MazeCell cell) {
		// each cell should always have at least 2 neighbors that are considered bounds
		int boundsCount = 0;
		if (cell.getNorth() != null)
			boundsCount += 1;
		if (cell.getSouth() != null)
			boundsCount += 1;
		if (cell.getEast() != null)
			boundsCount += 1;
		if (cell.getWest() != null)
			boundsCount += 1;

		if (boundsCount < 4) {
			cell.setTile(Tile.BOUNDS);
		} else {
			ArrayList<MazeCell> options = new ArrayList<MazeCell>();
			// determine if there are cells to the south and/or east
			if (cell.getEast() != null) {
				options.add(cell.getEast());
			}
			if (cell.getSouth() != null) {
				options.add(cell.getSouth());
			}
			// randomly select one s/e neighbor to link
			MazeCell selected = null;
			if (options.size() == 2) {
				int ordinal = DiceRoller.roll(1, DiceValue.D2);
				selected = options.get(ordinal-1);
			} else if (options.size() == 1) {
				selected = options.get(0);
			}
			// use selected cell to determine next jump
			if (selected != null) {
				selected.setTile(Tile.FLOOR);
				cell.link(selected, true);
			}
			// if the current cell is a BOUND, change it to a WALL
			if (cell.getTile() == Tile.BOUNDS) {
				cell.setTile(Tile.WALL);
			}
		}
	}

	/**
	 * Original btree implementation. No regard for neighbor cells that could 
	 * be walls. Assumes walls are extra.
	 * @param cell
	 */
	protected void linkSouthOrEastCell(MazeCell cell) {
		ArrayList<MazeCell> options = new ArrayList<MazeCell>();
		// determine if there are cells to the south and/or east
		if (cell.getEast() != null) {
			options.add(cell.getEast());
		}
		if (cell.getSouth() != null) {
			options.add(cell.getSouth());
		}
		// randomly select one s/e neighbor to link
		MazeCell selected = null;
		if (options.size() == 2) {
			int ordinal = DiceRoller.roll(1, DiceValue.D2);
			selected = options.get(ordinal-1);
		} else if (options.size() == 1) {
			selected = options.get(0);
		}
		// use selected cell to determine next jump
		if (selected != null) {
			cell.link(selected, true);
		}
	}
}
