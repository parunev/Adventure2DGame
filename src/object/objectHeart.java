package object;

import main.gamePanel;

public class objectHeart extends entity.entity{

    public objectHeart(gamePanel gp){
        super(gp);

        name = "Heart";
        image = setup("heart_full", gp.tileSize, gp.tileSize);
        image2 = setup("heart_half", gp.tileSize, gp.tileSize);
        image3 = setup("heart_blank", gp.tileSize, gp.tileSize);
    }
}
