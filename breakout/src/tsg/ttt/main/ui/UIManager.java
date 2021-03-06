package tsg.ttt.main.ui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;

import tsg.ttt.main.Handler;

public class UIManager { //Manages the UI and keeps the arrays from being concurrently modified because that was crashing my game

	private Handler handler;
	private ArrayList<UIObject> objects, newObjects;
	boolean doMouseRelease;
	MouseEvent mm = null, mr = null;
	
	public UIManager(Handler handler) {
		this.handler = handler;
		objects = new ArrayList<UIObject>();
		newObjects = new ArrayList<UIObject>();
	}
	
	public void tick() {
		objects.addAll(newObjects);
		newObjects.clear();
		Iterator<UIObject> it = objects.iterator();
		while(it.hasNext()) {
			UIObject e = it.next();
			if (e.isShown()) {
				if (mm != null) {
					e.onMouseMove(mm);
				}
				if (mr != null) {
					e.onMouseRelease(mr);
				}
				e.tick();
			}
			if(!e.isActive()) {
				it.remove();
			}
		}
		mm = null;
		mr = null;
	}
	
	public void render(Graphics g) {
		Iterator<UIObject> it = objects.iterator();
		while(it.hasNext()) {
			UIObject e = it.next();
			if (e.getPriority() == 0) 
				if (e.isShown()) 
					e.render(g);
			
		}
		it = objects.iterator();
		while(it.hasNext()) {
			UIObject e = it.next();
			if (e.getPriority() == 1) 
				if (e.isShown()) 
					e.render(g);
			
		}
		it = objects.iterator();
		while(it.hasNext()) {
			UIObject e = it.next();
			if (e.getPriority() >= 2) 
				if (e.isShown()) 
					e.render(g);
			
		}
	}
	
	public ArrayList<UIObject> getNewObjects() {
		return newObjects;
	}

	public void setNewObjects(ArrayList<UIObject> newObjects) {
		this.newObjects = newObjects;
	}

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public ArrayList<UIObject> getObjects() {
		return objects;
	}

	public void setObjects(ArrayList<UIObject> objects) {
		this.objects = objects;
	}

	public void onMouseMove(MouseEvent e) {
		mm = e;
	}
	
	public void onMouseRelease(MouseEvent e) {
		mr = e;
	}
	
	public void addObject(UIObject o) {
		newObjects.add(o);
	}
	
	public void removeObject(UIObject o) {
		objects.remove(o);
	}
	
}
