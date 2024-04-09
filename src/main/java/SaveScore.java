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
    List<ScoreEntry> writeScore = new ArrayList<>();
    private String filePath = "Highscores/EasyHighScore.txt";

    public void saveAndLoadScore(int menyNum, KeyControls keyControls, ScoreEntry scoreEntry) {

        writeScore.clear();

        if (menyNum == 0 && keyControls.getMouseClick() || keyControls.getSpacebar()) {

            filePath = "Highscores/EasyHighScore.txt";
        } else if (menyNum == 1 && keyControls.getMouseClick() || keyControls.getSpacebar()) {

            filePath = "Highscores/NormalHighScore.txt";
        } else if (menyNum == 2 && keyControls.getMouseClick() || keyControls.getSpacebar()) {

            filePath = "Highscores/DeadlyHighScore.txt";
        }

        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(filePath), StandardOpenOption.APPEND)) {

            writer.write(scoreEntry.toString());

        } catch (IOException e) {
            System.err.println("Something went wrong in: " + filePath + e.getMessage());
        }

        try (BufferedReader reader = Files.newBufferedReader(Path.of(filePath))) {
            String line;

            List<ScoreEntry> usersList = new ArrayList<>();

            while ((line = reader.readLine()) != null) {
                String[] points = line.split(" ");
                ScoreEntry listEntry = new ScoreEntry(points[0], Integer.parseInt(points[1]));
                usersList.add(listEntry);

            }

            Collections.sort(usersList);

            for (ScoreEntry scoreEntry2 : usersList) {
                if (usersList.size() > 5) {

                    break;
                }

                System.out.println(scoreEntry2);
                writeScore.add(scoreEntry2);
            }

        } catch (IOException e) {

            System.err.println("Something went wrong" + e.getMessage());
        }
    }

    public List<ScoreEntry> getWriteScore() {
        return writeScore;
    }

    public void setWriteScore(List<ScoreEntry> writeScore) {
        this.writeScore = writeScore;
    }

    public String getHighScore() {
        return highScore;
    }
}