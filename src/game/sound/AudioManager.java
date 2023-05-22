package game.sound;


import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.*;

public class AudioManager {
    private static AudioManager instance;

    public static AudioManager getInstance() {
        if (instance == null)
            instance = new AudioManager();
        return instance;
    }

    private AudioManager() {}

    public void play(SoundModel soundModel){
        play(soundModel, false);
    }

    public void play(SoundModel soundModel, boolean loop) {
        try {
            InputStream in = new BufferedInputStream(new FileInputStream(soundModel.toString()));
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(in);
            Clip clip = AudioSystem.getClip();

            clip.open(audioIn);
            FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float volumeValue = (volume.getMinimum() + volume.getMaximum()) / 5;
            volume.setValue(volumeValue);

            if(loop){
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }

            clip.start();
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e1) {
            e1.printStackTrace();
        }
    }
}
