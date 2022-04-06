package entity;

import main.gamePanel;
import main.keyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class player extends entity{

    gamePanel gp;
    keyHandler keyH;
    //they're final because they do not change players screen position
    public final int screenX;
    public final int screenY;

    //implement at gamePanel
    public player(gamePanel gp, keyHandler keyH){
        this.gp = gp;
        this.keyH = keyH;

        //halfway point of the screen
        //fixed at the center of the screen
        screenX = gp.screenWidth / 2 - (gp.tileSize/2);
        screenY = gp.screenHeight / 2 - (gp.tileSize/2);

        setDefaultValue();
        getPlayerImage();
    }

    //default direction of the character
    public void setDefaultValue(){
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 4;
        direction ="down";
    }

    //character sprites
    public void getPlayerImage(){
        try{
            up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/boy_up_1.png")));
            up2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/boy_up_2.png")));
            down1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/boy_down_1.png")));
            down2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/boy_down_2.png")));
            left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/boy_left_1.png")));
            left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/boy_left_2.png")));
            right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/boy_right_1.png")));
            right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/boy_right_2.png")));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    //update our character position and image
    public void update(){

        if (keyH.upPressed || keyH.downPressed
                || keyH.leftPressed || keyH.rightPressed){

            if (keyH.upPressed){
                direction = "up";
                worldY -= speed;
            }else if (keyH.downPressed){
                direction = "down";
                worldY += speed;
            }else if (keyH.leftPressed){
                direction = "left";
                worldX -= speed;
            }else {
                direction = "right";
                worldX += speed;
            }

            // the sprite counter only increase if one of the above is true. etc. the character is in still position
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
    }

    //your paintbrush lol :D
    public void draw(Graphics2D g2){

        //based on direction we pick different image
        BufferedImage image = null;
        switch (direction) {
            case "up" -> {
                if (spriteNum == 1) {
                    image = up1;
                }
                if (spriteNum == 2) {
                    image = up2;
                }
            }
            case "down" -> {
                if (spriteNum == 1) {
                    image = down1;
                }
                if (spriteNum == 2) {
                    image = down2;
                }
            }
            case "left" -> {
                if (spriteNum == 1) {
                    image = left1;
                }
                if (spriteNum == 2) {
                    image = left2;
                }
            }
            case "right" -> {
                if (spriteNum == 1) {
                    image = right1;
                }
                if (spriteNum == 2) {
                    image = right2;
                }
            }
        }
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize,null); // draws an image on the screen
    }
}
