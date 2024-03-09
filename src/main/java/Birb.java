import org.w3c.dom.css.Rect;

import javax.swing.*;
import java.awt.*;

public class Birb {
    private final int BIRB_X = 110; // x-cordinate for birb. 240 is half of screen width.
    private int birbY = 278; // y-cordinate for birb. 336 is half of screen height.
    private final int BIRB_WIDTH = 70;
    private final int BIRB_HEIGHT = 56;
    private final int BIRB_SPEED = 8;
    private final int BIRB_GRAVITY = 1;
    private final int BIRB_JUMP = -11;

    private final int HITBOX_X = 110;
    private int hitboxY = 293;
    private final int HITBOX_WIDTH = 45;
    private final int HITBOX_HEIGHT = 33;
    private Rectangle birbHitbox;

    private Image sprite;

    private GamePanel gamePanel;

    public Birb() {
        this.birbHitbox = new Rectangle(HITBOX_X, hitboxY, HITBOX_WIDTH, HITBOX_HEIGHT);
    }

    public void updateHitbox() {
        birbHitbox.setBounds(HITBOX_X, hitboxY, HITBOX_WIDTH, HITBOX_HEIGHT);
    }

    public int getBirbY() {
        return birbY;
    }

    public void setBirbY(int birbY) {
        this.birbY = birbY;
    }

    public Rectangle getBirbHitbox() {
        return birbHitbox;
    }

    public int getBIRB_SPEED() {
        return BIRB_SPEED;
    }

    public int getBIRB_HEIGHT() {
        return BIRB_HEIGHT;
    }

    public int getBIRB_GRAVITY() {
        return BIRB_GRAVITY;
    }

    public int getBIRB_JUMP() {
        return BIRB_JUMP;
    }

    public int getHitboxY() {
        return hitboxY;
    }

    public void setHitboxY(int hitboxY) {
        this.hitboxY = hitboxY;
    }

    /**
     * @param g This method draws the birb.
     */
    public void drawBirb(Graphics g, KeyControls keyControls) {

        if (keyControls.getSpacebar()) {
            sprite = new ImageIcon("Images/birb_sprite2.png").getImage();

        } else {
            sprite = new ImageIcon("Images/birb_sprite.png").getImage();
        }

        g.fillRect(HITBOX_X, hitboxY, HITBOX_WIDTH, HITBOX_HEIGHT);
        // Draws Birb hitbox for test.
        g.drawImage(sprite, BIRB_X, birbY,
                BIRB_WIDTH, BIRB_HEIGHT, null);
    }

    public void birbControls(KeyControls keyControls) {
        if (keyControls.getSpacebar()) {
            birbY = birbY + BIRB_JUMP;
            hitboxY = hitboxY + BIRB_JUMP;

        } else {
            birbY = birbY + BIRB_SPEED / 2 + BIRB_GRAVITY;
            hitboxY = hitboxY + BIRB_SPEED / 2 + BIRB_GRAVITY;
        }

        updateHitbox();
    }
}
