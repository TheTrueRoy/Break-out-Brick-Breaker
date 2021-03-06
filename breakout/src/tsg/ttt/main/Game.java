package tsg.ttt.main;

import java.awt.Color;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import tsg.ttt.main.control.keyboard.KeyManager;
import tsg.ttt.main.control.mouse.MouseManager;
import tsg.ttt.main.display.Display;
import tsg.ttt.main.gfx.Assets;
import tsg.ttt.main.states.BrickState;
import tsg.ttt.main.states.ShopState;
import tsg.ttt.main.states.MenuState;
import tsg.ttt.main.states.State;
import tsg.ttt.main.utils.Utils;

public class Game implements Runnable {
	
	
	//Random Variables
	private static Display display;
	private int width, height;
	private static int gold;
	private static boolean[] purchases = new boolean[8];
	private static String activeSkin;
	private static String activeAnim;
	public String title;
	
	private static boolean cpuActive = false;

	private static boolean counterOn = false;
	
	private static int difficulty = 0;

	private boolean running = false;
	private static boolean easyBot = false;
	private Thread thread;
	
	private BufferStrategy bs;
	private Graphics g;
	
	//States 
	public State gameState;
	public State menuState;
	public State brickState;
	public State settingState;
	public State shopState;
	
	//Input
	private KeyManager keyManager;
	private MouseManager mouseManager;
	
	private Handler handler;
	
	
	//Game Constructor
	public Game(String title, int width, int height) {
		this.width = width;
		this.height = height;
		this.title = title;
		keyManager = new KeyManager();
		mouseManager = new MouseManager();
	}
	
	//Audio Variables
	Long currentFrame; 
    static Clip clip, zClip; 
      
    static AudioInputStream audioInputStream, zAudioInputStream; 
    static InputStream backgroundMusic, zBackgroundMusic;
	static FloatControl gainControl, zGainControl;
	
