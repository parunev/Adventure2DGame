package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class keyHandler implements KeyListener { // used for receiving keyboard events(strokes)
    public boolean upPressed, downPressed, leftPressed, rightPressed;
    //DEBUG
    public boolean checkDrawTime = false;

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode(); // returns the integer keyCode associated with the key in this event
        if (code == KeyEvent.VK_W){upPressed = true;}
        if (code == KeyEvent.VK_S){downPressed = true;}
        if (code == KeyEvent.VK_A){leftPressed = true;}
        if (code == KeyEvent.VK_D){rightPressed = true;}

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
