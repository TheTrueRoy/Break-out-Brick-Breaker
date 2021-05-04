package tsg.ttt.main;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.MemoryImageSource;

public class Launcher {
	public static void main(String[] args) {
		//Launches the game by starting a Game Class
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Game ttt = new Game("Break-OUT", screenSize.width, screenSize.height);
		ttt.start();
	}
}
