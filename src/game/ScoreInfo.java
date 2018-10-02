package game;

import java.io.Serializable;

public class ScoreInfo implements Serializable {

    //variables.
    private String name;
    private int score;

    /**
     * the constructor method of scoreinfo.
     * @param playerName - the player name.
     * @param playerScore - current players score.
     */
    public ScoreInfo(String playerName, int playerScore) {
        this.name = playerName;
        this.score = playerScore;
    }


    /**
     * a function which returns the players name.
     * @return - a string.
     */
    public String getName() {
        return this.name;
    }


    /**
     * a function which returns the plyers score.
     * @return - an integer.
     */
    public int getScore() {
        return this.score;
    }
}