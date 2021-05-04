package tsg.ttt.main.ui.particles;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import tsg.ttt.main.gfx.Assets;
import tsg.ttt.main.ui.UIManager;
import tsg.ttt.main.ui.UIObject;

public class brickParticle2 extends UIObject{ //Black or white particle, has velocity
	
	int size, lifetime, spRate, time = 0, health;
	double heightR, widthR;
	float xSp, ySp;
	UIManager uiManager;
	
	public brickParticle2(float x, float y, int width, int height, int lifetime, float xSp, float ySp, int health) {
		super(x, y, width, height, 1);
		this.lifetime = lifetime;
		this.xSp = xSp;
		this.ySp = ySp;
		this.health = health-1;
		widthR = 0;
		heightR = 0;
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
		ySp-=0.06;
		y-=ySp;
		x-=xSp;
		if (widthR < width) {
			widthR+=2;
		}
		if (heightR < height) {
			heightR+=2.0*height/width;
		}
	}

	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform backup = g2d.getTransform();
		AffineTransform trans = new AffineTransform();
		trans.rotate(rot, x+widthR/2, y+heightR/2);
		g2d.transform(trans);
		g2d.drawImage(Assets.bricks[health],(int)x,(int)y,(int)widthR,(int)heightR, null);
		g2d.setTransform(backup); 
	}

	public void onClick() {}
	
	
	
}