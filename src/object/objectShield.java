package object;

import main.gamePanel;

public class objectShield extends entity.entity{
    public objectShield(gamePanel gp) {
        super(gp);

        name = "Wooden Shield";
        down1 = setup("shield_wood", gp.tileSize, gp.tileSize);
        defenceValue = 1;
        description = "[" + name + "]\nMade by wood.";
    }
}
