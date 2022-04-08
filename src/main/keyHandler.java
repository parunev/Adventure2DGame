package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
// used for receiving keyboard events(strokes)
public class keyHandler implements KeyListener {
    gamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed;
    //DEBUG
    public boolean checkDrawTime = false;

    public keyHandler(gamePanel gp){
        this.gp = gp;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode(); // returns the integer keyCode associated with the key in this event
        if (code == KeyEvent.VK_W){upPressed = true;} // up button
        if (code == KeyEvent.VK_S){downPressed = true;} // down button
        if (code == KeyEvent.VK_A){leftPressed = true;} // left button
        if (code == KeyEvent.VK_D){rightPressed = true;} // right button
        if (code == KeyEvent.VK_P){ //pause button
            if (gp.gameState == gp.playState){
                gp.gameState = gp.pauseState;
            }else if (gp.gameState == gp.pauseState){
                gp.gameState = gp.playState;
            }
        }

        //DEBUG
        if (code == KeyEvent.VK_T){checkDrawTime = !checkDrawTime;}
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W){upPressed = false;}
        if (code == KeyEvent.VK_S){downPressed = false;}
        if (code == KeyEvent.VK_A){leftPressed = false;}
        if (code == KeyEvent.VK_D){rightPressed = false;}
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
}
