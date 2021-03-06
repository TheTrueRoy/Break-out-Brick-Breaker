package tsg.ttt.main.control.keyboard;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener{
	//This class just checks if keys are pressed and keeps a boolean[] for other classes to check
	private boolean[] keys;
	public boolean up, down, left, right, esc, q, e, f, FisPressed, space, c, aUp, aDown, aLeft, aRight;
	
	public KeyManager() {
		keys = new boolean[600];
	}
	
	public void tick() {
		esc = keys[KeyEvent.VK_ESCAPE];
		right = keys[KeyEvent.VK_RIGHT];
		left = keys[KeyEvent.VK_LEFT];
		space = keys[KeyEvent.VK_SPACE];
	}
	
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;

	}

	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}

	public void keyTyped(KeyEvent e) {
		
	}
	
}
