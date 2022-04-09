package entity;

import main.gamePanel;
import main.userInterface;
import main.utilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class entity {

    gamePanel gp;
    public int worldX, worldY;
    public int speed;

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction;
    public int spriteCounter = 0;
    public int spriteNum = 1;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48); //default solid area
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
    public int actionLockCounter = 0;
    String[]dialogues = new String[20];
    int dialogueIndex = 0;

    public entity(gamePanel gp){
        this.gp = gp;
    }
    public void setAction(){}
    public void speak(){

        if (dialogues[dialogueIndex]==null){ // if there is no text we got back to index 0
            dialogueIndex = 0;
        }
        userInterface.currentDialog = dialogues[dialogueIndex];
        dialogueIndex++;

        // fixing npc dialogue direction. making it more natural.
        switch (gp.player.direction) {
            case "up" -> direction = "down";
            case "down" -> direction = "up";
            case "left" -> direction = "right";
            case "right" -> direction = "left";
        }
    }
    public void update(){
        setAction();

        collisionOn = false;
        gp.cCheker.checkTile(this);
        gp.cCheker.checkObject(this, false);
        gp.cCheker.checkPlayer(this);

        if(!collisionOn){
            switch (direction) {
                case "up" -> worldY -= speed;
                case "down" -> worldY += speed;
                case "left" -> worldX -= speed;
                case "right" -> worldX += speed;
            }
        }
        spriteCounter++;
        if (spriteCounter > 10){
            if (spriteNum == 1){
                spriteNum =2;
            }else if (spriteNum == 2){
                spriteNum =1;
            }
            spriteCounter = 0;
        }
    }
    public void draw(Graphics2D g2, gamePanel gamePanel){
        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize> gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY){

            switch (direction) {
                case "up" -> {
                    if (spriteNum == 1) {image = up1;}
                    if (spriteNum == 2) {image = up2;}}
                case "down" -> {
                    if (spriteNum == 1) {image = down1;}
                    if (spriteNum == 2) {image = down2;}}
                case "left" -> {
                    if (spriteNum == 1) {image = left1;}
                    if (spriteNum == 2) {image = left2;}}
                case "right" -> {
                    if (spriteNum == 1) {image = right1;}
                    if (spriteNum == 2) {image = right2;}}
            }

            // tile[tileNum].image is getting number from the txt. 0 - grass, 1 - wall, 2 - water etc. etc.
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }
    public BufferedImage setup(String imageName){
        utilityTool uTool = new utilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read((Objects.requireNonNull(getClass().getResourceAsStream("/res/" + imageName + ".png"))));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);

        }catch (IOException e){
            e.printStackTrace();
        }
        return image;
    }
}
