import javax.swing.*;
import java.awt.*;

public class Window {

    public Window() {
        JFrame window = new JFrame();

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
}
