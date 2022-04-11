package main;

import entity.entity;
import object.objectHeart;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class userInterface {

    static gamePanel gp;
    public static Graphics2D g2;
    static Font pixelFont;
    static BufferedImage heart_full;
    static BufferedImage heart_half;
    static BufferedImage heart_blank;
    public static boolean messageOn = false;
    public static String message = "";
    static int messageCounter = 0;
    public static boolean gameFinish = false;
    public static String currentDialog = "";
    public static int commandNum = 0;

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
    //RECEIVING A TEXT
    public void showMessage(String text){
        message = text;
        messageOn = true;
    }

    //SETTING UP PAUSE
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
            //do playState stuff later
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
}
