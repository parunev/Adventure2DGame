package entity;

import main.gamePanel;

import java.awt.*;

public class particle extends entity{

    entity generator;
    Color color;
    int size;
    int xd;
    int yd;

    public particle(gamePanel gp, entity generator, Color color, int size, int speed, int maxLife, int xd, int yd) {
        super(gp);

        this.generator = generator;
        this.color = color;
        this.size = size;
        this.speed = speed;
        this.maxLife = maxLife;
        this.xd = xd;
        this.yd = yd;

        life = maxLife;
        int offSet = (gp.tileSize/2) - (size/2);
        worldX = generator.worldX + offSet;
        worldY = generator.worldY + offSet;
    }
    public void update(){
        life--;

        if (life < maxLife/3){
            yd++;
        }

        worldX += xd*speed;
        worldY += yd*speed;
        if (life == 0){
            alive = false;
        }

    }
    public void draw(Graphics2D g2){
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        g2.setColor(color);
        g2.fillRect(screenX, screenY,size, size);
    }
}
