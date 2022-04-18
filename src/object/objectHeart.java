package object;

import entity.entity;
import main.gamePanel;
import main.userInterface;

public class objectHeart extends entity {

    public objectHeart(gamePanel gp){
        super(gp);

        type = typePickup;
        name = "Heart";
        value = 2;
        down1 = setup("heart_full", gp.tileSize, gp.tileSize);
        image = setup("heart_full", gp.tileSize, gp.tileSize);
        image2 = setup("heart_half", gp.tileSize, gp.tileSize);
        image3 = setup("heart_blank", gp.tileSize, gp.tileSize);
    }
    public void use(entity entity){
        gp.playSE(2);
        userInterface.addMessage("Life +" + value);
        entity.life += value;
    }
}
