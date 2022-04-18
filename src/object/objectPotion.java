package object;

import entity.entity;
import main.gamePanel;
import main.userInterface;

public class objectPotion extends entity {

    public objectPotion(gamePanel gp) {
        super(gp);

        type = typeConsumable;
        name = "Health Potion";
        value = 5;
        down1 = setup("potion_red", gp.tileSize, gp.tileSize);
        defenceValue = 1;
        description = "[" + name + "]\nHeals your life by " + value + ".";
    }
    public void use(entity entity){
        gp.gameState = gp.dialogState;
        userInterface.currentDialog = "You drink the " + name + "!\n" + "Your life has been recovered by " + value + ".";
        entity.life += value;
        if (gp.player.life > gp.player.maxLife){
            gp.player.life = gp.player.maxLife;
        }
        gp.playSE(2);
    }
}
