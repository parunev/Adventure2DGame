package entity;

import main.gamePanel;
import object.*;

public class NPC_Merchant extends entity{

    public NPC_Merchant(gamePanel gp) {
        super(gp);
        direction = "down";
        speed = 1;
        getImage();
        setDialog();
        setItems();
    }
    public void getImage(){

        up1 = setup("merchant_down_1", gp.tileSize, gp.tileSize);
        up2 = setup("merchant_down_2", gp.tileSize, gp.tileSize);
        down1 = setup("merchant_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("merchant_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("merchant_down_1", gp.tileSize, gp.tileSize);
        left2 = setup("merchant_down_2", gp.tileSize, gp.tileSize);
        right1 = setup("merchant_down_1", gp.tileSize, gp.tileSize);
        right2 = setup("merchant_down_2", gp.tileSize, gp.tileSize);
    }
    //DIALOG PRESETS
    public void setDialog(){
        dialogues[0]="He he, so you found me.\nI have some good stuff.\nDo you want to trade?";
    }
    public void setItems(){
        inventory.add(new objectPotion(gp));
        inventory.add(new objectKey(gp));
        inventory.add(new objectSword(gp));
        inventory.add(new objectAxe(gp));
        inventory.add(new objectBlueShield(gp));
    }

}
