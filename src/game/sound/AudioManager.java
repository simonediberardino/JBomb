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

    /**
     * Plays the specified sound model.
     *
     * @param soundModel The sound model to play.
     * @param loop       Determines whether the sound should be played in a loop.
     */
    public void play(SoundModel soundModel, boolean loop) {
        try {
            InputStream in = new BufferedInputStream(new FileInputStream(soundModel.toString()));
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(in);
            Clip clip = AudioSystem.getClip();

            clip.open(audioIn);

            // Set the volume of the clip to 1/5 of the available range
            FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float volumeValue = (volume.getMinimum() + volume.getMaximum()) / 5;
            volume.setValue(volumeValue);

            if (loop) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }

            // Start playing the clip
            clip.start();
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e1) {
            e1.printStackTrace();
        }
    }
}
