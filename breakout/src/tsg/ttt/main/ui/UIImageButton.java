package tsg.ttt.main.ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class UIImageButton extends UIObject{ //Same thing as UIImage except if you click it, it does something and if you hover over it it changes color
	
	private BufferedImage[] images;
	private ClickListener clicker;
	private boolean keepActive;

	public UIImageButton(float x, float y, int width, int height, BufferedImage[] images, ClickListener clicker, boolean keepActive) {
		super(x, y, width, height, 1);
		this.images = images;
		this.clicker = clicker;
		this.keepActive = keepActive;
	}

	public void tick() {}

	public void render(Graphics g) {
		if(hovering)
			g.drawImage(images[1], (int) x, (int) y, width, height, null);
		else
			g.drawImage(images[0], (int) x, (int) y, width, height, null);
	}

	public void onClick() {
		active = keepActive;
		clicker.onClick();
	}
	
	
	
}
