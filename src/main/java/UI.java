import javax.swing.*;
import java.awt.*;

public class UI {

    private SaveScore highscore = new SaveScore();

    String difficulty;
    String easy = "Easy";
    String normal = "Normal";
    String deadly = "Deadly";
    String quit = "Quit";

    //Menu
    private final int MENU_X = 80;
    private final int MENU_Y = 80;
    private final int MENU_WIDTH = 480 - 160;
    private final int MENU_HEIGHT = 672 - 160;

    //Menu controls
    private int menuNumbers; //for the menu buttons

    public int getMenuNumbers() {
        return menuNumbers;
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
     * @param g draws the current panelScore
     */
    public void drawScore(Graphics g, int panelScore, boolean gameOver, GamePanel gamePanel, int SCREEN_WIDTH, int SCREEN_HEIGHT) {
        String panelScoreString = Integer.toString(panelScore);
        String username = "ULF";

        if (gameOver) {
            menuWindow(MENU_X, MENU_Y, MENU_WIDTH, MENU_HEIGHT, g, SCREEN_WIDTH, SCREEN_HEIGHT);
            gamePanel.setBorder(BorderFactory.createLineBorder(Color.black));

            int you_diedY = 60;
            int currentScoreY = 130;
            int highscoreY = currentScoreY + 55;
            int difficultyY = highscoreY + 25;

            String you_died = "YOU DIED";
            g.setFont(new Font("Serif", Font.PLAIN, 50));
            g.setColor(Color.red);
            g.drawString(you_died, getxtextCenter(you_died, g, SCREEN_WIDTH), you_diedY);

            String current_score = "Current score";
            String highScore_text = "Highscore";
            g.setFont(new Font("Serif", Font.PLAIN, 25));
            g.setColor(Color.green);
            g.drawString(current_score, getxtextCenter(current_score, g, SCREEN_WIDTH), currentScoreY);
            g.drawString(highScore_text, getxtextCenter(highScore_text, g, SCREEN_WIDTH), highscoreY);

            //Difficulty
            g.setFont(new Font("Serif", Font.PLAIN, 23));
            g.setColor(Color.cyan);
            g.drawString(difficulty, getxtextCenter(difficulty, g, SCREEN_WIDTH), difficultyY);

            g.setFont(new Font("Serif", Font.PLAIN, 19));
            g.setColor(Color.green);
            String name_score = "name:";
            String score_text = "score:";
            g.drawString(score_text, getxNameCenter(score_text, g, false, SCREEN_WIDTH), highscoreY + 50);
            g.drawString(name_score, getxNameCenter(name_score, g, true, SCREEN_WIDTH), highscoreY + 50);

            // SCORES
            g.setFont(new Font("Serif", Font.BOLD, 18));
            g.setColor(Color.yellow);
            g.drawString(panelScoreString, getxtextCenter(panelScoreString, g, SCREEN_WIDTH), currentScoreY + 27); //Current Score

            for (int i = 85; i <= 225; i = i + 35) {
                g.drawString(highscore.getHighScore(), getxNameCenter(highscore.getHighScore(), g, false, SCREEN_WIDTH), highscoreY + i);
                g.drawString(username, getxNameCenter(username, g, true, SCREEN_WIDTH), highscoreY + i);
            }

        } else {

            g.setColor(Color.green.darker());
            g.setFont(new Font("Serif", Font.BOLD, 50));
            g.drawString(panelScoreString, getxtextCenter(panelScoreString, g, SCREEN_WIDTH), 50);
        }
    }

    private int getxtextCenter(String text, Graphics g, int SCREEN_WIDTH) {
        int length = (int) g.getFontMetrics().getStringBounds(text, g).getWidth();
        return SCREEN_WIDTH / 2 - length / 2;
    }

    private int getxNameCenter(String text, Graphics g, boolean username, int SCREEN_WIDTH) {
        if (username) {
            int length = (int) g.getFontMetrics().getStringBounds(text, g).getWidth();
            return SCREEN_WIDTH / 3 - length / 2;
        } else {
            int length = (int) g.getFontMetrics().getStringBounds(text, g).getWidth();
            return (SCREEN_WIDTH / 3) * 2 - length / 2;
        }
    }

    //MENU
    private void menuWindow(int menuX, int menuY, int menuWidth, int menuHeight, Graphics g, int SCREEN_WIDTH, int SCREEN_HEIGHT) {
        int roundedCorner = 35;

        //Transparent rectangle
        int whiteValue = 255;
        int alpha = 127;//50% transparency
        Color transparentColor = new Color(whiteValue, whiteValue, whiteValue, alpha);
        g.setColor(transparentColor);
        g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

        //Black border
        g.setColor(Color.black);
        g.fillRoundRect(menuX - 2, menuY - 2, menuWidth + 4, menuHeight + 4, roundedCorner + 2, roundedCorner + 2);

        //White border
        g.setColor(Color.white);
        g.fillRoundRect(menuX, menuY, menuWidth, menuHeight, roundedCorner, roundedCorner);

        //Main menu
        g.setColor(Color.black);
        g.fillRoundRect(menuX + 5, menuY + 5, menuWidth - 10, menuHeight - 10, roundedCorner - 10, roundedCorner - 10);


        menuSelectionColor(g, menuHeight, menuWidth, menuX, menuY);
    }

    private void makeButton(String level, int buttonX, int buttonY, int buttonWidth, int buttonHeight, Graphics g, Color color) {
        int roundedCorner = 35;

        g.setColor(color);
        g.fillRoundRect(buttonX, buttonY, buttonWidth, buttonHeight, roundedCorner, roundedCorner);

        g.setFont(new Font("Courier", Font.BOLD, 23));
        g.setColor(Color.black);

        if(level.equals(easy)){
            difficulty = easy;
            g.drawString(difficulty, buttonX + 32, buttonY + 32);
        } else if( level.equals(normal)){
            difficulty = normal;
            g.drawString(difficulty, buttonX + 32, buttonY + 32);
        } else if(level.equals(deadly)){
            difficulty = deadly;
            g.drawString(difficulty, buttonX + 32, buttonY + 32);
        } else if(level.equals(quit)){
            difficulty = quit;
            g.drawString(difficulty, buttonX + 32, buttonY + 32);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void menuSelectionColor(Graphics g, int menuHeight, int menuWidth, int menuX, int menuY) {

        int buttonHeight = menuHeight / 10;
        int buttonWidth = menuWidth - 200;
        int leftButtonX = menuX + 30;
        int rightButtonX = leftButtonX + buttonWidth + 20;
        int topButtonY = menuY + 360;
        int bottomButtonY = menuY + 420;

        makeButton(easy, leftButtonX, topButtonY, buttonWidth, buttonHeight, g, Color.lightGray);
        makeButton(deadly,leftButtonX, bottomButtonY, buttonWidth, buttonHeight, g, Color.lightGray);
        makeButton(normal ,rightButtonX, topButtonY, buttonWidth, buttonHeight, g, Color.lightGray);
        makeButton(quit,rightButtonX, bottomButtonY, buttonWidth, buttonHeight, g, Color.lightGray);

        switch (menuNumbers) {
            case 0:

                makeButton(easy,  leftButtonX, topButtonY, buttonWidth, buttonHeight, g, Color.cyan);
                break;
            case 1:

                makeButton(deadly, rightButtonX, topButtonY, buttonWidth, buttonHeight, g, Color.cyan);
                break;
            case 2:

                makeButton(normal ,leftButtonX, bottomButtonY, buttonWidth, buttonHeight, g, Color.cyan);
                break;
            case 3:

                makeButton(quit, rightButtonX, bottomButtonY, buttonWidth, buttonHeight, g, Color.cyan);
                break;
        }
    }

    public void menuControls(KeyControls keyControls) {
        switch (menuNumbers) {
            case 0 -> {
                if (keyControls.Down()) {
                    menuNumbers = 2;
                } else if (keyControls.Right()) {
                    menuNumbers = 1;
                }
            }
            case 1 -> {
                if (keyControls.Left()) {
                    menuNumbers = 0;
                } else if (keyControls.Down()) {
                    menuNumbers = 3;
                }
            }
            case 2 -> {
                if (keyControls.Up()) {
                    menuNumbers = 0;
                } else if (keyControls.Right()) {
                    menuNumbers = 3;
                }
            }
            case 3 -> {
                if (keyControls.Up()) {
                    menuNumbers = 1;
                } else if (keyControls.Left()) {
                    menuNumbers = 2;
                }
            }
        }
    }
}
