package main;

public class eventHandler {
    gamePanel gp;
    eventRect[][] eventRect;

    //disable event until player moves away by 1 tile distance
    int previousEventX, previousEventY;
    boolean canTouchEvent = true;

    public eventHandler(gamePanel gp){
        this.gp = gp;
        //Setting up eventRect on every single map tile
        eventRect = new eventRect[gp.maxWorldCol][gp.maxWorldRow];

        int col = 0;
        int row = 0;
        while (col < gp.maxWorldCol && row < gp.maxWorldRow){
            //we set trigger point to be middle of a tile
            eventRect[col][row] = new eventRect();
            eventRect[col][row].x = 23;
            eventRect[col][row].y = 23;
            eventRect[col][row].width = 2;
            eventRect[col][row].height = 3;
            eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
            eventRect[col][row].eventRectDefaultY = eventRect[col][row].y;

            col++;
            if (col == gp.maxWorldCol){
                col = 0;
                row++;
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
            if (hit(27, 16, "right")){damagePit(27, 16,gp.dialogState);}
            if (hit(23, 19, "any")){damagePit(27, 16,gp.dialogState);}
            if (hit(23, 12,"up")){healingPool(23, 12, gp.dialogState);}
        }


    }

    //CHECK EVENT COLLISION
    public boolean hit(int col, int row, String regDirection){

        boolean hit = false;

        //GETTING PLAYER CURRENT SOLID AREA
        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;

        //GETTING EVENT SOLID AREA POSITIONS
        eventRect[col][row].x = col*gp.tileSize + eventRect[col][row].x;
        eventRect[col][row].y = row*gp.tileSize + eventRect[col][row].y;

        //CHECKING IF PLAYER SOLID AREA IS COLLIDING WITH EVENTRECT SOLID AREA
        if (gp.player.solidArea.intersects(eventRect[col][row]) && (!eventRect[col][row].eventDone)){
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

        eventRect[col][row].x = eventRect[col][row].eventRectDefaultX;
        eventRect[col][row].y = eventRect[col][row].eventRectDefaultY;

        return hit;
    }
    public void damagePit(int col, int row, int gameState){
        gp.gameState = gameState;
        userInterface.currentDialog = "You fall into a pit!";
        gp.player.life -= 1;
      //  eventRect[col][row].eventDone = true;
        canTouchEvent = false;
    }

    public void healingPool(int col, int row,int gameState){
        if (gp.keyH.enterPressed){
            gp.gameState = gameState;
            userInterface.currentDialog = "You drink the water.\nYour life has been recovered!";
            gp.player.life = gp.player.maxLife;
        }
    }
}
