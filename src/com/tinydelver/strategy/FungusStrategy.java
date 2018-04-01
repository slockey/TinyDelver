package com.tinydelver.strategy;

import java.util.ArrayList;

import com.tinydelver.creature.Actor;
import com.tinydelver.creature.CreatureFactory;
import com.tinydelver.utils.Tuple;
import com.tinydelver.utils.TupleFactory;
import com.tinydelver.utils.dice.DiceRoller;
import com.tinydelver.utils.dice.DiceValue;
import com.tinydelver.utils.dice.IDiceRoller;
import com.tinydelver.world.Tile;

/**
 * Strategy for a generic fungus monster.
 * No movement, but can expand to adjacent open space.
 *
 */
public class FungusStrategy implements IStrategy {

    private final Actor fungus;
    private final IDiceRoller diceRoller;
    private final int MAX_SPREAD = 5;

    private int spreadCount = 0;

    public FungusStrategy(Actor fungus) {
        this(fungus, DiceRoller.getInstance());
    }
    
    protected FungusStrategy(Actor fungus, IDiceRoller diceRoller) {
        this.fungus = fungus;
        this.diceRoller = diceRoller;
    }

    protected int getSpreadCount() {
        return spreadCount;
    }

    protected void setSpreadCount(int spreadCount) {
        this.spreadCount = spreadCount;
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
        if (spreadCount < MAX_SPREAD && (diceRoller.roll(1, DiceValue.D100) <= 15)) {
            takeAction();
        }
        
    }

    /**
     * Send out some spores and take over an adjacent space if possible, or attack/infect
     * hostile actors.
     */
    private void takeAction() {
        // determine adjacent space (if any)
        ArrayList<Tuple<Integer,Integer>> points = TupleFactory.generateCardinalPoints(fungus.getXPos(), fungus.getYPos());
        takeAction(points);

    }

    protected void takeAction(ArrayList<Tuple<Integer, Integer>> points) {
        ArrayList<Tuple<Integer,Integer>> spreadable = new ArrayList<Tuple<Integer,Integer>>();
        ArrayList<Actor> attackable = new ArrayList<Actor>();

        for (int idx = 0; idx < points.size(); idx++) {
            Tuple<Integer,Integer> location = points.get(idx);
            // check world tile
            if (fungus.getWorld().getTile(location.getLeft(), location.getRight()).isWalkable()) {
                // could spread - make sure no actor
                Actor actor = fungus.getWorld().getActorAtLocation(location.getLeft(), location.getRight());
                if (actor == null && spreadCount < MAX_SPREAD) {
                    spreadable.add(location);
                } else {
                    // attack anything that's not a fungus
                    if (!actor.getTile().equals(Tile.FUNGUS)) {
                        attackable.add(actor);
                    }
                }
            }
        }

        // determine action to take - favor spread over attack
        if (spreadable.size() > 0){
            createChildAtLocation(spreadable.get(0));
        } else if ((attackable.size() > 0) && (diceRoller.roll(1, DiceValue.D100) <= 25)) {
            // attack the first attackable actor
            fungus.attack(attackable.get(0));
        }
    }

    private void createChildAtLocation(Tuple<Integer, Integer> location) {
        Actor child = CreatureFactory.newFungusInstance(fungus.getWorld());
        child.setXPos(location.getLeft());
        child.setYPos(location.getRight());
        fungus.getWorld().addActorToWorld(child);
        spreadCount += 1;
    }

    @Override
    public void onNotify(String message) {
        // TODO: put messages on the message queue
    }
}
