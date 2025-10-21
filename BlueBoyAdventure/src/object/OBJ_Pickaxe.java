package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Pickaxe extends Entity {

	public static final String objName = "Pickaxe";

	public OBJ_Pickaxe(GamePanel gp) {
		super(gp);

		type = type_pickaxe;
		name = objName;
		down1 = setup("/objects/pickaxe");
		attackValue = 2;
		attackArea.width = 30;
		attackArea.height = 30;
		description = "[ " + name + " ]\n" + "Smash rocks with it!";
		price = 75;
		knockBackPower = 10;
		motion1_duration = 10;
		motion2_duration = 30;
	}

}
