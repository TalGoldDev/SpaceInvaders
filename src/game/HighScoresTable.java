package game;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class HighScoresTable {

   // the variables.
    private int size;
    private List<ScoreInfo> highScores;
    private int currentHeldScores;

    /**
     * the function returns the amount of scores currently held in the highscore table.
     * @return - an integer representing the amount of scores currently held in the high score table.
     */
    public int getCurrentHeldScores() {
        return currentHeldScores;
    }


    /**
     * Create an empty high-scores table with the specified size.
     * The size means that the table holds up to size top scores.
     * @param size - the size of the highscore table we want to create.
     */
    public HighScoresTable(int size) {
        this.size = size;
        this.highScores = new LinkedList<>();
        currentHeldScores = 0;
    }

    /**
     * the function adds a score to the highscore table in the right rank.
     * @param score - the score we are looking to add.
     */
    public void add(ScoreInfo score) {
        if (highScores.isEmpty()) {
            highScores.add(score);
            currentHeldScores++;
        } else {
            if (highScores.size() > currentHeldScores) {
                highScores.add(score);
                currentHeldScores++;
            }
        }

        if (currentHeldScores >= highScores.size()) {
            if (this.getRank(score.getScore()) <= currentHeldScores) {
                highScores.add(score);
            }
        }
        //sorting the highscore list.
        sortList();
    }


    /**
     * checking if the score should be added.
     * @param score - the score we are checking.
     * @return - true or false
     */
    public boolean shouldBeAdded(int score) {
        if (this.getRank(score) <= size) {
            return true;
        }
        return false;
    }

    /**
     *  Return table size.
     * @return - the current size of the highscore table.
     */
    public int size() {
        return this.highScores.size();
    }

    /**
     * Return the current high scores.
     * The list is sorted such that the highest
     * scores come first.
     * @return - a sorted high scores list.
     */
    public List<ScoreInfo> getHighScores() {
        sortList();
        return highScores;
    }

    /**
     * the function sorts the highscore lists by the score amount.
     * where the highest score at the top and the lowest at the bottom.
     * if there are more highscores than the size of the highscoretable it
     * removes the lowest highscores that didn't make the cut.
     */
    public void sortList() {
        Collections.sort(highScores, new Comparator<ScoreInfo>() {
            @Override
            public int compare(ScoreInfo o1, ScoreInfo o2) {
                if (o1.getScore() > o2.getScore()) {
                    return -1;
                }
                if (o1.getScore() < o2.getScore()) {
                    return 1;
                }
                return 0;
            }
        });

        if (this.size() > this.size) {
            highScores.remove(this.size() - 1);
        }
    }

    /**
     * return the rank of the current score: where will it be on the list if added?.
     * Rank 1 means the score will be highest on the list.
     * Rank `size` means the score will be lowest.
     * Rank > `size` means the score is too low and will not
     * be added to the list.
     * @param score - the score we want to get the rank of.
     * @return - the rank of the inputted score.
     */
    public int getRank(int score) {
        for (int i = 0; i < this.currentHeldScores; i++) {
            if (score >= this.highScores.get(i).getScore()) {
                return i + 1;
            }
        }
        if (this.currentHeldScores == 0) {
            return 1;
        } else {
            return this.size + 1;
        }
    }

    /**
     *  Clears the table.
     */
    public void clear() {
        this.highScores = new LinkedList<>();
        this.currentHeldScores = 0;
    }

    /**
     * Load table data from file, Current table data is cleared.
     * @param filename - the highscoretable file.
     * @throws IOException - if an error occurs.
     */
    public void load(File filename) throws IOException {
        //clearing the current table.
        this.clear();
        // the file's name.
        String file = filename.getName();

        ScoreInfo scoreInfo;
        ObjectInputStream objectInputStream = null;

        try {
            objectInputStream = new ObjectInputStream(new FileInputStream(file));
            this.size = (int) objectInputStream.readObject();
            this.currentHeldScores = (int) objectInputStream.readObject();
            for (int i = 0; i < this.size; i++) {
                scoreInfo = (ScoreInfo) objectInputStream.readObject();
                this.highScores.add(scoreInfo);
            }
        } catch (FileNotFoundException e) { // Can't find file to open
            System.out.println("Unable to find file: " + file);
        } catch (ClassNotFoundException e) { // The class in the stream is unknown to the JVM
            System.out.println("Unable to find class for object in file: " + file);
        } catch (IOException e) { // Some other problem
            System.out.println("Failed reading object");
            e.printStackTrace(System.out);
        } finally {
            try {
                if (objectInputStream != null) {
                    objectInputStream.close();
                }
            } catch (IOException e) {
                System.err.println("Failed closing file: " + file);
            }
        }
        this.currentHeldScores = this.highScores.size();
    }

    /**
     * Save table data to the specified file.
     * @param filename - the file we want to save the table to.
     * @throws IOException - i an error occurs.
     */
    public void save(File filename) throws IOException {
        String file = filename.getName();
        ObjectOutputStream objectOutputStream = null;

        try {
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
            objectOutputStream.writeObject(size);
            objectOutputStream.writeObject(currentHeldScores);
            for (ScoreInfo highScore : this.highScores) {
                objectOutputStream.writeObject(highScore);
            }
        } catch (IOException e) {
            System.out.println("Failed saving object");
            e.printStackTrace(System.err);
        } finally {
            try {
                if (objectOutputStream != null) {
                    objectOutputStream.close();
                }
            } catch (IOException e) {
                System.out.println("Failed closing file: " + file);
            }
        }

    }

    /**
     * the function returns the max size of the highscore table.
     * @return - the maximum amount of scores that can be saved.
     */
    public int getSize() {
        return size;
    }


    /**
     * Read a table from file and return it.
     * If the file does not exist, or there is a problem with
     * reading it, an empty table is returned.
     * @param filename - the file we want to load the table from.
     * @return - the loaded highscoretable.
     */
    public static HighScoresTable loadFromFile(File filename) {

        HighScoresTable hst = new HighScoresTable(5);
        try {
            hst.load(filename);
        } catch (IOException e) {
            hst.clear();
            e.printStackTrace();
        }

        return hst;
    }
}