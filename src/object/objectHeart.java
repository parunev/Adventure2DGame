package object;

import main.gamePanel;

public class objectHeart extends entity.entity{

    public objectHeart(gamePanel gp){
        super(gp);

        name = "Heart";
        image = setup("heart_full");
        image2 = setup("heart_half");
        image3 = setup("heart_blank");
    }
}
