package tsg.ttt.main.states;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.MemoryImageSource;
import java.util.ArrayList;
import java.util.Iterator;

import tsg.ttt.main.Game;
import tsg.ttt.main.Handler;
import tsg.ttt.main.control.keyboard.KeyManager;
import tsg.ttt.main.control.mouse.MouseManager;
import tsg.ttt.main.gfx.Assets;
import tsg.ttt.main.ui.*;
import tsg.ttt.main.ui.bricks.BasicBrick;
import tsg.ttt.main.ui.bricks.Brick;
import tsg.ttt.main.ui.bricks.PowerBrick;
import tsg.ttt.main.ui.particles.brickParticle2;
import tsg.ttt.main.ui.particles.starParticle;

public class BrickState extends State{

	private UIManager uiManager;
	boolean paused = false, pauseCD = false, over = false;
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	KeyManager keyManager = handler.getKeyManager();
	MouseManager mouseManager = handler.getMouseManager();
	int timeEsc = 0;
	double timeBomb = 0; boolean bombActive = false; boolean bombWasActive;
	int time = 0;
	long overTime = 0;
	long lastBreak = 0;

	//Settings
	int diff = Game.getDifficulty();
	double emptyChance = 99/100.0;
	double powerChance = 10/100.0;
	int scale = 2; //Above 20 causes laggy
	String control = "mouse";
	//Power UPs 
	boolean drill = false;
	boolean portal = false;
	
	//Upgrades
	int bombRadius = 200;
	int bombCooldown = 30;
	int bombDamage = 2;
	double bombPassiveRecharge = 0.1;
	double paddleBoost = 0;
	double comboMult = 1;
	double ballSizeMult = 1;
	
	//Combo
	int combo = 0;
	UIImage[] digitsC;
	
	//Level
	int level = 4;
	int DiffSpeedMult = 4;
	int DiffScoreMult = 10000;

	ArrayList<Brick> bricks = new ArrayList<Brick>();

	UIImage charge, box;
	UIFilter pause = new UIFilter(0,0,screenSize.width,screenSize.height,Assets.pal[0],0); 
	UITiltImage gameover = new UITiltImage((screenSize.width/2-500)*0, (screenSize.height/10*4-128/7*5-750)*0, 1000, 128/7*10, Assets.gameover, 0.05, 0.00025);
	UIImage score = new UIImage(0,0,0,0,Assets.score[0]);
	UIImage coin = new UIImage(0,0,0,0,Assets.coin);

	UIImage paddle;
	int paddleX = 0, paddleW = 400/(diff+1)*2, paddleH = 70;
	Rectangle pBounds = new Rectangle(0,screenSize.height-paddleH,paddleW,paddleH);

	UIImage paddle2;
	Rectangle pBounds2 = new Rectangle(0,screenSize.height-paddleH,paddleW,paddleH);

	UIImage ball;
	double ballD, ballX, ballY, ballVX, ballVY; 
	int ballW = (int)(50*ballSizeMult), speed = DiffSpeedMult*(3+(diff+1)), paddleSpeed = (int) (10+paddleBoost);
	int brickReward = (int) (Math.pow(diff,2)+1) * DiffScoreMult;
	Rectangle bBounds = new Rectangle(0,0,ballW,ballW);

	long points = 0;
	int svpb = (int) paddleBoost;
	UIImage[] digits;

	public BrickState(Handler handler) {
		super(handler);
	}

