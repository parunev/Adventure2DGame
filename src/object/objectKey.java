package object;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class objectKey extends objectManager{

    public objectKey(){
        name = "key";
        try{
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/key.png")));

        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
