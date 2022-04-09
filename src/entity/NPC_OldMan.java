package entity;

import main.gamePanel;

import java.util.Random;

public class NPC_OldMan extends entity{


    public NPC_OldMan(gamePanel gp) {
        super(gp);
        direction = "down";
        speed = 1;
        getImage();
        setDialog();
    }
    public void getImage(){

        up1 = setup("oldman_up_1");
        up2 = setup("oldman_up_2");
        down1 = setup("oldman_down_1");
        down2 = setup("oldman_down_2");
        left1 = setup("oldman_left_1");
        left2 = setup("oldman_left_2");
        right1 = setup("oldman_right_1");
        right2 = setup("oldman_right_2");
    }
    //DIALOG PRESETS
    public void setDialog(){
        dialogues[0]="Hello, lad.";
        dialogues[1]="So you've come to this island to \nfind the treasure?";
        dialogues[2]="I used to be a great wizard but now... \nI'm a bit too old for taking an adventure.";
        dialogues[3]="Well, good luck on you.";
    }
    //CHARACTER BEHAVIOR - AI
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
    //leaving this method here because the character might do some specific stuff
    public void speak(){
        super.speak();
    }
}
