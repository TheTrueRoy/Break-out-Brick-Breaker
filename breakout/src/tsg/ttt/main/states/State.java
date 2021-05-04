package tsg.ttt.main.states;

import java.awt.Graphics;

import tsg.ttt.main.Handler;

public abstract class State { //The abstract mold for all the states to come out of
	
	private static State currentState = null;
	
	public static void setState(State state) {
		currentState = state;
		state.init();
	}
	
	public static State getState() {
		return currentState; 
	}
	
	//CLASS
	
	protected Handler handler;
	
	public State(Handler handler) {
		this.handler = handler;
	}
	
	public abstract void init();
	
	public abstract void tick();
	
	public abstract void render(Graphics g);

}
