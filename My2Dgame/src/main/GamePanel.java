package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.print.attribute.SetOfIntegerSyntax;
import javax.swing.JPanel;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{
	
	final int originalTileSize = 16; //16x16 tile size
	final int scale = 3;

	public final int tileSize = originalTileSize * scale; //48x48 tile size
	public final int maxScreenCol= 16;
	public final int maxScreenRow= 12;
	public final int screenWidth = tileSize*maxScreenCol; // 786 pixels
	public final int screenHeight = tileSize*maxScreenRow; //576 pixels
	
	//WORLD SETTINGS
	
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;
	public final int worldWidth = tileSize*maxWorldCol;
	public final int worldHeight= tileSize*maxWorldRow;
	
	//FPS
	
	int FPS =60;
	
	//SYSTEM
	TileManager tileM = new TileManager(this);
	KeyHandler keyH = new KeyHandler();
	Sound music = new Sound();
	Sound se = new Sound();
	public CollisionChecker cChecker = new CollisionChecker(this);
	public AssetSetter aSetter = new AssetSetter(this);
	public UI ui = new UI(this);
	Thread gameThread;
	
	//ENTITY AND OBJECT
	public Player player = new Player(this,keyH);
	public SuperObject obj[] = new SuperObject[10];
	
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth,screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}
	
	public void setupGame() {
		aSetter.setObject();
		
		playMusic(0);
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
		
		double drawInterval = 1000000000/FPS; //0.01666 seconds
		double delta = 0;
		
		long lastTime=System.nanoTime();
		long currentTime;
		
		while(gameThread != null) {
			
			currentTime = System.nanoTime();
			
			delta += (currentTime-lastTime)/drawInterval;

			lastTime=currentTime;
			
			if(delta>=1) {
				// 1. UPDATE: update informations such as character positions
				update();
				
				// 2. DRAW: draw the screen with the updated information
				repaint();
				delta--;
			}
			
			
			
		}
		
	}
	
	public void update() {
		
		player.update();
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		
		//DEBUG
		long drawStart = 0;
		if(keyH.checkDrawTime==true) {
		
			drawStart =System.nanoTime();
		}
		
		//TILES
		tileM.draw(g2);
		
		//OBJECT
		for(int i=0; i<obj.length;i++) {
			if(obj[i]!= null) {
				obj[i].draw(g2, this);
			}
		}
		
		//PLAYER
		player.draw(g2);
		
		//UI
		ui.draw(g2);
		
		//DEGUG
		if(keyH.checkDrawTime==true) {
			
			long drawEnd = System.nanoTime();
			long passed = drawEnd-drawStart;
			g2.setColor(Color.white);
			g2.drawString("Draw Time: "+ passed, 10, 400);
			System.out.println("Draw Time: "+ passed);
		}
		
		g2.dispose();
	}
	
	public void playMusic(int i) {
		
		music.setFile(i);
		music.play();
		music.loop();
	}
	
	public void stopmusic() {
		music.stop();
	}
	
	public void playSE (int i) {
		se.setFile(i);
		se.play();
	}
	
}
