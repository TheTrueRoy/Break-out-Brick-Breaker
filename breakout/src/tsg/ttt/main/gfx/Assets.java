package tsg.ttt.main.gfx;

import java.awt.image.BufferedImage;

import tsg.ttt.main.Game;

public class Assets {
	//Initializes and stores textures for faster loading and simplicity, It also chops up spritesheets
	private static int t = 0;
	
	public static BufferedImage title, gameover, box, x, o, coin, description, wx, wo, tie, dText, easymode, bar, bZone, onecoin, tencoin, twentycoin;
	public static BufferedImage[] playButton = new BufferedImage[2];
	public static BufferedImage[] shop = new BufferedImage[2];
	public static BufferedImage[] buy = new BufferedImage[2];
	public static BufferedImage[] equip = new BufferedImage[2];
	public static BufferedImage[] playAgain = new BufferedImage[2];
	public static BufferedImage[] instructions = new BufferedImage[2];
	public static BufferedImage[] settings = new BufferedImage[2];
	public static BufferedImage[] exit = new BufferedImage[2];
	public static BufferedImage[] pos = new BufferedImage[2];
	public static BufferedImage[] switchBi = new BufferedImage[2];
	public static BufferedImage[] score = new BufferedImage[2];
	public static BufferedImage[] apply = new BufferedImage[2];
	public static BufferedImage[] counter = new BufferedImage[2];
	public static BufferedImage[] price = new BufferedImage[3];
	public static BufferedImage[] mode = new BufferedImage[6];
	public static BufferedImage[] pal = new BufferedImage[6];
	public static BufferedImage[] beam = new BufferedImage[10];
	public static BufferedImage[] numbers = new BufferedImage[10];
	public static BufferedImage[] numbersRED = new BufferedImage[10];
	public static BufferedImage[] numbersBLUE = new BufferedImage[10];
	public static BufferedImage[] bricks = new BufferedImage[10];
	public static BufferedImage[] bricksCOLOR = new BufferedImage[10];
	public static SpriteSheet rPlayButton, rShop, rBuy, rEquip, rPlayAgain, rInstructions, rSettings, rExit, rPosition, rSwitch, rApply, rScore, rBeam, rPrice, rCounter, rNumbers, rNumbersRED, rNumbersBLUE, rBricks, rBricksCOLOR, rMode, rPal;
	
	public static void init() {
		t = 0;
			rPlayButton = new SpriteSheet(ImageLoader.loadImage("/textures/play_button.png"));
			rShop = new SpriteSheet(ImageLoader.loadImage("/textures/shop.png"));
			rBuy = new SpriteSheet(ImageLoader.loadImage("/textures/buy.png"));
			rEquip = new SpriteSheet(ImageLoader.loadImage("/textures/equip.png"));
			rPlayAgain = new SpriteSheet(ImageLoader.loadImage("/textures/playagain.png"));
			rInstructions = new SpriteSheet(ImageLoader.loadImage("/textures/instructions.png"));
			rSettings = new SpriteSheet(ImageLoader.loadImage("/textures/settings.png"));
			rExit = new SpriteSheet(ImageLoader.loadImage("/textures/exit.png"));
			rPosition = new SpriteSheet(ImageLoader.loadImage("/textures/position.png"));
			rSwitch = new SpriteSheet(ImageLoader.loadImage("/textures/switch.png"));
			rApply = new SpriteSheet(ImageLoader.loadImage("/textures/apply.png"));
			rScore = new SpriteSheet(ImageLoader.loadImage("/textures/score.png"));
			rCounter = new SpriteSheet(ImageLoader.loadImage("/textures/counter.png"));
			rPrice = new SpriteSheet(ImageLoader.loadImage("/textures/price.png"));
			rMode = new SpriteSheet(ImageLoader.loadImage("/textures/difficulty.png"));
			rPal = new SpriteSheet(ImageLoader.loadImage("/textures/pallette.png"));
			rBeam = new SpriteSheet(ImageLoader.loadImage("/textures/beam.png"));
			rNumbers = new SpriteSheet(ImageLoader.loadImage("/textures/numbers.png"));
			rNumbersRED = new SpriteSheet(ImageLoader.loadImage("/textures/numbersred.png"));
			rNumbersBLUE = new SpriteSheet(ImageLoader.loadImage("/textures/numbersblue.png"));
			rBricks = new SpriteSheet(ImageLoader.loadImage("/textures/bricks.png"));
			rBricksCOLOR = new SpriteSheet(ImageLoader.loadImage("/textures/color/bricks.png"));
		
			title = ImageLoader.loadImage("/textures/title.png");
			gameover = ImageLoader.loadImage("/textures/gameover.png");
			box = ImageLoader.loadImage("/textures/box.png");
			x = ImageLoader.loadImage("/textures/x.png");
			o = ImageLoader.loadImage("/textures/o.png");
			coin = ImageLoader.loadImage("/textures/coin.png");
			wx = ImageLoader.loadImage("/textures/xwin.png");
			wo = ImageLoader.loadImage("/textures/owin.png");
			tie = ImageLoader.loadImage("/textures/tie.png");
			description = ImageLoader.loadImage("/textures/description.png");
			dText = ImageLoader.loadImage("/textures/difficultytext.png");
			easymode = ImageLoader.loadImage("/textures/easymode.png");
			bar = ImageLoader.loadImage("/textures/bar.png");
			bZone = ImageLoader.loadImage("/textures/bZone.png");
			onecoin = ImageLoader.loadImage("/textures/onecoin.png");
			tencoin = ImageLoader.loadImage("/textures/tencoin.png");
			twentycoin = ImageLoader.loadImage("/textures/twentycoin.png");
		
		while (t < 10) {
			if (t < 2) {
				playButton[t] = rPlayButton.crop(0, 11*t, 33, 11);
				shop[t] = rShop.crop(0, 6*t, 19, 6);
				buy[t] = rBuy.crop(0, 6*t, 14, 6);
				equip[t] = rEquip.crop(0, 6*t, 24, 6);
				playAgain[t] = rPlayAgain.crop(0, 6*t, 47, 6);
				instructions[t] = rInstructions.crop(0, 11*t, 97, 11);
				settings[t] = rSettings.crop(0, 11*t, 65, 11);
				exit[t] = rExit.crop(0, 14*t, 14, 14);
				pos[t] = rPosition.crop(0, 64*t, 64, 64);
				switchBi[t] = rSwitch.crop(0, 6*t, 14, 6);
				apply[t] = rApply.crop(0, 11*t, 33, 11);
				score[t] = rScore.crop(0, 9*t, 26, 9);
				counter[t] = rCounter.crop(0, 6*t, 34, 6);
			} if (t < 3) {
				price[t] = rPrice.crop(0, 6*t, 24, 6);
			}
			beam[t] = rBeam.crop(96*t, 0, 96, 288);
			numbers[t] = rNumbers.crop(9*t, 0, 9, 11);
			numbersRED[t] = rNumbersRED.crop(9*t, 0, 9, 11);
			numbersBLUE[t] = rNumbersBLUE.crop(9*t, 0, 9, 11);
			t++;
		}
		for (int i = 0; i < 6; i++) {
			pal[i] = rPal.crop(0, i*100, 100, 100);
			mode[i] = rMode.crop(0, i*11, 33, 11);
		}
		for (int i = 0; i < 10; i++) {
			bricks[i] = rBricks.crop((i/5)*160, (i%5)*60, 160, 60);
			bricksCOLOR[i] = rBricksCOLOR.crop((i/5)*160, (i%5)*60, 160, 60);
		}
	}
}
