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
    private int playerHeight = 15; // birb hight

    private Image backgroundImage; 
    private Image groundImage;

    KeyControls keyControls = new KeyControls();

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyControls);
        this.setFocusable(true);
        backgroundImage = new ImageIcon("Images/background_test.jpg").getImage();
        groundImage = new ImageIcon("Images/ground_test.jpg").getImage();

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, screenWidth, screenHeight, this);
        g.drawImage(groundImage, 0, 500, screenWidth, 70, this); // y for placement in height, 70 for image height
        drawPlayer(g);
    }

    private void drawPlayer(Graphics g) {
        g.setColor(Color.ORANGE);
        g.fillRect(birbX, birbY, playerWidth, playerHeight);
    }

    public void update() {
        if (keyControls.getSpacebar()) {
            birbY = birbY - birbSpeed;

        } else {

            birbY = birbY - Integer.MAX_VALUE;
        }
    }
}