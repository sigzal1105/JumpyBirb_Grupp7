import java.awt.*;

public class Obstacle {

    int obstacleX;
    int obstacleY;
    final int obstacleWidth = 100;
    int obstacleHeight;
    Image img;
    boolean passed = false; //Keeps track of when flappy bird passes
    private Rectangle obstacleHitbox;

    public Obstacle(Image img, int obstacleX, int obstacleY, int obstacleHeight) {
        this.img = img;
        this.obstacleX = obstacleX;
        this.obstacleY = obstacleY;
        this.obstacleHeight = obstacleHeight;
        this.obstacleHitbox = new Rectangle(obstacleX, obstacleY, obstacleWidth, obstacleHeight);
    }

    public Rectangle getObstacleHitbox() {
        return obstacleHitbox;
    }
}
