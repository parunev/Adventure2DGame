package object;

import entity.entity;
import main.gamePanel;
import main.userInterface;

public class objectCoin extends entity {
    public objectCoin(gamePanel gp) {
        super(gp);

        type = 7;
        name = "Coin";
        value = 1;
        down1 = setup("coin_bronze",gp.tileSize, gp.tileSize);
    }
    public void use(entity entity){

        gp.playSE(1);
        userInterface.addMessage("Coin +" + value);
        gp.player.coin += value;
    }
}
