package tsg.ttt.main.ui;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

public abstract class UIObject { //An abstract UI that everything else emulates
	
	protected float x, y;
	protected int width, height;
	protected Rectangle bounds;
	protected boolean hovering = false;
	protected boolean active = true;
	protected boolean shown = true;
	protected int prio = 0;
	
	public int getPriority() {
		return prio;
	}
	
	public boolean isShown() {
		return shown;
	}

	public void setShown(boolean shown) {
		this.shown = shown;
	}

	public boolean isActive() {
		return active;
	}

	public int getPrio() {
		return prio;
	}

	public void setPrio(int prio) {
		this.prio = prio;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public UIObject(float x, float y, int width, int height, int prio) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.prio = prio;
		bounds = new Rectangle((int) x, (int) y, width, height);
	}
	
	public abstract void tick();
	
	public abstract void render(Graphics g);
	
	public abstract void onClick();
	
	public void onMouseMove(MouseEvent e) {
		if(bounds.contains(e.getX(), e.getY())){
			hovering = true;
		}
		else {
			hovering = false;
		}
	}
	
	public void onMouseRelease(MouseEvent e) {
		if(hovering && shown)
			onClick();
	}
	
	//Get and set
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
		bounds = new Rectangle((int) x, (int) y, width, height);
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
		bounds = new Rectangle((int) x, (int) y, width, height);
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
		bounds = new Rectangle((int) x, (int) y, width, height);
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
		bounds = new Rectangle((int) x, (int) y, width, height);
	}

	public boolean isHovering() {
		return hovering;
	}

	public void setHovering(boolean hovering) {
		this.hovering = hovering;
	}
}
