package com.tinydelver.creature;

import java.util.ArrayList;

import com.tinydelver.strategy.FungusStrategy;
import com.tinydelver.strategy.NullStrategy;
import com.tinydelver.strategy.PlayerStrategy;
import com.tinydelver.utils.dice.DiceValue;
import com.tinydelver.utils.dice.HitDice;
import com.tinydelver.world.Tile;
import com.tinydelver.world.World;

public class CreatureFactory {

	public static Actor newPlayerInstance(World world, ArrayList<String> messageQueue) {

		Creature player = new Creature("0:Player", "Player", Tile.PLAYER, new HitDice(1, DiceValue.D10), world, 0, 0);

		// add AI/strategies
		PlayerStrategy strat = new PlayerStrategy(player, messageQueue);
		player.setStrategy(strat);
		// set player at random starting location
		world.determineEmptyStartLocation(player);
		world.addActorToWorld(player);

		return player;
	}
	
	public static Actor newFungusInstance(World world) {
		Creature fungus = new Creature("Fungus", Tile.FUNGUS, new HitDice(1, DiceValue.D4), world, 0, 0);
		
		FungusStrategy strat = new FungusStrategy(fungus);
		fungus.setStrategy(strat);
		
		world.determineEmptyStartLocation(fungus);
		world.addActorToWorld(fungus);
		
		return fungus;
	}

    public static Actor newRatInstance(World world) {
        Creature rat = new Creature("Rat", Tile.RAT, new HitDice(1, DiceValue.D4), world, 0, 0);

        // TODO: implement rat strategy
        NullStrategy strat = new NullStrategy();
        rat.setStrategy(strat);

        world.determineEmptyStartLocation(rat);
        world.addActorToWorld(rat);

        return rat;
    }
    
}
