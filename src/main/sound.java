package main;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class sound {

    Clip clip;
    URL[] soundURL = new URL[30]; //storing sound paths
    FloatControl fc;
    int volumeScale = 3;
    float volume;

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
        soundURL[9] = getClass().getResource("/res/cursor.wav");
        soundURL[10] = getClass().getResource("/res/burning.wav");
        soundURL[11] = getClass().getResource("/res/cuttree.wav");
        soundURL[12] = getClass().getResource("/res/gameover.wav");
        soundURL[13] = getClass().getResource("/res/stairs.wav");
    }

    public void setFile(int i){
        try{
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
            fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            checkVolume();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {e.printStackTrace();}
    }

    public void play(){clip.start();}

    //LOOP FOR REPEATING THE BACKGROUND MUSIC
    public void loop(){clip.loop(Clip.LOOP_CONTINUOUSLY);}

    public void stop(){clip.stop();}

    //SETTING UP VOLUME
    public void checkVolume(){
        switch (volumeScale) {
            case 0 -> volume = -80f;
            case 1 -> volume = -20f;
            case 2 -> volume = -12f;
            case 3 -> volume = -5f;
            case 4 -> volume = 1f;
            case 5 -> volume = 6f;
        }
        fc.setValue(volume);
    }
}
