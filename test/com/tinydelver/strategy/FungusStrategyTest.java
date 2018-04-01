package com.tinydelver.strategy;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import com.tinydelver.creature.Actor;
import com.tinydelver.utils.Tuple;
import com.tinydelver.utils.dice.DiceValue;
import com.tinydelver.utils.dice.IDiceRoller;
import com.tinydelver.world.Tile;
import com.tinydelver.world.World;

public class FungusStrategyTest {

    @Mock Actor fungus;
    @Mock Actor fungus2;
    @Mock Actor rat;
    @Mock World world;
    @Mock IDiceRoller diceRoller;

    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void fungusShouldSpreadToEmptyTile() throws Exception {
        // adjacent points - single point for testing
        ArrayList<Tuple<Integer, Integer>> points = new ArrayList<Tuple<Integer, Integer>>();
        points.add(new Tuple<Integer,Integer>(-1,0));

        // this will be the adjacent tile
        Tile tile = Tile.FLOOR;

        when(fungus.getXPos()).thenReturn(0);
        when(fungus.getYPos()).thenReturn(0);
        when(fungus.getWorld()).thenReturn(world);
        when(world.getTile(-1, 0)).thenReturn(tile);

        verify(world, times(0)).addActorToWorld(any(Actor.class));

        FungusStrategy strat = new FungusStrategy(fungus, diceRoller);
        Assert.assertEquals(0L, strat.getSpreadCount());
        // attempt to spread to adjacent tile
        strat.takeAction(points);
        Assert.assertEquals(1L,  strat.getSpreadCount());
    }

    @Test
    public void fungusShouldAttackActorIfAtMaxSpread() throws Exception {
        // adjacent points - single point for testing
        ArrayList<Tuple<Integer, Integer>> points = new ArrayList<Tuple<Integer, Integer>>();
        points.add(new Tuple<Integer,Integer>(-1,0));

        // this will be the adjacent tile
        Tile tile = Tile.FLOOR;

        when(fungus.getXPos()).thenReturn(0);
        when(fungus.getYPos()).thenReturn(0);
        when(fungus.getWorld()).thenReturn(world);
        when(world.getTile(-1, 0)).thenReturn(tile);
        when(world.getActorAtLocation(-1, 0)).thenReturn(rat);
        when(rat.getTile()).thenReturn(Tile.RAT);
        when(diceRoller.roll(any(Integer.class), any(DiceValue.class))).thenReturn(25);

        FungusStrategy strat = new FungusStrategy(fungus, diceRoller);
        strat.setSpreadCount(5); // this is the max spread for a given fungus
        Assert.assertEquals(5L, strat.getSpreadCount());
        // attempt to spread to adjacent tile
        strat.takeAction(points);

        verify(fungus, times(1)).attack(rat);
    }

    @Test
    public void fungusShouldNotAttackFungusActor() throws Exception {
        // adjacent points - single point for testing
        ArrayList<Tuple<Integer, Integer>> points = new ArrayList<Tuple<Integer, Integer>>();
        points.add(new Tuple<Integer,Integer>(-1,0));

        // this will be the adjacent tile
        Tile tile = Tile.FLOOR;

        when(fungus.getXPos()).thenReturn(0);
        when(fungus.getYPos()).thenReturn(0);
        when(fungus.getWorld()).thenReturn(world);
        when(world.getTile(-1, 0)).thenReturn(tile);
        when(world.getActorAtLocation(-1, 0)).thenReturn(fungus2);
        when(fungus2.getTile()).thenReturn(Tile.FUNGUS);

        FungusStrategy strat = new FungusStrategy(fungus, diceRoller);
        Assert.assertEquals(0L, strat.getSpreadCount());
        // attempt to spread to adjacent tile
        strat.takeAction(points);
        Assert.assertEquals(0L,  strat.getSpreadCount());

        verify(fungus, times(0)).attack(fungus2);
    }

    @Test
    public void fungusShouldAttackNonFungusActor() throws Exception {
        // adjacent points - single point for testing
        ArrayList<Tuple<Integer, Integer>> points = new ArrayList<Tuple<Integer, Integer>>();
        points.add(new Tuple<Integer,Integer>(-1,0));

        // this will be the adjacent tile
        Tile tile = Tile.FLOOR;

        when(fungus.getXPos()).thenReturn(0);
        when(fungus.getYPos()).thenReturn(0);
        when(fungus.getWorld()).thenReturn(world);
        when(world.getTile(-1, 0)).thenReturn(tile);
        when(world.getActorAtLocation(-1, 0)).thenReturn(rat);
        when(rat.getTile()).thenReturn(Tile.RAT);
        when(diceRoller.roll(any(Integer.class), any(DiceValue.class))).thenReturn(25);

        FungusStrategy strat = new FungusStrategy(fungus, diceRoller);
        Assert.assertEquals(0L, strat.getSpreadCount());
        // attempt to spread to adjacent tile
        strat.takeAction(points);
        Assert.assertEquals(0L,  strat.getSpreadCount());

        verify(fungus, times(1)).attack(rat);
    }
}
