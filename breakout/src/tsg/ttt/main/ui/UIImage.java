package tsg.ttt.main.ui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import tsg.ttt.main.ui.UIObject;

public class UIImage extends UIObject{ //Displays a simple image with limited interactions
	
	BufferedImage image;
	private boolean doRot = false;
	private double rotC = 0;
	private boolean visible = true;
	
	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public UIImage(float x, float y, int width, int height, BufferedImage image) {
		super(x, y, width, height, 1);
		this.image = image;
	}

	public void tick() {}

	public void render(Graphics g) {
		if (visible) {
			g.drawImage(image, (int) x, (int) y, width, height, null);
		}
	}

	public void onClick() {}
	
	public void doRot() {
		doRot = true;
	}
	
	public void stopRot() {
		doRot = false;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}
}