package tsg.ttt.main.ui.particles;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;

import tsg.ttt.main.gfx.Assets;
import tsg.ttt.main.ui.UIManager;
import tsg.ttt.main.ui.UIObject;

public class coinParticle extends UIObject{ //Black or white particle that has a coin value associated and three possible icons, has velocity
	
	int size, lifetime, time = 0, value = 1;
	UIManager uiManager;
	
	public coinParticle(float x, float y, int width, int height, int lifetime, int value) {
		super(x, y, width, height, 0);
		this.lifetime = lifetime;
		this.value = value;
	}

	double rot = Math.random()*10;
	int i = (int) (Math.random()*2);
	
	public void tick() {
		lifetime--;
		if (lifetime < 0) {
			active = false;
		}
		y -= 2; 
	}

	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		AlphaComposite composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)(lifetime/180.0));
		g2d.setComposite(composite);
		if (value == 1)
			g2d.drawImage(Assets.onecoin,(int)x,(int)y,width,height, null);
		else if (value == 10)
			g2d.drawImage(Assets.tencoin,(int)x,(int)y,width,height, null);
		else if (value == 20)
			g2d.drawImage(Assets.twentycoin,(int)x,(int)y,width,height, null);
		composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0);
		g2d.setComposite(composite);
	}

	public void onClick() {}
	
	
	
}