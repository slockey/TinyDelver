package com.tinydelver.world.maze;

import java.util.ArrayList;

import com.tinydelver.utils.dice.DiceRoller;
import com.tinydelver.utils.dice.DiceValue;

public class SidewinderMazeStrategy implements MazeStrategy {

	@Override
	public MazeCell[][] generateMaze(MazeCell[][] original, int height, int width) {

		for (int idx = 0; idx < height; idx++) {
			ArrayList<MazeCell> run = new ArrayList<MazeCell>();
			
			for (int idy = 0; idy < width; idy++) {
				run.add(original[idx][idy]);
				
				boolean atEasternBound = (original[idx][idy].getEast() == null);
				boolean atSouthBound = (original[idx][idy].getSouth() == null);
				
				boolean shouldCloseOut = atEasternBound || (!atSouthBound && DiceRoller.roll(1, DiceValue.D2) == 2);
				
				if (shouldCloseOut) {
					MazeCell cell = run.get((int)(Math.random() * run.size()-1));
					if (cell.getSouth() != null) {
						cell.link(cell.getSouth(), true);
					}
				} else {
					original[idx][idy].link(original[idx][idy].getEast(), true);
				}
			}
		}
		return original;
	}

}
