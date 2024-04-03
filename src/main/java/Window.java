import javax.swing.*;
import java.awt.*;

public class Window {

    JFrame window = new JFrame();
    private static GamePanel gamePanel;
    
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

    public static GamePanel getGamePanel(){
        return gamePanel;
    }

    public void exitGame() {
        window.dispose();
        System.exit(0);
    }
}
