package tsg.ttt.main.ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class UIImageRotarySwitch extends UIObject{ //Like UIImageButton except it doesnt change textures when you hover, instead it changes when you click it (Toggle)
	
	private BufferedImage[] images;
	private ClickListener clicker;
	private int state, stateCap;

	public UIImageRotarySwitch(float x, float y, int width, int height, BufferedImage[] images, ClickListener clicker, int state, int stateCap) {
		super(x, y, width, height, 1);
		this.images = images;
		this.clicker = clicker;
		this.state = state;
		this.stateCap = stateCap;
	}
		
	
	public void tick() {}

	public void render(Graphics g) {
		if (hovering)
			g.drawImage(images[state*2+1], (int) x, (int) y, width, height, null);
		else
			g.drawImage(images[state*2], (int) x, (int) y, width, height, null);
	}

	public void onClick() {
		state++;
		state%=stateCap;
		clicker.onClick();
	}
	
	
	
}
