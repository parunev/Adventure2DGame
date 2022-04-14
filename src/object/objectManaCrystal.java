package object;

import main.gamePanel;

public class objectManaCrystal extends entity.entity{
    public objectManaCrystal(gamePanel gp) {
        super(gp);

        name = "Mana Crystal";
        image = setup("manacrystal_full",gp.tileSize,gp.tileSize);
        image2 = setup("manacrystal_blank",gp.tileSize,gp.tileSize);
    }
}
