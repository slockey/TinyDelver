package com.tinydelver.strategy;

import java.util.ArrayList;

import com.tinydelver.creature.Actor;
import com.tinydelver.creature.CreatureFactory;
import com.tinydelver.utils.Tuple;
import com.tinydelver.utils.TupleFactory;
import com.tinydelver.world.Tile;

/**
 * Strategy for a generic fungus monster.
 * No movement, but can expand to adjacent open space.
 *
 */
public class FungusStrategy implements IStrategy {

	private final Actor fungus;
	
	private int spreadCount = 0;
	
	public FungusStrategy(Actor fungus) {
		this.fungus = fungus;
	}
	
	@Override
	public void onEnter(int x, int y, Tile tile) {
		// TODO Auto-generated method stub

	}

	@Override
	public Actor getCreature() {
		return fungus;
	}

	@Override
	public void onUpdate() {
		// fungus can only spread 5 spaces total
		if (spreadCount < 5 && Math.random() < 0.02) {
			spread();
		}
		
	}

	/**
	 * Send out some spores and take over an adjacent space if possible.
	 * 
	 */
	private void spread() {
		int currentX = fungus.getXPos();
		int currentY = fungus.getYPos();

		if (spreadCount < 5) {
			// determine empty adjacent space (if any)
			ArrayList<Tuple<Integer,Integer>> points = TupleFactory.generateCardinalPoints(currentX, currentY);
			for (int idx = 0; idx < points.size(); idx++) {
				Tuple<Integer,Integer> location = points.get(idx);
				// check world tile
				if (fungus.getWorld().getTile(location.getLeft(), location.getRight()).isWalkable()) {
					// could spread - make sure no actor
					Actor actor = fungus.getWorld().getActorAtLocation(location.getLeft(), location.getRight());
					if (actor == null) {
						Actor child = CreatureFactory.newFungusInstance(fungus.getWorld());
						child.setXPos(location.getLeft());
						child.setYPos(location.getRight());
						fungus.getWorld().addActorToWorld(child);
						spreadCount += 1;
						break;
					} else {
						// there is an actor here... infect!
						fungus.attack(actor);
						break;
					}
				}
			}
		}
	}

	@Override
	public void onNotify(String message) {
		// TODO Auto-generated method stub
		
	}
}
