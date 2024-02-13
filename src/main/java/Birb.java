import java.awt.*;

public class Birb {
    private final int playerWidth = 40;
    private final int playerHeight = 36;
    private final int birbSpeed = 8;
    private Rectangle hitbox;
    private Image sprite1;
    private Image sprite2;
    private Image sprite3;

    public int getBirbSpeed() {
        return birbSpeed;
    }

    public int getPlayerWidth() {
        return playerWidth;
    }

    public int getPlayerHeight() {
        return playerHeight;
    }
}
