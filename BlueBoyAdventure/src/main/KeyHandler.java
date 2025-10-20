package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

	GamePanel gp;

	public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, shotKeyPressed, spacePressed;
	// DEBUG
	boolean checkDrawTime = false;

	public KeyHandler(GamePanel gp) {
		this.gp = gp;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

		int code = e.getKeyCode();

		// TITLE SCREEN

		if (gp.gameState == gp.titleState) {

			titleState(code);

		}

		// PLAY STATE
		else if (gp.gameState == gp.playState) {
			playState(code);

		}

		// PAUSE STATE
		else if (gp.gameState == gp.pauseState) {
			pauseState(code);

		}
		// DIALOGUE STATE
		else if (gp.gameState == gp.dialogueState) {
			dialogueState(code);

		}

		// CHARACTER STATE
		else if (gp.gameState == gp.characterState) {
			characterState(code);

		}

		// OPTIONS STATE
		else if (gp.gameState == gp.optionsState) {
			optionsState(code);

		}

		// GAME OVER STATE
		else if (gp.gameState == gp.gameOverState) {
			gameOverState(code);

		}

		// GAME OVER STATE
		else if (gp.gameState == gp.tradeState) {
			tradeState(code);

		}

		// GAME OVER STATE
		else if (gp.gameState == gp.mapState) {
			mapState(code);

		}

	}

	public void titleState(int code) {
		if (gp.ui.titleScreenState == 0) {
			if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
				gp.ui.commandNum--;
				if (gp.ui.commandNum < 0) {
					gp.ui.commandNum = 2;
				}
			}

			if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
				gp.ui.commandNum++;
				if (gp.ui.commandNum > 2) {
					gp.ui.commandNum = 0;
				}
			}
			if (code == KeyEvent.VK_ENTER) {
				if (gp.ui.commandNum == 0) {
					gp.ui.titleScreenState = 1;
				}
				if (gp.ui.commandNum == 1) {
					gp.saveload.load();
					gp.gameState = gp.playState;
					gp.playMusic(0);
				}
				if (gp.ui.commandNum == 2) {
					System.exit(0);
				}
			}
		}

		else if (gp.ui.titleScreenState == 1) {
			if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
				gp.ui.commandNum--;
				if (gp.ui.commandNum < 0) {
					gp.ui.commandNum = 3;
				}
			}

			if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
				gp.ui.commandNum++;
				if (gp.ui.commandNum > 3) {
					gp.ui.commandNum = 0;
				}
			}
			if (code == KeyEvent.VK_ENTER) {
				if (gp.ui.commandNum == 0) {
					gp.gameState = gp.playState;
					gp.playMusic(0);
				}
				if (gp.ui.commandNum == 1) {
					gp.gameState = gp.playState;
					gp.playMusic(0);
				}
				if (gp.ui.commandNum == 2) {
					gp.gameState = gp.playState;
					gp.playMusic(0);
				}
				if (gp.ui.commandNum == 3) {
					gp.ui.commandNum = 0;
					gp.ui.titleScreenState = 0;
				}
			}
		}

	}

	public void playState(int code) {

		if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
			upPressed = true;
		}

		if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
			downPressed = true;
		}
		if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
			leftPressed = true;
		}
		if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
			rightPressed = true;
		}
		if (code == KeyEvent.VK_P) {
			gp.gameState = gp.pauseState;
		}
		if (code == KeyEvent.VK_C) {
			gp.gameState = gp.characterState;
		}
		if (code == KeyEvent.VK_ENTER) {
			enterPressed = true;
		}
		if (code == KeyEvent.VK_F) {
			shotKeyPressed = true;
		}
		if (code == KeyEvent.VK_ESCAPE) {
			gp.gameState = gp.optionsState;
		}
		if (code == KeyEvent.VK_M) {
			gp.gameState = gp.mapState;
		}
		if (code == KeyEvent.VK_X) {
			if (gp.map.miniMapOn == false) {
				gp.map.miniMapOn = true;
			} else {
				gp.map.miniMapOn = false;
			}
		}

		if (code == KeyEvent.VK_SPACE) {
			spacePressed = true;
		}

		// DEBUG

		if (code == KeyEvent.VK_T) {
			if (checkDrawTime == false) {
				checkDrawTime = true;
			} else if (checkDrawTime == true) {
				checkDrawTime = false;
			}
		}

	}

	public void pauseState(int code) {

		if (code == KeyEvent.VK_P) {
			gp.gameState = gp.playState;
		}

	}

	public void dialogueState(int code) {

		if (code == KeyEvent.VK_ENTER) {
			enterPressed = true;
		}

	}

	public void characterState(int code) {

		if (code == KeyEvent.VK_C) {
			gp.gameState = gp.playState;
		}
		if (code == KeyEvent.VK_ENTER) {
			gp.player.selectItem();
		}
		playerInventory(code);

	}

	public void optionsState(int code) {
		if (code == KeyEvent.VK_ESCAPE) {
			gp.gameState = gp.playState;
		}
		if (code == KeyEvent.VK_ENTER) {
			enterPressed = true;
		}

		int maxCommandNum = 0;
		switch (gp.ui.subState) {
		case 0:
			maxCommandNum = 5;
			break;
		case 3:
			maxCommandNum = 1;
			break;
		}

		if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
			gp.ui.commandNum--;
			gp.playSE(9);
			if (gp.ui.commandNum < 0) {
				gp.ui.commandNum = maxCommandNum;
			}
		}

		if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
			gp.ui.commandNum++;
			gp.playSE(9);
			if (gp.ui.commandNum > maxCommandNum) {
				gp.ui.commandNum = 0;
			}
		}
		if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
			if (gp.ui.subState == 0) {
				if (gp.ui.commandNum == 1 && gp.music.volumeScale > 0) {
					gp.music.volumeScale--;
					gp.music.checkVolume();
					gp.playSE(9);
				}
				if (gp.ui.commandNum == 2 && gp.se.volumeScale > 0) {
					gp.se.volumeScale--;
					gp.playSE(9);
				}
			}

		}
		if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
			if (gp.ui.subState == 0) {
				if (gp.ui.commandNum == 1 && gp.music.volumeScale < 5) {
					gp.music.volumeScale++;
					gp.music.checkVolume();
					gp.playSE(9);
				}
				if (gp.ui.commandNum == 2 && gp.se.volumeScale < 5) {
					gp.se.volumeScale++;
					gp.playSE(9);
				}
			}
		}
	}

	public void gameOverState(int code) {

		if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
			gp.ui.commandNum--;
			if (gp.ui.commandNum < 0) {
				gp.ui.commandNum = 1;
			}
			gp.playSE(9);
		}

		if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
			gp.ui.commandNum++;
			if (gp.ui.commandNum > 1) {
				gp.ui.commandNum = 0;
			}
			gp.playSE(9);
		}
		if (code == KeyEvent.VK_ENTER) {
			if (gp.ui.commandNum == 0) {
				gp.gameState = gp.playState;
				gp.resetGame(false);
				gp.playMusic(0);
			} else if (gp.ui.commandNum == 1) {
				gp.ui.commandNum = 0;
				gp.ui.titleScreenState = 0;
				gp.gameState = gp.titleState;
				gp.resetGame(true);
			}
		}

	}

	public void tradeState(int code) {
		if (code == KeyEvent.VK_ENTER) {
			enterPressed = true;
		}
		if (gp.ui.subState == 0) {
			if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
				gp.ui.commandNum--;
				if (gp.ui.commandNum < 0) {
					gp.ui.commandNum = 2;
				}
				gp.playSE(9);
			}
			if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
				gp.ui.commandNum++;
				if (gp.ui.commandNum > 2) {
					gp.ui.commandNum = 0;
				}
				gp.playSE(9);
			}
		}
		if (gp.ui.subState == 1) {
			npcInventory(code);
			if (code == KeyEvent.VK_ESCAPE) {
				gp.ui.subState = 0;
			}
		}
		if (gp.ui.subState == 2) {
			playerInventory(code);
			if (code == KeyEvent.VK_ESCAPE) {
				gp.ui.subState = 0;
			}
		}
	}

	public void mapState(int code) {
		if (code == KeyEvent.VK_M) {
			gp.gameState = gp.playState;
		}
	}

	public void playerInventory(int code) {
		if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
			if (gp.ui.playerSlotRow != 0) {
				gp.ui.playerSlotRow--;
				gp.playSE(9);
			}

		}

		if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
			if (gp.ui.playerSlotRow != 3) {
				gp.ui.playerSlotRow++;
				gp.playSE(9);
			}

		}
		if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
			if (gp.ui.playerSlotCol != 0) {
				gp.ui.playerSlotCol--;
				gp.playSE(9);
			}

		}
		if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
			if (gp.ui.playerSlotCol != 4) {
				gp.ui.playerSlotCol++;
				gp.playSE(9);
			}
		}
	}

	public void npcInventory(int code) {
		if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
			if (gp.ui.npcSlotRow != 0) {
				gp.ui.npcSlotRow--;
				gp.playSE(9);
			}

		}

		if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
			if (gp.ui.npcSlotRow != 3) {
				gp.ui.npcSlotRow++;
				gp.playSE(9);
			}

		}
		if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
			if (gp.ui.npcSlotCol != 0) {
				gp.ui.npcSlotCol--;
				gp.playSE(9);
			}

		}
		if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
			if (gp.ui.npcSlotCol != 4) {
				gp.ui.npcSlotCol++;
				gp.playSE(9);
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

		int code = e.getKeyCode();

		if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
			upPressed = false;
		}

		if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
			downPressed = false;
		}
		if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
			leftPressed = false;
		}
		if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
			rightPressed = false;
		}
		if (code == KeyEvent.VK_F) {
			shotKeyPressed = false;
		}
		if (code == KeyEvent.VK_ENTER) {
			enterPressed = false;
		}
		if (code == KeyEvent.VK_SPACE) {
			spacePressed = false;
		}

	}

}
