import javax.swing.*;
import java.awt.*;

public class UI{

    private SaveScore highscore = new SaveScore();

    //Menu
    private final int menuX = 80;
    private final int menuY = 80;
    private final int menuWidth = 480-160;
    private final int menuHeight = 672-160;

    /**
     * @param g draws the current panelScore
     */
    public void drawScore(Graphics g, int panelScore, boolean gameOver, GamePanel gamePanel, int SCREEN_WIDTH, int SCREEN_HEIGHT) {
        String panelScoreString = Integer.toString(panelScore);
        String difficulty = "'easy'";
        String username = "ULF";

//        if(SCROLL_SPEED==2){
//            difficulty = "'easy'";
//        } else if(SCROLL_SPEED==3){
//            difficulty = "'medium'";
//        } else if (SCROLL_SPEED==5) {
//            difficulty = "'hard'";
//        }

        if (gameOver) {
            menuWindow(menuX, menuY, menuWidth, menuHeight, g, SCREEN_WIDTH, SCREEN_HEIGHT);
            gamePanel.setBorder(BorderFactory.createLineBorder(Color.black));

            int you_diedY = 60;
            int currentScoreY = 130;
            int highscoreY = currentScoreY+55;
            int difficultyY = highscoreY+25;

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
            g.drawString(score_text, getxNameCenter(name_score, g, false, SCREEN_WIDTH), highscoreY+50);
            g.drawString(name_score, getxNameCenter(score_text, g, true, SCREEN_WIDTH), highscoreY+50);

            // SCORES
            g.setFont(new Font("Serif", Font.BOLD, 18));
            g.setColor(Color.yellow);
            g.drawString(panelScoreString, getxtextCenter(panelScoreString, g, SCREEN_WIDTH), currentScoreY+27); //Current Score

            for(int i = 85; i <= 225; i = i +35){
                g.drawString(highscore.getHighScore(), getxNameCenter(highscore.getHighScore(), g, false, SCREEN_WIDTH), highscoreY+i);
                g.drawString(username, getxNameCenter(username, g, true, SCREEN_WIDTH), highscoreY+i);
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
        if(username){
            int length = (int) g.getFontMetrics().getStringBounds(text, g).getWidth();
            return SCREEN_WIDTH / 3 - length / 2;
        } else {
            int length = (int) g.getFontMetrics().getStringBounds(text, g).getWidth();
            return (SCREEN_WIDTH / 3) * 2 - length / 2;
        }
    }

    //MENU
    public void menuWindow(int menuX, int menuY, int menuWidth, int menuHeight, Graphics g, int SCREEN_WIDTH, int SCREEN_HEIGHT){
        int roundedCorner = 35;

        //Transparent rectangle
        int whiteValue = 255;
        int alpha = 127;//50% transparency
        Color transparentColor = new Color(whiteValue, whiteValue, whiteValue, alpha);
        g.setColor(transparentColor);
        g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

        //Black border
        g.setColor(Color.black);
        g.fillRoundRect(menuX-2, menuY-2, menuWidth+4, menuHeight+4, roundedCorner+2, roundedCorner+2);

        //White border
        g.setColor(Color.white);
        g.fillRoundRect(menuX, menuY, menuWidth, menuHeight, roundedCorner, roundedCorner);

        //Main menu
        g.setColor(Color.black);
        g.fillRoundRect(menuX+5, menuY+5, menuWidth-10, menuHeight-10, roundedCorner-10, roundedCorner-10);

        int buttonHeight = menuHeight/10;
        int buttonWidth = menuWidth-200;
        int leftButtonX = menuX + 30;
        int rightButtonX = leftButtonX + buttonWidth + 20;
        int topButtonY = menuY+360;
        int bottomButtonY = menuY+420;

        makeButton(leftButtonX, topButtonY, buttonWidth, buttonHeight, g);
        makeButton(leftButtonX, bottomButtonY, buttonWidth, buttonHeight, g);
        makeButton(rightButtonX, topButtonY, buttonWidth, buttonHeight, g);
        makeButton(rightButtonX, bottomButtonY, buttonWidth, buttonHeight, g);

    }

    public void makeButton(int buttonX, int buttonY, int buttonWidth, int buttonHeight, Graphics g){
        int roundedCorner = 35;
        g.setColor(Color.lightGray);
        g.fillRoundRect(buttonX, buttonY, buttonWidth, buttonHeight, roundedCorner, roundedCorner);
    }
}
