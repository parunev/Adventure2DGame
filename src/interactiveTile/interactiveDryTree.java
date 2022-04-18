package interactiveTile;

import entity.entity;
import main.gamePanel;

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
}
