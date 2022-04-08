package object;

import main.gamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class objectKey extends objectManager{

    gamePanel gp;
    public objectKey(gamePanel gp){
        this.gp = gp;
        name = "key";
        try{
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/key.png")));
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        }catch (IOException e){e.printStackTrace();}
    }
}
