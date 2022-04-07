package object;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class objectBoots extends objectManager{

    public objectBoots(){

        name = "Boots";
        try{
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/boots.png")));

        }catch (IOException e){e.printStackTrace();}
    }
}
