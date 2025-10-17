package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JPanel;

import ai.PathFinder;
import entity.Entity;
import entity.Player;
import tile.TileManager;
import tile_interactive.InteractiveTile;

public class GamePanel extends JPanel implements Runnable {

	final int originalTileSize = 16; // 16x16 tile size
	final int scale = 3;

	public final int tileSize = originalTileSize * scale; // 48x48 tile size
	public final int maxScreenCol = 20;
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize * maxScreenCol; // 960 pixels
	public final int screenHeight = tileSize * maxScreenRow; // 576 pixels

	// WORLD SETTINGS

	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;
	public final int worldWidth = tileSize * maxWorldCol;
	public final int worldHeight = tileSize * maxWorldRow;
	public final int maxMap = 10;
	public int currentMap = 0;

	// FOR FULL SCREEN
	int screenWidth2 = screenWidth;
	int screenHeight2 = screenHeight;
	BufferedImage tempScreen;
	Graphics2D g2;
	public boolean fullScreenOn = false;

	// FPS

	int FPS = 60;

	// SYSTEM
	public TileManager tileM = new TileManager(this);
	public KeyHandler keyH = new KeyHandler(this);
	Sound music = new Sound();
	Sound se = new Sound();
	public CollisionChecker cChecker = new CollisionChecker(this);
	public AssetSetter aSetter = new AssetSetter(this);
	public UI ui = new UI(this);
	public EventHandler eHandler = new EventHandler(this);
	Config config = new Config(this);
	public PathFinder pFinder = new PathFinder(this);
	Thread gameThread;

	// ENTITY AND OBJECT
	public Player player = new Player(this, keyH);
	public Entity obj[][] = new Entity[maxMap][20];
	public Entity npc[][] = new Entity[maxMap][10];
	public Entity monster[][] = new Entity[maxMap][20];
	public InteractiveTile iTile[][] = new InteractiveTile[maxMap][50];
	public ArrayList<Entity> projectileList = new ArrayList<>();
	public ArrayList<Entity> particleList = new ArrayList<>();
	ArrayList<Entity> entityList = new ArrayList<>();

	// GAME STATE
	public int gameState;
	public final int titleState = 0;
	public final int playState = 1;
	public final int pauseState = 2;
	public final int dialogueState = 3;
	public final int characterState = 4;
	public final int optionsState = 5;
	public final int gameOverState = 6;
	public final int transitionState = 7;
	public final int tradeState = 8;

	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}

	public void setupGame() {
		aSetter.setObject();
		aSetter.setNPC();
		aSetter.setMonster();
		aSetter.setInteractiveTile();
//		playMusic(0);
		gameState = titleState;

		tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB_PRE);
		g2 = (Graphics2D) tempScreen.getGraphics();

		if (fullScreenOn == true) {
			setFullScreen();
		}
	}

	public void retry() {
		player.setDefaultPositions();
		player.restoreLifeAndMana();
		aSetter.setNPC();
		aSetter.setMonster();
	}

	public void restart() {

		player.setDefaultValues();
		player.setItems();
		aSetter.setObject();
		aSetter.setNPC();
		aSetter.setMonster();
		aSetter.setInteractiveTile();

	}

	public void setFullScreen() {
		// GET LOCAL DEVICE SCREEN
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		gd.setFullScreenWindow(Main.window);

		// GET FULL SCREEN WIDTH AND HEIGHT
		screenWidth2 = Main.window.getWidth();
		screenHeight2 = Main.window.getHeight();

	}

	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	@Override
