package object;

import main.gamePanel;

public class objectBlueShield extends entity.entity{
    public objectBlueShield(gamePanel gp) {
        super(gp);

        type = typeShield;
        name = "Blue Shield";
        down1 = setup("shield_blue",gp.tileSize,gp.tileSize);
        defenceValue = 2;
        description = "[" + name + "]" + "\nShiny blue shield";
        price = 100;
    }
}
