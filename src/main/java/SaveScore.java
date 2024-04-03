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

    public void saveAndLoadScore(int panelScore) {
        UI menyNum = new UI();
        UI userName = new UI();

        if (menyNum.getMenuNumbers() == 0) {
            filePath = "Highscores/EasyHighScore.txt";
        } else if (menyNum.getMenuNumbers() == 1) {
            filePath = "Hiscores/NormalHighScore.txt";
        } else if (menyNum.getMenuNumbers() == 2) {
            filePath = "Highscores/DeadlyHighScore";
        }

        // Easy:
        if (menyNum.getMenuNumbers() == 0) {
            try (BufferedReader reader = Files.newBufferedReader(Path.of(filePath));) {
                String line;
                while ((line = reader.readLine()) != null) {

                    if (Integer.parseInt(line) < panelScore) {

                        highScore = Integer.toString(panelScore);
                        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(filePath))) {
                            writer.write(Integer.toString(panelScore) + userName.getUsername());
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
}
