import java.util.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;

public class SaveScore {
    private String highScore = "0";
    private List<ScoreEntry> usersList = new ArrayList<>();
    private String filePath = "Highscores/EasyHighScore.txt";

    public void saveAndLoadScore(int panelScore, String userName, int menyNum, KeyControls keyControls,
            ScoreEntry scoreEntry) {

        if (menyNum == 0 && keyControls.getMouseClick() || keyControls.getSpacebar()) {
            filePath = "Highscores/EasyHighScore.txt";
        } else if (menyNum == 1 && keyControls.getMouseClick() || keyControls.getSpacebar()) {
            filePath = "Highscores/NormalHighScore.txt";
        } else if (menyNum == 2 && keyControls.getMouseClick() || keyControls.getSpacebar()) {
            filePath = "Highscores/DeadlyHighScore.txt";
        }

        try (BufferedReader reader = Files.newBufferedReader(Path.of(filePath))) {
            String line;
            int i = 0;

            while ((line = reader.readLine()) != null) {
                String[] points = line.split(" ");

                ScoreEntry listEntry = new ScoreEntry(points[0],points[1]);
                usersList.add(listEntry);
                
                
                // ScoreEntry scoreEntry = new ScoreEntry(userName, panelScore);
                
            }
        } catch (IOException e) {
            System.err.println("Something went wrong" + e.getMessage());
        }
        usersList.add(scoreEntry);
        Collections.sort(usersList);
        
        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(filePath))) {
    
            for (ScoreEntry se : usersList) {
    
                writer.write(se.toString());
            }
        } catch (IOException e) {
            System.err.println("Something went wrong in: " + filePath + e.getMessage());
        }
    }

    public List<ScoreEntry> getUsersList() {
        return usersList;
    }

    public String getHighScore() {
        return highScore;
    }
}

// writer.write(userName + " " + Integer.toString(panelScore));