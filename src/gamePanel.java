import javax.swing.*;
import java.awt.*;

public class gamePanel extends JPanel implements Runnable {
    //setting up a game screen

    //SCREEN SETTING

    final int originalTileSize = 16; //16x16 pixel character
    final int scale = 3; // scaling the character
    final int tileSize = originalTileSize * scale; // 48x48 scaled character

    // size of our game screen 4:3 scale - 576 x 768
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol;
    final int screenHeight = tileSize * maxScreenRow;

    //FPS
    double fps = 60;

    //KEYS
    keyHandler keyH = new keyHandler();

    Thread gameThread;

    //set player default position and moving speed
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;


    //set the size of the JPanel
    public gamePanel(){
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); // this can improve game rendering performance
        this.addKeyListener(keyH);
        this.setFocusable(true); // receiving key inputs the game panel is focused:D

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
        if (keyH.upPressed){
            playerY -= playerSpeed;
        }else if (keyH.downPressed){
            playerY += playerSpeed;
        }else if (keyH.leftPressed){
            playerX -= playerSpeed;
        }else if (keyH.rightPressed){
            playerX += playerSpeed;
        }

    }

    //imagine this is your paintbrush
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        g2.setColor(Color.WHITE);
        g2.fillRect(playerX, playerY,tileSize,tileSize);
        g2.dispose();
    }
}
