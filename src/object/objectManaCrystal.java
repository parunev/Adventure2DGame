package object;

import entity.entity;
import main.gamePanel;
import main.userInterface;

public class objectManaCrystal extends entity {

    public objectManaCrystal(gamePanel gp) {
        super(gp);

        type = typePickup;
        name = "Mana Crystal";
        value = 1;
        down1 = setup("manacrystal_full",gp.tileSize,gp.tileSize);
        image = setup("manacrystal_full",gp.tileSize,gp.tileSize);
        image2 = setup("manacrystal_blank",gp.tileSize,gp.tileSize);
    }
    public void use(entity entity){
        gp.playSE(2);
        userInterface.addMessage("Mana +" + value);
        entity.mana += value;
    }
}
