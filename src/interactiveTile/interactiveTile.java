package interactiveTile;

import entity.entity;
import main.gamePanel;

public class interactiveTile extends entity {
    gamePanel gp;
    public boolean destructible = false;

    public interactiveTile(gamePanel gp, int col, int row) {
        super(gp);
        this.gp = gp;
    }
    public boolean isCorrectItem(entity entity){
        return false;
    }
    public void playSE(){

    }
    public interactiveTile getDestroyedForm(){
        interactiveTile tile = null;
        return tile;
    }
    public void update(){
        if (invincible){
            invincibleCounter++;
                if (invincibleCounter>20){
                    invincible = false;
                    invincibleCounter = 0;
                }
            }
        }
    }
