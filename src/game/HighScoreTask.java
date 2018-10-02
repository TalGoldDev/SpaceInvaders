package game;

import animation.AnimationRunner;
import animation.HighScoresAnimation;
import animation.KeyPressStoppableAnimation;
import biuoop.GUI;
import biuoop.KeyboardSensor;

import java.io.File;

public class HighScoreTask implements Task<Void> {

    //the variables
    private AnimationRunner animationR;
    private GUI gui;
    private File highScoresTableFile;

    /**
     * the Constructor Method.
     * @param r - the animation Runner.
     * @param g - the GUI.
     * @param f - the File.
     */
    public HighScoreTask(AnimationRunner r, GUI g, File f) {
        this.highScoresTableFile = f;
        this.gui = g;
        this.animationR = r;
    }

    /**
     * Method that runs the HighScores animation.
     * @return null.
     */
    public Void run() {
        HighScoresTable loadedTable = HighScoresTable.
                loadFromFile(highScoresTableFile);
        animationR.run(new KeyPressStoppableAnimation(gui.getKeyboardSensor(),
                KeyboardSensor.SPACE_KEY,
                new HighScoresAnimation(loadedTable)));
        return null;
    }
}