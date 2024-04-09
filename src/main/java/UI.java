import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class UI implements ActionListener {

    private final SaveScore highscore = new SaveScore();

    String difficulty;
    String easy = "Easy";
    String normal = "Normal";
    String deadly = "Deadly";
    String quit = "Quit";

    // Start menu
    private final int START_MENU_X = 80;
    private final int START_MENU_Y = 338;
    private final int START_MENU_WIDTH = 480 - 160; // 320
    private final int START_MENU_HEIGHT = 672 - (START_MENU_X + START_MENU_Y); // 254

    // Menu
    private final int MENU_X = 80;
    private final int MENU_Y = 80;
    private final int MENU_WIDTH = 480 - 160; // 320
    private final int MENU_HEIGHT = 672 - 160; // 512

    // Menu controls
    private int menuNumbers; // for the menu buttons

    // Panel
    private JPanel inputPanel = new JPanel();
    private JTextArea nameInputField = new JTextArea();
    private JButton submitNameButton = new JButton("Enter");

    // Name input
    private String username;

    /**
     * Constructs a new UI object.
     * This constructor initializes the UI components:
     * including a panel for input,
     * a label for displaying a new score message,
     * a label prompting the user to enter their name,
     * a text field for entering the name,
     * and a button for submitting the name.
     * It also sets up event listeners for the name input field and the submit
     * button.
     */
    public UI() {

        inputPanel.setLayout(new GridLayout(0, 1));
        inputPanel.setBackground(Color.BLACK);

        JLabel text = new JLabel("     New score!");
        text.setForeground(Color.GREEN);
        JLabel labelEnterName = new JLabel("     Enter name:");
        labelEnterName.setForeground(Color.GREEN);

        inputPanel.add(text);
        inputPanel.add(labelEnterName);
        inputPanel.add(nameInputField);
        inputPanel.add(submitNameButton);

        nameInputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    submitName();
                }
            }
        });

        submitNameButton.addActionListener(this);
    }

    public String getUsername() {
        return username;
    }

    public JPanel getInputPanel() {
        return inputPanel;
    }

    public void setInputPanel(JPanel inputPanel) {
        this.inputPanel = inputPanel;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getMenuNumbers() {
        return menuNumbers;
    }

    public void setMenuNumbers(int menuNumbers) {
        this.menuNumbers = menuNumbers;
    }

    public int getMENU_X() {
        return MENU_X;
    }

    public int getMENU_Y() {
        return MENU_Y;
    }

    public int getMENU_WIDTH() {
        return MENU_WIDTH;
    }

    public int getMENU_HEIGHT() {
        return MENU_HEIGHT;
    }

    /**
     * This method is responsible for rendering the start menu, including the option
     * to choose a level,
     * when the game is initiated.
     *
     * @param g         The Graphics object used for rendering.
     * @param gameStart A boolean indicating whether the game has started or not.
     * @param gamePanel The GamePanel object associated with the game.
     */
    public void drawStartMenu(Graphics g, boolean gameStart, GamePanel gamePanel, int SCREEN_WIDTH, int SCREEN_HEIGHT) {
        if (!gameStart) {
            menuWindow(START_MENU_X, START_MENU_Y, START_MENU_WIDTH, START_MENU_HEIGHT, g, SCREEN_WIDTH, SCREEN_HEIGHT,
                    false);
            gamePanel.setBorder(BorderFactory.createLineBorder(Color.black));

            String serif = "Serif";

            int choose_levelY = START_MENU_Y + (g.getFont().getSize()) + 35;
            String choose_level = "CHOOSE LEVEL";
            g.setFont(new Font(serif, Font.PLAIN, 30));
            g.setColor(Color.red);
            g.drawString(choose_level, getXtextCenter(choose_level, g, SCREEN_WIDTH), choose_levelY);
        }
    }

    /**
     * Draw the current panelScore and game over screen if the game is over.
     *
     * @param g          The Graphics object used for rendering.
     * @param panelScore The current score to be displayed.
     * @param gameOver   A boolean indicating whether the game is over or not.
     * @param gamePanel  The GamePanel object associated with the game.
     * @param idk        An instance of SaveScore (not clear what it represents).
     */
    public void drawScore(Graphics g, int panelScore, boolean gameOver, GamePanel gamePanel, int SCREEN_WIDTH,
            int SCREEN_HEIGHT, SaveScore idk) {
        String panelScoreString = Integer.toString(panelScore);
        String printName = highscore.getHighScore(); // Här har jag ändrat från ulf!/TF
        String serif = "Serif";

        if (gameOver) {

            menuWindow(MENU_X, MENU_Y, MENU_WIDTH, MENU_HEIGHT, g, SCREEN_WIDTH, SCREEN_HEIGHT, true);
            gamePanel.setBorder(BorderFactory.createLineBorder(Color.black));

            int you_diedY = 60;
            int currentScoreY = 130;
            int highscoreY = currentScoreY + 55;
            int difficultyY = highscoreY + 25;

            String you_died = "YOU DIED";
            g.setFont(new Font(serif, Font.PLAIN, 50));
            g.setColor(Color.red);
            g.drawString(you_died, getXtextCenter(you_died, g, SCREEN_WIDTH), you_diedY);

            String current_score = "Current score";
            String highScore_text = "Highscore";
            g.setFont(new Font(serif, Font.PLAIN, 25));
            g.setColor(Color.green);
            g.drawString(current_score, getXtextCenter(current_score, g, SCREEN_WIDTH), currentScoreY);
            g.drawString(highScore_text, getXtextCenter(highScore_text, g, SCREEN_WIDTH), highscoreY);

            // Difficulty
            g.setFont(new Font(serif, Font.PLAIN, 23));
            g.setColor(Color.cyan);
            g.drawString(difficulty, getXtextCenter(difficulty, g, SCREEN_WIDTH), difficultyY);

            g.setFont(new Font(serif, Font.PLAIN, 19));
            g.setColor(Color.green);
            String name_score = "name:";
            String score_text = "score:";
            g.drawString(score_text, getXnameCenter(score_text, g, false, SCREEN_WIDTH), highscoreY + 50);
            g.drawString(name_score, getXnameCenter(name_score, g, true, SCREEN_WIDTH), highscoreY + 50);

            // SCORES
            g.setFont(new Font(serif, Font.BOLD, 18));
            g.setColor(Color.yellow);
            g.drawString(panelScoreString, getXtextCenter(panelScoreString, g, SCREEN_WIDTH), currentScoreY + 27); // Current
            // Score

            for (int i = 85; i <= 225; i = i + 35) {

                g.drawString(highscore.getHighScore(), getXnameCenter(highscore.getHighScore(), g, false, SCREEN_WIDTH),
                        highscoreY + i);
                g.drawString(printName, getXnameCenter(printName, g, true, SCREEN_WIDTH), highscoreY + i);
            }

        } else {

            g.setColor(Color.green.darker());
            g.setFont(new Font(serif, Font.BOLD, 50));
            g.drawString(panelScoreString, getXtextCenter(panelScoreString, g, SCREEN_WIDTH), 50);
        }
    }

    /**
     * Calculates the x-coordinate for centering text horizontally on the screen.
     *
     * @param text The text whose center position needs to be calculated.
     * @return The x-coordinate for centering the text.
     */
    private int getXtextCenter(String text, Graphics g, int SCREEN_WIDTH) {
        int length = (int) g.getFontMetrics().getStringBounds(text, g).getWidth();
        return SCREEN_WIDTH / 2 - length / 2;
    }

    /**
     * Calculates the x-coordinate for centering text horizontally on the screen,
     * either towards the left or right side.
     *
     * @param text     The text whose center position needs to be calculated.
     * @param username A boolean indicating whether the text is for a username
     *                 (left) or not (right).
     * @return The x-coordinate for centering the text.
     */
    private int getXnameCenter(String text, Graphics g, boolean username, int SCREEN_WIDTH) {
        if (username) {// left text
            int length = (int) g.getFontMetrics().getStringBounds(text, g).getWidth();
            return SCREEN_WIDTH / 3 - length / 2;
        } else { // right text
            int length = (int) g.getFontMetrics().getStringBounds(text, g).getWidth();
            return (SCREEN_WIDTH / 3) * 2 - length / 2;
        }
    }

    /**
     * Renders a menu window on the screen.
     *
     * @param gameOver A boolean indicating whether the game is over or not.
     */
    public void menuWindow(int menuX, int menuY, int menuWidth, int menuHeight, Graphics g, int SCREEN_WIDTH,
            int SCREEN_HEIGHT, boolean gameOver) {
        int roundedCorner = 35;

        // Transparent rectangle
        int whiteValue = 255;
        int alpha = 127;// 50% transparency
        Color transparentColor = new Color(whiteValue, whiteValue, whiteValue, alpha);
        g.setColor(transparentColor);
        g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

        // Black border
        g.setColor(Color.black);
        g.fillRoundRect(menuX - 2, menuY - 2, menuWidth + 4, menuHeight + 4, roundedCorner + 2, roundedCorner + 2);

        // White border
        g.setColor(Color.white);
        g.fillRoundRect(menuX, menuY, menuWidth, menuHeight, roundedCorner, roundedCorner);

        // Main menu
        g.setColor(Color.black);
        g.fillRoundRect(menuX + 5, menuY + 5, menuWidth - 10, menuHeight - 10, roundedCorner - 10, roundedCorner - 10);

        if (gameOver) {
            menuSelectionColor(g, menuHeight, menuWidth, menuX, menuY);
        } else {
            menuHeight = MENU_HEIGHT;
            menuY = MENU_Y;
            menuSelectionColor(g, menuHeight, menuWidth, menuX, menuY);
        }
    }

    /**
     * Adjusts button colors based on the current menu selection.
     */
    public void menuSelectionColor(Graphics g, int menuHeight, int menuWidth, int menuX, int menuY) {

        int buttonHeight = menuHeight / 10; // 51
        int buttonWidth = menuWidth - 200; // 120
        int leftButtonX = menuX + 30; // 110
        int rightButtonX = leftButtonX + buttonWidth + 20; // 250
        int topButtonY = menuY + 360; // 440
        int bottomButtonY = menuY + 420; // 500

        makeButton(easy, leftButtonX, topButtonY, buttonWidth, buttonHeight, g, Color.lightGray);
        makeButton(deadly, leftButtonX, bottomButtonY, buttonWidth, buttonHeight, g, Color.lightGray);
        makeButton(normal, rightButtonX, topButtonY, buttonWidth, buttonHeight, g, Color.lightGray);
        makeButton(quit, rightButtonX, bottomButtonY, buttonWidth, buttonHeight, g, Color.lightGray);

        switch (menuNumbers) {
            case 0:
                makeButton(easy, leftButtonX, topButtonY, buttonWidth, buttonHeight, g, Color.cyan);
                break;
            case 1:
                makeButton(normal, rightButtonX, topButtonY, buttonWidth, buttonHeight, g, Color.cyan);
                break;
            case 2:
                makeButton(deadly, leftButtonX, bottomButtonY, buttonWidth, buttonHeight, g, Color.cyan);
                break;
            case 3:
                makeButton(quit, rightButtonX, bottomButtonY, buttonWidth, buttonHeight, g, Color.cyan);
                break;
            default:
                System.err.println("Invalid menu button " + menuNumbers);
                break;
        }
    }

    /**
     * Creates a button with the specified level, position, dimensions, and color.
     *
     * @param level The text displayed on the button.
     * @param color The color of the button.
     */
    private void makeButton(String level, int buttonX, int buttonY, int buttonWidth, int buttonHeight, Graphics g,
            Color color) {
        int roundedCorner = 35;

        g.setColor(color);
        g.fillRoundRect(buttonX, buttonY, buttonWidth, buttonHeight, roundedCorner, roundedCorner);

        g.setFont(new Font("Courier", Font.BOLD, 23));
        g.setColor(Color.black);

        // Get text height and width to find center
        int height = (int) g.getFontMetrics().getStringBounds(level, g).getHeight();
        int length = (int) g.getFontMetrics().getStringBounds(level, g).getWidth();
        int buttonCenterX = (buttonX + buttonWidth / 2) - length / 2;
        int buttonYcenter = (buttonY + buttonHeight / 2) + height / 3;

        // Button texts
        if (level.equals(easy)) {
            difficulty = easy;
            g.drawString(difficulty, buttonCenterX, buttonYcenter);
        } else if (level.equals(normal)) {
            difficulty = normal;
            g.drawString(difficulty, buttonCenterX, buttonYcenter);
        } else if (level.equals(deadly)) {
            difficulty = deadly;
            g.drawString(difficulty, buttonCenterX, buttonYcenter);
        } else if (level.equals(quit)) {
            difficulty = quit;
            g.drawString(difficulty, buttonCenterX, buttonYcenter);
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Controls menu navigation based on the current menu selection.
     *
     * @param keyControls The KeyControls object used for menu navigation.
     */
    public void menuControls(KeyControls keyControls) {
        switch (menuNumbers) {

            case 0 -> handleEasyButton(keyControls);
            case 1 -> handleNormalButton(keyControls);
            case 2 -> handleDeadlyButton(keyControls);
            case 3 -> handleQuitButton(keyControls);
            default -> System.err.println("ERROR: Invalid menu button " + menuNumbers);
        }
    }

    /**
     * Handles button actions for the quit button.
     *
     * @param keyControls The KeyControls object used for button actions.
     */
    private void handleQuitButton(KeyControls keyControls) {
        if (keyControls.Up()) {
            menuNumbers = 1;
        } else if (keyControls.Left()) {
            menuNumbers = 2;

        } else if (keyControls.getSpacebar()) {
            StartGame.getWindow().exitGame();
        }
    }

    /**
     * Handles button actions for the deadly button.
     */
    private void handleDeadlyButton(KeyControls keyControls) {
        if (keyControls.Up()) {
            menuNumbers = 0;
        } else if (keyControls.Right()) {
            menuNumbers = 3;
        } else if (keyControls.getSpacebar()) {
            Window.getGamePanel().restartGame(4, 170);
        }
    }

    /**
     * Handles button actions for the normal button.
     */
    private void handleNormalButton(KeyControls keyControls) {
        if (keyControls.Left()) {
            menuNumbers = 0;
        } else if (keyControls.Down()) {
            menuNumbers = 3;
        } else if (keyControls.getSpacebar()) {
            Window.getGamePanel().restartGame(3, 185);
        }
    }

    /**
     * Handles button actions for the easy button.
     */
    public void handleEasyButton(KeyControls keyControls) {
        if (keyControls.Down()) {
            menuNumbers = 2;
        } else if (keyControls.Right()) {
            menuNumbers = 1;
        } else if (keyControls.getSpacebar()) {
            Window.getGamePanel().restartGame(2, 200);
        }
    }

    private void submitName() {

        username = nameInputField.getText().trim();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == submitNameButton) {

            submitName();
        }
    }

}
