package object;

import main.gamePanel;

public class objectBoots extends entity.entity {

    public objectBoots(gamePanel gp){
        super(gp);

        name = "Boots";
        down1 = setup("boots", gp.tileSize, gp.tileSize);
    }
}
