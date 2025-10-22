package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;

public class MON_Bat extends Entity {

	GamePanel gp;

	public MON_Bat(GamePanel gp) {
		super(gp);

		this.gp = gp;

		type = type_monster;
		name = "Bat";
		defaultSpeed = 4;
		speed = defaultSpeed;
		maxLife = 7;
		life = maxLife;
		attack = 7;
		defence = 0;
		exp = 7;

		solidArea.x = 3;
		solidArea.y = 15;
		solidArea.width = 42;
		solidArea.height = 21;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;

		getImage();
	}

	public void getImage() {

		up1 = setup("/monster/bat_down_1");
		up2 = setup("/monster/bat_down_2");
		down1 = setup("/monster/bat_down_1");
		down2 = setup("/monster/bat_down_2");
		left1 = setup("/monster/bat_down_1");
		left2 = setup("/monster/bat_down_2");
		right1 = setup("/monster/bat_down_1");
		right2 = setup("/monster/bat_down_2");

	}

	public void setAction() {

		if (onPath == true) {

			// Check if it stops chasing
//			checkStopChasingOrNot(gp.player, 15, 100);
//
//			// Path to a certain Goal
////			int goalCol = 12;
////			int goalRow = 9;
//
//			// NPC follows player
//			// Search the direction to go
//			searchPath(getGoalCol(gp.player), getGoalRow(gp.player));
		} else {
			// Check if it starts chasing
//			checkStartChasingOrNot(gp.player, 5, 100);
			// Get a random direction
			getRandomDirection(10);

		}

	}

	public void damageReaction() {
		actionLockCounter = 0;
//		direction = gp.player.direction;
//		onPath = true;
	}

	public void checkDrop() {

		// CAST A DIE
		int i = new Random().nextInt(100) + 1;

		// DROP THE ITEM
		if (i < 50) {
			dropItem(new OBJ_Coin_Bronze(gp));
		}
		if (i >= 50 && i < 75) {
			dropItem(new OBJ_Heart(gp));
		}
		if (i >= 75 && i < 100) {
			dropItem(new OBJ_ManaCrystal(gp));
		}
	}

}
