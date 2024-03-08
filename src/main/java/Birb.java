import javax.swing.*;
import java.awt.*;

public class Birb {
    private final int BIRB_X = (240 - 130); // x-cordinate for birb. 240 is half of screen width.
    private int birbY = (336 / 2 - 50); // y-cordinate for birb. 336 is half of screen height.
    private final int PLAYER_WIDTH = 70;
    private final int PLAYER_HEIGHT = 56;
    private final int BIRB_SPEED = 8;
    private final int BIRB_GRAVITY = 1;
    private final int BIRB_JUMP = -11;
    private Rectangle birbHitbox;
    private Image sprite;

    public Birb() {
        this.birbHitbox = new Rectangle(BIRB_X, birbY, PLAYER_WIDTH, PLAYER_HEIGHT);
    }

    public void updateHitbox() {
        birbHitbox.setBounds(BIRB_X, birbY, PLAYER_WIDTH, PLAYER_HEIGHT);
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

    public int getPLAYER_WIDTH() {
        return PLAYER_WIDTH;
    }

    public int getPLAYER_HEIGHT() {
        return PLAYER_HEIGHT;
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
}
