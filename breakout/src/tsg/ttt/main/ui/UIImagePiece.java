package tsg.ttt.main.ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import tsg.ttt.main.ui.UIObject;

public class UIImagePiece extends UIObject{ //Like a UIImage but has a special variable called winningPiece and can basically change to a different texture
	
	BufferedImage image, winImage;
	int rotX, rotY;
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	private static boolean doRot = false;
	
	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public BufferedImage getWinImage() {
		return winImage;
	}

	public void setWinImage(BufferedImage winImage) {
		this.winImage = winImage;
	}

	double rot, rotC;
	boolean winningPiece = false;
	
	public boolean isWinningPiece() {
		return winningPiece;
	}

	public void setWinningPiece(boolean winningPiece) {
		this.winningPiece = winningPiece;
	}

	public UIImagePiece(float x, float y, int width, int height, BufferedImage image, BufferedImage winImage) {
		super(x, y, width, height, 1);
		this.image = image;
		this.winImage = winImage;
		rot = 2.358;
		rotC = 0;
	}

	public void tick() {}

	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform backup = g2d.getTransform();
		AffineTransform trans = new AffineTransform();
		if (doRot) {
			rotC+=0.01;
			rotX = screenSize.width/2;
			rotY = screenSize.height/2;
			trans.rotate(rotC, rotX, rotY);
		}
		rotX = (int) (x+width/2);
		rotY = (int) (y+height/2);
		trans.rotate(rot, rotX, rotY);
		g2d.transform(trans);
		if (!winningPiece)
			g2d.drawImage(image, (int) x, (int) y, width, height, null);
		else {
			rot+=0.01;
			g2d.drawImage(winImage, (int) x, (int) y, width, height, null);
		}
		g2d.setTransform(backup); 
	}

	public void onClick() {}
	
	public void doRot() {
		doRot = true;
	}
	
	public void stopRot() {
		doRot = false;
	}
	
}