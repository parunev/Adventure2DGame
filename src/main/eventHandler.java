package main;

import entity.entity;

public class eventHandler {
    gamePanel gp;
    eventRect[][][] eventRect;

    //disable event until player moves away by 1 tile distance
    int previousEventX, previousEventY;
    boolean canTouchEvent = true;
    int tempMap, tempCol, tempRow;

    public eventHandler(gamePanel gp){
        this.gp = gp;
        //Setting up eventRect on every single map tile
        eventRect = new eventRect[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];

        int map = 0;
        int col = 0;
        int row = 0;
        while (map < gp.maxMap && col < gp.maxWorldCol && row < gp.maxWorldRow){
            //we set trigger point to be middle of a tile
            eventRect[map][col][row] = new eventRect();
            eventRect[map][col][row].x = 23;
            eventRect[map][col][row].y = 23;
            eventRect[map][col][row].width = 2;
            eventRect[map][col][row].height = 3;
            eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
            eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;

            col++;
            if (col == gp.maxWorldCol){
                col = 0;
                row++;

                if (row == gp.maxWorldRow){
                    row = 0;
                    map++;
                }
            }
        }
    }

    //EVENT POSITION
    public void checkEvent(){
        //check if the player character is more than 1 tile away from the last event
        int xDistance = Math.abs(gp.player.worldX - previousEventX);
        int yDistance = Math.abs(gp.player.worldY - previousEventY);
        int distance = Math.max(xDistance, yDistance);
        if (distance > gp.tileSize){
            canTouchEvent = true;
        }

        if (canTouchEvent){
            if (hit(0,27, 16, "right")){damagePit(gp.dialogState);}
            else if (hit(0,23, 12,"up")){healingPool(gp.dialogState);}
            else if (hit(0,10,39,"any")){teleport(1, 12,13);}
            else if (hit(1,12,13,"any")){teleport(0, 10,39);}
            else if (hit(1,12,8,"up")){speak(gp.npc[1][0]);}
        }
    }

    //CHECK EVENT COLLISION
    public boolean hit(int map, int col, int row, String regDirection){

        boolean hit = false;

        if (map == gp.currentMap){

            //GETTING PLAYER CURRENT SOLID AREA
            gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
            gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;

            //GETTING EVENT SOLID AREA POSITIONS
            eventRect[map][col][row].x = col*gp.tileSize + eventRect[map][col][row].x;
            eventRect[map][col][row].y = row*gp.tileSize + eventRect[map][col][row].y;

            //CHECKING IF PLAYER SOLID AREA IS COLLIDING WITH EVENTRECT SOLID AREA
            if (gp.player.solidArea.intersects(eventRect[map][col][row]) && (!eventRect[map][col][row].eventDone)){
                if (gp.player.direction.contentEquals(regDirection) || regDirection.contentEquals("any")){
                    hit = true;

                    //based on this we check the distance between player character and last event
                    previousEventX = gp.player.worldX;
                    previousEventY = gp.player.worldY;
                }
            }

            //AFTER CHECKING THE COLLISION RESET SOLID AREA x and y
            gp.player.solidArea.x = gp.player.solidAreaDefaultX;
            gp.player.solidArea.y = gp.player.solidAreaDefaultY;

            eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
            eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;
        }

        return hit;
    }

    //DAMAGE PIT EVENT
    public void damagePit( int gameState){
        gp.gameState = gameState;
        gp.playSE(6);
        userInterface.currentDialog = "You fall into a pit!";
        gp.player.life -= 1;
      //  eventRect[col][row].eventDone = true;
        canTouchEvent = false;
    }

    //HEALING POOL EVENT
    public void healingPool(int gameState){
        if (gp.keyH.enterPressed){
            gp.gameState = gameState;
            gp.player.attackCanceled = true;
            gp.playSE(2);
            userInterface.currentDialog = "You drink the water.\nYour life and mana have been recovered!";
            gp.player.life = gp.player.maxLife;
            gp.player.mana = gp.player.maxMana;
            gp.aSetter.setMonster();
        }
    }

    //TELEPORT
    public void teleport(int map, int col, int row){

        gp.gameState = gp.transitionState;
        tempMap = map;
        tempCol = col;
        tempRow = row;
        canTouchEvent = false;
        gp.playSE(13);
    }

    //SPEAK
    public void speak(entity entity){
        if (gp.keyH.enterPressed){
            gp.gameState = gp.dialogState;
            gp.player.attackCanceled = true;
            entity.speak();
        }
    }
}
