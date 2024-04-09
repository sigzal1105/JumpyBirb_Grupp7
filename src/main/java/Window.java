import javax.swing.*;
import java.awt.*;

public class Window {

    JFrame window = new JFrame();
    private static GamePanel gamePanel;

    /**
     * Constructs the game window.
     */
    public Window() {

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Jumpy Birb");

        gamePanel = new GamePanel();
        window.add(gamePanel);
        window.pack();

        window.setLocationRelativeTo(null);
        gamePanel.requestFocusInWindow();
        window.setVisible(true);

        gamePanel.startGame();
    }

    /**
     * Retrieves the game panel instance.
     *
     * @return The game panel instance
     */
    public static GamePanel getGamePanel(){
        return gamePanel;
    }

    /**
     * Exits the game by disposing the window and exiting the program.
     */
    public void exitGame() {
        window.dispose();
        System.exit(0);
    }
}
