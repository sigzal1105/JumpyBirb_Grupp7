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
    private String printName;
    private String filePath;
    private List<String> writeScore = new ArrayList<>();

    public String getFilePath() {
        return filePath;
    }

    public String getHighScore() {
        return highScore;
    }

    public void setHighScore(String highScore) {
        this.highScore = highScore;
    }

    public String getPrintName() {
        return printName;
    }

    public void setPrintName(String printName) {
        this.printName = printName;
    }

    public List<String> getWriteScore() {
        return writeScore;
    }

    public void setWriteScore(List<String> writeScore) {
        this.writeScore = writeScore;
    }

    /**
     * Saves the score to the appropriate high score file based on the menu number
     * and input controls.
     *
     * @param menuNum     The menu number indicating the difficulty level
     * @param keyControls The KeyControls instance to determine input controls
     * @param scoreEntry  The ScoreEntry to be saved
     */
    public void saveScore(int menuNum, KeyControls keyControls, ScoreEntry scoreEntry) {

        filePath = determineFilePath(menuNum, keyControls);
        writeScoreToFile(scoreEntry, filePath);
        // loadScoresFromFile(filePath);
    }

    /**
     * Determines the file path based on the menu number and input controls.
     *
     * @param menuNum     The menu number indicating the difficulty level
     * @param keyControls The KeyControls instance to determine input controls
     * @return The file path corresponding to the specified menu number and input
     *         controls
     */
    private String determineFilePath(int menuNum, KeyControls keyControls) {

        if ((menuNum == 0 || menuNum == 1 || menuNum == 2)) {
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
    public void loadScore(String filePath) {
        try (BufferedReader reader = Files.newBufferedReader(Path.of(filePath))) {
            String line;
            List<ScoreEntry> usersList = new ArrayList<>();

            while ((line = reader.readLine()) != null) {
                // Split each line into username and score
                String[] points = line.split(" ");
                // Create a ScoreEntry object from the data and add it to the list
                ScoreEntry listEntry = new ScoreEntry(points[0], Integer.parseInt(points[1]));
                usersList.add(listEntry);
            }
            // Sort the list based on scores
            Collections.sort(usersList);
            // Add the top 5 scores to the writeScore list
            for (ScoreEntry scoreEntry2 : usersList) {
                if (writeScore.size() >= 5) {
                    break;
                }

                writeScore.add(scoreEntry2.toString());
            }
        } catch (IOException e) {
            // Handle IOException
            System.err.println("Error loading scores from file: " + e.getMessage());
        }
    }

}