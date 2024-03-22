import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundPlayer {
    private Clip clip;
    private boolean isPlaying = false;

    public void playSound(String SoundFilePath) {
        try {

            if (clip != null && clip.isRunning()) {
                return; // Om ett ljud redan spelas, avbryt och spela inte upp igen
            }

            File soundFile = new File(SoundFilePath);

            if (!soundFile.exists()) {
                System.out.println("The file doesn't exists." + SoundFilePath);
                return;
            }

            AudioInputStream audioIS = AudioSystem.getAudioInputStream(soundFile);
            clip = AudioSystem.getClip();
            clip.open(audioIS);
            clip.start();

            clip.addLineListener(event -> {
                if (event.getType() == javax.sound.sampled.LineEvent.Type.STOP) {
                    clip.close(); // Stäng klippet för att frigöra resurser
                }
            });

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void stopSound() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }
}
