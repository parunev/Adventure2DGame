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
    public final int tileSize = originalTileSize * scale; // 48x48 scaled character, setting public to access it

    // size of our game screen 4:3 scale - 576 x 768
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    //WORLD SETTING
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxScreenCol;
    public final int worldHeight = tileSize * maxScreenRow;


    //FPS
    double fps = 60;

    //KEYS
    keyHandler keyH = new keyHandler();

    public checkCollision cCheker = new checkCollision(this);
    public assetSetter aSetter = new assetSetter(this);
    public TileManager tileM = new TileManager(this);
    Thread gameThread;
    public player player = new player(this, keyH);
    public objectManager[] obj = new objectManager[10]; // we prepare 10 slots for objects


    //set the size of the JPanel
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

    //updates our character position
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
        player.draw(g2); // player draw
        g2.dispose();
    }
}
