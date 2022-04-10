package main;

import java.awt.*;

public class eventHandler {
    gamePanel gp;
    Rectangle eventRect;
    int  eventRectDefaultX, eventRectDefaultY;

    public eventHandler(gamePanel gp){
        this.gp = gp;

        //we set trigger point to be middle of a tile
        eventRect = new Rectangle();
        eventRect.x = 23;
        eventRect.y = 23;
        eventRect.width = 2;
        eventRect.height = 3;
        eventRectDefaultX = eventRect.x;
        eventRectDefaultY = eventRect.y;
    }

    //EVENT POSITION
    public void checkEvent(){
        if (hit(27, 16, "right")){damagePit(gp.dialogState);}
        if (hit(23, 12,"up")){healingPool(gp.dialogState);}

    }

    //CHECK EVENT COLLISION
    public boolean hit(int eventCol, int eventRow, String regDirection){

        boolean hit = false;

        //GETTING PLAYER CURRENT SOLID AREA
        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;

        //GETTING EVENT SOLID AREA POSITIONS
        eventRect.x = eventCol*gp.tileSize + eventRect.x;
        eventRect.y = eventRow*gp.tileSize + eventRect.y;

        //CHECKING IF PLAYER SOLID AREA IS COLLIDING WITH EVENTRECT SOLID AREA
        if (gp.player.solidArea.intersects(eventRect)){
            if (gp.player.direction.contentEquals(regDirection) || regDirection.contentEquals("any")){
                hit = true;
            }
        }

        //AFTER CHECKING THE COLLISION RESET SOLID AREA x and y
        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;

        eventRect.x = eventRectDefaultX;
        eventRect.y = eventRectDefaultY;

        return hit;
    }
    public void damagePit(int gameState){
        gp.gameState = gameState;
        userInterface.currentDialog = "You fall into a pit!";
        gp.player.life -= 1;
    }

    public void healingPool(int gameState){
        if (gp.keyH.enterPressed){
            gp.gameState = gameState;
            userInterface.currentDialog = "You drink the water.\nYour life has been recovered!";
            gp.player.life = gp.player.maxLife;
        }
    }
}
