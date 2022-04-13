package object;

import main.gamePanel;

public class objectAxe extends entity.entity{

    public objectAxe(gamePanel gp) {
        super(gp);

        type = typeAxe;
        name = "Woodcutter's Axe";
        down1 = setup("axe", gp.tileSize, gp.tileSize);
        attackValue = 2;
        attackArea.width = 30;
        attackArea.height = 30;
        description = "[" + name + "]" +"\nA bit rusty but still \ncan cut some trees.";
    }
}
