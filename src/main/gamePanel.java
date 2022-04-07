package main;

import entity.player;
import object.objectManager;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class gamePanel extends JPanel implements Runnable {
    //setting up a game screen

    //SCREEN SETTING
    final int originalTileSize = 16; //16x16 pixel character
    final int scale = 3; // scaling the character
    public final int tileSize = originalTileSize * scale; // 48x48 scaled character
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12; // 3:4 RATIO
    public final int screenWidth = tileSize * maxScreenCol; //768
    public final int screenHeight = tileSize * maxScreenRow; //576

    //WORLD SETTING
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxScreenCol;
    public final int worldHeight = tileSize * maxScreenRow;


    //SYSTEM
    double fps = 60;
    keyHandler keyH = new keyHandler();
    public checkCollision cCheker = new checkCollision(this);
    public assetSetter aSetter = new assetSetter(this);
    public TileManager tileM = new TileManager(this);
    Thread gameThread;
    public player player = new player(this, keyH);

    //OBJECTS
    public objectManager[] obj = new objectManager[10];


    public gamePanel(){
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); // this can improve game rendering performance
        this.addKeyListener(keyH);
        this.setFocusable(true); // receiving key inputs the game panel is focused:D

    }
    //creating this method for adding other stuff in the future
    public void setupGame(){
        aSetter.setObject();
    }


    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    //updates the character positions and draw the updated information
    //game-loop called "delta"
    //Delta time describes the time difference between the previous frame that was drawn and the current frame.
    @Override
    public void run() {
        double drawInterval = 1000000000/fps;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null){
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime)/drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta>=1){
                update();
                repaint();
                delta--;
                drawCount++;
            }
            if (timer>=1000000000){
                System.out.println("FPS:" + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    //CHARACTER POSITION UPDATE
    public void update(){
        player.update();
    }

    //imagine this is your paintbrush
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        //TILE
        tileM.draw(g2); // always draw the tiles first otherwise you'll not see the character

        //OBJECT
        for (object.objectManager objectManager : obj) {
            if (objectManager != null) { // we need to check if there is an object
                objectManager.draw(g2, this);
            }
        }

        //PLAYER
        player.draw(g2);

        //DESTROYER :D
        g2.dispose();
    }
}
