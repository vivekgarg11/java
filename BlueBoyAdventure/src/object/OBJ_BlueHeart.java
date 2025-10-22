package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_BlueHeart extends Entity {

	GamePanel gp;
	public static final String objName = "Blue Heart";

	public OBJ_BlueHeart(GamePanel gp) {
		super(gp);
		this.gp = gp;

		type = type_pickupOnly;
		name = objName;
		down1 = setup("/objects/blueheart");
		setDialogues();
	}

	public void setDialogues() {
		dialogues[0][0] = "You have picked up a beautiful blue gem.";
		dialogues[0][1] = "You have found the Blue heart, the legendary treasure!";
	}

	public boolean use(Entity entity) {
		gp.gameState = gp.cutsceneState;
		gp.csManager.sceneNum = gp.csManager.ending;

		return true;
	}
}
