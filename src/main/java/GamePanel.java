import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class GamePanel extends JPanel implements Runnable {
    private final int ORIGINAL_TILE_SIZE = 16; // 16x16 tile
    private final int SCALE = 3;
    private final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE; // Size of the original tiles scaled by 3, 48x48
    private final int MAX_SCREEN_COLUMN = 10;
    private final int MAX_SCREEN_ROW = 14;
    private final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COLUMN; // 480 pixels
    private final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW; // 672 pixels

    // Game start & Game over
    private boolean gameStarted = false;
    private boolean gameOver = false;

    // score
    private int panelScore = 0;
    private SaveScore highscore = new SaveScore();

    public int getPanelScore() {
        return panelScore;
    }

    // Birb
    private Birb birb = new Birb();
    private KeyControls keyControls = new KeyControls();

    // Obstacles
    private List<Obstacle> obstacles;
    private List<Obstacle> obstacles2;
    private final int SPACE_BETWEEN_OBSTACLES = 170;
    private int pointZoneY;
    private int pointZoneY2;

    // Frames Per Second
    private final int FPS = 60;

    // Scroll
    private final int SCROLL_SPEED = 2;
    private int scrollPosition = 0;

    // Images
    private Image backgroundImage = new ImageIcon("Images/BackgroundStart2.png").getImage();
    private Image groundImage = new ImageIcon("Images/ground_flowers.png").getImage();
    private Image bottomObstacle = new ImageIcon("Images/Obsticle_start_bottom.png").getImage();
    private Image topObstacle = new ImageIcon("Images/Obsticle_start_top_kiwi.png").getImage();

    // Thread
    Thread gameThread;

    public void startGame() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setDoubleBuffered(true);
        this.addKeyListener(keyControls);
        this.addMouseListener(keyControls);
        this.setFocusable(true);
        this.obstacles = new ArrayList<>();
        this.obstacles2 = new ArrayList<>();
        //Adds to list 1.
        addObstacles(SCREEN_WIDTH + 350, obstacles, pointZoneY);
        //Adds to list 2.
        addObstacles(SCREEN_WIDTH, obstacles2, pointZoneY2);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(backgroundImage, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT + 50, null);
        Obstacle.drawObstacle(g, obstacles, gameStarted);
        Obstacle.drawObstacle(g, obstacles2, gameStarted);
        drawGround(g);
        birb.drawBirb(g, keyControls);
        drawScore(g);

    }


    /**
     * @param g draws the current panelScore
     */
    private void drawScore(Graphics g) {
        String panelScoreString = Integer.toString(panelScore);

        Color red = Color.RED;
        Color yellow = Color.YELLOW;
        Color green = Color.GREEN;

        if (gameOver) {

            setBorder(BorderFactory.createLineBorder(Color.black));
            g.setFont(new Font("Serif", Font.BOLD, 50));

            g.setColor(red);
            String you_died = "YOU DIED";
            g.drawString(you_died, getxtextCenter(you_died, g), 100);

            g.setColor(green);
            String current_score = "Current score";
            g.drawString(current_score, getxtextCenter(current_score, g), 250);
            String highScore_text = "Highscore";
            g.drawString(highScore_text, getxtextCenter(highScore_text, g), 400);

            // Highscore score

            g.setColor(yellow);
            g.drawString(panelScoreString, getxtextCenter(panelScoreString, g), 300);
            g.drawString(highscore.getHighScore(), getxtextCenter(panelScoreString, g), 450);

        } else {

            g.setColor(green);
            g.setFont(new Font("Serif", Font.BOLD, 50));
            g.drawString(panelScoreString, getxtextCenter(panelScoreString, g), 50);
        }
    }

    private int getxtextCenter(String text, Graphics g) {
        int length = (int) g.getFontMetrics().getStringBounds(text, g).getWidth();
        int x = SCREEN_WIDTH / 2 - length / 2;
        return x;
    }

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

    private void addObstacles(int x, List<Obstacle> obstacles, int pointZone) {
        int topObstY = 0;
        int randBottomHeight = ThreadLocalRandom.current().nextInt(SCREEN_HEIGHT / 4, (SCREEN_HEIGHT / 4) * 3);
        int bottomObstY = SCREEN_HEIGHT - randBottomHeight;
        int randTopHeight = bottomObstY - SPACE_BETWEEN_OBSTACLES;

        if (pointZone == pointZoneY) {
            pointZoneY = (topObstY + bottomObstY) - SPACE_BETWEEN_OBSTACLES;
        } else if (pointZone == pointZoneY2) {
            pointZoneY2 = (topObstY + bottomObstY) - SPACE_BETWEEN_OBSTACLES;
        }

        obstacles.add(new Obstacle(topObstacle, x, topObstY, randTopHeight));
        obstacles.add(new Obstacle(bottomObstacle, x, bottomObstY, randBottomHeight));
    }

    private void ifPassPointZone(Obstacle obstacle, int pointZone) {
        Rectangle pointZoneHitbox = new Rectangle(obstacle.getObstacleX(), pointZone,
                obstacle.getOBSTACLE_WIDTH(), SPACE_BETWEEN_OBSTACLES);


        if (pointZoneHitbox.intersects(birb.getBirbHitbox())) {

            panelScore++;
            changeBackground();
        }
    }

    private void ifDie(Obstacle obstacle) {
        Rectangle obstacleHitbox = new Rectangle(obstacle.getObstacleX(), obstacle.getObstacleY(),
                obstacle.getOBSTACLE_WIDTH(), obstacle.getObstacleHeight());

        // GAME OVER when birb hit obstacle
        if (obstacleHitbox.intersects(birb.getBirbHitbox())) {
            highscore.saveAndLoadScore(panelScore);
            gameOver = true;
            return;
        }
        
        // GAME OVER when birb hits edges of window
        if (birb.getHitboxY() + birb.getHITBOX_HEIGHT() >= SCREEN_HEIGHT - TILE_SIZE || birb.getHitboxY() <= 0) {
            highscore.saveAndLoadScore(panelScore);
            gameOver = true;
        }
    }

    private void updateObstacles(List<Obstacle> obstacles, int pointZone) {
        if (!gameStarted) {
            return;
        }

        Iterator<Obstacle> iterator = obstacles.iterator();

        while (iterator.hasNext()) {
            Obstacle obstacle = iterator.next();
            //This method runs if birb gets points.
            ifPassPointZone(obstacle, pointZone);

            //This method runs if birb dies.
            ifDie(obstacle);

            obstacle.setObstacleX(obstacle.getObstacleX() - SCROLL_SPEED);

            // Remove object when it reaches the end of the screen
            if (obstacle.getObstacleX() + obstacle.getOBSTACLE_WIDTH() <= -obstacle.getOBSTACLE_WIDTH()) {
                removeObjects(iterator);
                addObstacles(SCREEN_WIDTH, obstacles, pointZone);
                return;// Exit the loop removeObjects(iterator); after removing obstacles
            }
        }

    }

    private void changeBackground() {
        if (panelScore > 3000 && panelScore < 5000) {
            backgroundImage = new ImageIcon("Images/Background_sunset.png").getImage();
        }
        if (panelScore > 5000) {
            backgroundImage = new ImageIcon("Images/Background_night.png").getImage();
            groundImage = new ImageIcon("Images/ground_flowers_night.png").getImage();
            bottomObstacle = new ImageIcon("Images/Obsticle_bat_night.png").getImage();
            topObstacle = new ImageIcon("Images/Obsticle_bat_night_top.png").getImage();
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
     * These methods contain the controls to the birb.
     */
    private void update() {

        if (!gameStarted) {
            if (keyControls.getSpacebar() || keyControls.getMouseClick()) {
                gameStarted = true;
            }
            return;
        }

        birb.birbControls(keyControls);
    }

    @Override
    public void run() {

        // Tell the system when to draw the screen again.
        double drawInterval = (double) 1000000000 / FPS; // 0.016666 seconds
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null) {

            // GAME OVER
            if (gameOver) {
//                if (keyControls.getSpacebar()){
//                    gameOver = false;
//                    birb.setBirbY(SCREEN_HEIGHT/2);
//                    continue;
//                }
                return;
            }

            update();
            //Update list 1.
            updateObstacles(obstacles, pointZoneY); // This should be after update() if the objects should update
            //Update list 2.
            updateObstacles(obstacles2, pointZoneY2);
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