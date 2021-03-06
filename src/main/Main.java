package main;

import javax.swing.*;

public class  Main {

    public static JFrame window;
    public static void main(String[] args) {

        window = new JFrame();
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Adventure2DGame");

        //calling the gamePanel
        gamePanel gamePanel = new gamePanel();
        window.add(gamePanel);

        gamePanel.config.loadConfig();
        if (gamePanel.fullScreenOn){
            window.setUndecorated(true);
        }

        window.pack(); // window to be sized to fit the preferred size and layouts of its subcomponents "gamePanel"

        //not specifying the window position
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.setupGame();
        gamePanel.startGameThread();
    }
}
