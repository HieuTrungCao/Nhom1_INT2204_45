package uet.oop.bomberman.sound;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Sound {
    private File file;
    private AudioInputStream audioInputStream;
    private Clip clip;

    public Sound(String path) {
        file = new File(path);

        this.getAudio();
    }

    private void getAudio() {
        try {
            audioInputStream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    public void start() {
        System.out.println("Start");
        clip.start();
    }

    public void restart() {
        System.out.println("restart");
        clip.setMicrosecondPosition(0);
    }

    public void close() {
        clip.close();
    }
    public static Sound bombExplode = new Sound("resources/sounds/Bomb_Explode.wav");

}
