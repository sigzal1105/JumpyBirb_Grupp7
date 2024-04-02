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
        UI menyNum = new UI();

        //Easy:
        if (menyNum.getMenuNumbers() == 0) {
            try (BufferedReader reader = Files.newBufferedReader(Path.of("EasyHighScore.txt"));) {
                String line;
                while ((line = reader.readLine()) != null) {

                    if (Integer.parseInt(line) < panelScore) {

                        highScore = Integer.toString(panelScore);
                        try (BufferedWriter writer = Files.newBufferedWriter(Path.of("EasyHighScore.txt"))) {
                            writer.write(Integer.toString(panelScore));
                        } catch (IOException e) {
                            System.err.println("Something went wrong in Easy." + e.getMessage());
                        }
                    } else {
                        highScore = line;
                    }
                }
            } catch (IOException e) {
                System.err.println("Something went wrong" + e.getMessage());
            }
        }

        //Normal:
        if (menyNum.getMenuNumbers() == 1) {
            try (BufferedReader reader = Files.newBufferedReader(Path.of("NormalHighScore.txt"));) {
                String line;
                while ((line = reader.readLine()) != null) {

                    if (Integer.parseInt(line) < panelScore) {

                        highScore = Integer.toString(panelScore);
                        try (BufferedWriter writer = Files.newBufferedWriter(Path.of("NormalHighScore.txt"))) {
                            writer.write(Integer.toString(panelScore));
                        } catch (IOException e) {
                            System.err.println("Something went wrong in Normal" + e.getMessage());
                        }
                    } else {
                        highScore = line;
                    }
                }
            } catch (IOException e) {
                System.err.println("Something went wrong" + e.getMessage());
            }
        }
        //Deadly:
        if (menyNum.getMenuNumbers() == 2) {
            try (BufferedReader reader = Files.newBufferedReader(Path.of("DeadlyHighScore.txt"));) {
                String line;
                while ((line = reader.readLine()) != null) {

                    if (Integer.parseInt(line) < panelScore) {

                        highScore = Integer.toString(panelScore);
                        try (BufferedWriter writer = Files.newBufferedWriter(Path.of("DeadlyHighScore.txt"))) {
                            writer.write(Integer.toString(panelScore));
                        } catch (IOException e) {
                            System.err.println("Something went wrong in Deadly" + e.getMessage());
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
