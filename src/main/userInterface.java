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
    public static String currentDialog = "";

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

        //PLAY STATE
        if (gp.gameState == gp.playState){
            //do playState stuff later
        }
        //PAUSE STATE
        if (gp.gameState == gp.pauseState){
            drawPauseScreen();
        }
        //DIALOG STATE
        if(gp.gameState == gp.dialogState){
            drawDialogScreen();
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