	public void set() {
		level++;
		diff*=1.3;
		ballD = Math.random()*90+45;
		ballD = Math.toRadians(ballD);
		ball.setX(screenSize.width/2-ballW/2); ball.setY(screenSize.height-paddleH-200-ballW/2); ballVX = 0; ballVY = 0;
		bricks.clear();
		if (level%5==0) {
			for(int i = 0; i < 8*scale; i++) {
				for(int j = 0; j < 10*scale; j++) {
					int health = Math.min(Math.max((int)((Math.random()*11.0-1)/(i+1)*scale*2),1),9);
					PowerBrick power = new PowerBrick(j*160/scale+120+80/scale*(i%2),i*60/scale+180,160/scale,60/scale,1);
					double real = Math.random();
					if (real > emptyChance) {
						bricks.add(power);
						uiManager.addObject(power);
					}
				}
			}
		} else {
			for(int i = 0; i < 8*scale; i++) {
				for(int j = 0; j < 10*scale; j++) {
					int health = Math.min(Math.max((int)((Math.random()*11.0-1)/(i+1)*scale*2),1),9);
					BasicBrick brick = new BasicBrick(j*160/scale+120+80/scale*(i%2),i*60/scale+180,160/scale,60/scale,health);
					PowerBrick power = new PowerBrick(j*160/scale+120+80/scale*(i%2),i*60/scale+180,160/scale,60/scale,1);
					double real = Math.random();
					if (real > emptyChance) {
						double pow = Math.random();
						if (pow > powerChance) {
							bricks.add(brick);
							uiManager.addObject(brick);
						} else {
							bricks.add(power);
							uiManager.addObject(power);
						}
					}
				}
			}
		}
		lastBreak = System.nanoTime()-7000000000l;
	}

	public void init() {
		//Robot robot = new Robot();
		if(control.equals("mouse")) {
			int[] pixels = new int[16 * 16];
			Image image = Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(16, 16, pixels, 0, 16));
			Cursor transparentCursor = Toolkit.getDefaultToolkit().createCustomCursor(image, new Point(0, 0), "invisibleCursor");
			Game.getDisplay().setCursor(transparentCursor);
		}
		paused = false;
		pauseCD = false;
		over = false;

		diff = Game.getDifficulty();
		paddleBoost = svpb;
		if (diff == 2)
			paddleBoost += 5;
		paddleSpeed = (int) (10+paddleBoost);
		paddleW = 400/(diff+1)*2;
		pBounds = new Rectangle(0,screenSize.height-paddleH,paddleW,paddleH);

		speed = DiffSpeedMult*(+1);
		points = 0;
		combo = 0;
		bombActive = false;
		brickReward = (int) (Math.pow(diff,2)+1) * DiffScoreMult;

