package com.tinydelver.creature;

import com.tinydelver.strategy.IStrategy;
import com.tinydelver.utils.dice.HitDice;
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
	
	public HitDice getHitDice();
	public int getHitPoints();
	public int getTotalHitPoints();
	
	public void moveBy(int x, int y);
	public void dig(int x, int y);
	public void attack(Actor actor);
	public void takeDamage(int damage);
	public void healDamage(int damage);
	
	public void onUpdate();
	
}
