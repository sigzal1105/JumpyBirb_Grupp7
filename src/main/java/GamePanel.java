import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class GamePanel extends JPanel implements Runnable {
    private static final int ORIGINAL_TILE_SIZE = 16; // 16x16 tile
    private static final int SCALE = 3;
    private static final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE; // Size of the original tiles scaled by 3, 48x48
    private static final int MAX_SCREEN_COLUMN = 10;
    private static final int MAX_SCREEN_ROW = 14;
    private static final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COLUMN; // 480 pixels
    private static final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW; // 672 pixels

    // Game start & Game over
    private boolean gameStart = false;
    private boolean gameOver = false;
    private boolean enterNameState = false;
    private boolean idk = false;

    ScoreEntry scoreEntry;

    // score
    private int panelScore = 0;
    private final transient SaveScore highscore = new SaveScore();

    // Birb
    private final transient Birb birb = new Birb();
    private final transient KeyControls keyControls = new KeyControls();

    // UI
    private transient UI userInterface = new UI();

    // Obstacles
    private final transient List<Obstacle> obstacles;
    private final transient List<Obstacle> obstacles2;

    private int spaceBetweenObstacles = 200; // 200 = easy. 170 = normal and hard.
    private int pointZoneY;
    private int pointZoneY2;

    // Scroll
    private int scrollSpeed = 2; // 2 = easy. 3 = normal. 5 = hard.
    private int scrollPosition = 0;

    // Images
    private transient Image backgroundImage = new ImageIcon("Images/BackgroundStart2.png").getImage();
    private transient Image groundImage = new ImageIcon("Images/ground_flowers.png").getImage();
    private transient Image bottomObstacle = new ImageIcon("Images/Obsticle_start_bottom.png").getImage();
    private transient Image topObstacle = new ImageIcon("Images/Obsticle_start_top_kiwi.png").getImage();

    // SoundPlayer
    private final transient SoundPlayer SOUND_PLAYER = new SoundPlayer();

    private transient Thread gameThread;

    public void startGame() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    private void mouseMenu(int x, int y) {

        // EASY
        if (x >= 110 && x <= 230 && y >= 440 && y <= 491) {

            userInterface.setMenuNumbers(0);
            Window.getGamePanel().restartGame(2, 200);
        }

        // NORMAL
        else if (x >= 250 && x <= 370 && y >= 440 && y <= 491) {

            userInterface.setMenuNumbers(1);
            Window.getGamePanel().restartGame(3, 185);
        }

        // DEADLY
        else if (x >= 110 && x <= 230 && y >= 500 && y <= 551) {

            userInterface.setMenuNumbers(2);
            Window.getGamePanel().restartGame(4, 170);
        }

        // QUIT
        else if (x >= 250 && x <= 370 && y >= 500 && y <= 551) {

            userInterface.setMenuNumbers(3);
            StartGame.getWindow().exitGame();
        }
    }

    public GamePanel() {
        this.setLayout(null);
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setDoubleBuffered(true);
        this.addKeyListener(keyControls);
        this.addMouseListener(keyControls);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                int x = e.getX();
                int y = e.getY();

                if (gameOver) {

                    mouseMenu(x, y);
                }

                else if (!gameStart) {

                    mouseMenu(x, y);
                }
            }
        });
        this.setFocusable(true);
        this.obstacles = new ArrayList<>();
        this.obstacles2 = new ArrayList<>();
        // Adds to list 1.
        addObstacles(SCREEN_WIDTH + 350, obstacles, pointZoneY);
        // Adds to list 2.
        addObstacles(SCREEN_WIDTH, obstacles2, pointZoneY2);

        this.add(userInterface.getInputPanel());
        userInterface.getInputPanel().setBounds(196, 311, 100, 100);
        userInterface.getInputPanel().setVisible(false);
    }

    public void restartGame(int selectedScrollSpeed, int selectedSpaceBetweenObstacles) {
        gameStart = true;
        gameOver = false;
        enterNameState = false;

        scrollSpeed = selectedScrollSpeed;
        spaceBetweenObstacles = selectedSpaceBetweenObstacles;

        // reset score
        panelScore = 0;

        // reset scroll position
        scrollPosition = 0;

        // reset images
        backgroundImage = new ImageIcon("Images/BackgroundStart2.png").getImage();
        groundImage = new ImageIcon("Images/ground_flowers.png").getImage();
        bottomObstacle = new ImageIcon("Images/Obsticle_start_bottom.png").getImage();
        topObstacle = new ImageIcon("Images/Obsticle_start_top_kiwi.png").getImage();

        // reset birb and hitbox position
        birb.reset();

        // Clear obstacles lists and add new obstacles
        obstacles.clear();
        obstacles2.clear();
        addObstacles(SCREEN_WIDTH + 350, obstacles, pointZoneY);
        addObstacles(SCREEN_WIDTH, obstacles2, pointZoneY2);

        userInterface.setUsername(null);
        this.add(userInterface.getInputPanel());
        userInterface.getInputPanel().setBounds(196, 311, 100, 100);
        userInterface.getInputPanel().setVisible(false);

        // Restart the game thread
        if (gameThread != null && !gameThread.isAlive()) {
            gameThread = new Thread(this);
            gameThread.start();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(backgroundImage, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT + 50, null);
        userInterface.drawStartMenu(g, gameStart, this, SCREEN_WIDTH, SCREEN_HEIGHT);
        Obstacle.drawObstacle(g, obstacles, gameStart);
        Obstacle.drawObstacle(g, obstacles2, gameStart);
        drawGround(g);
        birb.drawBirb(g, keyControls, gameOver);
        userInterface.drawScore(g, panelScore, gameOver, this, SCREEN_WIDTH, SCREEN_HEIGHT, highscore);
        userInterface.menuSelectionColor(g, userInterface.getMENU_X(), userInterface.getMENU_Y(),
                userInterface.getMENU_WIDTH(), userInterface.getMENU_HEIGHT());
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
        int randTopHeight = bottomObstY - spaceBetweenObstacles;

        if (pointZone == pointZoneY) {
            pointZoneY = (topObstY + bottomObstY) - spaceBetweenObstacles;
        } else if (pointZone == pointZoneY2) {
            pointZoneY2 = (topObstY + bottomObstY) - spaceBetweenObstacles;
        }

        obstacles.add(new Obstacle(topObstacle, x, topObstY, randTopHeight));
        obstacles.add(new Obstacle(bottomObstacle, x, bottomObstY, randBottomHeight));
    }

    private void ifPassPointZone(Obstacle obstacle, int pointZone) {
        Rectangle pointZoneHitbox = new Rectangle(obstacle.getObstacleX(), pointZone,
                obstacle.getOBSTACLE_WIDTH(), spaceBetweenObstacles);

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
            afterDeath();
        }

        // GAME OVER when birb hits edges of window
        if (birb.getHitboxY() + birb.getHITBOX_HEIGHT() >= SCREEN_HEIGHT - TILE_SIZE || birb.getHitboxY() <= 0) {
            afterDeath();
        }
    }

    private void afterDeath() {

        SOUND_PLAYER.playSound(SOUND_PLAYER.getClipDeath());
        SOUND_PLAYER.stopSound();
        birb.setDead(true);
        enterNameState = true;
        gameOver = true;
    }

    private void updateObstacles(List<Obstacle> obstacles, int pointZone) {
        if (!gameStart) {
            return;
        }

        Iterator<Obstacle> iterator = obstacles.iterator();

        while (iterator.hasNext()) {
            Obstacle obstacle = iterator.next();
            // This method runs if birb gets points.
            ifPassPointZone(obstacle, pointZone);

            // This method runs if birb dies.
            ifDie(obstacle);

            obstacle.setObstacleX(obstacle.getObstacleX() - scrollSpeed);

            // Remove object when it reaches the end of the screen
            if (obstacle.getObstacleX() + obstacle.getOBSTACLE_WIDTH() <= -obstacle.getOBSTACLE_WIDTH()) {
                removeObstacle(iterator);
                addObstacles(SCREEN_WIDTH, obstacles, pointZone);
                return;// Exit the loop removeObjects(iterator); after removing obstacles
            }
        }
    }

    private static void removeObstacle(Iterator<Obstacle> iterator) {
        iterator.remove(); // Remove the current obstacle
        if (iterator.hasNext()) {
            iterator.next(); // Move to the next obstacle (bottom obstacle)
            iterator.remove(); // Remove the bottom obstacle
        }
    }

    private void changeBackground() {
        if (panelScore > 3000 && panelScore < 5000) {
            backgroundImage = new ImageIcon("Images/Background_sunset.png").getImage();
            switch (spaceBetweenObstacles) {
                case 170 -> scrollSpeed = 5;
                case 185 -> scrollSpeed = 4;
                case 200 -> scrollSpeed = 3;
                default -> System.err.println("Illegitimate choice");
            }

        }
        if (panelScore >= 5000) {
            backgroundImage = new ImageIcon("Images/Background_night.png").getImage();
            groundImage = new ImageIcon("Images/ground_flowers_night.png").getImage();
            bottomObstacle = new ImageIcon("Images/Obsticle_bat_night.png").getImage();
            topObstacle = new ImageIcon("Images/Obsticle_night_top_bat.png").getImage();

            switch (spaceBetweenObstacles) {
                case 170 -> scrollSpeed = 6;
                case 185 -> scrollSpeed = 5;
                case 200 -> scrollSpeed = 4;
                default -> System.err.println("Illegitimate choice");
            }
        }
    }

    /**
     * update() is to be put in the run() method.
     * These methods contain the controls to the birb and menu.
     */
    void update() {

        if (!gameStart) {
            userInterface.menuControls(keyControls);
            return;
        }

        if (gameOver) {
            userInterface.menuControls(keyControls);
        } else {
            birb.birbControls(keyControls);
        }
    }

    @Override
    public void run() {

        // Tell the system when to draw the screen again.
        // Frames Per Second
        int fps = 60;
        double drawInterval = (double) 1000000000 / fps; // 0.016666 seconds
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null) {

            if (enterNameState) {

                userInterface.getInputPanel().setVisible(true);
                enterNameState = false;
                idk = true;
            }

            // GAME OVER
            else if (gameOver && !enterNameState) {

                if (userInterface.getUsername() != null) {
                    if (idk) {

                        scoreEntry = new ScoreEntry(userInterface.getUsername(), panelScore);
                        highscore.saveAndLoadScore(userInterface.getMenuNumbers(), keyControls, scoreEntry);
                        idk = false;
                        continue;
                    }
                    this.remove(userInterface.getInputPanel());
                    update();
                    SwingUtilities.invokeLater(this::repaint);
                }

            } else {
                update();
                // Update list 1.
                updateObstacles(obstacles, pointZoneY);
                // Update list 2.
                updateObstacles(obstacles2, pointZoneY2);
                scrollPosition = scrollPosition + scrollSpeed;
                SwingUtilities.invokeLater(this::repaint);
            }

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