package com.tinydelver.creature;

import java.util.UUID;

import com.tinydelver.strategy.IStrategy;
import com.tinydelver.strategy.NullStrategy;
import com.tinydelver.utils.dice.DiceValue;
import com.tinydelver.utils.dice.HitDice;
import com.tinydelver.world.Tile;
import com.tinydelver.world.World;


public class Creature implements Actor {

	private final String id;
	private final String name;
	private final Tile tile;

	private World world = null;
	private int xPos = 0;
	private int yPos = 0;

	private HitDice hitDice = new HitDice(0, DiceValue.D0);
	private int hitPoints = 0;
	private int totalHitPoints = 0;
	
	private IStrategy strategy = new NullStrategy();

	public Creature(String id, String name, Tile tile) {
		this.id = id;
		this.name = name;
		this.tile = tile;
	}

	public Creature(String name, Tile tile, World world, int xPos, int yPos) {
		this(UUID.randomUUID().toString(), name, tile, world, xPos, yPos);
	}

	public Creature(String id, String name, Tile tile, World world, int xPos, int yPos) {
		this(id, name, tile);
		this.world = world;
		this.xPos = xPos;
		this.yPos = yPos;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Tile getTile() {
		return tile;
	}

	@Override
	public IStrategy getStrategy() {
		return strategy;
	}

	public void setStrategy(IStrategy strategy) {
		this.strategy = strategy;
	}
	
	@Override
	public int getXPos() {
		return xPos;
	}
	
	@Override
	public void setXPos(int x) {
		this.xPos = x;
	}

	@Override
	public int getYPos() {
		return yPos;
	}

	@Override
	public void setYPos(int y) {
		this.yPos = y;
	}
	
	@Override
	public World getWorld() {
		return world;
	}

	@Override
	public HitDice getHitDice() {
		return hitDice;
	}

	@Override
	public int getHitPoints() {
		return hitPoints;
	}
	
	@Override
	public int getTotalHitPoints() {
		return totalHitPoints;
	}

	@Override
	public void moveBy(int x, int y) {
		int targetX = getXPos() + x;
		int targetY =  getYPos() + y;

		Actor actor = world.getActorAtLocation(targetX, targetY);
		if (actor != null) {
			attack(actor);
		} else {
			strategy.onEnter(targetX, targetY, world.getTile(targetX, targetY));
		}
	}

	@Override
	public void dig(int x, int y) {
		world.dig(x, y);
	}

	@Override
	public void attack(Actor actor) {
		// for the moment an attack removes the attacked actor from the world
		// ie: insta-kill
		world.removeActorFromWorld(actor);
	}

	@Override
	public void takeDamage(int damage) {
		hitPoints =- damage;
	}

	@Override
	public void onUpdate() {
		strategy.onUpdate();
	}

}
