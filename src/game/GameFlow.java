package game;

import animation.HighScoresAnimation;
import animation.KeyPressStoppableAnimation;
import biuoop.DialogManager;
import levels.LevelInformation;
import animation.AnimationRunner;
import animation.EndScreen;
import biuoop.GUI;
import biuoop.KeyboardSensor;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class GameFlow {

    //the variables.
    private KeyboardSensor keyboardSensor;
    private AnimationRunner animationRunner;
    private GUI gui;
    private List levels;
    private Counter livesCounter;
    private Counter scoreCounter;
    private boolean playerResult;
    private HighScoresTable table;

    /**
     * the gameflow constructor.
     * @param ar - the animation runner.
     * @param ks - the keyboard sensor.
     * @param gui - the game gui.
     * @param levelsList - the list of levels.
     * @param lives - the game lives counter.
     * @param score - the game score counter.
     * @param scoresTable - the highscoretable.
     */
    public GameFlow(AnimationRunner ar, KeyboardSensor ks, GUI gui, List<LevelInformation> levelsList,
                    Counter lives, Counter score, HighScoresTable scoresTable) {

        keyboardSensor = ks;
        animationRunner = ar;
        this.gui = gui;
        this.levels = levelsList;
        livesCounter = lives;
        scoreCounter = score;
        playerResult = true;
        this.table = scoresTable;

    }

    /**
     * the function runs the levels in a for loop until either the player loses or there are no longer more levels.
     * @param levelsList - the levels list.
     */
    public void runLevels(List<LevelInformation> levelsList) {
        // the loop runs through the levels and initializes each one in order.
        for (LevelInformation levelInfo : levelsList) {

            GameLevel level = new GameLevel(levelInfo, this.keyboardSensor,
                    this.animationRunner, this.gui, this.livesCounter, this.scoreCounter);

            level.initialize();
            //level has more blocks and player has more lives
            while (level.livesLeft() != 0 && level.blocksLeft() != 0) {
                level.playOneTurn();
            }

            if (level.livesLeft() == 0) {
                playerResult = false;

            }
        }

        // checking if a new high score was achieved.
        File highScoresTableFile = new File("highscores.txt");
        int rank = this.table.getRank(this.scoreCounter.getValue());
        if (rank <= this.table.getSize()) {
            DialogManager dialog = gui.getDialogManager();
            String name = dialog.showQuestionDialog("Name", "What is your name?", "default-player");
            this.table.add(new ScoreInfo(name, this.scoreCounter.getValue()));
            try {
                this.table.save(highScoresTableFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        this.animationRunner.run(new KeyPressStoppableAnimation(this.keyboardSensor,
                KeyboardSensor.SPACE_KEY, new EndScreen(scoreCounter, playerResult)));
        this.animationRunner.run(new KeyPressStoppableAnimation(this.keyboardSensor, KeyboardSensor.SPACE_KEY,
                new HighScoresAnimation(table)));

        //resetting the lives and score counters
        livesCounter = new Counter(3);
        scoreCounter = new Counter(0);
    }
}
