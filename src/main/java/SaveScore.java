import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;

public class SaveScore {
    private String highScore = "0";

    public String getHighScore() {
        return highScore;
    }

    public void saveAndLoadScore(int panelScore) {
        try (BufferedReader reader = Files.newBufferedReader(Path.of("AllTimeHigh.txt"));) {
            String line;
            while ((line = reader.readLine()) != null) {

                if (Integer.parseInt(line) < panelScore) {

                    highScore = Integer.toString(panelScore);
                    try (BufferedWriter writer = Files.newBufferedWriter(Path.of("AllTimeHigh.txt"))) {
                        writer.write(Integer.toString(panelScore));
                    } catch (IOException e) {
                        System.err.println("Something went wrong" + e.getMessage());
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
