import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class KeyControls implements KeyListener, MouseListener {
    private boolean spacebar;
    private boolean up;
    private boolean down;
    private boolean left;
    private boolean right;
    private boolean mouseClick;

    public boolean getSpacebar() {
        return spacebar;
    }

    public boolean Up() {
        return up;
    }

    public boolean Down() {
        return down;
    }

    public boolean Left() {
        return left;
    }

    public boolean Right() {
        return right;
    }

    public boolean getMouseClick() {
        return mouseClick;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_SPACE) {
            spacebar = true;
        }

        if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) {
            up = true;
        }

        if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT) {
            left = true;
        }

        if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN) {
            down = true;
        }

        if (keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT) {
            right = true;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_SPACE) {
            spacebar = false;
        }

        if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) {
            up = false;
        }

        if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT) {
            left = false;
        }

        if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN) {
            down = false;
        }

        if (keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT) {
            right = false;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            mouseClick = false;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            mouseClick = true;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
