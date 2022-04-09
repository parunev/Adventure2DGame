package main;

import entity.NPC_OldMan;
import object.objectBoots;
import object.objectChest;
import object.objectDoor;
import object.objectKey;

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
}
