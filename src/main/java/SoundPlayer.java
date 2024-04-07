import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundPlayer {
    private Clip clipJump;
    private Clip clipDeath;
    private Clip clip;
    private boolean isPlaying = false;

    public SoundPlayer() {
        try {
            AudioInputStream audioISJump = AudioSystem.getAudioInputStream(new File("SoundFiles/Jump.wav"));
            AudioInputStream audioISDeath = AudioSystem.getAudioInputStream(new File("SoundFiles/Explosion.wav"));

            // Öppna Clips för ljudklippen
            clipJump = AudioSystem.getClip();
            clipJump.open(audioISJump);

            clipDeath = AudioSystem.getClip();
            clipDeath.open(audioISDeath);

        } catch (Exception ex) {
            System.err.println("Error loading clips: " + ex.getMessage());

        }
    }

    public Clip getClipJump() {
        return clipJump;
    }

    public Clip getClipDeath() {
        return clipDeath;
    }

    public void playSound(Clip clip) {
        try {
            if (clip != null) {
                if (clip.isRunning()) {
                    clip.stop();
                }
                clip.setFramePosition(0);
                clip.start();
            }

        } catch (Exception ex) {
            System.err.println("Error playing sound: " + ex.getMessage());
        }
    }

    public void stopSound() {
        if (clipJump != null && clipJump.isRunning()) {
            clipJump.stop();
        }
        if (clipDeath != null && clipDeath.isRunning()) {
            clipDeath.stop();
        }
    }
}
