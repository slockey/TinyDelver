package com.tinydelver.strategy;

import com.tinydelver.creature.Actor;
import com.tinydelver.world.Tile;

public interface IStrategy {

	public void onEnter(int x, int y, Tile tile);
	public void onUpdate();

	public Actor getCreature();
	
}
