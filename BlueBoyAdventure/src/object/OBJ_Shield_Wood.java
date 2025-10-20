package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Shield_Wood extends Entity {

	public static final String objName = "Wood Shield";

	public OBJ_Shield_Wood(GamePanel gp) {
		super(gp);

		type = type_shield;
		name = objName;
		down1 = setup("/objects/shield_wood");
		defenceValue = 1;
		description = "[ " + name + " ]\nMade of Wood.";
		price = 35;
	}

}
