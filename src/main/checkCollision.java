package main;

import entity.entity;

public class checkCollision {

    gamePanel gp;

    public checkCollision(gamePanel gp){
        this.gp = gp;
    }
    // not player but entity. we use this method to check npcs, monsters and player collision
    public void checkTile(entity entity){

        //detect collision, by these coordinates we find their col and row numbers
        int entityLeftWorldX = entity.worldX + entity.solidArea.x; //solidArea.x = 8
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width; // width = 32
        int entityTopWorldY = entity.worldY + entity.solidArea.y; //solidArea.y = 16;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height; // height = 32

        int entityLeftCol = entityLeftWorldX/gp.tileSize;
        int entityRightCol = entityRightWorldX/gp.tileSize;
        int entityTopRow = entityTopWorldY/gp.tileSize;
        int entityBottomRow = entityBottomWorldY/gp.tileSize;

        int tileNum1, tileNum2;

        switch (entity.direction) {
            case "up" -> {
                entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                if ((gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision)) {
                    entity.collisionOn = true;
                }
            }
            case "down" -> {
                entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if ((gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision)) {
                    entity.collisionOn = true;
                }
            }
            case "left" -> {
                entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                if ((gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision)) {
                    entity.collisionOn = true;
                }
            }
            case "right" -> {
                entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if ((gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision)) {
                    entity.collisionOn = true;
                }
            }
        }
    }

}
