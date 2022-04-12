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

    public gamePanel gp;

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2;
    public BufferedImage image, image2, image3;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48); //default solid area
    public Rectangle attackArea = new Rectangle(0, 0, 0, 0); // indicates entity attack area
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
    String[]dialogues = new String[20];

    //STATE
    public int worldX, worldY;
    public String direction = "down";
    public int spriteNum = 1;
    int dialogueIndex = 0;
    public boolean collision = false;
    public boolean invincible = false;
    public boolean attacking = false;

    //COUNTER
    public int spriteCounter = 0;
    public int invincibleCounter = 0;
    public int actionLockCounter = 0;

    //CHARACTER ATTRIBUTES
    public int type; //0 player, 1 npc, 2 monster
    public String name;
    public int speed;
    public int maxLife;
    public int life;


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
    //CHECK COLLISION BETWEEN ENTITIES
    public void update(){
        setAction();

        collisionOn = false;
        gp.cCheker.checkTile(this);
        gp.cCheker.checkObject(this, false);
        gp.cCheker.checkEntity(this, gp.npc);
        gp.cCheker.checkEntity(this, gp.monster);
        boolean contactPlayer = gp.cCheker.checkPlayer(this);

        if (this.type == 2 && contactPlayer){
            if (!gp.player.invincible){ // we can give damage
                gp.player.life -=1;
                gp.player.invincible = true;
            }
        }

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
        if (invincible){
            invincibleCounter++;
            if (invincibleCounter>40){
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

            if (invincible){
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
            }
            // tile[tileNum].image is getting number from the txt. 0 - grass, 1 - wall, 2 - water etc. etc.
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }
    }
    public BufferedImage setup(String imageName, int width, int height){
        utilityTool uTool = new utilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read((Objects.requireNonNull(getClass().getResourceAsStream("/res/" + imageName + ".png"))));
            image = uTool.scaleImage(image, width, height);

        }catch (IOException e){
            e.printStackTrace();
        }
        return image;
    }
}
