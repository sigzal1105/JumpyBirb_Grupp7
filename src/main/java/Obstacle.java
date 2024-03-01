import java.awt.*;

public class Obstacle {

    private int obstacleX;
    private int obstacleY;
    private final int OBSTACLE_WIDTH = 100;
    private int obstacleHeight;
    Image img;


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

    public Image getImg() {
        return img;
    }

}
