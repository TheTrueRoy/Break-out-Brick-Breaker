package tsg.ttt.main.states;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.MemoryImageSource;
import java.io.FileWriter;
import java.io.IOException;

import tsg.ttt.main.Game;
import tsg.ttt.main.Handler;
import tsg.ttt.main.gfx.Assets;
import tsg.ttt.main.control.keyboard.KeyManager;
import tsg.ttt.main.ui.ClickListener;
import tsg.ttt.main.ui.UIImage;
import tsg.ttt.main.ui.UIImageButton;
import tsg.ttt.main.ui.UIImageRotarySwitch;
import tsg.ttt.main.ui.UIManager;
import tsg.ttt.main.ui.UITiltImage;
import tsg.ttt.main.ui.particles.brickParticle;
import tsg.ttt.main.ui.particles.brickParticleB;
import tsg.ttt.main.ui.particles.starParticle;

public class MenuState extends State{
	
	private UIManager uiManager;
	KeyManager keyManager = handler.getKeyManager();
	private int time = 0;
	private boolean settingsOpen = false, instructionsOpen = false;
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	UIImage[] digits;
	
	private UITiltImage title;
	private UIImageButton play, instruction, settings, shop, apply;
	private UIImage description, difficulty;
	private UIImageRotarySwitch mode;
	
	public MenuState(Handler handler) {
		super(handler);
		init();
	}

