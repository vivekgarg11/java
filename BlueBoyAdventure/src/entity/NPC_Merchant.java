package entity;

import java.awt.Rectangle;

import main.GamePanel;
import object.OBJ_Axe;
import object.OBJ_Key;
import object.OBJ_Potion_Red;
import object.OBJ_Shield_Blue;
import object.OBJ_Shield_Wood;
import object.OBJ_Sword_Normal;

public class NPC_Merchant extends Entity {

	public NPC_Merchant(GamePanel gp) {
		super(gp);

		direction = "down";
		speed = 1;
		solidArea = new Rectangle();
		solidArea.x = 8;
		solidArea.y = 16;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		solidArea.width = 32;
		solidArea.height = 32;
		getImage();
		setDialogue();
		setItems();
	}

	public void getImage() {

		up1 = setup("/npc/merchant_down_1");
		up2 = setup("/npc/merchant_down_2");
		down1 = setup("/npc/merchant_down_1");
		down2 = setup("/npc/merchant_down_2");
		left1 = setup("/npc/merchant_down_1");
		left2 = setup("/npc/merchant_down_2");
		right1 = setup("/npc/merchant_down_1");
		right2 = setup("/npc/merchant_down_2");
	}

	public void setDialogue() {
		dialogues[0] = "He he, so you found me.\nI have some good stuff.\nDo you want to trade?";

	}

	public void setItems() {
		inventory.add(new OBJ_Potion_Red(gp));
		inventory.add(new OBJ_Key(gp));
		inventory.add(new OBJ_Sword_Normal(gp));
		inventory.add(new OBJ_Axe(gp));
		inventory.add(new OBJ_Shield_Wood(gp));
		inventory.add(new OBJ_Shield_Blue(gp));

	}

	public void speak() {
		super.speak();
		gp.gameState = gp.tradeState;
		gp.ui.npc = this;
	}

}
