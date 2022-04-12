package main;

import entity.entity;
import entity.player;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

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
    Thread gameThread;


    //ENTITY AND OBJECTS
    public player player = new player(this, keyH);
    public entity[] obj = new entity[10];
    public entity[] npc = new entity[10];
    public entity[] monster = new entity[20];
    ArrayList<entity> entityList = new ArrayList<>();

    //GAME STATE
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogState = 3;


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
        //playMusic(0);
        gameState = titleState;
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
                if (monster[i]!=null){
                    if (monster[i].alive){
                        monster[i].update();
                    }
                    if (!monster[i].alive){
                        monster[i] = null;
                    }
            }
            }
        }
        if (gameState == pauseState){
            //nothing
        }

    }

    //imagine this is your paintbrush
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        //DEBUG START
        long drawStart = 0;
        if (keyH.checkDrawTime){drawStart = System.nanoTime();}

        //TITLE SCREEN
        if (gameState == titleState){
            userInterface.draw(g2);
        //OTHERS
        }else{
            //TILE
            tileM.draw(g2); // always draw the tiles first otherwise you'll not see the character

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



        //DEBUG END
        if (keyH.checkDrawTime){
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;
            g2.setColor(Color.white);
            g2.drawString("Draw time: " + passed, 10, 400);
            System.out.println("Draw time: " + passed);
        }

        //DESTROYER :D
        g2.dispose();
    }

    //PLAYING MUSIC
    public void playMusic(int i){music.setFile(i);music.play();music.loop();} // we call loop for the background music
    //STOP MUSIC
    public void stopMusic(){music.stop();}

    //PLAYING SOUNDS
    public void playSE(int i){se.setFile(i);se.play();}
}