//	public void run() {
//		
//		
//		double drawInterval = 1000000000/FPS; //0.01666 seconds
//		double nextDrawInterval = System.nanoTime() + drawInterval;
//		
//		while(gameThread != null) {
//			
////			System.out.println("The game loop is running");
//			// 1. UPDATE: update informations such as character positions
//			update();
//			
//			// 2. DRAW: draw the screen with the updated information
//			repaint();
//			
//			
//			try {
//				double remainingTime = nextDrawInterval - System.nanoTime();
//				remainingTime=remainingTime/1000000;
//				if(remainingTime<0) {
//					remainingTime=0;
//				}
//				Thread.sleep((long)remainingTime);
//				
//				nextDrawInterval += drawInterval;
//				
//			} 
//			catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		
//		
//	}

	public void run() {

		double drawInterval = 1000000000 / FPS; // 0.01666 seconds
		double delta = 0;

		long lastTime = System.nanoTime();
		long currentTime;

		while (gameThread != null) {

			currentTime = System.nanoTime();

			delta += (currentTime - lastTime) / drawInterval;

			lastTime = currentTime;

			if (delta >= 1) {
				// 1. UPDATE: update informations such as character positions
				update();

				// 2. DRAW: draw the screen with the updated information
				drawToTempScreen();// draw everything to the buffered image
				drawToScreen();// draw the buffered image to the screen
				delta--;
			}

		}

	}

	public void update() {

		if (gameState == playState) {
			// PLAYER
			player.update();
			// NPC
			for (int i = 0; i < npc[1].length; i++) {
				if (npc[currentMap][i] != null) {
					npc[currentMap][i].update();
				}
			}

			// MONSTER
			for (int i = 0; i < monster[1].length; i++) {
				if (monster[currentMap][i] != null) {
					if (monster[currentMap][i].alive == true && monster[currentMap][i].dying == false) {
						monster[currentMap][i].update();
					}
					if (monster[currentMap][i].alive == false) {
						monster[currentMap][i].checkDrop();
						monster[currentMap][i] = null;
					}
				}
			}
			// PROJECTILES
			for (int i = 0; i < projectileList.size(); i++) {
				if (projectileList.get(i) != null) {
					if (projectileList.get(i).alive == true) {
						projectileList.get(i).update();
					}
					if (projectileList.get(i).alive == false) {
						projectileList.remove(i);
					}
				}
			}
			for (int i = 0; i < particleList.size(); i++) {
				if (particleList.get(i) != null) {
					if (particleList.get(i).alive == true) {
						particleList.get(i).update();
					}
					if (particleList.get(i).alive == false) {
						particleList.remove(i);
					}
				}
			}
			for (int i = 0; i < iTile[1].length; i++) {
				if (iTile[currentMap][i] != null) {
					iTile[currentMap][i].update();
				}
			}
		}

		if (gameState == pauseState) {
			// nothing
		}

	}

	public void drawToTempScreen() {

		g2.clearRect(0, 0, screenWidth2, screenHeight2); // clear canvas for repainting in every loop

		// DEBUG
		long drawStart = 0;
		if (keyH.checkDrawTime == true) {

			drawStart = System.nanoTime();
		}

		// TITLE SCREEN
		if (gameState == titleState) {
			ui.draw(g2);
		}

		// OTHERS

		else {
			// TILES
			tileM.draw(g2);

			// INTERACTIVE TILE
			for (int i = 0; i < iTile[1].length; i++) {
				if (iTile[currentMap][i] != null) {
					iTile[currentMap][i].draw(g2);
				}
			}

			// ADD ENTITIES TO THE LIST
			entityList.add(player);

			for (int i = 0; i < npc[1].length; i++) {
				if (npc[currentMap][i] != null) {
					entityList.add(npc[currentMap][i]);
				}
			}

			for (int i = 0; i < obj[1].length; i++) {
				if (obj[currentMap][i] != null) {
					entityList.add(obj[currentMap][i]);
				}
			}

			for (int i = 0; i < monster[1].length; i++) {
				if (monster[currentMap][i] != null) {
					entityList.add(monster[currentMap][i]);
				}
			}
			for (int i = 0; i < projectileList.size(); i++) {
				if (projectileList.get(i) != null) {
					entityList.add(projectileList.get(i));
				}
			}
			for (int i = 0; i < particleList.size(); i++) {
				if (particleList.get(i) != null) {
					entityList.add(particleList.get(i));
				}
			}

			// SORT
			Collections.sort(entityList, new Comparator<Entity>() {

				@Override
				public int compare(Entity e1, Entity e2) {
					int result = Integer.compare(e1.worldY, e2.worldY);
					return result;
				}

			});

			// DRAW ENTITIES
			for (int i = 0; i < entityList.size(); i++) {
				entityList.get(i).draw(g2);
			}

			// EMPTY ENITITY LIST
			entityList.clear();

			// UI
			ui.draw(g2);
		}

		// DEGUG
		if (keyH.checkDrawTime == true) {

			long drawEnd = System.nanoTime();
			long passed = drawEnd - drawStart;
			g2.setColor(Color.white);
			g2.drawString("Draw Time: " + passed, 10, 400);
			System.out.println("Draw Time: " + passed);
		}

	}

	public void drawToScreen() {

		Graphics g = getGraphics();
		g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
		g.dispose();

	}

	public void playMusic(int i) {

		music.setFile(i);
		music.play();
		music.loop();
	}

	public void stopmusic() {
		music.stop();
	}

	public void playSE(int i) {
		se.setFile(i);
		se.play();
	}

}
