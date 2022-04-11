package monster;

import main.gamePanel;

import java.util.Random;

public class greenSlime extends entity.entity{

    public greenSlime(gamePanel gp) {
        super(gp);

        type = 2;
        name = "Green Slime";
        speed = 1;
        maxLife = 4;
        life = maxLife;

        //SOLID AREA
        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
    }
    //LOADING AND SCALING
    public void getImage(){

        //USING TWO IMAGES FOR ALL DIRECTIONS
        up1    = setup("greenslime_down_1");
        up2    = setup("greenslime_down_2");
        down1  = setup("greenslime_down_1");
        down2  = setup("greenslime_down_2");
        left1  = setup("greenslime_down_1");
        left2  = setup("greenslime_down_2");
        right1 = setup("greenslime_down_1");
        right2 = setup("greenslime_down_2");
    }

    //SETTING BEHAVIOR
    public void setAction(){

        actionLockCounter++;
        if (actionLockCounter == 120){ // every ** seconds the npc move
            Random random = new Random();
            int i = random.nextInt(100)+1; // pick up a number from 1 to 100

            //randomizing the npcs movement
            if (i <= 25){direction = "up";}
            if (i > 25 && i <= 50){direction = "down";}
            if (i > 50 && i <= 75){direction = "left";}
            if (i > 75){direction = "right";}
            actionLockCounter = 0;
        }
    }

}
