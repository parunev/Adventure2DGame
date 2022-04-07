package main;

import object.objectKey;

import java.awt.*;
import java.awt.image.BufferedImage;

public class userInterface {

    static gamePanel gp;
    static Font Dialog_30, Dialog_60;
    static BufferedImage keyImage;
    public static boolean messageOn = false;
    public static String message = "";
    static int messageCounter = 0;
    public static boolean gameFinish = false;

    public userInterface(gamePanel gp){
        userInterface.gp = gp;

        Dialog_30 = new Font("Dialog",Font.BOLD, 30); // finishing the instantiation before the game-loop start
        Dialog_60 = new Font("Dialog",Font.BOLD, 60);
        objectKey key = new objectKey();
        keyImage = key.image;
    }
    //RECEIVING A TEXT
    public void showMessage(String text){
        message = text;
        messageOn = true;
    }

    public static void draw(Graphics2D g2){

        if (gameFinish){
            g2.setFont(Dialog_30);
            g2.setColor(Color.WHITE);

            String text;
            int textLength;
            int x;
            int y;

            //DISPLAYING A CONGRATULATIONS MESSAGE AT THE CENTRE OF THE SCREEN
            text = "You found the treasure!";
            textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.screenWidth/2 - textLength/2;
            y = gp.screenHeight/2 - (gp.tileSize*3);
            g2.drawString(text, x, y);

            g2.setFont(Dialog_60);
            g2.setColor(Color.GREEN);

            text = "Congratulations";
            textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.screenWidth/2 - textLength/2;
            y = gp.screenHeight/2 + (gp.tileSize*2);
            g2.drawString(text, x, y);

            gp.gameThread = null;

        }else{
            //SETTING UP FONT, FONT-TYPE, FONT-SIZE
            g2.setFont(Dialog_30);
            g2.setColor(Color.WHITE);
            g2.drawImage(keyImage, gp.tileSize/2 + 5, gp.tileSize/2 - 15, gp.tileSize - 5, gp.tileSize - 5, null);
            g2.drawString("X" + gp.player.hasKey,74, 45);

            //MESSAGE
            if (messageOn){
                g2.setFont(g2.getFont().deriveFont(20F));
                g2.drawString(message, gp.tileSize + 230, gp.tileSize+150);

                //SETTING MESSAGE TIMER
                messageCounter++;
                if (messageCounter>180){ //TIMES 180 means 60*3 which means 3 seconds
                    messageCounter = 0;
                    messageOn = false;
                }
            }
        }
    }
}
