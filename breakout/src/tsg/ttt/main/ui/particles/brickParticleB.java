package tsg.ttt.main.ui.particles;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import tsg.ttt.main.gfx.Assets;
import tsg.ttt.main.ui.UIManager;
import tsg.ttt.main.ui.UIObject;

public class brickParticleB extends UIObject{ //Black or white particle, has velocity
	
	int size, lifetime, spRate, time = 0;
	float xSp, ySp;
	UIManager uiManager;
	
	public brickParticleB(float x, float y, int width, int height, int lifetime, float xSp, float ySp) {
		super(x, y, width, height, 0);
		this.lifetime = lifetime;
		this.xSp = xSp;
		this.ySp = ySp;
	}

	double rot = Math.random()*0;
	int i = (int)(Math.random()*10);
	
	public void tick() {
		if (y > lifetime) {
			active = false;
		}
		y-=ySp;
		x-=xSp;
	}

	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform backup = g2d.getTransform();
		AffineTransform trans = new AffineTransform();
		trans.rotate(rot, x+width*5, y+height*5);
		g2d.transform(trans);
		AlphaComposite composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) 0.3);
		g2d.setComposite(composite);
		g2d.drawImage(Assets.bricks[i],(int)x-5*width,(int)y-5*height,10*width,10*height, null);
		composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1);
		g2d.setComposite(composite);
		g2d.setTransform(backup); 
	}

	public void onClick() {}
	
	
	
}