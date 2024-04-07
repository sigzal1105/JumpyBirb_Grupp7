import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;

public class SaveScore {
    private String highScore = "0";
    private String filePath;

    public String getHighScore() {
        return highScore;
    }

    public void saveAndLoadScore(int panelScore, String userName) {
        UI menyNum = new UI();

        if (menyNum.getMenuNumbers() == 0) {
            filePath = "Highscores/EasyHighScore.txt";
        } else if (menyNum.getMenuNumbers() == 1) {
            filePath = "Hiscores/NormalHighScore.txt";
        } else if (menyNum.getMenuNumbers() == 2) {
            filePath = "Highscores/DeadlyHighScore.txt";
        }

        try (BufferedReader reader = Files.newBufferedReader(Path.of(filePath));) {
            String line;

            while ((line = reader.readLine()) != null) {

               String[] points= line.split(" ");

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
}
