package object;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class objectDoor extends objectManager{
    public objectDoor(){
        name = "Door";
        try{
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/door.png")));

        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
