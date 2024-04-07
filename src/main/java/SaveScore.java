import java.awt.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class SaveScore {
    private String highScore = "0";
    // private List<String> scoreAndName = new List();
    private String filePath = "Highscores/EasyHighScore.txt";

    public void saveAndLoadScore(int panelScore, String userName, int menyNum, KeyControls keyControls) {

        if (menyNum == 0 && keyControls.getMouseClick() || keyControls.getSpacebar()) {
            filePath = "Highscores/EasyHighScore.txt";
        } else if (menyNum == 1 && keyControls.getMouseClick() || keyControls.getSpacebar()) {
            filePath = "Highscores/NormalHighScore.txt";
        } else if (menyNum == 2 && keyControls.getMouseClick() || keyControls.getSpacebar()) {
            filePath = "Highscores/DeadlyHighScore.txt";
        }

        try (BufferedReader reader = Files.newBufferedReader(Path.of(filePath));) {
            String line;

            while ((line = reader.readLine()) != null) {

                String[] points = line.split(" ");

                if (Integer.parseInt(points[1]) < panelScore) {
                    highScore = Integer.toString(panelScore);
                    try (BufferedWriter writer = Files.newBufferedWriter(Path.of(filePath))) {
                        writer.write(userName + " " + Integer.toString(panelScore));
                    } catch (IOException e) {
                        System.err.println("Something went wrong in: " + filePath + e.getMessage());
                    }
                } else {
                    highScore = line;
                }

            }
        } catch (IOException e) {
            System.err.println("Something went wrong" + e.getMessage());
        }

    }

    public String getHighScore() {
        return highScore;
    }
}