	public void init() {
		
		Image image = Assets.x;
		Cursor transparentCursor = Toolkit.getDefaultToolkit().createCustomCursor(image, new Point(0, 0), "invisibleCursor");
		Game.getDisplay().setCursor(transparentCursor);
		FileWriter writer;
		try {
			writer = new FileWriter("docs/userdata.txt", false);
			writer.write(Integer.toString(Game.getGold())+"\n");
			for (int i = 0; i < 8; i++) 
				writer.write(Boolean.toString(Game.getPurchases()[i])+"\n");
			writer.write(Game.getActiveSkin()+"\n");
			writer.write(Game.getActiveAnim()+"\n");
		    writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		uiManager = new UIManager(handler);
		handler.getMouseManager().setUIManager(uiManager);
		//Displays The coin in the upper right
		UIImage coins = new UIImage(16, 16, 64, 64, Assets.coin);
		uiManager.addObject(coins);
		
		//Displays the count
		String count = Integer.toString(Game.getGold());
		digits = new UIImage[count.length()];
		for (int i = 0; i < count.length(); i++) {
			digits[i] = new UIImage(80+i*62, 16, 54, 66, Assets.numbers[Character.getNumericValue(count.charAt(i))]);
			uiManager.addObject(digits[i]);
		}
		
		//Creates a bunch of buttons
		play = new UIImageButton(screenSize.width/2-127, screenSize.height/10*6-44, 254, 88, Assets.playButton, new ClickListener(){

			public void onClick() {
				handler.getMouseManager().setUIManager(null);
				State.setState(handler.getGame().brickState);
			}}, true);
		uiManager.addObject(play);
		
		description = new UIImage(screenSize.width/2-376,screenSize.height/2+600, 752, 200, Assets.description);
		uiManager.addObject(description);
		instruction = new UIImageButton(screenSize.width/2-388, screenSize.height/10*7-44, 776, 88, Assets.instructions, new ClickListener(){

			public void onClick() {
				instructionsOpen = !instructionsOpen;
				settingsOpen = false;
			}}, true);
		uiManager.addObject(instruction);
		
		shop = new UIImageButton(30, screenSize.height/20*19-36, 216, 72, Assets.shop, new ClickListener(){

			public void onClick() {
				State.setState(handler.getGame().shopState);
			}}, true);
		uiManager.addObject(shop);
		
		difficulty = new UIImage(screenSize.width/2-580, screenSize.height/2-54+800, 729, 99, Assets.dText);
		mode = new UIImageRotarySwitch(screenSize.width/2+180, screenSize.height/2-54+800, 292, 99, Assets.mode, new ClickListener(){
			public void onClick() {
				handler.getGame().setDifficulty((Game.getDifficulty()+1)%3);
				System.out.println(Game.getDifficulty());
			}}, Game.getDifficulty(), 3);
		uiManager.addObject(difficulty);
		uiManager.addObject(mode);
		
		apply = new UIImageButton(screenSize.width/2-90, screenSize.height/10*9+800, 180, 60, Assets.apply, new ClickListener(){

			public void onClick() {
				try {
		            FileWriter writer;
					writer = new FileWriter("docs/preferences.txt", false);
		            writer.write(Game.getDifficulty()+"\n");
		            writer.close();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
			}}, true);
		uiManager.addObject(apply);
		
		settings = new UIImageButton(screenSize.width/2-260, screenSize.height/10*8-44, 520, 88, Assets.settings, new ClickListener(){

			public void onClick() {
				settingsOpen = !settingsOpen;
				instructionsOpen = false;
			}}, true);
		uiManager.addObject(settings);
		uiManager.addObject(new UIImageButton(screenSize.width-80, 16, 64, 64, Assets.exit, new ClickListener(){

			public void onClick() {
				System.exit(0);
			}}, true));
		//Displays the title
		title = new UITiltImage(screenSize.width/2-500, screenSize.height/10*4-128/7*5, 1000, 128/7*10, Assets.title, 0.05, 0.00025);
		uiManager.addObject(title);
		//Creates Particles
		for(int i = 0; i < 180; i++){
			double wid = Math.random();
			uiManager.addObject(new brickParticleB((float)Math.random()*screenSize.width, (float)(Math.random()*screenSize.height*1.5-screenSize.height*0.5), (int)(56*wid)+24, (int)(21*wid)+9, screenSize.height+100, (float)Math.random()*-0.3f*0.3f, -2+(float)Math.random()*-1.3f*1.3f));
		}
		//animation for settings opening
	}
	
	public void tick() {
		if (keyManager.esc == true) { 
			settingsOpen = false;
			instructionsOpen = false;
		}
		/*if (keyManager.left == true) { 
			Game.setGold(Game.getGold()-1);
			for (int i = 0; i < digits.length; i++) {
				digits[i].setActive(false);
			}
			String count = Integer.toString(Game.getGold());
			digits = new UIImage[count.length()];
			for (int i = 0; i < count.length(); i++) {
				digits[i] = new UIImage(80+i*62, 16, 54, 66, Assets.numbers[Character.getNumericValue(count.charAt(i))]);
				uiManager.addObject(digits[i]);
			}
		}*/
		if (settingsOpen && settings.getY() <= screenSize.height/10*8-44) {
			if (title.getY() > screenSize.height/10*4-128/7*5 - 800) {
				title.setY(title.getY()-10);
				play.setY(play.getY()-10);
				instruction.setY(instruction.getY()-10);
				settings.setY(settings.getY()-10);
				difficulty.setY(difficulty.getY()-10);
				mode.setY(mode.getY()-10);
				apply.setY(apply.getY()-10);
			}
		} else if (instructionsOpen && settings.getY() >= screenSize.height/10*8-44) {
			if (title.getY() > screenSize.height/10*4-128/7*5 - 700) {
				title.setY(title.getY()-10);
				play.setY(play.getY()-10);
				instruction.setY(instruction.getY()-10);
				description.setY(description.getY()-10);
				settings.setY(settings.getY()+10);
			}	
		} else if (settings.getY() < screenSize.height/10*8-44) {
			title.setY(title.getY()+10);
			play.setY(play.getY()+10);
			instruction.setY(instruction.getY()+10);
			settings.setY(settings.getY()+10);
			difficulty.setY(difficulty.getY()+10);
			mode.setY(mode.getY()+10);
			apply.setY(apply.getY()+10);
		} else if (settings.getY() > screenSize.height/10*8-44) {
			title.setY(title.getY()+10);
			play.setY(play.getY()+10);
			instruction.setY(instruction.getY()+10);
			description.setY(description.getY()+10);
			settings.setY(settings.getY()-10);
		}
		time++;
		//Makes Ambiance and ticks the UI
		if (time >= 10) {
			//for (int i = 0; i < 4; i++) {
				double wid = Math.random();
				uiManager.addObject(new brickParticleB((float)Math.random()*screenSize.width, -500, (int)(56*wid)+24, (int)(21*wid)+9, screenSize.height+100, (float)Math.random()*-0.3f*0.3f, -4+(float)Math.random()*-1.3f*1.3f));
				time = 0;
			//}
		}
		uiManager.tick();
	}

	public void render(Graphics g) {
		//Renders the # of coins and then the rest of the UI
		uiManager.render(g);
	}
	
}
