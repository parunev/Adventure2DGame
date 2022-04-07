package object;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class objectChest extends objectManager{

    public objectChest(){
        name = "Chest";
        try{
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/chest.png")));

        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
