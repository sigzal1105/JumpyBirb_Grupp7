import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

//Får se om vi ens behöver denna klassen eller om jag bara tänkt i cirklar...

public class GetHighscore {
    private String filePathDefault = "Highscores/EasyHighScore.txt";
    private List<String> elementsInHighScore = new ArrayList<>();

    public List<String> elementHighscore(String filePath) {
        String line;
        try (
                BufferedReader reader = Files.newBufferedReader(Path.of(filePath));) {
            while ((line = reader.readLine()) != null) {
                String[] points = line.split(" ");

                for (String string : points) {
                    //har inte helt tänkt klart här...

                }
            }

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return elementsInHighScore;

    }
}
/*
 * här vill vi ha en getter för att få ut highscore namn, alltså alla jämna
 * nummer i listan.
 * 
 * Vi vill också ha en getter för alla för alla highscorepoäng, alla ojämna
 * nummer i listan.
 * 
 * Förslagsvis så skriver man ut dom på samma sätt i UI-klassen rad 88.
 * 
 * Det ska finnas en konstruktor för där man skickar med vilken highscore man
 * vill se.
 */
