package object;

import main.gamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class objectChest extends objectManager{

    gamePanel gp;

    public objectChest(gamePanel gp){
        this.gp = gp;
        name = "Chest";
        try{
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/chest.png")));
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);

        }catch (IOException e){e.printStackTrace();}
    }
}
