package main;

import javax.swing.JFrame;

public class Main {

	public static JFrame window;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("2D Adventure");

		GamePanel gamepanel = new GamePanel();
		window.add(gamepanel);

		gamepanel.config.loadConfig();
		if (gamepanel.fullScreenOn == true) {
			window.setUndecorated(true);
		}

		window.pack();

		window.setLocationRelativeTo(null);
		window.setVisible(true);

		gamepanel.setupGame();
		gamepanel.startGameThread();

	}

}
