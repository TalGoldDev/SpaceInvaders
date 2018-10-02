package animation;

import biuoop.DrawSurface;
import game.HighScoresTable;

public class HighScoresAnimation implements Animation {

    //the variables.
    private HighScoresTable scoresBoard;


    /**
     * the constructor method.
     * @param scores - the highscore table.
     */
    public HighScoresAnimation(HighScoresTable scores) {
        this.scoresBoard = scores;
    }

    /**
     * a function that draws a frame to the screen.
     * @param d - the drawing surface.
     * @param dt - the time passed since last frame.
     */
    public void doOneFrame(DrawSurface d, double dt) {
        d.drawText(125, 100, "High Scores", 100);
        int y = 200;
        for (int i = 0; i < this.scoresBoard.size(); i++) {
            d.drawText(100, y, (i + 1) + ". " + this.scoresBoard.getHighScores().get(i).getName(), 25);
            d.drawText(650, y, String.valueOf(this.scoresBoard.getHighScores().get(i).getScore()), 25);
            y += 35;
        }
    }

    /**
     * a function that checks if this screen should stop.
     * @return - a boolean.
     */
    public boolean shouldStop() {
        return false;
    }
}