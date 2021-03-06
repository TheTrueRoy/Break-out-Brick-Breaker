package tsg.ttt.main.ui.particles;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import tsg.ttt.main.gfx.Assets;
import tsg.ttt.main.ui.UIManager;
import tsg.ttt.main.ui.UIObject;

public class starParticle extends UIObject{ //Black or white particle, has velocity
	
	int size, lifetime, maxL, spRate, time = 0;
	float xSp, ySp;
	UIManager uiManager;
	
	public starParticle(float x, float y, int width, int height, int lifetime, int maxL, float xSp, float ySp) {
		super(x, y, width, height, 1);
		this.lifetime = lifetime;
		this.maxL = maxL;
		this.xSp = xSp;
		this.ySp = ySp;
	}

	double rot = Math.random()*10;
	
	public void tick() {
		lifetime--;
		if (lifetime < 0) {
			active = false;
		}
		y-=ySp;
		x-=xSp;
	}

	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform backup = g2d.getTransform();
		AffineTransform trans = new AffineTransform();
		trans.rotate(rot, x+width/2, y+width/2);
		g2d.transform(trans);
		AlphaComposite composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)(lifetime)/maxL/2);
		g2d.setComposite(composite);
		g2d.drawImage(Assets.x,(int)x,(int)y,width,width, null);
		composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1);
		g2d.setComposite(composite);
		g2d.setTransform(backup); 
	}

	public void onClick() {}
	
	
}