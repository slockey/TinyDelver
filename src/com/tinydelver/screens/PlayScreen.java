package com.tinydelver.screens;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import com.tinydelver.creature.Actor;
import com.tinydelver.creature.CreatureFactory;
import com.tinydelver.world.Tile;
import com.tinydelver.world.World;
import com.tinydelver.world.CaveWorldBuilder;

import asciiPanel.AsciiPanel;

public class PlayScreen implements Screen {

	private World world;
	private int screenWidth;
	private int screenHeight;

	private ArrayList<String> messages;

	private Actor player;
	
	public PlayScreen() {
		screenWidth = 80;
		screenHeight = 21;
		messages = new ArrayList<String>();
		createWorld();
		player = CreatureFactory.newPlayerInstance(world, messages);
	}
	
	private void createWorld() {
		CaveWorldBuilder builder = new CaveWorldBuilder(31, 90);
		world = builder.buildWorld();
	}
	
	public int getScrollX() {
		return Math.max(0, Math.min(player.getXPos() - screenHeight / 2, world.getHeight() - screenHeight));
	}
	
	public int getScrollY() {
		return Math.max(0, Math.min(player.getYPos() - screenWidth / 2, world.getWidth() - screenWidth));
	}
	
	private void displayTiles(AsciiPanel terminal, int top, int left) {
		// write the world tiles
		for (int idx = 0; idx < screenHeight; idx++) {
			for (int idy = 0; idy < screenWidth; idy++) {
				int wx = idx + top;
				int wy = idy + left;
				Tile tile = world.getTile(wx, wy);
				// ascii panel uses x axis for width and y axis for height
				terminal.write(tile.getGlyph(), idy, idx, tile.getColor());
				
				// TODO: this is inefficient. we should only render something that is visible
				Actor actor = world.getActorAtLocation(wx, wy);
				if ( actor != null) {
					terminal.write(actor.getTile().getGlyph(), 
							   actor.getYPos() - left, 
							   actor.getXPos() - top, 
							   actor.getTile().getColor());
				}
			}
		}
	}
	
	@Override
	public void displayOutput(AsciiPanel terminal) {
		int top = getScrollX();
		int left = getScrollY();
		// show the tiles
		displayTiles(terminal, top, left);
		// show the message queue
		displayMessages(terminal);
		// show some player stats
		String stats = String.format(" %3d/%3d hp", player.getHitPoints(), player.getTotalHitPoints());
	    terminal.write(stats, 1, 23);
	}

	/**
	 * Display queued messages for the user then clear the queue.
	 * @param terminal
	 */
	private void displayMessages(AsciiPanel terminal) {
	    int top = screenHeight - messages.size();
	    for (int i = 0; i < messages.size(); i++){
	        terminal.writeCenter(messages.get(i), top + i);
	    }
	    messages.clear();
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
	        	player.moveBy(0, -1);
	        	break;
	        case KeyEvent.VK_RIGHT:
	        case KeyEvent.VK_L: 
	        	player.moveBy(0, 1);
	        	break;
	        case KeyEvent.VK_UP:
	        case KeyEvent.VK_K:
	        	player.moveBy(-1, 0);
	        	break;
	        case KeyEvent.VK_DOWN:
	        case KeyEvent.VK_J:
	        	player.moveBy(1, 0);
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
