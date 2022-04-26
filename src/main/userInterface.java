package main;

import entity.entity;
import object.objectHeart;
import object.objectManaCrystal;

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
    static BufferedImage crystal_full;
    static BufferedImage crystal_blank;
    public static boolean messageOn = false;
    public static ArrayList<String> message = new ArrayList<>();
    public static ArrayList<Integer> messageCounter = new ArrayList<>();
    public static boolean gameFinish = false;
    public static String currentDialog = "";
    public static int commandNum = 0;
    public static int slotCol = 0;
    public static int slotRow = 0;
    static int subState = 0;


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
        entity crystal = new objectManaCrystal(gp);
        crystal_full =  crystal.image;
        crystal_blank =  crystal.image2;

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

        //OPTIONS STATE
        if (gp.gameState == gp.optionsState){
            drawOptionsScreen();

        }
    }

    //DRAWING PLAYER HEARTS AND MANA
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

        //DRAW MAX MANA
        x = gp.tileSize/2-5;
        y = (int)(gp.tileSize*1.5);
        i = 0;
        while (i < gp.player.maxMana){
            g2.drawImage(crystal_blank, x, y, null);
            i++;
            x += 35;
        }

        //DRAW MANA
        x = gp.tileSize/2-5;
        y = (int)(gp.tileSize*1.5);
        i = 0;
        while (i < gp.player.mana){
            g2.drawImage(crystal_full, x, y, null);
            i++;
            x+= 35;
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
        final int frameX = gp.tileSize*2;
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
        g2.drawString("MANA:", textX, textY);
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
        textY += lineHeight+10;
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

        value = gp.player.mana + "/" + gp.player.maxMana;
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


        g2.drawImage(gp.player.currentWeapon.down1, tailX - gp.tileSize, textY-42, null);
        textY += gp.tileSize;
        g2.drawImage(gp.player.currentShield.down1, tailX - gp.tileSize, textY-42, null);

    }

    //SETTING UP INVENTORY
    public static void drawInventory(){

        //FRAME
        int frameX = gp.tileSize*12;
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
            //EQUIP CURSOR
            if (gp.player.inventory.get(i)==gp.player.currentWeapon || gp.player.inventory.get(i)==gp.player.currentShield){
                g2.setColor(new Color(240, 190,90));
                g2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 10, 10);
            }



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
        int dFrameY = frameY + frameHeight;
        int dFrameHeight = gp.tileSize*3;


        //DRAW DESCRIPTION TEXT
        int textX = frameX +20;
        int textY = dFrameY+gp.tileSize;
        g2.setFont(g2.getFont().deriveFont(25F));

        int itemIndex = getItemIndexOnSlot();
        if (itemIndex < gp.player.inventory.size()){

            drawSubWindows(frameX, dFrameY, frameWidth, dFrameHeight);

            for (String line : gp.player.inventory.get(itemIndex).description.split("\n")) {
                g2.drawString(line, textX, textY);
                textY+=32;
            }
        }
    }

    //SETTING UP OPTION MENU
    public static void drawOptionsScreen(){

        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(24F));

        //SUB WINDOW
        int frameX = gp.tileSize*6;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize*8;
        int frameHeight = gp.tileSize*10;
        drawSubWindows(frameX, frameY, frameWidth, frameHeight);

        switch (subState) {
            case 0 -> options_top(frameX, frameY);
            case 1 -> options_fullScreenNotification(frameX, frameY);
            case 2 -> options_controls(frameX, frameY);
            case 3 -> options_endGameConfirmation(frameX,frameY);
        }

        gp.keyH.enterPressed = false;
    }
    public static void options_top(int frameX, int frameY){
        int textX;
        int textY;

        //TITLE
        String text = "Options";
        textX = getXFForCenteredText(text);
        textY = frameY+gp.tileSize;
        g2.drawString(text, textX, textY);

        //FULL SCREEN ON/OFF
        textX = frameX + gp.tileSize;
        textY += gp.tileSize*2;
        g2.drawString("Full screen", textX, textY);
        if (commandNum == 0){
            g2.drawString(">",textX-25, textY);
            if (gp.keyH.enterPressed){
                if (!gp.fullScreenOn) {
                    gp.fullScreenOn = true;
                }else if (gp.fullScreenOn){
                    gp.fullScreenOn = false;
                }
                subState = 1;
            }
        }

        //MUSIC
        textY += gp.tileSize;
        g2.drawString("Music", textX, textY);
        if (commandNum == 1){
            g2.drawString(">",textX-25, textY);
        }

        //SE
        textY += gp.tileSize;
        g2.drawString("SE", textX, textY);
        if (commandNum == 2){
            g2.drawString(">",textX-25, textY);
        }

        //CONTROL
        textY += gp.tileSize;
        g2.drawString("Control", textX, textY);
        if (commandNum == 3){
            g2.drawString(">",textX-25, textY);
            if (gp.keyH.enterPressed){
                subState = 2;
                commandNum = 0;
            }
        }

        //END GAME
        textY += gp.tileSize;
        g2.drawString("End game", textX, textY);
        if (commandNum == 4){
            g2.drawString(">",textX-25, textY);
            if (gp.keyH.enterPressed){
                subState = 3;
                commandNum = 0;
            }
        }

        //BACK
        textY += gp.tileSize*2;
        g2.drawString("Back", textX, textY);
        if (commandNum == 5){
            g2.drawString(">",textX-25, textY);
            if (gp.keyH.enterPressed){
                gp.gameState = gp.playState;
                commandNum = 0;
            }
        }

        //FULL SCREEN CHECK BOX
        textX = frameX + (int)(gp.tileSize*4.5);
        textY = frameY + gp.tileSize*2 + 24;
        g2.drawRect(textX,textY,24,24);
        if (gp.fullScreenOn){
            g2.fillRect(textX,textY, 24, 24);
        }

        //MUSIC VOLUME
        textY += gp.tileSize;
        g2.drawRect(textX, textY,120, 24); // 120/5 = 24
        int volumeWidth = 24 * gp.music.volumeScale;
        g2.fillRect(textX, textY,volumeWidth,24);

        //SE VOLUME
        textY += gp.tileSize;
        g2.drawRect(textX, textY,120, 24);
        volumeWidth = 24 * gp.se.volumeScale;
        g2.fillRect(textX, textY,volumeWidth,24);

    }
    public static void options_fullScreenNotification(int frameX, int frameY){
        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize*3;

        currentDialog = "The change will take \neffect after restarting \nthe game.";
        for (String line:currentDialog.split("\n")) {
            g2.drawString(line,textX, textY);
            textY += 40;
        }

        //BACK
        textY = frameY + gp.tileSize*9;
        g2.drawString("Back",textX,textY);
        if (commandNum == 0){
            g2.drawString(">",textX-25,textY);
            if (gp.keyH.enterPressed){
                subState = 0;
            }
        }
    }
    public static void options_controls(int frameX, int frameY){

        int textX;
        int textY;

        //TITLE
        String text = "Control";
        textX = getXFForCenteredText(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);

        textX = frameX + gp.tileSize;
        textY += gp.tileSize;
        g2.drawString("Move",textX,textY);textY+=gp.tileSize;
        g2.drawString("Confirm/Attack",textX,textY);textY+=gp.tileSize;
        g2.drawString("Shoot/Cast",textX,textY);textY+=gp.tileSize;
        g2.drawString("Character Screen",textX,textY);textY+=gp.tileSize;
        g2.drawString("Pause",textX,textY);textY+=gp.tileSize;
        g2.drawString("Options",textX,textY);

        textX = frameX + gp.tileSize*6;
        textY = frameY + gp.tileSize*2;
        g2.drawString("WASD",textX,textY);textY+=gp.tileSize;
        g2.drawString("ENTER",textX,textY);textY+=gp.tileSize;
        g2.drawString("F",textX,textY);textY+=gp.tileSize;
        g2.drawString("C",textX,textY);textY+=gp.tileSize;
        g2.drawString("P",textX,textY);textY+=gp.tileSize;
        g2.drawString("ESC",textX,textY);

        //BACK
        textX = frameX + gp.tileSize;
        textY = frameY + gp.tileSize*9;
        g2.drawString("Back", textX, textY);
        if (commandNum == 0){
            g2.drawString(">",textX-25,textY);
            if (gp.keyH.enterPressed){
                subState = 0;
                commandNum = 3;
            }
        }
    }
    public static void options_endGameConfirmation(int frameX, int frameY){
        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize*3;

        currentDialog = "Quit the game and \nreturn to the title screen?";

        for (String line:currentDialog.split("\n")) {
            g2.drawString(line,textX,textY);
            textY += 40;
        }
        //YES
        String text = "Yes";
        textX = getXFForCenteredText(text);
        textY += gp.tileSize*3;
        g2.drawString(text,textX,textY);
        if (commandNum==0){
            g2.drawString(">",textX-25,textY);
            if (gp.keyH.enterPressed){
                subState = 0;
                gp.gameState = gp.titleState;
                gp.stopMusic();
            }
        }

        //NO
        text = "No";
        textX = getXFForCenteredText(text);
        textY += gp.tileSize;
        g2.drawString(text,textX,textY);
        if (commandNum==1){
            g2.drawString(">",textX-25,textY);
            if (gp.keyH.enterPressed){
                subState = 0;
                commandNum = 4;
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
