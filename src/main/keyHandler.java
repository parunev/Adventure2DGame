package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

// used for receiving keyboard events(strokes)
public class keyHandler implements KeyListener {
    gamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed;

    //DEBUG
    public boolean showDebugText = false;

    public keyHandler(gamePanel gp){
        this.gp = gp;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode(); // returns the integer keyCode associated with the key in this event

        //TITLE STATE
        if (gp.gameState == gp.titleState){titleState(code);}
        //PLAY STATE
        else if (gp.gameState == gp.playState){playState(code);}
        //PAUSE STATE
        else if (gp.gameState == gp.pauseState){pauseState(code);}
        //DIALOGUE STATE
        else if (gp.gameState == gp.dialogState){dialogueState(code);}
        //CHARACTER STATE
        else if (gp.gameState == gp.characterState){characterState(code);}
    }
    public void titleState(int code){
        if (code == KeyEvent.VK_W){ // up menu
            userInterface.commandNum--;
            if (userInterface.commandNum < 0){
                userInterface.commandNum = 2;
            }
        }
        if (code == KeyEvent.VK_S){ // down menu
            userInterface.commandNum++;
            if (userInterface.commandNum > 2){
                userInterface.commandNum = 0;
            }
        }
        if (code == KeyEvent.VK_ENTER){ // if we click NEW GAME
            if (userInterface.commandNum ==0){
                gp.gameState = gp.playState;
                gp.playMusic(0);
            }
            if (userInterface.commandNum ==1){ // if we click LOAD GAME

            }
            if (userInterface.commandNum ==2){System.exit(0);} // if we click QUIT GAME
        }
    }

    public void playState(int code){

        if (code == KeyEvent.VK_W){upPressed = true;} // up button
        if (code == KeyEvent.VK_S){downPressed = true;} // down button
        if (code == KeyEvent.VK_A){leftPressed = true;} // left button
        if (code == KeyEvent.VK_D){rightPressed = true;} // right button
        if (code == KeyEvent.VK_ENTER){enterPressed = true;} // enter button
        if (code == KeyEvent.VK_P){ gp.gameState = gp.pauseState;} //pause button
        if (code == KeyEvent.VK_C){ gp.gameState = gp.characterState;} // character status button

        //DEBUG
        if (code == KeyEvent.VK_T){ // showing debug info
           if (!showDebugText){
               showDebugText = true;
           }else{
               showDebugText = false;
           }
        }
        //RELOAD MAP WHEN UPDATED
        if (code == KeyEvent.VK_R){
            gp.tileM.loadMap("/res/worldV2.txt");
        }
    }

    public void pauseState(int code){
        if (code == KeyEvent.VK_P){ gp.gameState = gp.playState;} // when we press p we pause the game
    }

    public void dialogueState(int code){
        if (code == KeyEvent.VK_ENTER) { // when we press enter we close the dialog and proceed...
            gp.gameState = gp.playState;
        }
    }

    public void characterState(int code){
        if (code == KeyEvent.VK_C){
            gp.gameState = gp.playState;
        }
        if (code == KeyEvent.VK_W){
            if (userInterface.slotRow != 0){
                userInterface.slotRow--;
                gp.playSE(9);
            }

        }
        if (code == KeyEvent.VK_A){
            if (userInterface.slotCol != 0){
                userInterface.slotCol--;
                gp.playSE(9);
            }

        }
        if (code == KeyEvent.VK_S){
            if (userInterface.slotRow != 3){
                userInterface.slotRow++;
                gp.playSE(9);
            }

        }
        if (code == KeyEvent.VK_D){
            if (userInterface.slotCol != 4){
                userInterface.slotCol++;
                gp.playSE(9);
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
