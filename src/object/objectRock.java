package object;

import main.gamePanel;

public class objectRock extends entity.projectile{

    public objectRock(gamePanel gp) {
        super(gp);

        name = "Rock";
        speed = 8;
        maxLife = 80;
        life = maxLife;
        attack = 2;
        useCost = 1;
        alive = false;
        getImage();
    }

    public void getImage(){
        up1    =setup("rock_down_1",gp.tileSize, gp.tileSize);
        up2    =setup("rock_down_1",gp.tileSize, gp.tileSize);
        down1  =setup("rock_down_1",gp.tileSize, gp.tileSize);
        down2  =setup("rock_down_1",gp.tileSize, gp.tileSize);
        left1  =setup("rock_down_1",gp.tileSize, gp.tileSize);
        left2  =setup("rock_down_1",gp.tileSize, gp.tileSize);
        right1 =setup("rock_down_1",gp.tileSize, gp.tileSize);
        right2 =setup("rock_down_1",gp.tileSize, gp.tileSize);
    }
}
