package main;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class sound {

    Clip clip;
    URL[] soundURL = new URL[30]; //storing sound paths

    //SOUND LIBRARY
    public sound(){
        soundURL[0] = getClass().getResource("/res/music.wav");
        soundURL[1] = getClass().getResource("/res/coin.wav");
        soundURL[2] = getClass().getResource("/res/powerup.wav");
        soundURL[3] = getClass().getResource("/res/unlock.wav");
        soundURL[4] = getClass().getResource("/res/fanfare.wav");
        soundURL[5] = getClass().getResource("/res/hitmonster.wav");
        soundURL[6] = getClass().getResource("/res/receivedamage.wav");
        soundURL[7] = getClass().getResource("/res/swordwoosh.wav");
        soundURL[8] = getClass().getResource("/res/levelup.wav");
    }

    public void setFile(int i){
        try{
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {e.printStackTrace();}
    }

    public void play(){clip.start();}

    //LOOP FOR REPEATING THE BACKGROUND MUSIC
    public void loop(){clip.loop(Clip.LOOP_CONTINUOUSLY);}

    public void stop(){clip.stop();}
}
