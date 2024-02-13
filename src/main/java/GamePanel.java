import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    private final int originalTileSize = 16; // 16x16 tile
    private final int scale = 3;

    private final int tileSize = originalTileSize * scale; // Size of the tiles scaled, 48x48
    private final int maxScreenColumn = 10; 
    private final int getMaxScreenRow = 14;
    private final int screenWidth = tileSize * maxScreenColumn; // 480 pixels
    private final int screenHeight = tileSize * getMaxScreenRow; // 672 pixels

    private int scrollPosition = 0;
    private final int scrollSpeed = 2;

    private int birbX = (screenWidth / 2 - 130); // x-cordinate for birb
    private int birbY = (screenHeight / 2 - 50); // y-cordinate for birb

    private Image backgroundImage;
    private Image groundImage;
    private Image bottomPipe;
    private Image topPipe;
    private Image shortBottomPipe;
    private Image shortTopPipe;


    KeyControls keyControls = new KeyControls();

    // Frames Per Second
    private final int FPS = 60;
    Thread gameThread;

    public void startGame() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyControls);
        this.setFocusable(true);
        backgroundImage = new ImageIcon("Images/Background_night.png").getImage();
        groundImage = new ImageIcon("Images/ground_flowers.png").getImage();
        groundImage = new ImageIcon("Images/ground_flowers_night.png").getImage();
        bottomPipe = new ImageIcon("Images/bottomPipe.png").getImage();
//        topPipe = new ImageIcon("Images/topPipe.png").getImage();
//        shortBottomPipe = new ImageIcon("Images/shortBottomPipe.png").getImage();
//        shortTopPipe = new ImageIcon("Images/shortTopPipe.png").getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, screenWidth, screenHeight+50, this);
        drawPlayer(g);
        drawGround(g);
        drawPipe(g);
    }

    /**
     * @param g This method draws the birb.
     */
    private void drawPlayer(Graphics g) {
        Birb birb = new Birb();
        g.setColor(Color.ORANGE);
        g.fillRect(birbX, birbY, birb.getPlayerWidth(), birb.getPlayerHeight());
    }

    /**
     * @param g This method loops the ground tiles.
     */
    private void drawGround(Graphics g) {

        for (int i = 0; i < 20; i++) {
            int x = i * tileSize - scrollPosition % tileSize;
            g.drawImage(groundImage, x, 624, tileSize, tileSize, this);
        }
    }

    /**
     * @param g This method loops the pipe objects.
     */
    private void drawPipe(Graphics g){

        for (int i = 0; i < 20; i = i+5) {
            int x = i * tileSize % tileSize;
            g.drawImage(bottomPipe, x, 530, tileSize+5, 200, this);
        }
    }

    /**
     * update() is to be put in the run() method.
     * This methods contains the controls to the birb.
     */
    public void update() {
        Birb birb = new Birb();
        if (keyControls.getSpacebar()) {
            birbY = birbY - birb.getBirbSpeed();

        } else {

            birbY = birbY + (birb.getBirbSpeed() / 2);
        }
    }

    @Override
    public void run() {

        // Tell the system when to draw the screen again.
        double drawInterval = (double) 1000000000 / FPS; // 0.016666 seconds
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null) {
            update();
            scrollPosition = scrollPosition + scrollSpeed;
            repaint();

            try {

                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000;

                // If game doesn't have any
                if (remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);
                nextDrawTime = nextDrawTime + drawInterval;

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}