import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private final int originalTileSize = 16; // 16x16 tile
    private final int scale = 3;
    private final int tileSize = originalTileSize * scale; // 48x48
    private final int maxScreenColumn = 10;
    private final int getMaxScreenRow = 14;
    private final int screenWidth = tileSize * maxScreenColumn; // 160 pixels
    private final int screenHeight = tileSize * getMaxScreenRow; // 224 pixels

    private int birbX = screenWidth / 2 - 50; // x-cordinate for birb
    private int birbY = screenHeight / 2 - 50; // y-cordinate for birb
    private int playerWidth = 15; // birb width
    private int playerHight = 15; // birb hight

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawPlayer(g);

    }

    private void drawPlayer(Graphics g) {
        g.setColor(Color.ORANGE); // color of birb
        g.fillRect(birbX, birbY, playerWidth, playerHight); // rectangle birb
    }
}
