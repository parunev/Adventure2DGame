package entity;

import main.gamePanel;
import main.keyHandler;
import main.userInterface;
import object.objectShield;
import object.objectSword;

import java.awt.*;
import java.awt.image.BufferedImage;

public class player extends entity{

    keyHandler keyH;
    public final int screenX;
    public final int screenY;
    public boolean attackCanceled = false;

    //MAIN PLAYER
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

        //PLAYER ATTACK AREA/RANGE
        attackArea.width = 36;
        attackArea.height = 36;

        setDefaultValue();
        getPlayerImage();
        getPlayerAttackImage();
    }

    //DEFAULT DIRECTION OF THE CHARACTER
    public void setDefaultValue(){
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 4;
        direction ="down";

        //PLAYER STATUS
        level = 1;
        maxLife = 6; // 3 hearts = 6, 1half + 1half = 1 heart
        life = maxLife; //starting life
        strength = 1; // the more strength he has, the more damage he gives
        dexterity = 1; // the more dexterity he has, the less damage he receives
        exp = 0;
        nextLevelEXP = 5;
        coin = 0;
        currentWeapon = new objectSword(gp);
        currentShield = new objectShield(gp);
        attack = getAttack(); // the total attack value is decided by strength and weapon
        defence = getDefence(); // the total defence value is decided by dexterity and shield
    }
    public int getAttack(){return attack = strength * currentWeapon.attackValue;}
    public int getDefence(){return defence = dexterity * currentShield.defenceValue;}

    //CHARACTER SPRITES
    public void getPlayerImage(){

        up1 = setup("boy_up_1", gp.tileSize, gp.tileSize);
        up2 = setup("boy_up_2", gp.tileSize, gp.tileSize);
        down1 = setup("boy_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("boy_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("boy_left_1", gp.tileSize, gp.tileSize);
        left2 = setup("boy_left_2", gp.tileSize, gp.tileSize);
        right1 = setup("boy_right_1", gp.tileSize, gp.tileSize);
        right2 = setup("boy_right_2", gp.tileSize, gp.tileSize);
    }

    //CHARACTER ATTACK SRPITES
    public void getPlayerAttackImage(){
        attackUp1 = setup("boy_attack_up_1", gp.tileSize, gp.tileSize*2);
        attackUp2 = setup("boy_attack_up_2", gp.tileSize, gp.tileSize*2);
        attackDown1 = setup("boy_attack_down_1", gp.tileSize, gp.tileSize*2);
        attackDown2 = setup("boy_attack_down_2", gp.tileSize, gp.tileSize*2);
        attackLeft1 = setup("boy_attack_left_1", gp.tileSize*2, gp.tileSize);
        attackLeft2 = setup("boy_attack_left_2", gp.tileSize*2, gp.tileSize);
        attackRight1 = setup("boy_attack_right_1", gp.tileSize*2, gp.tileSize);
        attackRight2 = setup("boy_attack_right_2", gp.tileSize*2, gp.tileSize);
    }

    //UPDATE OUR CHARACTER POSITION AND IMAGE
    public void update(){

        if (attacking){
            attacking();

        }else if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed || keyH.enterPressed){

            //first we check the direction and based on this direction we check the collision
            if (keyH.upPressed){direction = "up";
            }else if (keyH.downPressed){direction = "down";
            }else if (keyH.leftPressed){direction = "left";
            }else if (keyH.rightPressed){direction = "right";}

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

            //IF COLLISION IS FALSE, PLAYER CAN MOVE
            if(!collisionOn && !keyH.enterPressed){
                switch (direction) {
                    case "up" -> worldY -= speed;
                    case "down" -> worldY += speed;
                    case "left" -> worldX -= speed;
                    case "right" -> worldX += speed;
                }
            }
            if (keyH.enterPressed && !attackCanceled){
                gp.playSE(7);
                attacking = true;
                spriteCounter = 0;
            }

            attackCanceled = false;
            gp.keyH.enterPressed = false;

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

    //DAMAGE BETWEEN CHARACTER AND MONSTER
    public void attacking(){
        spriteCounter++;

        if (spriteCounter <= 5){
            spriteNum = 1; // attack image1 (0~5frame)
        }
        if (spriteCounter > 5 && spriteCounter <= 25){
            spriteNum=2; // attack image2 (6~25frame)

            //Save the current worldX, worldY, solidArea
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            //Adjust player's worldX/y for the attackArea
            switch (direction) {
                case "up" -> worldY -= attackArea.height;
                case "down" -> worldY += attackArea.height;
                case "left" -> worldX -= attackArea.width;
                case "right" -> worldX += attackArea.width;
            }

            //ATTACK AREA BECOME SOLID AREA
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            //CHECK MONSTER COLLISION WITH THE UPDATED worldX, worldY, solidArea
            int monsterIndex = gp.cCheker.checkEntity(this, gp.monster);
            damageMonster(monsterIndex);

            //AFTER CHECKING COLLISION RESTORE THE ORIGINAL DATA
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
        }
        if (spriteCounter > 25){
            spriteNum = 1; // when we hit more than 25 we reset
            spriteCounter = 0;
            attacking = false;
        }
    }

    //if we are equal to 999 that means we didn't touch the object. Otherwise, simply delete the object we touched.
    public void pickUpObject(int i){
        if (i != 999){

        }
    }

    //IF YOU HIT THE NPC
    public void interactNPC(int i){
        if (gp.keyH.enterPressed){
            if (i != 999){
                attackCanceled = true;
                gp.gameState = gp.dialogState;
                gp.npc[i].speak();
                }}}

    //PLAYER RECEIVES DMG CONTACTING THE MONSTER
    public void contactMonster(int i){
        if (i != 999){
            // player receives damage only if he's not invincible
            if (!invincible){
                gp.playSE(6);

                int damage = gp.monster[i].attack - defence;
                if (damage<0){ //in case monster defence is greater than players attack we change all the negatives to zero
                    damage = 0;
                }

                life -=damage;
                invincible = true;
            }
        }
    }

    //MONSTER RECEIVES DMG
    public void damageMonster(int i){
        if (i != 999){
            if (!gp.monster[i].invincible){
                gp.playSE(5);


                int damage = attack - gp.monster[i].defence;
                if (damage<0){ //in case monster defence is greater than players attack we change all the negatives to zero
                    damage = 0;
                }

                gp.monster[i].life -= damage;
                userInterface.addMessage(damage + " damage!");
                gp.monster[i].invincible = true;
                gp.monster[i].damageReaction();

                if (gp.monster[i].life <= 0){
                    gp.monster[i].dying = true;
                    userInterface.addMessage("Killed the " + gp.monster[i].name + "!");
                    userInterface.addMessage("EXP + "+gp.monster[i].exp);
                    exp += gp.monster[i].exp;
                    checkLevelUp();
                }
            }
        }
    }
    public void checkLevelUp(){
        if (exp >= nextLevelEXP){
            level++;
            nextLevelEXP = nextLevelEXP*2;
            maxLife += 2;
            strength++;
            dexterity++;
            attack = getAttack();
            defence = getDefence();
            gp.playSE(8);
            gp.gameState = gp.dialogState;
            userInterface.currentDialog = "YOU ARE LEVEL " + level + " NOW!\n"
                    + "YOU FEEL STRONGER!";
        }
    }

    //your paintbrush lol :D
    public void draw(Graphics2D g2){
        int tempScreenX = screenX;
        int tempScreenY = screenY;

        BufferedImage image = null;
        switch (direction) {  //based on direction we pick different image
            case "up" -> {
                if (!attacking){
                    if (spriteNum == 1) {image = up1;}
                    if (spriteNum == 2) {image = up2;}}
                if (attacking){
                    tempScreenY = screenY - gp.tileSize;
                    if (spriteNum == 1) {image = attackUp1;}
                    if (spriteNum == 2) {image = attackUp2;}}}
            case "down" -> {
                if (!attacking){
                    if (spriteNum == 1) {image = down1;}
                    if (spriteNum == 2) {image = down2;}}
                if (attacking){
                    if (spriteNum == 1) {image = attackDown1;}
                    if (spriteNum == 2) {image = attackDown2;}}}
            case "left" -> {
                if (!attacking){
                    if (spriteNum == 1) {image = left1;}
                    if (spriteNum == 2) {image = left2;}}
                if (attacking){
                    tempScreenX = screenX - gp.tileSize;
                    if (spriteNum == 1) {image = attackLeft1;}
                    if (spriteNum == 2) {image = attackLeft2;}}}
            case "right" -> {
                if (!attacking){
                    if (spriteNum == 1) {image = right1;}
                    if (spriteNum == 2) {image = right2;}}
                if (attacking){
                    if (spriteNum == 1) {image = attackRight1;}
                    if (spriteNum == 2) {image = attackRight2;}
                }
                }
        }

        //VISUAL EFFECT TO INVINCIBLE STATE - TRANSPARENCY
        if (invincible){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
        }

        g2.drawImage(image, tempScreenX, tempScreenY,null); // draws an image on the screen

        //RESET ALPHA
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        //DEBUG
//        g2.setFont(new Font("Arial", Font.PLAIN, 26));
//        g2.setColor(Color.white);
//        g2.drawString("Invincible counter: "+invincibleCounter,10,400);
    }
}
