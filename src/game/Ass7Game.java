package game;

import animation.AnimationRunner;
import animation.Menu;
import animation.MenuAnimation;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import levels.LevelInformation;
import levels.LevelSets;
import levels.LevelSpecificationReader;
import levels.SetLevel;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class Ass7Game {

    /**
     * the main function of the game.
     * @param args - takes command line input.
     */
    public static void main(String[] args) {


       //creating\loading the highscores table.
        String scoresFile = "highscores.txt";
        final File highScoresTableFile = new File(scoresFile);

        HighScoresTable highScoresTable = null;

        try {
            if (!highScoresTableFile.exists()) {
                highScoresTableFile.createNewFile();
                highScoresTable = new HighScoresTable(5);
                highScoresTable.save(highScoresTableFile);
            } else {
                highScoresTable = HighScoresTable.loadFromFile(highScoresTableFile);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }



        //initiallizing the game.
        List<LevelInformation> levels = new LinkedList<>();
        GUI gui = new GUI("SpaceInvaders", 800, 600);
        AnimationRunner animationRunner = new AnimationRunner(gui, 60);

        KeyboardSensor keyboard = gui.getKeyboardSensor();
        GameFlow game = new GameFlow(animationRunner, keyboard, gui, levels, new Counter(3),
                new Counter(0), highScoresTable);


        // anonymous classes.
        Menu<Task<Void>> menu = new
                MenuAnimation<Task<Void>>("Main Menu", keyboard, animationRunner);

        Menu<Task<Void>> subMenu = new MenuAnimation<Task<Void>>("Choose Difficulty", keyboard, animationRunner);

        Task<Void> quit = new Task<Void>() {
            @Override
            public Void run() {
                System.exit(1);
                return null;
            }
        };


        LevelSets levelSets = null;
        if (args.length > 0 && args != null) {
            BufferedReader argsReader = new
                    BufferedReader(new InputStreamReader(
                            ClassLoader.getSystemClassLoader().getResourceAsStream(args[0])));
            levelSets = LevelSets.fromReader(argsReader);

        } else {
            BufferedReader reader = new BufferedReader(new
                    InputStreamReader(ClassLoader.getSystemClassLoader().getResourceAsStream("level_sets.txt")));


            levelSets = LevelSets.fromReader(reader);
        }

        PlayTask invadersLevel = null;
        for (SetLevel level : levelSets.getSetLevelList()) {
            BufferedReader reader2 = new BufferedReader(new InputStreamReader(
                    ClassLoader.getSystemClassLoader().getResourceAsStream(level.getLevelPath())));

            List<LevelInformation> info = LevelSpecificationReader.fromReader(new BufferedReader(reader2));
            //subMenu.addSelection(level.getKey(), level.getTitle(), new PlayTask(game, info));

            invadersLevel = new PlayTask(game, info);
        }

        //.addSelection(level.getKey(), level.getTitle(), new PlayTask(game, info));
       // menu.addSubMenu("s", "Start Game", subMenu);

        menu.addSelection("s", "Start Game", invadersLevel);
        menu.addSelection("h", "high scores",
                new HighScoreTask(animationRunner, gui, highScoresTableFile));
        menu.addSelection("q", "quit", quit);



        // the game loop.
        while (true) {
            animationRunner.run(menu);
            Task<Void> task = menu.getStatus();
            task.run();
        }


    }
}
