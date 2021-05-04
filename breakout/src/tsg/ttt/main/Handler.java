package tsg.ttt.main;

import tsg.ttt.main.control.keyboard.KeyManager;
import tsg.ttt.main.control.mouse.MouseManager;

public class Handler {
	
	//Stores certain variables and classes that are referenced often
	
	private Game game;
	
	public Handler(Game game) {
		this.game = game;
	}

	public int getWidth() {
		return game.getWidth();
	}
	
	public int getHeight() {
		return game.getHeight();
	}
	
	public KeyManager getKeyManager() {
		return game.getKeyManager();
	}
	
	public MouseManager getMouseManager() {
		return game.getMouseManager();
	}
	
	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}
}
