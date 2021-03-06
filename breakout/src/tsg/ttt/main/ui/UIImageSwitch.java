package tsg.ttt.main.ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class UIImageSwitch extends UIObject{ //Like UIImageButton except it doesnt change textures when you hover, instead it changes when you click it (Toggle)
	
	private BufferedImage[] images;
	private ClickListener clicker;
	private int onOff;

	public UIImageSwitch(float x, float y, int width, int height, BufferedImage[] images, ClickListener clicker, boolean isOn) {
		super(x, y, width, height, 1);
		this.images = images;
		this.clicker = clicker;
		if (isOn)
			onOff = 1;
	}
		
	
	public void tick() {}

	public void render(Graphics g) {
			g.drawImage(images[onOff], (int) x, (int) y, width, height, null);
	}

	public void onClick() {
		onOff = -onOff+1;
		clicker.onClick();
	}
	
	
	
}
