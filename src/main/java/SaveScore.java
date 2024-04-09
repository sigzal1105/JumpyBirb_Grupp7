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
    private String filePath;

    /**
     * Saves the score to the appropriate high score file based on the menu number and input controls.
     *
     * @param menuNum     The menu number indicating the difficulty level
     * @param keyControls The KeyControls instance to determine input controls
     * @param scoreEntry  The ScoreEntry to be saved
     */
    public void saveAndLoadScore(int menuNum, KeyControls keyControls, ScoreEntry scoreEntry) {

        writeScore.clear();
        filePath = determineFilePath(menuNum, keyControls);
        writeScoreToFile(scoreEntry, filePath);
        loadScoresFromFile(filePath);
    }

    /**
     * Determines the file path based on the menu number and input controls.
     *
     * @param menuNum     The menu number indicating the difficulty level
     * @param keyControls The KeyControls instance to determine input controls
     * @return The file path corresponding to the specified menu number and input controls
     */
    private String determineFilePath(int menuNum, KeyControls keyControls) {
        String filePath = "";
        if ((menuNum == 0 || menuNum == 1 || menuNum == 2) && (keyControls.getMouseClick() || keyControls.getSpacebar())) {
            switch (menuNum) {
                case 0:
                    filePath = "Highscores/EasyHighScore.txt";
                    break;
                case 1:
                    filePath = "Highscores/NormalHighScore.txt";
                    break;
                case 2:
                    filePath = "Highscores/DeadlyHighScore.txt";
                    break;
                default:
                    break;
            }
        }
        return filePath;
    }

    /**
     * Writes the score entry to the specified file path.
     *
     * @param scoreEntry The score entry to be written
     * @param filePath   The file path where the score entry will be written
     */
    private void writeScoreToFile(ScoreEntry scoreEntry, String filePath) {
        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(filePath), StandardOpenOption.APPEND)) {
            writer.write(scoreEntry.toString());
        } catch (IOException e) {
            System.err.println("Something went wrong in: " + filePath + e.getMessage());
        }
    }

    /**
     * Loads scores from the specified file path.
     *
     * @param filePath The file path from which scores will be loaded
     */
    private void loadScoresFromFile(String filePath) {
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