package object;

import main.gamePanel;

public class objectChest extends entity.entity {

    public objectChest(gamePanel gp) {
        super(gp);

        name = "Chest";
        down1 = setup("chest", gp.tileSize, gp.tileSize);
    }
}
