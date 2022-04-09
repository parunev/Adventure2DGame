package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
// used for receiving keyboard events(strokes)
public class keyHandler implements KeyListener {
    gamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed;
    //DEBUG
    public boolean checkDrawTime = false;

    public keyHandler(gamePanel gp){
        this.gp = gp;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode(); // returns the integer keyCode associated with the key in this event

        //PLAY STATE
        if (gp.gameState == gp.playState){
            if (code == KeyEvent.VK_W){upPressed = true;} // up button
            if (code == KeyEvent.VK_S){downPressed = true;} // down button
            if (code == KeyEvent.VK_A){leftPressed = true;} // left button
            if (code == KeyEvent.VK_D){rightPressed = true;} // right button
            if (code == KeyEvent.VK_ENTER){enterPressed = true;} // enter button
            if (code == KeyEvent.VK_P){ gp.gameState = gp.pauseState;} //pause button

            //DEBUG
            if (code == KeyEvent.VK_T){checkDrawTime = !checkDrawTime;} // showing draw time
        }
        //PAUSE STATE
        else if (gp.gameState == gp.pauseState){
            if (code == KeyEvent.VK_P){ gp.gameState = gp.playState;} // when we press p we pause the game
        }
        //DIALOGUE STATE
        else if (gp.gameState == gp.dialogState){
            if (code == KeyEvent.VK_ENTER) { // when we press enter we close the dialog and proceed...
                gp.gameState = gp.playState;
            }
        }
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
