import javax.swing.*;
import java.awt.*;

public class Birb {
    private final int BIRB_X = 110; // x-cordinate for birb. 240 is half of screen width.
    private int birbY = 278; // y-cordinate for birb. 336 is half of screen height.

    private final int BIRB_WIDTH = 70;
    private final int BIRB_HEIGHT = 56;

    private int birbVelocity = 0;
    private final int BIRB_GRAVITY = 1;

    private final int HITBOX_X = 110;
    private int hitboxY = 293;
    private final int HITBOX_WIDTH = 45;
    private final int HITBOX_HEIGHT = 40;
    private Rectangle birbHitbox;
    private Image sprite;
    private boolean dead;

    // Soundplayer
    private final transient SoundPlayer SOUND_PLAYER = new SoundPlayer();

    public Birb() {
        this.birbHitbox = new Rectangle(HITBOX_X, hitboxY, HITBOX_WIDTH, HITBOX_HEIGHT);
    }

    public void updateHitbox() {
        birbHitbox.setBounds(HITBOX_X, hitboxY, HITBOX_WIDTH, HITBOX_HEIGHT);
    }

    public Rectangle getBirbHitbox() {
        return birbHitbox;
    }

    public int getHitboxY() {
        return hitboxY;
    }

    public int getHITBOX_HEIGHT() {
        return HITBOX_HEIGHT;
    }

    public boolean getDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public void reset() {
        birbY = 278;
        sprite = new ImageIcon("Images/birb_sprite.png").getImage();
        birbVelocity = 0;
        dead = false;
        hitboxY = 293;
    }

    /**
     * @param g This method draws the birb.
     */
    public void drawBirb(Graphics g, KeyControls keyControls, boolean dead) {

        if (birbVelocity < 0) {

            sprite = new ImageIcon("Images/birb_sprite2.png").getImage();

        } else {

            sprite = new ImageIcon("Images/birb_sprite.png").getImage();
        }
        if (dead) {

            sprite = new ImageIcon("Images/explosion.png").getImage();
        }

        // Draws Birb hitbox for test.
        // g.fillRect(HITBOX_X, hitboxY, HITBOX_WIDTH, HITBOX_HEIGHT);
        g.drawImage(sprite, BIRB_X, birbY,
                BIRB_WIDTH, BIRB_HEIGHT, null);
    }

    private void flight() {
        birbVelocity = birbVelocity + BIRB_GRAVITY;
        birbY = birbY + birbVelocity;
        hitboxY = hitboxY + birbVelocity;
    }

    public void birbControls(KeyControls keyControls) {
        flight();

        if (keyControls.getSpacebar() || keyControls.getMouseClick()) {

            birbVelocity = -10;
            SOUND_PLAYER.playSound(SOUND_PLAYER.getClipJump());
            //SOUND_PLAYER.stopSound();

        }
        updateHitbox();
    }
}
