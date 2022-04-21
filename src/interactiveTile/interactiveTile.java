package interactiveTile;

import entity.entity;
import main.gamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class interactiveTile extends entity {
    gamePanel gp;
    public boolean destructible = false;

    public interactiveTile(gamePanel gp, int col, int row) {
        super(gp);
        this.gp = gp;
    }
    public boolean isCorrectItem(entity entity){
        return false;
    }
    public void playSE(){

    }
    public interactiveTile getDestroyedForm(){
        interactiveTile tile = null;
        return tile;
    }
    public void update(){
        if (invincible){
            invincibleCounter++;
                if (invincibleCounter>20){
                    invincible = false;
                    invincibleCounter = 0;
                }
            }
        }
        public void draw(Graphics2D g2){
            BufferedImage image = null;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                    worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                    worldY + gp.tileSize> gp.player.worldY - gp.player.screenY &&
                    worldY - gp.tileSize < gp.player.worldY + gp.player.screenY){

                g2.drawImage(down1, screenX, screenY,null);
            }
        }
    }
