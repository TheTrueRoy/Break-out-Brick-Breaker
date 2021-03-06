package tsg.ttt.main.ui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class UITiltImage extends UIObject{ //Like UIImage except it tilts
	
	private BufferedImage image;
	private boolean doRot = true;
	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public boolean isDoRot() {
		return doRot;
	}

	public void setDoRot(boolean doRot) {
		this.doRot = doRot;
	}

	public float getRot() {
		return rot;
	}

	public void setRot(float rot) {
		this.rot = rot;
	}

	public double getRotr() {
		return rotr;
	}

	public void setRotr(double rotr) {
		this.rotr = rotr;
	}
	
	private double rotr = 0.00025, range = 0;

	public UITiltImage(float x, float y, int width, int height, BufferedImage image, double range, double speed) {
		super(x, y, width, height, 1);
		this.image = image;
		this.rotr = speed;
		this.range = range;
	}
	
	private float rot = (float) ((Math.random()*2-1)*range);
	
	public void tick() {}

	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform backup = g2d.getTransform();
		AffineTransform trans = new AffineTransform();
		trans.rotate(rot, x+width/2, y+height/2);
		g2d.transform(trans);
		g2d.drawImage(image, (int) x, (int) y, width, height, null);
		g2d.setTransform(backup); 
		rot+=rotr;
		if (Math.abs(rot) >= range) {
		rotr = -rotr;
		}
	}

	public void onClick() {}
	
	
	
}
