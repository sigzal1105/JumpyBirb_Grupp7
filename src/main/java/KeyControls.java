import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class KeyControls implements KeyListener, MouseListener {
    private boolean spacebar;
    private boolean enter;
    private boolean up;
    private boolean down;
    private boolean left;
    private boolean right;
    private boolean mouseClick;

    public boolean getSpacebar() {
        return spacebar;
    }

    public boolean getEnter() {
        return enter;
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

    /**
     * Handles key presses and sets corresponding boolean flags based on the pressed
     * keys.
     *
     * @param e The KeyEvent object representing the key press event
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_SPACE) {
            spacebar = true;
        }

        if (keyCode == KeyEvent.VK_ENTER) {
            enter = true;
        }

        if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) {
            up = true;
            down = false;
            right = false;
            left = false;
        }

        if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT) {
            up = false;
            left = true;
            down = false;
            right = false;
        }

        if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN) {
            up = false;
            left = false;
            down = true;
            right = false;
        }

        if (keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT) {
            up = false;
            left = false;
            down = false;
            right = true;
        }

    }

    /**
     * Handles key releases and resets corresponding boolean flags based on the
     * released keys.
     *
     * @param e The KeyEvent object representing the key release event
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_SPACE) {
            spacebar = false;
        }

        if (keyCode == KeyEvent.VK_ENTER) {
            enter = false;
        }
    }

    /**
     * Handles mouse releases and resets the mouseClick flag when the left mouse
     * button is released.
     *
     * @param e The MouseEvent object representing the mouse release event
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            mouseClick = false;
        }
    }

    /**
     * Handles mouse presses and sets the mouseClick flag to true when the left
     * mouse button is pressed.
     *
     * @param e The MouseEvent object representing the mouse press event
     */
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
