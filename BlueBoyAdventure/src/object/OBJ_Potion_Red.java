package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Potion_Red extends Entity {

	GamePanel gp;

	public OBJ_Potion_Red(GamePanel gp) {

		super(gp);

		this.gp = gp;

		type = type_consumable;
		name = "Red Potion";
		value = 5;
		down1 = setup("/objects/potion_red");
		description = "[ " + name + " ]\nHeals your life by " + value + ".";
		price = 25;
		stackable = true;
	}

	public boolean use(Entity entity) {
		gp.gameState = gp.dialogueState;
		gp.ui.currentDialogue = "You drank the " + name + "!\nYour life has been recovered by " + value + ".";

		entity.life += value;
		gp.playSE(2);
		return true;

	}
}
