
package uet.oop.bomberman.sound;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Sound {
    private File file;
    private AudioInputStream audioInputStream;
    private Clip clip;

    private static boolean isOn = true;

    public Sound(String path) {
        file = new File(path);
        this.getAudio();
    }

    private void getAudio() {
        try {
            audioInputStream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    public void setOn(boolean on) {
        isOn = on;
    }

    public static boolean isIsOn() {
        return isOn;
    }

    public static void toggleStatus() {
        if (isOn) {
            isOn = false;
        } else {
            isOn = true;
        }
    }

    public void start() {
        if (isOn) clip.start();
    }

    public void restart() {
        clip.setMicrosecondPosition(0);
    }

    public void loop() {
        if (isOn) clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void close() {
        clip.stop();
    }

    public static void closeAll() {
        bombExplode.close();
        gameClear.close();
        gameOver.close();
        menuTheme.close();
        powerUp.close();
    }
    public static Sound bombExplode = new Sound("resources/sounds/BombExplode.wav");

    public static Sound soundTheme = new Sound("resources/sounds/Sound_Theme.wav");

    public static Sound gameClear = new Sound("resources/sounds/GameClear.wav");

    public static Sound gameOver = new Sound("resources/sounds/GameOver.wav");

    public static Sound menuTheme = new Sound("resources/sounds/MenuTheme.wav");

    public static Sound powerUp = new Sound("resources/sounds/PowerUp.wav");
}
