package object;

import main.gamePanel;

public class objectSword extends entity.entity{
    public objectSword(gamePanel gp) {
        super(gp);

        name = "Normal Sword";
        down1 = setup("sword_normal", gp.tileSize, gp.tileSize);
        attackValue = 1;
        description = "[" + name + "]\nAn Old Sword.";
    }
}
