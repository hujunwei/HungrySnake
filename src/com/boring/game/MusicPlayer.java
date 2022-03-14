package com.boring.game;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * @Author: jasonhu
 * @Date: 2022/3/14
 * @Description: com.boring.game
 * @version: 1.0
 */
public class MusicPlayer {
    public static void playMusic() {
        File audioFile = new File(Musics.backgroundMusicPath);
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            AudioFormat format = audioStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            Clip audioClip = (Clip) AudioSystem.getLine(info);
            audioClip.open(audioStream);
            audioClip.start();
        } catch (UnsupportedAudioFileException ex) {
            System.out.println("The specified audio file is not supported.");
            ex.printStackTrace();
        } catch (LineUnavailableException ex) {
            System.out.println("Audio line for playing back is unavailable.");
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("Error playing the audio file.");
            ex.printStackTrace();
        }
    }
}
