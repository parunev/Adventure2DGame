package tile;

import main.gamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class TileManager {
    gamePanel gp;
    public Tile[] tile; //array
    public int[][] mapTileNum;

    public TileManager(gamePanel gp){
        this.gp = gp;
        tile = new Tile[10];// setting up 10 tiles (water, sand, water, walls, etc. etc.)
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
        getTileImage();
        loadMap("/res/world01.txt");
    }

    //implementing the images similar to the player part
    public void getTileImage(){
        try{
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/grass.png")));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/wall.png")));
            tile[1].collision = true;

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/water.png")));
            tile[2].collision = true;

            tile[3] = new Tile();
            tile[3].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/earth.png")));

            tile[4] = new Tile();
            tile[4].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/tree.png")));
            tile[4].collision = true;

            tile[5] = new Tile();
            tile[5].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/sand.png")));

        }catch(IOException e){
            e.printStackTrace();
        }
    }


    public void loadMap(String filePath){
        try{
            InputStream is = getClass().getResourceAsStream(filePath);
            assert is != null;
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;
            while (col < gp.maxWorldCol && row < gp.maxWorldRow){
                String line = br.readLine(); // reads a single line
                while (col < gp.maxWorldCol){
                    String[] numbers = line.split(" ");

                    int num = Integer.parseInt(numbers[col]); // using col as index for numbers[]array

                    mapTileNum[col][row] = num; // we store the extracted number
                    col++; // continue this until everything in the numbers[] is store in the mapTileNum[][]
                }
                if (col == gp.maxWorldCol){
                    col = 0;
                    row++;
                }
            }
            br.close();
        }catch (Exception ignored){}
    }


    public void draw(Graphics2D g2){
       int worldCol = 0;
       int worldRow = 0;

       while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow){
           int tileNum = mapTileNum[worldCol][worldRow];

           // extracting a tile number from index
            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            //cutting some extra processing
            //improving rendering efficiency
            if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                    worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                    worldY + gp.tileSize> gp.player.worldY - gp.player.screenY &&
                    worldY - gp.tileSize < gp.player.worldY + gp.player.screenY){

                // tile[tileNum].image is getting number from the txt. 0 - grass, 1 - wall, 2 - water etc. etc.
                g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            }

           worldCol++;

           if (worldCol == gp.maxWorldCol){
               worldCol = 0;
               worldRow++;
           }
       }
    }
}
