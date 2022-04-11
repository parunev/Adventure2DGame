package entity;

import main.gamePanel;
import main.keyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class player extends entity{

    keyHandler keyH;
    public final int screenX;
    public final int screenY;

    public player(gamePanel gp, keyHandler keyH){
        super(gp);
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

        //PLAYER STATUS
        maxLife = 6; // 3 hearts = 6, 1half + 1half = 1 heart
        life = maxLife; //starting life
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

            //CHECK NPC COLLISION
            int npcIndex = gp.cCheker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);

            //CHECK MONSTER COLLISION
            int monsterIndex = gp.cCheker.checkEntity(this, gp.monster);
            contactMonster(monsterIndex);

            //CHECK EVENT
            gp.eHandler.checkEvent();

            gp.keyH.enterPressed = false;

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
        //This needs to be outside of key if-statement
        if (invincible){
            invincibleCounter++;
            if (invincibleCounter>60){ // we can receive damage every 1 second
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }

    //if we are equal to 999 that means we didn't touch the object. Otherwise, simply delete the object we touched.
    public void pickUpObject(int i){
        if (i != 999){

        }
    }
    //IF YOU HIT THE NPC
    public void interactNPC(int i){
        if (i != 999){
            if (gp.keyH.enterPressed){
                gp.gameState = gp.dialogState;
                gp.npc[i].speak();
            }
        }
    }
    //PLAYER RECEIVES DMG CONTACTING THE MONSTER
    public void contactMonster(int i){
        if (i != 999){
            // player receives damage only if he's not invincible
            if (!invincible){
                life -=1;
                invincible = true;
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

        //VISUAL EFFECT TO INVINCIBLE STATE - TRANSPARENCY
        if (invincible){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }

        g2.drawImage(image, screenX, screenY,null); // draws an image on the screen

        //RESET ALPHA
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        //DEBUG
//        g2.setFont(new Font("Arial", Font.PLAIN, 26));
//        g2.setColor(Color.white);
//        g2.drawString("Invincible counter: "+invincibleCounter,10,400);
    }
}
