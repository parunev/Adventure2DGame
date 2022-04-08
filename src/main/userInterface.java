package main;

import java.awt.*;

public class userInterface {

    static gamePanel gp;
    public static Graphics2D g2;
    static Font Dialog_30, Dialog_60;
    public static boolean messageOn = false;
    public static String message = "";
    static int messageCounter = 0;
    public static boolean gameFinish = false;

    public userInterface(gamePanel gp){
        userInterface.gp = gp;

        Dialog_30 = new Font("Dialog",Font.BOLD, 30); // finishing the instantiation before the game-loop start
        Dialog_60 = new Font("Dialog",Font.BOLD, 60);
    }
    //RECEIVING A TEXT
    public void showMessage(String text){
        message = text;
        messageOn = true;
    }

    //SETTING UP PAUSE
    public static void draw(Graphics2D g2){
        userInterface.g2 = g2;
        g2.setFont(Dialog_30);
        g2.setColor(Color.white);

        if (gp.gameState == gp.playState){
            //do playState stuff later
        }
        if (gp.gameState == gp.pauseState){
            drawPauseScreen();
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
    public static int getXFForCenteredText(String text){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.screenWidth/2 - length/2;
    }
}
