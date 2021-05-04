package tsg.ttt.main.control.mouse;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import tsg.ttt.main.ui.UIManager;

public class MouseManager implements MouseListener, MouseMotionListener{
	//This class keeps track of the mouse state and location and stores the varibales for other classes
	
	private boolean leftPressed, rightPressed;
	private int mouseX, mouseY;
	private UIManager uiManager;
	
	public MouseManager(){
		
	}
	
	public void setUIManager(UIManager uiManager) {
		this.uiManager = uiManager;
	}
	
	//Getters
	
	public boolean isLeftPressed() {
		return leftPressed;
	}
	
	public boolean isRightPressed() {
		return rightPressed;
	}
	
	public int getMouseX() {
		return mouseX;
	}
	
	public int getMouseY() {
		return mouseY;
	}

	public void mouseDragged(MouseEvent e) {
		
		
		
	}

	public void mouseMoved(MouseEvent e) {
		//Updates Mouse Location when it moves
		mouseX = e.getX();
		mouseY = e.getY();
		
		if(uiManager != null) {
			uiManager.onMouseMove(e);
		}
		
	}

	public void mouseClicked(MouseEvent e) {

		
		
	}

	public void mouseEntered(MouseEvent e) {

		
		
	}

	public void mouseExited(MouseEvent e) {
		
		
		
	}

	public void mousePressed(MouseEvent e) {
		//Updates Button States when clicked
		if(e.getButton() == MouseEvent.BUTTON1) 
			leftPressed = true;
		else if(e.getButton() == MouseEvent.BUTTON3) 
			rightPressed = true;
		
	}

	public void mouseReleased(MouseEvent e) {
		//Updates Button States when released
		if(e.getButton() == MouseEvent.BUTTON1) 
			leftPressed = false;
		else if(e.getButton() == MouseEvent.BUTTON3) 
			rightPressed = false;
		
		if(uiManager != null) {
			uiManager.onMouseRelease(e);
		}
	}
	
}
