package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_ManaCrystal extends Entity {

	public static final String objName = "Mana Crystal";

	GamePanel gp;

	public OBJ_ManaCrystal(GamePanel gp) {
		super(gp);
		this.gp = gp;

		type = type_pickupOnly;
		name = objName;
		value = 1;
		down1 = setup("/objects/manacrystal_full");
		image = setup("/objects/manacrystal_full");
		image2 = setup("/objects/manacrystal_blank");
	}

	public boolean use(Entity entity) {
		gp.playSE(2);
		gp.ui.addMessage("Mana +" + value);
		entity.mana += value;
		return true;
	}

}
