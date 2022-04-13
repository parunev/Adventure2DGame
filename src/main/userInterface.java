package main;

import entity.entity;
import object.objectHeart;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class userInterface {

    static gamePanel gp;
    public static Graphics2D g2;
    static Font pixelFont;
    static BufferedImage heart_full;
    static BufferedImage heart_half;
    static BufferedImage heart_blank;
    public static boolean messageOn = false;
    public static ArrayList<String> message = new ArrayList<>();
    public static ArrayList<Integer> messageCounter = new ArrayList<>();
    public static boolean gameFinish = false;
    public static String currentDialog = "";
    public static int commandNum = 0;
    public static int slotCol = 0;
    public static int slotRow = 0;


    public userInterface(gamePanel gp){
        userInterface.gp = gp;

        try{
            InputStream is = getClass().getResourceAsStream("/res/font.ttf");
            assert is != null;
            pixelFont = Font.createFont(Font.TRUETYPE_FONT, is);
        }catch (FontFormatException | IOException e){
            e.printStackTrace();
        }

        // CREATE HUD OBJECT
        entity heart = new objectHeart(gp);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_blank = heart.image3;

    }

    //ADDING MESSAGES
    public static void addMessage(String text){
        message.add(text);
        messageCounter.add(0);

    }

    //SETTING UP GAME STATES
    public static void draw(Graphics2D g2){
        userInterface.g2 = g2;
        g2.setFont(pixelFont);
        g2.setColor(Color.white);
        //TITLE STATE
        if (gp.gameState == gp.titleState){
            drawTitleScreen();
        }

        //PLAY STATE
        if (gp.gameState == gp.playState){
            drawPlayerLife(); // displaying while playing
            drawMessage();
        }

        //PAUSE STATE
        if (gp.gameState == gp.pauseState){
            drawPlayerLife(); // still displaying while pausing
            drawPauseScreen();
        }

        //DIALOG STATE
        if(gp.gameState == gp.dialogState){
            drawPlayerLife(); // still displaying while talking to npcs
            drawDialogScreen();
        }

        //CHARACTER STATE
        if (gp.gameState == gp.characterState){
            drawCharacterScreen();
            drawInventory();
        }
    }

    //DRAWING PLAYER HEARTS
    public static void drawPlayerLife(){

        int x = gp.tileSize/2;
        int y = gp.tileSize/2;
        int i = 0;

        //DRAW MAX LIFE
        while (i < gp.player.maxLife/2){
            g2.drawImage(heart_blank, x, y, null);
            i++;
            x += gp.tileSize;
        }

        //RESET
        x = gp.tileSize/2;
        i = 0;

        //DRAW CURRENT LIFE
        while (i < gp.player.life){
            g2.drawImage(heart_half, x,y, null);
            i++;
            if (i < gp.player.life){
                g2.drawImage(heart_full, x,y,null);
            }
            i++;
            x += gp.tileSize;
        }

    }

    //SETTING UP DISAPPEARING MESSAGES
    public static void drawMessage(){
        int messageX = gp.tileSize;
        int messageY = gp.tileSize*4;

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,28F));
        for (int i = 0; i < message.size() ; i++) {
            if (message.get(i) != null){
                //shadow
                g2.setColor(Color.black);
                g2.drawString(message.get(i),messageX+2,messageY+2);
                //text
                g2.setColor(Color.WHITE);
                g2.drawString(message.get(i),messageX,messageY);

                int counter = messageCounter.get(i)+1; // message counter++
                messageCounter.set(i,counter); //set the counter to the array
                messageY+=50;

                if (messageCounter.get(i) > 180){
                    message.remove(i);
                    messageCounter.remove(i);
                }
            }
        }
    }

    //SETTING UP TITLE SCREEN
    public static void drawTitleScreen(){
        //TITLE BACKGROUND COLOR
        g2.setColor(new Color(70, 120, 80));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        //TITLE NAME
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 66F));
        String text = "Adventure 2D Game";
        int x = getXFForCenteredText(text);
        int y = gp.tileSize*2;

        //SHADOW ON MAIN TEXT
        g2.setColor(Color.black);
        g2.drawString(text, x+5, y+5);

        //TITLE MAIN COLOR
        g2.setColor(Color.white);
        g2.drawString(text, x, y);

        //DISPLAYING CHARACTER IMAGE
        x = gp.screenWidth/2 - (gp.tileSize*2)/2;
        y += gp.tileSize*2;
        g2.drawImage(gp.player.down1, x, y+20, gp.tileSize*2, gp.tileSize*2, null);

        //MENU
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 38F));

        text = "NEW GAME";
        x = getXFForCenteredText(text);
        y += gp.tileSize *3.5;
        g2.drawString(text, x, y);
        if (commandNum == 0){
            g2.drawString(">", x -gp.tileSize, y);
        }

        text = "LOAD GAME";
        x = getXFForCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if (commandNum == 1){
            g2.drawString(">", x -gp.tileSize, y);
        }

        text = "QUIT GAME";
        x = getXFForCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if (commandNum == 2){
            g2.drawString(">", x -gp.tileSize, y);
        }

    }

    //SETTING UP PAUSE
    public static void drawPauseScreen(){
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 60));
        String text = "PAUSED";
        int x = getXFForCenteredText(text);
        int y = gp.screenHeight/2;

        g2.drawString(text, x, y);
    }

    //SETTING UP DIALOGUE
    public static void drawDialogScreen(){

        //DIALOG WINDOW PARAMETERS
        int x = gp.tileSize*2;
        int y = gp.tileSize/2;
        int width = gp.screenWidth - (gp.tileSize*4);
        int height = gp.tileSize*4;
        drawSubWindows(x,y,width, height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 25));
        x += gp.tileSize;
        y += gp.tileSize;

        for(String line: currentDialog.split("\n")){
            g2.drawString(line, x, y);
            y+=40;
        }
    }

    //SETTING UP CHARACTER STATS
    public static void drawCharacterScreen(){

        //CREATE A FRAME
        final int frameX = gp.tileSize;
        final int frameY = gp.tileSize;
        final int frameWidth = gp.tileSize*5;
        final int frameHeight = gp.tileSize*10;
        drawSubWindows(frameX, frameY, frameWidth, frameHeight);

        //TEXT
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(24F));

        int textX = frameX + 20;
        int textY = frameY + gp.tileSize;
        final int lineHeight = 35;

        //NAMES
        g2.drawString("LEVEL", textX, textY);
        textY += lineHeight;
        g2.drawString("LIFE:", textX, textY);
        textY += lineHeight;
        g2.drawString("STRENGTH:", textX, textY);
        textY += lineHeight;
        g2.drawString("DEXTERITY:", textX, textY);
        textY += lineHeight;
        g2.drawString("ATTACK:", textX, textY);
        textY += lineHeight;
        g2.drawString("DEFENCE:", textX, textY);
        textY += lineHeight;
        g2.drawString("EXP:", textX, textY);
        textY += lineHeight;
        g2.drawString("NEXT LEVEL:", textX, textY);
        textY += lineHeight;
        g2.drawString("COINS:", textX, textY);
        textY += lineHeight+20;
        g2.drawString("WEAPON:", textX, textY);
        textY += lineHeight+15;
        g2.drawString("SHIELD:", textX, textY);

        //VALUES
        int tailX = (frameX + frameWidth) - 30;

        //RESET TEXTY
        textY = frameY + gp.tileSize;
        String value;

        value = String.valueOf(gp.player.level);
        textX = getXFForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = gp.player.life + "/" + gp.player.maxLife;
        textX = getXFForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.strength);
        textX = getXFForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.dexterity);
        textX = getXFForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.attack);
        textX = getXFForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.defence);
        textX = getXFForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.exp);
        textX = getXFForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.nextLevelEXP);
        textX = getXFForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.coin);
        textX = getXFForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight + 20;


        g2.drawImage(gp.player.currentWeapon.down1, tailX - gp.tileSize, textY-32, null);
        textY += gp.tileSize;
        g2.drawImage(gp.player.currentShield.down1, tailX - gp.tileSize, textY-32, null);

    }

    //SETTING UP INVENTORY
    public static void drawInventory(){

        //FRAME
        int frameX = gp.tileSize*9;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize*6;
        int frameHeight= gp.tileSize*5;
        drawSubWindows(frameX, frameY, frameWidth, frameHeight);

        //SLOT
        final int slotXStart = frameX +20;
        final int slotYStart = frameY +20;
        int slotX = slotXStart;
        int slotY = slotYStart;
        int slotSize = gp.tileSize+3;

        // DRAW PLAYER ITEMS
        for (int i = 0; i <gp.player.inventory.size() ; i++) {
            g2.drawImage(gp.player.inventory.get(i).down1,slotX,slotY,null);
            slotX+=slotSize;
            if (i == 4 || i == 9 || i == 14 ){
                slotX = slotXStart;
                slotY += slotSize;
            }
        }

        //CURSOR
        int cursorX = slotXStart + (slotSize*slotCol);
        int cursorY = slotYStart + (slotSize*slotRow);
        int cursorWidth = gp.tileSize;
        int cursorHeight = gp.tileSize;

        //DRAW CURSOR
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight,10,10);

        //DESCRIPTION FRAME
        int dFrameX = frameX;
        int dFrameY = frameY + frameHeight;
        int dFrameWidth = frameWidth;
        int dFrameHeight = gp.tileSize*3;


        //DRAW DESCRIPTION TEXT
        int textX = dFrameX+20;
        int textY = dFrameY+gp.tileSize;
        g2.setFont(g2.getFont().deriveFont(28F));

        int itemIndex = getItemIndexOnSlot();
        if (itemIndex < gp.player.inventory.size()){

            drawSubWindows(dFrameX, dFrameY, dFrameWidth, dFrameHeight);

            for (String line : gp.player.inventory.get(itemIndex).description.split("\n")) {
                g2.drawString(line, textX, textY);
                textY+=32;
            }
        }
    }

    //FIXED METHODS
    public static int getItemIndexOnSlot(){
        return slotCol + (slotRow*5);
    }
    public static void drawSubWindows(int x, int y, int width, int height){

        Color c = new Color(0, 0,0,210 ); //setting up customized rgb color, the alpha value is adjusting the opacity
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35,35);

        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke()); // defines the width of outlines of graphics which are rendered with Graphics2D
        g2.drawRoundRect(x+5, y+5,width-10,height-10,25,25);

    }
    public static int getXFForCenteredText(String text){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.screenWidth/2 - length/2;
    }
    public static int getXFForAlignToRightText(String text, int tailX){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return tailX - length;
    }

}
