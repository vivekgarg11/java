package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Door extends Entity {

	public static final String objName = "Door";

	GamePanel gp;

	public OBJ_Door(GamePanel gp) {

		super(gp);
		this.gp = gp;

		type = type_obstacle;
		name = objName;
		down1 = setup("/objects/door");
		collision = true;

		solidArea.x = 0;
		solidArea.y = 14;
		solidArea.width = 48;
		solidArea.height = 30;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		setDialogue();
	}

	public void setDialogue() {
		dialogues[0][0] = "You need a key to open the door.";
	}

	public void interact() {
		startDialogue(this, 0);
	}
}
