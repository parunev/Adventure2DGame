package main;

import entity.entity;
import object.objectCoin;
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
    static BufferedImage heart_full, heart_half, heart_blank, crystal_full, crystal_blank,coin;
    public static boolean messageOn = false;
    public static ArrayList<String> message = new ArrayList<>();
    public static ArrayList<Integer> messageCounter = new ArrayList<>();
    public static boolean gameFinish = false;
    public static String currentDialog = "";
    public static int commandNum = 0;
    public static int playerSlotCol = 0;
    public static int playerSlotRow = 0;
    public static int npcSlotCol = 0;
    public static int npcSlotRow = 0;
    static int subState = 0;
    static int counter = 0;
    public static entity npc;


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
        entity bronzeCoin = new objectCoin(gp);
        coin = bronzeCoin.down1;

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
            drawInventory(gp.player,true);
        }

        //OPTIONS STATE
        if (gp.gameState == gp.optionsState){
            drawOptionsScreen();
        }

        //GAME OVER STATE
        if (gp.gameState == gp.gameOverState){
            drawGameOverScreen();
        }

        //TRANSITION STATE
        if (gp.gameState == gp.transitionState){
            drawTransition();
        }

        //TRADE STATE
        if (gp.gameState == gp.tradeState){
            drawTradeScreen();
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
        int x = gp.tileSize*3;
        int y = gp.tileSize/2;
        int width = gp.screenWidth - (gp.tileSize*6);
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
    public static void drawInventory(entity entity, boolean cursor){

        int frameX = 0;
        int frameY = 0;
        int frameWidth = 0;
        int frameHeight = 0;
        int slotCol = 0;
        int slotRow = 0;

        if (entity == gp.player){
            frameX = gp.tileSize*12;
            frameY = gp.tileSize;
            frameWidth = gp.tileSize*6;
            frameHeight= gp.tileSize*5;
            slotCol = playerSlotCol;
            slotRow = playerSlotRow;
        }else{
            frameX = gp.tileSize*2;
            frameY = gp.tileSize;
            frameWidth = gp.tileSize*6;
            frameHeight= gp.tileSize*5;
            slotCol = npcSlotCol;
            slotRow = npcSlotRow;
        }

        //FRAME
        drawSubWindows(frameX, frameY, frameWidth, frameHeight);

        //SLOT
        final int slotXStart = frameX +20;
        final int slotYStart = frameY +20;
        int slotX = slotXStart;
        int slotY = slotYStart;
        int slotSize = gp.tileSize+3;

        // DRAW PLAYER ITEMS
        for (int i = 0; i <entity.inventory.size() ; i++) {
            //EQUIP CURSOR
            if (entity.inventory.get(i)==entity.currentWeapon || entity.inventory.get(i)==entity.currentShield){
                g2.setColor(new Color(240, 190,90));
                g2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 10, 10);
            }



            g2.drawImage(entity.inventory.get(i).down1,slotX,slotY,null);
            slotX+=slotSize;
            if (i == 4 || i == 9 || i == 14 ){
                slotX = slotXStart;
                slotY += slotSize;
            }
        }

        //CURSOR
        if (cursor){
            int cursorX = slotXStart + (slotSize*playerSlotCol);
            int cursorY = slotYStart + (slotSize*playerSlotRow);
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

            int itemIndex = getItemIndexOnSlot(slotCol, slotRow);
            if (itemIndex < entity.inventory.size()){

                drawSubWindows(frameX, dFrameY, frameWidth, dFrameHeight);

                for (String line : entity.inventory.get(itemIndex).description.split("\n")) {
                    g2.drawString(line, textX, textY);
                    textY+=32;
                }
            }
        }
    }

    //SETTING UP OPTION MENU
    public static void drawGameOverScreen(){

        g2.setColor(new Color(0,0,0,150));
        g2.fillRect(0,0,gp.screenWidth,gp.screenHeight);

        int x;
        int y;
        String text;
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,110f));

        text = "Game Over";
        //shadow
        g2.setColor(Color.black);
        x = getXFForCenteredText(text);
        y = gp.tileSize*4;
        g2.drawString(text, x, y);
        //main
        g2.setColor(Color.WHITE);
        g2.drawString(text,x-4,y-4);

        //retry
        g2.setFont(g2.getFont().deriveFont(50f));
        text = "Retry";
        x = getXFForCenteredText(text);
        y += gp.tileSize*4;
        g2.drawString(text, x, y);
        if (commandNum == 0){
            g2.drawString(">",x-40,y);
        }

        //back to title screen
        text = "Quit";
        x = getXFForCenteredText(text);
        y += 55;
        g2.drawString(text, x, y);
        if (commandNum == 1  ){
            g2.drawString(">",x-40,y);
        }

    }
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

        gp.config.safeConfig();
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

    //TRANSITION
    public static void drawTransition(){
        counter++;
        g2.setColor(new Color(0,0,0,counter*5));
        g2.fillRect(0,0,gp.screenWidth,gp.screenHeight);

        if (counter == 50){
            counter = 0;
            gp.gameState = gp.playState;
            gp.currentMap = gp.eHandler.tempMap;
            gp.player.worldX = gp.tileSize * gp.eHandler.tempCol;
            gp.player.worldY = gp.tileSize * gp.eHandler.tempRow;
            gp.eHandler.previousEventX = gp.player.worldX;
            gp.eHandler.previousEventY = gp.player.worldY;
        }

    }

    //TRADE SCREEN
    public static void drawTradeScreen(){
        switch (subState) {
            case 0 -> trade_select();
            case 1 -> trade_buy();
            case 2 -> trade_sell();
        }
        gp.keyH.enterPressed = false;
    }

    //TRADE SELECTION
    public static void trade_select(){
        drawDialogScreen();
        //DRAW WINDOW
        int x = gp.tileSize*15;
        int y = gp.tileSize*4;
        int width = gp.tileSize*3;
        int height = (int)(gp.tileSize*3.5);
        drawSubWindows(x,y,width,height);

        //DRAW TEXTS
        x += gp.tileSize;
        y += gp.tileSize;
        g2.drawString("Buy",x,y);
        if (commandNum == 0){g2.drawString(">",x-24,y);if(gp.keyH.enterPressed){subState = 1;}}
        y += gp.tileSize;
        g2.drawString("Sell",x,y);
        if (commandNum == 1){g2.drawString(">",x-24,y);if(gp.keyH.enterPressed){subState = 2;}}
        y += gp.tileSize;
        g2.drawString("Leave",x,y);
        if (commandNum == 2){g2.drawString(">",x-24,y);
            if(gp.keyH.enterPressed){commandNum = 0; gp.gameState = gp.dialogState;
                currentDialog = "Come again, he he!";}}
    }

    //TRADE BUY MENU
    public static void trade_buy(){

        //DRAW PLAYER INVENTORY
        drawInventory(gp.player,false);
        //DRAW NPC INVENTORY
        drawInventory(npc, true);

        //DRAW HINT WINDOW
        int x = gp.tileSize*2;
        int y = gp.tileSize*9;
        int width = gp.tileSize*6;
        int height = gp.tileSize*2;
        drawSubWindows(x,y,width,height);
        g2.drawString("[ESC] Back",x+24,y+60);

        //DRAW PLAYER COIN WINDOW
         x = gp.tileSize*12;
         y = gp.tileSize*9;
         width = gp.tileSize*6;
         height = gp.tileSize*2;
        drawSubWindows(x,y,width,height);
        g2.drawString("Your coins: " + gp.player.coin,x+24,y+60);

        //DRAW PRICE WINDOW
        int itemIndex = getItemIndexOnSlot(npcSlotCol,npcSlotRow);
        if(itemIndex < npc.inventory.size()){
            x = (int)(gp.tileSize*5.5);
            y = (int)(gp.tileSize*5.5);
            width = (int)(gp.tileSize*2.5);
            height = gp.tileSize;
            drawSubWindows(x, y, width, height);
            g2.drawImage(coin,x+10,y+8,32,32,null);

            int price = npc.inventory.get(itemIndex).price;
            String text = ""+price;
            x = getXFForAlignToRightText(text, gp.tileSize*8-20);
            g2.drawString(text,x,y+34);

            //BUY ITEM
            if(gp.keyH.enterPressed){
                if (npc.inventory.get(itemIndex).price > gp.player.coin){
                    subState = 0;
                    gp.gameState = gp.dialogState;
                    currentDialog = "You need more coins to buy that!";
                    drawDialogScreen();
                } else if(gp.player.inventory.size() == gp.player.inventorySize){
                    subState = 0;
                    gp.gameState = gp.dialogState;
                    currentDialog = "You cannot carry more items!";
                }else{
                    gp.player.coin -= npc.inventory.get(itemIndex).price;
                    gp.player.inventory.add(npc.inventory.get(itemIndex));
                }
            }
        }
    }

    //TRADE SELL MENU
    public static void trade_sell(){
        //DRAW PLAYER INVENTORY
        drawInventory(gp.player,true);

        int x;
        int y;
        int width;
        int height;

        //DRAW HINT WINDOW
        x = gp.tileSize*2;
        y = gp.tileSize*9;
        width = gp.tileSize*6;
        height = gp.tileSize*2;
        drawSubWindows(x,y,width,height);
        g2.drawString("[ESC] Back",x+24,y+60);

        //DRAW PLAYER COIN WINDOW
        x = gp.tileSize*12;
        y = gp.tileSize*9;
        width = gp.tileSize*6;
        height = gp.tileSize*2;
        drawSubWindows(x,y,width,height);
        g2.drawString("Your coins: " + gp.player.coin,x+24,y+60);

        //DRAW PRICE WINDOW
        int itemIndex = getItemIndexOnSlot(playerSlotRow,playerSlotCol);
        if(itemIndex < gp.player.inventory.size()){
            x = (int)(gp.tileSize*15.5);
            y = (int)(gp.tileSize*5.5);
            width = (int)(gp.tileSize*2.5);
            height = gp.tileSize;
            drawSubWindows(x, y, width, height);
            g2.drawImage(coin,x+10,y+8,32,32,null);

            int price = gp.player.inventory.get(itemIndex).price/2;
            String text = ""+price;
            x = getXFForAlignToRightText(text, gp.tileSize*18-20);
            g2.drawString(text,x,y+34);

            //SELL AN  ITEM
            if(gp.keyH.enterPressed){
                if(gp.player.inventory.get(itemIndex) == gp.player.currentWeapon ||
                gp.player.inventory.get(itemIndex) == gp.player.currentShield){
                    commandNum = 0;
                    subState = 0;
                    gp.gameState = gp.dialogState;
                    currentDialog = "You cannot sell equipped item!";
                }else{
                    gp.player.inventory.remove(itemIndex);
                    gp.player.coin += price;
                }
            }
        }
    }

    //FIXED METHODS
    public static int getItemIndexOnSlot(int slotCol, int slotRow){
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
