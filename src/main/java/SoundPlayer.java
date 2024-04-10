import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundPlayer {
    private Clip clipJump;
    private Clip clipDeath;
    private Clip clipBackground;

    /**
     * Initializes the SoundPlayer by loading audio clips.
     */
    public SoundPlayer() {
        try {
            AudioInputStream audioISJump = AudioSystem.getAudioInputStream(new File("SoundFiles/Jump.wav"));
            AudioInputStream audioISDeath = AudioSystem.getAudioInputStream(new File("SoundFiles/Explosion.wav"));
            AudioInputStream audioISBackground = AudioSystem
                    .getAudioInputStream(new File("SoundFiles/BackgroundSound.wav"));

            // Öppna Clips för ljudklippen
            clipBackground = AudioSystem.getClip();
            clipBackground.open(audioISBackground);

            clipJump = AudioSystem.getClip();
            clipJump.open(audioISJump);

            clipDeath = AudioSystem.getClip();
            clipDeath.open(audioISDeath);

        } catch (Exception ex) {
            System.err.println("Error loading clips: " + ex.getMessage());

        }

    }

    /**
     * Plays the specified sound clip.
     *
     * @param clip The Clip to play
     */
    public Clip getClipJump() {
        return clipJump;
    }

    public Clip getClipDeath() {
        return clipDeath;
    }

    public Clip getClipBackground() {
        return clipBackground;
    }

    public void playSound(Clip clip) {
        try {
            if (clip != null) {
                clip.setFramePosition(0);
                if (clip.isRunning()) {
                    clip.stop();
                }
                clip.start();
            }

        } catch (Exception ex) {
            System.err.println("Error playing sound: " + ex.getMessage());
        }
    }

    /**
     * Stops any currently playing sound clips.
     */
    public void stopSound() {
        if (clipJump != null && clipJump.isRunning()) {
            clipJump.stop();
        }
        if (clipDeath != null && clipDeath.isRunning()) {
            clipDeath.stop();
        }
    }

    public static void playNonStop(Clip clip) {
        if (clip != null) {
            clip.loop(clip.LOOP_CONTINUOUSLY);
        }
    }

}
