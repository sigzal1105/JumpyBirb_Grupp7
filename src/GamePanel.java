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

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
    }
}
