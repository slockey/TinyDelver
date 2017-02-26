package com.tinydelver.creature;

import com.tinydelver.strategy.IStrategy;
import com.tinydelver.world.Tile;
import com.tinydelver.world.World;

public interface Actor {

	public String getId();
	public String getName();
	public Tile getTile();
	public IStrategy getStrategy();
	
	public World getWorld();
	public int getXPos();
	public int getYPos();
	public void setXPos(int x);
	public void setYPos(int y);
	
	public void moveBy(int x, int y);
	public void dig(int x, int y);
	public void attack(Actor actor);
	
	public void onUpdate();
	
}
