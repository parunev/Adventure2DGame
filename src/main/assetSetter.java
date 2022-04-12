package main;

import entity.NPC_OldMan;
import monster.greenSlime;

public class assetSetter {

    gamePanel gp;

    public assetSetter(gamePanel gp){
        this.gp = gp;
    }

    //setting object locations
    public void setObject(){

    }
    public void setNPC(){
        gp.npc[0] = new NPC_OldMan(gp);
        gp.npc[0].worldX = gp.tileSize*21;
        gp.npc[0].worldY = gp.tileSize*21;

    }

    public void setMonster(){

        int i = 0;
        gp.monster[i] = new greenSlime(gp);
        gp.monster[i].worldX = gp.tileSize*21;
        gp.monster[i].worldY = gp.tileSize*38;
        i++;
        gp.monster[i] = new greenSlime(gp);
        gp.monster[i].worldX = gp.tileSize*23;
        gp.monster[i].worldY = gp.tileSize*42;
        i++;
        gp.monster[i] = new greenSlime(gp);
        gp.monster[i].worldX = gp.tileSize*24;
        gp.monster[i].worldY = gp.tileSize*37;
        i++;
        gp.monster[i] = new greenSlime(gp);
        gp.monster[i].worldX = gp.tileSize*34;
        gp.monster[i].worldY = gp.tileSize*42;
        i++;
        gp.monster[i] = new greenSlime(gp);
        gp.monster[i].worldX = gp.tileSize*38;
        gp.monster[i].worldY = gp.tileSize*42;
    }
}
