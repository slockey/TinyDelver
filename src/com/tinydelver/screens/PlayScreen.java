package com.tinydelver.screens;

import java.awt.event.KeyEvent;

import com.tinydelver.creature.Actor;
import com.tinydelver.creature.CreatureFactory;
import com.tinydelver.world.Tile;
import com.tinydelver.world.World;
import com.tinydelver.world.WorldBuilder;

import asciiPanel.AsciiPanel;

public class PlayScreen implements Screen {

	private World world;
	private int screenWidth;
	private int screenHeight;

	private Actor player;
	
	public PlayScreen() {
		screenWidth = 80;
		screenHeight = 21;
		createWorld();
		player = CreatureFactory.newPlayerInstance(world);
	}
	
	private void createWorld() {
		WorldBuilder builder = new WorldBuilder(90, 31);
		world = builder.build();
	}
	
	public int getScrollX() {
		return Math.max(0, Math.min(player.getXPos() - screenWidth / 2, world.getWidth() - screenWidth));
	}
	
	public int getScrollY() {
		return Math.max(0, Math.min(player.getYPos() - screenHeight / 2, world.getHeight() - screenHeight));
	}
	
	private void displayTiles(AsciiPanel terminal, int left, int top) {
		// write the world tiles
		for (int idx = 0; idx < screenWidth; idx++) {
			for (int idy = 0; idy < screenHeight; idy++) {
				int wx = idx + left;
				int wy = idy + top;
				Tile tile = world.getTile(wx, wy);
				terminal.write(tile.getGlyph(), idx, idy, tile.getColor());
				
				// TODO: this is inefficient. we should only render something that is visible
				Actor actor = world.getActorAtLocation(wx, wy);
				if ( actor != null) {
					terminal.write(actor.getTile().getGlyph(), 
							   actor.getXPos() - left, 
							   actor.getYPos() - top, 
							   actor.getTile().getColor());
				}
			}
		}
	}
	
	@Override
	public void displayOutput(AsciiPanel terminal) {
		int left = getScrollX();
		int top = getScrollY();
		displayTiles(terminal, left, top);
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {

		switch (key.getKeyCode()) {
			case KeyEvent.VK_ESCAPE:
				return new LoseScreen();
			case KeyEvent.VK_ENTER:
				return new WinScreen();
			case KeyEvent.VK_LEFT:
	        case KeyEvent.VK_H:
	        	player.moveBy(-1, 0);
	        	break;
	        case KeyEvent.VK_RIGHT:
	        case KeyEvent.VK_L: 
	        	player.moveBy(1, 0);
	        	break;
	        case KeyEvent.VK_UP:
	        case KeyEvent.VK_K:
	        	player.moveBy(0, -1);
	        	break;
	        case KeyEvent.VK_DOWN:
	        case KeyEvent.VK_J:
	        	player.moveBy(0, 1);
	        	break;
	        case KeyEvent.VK_SPACE:
	        	// space == pass
	        	break;
	        case KeyEvent.VK_Y:
	        case KeyEvent.VK_U:
	        case KeyEvent.VK_B:
	        case KeyEvent.VK_N:
	        	break;
        	default:
        		break;
        }

		// make the world update
    	world.update();

		return this;
	}

}
