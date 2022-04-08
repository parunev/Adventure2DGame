package object;

import main.gamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class objectBoots extends objectManager{

    gamePanel gp;

    public objectBoots(gamePanel gp){
        this.gp = gp;

        name = "Boots";
        try{
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/boots.png")));
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);

        }catch (IOException e){e.printStackTrace();}
    }
}
