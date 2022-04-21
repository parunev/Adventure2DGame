package interactiveTile;

import entity.entity;
import main.gamePanel;

import java.awt.*;

public class interactiveDryTree extends interactiveTile{
    public interactiveDryTree(gamePanel gp, int col, int row) {
        super(gp, col, row);

        this.worldX = gp.tileSize * col;
        this.worldY = gp.tileSize * row;

        down1 = setup("drytree",gp.tileSize,gp.tileSize);
        destructible = true;
        life = 3;
    }
    public boolean isCorrectItem(entity entity){
        boolean isCorrectItem = entity.currentWeapon.type == typeAxe;
        return isCorrectItem;
    }
    public void playSE(){
        gp.playSE(11);
    }
    public interactiveTile getDestroyedForm(){
        interactiveTile tile = new interactiveTrunk(gp, worldX/gp.tileSize, worldY/gp.tileSize);
        return tile;
    }
    public Color getParticleColor(){
        Color color = new Color(65, 50, 30);
        return color;
    }
    public int getParticleSize(){
        int size = 6; // 6 pixels
        return size;
    }
    public int getParticleSpeed(){
        int speed = 1;
        return speed;
    }
    public int getParticleMaxLife(){
        int maxLife = 20;
        return maxLife;
    }
}
