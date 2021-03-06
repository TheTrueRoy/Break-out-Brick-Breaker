package tsg.ttt.main.display;

import java.awt.Canvas;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.MemoryImageSource;

import javax.swing.JFrame;

public class Display {
	
	private JFrame window;
	private Canvas canvas;
	
	private String title;
	private int width, height;
	
	public Display(String title, int width, int height) {
		this.title = title;
		this.width = width;
		this.height = height;
		
		createDisplay();
	}
	
	private void createDisplay() {
		//Creates a new JFrame with a canvas inside when called
		window = new JFrame(title);
		window.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		window.setUndecorated(true);
		window.setVisible(true);
		window.setTitle(title);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(width,height));
		canvas.setMaximumSize(new Dimension(width,height));
		canvas.setMinimumSize(new Dimension(width,height));
		canvas.setFocusable(false);
		window.add(canvas);
		window.pack();

		int[] pixels = new int[16 * 16];
		Image image = Toolkit.getDefaultToolkit().createImage(
		        new MemoryImageSource(16, 16, pixels, 0, 16));
		Cursor transparentCursor =
		        Toolkit.getDefaultToolkit().createCustomCursor
		             (image, new Point(0, 0), "invisibleCursor");
		window.setCursor(transparentCursor);
	}
	
	public Canvas getCanvas(){
		return canvas;		
	}
	
	public JFrame getFrame() {
		return window;
	}
	
	public void setCursor(Cursor cursor) {
		window.setCursor(cursor);
	}
	
}
