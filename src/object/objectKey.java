package object;

import main.gamePanel;

public class objectKey extends entity.entity{

    public objectKey(gamePanel gp){
        super(gp);

        name = "key";
        down1 = setup("key");
    }
}
