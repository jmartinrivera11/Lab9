
package reproductor;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Reproductor {
    
    private Clip clip;
    private long timeline;
    
    public void play(String path) {
        try {
            File file = new File(path);
            AudioInputStream ais = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(ais);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    
    
    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }
    
    public void pause() {
        if (clip != null && clip.isRunning()) {
            timeline = clip.getMicrosecondPosition();
            clip.stop();
        }
    }
    
    public void resume() {
        if (clip != null) {
            clip.setMicrosecondPosition(timeline);
            clip.start();
        }
    }
}
