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

    Thread gameThread;

    //set the size of the JPanel
    public gamePanel(){
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); // this can improve game rendering performance

    }
    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() { //game-loop core of our game

        //Update information such as character positions
        //Draw the screen with the updated information
        while (gameThread != null){
            update();
            repaint();

        }
    }
    public void update(){

    }
    public void paintComponent(Graphics g){ //imagine this is your paintbrush
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        g2.setColor(Color.WHITE);
        g2.fillRect(100, 100,tileSize,tileSize);
        g2.dispose();
    }
}
