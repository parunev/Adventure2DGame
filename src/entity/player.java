package entity;

import main.gamePanel;
import main.keyHandler;
import main.userInterface;
import main.utilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class player extends entity{

    gamePanel gp;
    keyHandler keyH;

    public final int screenX;
    public final int screenY;
    public int hasKey = 0;

    public player(gamePanel gp, keyHandler keyH){
        this.gp = gp;
        this.keyH = keyH;

        //FIXED POSITION AT THE CENTER OF THE SCREEN
        screenX = gp.screenWidth / 2 - (gp.tileSize/2);
        screenY = gp.screenHeight / 2 - (gp.tileSize/2);

        //PLAYER SOLID AREA
        solidArea = new Rectangle();
        solidArea.x = 7;
        solidArea.y = 15;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;

        setDefaultValue();
        getPlayerImage();
    }

    //DEFAULT DIRECTION OF THE CHARACTER
    public void setDefaultValue(){
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 4;
        direction ="down";
    }

    //CHARACTER SPRITES
    public void getPlayerImage(){

        up1 = setup("boy_up_1");
        up2 = setup("boy_up_2");
        down1 = setup("boy_down_1");
        down2 = setup("boy_down_2");
        left1 = setup("boy_left_1");
        left2 = setup("boy_left_2");
        right1 = setup("boy_right_1");
        right2 = setup("boy_right_2");
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

    //UPDATE OUR CHARACTER POSITION AND IMAGE
    public void update(){

        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed){

            //first we check the direction and based on this direction we check the collision
            if (keyH.upPressed){direction = "up";
            }else if (keyH.downPressed){direction = "down";
            }else if (keyH.leftPressed){direction = "left";
            }else {direction = "right";}

            //CHECK TILE COLLISION
            collisionOn = false;
            gp.cCheker.checkTile(this);

            //CHECK OBJECT COLLISION
            int objIndex = gp.cCheker.checkObject(this, true);
            pickUpObject(objIndex);

            //IF COLLISION IS FALSE, PLAYER CAN MOVE
            if(!collisionOn){
                switch (direction) {
                    case "up" -> worldY -= speed;
                    case "down" -> worldY += speed;
                    case "left" -> worldX -= speed;
                    case "right" -> worldX += speed;
                }
            }
            spriteCounter++;  // the sprite counter only increase if one of the above is true. etc. the character is in still position
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

    //if we are equal to 999 that means we didn't touch the object. Otherwise, simply delete the object we touched.
    public void pickUpObject(int i){
        if (i != 999){
            String objectName = gp.obj[i].name;
            switch (objectName){
                case"key": // collecting keys

                    gp.playSE(1);
                    hasKey++;
                    gp.obj[i]=null;
                    gp.UI.showMessage("You collected a key!");
                    break;
                case"Door": // if we don't have key we can't open the door
                    if (hasKey>0){
                        gp.playSE(3);
                        gp.obj[i]=null;
                        hasKey--;
                        gp.UI.showMessage("You opened a door!");
                    }else{
                        gp.UI.showMessage("You need to find a key!");
                    }
                    break;
                case"Boots":
                    gp.playSE(2);
                    speed += 2;
                    gp.obj[i] = null;
                    gp.UI.showMessage("You received a speed up!");
                    break;
                case"Chest":
                    userInterface.gameFinish = true;
                    gp.stopMusic();
                    gp.playSE(4);
                    break;
            }
        }
    }

    //your paintbrush lol :D
    public void draw(Graphics2D g2){
        BufferedImage image = null;
        switch (direction) {  //based on direction we pick different image
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
        g2.drawImage(image, screenX, screenY,null); // draws an image on the screen
    }
}
