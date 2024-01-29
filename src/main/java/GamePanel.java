import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private final int originalTileSize = 16; // 16x16 tile
    private final int scale = 3;
    private final int tileSize = originalTileSize * scale; // Size of the tiles scaled, 48x48
    private final int maxScreenColumn = 10;
    private final int getMaxScreenRow = 14;
    private final int screenWidth = tileSize * maxScreenColumn; // 160 pixels
    private final int screenHeight = tileSize * getMaxScreenRow; // 224 pixels

    private int birbX = screenWidth / 2 - 50; // x-cordinate for birb
    private int birbY = screenHeight / 2 - 50; // y-cordinate for birb

    private int birbSpeed = 4;
    private int playerWidth = 15; // birb width
    private int playerHight = 15; // birb hight

    KeyControls keyControls = new KeyControls();

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyControls);
        this.setFocusable(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBackground(g);
        drawPlayer(g);

    }

    private void drawPlayer(Graphics g) {
        g.setColor(Color.ORANGE); // color of birb
        g.fillRect(birbX, birbY, playerWidth, playerHight); // rectangle birb
    }

    private void drawBackground(Graphics g) {
        // set dimensions
        int upperHeight = screenHeight * 15 / 100;
        int middleHeight = screenHeight * 65 / 100;
        int groundHeight = screenHeight * 5 / 100;
        int bottomHeight = screenHeight * 15 / 100;

        // pant the top
        g.setColor(Color.YELLOW);
        g.fillRect(0, 0, screenWidth, upperHeight);

        // paint the middle
        g.setColor(Color.CYAN);
        g.fillRect(0, upperHeight, screenWidth, middleHeight);

        // paint the ground
        g.setColor(Color.GREEN);
        g.fillRect(0, upperHeight + middleHeight, screenWidth, groundHeight);

        // paint the bottom
        g.setColor(Color.YELLOW);
        g.fillRect(0, upperHeight + middleHeight + groundHeight, screenWidth, bottomHeight);
    }

    public void update() {
        if (keyControls.getSpacebar()) {
            birbY = birbY - birbSpeed;

        } else {

            birbY = birbY - Integer.MAX_VALUE;
        }
    }
}