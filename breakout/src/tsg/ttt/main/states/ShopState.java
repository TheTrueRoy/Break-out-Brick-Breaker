package tsg.ttt.main.states;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.io.FileWriter;
import java.io.IOException;

import tsg.ttt.main.Game;
import tsg.ttt.main.Handler;
import tsg.ttt.main.control.keyboard.KeyManager;
import tsg.ttt.main.gfx.Assets;
import tsg.ttt.main.ui.*;
import tsg.ttt.main.ui.particles.brickParticleB;
import tsg.ttt.main.ui.particles.explosionParticle;

public class ShopState extends State{ // A shop, kind of like the settings file (Possibly incomplete)
	
	private UIManager uiManager;
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	KeyManager keyManager = handler.getKeyManager();
	
	int time = 0;
	
	public ShopState(Handler handler) {
		super(handler);
		
	}

	public void init() {
		time = 0;
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
		UIImage coins = new UIImage(16, 16, 64, 64, Assets.coin);
		uiManager.addObject(coins);

		String count = Integer.toString(Game.getGold());
		UIImage[] digits = new UIImage[count.length()];
		for (int i = 0; i < count.length(); i++) {
			digits[i] = new UIImage(80+i*62, 16, 54, 66, Assets.numbers[Character.getNumericValue(count.charAt(i))]);
			uiManager.addObject(digits[i]);
		}
		
		if(Game.getActiveAnim().equals("default")) {
			uiManager.addObject(new UIImage(screenSize.width/8-150, screenSize.height/4-150, 300, 300, Assets.wo));
		} else {
			uiManager.addObject(new UIImage(screenSize.width/8-150, screenSize.height/4-150, 300, 300, Assets.o));
			if (!Game.getActiveAnim().equals("default")) {
				uiManager.addObject(new UIImageButton(screenSize.width/8-135, screenSize.height/24*11-36, 270, 72, Assets.equip, new ClickListener(){
					public void onClick() {
						Game.setActiveAnim("default");
						
					}}, true));
			}
		} if(Game.getActiveAnim().equals("bomb")) {
			UIImage bomb = new UIImage(screenSize.width/8*3-150, screenSize.height/4-150, 300, 300, Assets.wo);
			uiManager.addObject(bomb);
		} else {
			UIImage bomb = new UIImage(screenSize.width/8*3-150, screenSize.height/4-150, 300, 300, Assets.o);
			uiManager.addObject(bomb);
			if(Game.getPurchases()[1]) {
				uiManager.addObject(new UIImageButton(screenSize.width/8*3-135, screenSize.height/24*11-36, 270, 72, Assets.equip, new ClickListener(){
					public void onClick() {
						Game.setActiveAnim("bomb");
						uiManager.addObject(new UIImage(screenSize.width/8*3-150, screenSize.height/4-150, 300, 300, Assets.wo));
						bomb.setActive(false);
					}}, false));
			} else {
				uiManager.addObject(new UIImage(screenSize.width/8*3-72, screenSize.height/24*10-20, 144, 48, Assets.price[2]));
				uiManager.addObject(new UIImageButton(screenSize.width/8*3-81, screenSize.height/2-36, 162, 72, Assets.buy, new ClickListener(){
					public void onClick() {
						if (Game.getGold()>=500) {
							Game.setGold(Game.getGold()-500);
						boolean[] purchases = Game.getPurchases();
						Game.setPurchases(new boolean[] {purchases[0],true,purchases[2],purchases[3],purchases[4],purchases[5],purchases[6],purchases[7]});
						Game.setActiveAnim("bomb");
						
						}
					}}, true));
			}
		} if(Game.getActiveAnim().equals("spiral")) {
			uiManager.addObject(new UIImage(screenSize.width/8*5-150, screenSize.height/4-150, 300, 300, Assets.wo));
		} else {
			uiManager.addObject(new UIImage(screenSize.width/8*5-150, screenSize.height/4-150, 300, 300, Assets.o));
			if(Game.getPurchases()[2]) {
				uiManager.addObject(new UIImageButton(screenSize.width/8*5-135, screenSize.height/24*11-36, 270, 72, Assets.equip, new ClickListener(){
					public void onClick() {
						Game.setActiveAnim("spiral");
						
					}}, true));
			} else {
				uiManager.addObject(new UIImage(screenSize.width/8*5-57, screenSize.height/24*10-20, 144, 48, Assets.price[1]));
				uiManager.addObject(new UIImageButton(screenSize.width/8*5-81, screenSize.height/2-36, 162, 72, Assets.buy, new ClickListener(){
					public void onClick() {
						if (Game.getGold()>=1000) {
							Game.setGold(Game.getGold()-1000);
						boolean[] purchases = Game.getPurchases();
						Game.setPurchases(new boolean[] {purchases[0],purchases[1],true,purchases[3],purchases[4],purchases[5],purchases[6],purchases[7]});
						Game.setActiveAnim("spiral");
						
						}
					}}, true));
			}
		} if(Game.getActiveAnim().equals("fallen")) {
			uiManager.addObject(new UIImage(screenSize.width/8*7-150, screenSize.height/4-150, 300, 300, Assets.wo));
		} else {
			uiManager.addObject(new UIImage(screenSize.width/8*7-150, screenSize.height/4-150, 300, 300, Assets.o));
			if(Game.getPurchases()[3]) {
				uiManager.addObject(new UIImageButton(screenSize.width/8*7-135, screenSize.height/24*11-36, 270, 72, Assets.equip, new ClickListener(){
					public void onClick() {
						Game.setActiveAnim("fallen");
						
					}}, true));
			} else {
				uiManager.addObject(new UIImage(screenSize.width/8*7-57, screenSize.height/24*10-20, 144, 48, Assets.price[0]));
				uiManager.addObject(new UIImageButton(screenSize.width/8*7-81, screenSize.height/2-36, 162, 72, Assets.buy, new ClickListener(){
					public void onClick() {
						if (Game.getGold()>=3000) {
							Game.setGold(Game.getGold()-3000);
							boolean[] purchases = Game.getPurchases();
							Game.setPurchases(new boolean[] {purchases[0],purchases[1],purchases[2],true,purchases[4],purchases[5],purchases[6],purchases[7]});
							Game.setActiveAnim("fallen");
						}
					}}, true));
			}
		}
	}
	
	public void tick() {
		if (keyManager.esc == true) {
			State.setState(handler.getGame().menuState);
		}
		time++;
		if (time%4==0 && time < 60) {
			double wid = Math.random();
			uiManager.addObject(new brickParticleB((float)Math.random()*screenSize.width, -500, (int)(56*wid)+24, (int)(21*wid)+9, screenSize.height+100, (float)Math.random()*-0.3f*0.3f, -20+(float)Math.random()*-1.3f*1.3f));
		}
		uiManager.tick();
	}

	public void render(Graphics g) {
		uiManager.render(g);
	}
}
