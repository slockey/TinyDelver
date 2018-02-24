package com.tinydelver.strategy;

import com.tinydelver.creature.Actor;
import com.tinydelver.world.Tile;

public class NullStrategy implements IStrategy {

	@Override
	public void onEnter(int x, int y, Tile tile) {
		// do nothing
	}

	@Override
	public Actor getCreature() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onUpdate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNotify(String message) {
		// TODO Auto-generated method stub
		
	}

}
