package tsg.ttt.main.ui.particles;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import tsg.ttt.main.gfx.Assets;
import tsg.ttt.main.ui.UIManager;
import tsg.ttt.main.ui.UIObject;

public class explosionParticle extends UIObject{ //Black, White and Green massive particles that fade quickly 
	
	int size, lifetime, spRate, time = 0;
	float xSp, ySp;
	UIManager uiManager;
	
	public explosionParticle(float x, float y, int width, int height, int lifetime, float xSp, float ySp) {
		super(x, y, width, height, 0);
		this.lifetime = lifetime;
		this.xSp = xSp;
		this.ySp = ySp;
	}

	double rot = Math.random()*10;
	double rotC = Math.random()*10;
	int i = (int) (Math.random()*2);
	int I = (int) (Math.random()*2);
	
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
		trans.rotate(rot, x+width/2, y+height/2);
		g2d.transform(trans);
		if (lifetime > 360) {
			lifetime=360;
		}
		AlphaComposite composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)(lifetime/360.0));
		g2d.setComposite(composite);
		if (I == 0) {
			if (i == 0) 
				g2d.drawImage(Assets.wx,(int)x,(int)y,width*10,width*10, null);
			else	
				g2d.drawImage(Assets.wo,(int)x,(int)y,width*10,width*10, null);
		} else {
			if (i == 0) 
				g2d.drawImage(Assets.x,(int)x,(int)y,width*10,width*10, null);
			else	
				g2d.drawImage(Assets.o,(int)x,(int)y,width*10,width*10, null);
		}
		composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0);
		g2d.setComposite(composite);
		g2d.setTransform(backup); 
	}

	public void onClick() {}
	
	
	
}