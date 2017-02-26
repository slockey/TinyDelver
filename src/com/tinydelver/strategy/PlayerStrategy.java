package com.tinydelver.strategy;

import com.tinydelver.creature.Actor;
import com.tinydelver.world.Tile;

public class PlayerStrategy implements IStrategy {

	private final Actor player;
	
	public PlayerStrategy(Actor player) {
		this.player = player;
	}
	
	@Override
	public void onEnter(int x, int y, Tile tile) {
		
		if (tile.isWalkable()){
	        player.setXPos(x);
	        player.setYPos(y);
	    } else if (tile.isDiggable()) {
			player.dig(x, y);
		}
	}

	public Actor getCreature() {
		return player;
	}

	@Override
	public void onUpdate() {
		// TODO Auto-generated method stub
		
	}

}
