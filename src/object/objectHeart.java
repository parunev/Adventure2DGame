package object;

import main.gamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class objectHeart extends objectManager{

    gamePanel gp;

    public objectHeart(gamePanel gp){
        this.gp = gp;
        name = "Heart";
        try{
            image  = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/heart_full.png")));
            image2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/heart_half.png")));
            image3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/heart_blank.png")));
            image  = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
            image2 = uTool.scaleImage(image2, gp.tileSize, gp.tileSize);
            image3 = uTool.scaleImage(image3, gp.tileSize, gp.tileSize);
        }catch (IOException e){e.printStackTrace();}
        collision = true;
    }
}
