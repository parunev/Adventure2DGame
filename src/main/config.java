package main;

import java.io.*;

public class config {

    gamePanel gp;

    public config(gamePanel gp){
        this.gp = gp;
    }

    public void safeConfig() {

        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter("config"));

            //FULL SCREEN SETTING
            if (gp.fullScreenOn){
                bw.write("On");
            }
            if (!gp.fullScreenOn){
                bw.write("Off");
            }
            bw.newLine();

            //MUSIC VOLUME
            bw.write(String.valueOf(gp.music.volumeScale));
            bw.newLine();

            //SE VOLUME
            bw.write(String.valueOf(gp.se.volumeScale));
            bw.newLine();

            bw.close();
        }catch (IOException e){
            e.printStackTrace();
        }

    }
    public void loadConfig() {

        try{
            BufferedReader br = new BufferedReader(new FileReader("config"));
            String s = br.readLine();

            //FULL SCREEN
            if (s.equals("On")){
                gp.fullScreenOn = true;
            }
            if (s.equals("Off")){
                gp.fullScreenOn = false;
            }

            //MUSIC VOLUME
            s = br.readLine();
            gp.music.volumeScale = Integer.parseInt(s);

            //SE VOLUME
            s = br.readLine();
            gp.se.volumeScale = Integer.parseInt(s);

            br.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
