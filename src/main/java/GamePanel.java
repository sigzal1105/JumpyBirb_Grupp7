import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class GamePanel extends JPanel implements Runnable {
    private final int ORIGINAL_TILE_SIZE = 16; // 16x16 tile
    private final int SCALE = 3;
    private int score = 0;
    private final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE; // Size of the original tiles scaled by 3, 48x48
    private final int MAX_SCREEN_COLUMN = 10;
    private final int MAX_SCREEN_ROW = 14;
    private final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COLUMN; // 480 pixels
    private final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW; // 672 pixels

    Birb birb = new Birb();
    private boolean gameStarted = false;

    //Obstacles
    private List<Obstacle> obstacles;
    private final int OBSTACLE_WIDTH = 100;
    private int obstacleHeight = 210;
    private int spaceBetweenObstacles = 130;
    private int obstacleX = SCREEN_WIDTH;

    //Images
    private Image backgroundImage;
    private Image groundImage;
    private Image bottomObstacle;
    private Image topObstacle;

    Thread gameThread;

    public void startGame() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    KeyControls keyControls = new KeyControls();

    public GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyControls);
        this.setFocusable(true);
        this.obstacles = new ArrayList<>();
        loadImages();
        addObstacles(SCREEN_WIDTH);
    }

    private void loadImages() {
        backgroundImage = new ImageIcon("Images/Background_night.png").getImage();
        //groundImage = new ImageIcon("Images/ground_flowers.png").getImage();
        groundImage = new ImageIcon("Images/ground_flowers_night.png").getImage();
        bottomObstacle = new ImageIcon("Images/icecreamRedBottom.png").getImage();
        topObstacle = new ImageIcon("Images/twisterTop.png").getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(backgroundImage, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT + 50, null);
        drawPlayer(g);
        drawObstacle(g);
        drawGround(g);
        drawScore(g);

    }

    /**
     * @param g This method draws the birb.
     */
    private void drawPlayer(Graphics g) {
        //g.setColor(Color.ORANGE);
        //g.fillRect(birb.getBIRB_X(), birb.getBirbY(), birb.getPLAYER_WIDTH(), birb.getPLAYER_HEIGHT());
        g.drawImage(birb.getSprite3(), birb.getBIRB_X(), birb.getBirbY(),
                birb.getPLAYER_WIDTH(), birb.getPLAYER_HEIGHT(), this);
    }

    /**
     * @param g draws the current score
     */
    private void drawScore(Graphics g) {
        g.setColor(Color.GREEN);
        g.setFont(new Font("Serif", Font.BOLD, 50));
        g.drawString("" + score, 220, 50);
    }

    private int scrollPosition = 0;

    /**
     * @param g This method loops the ground tiles.
     */
    private void drawGround(Graphics g) {
        int visibleTiles = (SCREEN_WIDTH / TILE_SIZE) + 2;
        int startTile = scrollPosition / TILE_SIZE;
        int groundY = 624;

        for (int i = 0; i < startTile + visibleTiles; i++) {
            int x = (i * TILE_SIZE) - (scrollPosition % TILE_SIZE);
            g.drawImage(groundImage, x, groundY, TILE_SIZE, TILE_SIZE, null);
        }
    }

    private void addObstacles(int x) {
        int topObstY = 0;
        int randBottomHeight = ThreadLocalRandom.current().nextInt(SCREEN_HEIGHT / 4, (SCREEN_HEIGHT / 4) * 3);
        int bottomObstY = SCREEN_HEIGHT - randBottomHeight;
        int randTopHeight = bottomObstY - spaceBetweenObstacles;

        obstacles.add(new Obstacle(topObstacle, x, topObstY, randTopHeight));
        obstacles.add(new Obstacle(bottomObstacle, x, bottomObstY, randBottomHeight));
    }

    /**
     * @param g This method loops the obstacle objects.
     */
    private void drawObstacle(Graphics g) {
        if (!gameStarted) {
            return;
        }
        for (Obstacle obstacle : obstacles) {
            g.drawImage(obstacle.img, obstacle.obstacleX, obstacle.obstacleY,
                    obstacle.obstacleWidth, obstacle.obstacleHeight, null);
        }
    }

    public void updateObstacles() {
        Iterator<Obstacle> iterator = obstacles.iterator();

        while (iterator.hasNext()) {
            Obstacle obstacle = iterator.next();

            // Here we control the point system connnected to the obstacles
            Rectangle obstacleHitbox = new Rectangle(obstacle.obstacleX, obstacle.obstacleY,
                    obstacle.obstacleWidth, obstacle.obstacleHeight);
            if (obstacleHitbox.intersects(birb.getBirbHitbox())) {
                score++;
            }

            obstacle.obstacleX -= SCROLL_SPEED;
            if (obstacle.obstacleX + obstacle.obstacleWidth <= 0) {
                removeObjects(iterator);
                addObstacles(SCREEN_WIDTH);
                return;// Exit the loop after removing obstacles
            }

            //Give obstacles closer distance.
//            if(obstacle.obstacleX == SCREEN_WIDTH/2){
//                addObstacles(SCREEN_WIDTH);
//                if (obstacle.obstacleX + obstacle.obstacleWidth <= 0) {
//                    iterator.remove(); // Remove the current obstacle
//                    if (iterator.hasNext()) {
//                        iterator.next(); // Move to the next obstacle (bottom obstacle)
//                        iterator.remove(); // Remove the bottom obstacle
//                    }
//                    return;// Exit the loop after removing obstacles
//                }
//            }
        }
    }

    private static void removeObjects(Iterator<Obstacle> iterator) {
        iterator.remove(); // Remove the current obstacle
        if (iterator.hasNext()) {
            iterator.next(); // Move to the next obstacle (bottom obstacle)
            iterator.remove(); // Remove the bottom obstacle
        }
    }

    /**
     * update() is to be put in the run() method.
     * This methods contains the controls to the birb.
     */
    public void update() {

        if (!gameStarted) {
            if (keyControls.getSpacebar()) {
                gameStarted = true;
            }
            return;
        }

        if (keyControls.getSpacebar()) {
            birb.setBirbY(birb.getBirbY() - birb.getBIRB_SPEED());

        } else {

            birb.setBirbY(birb.getBirbY() + (birb.getBIRB_SPEED() / 2));
        }

        birb.updateHitbox();
    }

    // Frames Per Second
    private final int FPS = 60;
    private final int SCROLL_SPEED = 2;

    @Override
    public void run() {

        // Tell the system when to draw the screen again.
        double drawInterval = (double) 1000000000 / FPS; // 0.016666 seconds
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null) {
            updateObstacles();
            update();
            scrollPosition = scrollPosition + SCROLL_SPEED;
            SwingUtilities.invokeLater(this::repaint);

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