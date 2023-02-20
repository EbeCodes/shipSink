import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class highScores {
    private static ArrayList<highScore> scores = new ArrayList<>();
    private static String FILENAME;

    public highScores() {
        FILENAME = "scores.txt";
        readScoresFromFile();
    }

    public static ArrayList<highScore> getAllScores() {
        Collections.sort(scores);
        return scores;
    }

    public void addScore(highScore h) {
        if (scores.size() < 5) {
            scores.add(h);
        } else {
            int minScoreIndex = 0;
            for (int i = 1; i < 5; i++) {
                if (scores.get(i).getScore() < scores.get(minScoreIndex).getScore()) {
                    minScoreIndex = i;
                }
            }
            }
        }

    public static int getLowestScorePosition() {
        if (scores.size() == 0) {
            return Integer.MAX_VALUE;
        }
        int minScore = scores.get(0).getScore();
        int minPosition=0;
        for (int i = 1; i < scores.size(); i++) {
            if (scores.get(i).getScore() < minScore) {
                minPosition = i;
            }
        }
        return minPosition;
    }

    public static int getLowestScore() {
        if (scores.size() == 0) {
            return Integer.MAX_VALUE;
        }
        int minScore = scores.get(0).getScore();
        for (int i = 1; i < scores.size(); i++) {
            if (scores.get(i).getScore() < minScore) {
                minScore = scores.get(i).getScore();
            }
        }
        return minScore;
    }

    public static void addHighScore(highScore h) {
        int i = getLowestScorePosition();
        scores.set(i, h);
        try {
            FileOutputStream file = new FileOutputStream(FILENAME);
            OutputStreamWriter output = new OutputStreamWriter(file, Charset.forName("UTF8"));
            for(highScore score : scores) {
                output.write(score.getName() + "," + Integer.toString(score.getScore()) + "\n");
            }
            output.close();
        } catch (IOException e) {
            System.err.println("Error: Failed to write file "+ FILENAME);
        }
    }

    private void readScoresFromFile() {
        try {
            File file = new File(FILENAME);
            Scanner scanner = new Scanner(file, "UTF-8");
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                String name = parts[0];
                int score = Integer.parseInt(parts[1]);
                highScore s = new highScore(name, score);
                addScore(s);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("Error: " +FILENAME+" file not found or failed to read it.");
        }
    }
}