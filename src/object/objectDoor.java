package object;

import main.gamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class objectDoor extends objectManager{

    gamePanel gp;

    public objectDoor(gamePanel gp){
        this.gp = gp;
        name = "Door";
        try{
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/door.png")));
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        }catch (IOException e){e.printStackTrace();}
        collision = true;
    }
}
