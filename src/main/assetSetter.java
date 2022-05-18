package main;

import entity.NPC_Merchant;
import entity.NPC_OldMan;
import interactiveTile.interactiveDryTree;
import monster.greenSlime;
import object.*;

public class assetSetter {

    gamePanel gp;

    public assetSetter(gamePanel gp){
        this.gp = gp;
    }

    //ADDING OBJECTS TO THE MAP
    public void setObject(){

        int mapNum = 0; // we use this to place monsters at different maps 0, 1, 2 etc. etc.
        int i = 0;
        gp.obj[mapNum][i] = new objectCoin(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*25;
        gp.obj[mapNum][i].worldY = gp.tileSize*23;
        i++;
        gp.obj[mapNum][i] = new objectCoin(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*21;
        gp.obj[mapNum][i].worldY = gp.tileSize*19;
        i++;
        gp.obj[mapNum][i] = new objectCoin(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*26;
        gp.obj[mapNum][i].worldY = gp.tileSize*21;
        i++;
        gp.obj[mapNum][i] = new objectAxe(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*33;
        gp.obj[mapNum][i].worldY = gp.tileSize*21;
        i++;
        gp.obj[mapNum][i] = new objectBlueShield(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*35;
        gp.obj[mapNum][i].worldY = gp.tileSize*21;
        i++;
        gp.obj[mapNum][i] = new objectPotion(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*22;
        gp.obj[mapNum][i].worldY = gp.tileSize*27;
        i++;
        gp.obj[mapNum][i] = new objectHeart(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*22;
        gp.obj[mapNum][i].worldY = gp.tileSize*29;
        i++;
        gp.obj[mapNum][i] = new objectManaCrystal(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*22;
        gp.obj[mapNum][i].worldY = gp.tileSize*31;
    }

    //ADDING NPCS TO THE MAP
    public void setNPC(){
        //MAP 0
        int mapNum = 0;
        int i = 0;
        gp.npc[mapNum][0] = new NPC_OldMan(gp);
        gp.npc[mapNum][0].worldX = gp.tileSize*21;
        gp.npc[mapNum][0].worldY = gp.tileSize*21;

        //MAP 1
        mapNum = 1;
        gp.npc[mapNum][0] = new NPC_Merchant(gp);
        gp.npc[mapNum][0].worldX = gp.tileSize*12;
        gp.npc[mapNum][0].worldY = gp.tileSize*7;


    }

    //ADDING MONSTERS TO THE MAP
    public void setMonster(){
        int mapNum = 0;
        int i = 0;
        gp.monster[mapNum][i] = new greenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize*21;
        gp.monster[mapNum][i].worldY = gp.tileSize*38;
        i++;
        gp.monster[mapNum][i] = new greenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize*23;
        gp.monster[mapNum][i].worldY = gp.tileSize*42;
        i++;
        gp.monster[mapNum][i] = new greenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize*24;
        gp.monster[mapNum][i].worldY = gp.tileSize*37;
        i++;
        gp.monster[mapNum][i] = new greenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize*34;
        gp.monster[mapNum][i].worldY = gp.tileSize*42;
        i++;
        gp.monster[mapNum][i] = new greenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize*38;
        gp.monster[mapNum][i].worldY = gp.tileSize*42;
    }

    public void setInteractiveTile(){
        int mapNum = 0;
        int i = 0;
        gp.iTile[mapNum][i] = new interactiveDryTree(gp, 27, 12);i++;
        gp.iTile[mapNum][i] = new interactiveDryTree(gp, 28, 12);i++;
        gp.iTile[mapNum][i] = new interactiveDryTree(gp, 29, 12);i++;
        gp.iTile[mapNum][i] = new interactiveDryTree(gp, 30, 12);i++;
        gp.iTile[mapNum][i] = new interactiveDryTree(gp, 31, 12);i++;
        gp.iTile[mapNum][i] = new interactiveDryTree(gp, 32, 12);i++;
        gp.iTile[mapNum][i] = new interactiveDryTree(gp, 18, 40);i++;
        gp.iTile[mapNum][i] = new interactiveDryTree(gp, 17, 40);i++;
        gp.iTile[mapNum][i] = new interactiveDryTree(gp, 16, 40);i++;
        gp.iTile[mapNum][i] = new interactiveDryTree(gp, 14, 40);i++;
        gp.iTile[mapNum][i] = new interactiveDryTree(gp, 13, 41);i++;
        gp.iTile[mapNum][i] = new interactiveDryTree(gp, 12, 41);i++;
        gp.iTile[mapNum][i] = new interactiveDryTree(gp, 11, 41);i++;
        gp.iTile[mapNum][i] = new interactiveDryTree(gp, 10, 41);i++;
        gp.iTile[mapNum][i] = new interactiveDryTree(gp, 15, 40);i++;
        gp.iTile[mapNum][i] = new interactiveDryTree(gp, 13, 40);
    }
}
