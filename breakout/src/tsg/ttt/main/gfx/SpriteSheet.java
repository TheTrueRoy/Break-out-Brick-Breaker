package tsg.ttt.main.gfx;

import java.awt.image.BufferedImage;

public class SpriteSheet {
	//Makes it simpler to divide spritesheets
	private BufferedImage sheet;
	
	public SpriteSheet(BufferedImage sheet) {
		this.sheet = sheet;
	
	}
	
	public BufferedImage crop(int x, int y, int w, int h) {
		return sheet.getSubimage(x, y, w, h);
	}
	
}
