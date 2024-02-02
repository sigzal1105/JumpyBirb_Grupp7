import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    private final int originalTileSize = 16; // 16x16 tile
    private final int scale = 3;

    private final int tileSize = originalTileSize * scale; // Size of the tiles scaled, 48x48
    private final int maxScreenColumn = 10;
    private final int getMaxScreenRow = 14;
    private final int screenWidth = tileSize * maxScreenColumn; // 160 pixels
    private final int screenHeight = tileSize * getMaxScreenRow; // 224 pixels

    private int scrollPosition = 0;
    private final int scrollSpeed = 4;

    private int birbX = (screenWidth / 2 - 50); // x-cordinate for birb
    private int birbY = (screenHeight / 2 - 50); // y-cordinate for birb

    private int birbSpeed = 8;
    private int playerWidth = 15; // birb width
    private int playerHeight = 15; // birb hight


    private Image backgroundImage;
    private Image groundImage;


    KeyControls keyControls = new KeyControls();

    //Frames Per Second
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
        backgroundImage = new ImageIcon("Images/background_test.jpg").getImage();
        groundImage = new ImageIcon("Images/ground_flowers.png").getImage();

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, screenWidth, screenHeight, this);
        drawPlayer(g);
        drawGround(g);
    }


    /**
     * @param g This method draws the birb.
     */
    private void drawPlayer(Graphics g) {
        g.setColor(Color.ORANGE);
        g.fillRect(birbX, birbY, playerWidth, playerHeight);
    }


    /**
     * @param g This method loops the ground tiles.
     */
    private void drawGround(Graphics g) {

        for (int i = 0; i < 20; i++) {
            int x = i * tileSize - scrollPosition % tileSize;

            g.drawImage(groundImage, x, 500, tileSize, tileSize, this);
        }


    }


    /**
     * update() is to be put in the run() method.
     * This methods contains the controls to the birb.
     */
    public void update() {
        if (keyControls.getSpacebar()) {
            birbY = birbY - birbSpeed;

        } else {

            birbY = birbY + (birbSpeed / 2);
        }
    }

    @Override
    public void run() {

        //Tell the system when to draw the screen again.
        double drawInterval = (double) 1000000000 / FPS; //0.016666 seconds
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null) {
            update();
            scrollPosition = scrollPosition + scrollSpeed;
            repaint();

            try {

                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000;

                //If game doesn't have any
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