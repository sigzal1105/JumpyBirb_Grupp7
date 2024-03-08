import org.w3c.dom.css.Rect;

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
    private int hitboxY = 295;
    private final int HITBOX_WIDTH = 40;
    private final int HITBOX_HEIGHT = 30;

    private Rectangle birbHitbox;
    private Image sprite;

    public Birb() {
        this.birbHitbox = new Rectangle(HITBOX_X, hitboxY, HITBOX_WIDTH, HITBOX_HEIGHT);
    }

    public void updateHitbox() {
        birbHitbox.setBounds(HITBOX_X, hitboxY, HITBOX_WIDTH, HITBOX_HEIGHT);
    }

    public int getBIRB_X() {
        return BIRB_X;
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

    public int getBIRB_WIDTH() {
        return BIRB_WIDTH;
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

    public Image getSprite() {
        return sprite;
    }

    public void setSprite(Image sprite) {
        this.sprite = sprite;
    }

    public int getHITBOX_X() {
        return HITBOX_X;
    }

    public int getHitboxY() {
        return hitboxY;
    }

    public void setHitboxY(int hitboxY) {
        this.hitboxY = hitboxY;
    }

    public int getHITBOX_WIDTH() {
        return HITBOX_WIDTH;
    }

    public int getHITBOX_HEIGHT() {
        return HITBOX_HEIGHT;
    }
}
