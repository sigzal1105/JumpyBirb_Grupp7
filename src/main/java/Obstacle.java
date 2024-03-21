import java.awt.*;
import java.util.List;

public class Obstacle {

    private int obstacleX;
    private int obstacleY;
    private final int OBSTACLE_WIDTH = 100;
    private int obstacleHeight;
    private Image img;


    public Obstacle(Image img, int obstacleX, int obstacleY, int obstacleHeight) {
        this.img = img;
        this.obstacleX = obstacleX;
        this.obstacleY = obstacleY;
        this.obstacleHeight = obstacleHeight;
    }

    public int getObstacleX() {
        return obstacleX;
    }

    public void setObstacleX(int obstacleX) {
        this.obstacleX = obstacleX;
    }

    public int getObstacleY() {
        return obstacleY;
    }

    public int getOBSTACLE_WIDTH() {
        return OBSTACLE_WIDTH;
    }

    public int getObstacleHeight() {
        return obstacleHeight;
    }

    /**
     * @param g This method loops the obstacle objects.
     */
    public static void drawObstacle(Graphics g, List<Obstacle> obstacles, boolean gameStarted) {
        if (!gameStarted) {
            return;
        }
        for (Obstacle obstacle : obstacles) {

            //g.fillRect(obstacle.getObstacleX(), obstacle.getObstacleY(),
                    //obstacle.getOBSTACLE_WIDTH(), obstacle.getObstacleHeight()); // Draws Obstacle hitbox for testing.
            g.drawImage(obstacle.img, (int) obstacle.getObstacleX(), obstacle.getObstacleY(),
                    obstacle.getOBSTACLE_WIDTH(), obstacle.getObstacleHeight(), null);
        }
    }

}
