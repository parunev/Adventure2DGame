package object;

import main.gamePanel;

public class objectDoor extends entity.entity{

    public objectDoor(gamePanel gp){
        super(gp);

        name = "Door";
        down1 = setup("door", gp.tileSize, gp.tileSize);
        collision = true;

        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = 48;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}
