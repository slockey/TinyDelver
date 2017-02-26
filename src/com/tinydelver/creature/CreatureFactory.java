package com.tinydelver.creature;

import com.tinydelver.strategy.FungusStrategy;
import com.tinydelver.strategy.PlayerStrategy;
import com.tinydelver.world.Tile;
import com.tinydelver.world.World;

public class CreatureFactory {

	public static Actor newPlayerInstance(World world) {

		Creature player = new Creature("0:Player", "Player", Tile.PLAYER, world, 0, 0);

		// add AI/strategies
		PlayerStrategy strat = new PlayerStrategy(player);
		player.setStrategy(strat);
		// set player at random starting location
		world.determineEmptyStartLocation(player);
		world.addActorToWorld(player);

		return player;
	}
	
	public static Actor newFungusInstance(World world) {
		Creature fungus = new Creature("Fungus", Tile.FUNGUS, world, 0, 0);
		
		FungusStrategy strat = new FungusStrategy(fungus);
		fungus.setStrategy(strat);
		
		world.determineEmptyStartLocation(fungus);
		world.addActorToWorld(fungus);
		
		return fungus;
	}
}
