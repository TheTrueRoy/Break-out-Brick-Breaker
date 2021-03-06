package tsg.ttt.main.ui.bricks;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import tsg.ttt.main.gfx.Assets;
import tsg.ttt.main.ui.UIManager;
import tsg.ttt.main.ui.UIObject;
import tsg.ttt.main.ui.particles.starParticle;

public class PowerBrick extends Brick{ //Displays a simple image with limited interactions
	
	private int health = 1, color;
	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}
	
	public void damage(int dmg) {
		this.health-=dmg;
	}

	
	public PowerBrick(float x, float y, int width, int height, int health) {
		super(x, y, width, height, 1);
		this.health = health;
		color = (int)(Math.random()*10);
	}

	public void tick() {}

	public void render(Graphics g) {
		if (health > 0)
			g.drawImage(Assets.bricksCOLOR[color], (int) x, (int) y, width, height, null);
		else
			active = false;
	}

	public void onClick() {}
	
	public String deathRattle(UIManager uiManager, float veloX, float veloY) {
		for(int i = 0; i < 50; i++) {
			int wid = (int)(Math.random()*40+10);
			uiManager.addObject(new starParticle((int)(x-wid/2+Math.random()*width), (int)(y-wid/2+Math.random()*height), wid, wid, (int)(Math.random()*40)+80, 120, 2*(float)Math.random()-1f, 2*(float)Math.random()-1f));
		}
		return "power";
	}
	
	public String iden() {
		return "power";
	}
	
}