package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Heart extends Entity {

	public static final String objName = "Heart";
	GamePanel gp;

	public OBJ_Heart(GamePanel gp) {

		super(gp);
		this.gp = gp;
		type = type_pickupOnly;
		name = objName;
		value = 2;
		down1 = setup("/objects/heart_full");
		image = setup("/objects/heart_full");
		image2 = setup("/objects/heart_half");
		image3 = setup("/objects/heart_blank");
	}

	public boolean use(Entity entity) {
		gp.playSE(2);
		gp.ui.addMessage("Life +" + value);
		entity.life += value;
		return true;
	}
}
