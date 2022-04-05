import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        //setting up a window
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Adventure2DGame");

        //not specifying the window position
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}
