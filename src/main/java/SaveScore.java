import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;

public class SaveScore {
    // Score
    private int finalScore = 0;

    public void setFinalScore(int finalScore) {
        this.finalScore = finalScore;
    }

    public void saveAndLoadScore() {
        try (
                BufferedWriter writer = Files.newBufferedWriter(Path.of("AllTimeHigh"));) {
            writer.write(finalScore);
        } catch (IOException eWriter) {
            System.err.println("Could not find file: " + eWriter.getMessage());
        }

        try (
                BufferedReader reader = Files.newBufferedReader(Path.of("AllTimeHigh"));) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

        } catch (IOException eReader) {
            System.err.println("Could not find file: " + eReader.getMessage());
        }
    }
}