	//Initializer
	private void init() {
		//Looks for storage files, if not found creates them, then stores the contents in certain variables
		boolean exists = new File("docs/preferences.txt").exists(); //Settings File
		boolean exists2 = new File("docs/userdata.txt").exists(); //Gold, and store file
		String[] prefs = null, userdata = null;
		if (exists && exists2) {
			prefs = Utils.loadFileAsString("docs/preferences.txt").split("\n");
			userdata = Utils.loadFileAsString("docs/userdata.txt").split("\n");
		} else { 
			File docs = new File("docs");
			docs.mkdir();
			try {
	            FileWriter writer;
	            if (!exists) {
	            	writer = new FileWriter("docs/preferences.txt", false);
	            	writer.write(Game.difficulty+"\n");
	            	writer.close();
	            }
	            if (!exists2) {
					writer = new FileWriter("docs/userdata.txt", false);
					writer.write("0");
					for (int i = 0; i < 8; i++) 
						writer.write("false");
		            writer.close();
		        }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
			prefs = Utils.loadFileAsString("docs/preferences.txt").split("\n");
			userdata = Utils.loadFileAsString("docs/userdata.txt").split("\n");
		}
		if (prefs.length < 1) {
			try {
	            FileWriter writer;
            	writer = new FileWriter("docs/preferences.txt", false);
            	writer.write(Game.difficulty+"\n");
            	writer.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
			prefs = Utils.loadFileAsString("docs/preferences.txt").split("\n");
		}
		if (userdata.length < 11) {
			try {
	            FileWriter writer;
	            writer = new FileWriter("docs/userdata.txt", false);
				writer.write("0\n");
				for(int i = 1; i < 9; i++) {
					writer.write("false\n");
				}
				writer.write("default\n");
				writer.write("default\n");
	            writer.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
			userdata = Utils.loadFileAsString("docs/userdata.txt").split("\n");
		}
		if (prefs[0] != null)
			Game.difficulty = Integer.valueOf(prefs[0]);
		for(int i = 1; i < 9; i++) {
			if (userdata[i] == null) {
				FileWriter writer;
	            try {
					writer = new FileWriter("docs/userdata.txt", true);
					writer.write("false");
		            writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		for(int i = 1; i < 9; i++) {
			if (userdata[i] != null) {
				purchases[i-1] =  Boolean.valueOf(userdata[i]);
			}
		}
		if (userdata[9] != null)
			Game.activeSkin = userdata[9];
		if (userdata[10] != null)
			Game.activeAnim = userdata[10];
		if (userdata[0] != null)
			if (userdata[0].length() <= 9)
				gold = Integer.valueOf(userdata[0]);
			else {
				try {
		            FileWriter writer;
		            writer = new FileWriter("docs/userdata.txt", false);
					writer.write("999999999");
		            writer.close();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
				userdata = Utils.loadFileAsString("docs/userdata.txt").split("\n");
				gold = Integer.valueOf(userdata[0]);
			}
		
		//Starts Music and sets volume
		try {
			backgroundMusic = new BufferedInputStream(Game.class.getResourceAsStream("/sounds/song.wav"));
			audioInputStream = AudioSystem.getAudioInputStream(backgroundMusic);
		} catch (UnsupportedAudioFileException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
        try {
			clip = AudioSystem.getClip();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        try {
			clip.open(audioInputStream);
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        gainControl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(20f * (float) Math.log10(1f));
        clip.loop(Clip.LOOP_CONTINUOUSLY); 
        
        //Applies Mouse and Key Listeners to the frame and canvas
		display = new Display(title, width, height);
		display.getFrame().addKeyListener(keyManager);
		display.getFrame().addMouseListener(mouseManager);
		display.getFrame().addMouseMotionListener(mouseManager);
		display.getCanvas().addMouseListener(mouseManager);
		display.getCanvas().addMouseMotionListener(mouseManager);
		
		Assets.init();
		
		//Fills creates all the state instances
		handler = new Handler(this);
		brickState = new BrickState(handler);
		menuState = new MenuState(handler);
		shopState = new ShopState(handler);
		
		//Opens the main menu
		State.setState(menuState);
	}
	
	public static Display getDisplay() {
		return display;
	}

	public void setDisplay(Display display) {
		this.display = display;
	}

	//Getters and Setters
	public static boolean isCpuActive() {
		return Game.cpuActive;
	}

	public static void setCpuActive(boolean cpuActive) {
		Game.cpuActive = cpuActive;
	}
	
	public static boolean isCounterOn() {
		return Game.counterOn;
	}

	public void setCounterOn(boolean counterOn) {
		Game.counterOn = counterOn;
	}
	
	public static int getDifficulty() {
		return Game.difficulty;
	}

	public void setDifficulty(int difficulty) {
		Game.difficulty = difficulty;
	}
	
	public static int getGold() {
		return gold;
	}

	public static void setGold(int gold) {
		Game.gold = gold;
	}
	
	public static String getActiveSkin() {
		return activeSkin;
	}

	public static void setActiveSkin(String activeSkin) {
		Game.activeSkin = activeSkin;
	}
	
	public static String getActiveAnim() {
		return activeAnim;
	}

	public static void setActiveAnim(String activeAnim) {
		Game.activeAnim = activeAnim;
	}
	
	public static boolean[] getPurchases() {
		return purchases;
	}

	public static void setPurchases(boolean[] purchases) {
		Game.purchases = purchases;
	}

	//Simplified Music Play Methods to be called from other classes and so music can be paused or stopped
	public static void playLoss() {
		clip.stop();
		try {
			backgroundMusic = new BufferedInputStream(Game.class.getResourceAsStream("/sounds/loss.wav"));//Astronomia
			audioInputStream = AudioSystem.getAudioInputStream(backgroundMusic);
		} catch (UnsupportedAudioFileException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
        try {
			clip = AudioSystem.getClip();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        try {
			clip.open(audioInputStream);
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        gainControl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(20f * (float) Math.log10(0.2f));
        clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public static void playZaWarudo() {
		try {
			zBackgroundMusic = new BufferedInputStream(Game.class.getResourceAsStream("/sounds/za warudo.wav"));//Za Warudo
			zAudioInputStream = AudioSystem.getAudioInputStream(zBackgroundMusic);
		} catch (UnsupportedAudioFileException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
        try {
			zClip = AudioSystem.getClip();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        try {
			zClip.open(zAudioInputStream);
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        zGainControl = (FloatControl)zClip.getControl(FloatControl.Type.MASTER_GAIN);
        zGainControl.setValue(20f * (float) Math.log10(1f));
        zClip.start();
	}
	
	public static void playZero() { //Skips Za Warudo to near the finish
        zClip.stop();
        zClip.setMicrosecondPosition(25500000);
        zClip.start();
	}
	
	public static void stopSound() {
		clip.stop();
	}
	public static void startSound() {
		clip.start();
	}
	
	public static void playVictory() {
		clip.stop();
		try {
			backgroundMusic = new BufferedInputStream(Game.class.getResourceAsStream("/sounds/victory.wav"));//Giorno's Theme
			audioInputStream = AudioSystem.getAudioInputStream(backgroundMusic);
		} catch (UnsupportedAudioFileException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
        try {
			clip = AudioSystem.getClip();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        try {
			clip.open(audioInputStream);
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        gainControl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(20f * (float) Math.log10(0.5f));
        clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public static void playMain() {
		clip.stop();
		try {
			backgroundMusic = new BufferedInputStream(Game.class.getResourceAsStream("/sounds/song.wav"));//Valorant Main Menu Theme
			audioInputStream = AudioSystem.getAudioInputStream(backgroundMusic);
		} catch (UnsupportedAudioFileException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
        try {
			clip = AudioSystem.getClip();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        try {
			clip.open(audioInputStream);
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        gainControl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(20f * (float) Math.log10(1f));
        clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	private void tick() {
		//Attempts to stop the gold from overflowing 
		if(Game.getGold() > 999999999)	
			Game.setGold(999999999);
		
		//Checks Key Presses
		keyManager.tick();
		
		//Ticks the current state
		if(State.getState() != null) {
			State.getState().tick();
		}
	}
	
	private void render() {
		//Creates a buffer strategy and fills the Graphics Variable
		bs = display.getCanvas().getBufferStrategy();
		if(bs == null) {
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		//Clears the screen and colors the background
		g.clearRect(0, 0, width, height);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);
		
		//Renders the current state
		if(State.getState() != null) {
			State.getState().render(g);
		}
		
		//Shows the graphics and resets the graphics variable
		bs.show();
		g.dispose();
	}
	
	//More Getters and Setters
	public static boolean isEasyBot() {
		return easyBot;
	}

	public void setEasyBot(boolean easyBot) {
		Game.easyBot = easyBot;
	}

	public void run() {

		init();
		
		double timePerTick = 1000000000/75;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime();
		
		//This while loop keeps the whole game running at 60 fps or as close as possible
		while(running) {
			now = System.nanoTime();
			delta += (now - lastTime) / timePerTick;
			lastTime = now;
			
			if (delta > 1) {
				tick();//Important Line, It ticks the whole Game
				render();//Important Line, It renders the whole Game
				delta=0;
			}
		}
		
		stop();
		
	}
	
	//This time its only getters for some reason 
	public KeyManager getKeyManager() {
		return keyManager;
	}
	
	public MouseManager getMouseManager() {
		return mouseManager;
	}

	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	//To be perfectly honest, I don't remember what this does but I know it was important because it fixed a lot of bugs I had
	public synchronized void start() {
		if (running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	public synchronized void stop() {
		if (!running)
			return;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
