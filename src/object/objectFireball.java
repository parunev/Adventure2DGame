package object;

import main.gamePanel;

public class objectFireball extends entity.projectile{

    public objectFireball(gamePanel gp) {
        super(gp);

        name = "Fireball";
        speed = 5;
        maxLife = 80;
        life = maxLife;
        attack = 2;
        useCost = 1;
        alive = false;
        getImage();
    }

    public void getImage(){
        up1    =setup("fireball_up_1",gp.tileSize, gp.tileSize);
        up2    =setup("fireball_up_2",gp.tileSize, gp.tileSize);
        down1  =setup("fireball_down_1",gp.tileSize, gp.tileSize);
        down2  =setup("fireball_down_2",gp.tileSize, gp.tileSize);
        left1  =setup("fireball_left_1",gp.tileSize, gp.tileSize);
        left2  =setup("fireball_left_2",gp.tileSize, gp.tileSize);
        right1 =setup("fireball_right_1",gp.tileSize, gp.tileSize);
        right2 =setup("fireball_right_2",gp.tileSize, gp.tileSize);
    }
}
