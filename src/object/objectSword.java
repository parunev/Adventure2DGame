package object;

import main.gamePanel;

public class objectSword extends entity.entity{
    public objectSword(gamePanel gp) {
        super(gp);

        type = typeSword;
        name = "Normal Sword";
        down1 = setup("sword_normal", gp.tileSize, gp.tileSize);
        attackValue = 1;
        attackArea.width = 36;
        attackArea.height = 36;
        description = "[" + name + "]\nAn Old Sword.";
        price = 55;
    }
}
