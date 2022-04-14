package entity;

import main.gamePanel;

public class projectile extends  entity{

    entity user;

    public projectile(gamePanel gp) {
        super(gp);
    }

    //SHOOTING PARAMETERS
    public void set(int worldX, int worldY, String direction, boolean alive, entity user){

        this.worldX = worldX;
        this.worldY = worldY;
        this.direction = direction;
        this.alive = alive;
        this.user = user;
        this.life = this.maxLife; // reset the life to the max value every time you shoot it
    }

    //MOVING
    public void update(){

        //CHECK WHO SHOOTS WITH PROJECTILE
        //HIT DETECTION
        if (user == gp.player){
            int monsterIndex = gp.cCheker.checkEntity(this, gp.monster);
            if (monsterIndex != 999){
                gp.player.damageMonster(monsterIndex, attack);
                alive = false; // if the projectile hits a monster, it dies (disappear)
            }
        }
        if (user != gp.player){
            boolean contactPlayer = gp.cCheker.checkPlayer(this);
            //this means it can hit player and deal damage
            if (!gp.player.invincible && contactPlayer){
                damagePlayer(attack);
                alive = false;
            }
        }

        //Just like other npcs or monsters, projectiles move based on its speed and direction
        switch (direction) {
            case "up" -> worldY -= speed;
            case "down" -> worldY += speed;
            case "left" -> worldX -= speed;
            case "right" -> worldX += speed;
        }
        life--;
        if (life <= 0){
            alive = false;
        }

        //the good old sprite counter - also the same
        spriteCounter++;
        if (spriteCounter > 12){
            if (spriteNum == 1){
                spriteNum = 2;
            }else if (spriteNum == 2){
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }
    public boolean haveResource(entity user) {
        return false;
    }
    public void subtractResource(entity user){}
}
