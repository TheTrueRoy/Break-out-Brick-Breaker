package tsg.ttt.main.ui.particles;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import tsg.ttt.main.gfx.Assets;
import tsg.ttt.main.ui.UIManager;
import tsg.ttt.main.ui.UIObject;

public class brickParticle extends UIObject{ //Black or white particle, has velocity
	
	int size, lifetime, spRate, time = 0, health;
	BufferedImage image;
	float xSp, ySp;
	UIManager uiManager;
	
	public brickParticle(float x, float y, int width, int height, int lifetime, float xSp, float ySp, BufferedImage image) {
		super(x, y, width, height, 1);
		this.lifetime = lifetime;
		this.xSp = xSp;
		this.ySp = ySp;
		this.health = health-1;
		this.image = image;
	}

	double rot = 0;
	double rotC = (Math.random()-0.5)/2;
	int i = (int)(Math.random()*10);
	public void tick() {
		if (y > lifetime) {
			active = false;
		}
		if (health < 0) {
			health = 0;
		}
		rotC*=0.95;
		rot+=rotC;
		ySp-=0.17;
		y-=ySp;
		x-=xSp;
	}

	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform backup = g2d.getTransform();
		AffineTransform trans = new AffineTransform();
		trans.rotate(rot, x+width/2, y+height/2);
		g2d.transform(trans);
		g2d.drawImage(image,(int)x,(int)y,width,height, null);
		g2d.setTransform(backup); 
	}

	public void onClick() {}
	
	
	
}