package tsg.ttt.main.ui;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import tsg.ttt.main.gfx.Assets;
import tsg.ttt.main.ui.UIObject;

public class UIFilter extends UIObject{ //Displays a simple image with limited interactions
	
	BufferedImage image;
	private boolean doRot = false;
	private double alpha = 1;
	
	public UIFilter(float x, float y, int width, int height, BufferedImage image, double alpha) {
		super(x, y, width, height, 2);
		this.image = image;
		this.alpha = alpha;
	}

	public void tick() {}

	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		AlphaComposite composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)alpha);
		g2d.setComposite(composite);
		g2d.drawImage(image,(int)x,(int)y,width,height, null);
		composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1);
		g2d.setComposite(composite);
	}

	public double getAlpha() {
		return alpha;
	}

	public void setAlpha(double alpha) {
		this.alpha = alpha;
	}

	public void onClick() {}

	public void setImage(BufferedImage image) {
		this.image = image;
	}
}