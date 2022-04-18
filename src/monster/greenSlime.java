package monster;

import main.gamePanel;
import object.objectCoin;
import object.objectHeart;
import object.objectManaCrystal;
import object.objectRock;

import java.util.Random;

public class greenSlime extends entity.entity{

    //MAIN
    public greenSlime(gamePanel gp) {
        super(gp);

        type = typeMonster;
        name = "Green Slime";
        speed = 1;
        maxLife = 6;
        life = maxLife;
        attack = 2;
        defence = 0;
        exp = 2;
        projectile = new objectRock(gp);

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
        up1    = setup("greenslime_down_1", gp.tileSize, gp.tileSize);
        up2    = setup("greenslime_down_2", gp.tileSize, gp.tileSize);
        down1  = setup("greenslime_down_1", gp.tileSize, gp.tileSize);
        down2  = setup("greenslime_down_2", gp.tileSize, gp.tileSize);
        left1  = setup("greenslime_down_1", gp.tileSize, gp.tileSize);
        left2  = setup("greenslime_down_2", gp.tileSize, gp.tileSize);
        right1 = setup("greenslime_down_1", gp.tileSize, gp.tileSize);
        right2 = setup("greenslime_down_2", gp.tileSize, gp.tileSize);
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

        //SLIME RANDOMLY SHOOTS A ROCK
        int i = new Random().nextInt(100)+1;
        if (i > 99 && !projectile.alive && shotAvailableCounter == 30){
            projectile.set(worldX, worldY, direction,true, this);
            gp.projectileList.add(projectile);
            shotAvailableCounter = 0;
        }
    }

    //BEHAVIOUR
    public void damageReaction(){

        //WHEN THE MONSTER RECEIVES DAMAGE ITS START MOVING IN THE DIRECTION PLAYER IS FACING
        actionLockCounter = 0;
        direction = gp.player.direction;
    }

    //THIS METHOD IS CALLED WHEN THE MONSTER DIES
    public void checkDrop(){

        //CAST A DIE
        int i = new Random().nextInt(100)+1;

        //SET THE MONSTER DROP BY THIS NUMBER
        if (i < 50){dropItem(new objectCoin(gp));} // 50% of the time drops a coin
        if (i >= 50 && i < 75){dropItem(new objectHeart(gp));} // 25% of the time drops a heart
        if (i >= 75 && i < 100){dropItem(new objectManaCrystal(gp));} // 25% of the time drops a mana
    }

}
