package main;

import entity.entity;
import entity.player;
import interactiveTile.interactiveTile;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;

public class gamePanel extends JPanel implements Runnable {
    //setting up a game screen

    //SCREEN SETTING
    final int originalTileSize = 16; //16x16 pixel character
    final int scale = 3; // scaling the character
    public final int tileSize = originalTileSize * scale; // 48x48 scaled character
    public final int maxScreenCol = 20;
    public final int maxScreenRow = 12; // 3:4 RATIO
    public final int screenWidth = tileSize * maxScreenCol; //960
    public final int screenHeight = tileSize * maxScreenRow; //576

    //WORLD SETTING
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    //FOR FULL SCREEN
    int screenWidth2 = screenWidth;
    int screenHeight2 = screenHeight;
    BufferedImage tempScreen;
    Graphics2D g2;
    public boolean fullScreenOn = false;

    //SYSTEM
    double fps = 60;
    public TileManager tileM = new TileManager(this);
    public keyHandler keyH = new keyHandler(this);
    sound music = new sound();
    sound se = new sound();
    public checkCollision cCheker = new checkCollision(this);
    public assetSetter aSetter = new assetSetter(this);
    public userInterface UI = new userInterface(this);
    public eventHandler eHandler = new eventHandler(this);
    config config = new config(this);
    Thread gameThread;


    //ENTITY AND OBJECTS
    public player player = new player(this, keyH);
    public entity[] obj = new entity[20];
    public entity[] npc = new entity[10];
    public entity[] monster = new entity[20];
    public interactiveTile[] iTile = new interactiveTile[50];
    public ArrayList<entity> projectileList = new ArrayList<>();
    public ArrayList<entity> particleList = new ArrayList<>();
    ArrayList<entity> entityList = new ArrayList<>();

    //GAME STATE
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogState = 3;
    public final int characterState = 4;
    public final int optionsState = 5;
    public final int gameOverState = 6;


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
        aSetter.setNPC();
        aSetter.setMonster();
        aSetter.setInteractiveTile();
        //playMusic(0);
        gameState = titleState;

        tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D) tempScreen.getGraphics();

       if (fullScreenOn){
           setFullScreen();
       }
    }

    public void retry(){
        player.setDefaultPositions();
        player.restoreLifeAndMana();
        aSetter.setNPC();
        aSetter.setMonster();
    }
    public void restart(){
        player.setDefaultValue();
        player.setItems();
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
        aSetter.setInteractiveTile();
    }

    //full screen setup
    public void setFullScreen(){

        //GET LOCAL SCREEN DEVICE
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(Main.window);

        //GET FULL SCREEN WIDTH AND HEIGHT
        screenWidth2 = Main.window.getWidth();
        screenHeight2 = Main.window.getHeight();
    }

    public void startGameThread(){gameThread = new Thread(this);gameThread.start();}

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
                drawToTempScreen(); // draw everything to the buffered image
                drawToScreen(); // draw the buffered image to the screen
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
        if (gameState == playState){
            //PLAYER
            player.update();

            //NPC
            for (entity entity : npc) {
                if (entity != null) {
                    entity.update();
                }
            }

            for (int i = 0; i <monster.length ; i++) {
                if (monster[i] != null){
                    if (monster[i].alive){
                        monster[i].update();
                    }
                    if (!monster[i].alive){
                        monster[i].checkDrop();//when the monster die we check the drop before we set it null
                        monster[i] = null;
                    }
            }
            }

            for (int i = 0; i <projectileList.size() ; i++) {
                if (projectileList.get(i) != null){
                    if (projectileList.get(i).alive){
                        projectileList.get(i).update();
                    }
                    if (!projectileList.get(i).alive){
                        projectileList.remove(i);
                    }
                }
            }
            for (int i = 0; i <particleList.size() ; i++) {
                if (particleList.get(i) != null){
                    if (particleList.get(i).alive){
                        particleList.get(i).update();
                    }
                    if (!particleList.get(i).alive){
                        particleList.remove(i);
                    }
                }
            }
            for (interactiveTile interactiveTile : iTile) {
                if (interactiveTile != null) {
                    interactiveTile.update();
                }
            }
        }
        if (gameState == pauseState){
            stopMusic();
        }

    }

    //PAINTING SCREEN
    public void drawToTempScreen(){

        //DEBUG START
        long drawStart = 0;
        if (keyH.showDebugText){drawStart = System.nanoTime();}

        //TITLE SCREEN
        if (gameState == titleState){
            userInterface.draw(g2);
            //OTHERS
        }else{
            //TILE
            tileM.draw(g2); // always draw the tiles first otherwise you'll not see the character

            //INTERACTIVE TILE
            for (interactiveTile interactiveTile : iTile) {
                if (interactiveTile != null) {
                    interactiveTile.draw(g2);
                }
            }

            //ADD ENTITIES TO THE LIST
            entityList.add(player);
            for (entity entity : npc) {
                if (entity != null) {
                    entityList.add(entity);
                }
            }
            for (entity entity : obj) {
                if (entity != null) {
                    entityList.add(entity);
                }
            }
            for (entity entity : monster) {
                if (entity != null) {
                    entityList.add(entity);
                }
            }
            for (entity entity : projectileList) {
                if (entity != null) {
                    entityList.add(entity);
                }
            }
            for (entity entity : particleList) {
                if (entity != null) {
                    entityList.add(entity);
                }
            }
            //SORT
            entityList.sort(Comparator.comparingInt((entity e) -> e.worldY));

            //DRAW ENTITIES
            for (entity entity : entityList) {
                entity.draw(g2);
            }
            //EMPTY ENTITY LIST
            entityList.clear();


            //USER INTERFACE
            userInterface.draw(g2);
        }



        //DEBUG
        if (keyH.showDebugText){
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;

            g2.setFont(new Font("Arial", Font.PLAIN, 20));
            g2.setColor(Color.white);
            int x = 10;
            int y = 400;
            int lineHeight = 20;

            g2.drawString("WorldX " + player.worldX, x, y); y+=lineHeight; //showing current worldX
            g2.drawString("WorldY " + player.worldY, x, y); y+=lineHeight; //showing current worldY
            g2.drawString("Col " + (player.worldX+player.solidArea.x)/tileSize, x, y); y+=lineHeight; // current col
            g2.drawString("Row " + (player.worldY+player.solidArea.y)/tileSize, x, y); y+=lineHeight; // current row
            g2.drawString("Draw time: " + passed, x, y); // draw time
        }
    }
    public void drawToScreen(){
        Graphics2D g = (Graphics2D) getGraphics();
        g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
        g.dispose();
    }

    //PLAYING MUSIC
    public void playMusic(int i){music.setFile(i);music.play();music.loop();} // we call loop for the background music

    //STOP MUSIC
    public void stopMusic(){music.stop();}

    //PLAYING SOUNDS
    public void playSE(int i){se.setFile(i);se.play();}
}
