package tsg.ttt.main.ui.bricks;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import tsg.ttt.main.gfx.Assets;
import tsg.ttt.main.ui.UIManager;
import tsg.ttt.main.ui.UIObject;
import tsg.ttt.main.ui.particles.brickParticle;

public class BasicBrick extends Brick{ //Displays a simple image with limited interactions
	
	private int health = 1;
	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}
	
	public void damage(int dmg) {
		this.health-=dmg;
	}

	
	public BasicBrick(float x, float y, int width, int height, int health) {
		super(x, y, width, height, 1);
		this.health = health;
	}

	public void tick() {}

	public void render(Graphics g) {
		if (health > 0)
			g.drawImage(Assets.bricks[health-1], (int) x, (int) y, width, height, null);
		else
			active = false;
	}

	public void onClick() {}
	
	public String deathRattle(UIManager uiManager, float veloX, float veloY) {
		if (this.getHealth() < 1)
			this.setHealth(1);
		uiManager.addObject(new brickParticle(x, y, width, height, 1080, veloX, veloY, Assets.bricks[this.getHealth()-1]));
		return "null";
	}
	
	public String iden() {
		return "null";
	}
	
}