		ballD = Math.random()*90+45;
		ballD = Math.toRadians(ballD);
		ballX = screenSize.width/2; ballY = screenSize.height-paddleH-100; ballVX = Math.cos(ballD)*3; ballVY = Math.sin(ballD)*3;
		timeEsc = 0;
		timeBomb = 0;
		uiManager = new UIManager(handler);
		handler.getMouseManager().setUIManager(uiManager);
		paddle = new UIImage(screenSize.width/2-paddleW/2, screenSize.height-paddleH, paddleW, paddleH, Assets.bricks[0]);
		paddle2 = new UIImage(screenSize.width*3/2, screenSize.height-paddleH, paddleW, paddleH, Assets.bricks[0]);
		ball = new UIImage((int)ballX-ballW/2, (int)ballY-ballW/2, ballW, ballW, Assets.o);
		uiManager.addObject(paddle);
		uiManager.addObject(paddle2);
		uiManager.addObject(ball);
		charge = new UIImage(0, 0, 0, 105, Assets.bricks[5]);
		uiManager.addObject(charge);
		box = new UIImage(0,0,1050,105,Assets.box);
		uiManager.addObject(box);
		bricks.clear();
		for(int i = 0; i < 8*scale; i++) {
			for(int j = 0; j < 10*scale; j++) {
				int health = Math.min(Math.max((int)((Math.random()*11.0-1)/(i+1)*scale*2),1),9);
				BasicBrick brick = new BasicBrick(j*160/scale+120+80/scale*(i%2),i*60/scale+180,160/scale,60/scale,health);
				PowerBrick power = new PowerBrick(j*160/scale+120+80/scale*(i%2),i*60/scale+180,160/scale,60/scale,1);
				double real = Math.random();
				if (real > emptyChance) {
					double pow = Math.random();
					if (pow > powerChance) {
						bricks.add(brick);
						uiManager.addObject(brick);
					} else {
						bricks.add(power);
						uiManager.addObject(power);
					}
				}
			}
		}
		uiManager.addObject(pause);
		uiManager.addObject(gameover);
		uiManager.addObject(score);
		uiManager.addObject(coin);
		pause.setAlpha(0);
		score.setX(0);
		score.setY(0);
		score.setWidth(0);
		score.setHeight(0);
		coin.setX(0);
		coin.setY(0);
		coin.setWidth(0);
		coin.setHeight(0);
		gameover.setWidth(0);
		gameover.setHeight(0);
		pause.setPrio(2);
		gameover.setPrio(2);
		score.setPrio(2);
		coin.setPrio(2);
		lastBreak = System.nanoTime();
	}

	public void tick() {
		if (!paused && !over) {
			overTime = 0;
			//Checks if the ball is just bouncing left and right aimlessly
			if ((lastBreak + 10000000000l) < System.nanoTime()) {
				lastBreak = System.nanoTime();
				ballD = Math.toRadians(90);
				ballX = screenSize.width/2; ballY = screenSize.height-paddleH-100; ballVX = Math.cos(ballD)*3; ballVY = Math.sin(ballD)*3;
			}

			//Most of the games code
			if (control.equals("keyboard")) {
				if (keyManager.right == true) { 
					paddle.setX(paddle.getX()+paddleSpeed);
				}
				if (keyManager.left == true) { 
					paddle.setX(paddle.getX()-paddleSpeed);
				}
			} else {
				//if (MouseInfo.getPointerInfo().getLocation().x == 0)
					//robot.mouseMove(
				paddle.setX(MouseInfo.getPointerInfo().getLocation().x-paddleW/2);
			}
			if (keyManager.space == true || (mouseManager.isLeftPressed() && control.equals("mouse"))) { //Explosion
				if (timeBomb >= bombCooldown) {
					bombActive = true;
				}
			}
			if (keyManager.esc == true) {
				if (!pauseCD) {
					Game.setGold((int) (Game.getGold()+points/100000));
					paused = true;
					pauseCD = true;
				}
			} else 
				pauseCD = false;

			//Trippy side portal nonsense
			if (paddle.getX() > screenSize.width)//Moves primary paddle to replace clone on left
				paddle.setX(paddle.getX()-screenSize.width);
			if (paddle.getX()+paddleW < 0)//Moves primary paddle to replace clone on right
				paddle.setX(paddle.getX()+screenSize.width);

			//Moves clone to the side more likely to require it next as well as removing it after teleportation
			if (paddle.getX()+paddleW/2 < screenSize.width/2) {
				paddle2.setX(paddle.getX()+screenSize.width);
			} else {
				paddle2.setX(paddle.getX()-screenSize.width);
			}

			pBounds.x = (int) paddle.getX();
			pBounds2.x = (int) paddle2.getX();
			bBounds.x = (int) ball.getX();
			bBounds.y = (int) ball.getY();

			if (pBounds.intersects(bBounds)) {
				if (portal)
					ball.setY(-ballW);
				else {
					ball.setY(screenSize.height-ballW-paddleH);
					ballD = (-ballD - (Math.PI - (ball.getX()+ball.getWidth()-paddle.getX())/(paddleW+ballW)*Math.PI))/2;
				}
				ballVX = Math.cos(ballD)*speed; ballVY = Math.sin(ballD)*speed;
				combo=0; //Resets combo count when the paddle is hit
				lastBreak = System.nanoTime();
			} else if (pBounds2.intersects(bBounds)) {
				if (portal)
					ball.setY(-ballW);
				else {
					ball.setY(screenSize.height-ballW-paddleH);
					ballD = (-ballD - (Math.PI - (ball.getX()+ball.getWidth()-paddle2.getX())/(paddleW+ballW)*Math.PI))/2;
				}
				ballVX = Math.cos(ballD)*speed; ballVY = Math.sin(ballD)*speed;
				combo=0; //Resets combo count when the paddle is hit
				lastBreak = System.nanoTime(); //player interaction resets ball reset CD
			}
			if (ball.getY() > screenSize.height-ballW) {
				over = true;
			} else if (ball.getY() < 0) {
				ball.setY(0);
				ballD = -ballD;
				ballVX = Math.cos(ballD)*speed; ballVY = Math.sin(ballD)*speed;
			}
			if (ball.getX() > screenSize.width-ballW) {
				ball.setX(screenSize.width-ballW);
				ballD = Math.PI-ballD;
				ballVX = Math.cos(ballD)*speed; ballVY = Math.sin(ballD)*speed;
			} else if (ball.getX() < 0) {
				ball.setX(0);
				ballD = Math.PI-ballD;
				ballVX = Math.cos(ballD)*speed; ballVY = Math.sin(ballD)*speed;
			}
			
			double ballDs = ballD, ballVXs = ballVX, ballVYs = ballVY;
			Iterator<Brick> it = bricks.iterator();
			ArrayList<Brick> broken = new ArrayList<Brick>();
			ArrayList<Brick> bombBroken = new ArrayList<Brick>();
			boolean hFlip = drill, vFlip = drill;
			int hitDMG = 3-Game.getDifficulty();
			if (bombActive)
				hitDMG = bombDamage;
			bombWasActive = false;
			while(it.hasNext()) {
				Brick e = it.next();
				Rectangle r = new Rectangle((int)e.getX(), (int)e.getY(), e.getWidth(), e.getHeight());
				if (r.intersects(bBounds)) {
					if (bombActive) { //Bomb
						bombWasActive = true;
						timeBomb = 0;
					}
					if (!hFlip && bBounds.y+bBounds.height/3 < r.y+r.height && bBounds.y+bBounds.height*2/3 > r.y) { //If it blatantly hits the side of the brick, it bounces of the side
						hFlip = true;
						ballD = Math.PI-ballD;
						ballVX = Math.cos(ballD)*speed; ballVY = Math.sin(ballD)*speed;
					} else if (!vFlip) { //It hits the top/near the top, it bounces back
						vFlip = true;
						ballD = -ballD;
						ballVX = Math.cos(ballD)*speed; ballVY = Math.sin(ballD)*speed;
					}
					lastBreak = System.nanoTime();
					broken.add(e);
				}
				int dist = (int) (Math.pow(bBounds.x+ballW/2-(r.x+r.width/2),2) + Math.pow(bBounds.y+ballW/2-(r.y+r.height/2),2));
				if (bombActive && dist < bombRadius*bombRadius) {
					if (!e.iden().equals("power"))
						bombBroken.add(e); //BasicBricks that break if bomb explodes
				}
			}
			if (bombBroken.size() > 0 && broken.size() > 0)
				broken.addAll(bombBroken); //Combines broken bricks if bomb has collided and will damage bricks
			it = broken.iterator();
			while(it.hasNext()) {
				combo++;
				if(level%5==0)
					points+=brickReward*combo*comboMult*100;
				else
					points+=brickReward*combo*comboMult;
				if (timeBomb < bombCooldown && !bombActive)
					timeBomb+=10;

				Brick e = it.next();
				double ratio = Math.PI*1/2-Math.atan2(ball.getX()+ballW/2-(e.getX()+e.getWidth()), ball.getY()+ballW/2-(e.getY()+e.getHeight()));
				double dist = Math.pow(ball.getX()+ballW/2-(e.getX()+e.getWidth()/2),2) + Math.pow(ball.getY()+ballW/2-(e.getY()+e.getHeight()/2),2);
				String dr;
				if (!bombActive) {
					dr = e.deathRattle(uiManager, (float)-ballVX, (float)ballVY);
				} else
					dr = e.deathRattle(uiManager, (float)(Math.cos(ratio)*(20*(Math.pow(bombRadius,2)-dist)/Math.pow(bombRadius,2))-ballVX), (float)(Math.sin(ratio)*(20*(Math.pow(bombRadius,2)-dist)/Math.pow(bombRadius,2))+ballVY));
				e.damage(hitDMG+(int)(combo/20));
				if (e.getHealth() < 1) {
					bricks.remove(e);
				}
				if (dr.equals("power")) {
					ballD = ballDs;
					ballVX = ballVXs;
					ballVY = ballVYs;
					drill = true;
				}
			}
			if (System.nanoTime() - lastBreak > 150000000) {
				drill = false;
			}
			if (bombActive && bombWasActive) {
				for (int i = 0; i < 70; i++) {
					double wid = Math.random()*60+30;
					uiManager.addObject(new starParticle((float)(ball.getX()+wid/2), (float)(ball.getY()+wid/2), (int)wid, (int)wid, (int)(Math.random()*40)+80, 120, (float)Math.random()*9-3, (float)Math.random()*9-3));
				}
				bombActive = false;
			}

			if (bricks.size() == 0) {
				set();
			}

			ball.setX((float)(ball.getX()+ballVX));
			ball.setY((float)(ball.getY()+ballVY));

			String count;
			//Updates SCORE Counter
			count = Long.toString(points);
			if (digits != null) {
				for (int i = 0; i < digits.length; i++) {
					digits[i].setActive(false);
				}
			}
			digits = new UIImage[count.length()];
			for (int i = 0; i < count.length(); i++) {
				digits[i] = new UIImage(10+i*62, 16, 54, 66, Assets.numbers[Character.getNumericValue(count.charAt(i))]);
				uiManager.addObject(digits[i]);
			}

			//Updates COMBO Counter
			count = Integer.toString(combo);
			if (digitsC != null) {
				for (int i = 0; i < digitsC.length; i++) {
					digitsC[i].setActive(false);
				}
			}
			digitsC = new UIImage[count.length()];
			for (int i = 0; i < count.length(); i++) {
				if (combo == 0) {
					speed = DiffSpeedMult*(diff+1);
					digitsC[i] = new UIImage(25+i*31, 90, 27, 33, Assets.numbers[Character.getNumericValue(count.charAt(i))]);
				}
				else if (combo < 10) {
					digitsC[i] = new UIImage(25+i*31, 90, 27, 33, Assets.numbersBLUE[Character.getNumericValue(count.charAt(i))]);
				}
				else {
					speed = DiffSpeedMult*(diff+1) + 2;
					digitsC[i] = new UIImage(25+i*31, 90, 27, 33, Assets.numbersRED[Character.getNumericValue(count.charAt(i))]);
				}
				digitsC[i].setPrio(1);
				uiManager.addObject(digitsC[i]);
			}

			//Draws CHARGE bar

			if (timeBomb < 0) {
				timeBomb = 0;
				bombActive = false;
			}
			
			if (bombActive) {
				timeBomb-=0.1;
				charge.setWidth((int)(1050.0*timeBomb/bombCooldown));
				charge.setImage(Assets.pal[(time%30)/15+4]);
			} else if ((int)timeBomb == bombCooldown) {
				charge.setWidth((int)(1050.0*timeBomb/bombCooldown));
				charge.setImage(Assets.pal[2]);
			} else {
				charge.setWidth((int)(1050.0*timeBomb/bombCooldown));
				charge.setImage(Assets.pal[1]);
			}

			time++;
			//Makes Ambiance and ticks the UI
			if (combo > 10) {
				//for (int i = 0; i < 4; i++) {
				double wid = Math.random()*20+20;
				uiManager.addObject(new starParticle((float)(Math.random()*ballW+ball.getX()-wid/2), (float)(Math.random()*ballW+ball.getY()-wid/2), (int)wid, (int)wid, 20, 20, (float)ballVX*1.5f, (float)ballVY*1.5f));
				//}
			}
			if (!bombActive)
				timeBomb+=bombPassiveRecharge*(2-diff);
			if (timeBomb > bombCooldown)
				timeBomb = bombCooldown;
			
			if (over) {
				Game.setGold((int)(Game.getGold()+points/100000));
				gameover.setX(screenSize.width/2-gameover.getWidth()/2);
				gameover.setY(screenSize.height/10*4-gameover.getHeight()/2);
				
				//Effects
				if (!bombActive)
					for (int i = 0; i < 60; i++) {
						double px = screenSize.width/2-125+Math.random()*250;
						double py = screenSize.height/2-64/7*5+Math.random()*128/7*5;
						double dist = 1-((px-screenSize.width/2)*(px-screenSize.width/2)+(py-screenSize.height/2)*(py-screenSize.height/2))/95936*4;
						px -= 40;
						py-= 15;
						double dir = Math.PI/2-Math.atan2(px-screenSize.width/2,py-screenSize.height/2);
						px = screenSize.width/2-40;
						py = screenSize.height/2-15;
						uiManager.addObject(new brickParticle2((float)px, (float)py, 80, 30, screenSize.height, (float)(-Math.cos(dir)*10*dist), (float)(-Math.sin(dir)*10*dist), (int)(Math.random()*10)));
					}
				
			}

		} else if (over) {
			if (keyManager.esc || (mouseManager.isLeftPressed() && control.equals("mouse"))) { 
				State.setState(handler.getGame().menuState);
			}
			overTime++;
			pause.setAlpha(Math.min(40,overTime)/50.0);
			gameover.setWidth((int)(1000*Math.min(30,overTime)/30.0));
			gameover.setHeight((int)(128/7*10*Math.min(30,overTime)/30.0));
			gameover.setX(screenSize.width/2-gameover.getWidth()/2);
			gameover.setY(screenSize.height/10*3-gameover.getHeight()/2);
			if (overTime == 60) {
				score.setX(screenSize.width/2-130);
				score.setY(screenSize.height/2-45);
				score.setWidth(260);
				score.setHeight(90);
				coin.setX(screenSize.width/2-45);
				coin.setY(screenSize.height/2+55);
				coin.setWidth(90);
				coin.setHeight(90);
			}
			if (overTime >= 60 && overTime <= 60 + 60*(int)Math.log10(points)) {
				String count = Long.toString((long)((points*((overTime-60)/(60.0*(int)Math.log10(points))))));
				score.setX((int)(screenSize.width/2-(85*count.length()+20+score.getWidth())/2));
				if (digitsC != null) {
					for (int i = 0; i < digitsC.length; i++) {
						digitsC[i].setActive(false);
					}
				}
				digitsC = new UIImage[count.length()];
				for (int i = 0; i < count.length(); i++) {
					digitsC[i] = new UIImage(screenSize.width/2+20-(85*count.length()+20+score.getWidth())/2+score.getWidth()+i*85, screenSize.height/2-45, 72, 90, Assets.numbersBLUE[Character.getNumericValue(count.charAt(i))]);
					digitsC[i].setPrio(2);
					uiManager.addObject(digitsC[i]);
				}
				long coins = points/100000;
				coins*=(level/5)+1;
				count = Long.toString((long)((coins*((overTime-60)/(60.0*(int)Math.log10(points))))));
				coin.setX((int)(screenSize.width/2-(85*count.length()+20+coin.getWidth())/2));
				if (digits != null) {
					for (int i = 0; i < digits.length; i++) {
						digits[i].setActive(false);
					}
				}
				digits = new UIImage[count.length()];
				for (int i = 0; i < count.length(); i++) {
					digits[i] = new UIImage(screenSize.width/2+20-(85*count.length()+20+coin.getWidth())/2+coin.getWidth()+i*85, screenSize.height/2+55, 72, 90, Assets.numbersRED[Character.getNumericValue(count.charAt(i))]);
					digits[i].setPrio(2);
					uiManager.addObject(digits[i]);
				}
			}
		} else if (paused) {
			if (!pauseCD && keyManager.esc || (mouseManager.isLeftPressed() && control.equals("mouse"))) { 
				paused = false;
				pauseCD = true;
				overTime = 0;
				pause.setAlpha(0);
			}
			if (!keyManager.esc)
				pauseCD = false;
			overTime++;
			pause.setAlpha(Math.min(100,overTime)/150.0);
		}
		//Ticks the UI
		uiManager.tick();
	}

	public void render(Graphics g) { //Renders everything
		uiManager.render(g);
	}
}
