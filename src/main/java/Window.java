import javax.swing.*;
import java.awt.*;

public class Window {

    JFrame window = new JFrame();
    
    public Window() {
        
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Jumpy Birb");

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        window.pack();

        window.setLocationRelativeTo(null);
        gamePanel.requestFocusInWindow();
        window.setVisible(true);
        gamePanel.startGame();

    }

    public void ExitGame() {
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
