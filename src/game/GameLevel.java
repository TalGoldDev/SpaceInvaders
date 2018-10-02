package game;

import animation.Animation;
import animation.PauseScreen;
import animation.AnimationRunner;
import animation.CountdownAnimation;
import animation.KeyPressStoppableAnimation;
import gameobjects.GameEnvironment;
import gameobjects.Paddle;
import gameobjects.ScoreIndicator;
import gameobjects.SpriteCollection;
import gameobjects.LiveIndicator;
import gameobjects.Block;
import gameobjects.LevelNameIndicator;
import gameobjects.Ball;
import geometry.Velocity;
import invaders.Swarm;
import levels.LevelInformation;
import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import geometry.Point;
import interfaces.Collidable;
import interfaces.Sprite;
import listeners.BallRemover;
import listeners.BlockRemover;
import listeners.ScoreTrackingListener;

import java.awt.Color;
import java.util.List;

public class GameLevel implements Animation {

    private boolean aliensWon;
    //the variables.
    private LevelInformation gameLevel;
    private SpriteCollection sprites;
    private GameEnvironment environment;
    private GUI gui;
    private Paddle player;
    private Counter blockCounter, ballCounter, scoreCounter, liveCounter;
    private BlockRemover blockRemoverListener;
    private BallRemover ballRemoverListener;
    private ScoreIndicator scoreDisplay;
    private ScoreTrackingListener scoreListener;
    private LevelNameIndicator levelNameDisplay;
    private LiveIndicator livesDisplay;
    private AnimationRunner runner;
    private boolean running;
    private KeyboardSensor keyboard;
    private List<Block> blockList;
    private boolean passedLevel;
    private double shoot = 0;
    private Swarm motherShip;
    /**
     * creates a new gamelevel object.
     * @param levelInfo - the level we want to ply.
     * @param keyboardSensor -the keyboard sensor.
     * @param animationRunner - the animation runner.
     * @param gui - the gui.
     * @param livesCounter - the players lives counter.
     * @param scoreCounter - the players score counter.
     */
    public GameLevel(LevelInformation levelInfo, KeyboardSensor keyboardSensor, AnimationRunner animationRunner,
                     GUI  gui, Counter livesCounter, Counter scoreCounter) {
        this.blockCounter = new Counter(levelInfo.numberOfBlocksToRemove());
        this.scoreCounter = scoreCounter;
        this.keyboard = keyboardSensor;
        this.ballCounter = new Counter(levelInfo.numberOfBalls());
        this.liveCounter = livesCounter;
        this.running = false;
        this.runner = animationRunner;
        this.gameLevel = levelInfo;
        this.gui = gui;
        this.blockList = levelInfo.blocks();
        this.levelNameDisplay = new LevelNameIndicator("Space Invaders");
        this.aliensWon = false;
    }

    /**
     * returns the lives left to the player.
     * @return - an int.
     */
    public int livesLeft() {
        return liveCounter.getValue();
    }

    /**
     * returns the amount of blocks left to finish the level.
     * @return an int.
     */
    public int blocksLeft() {
        return blockCounter.getValue();
    }


    /**
     * a function that adds an object to the game environment.
     * @param c - the object we want to add to the game.
     */
    public void addCollidable(Collidable c) {
        environment.addCollidable(c);
    }

    /**
     * a function that adds a sprite to the collection.
     * @param s - the sprite we want to add.
     */
    public void addSprite(Sprite s) {
        sprites.addSprite(s);
    }

    /**
     * a function that adds the borders to the game.
     */
    public void addBorders() {
        Block topBorder, leftBorder, rightBorder, bottomBorder;
        //top bar
        topBorder = new Block(new Point(0, 0), 800, 30, 0);
        //left bar
        leftBorder = new Block(new Point(0, 30), 30, 570, 0);
        //right bar
        rightBorder = new Block(new Point(770, 30), 30, 570, 0);
        //bottom bar
        bottomBorder = new Block(new Point(0, 630), 850, 30, 0);

        //adding to game
        topBorder.addToGame(this);
        leftBorder.addToGame(this);
        rightBorder.addToGame(this);
        bottomBorder.addToGame(this);
        bottomBorder.addHitListener(ballRemoverListener);
        topBorder.addHitListener(ballRemoverListener);
    }







    /**
     * a function that adds the balls to the game.
     */
    public void addBall() {
            this.shoot = 0.35D;
        //for (int i = 0; i < ballCounter.getValue(); i++) {
            double creationPoint = this.player.getCollisionRectangle().getUpperLeft().getX()
                    + player.getCollisionRectangle().getWidth() / 2;
            Ball ball = new Ball(creationPoint, this.player.getCollisionRectangle().getUpperLeft().getY() - 10,
                    3, Color.white);
            ball.setVelocity(new Velocity(0, 500));
            ball.addToGame(this);
            ball.setGameEnv(environment);
            ball.setCreatedByPlayer(true);
            // }
    }

    /**
     * a function that adds the paddle to the game.
     */
    public void addPaddle() {
        player = new Paddle(gui.getKeyboardSensor(), gui, gameLevel.paddleSpeed(), gameLevel.paddleWidth());
        player.addToGame(this);
        player.setGameEnv(environment);
        player.setGamelvl(this);

    }
    /**
     *  Initialize a new game: create the Blocks and gameobjects.Ball (and gameobjects.Paddle).
     *  and add them to the game.
     */
    public void initialize() {


        passedLevel = false;
        running = false;

        scoreDisplay = new ScoreIndicator(scoreCounter);
        livesDisplay = new LiveIndicator(liveCounter);

        //init listeners
        scoreListener = new ScoreTrackingListener(scoreCounter);
        blockRemoverListener = new BlockRemover(this, blockCounter);
        ballRemoverListener = new BallRemover(this, ballCounter);

        sprites = new SpriteCollection();
        environment = new GameEnvironment();
        this.sprites.addSprite(gameLevel.getBackground());
        //setting the blocks on the screen.
        addBorders();
        addBlocks(70);
        addBlocks(300);
        addBlocks(530);
        //add statistics
        addStats();

        motherShip = new Swarm(this, blockRemoverListener, ballRemoverListener, scoreListener, environment, 70);
       // motherShip.addToGame(this);

    }

    /**
     * adding the score and lives display to the screen.
     */
    private void addStats() {
        scoreDisplay.addToGame(this);
        livesDisplay.addToGame(this);
        levelNameDisplay.addToGame(this);
    }

    /**
     * a function that adds the colored blocks to the screen.
     * @param x - the placement of the defending blocks.
     */
    private void addBlocks(int x) {
        //for (Block block:blockList) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 16; j++) {
                Block block = new Block(new Point(x + j * 10, 500 - 5 * i), 10, 5, 1);
                block.setColor(Color.cyan);
                block.addToGame(this);
                block.addHitListener(blockRemoverListener);
                //block.addHitListener(scoreListener);
                block.addHitListener(ballRemoverListener);
            }
        }
        //}

    }



    /**
     * Run the game -- start the animation loop.
     */
    public void run() {
        playOneTurn();
        while (liveCounter.getValue() != 0 && !passedLevel) {
            playOneTurn();
        }
        return;
    }


    /**
     * the function adds the ball and paddle to the game and starts the animation runner.
     */
    public void playOneTurn() {
        //adding the two balls to the game.
        //addBall();
        //adding the player.
        addPaddle();
        this.running = true;

        this.runner.run(new CountdownAnimation(2000, 3, this.sprites));

        this.runner.run(this);

    }

    /**
     * checks if the game level should stop.
     * @return - a boolean.
     */
    public boolean shouldStop() {
        return !this.running;
    }

    /**
     * the function draws one game frame to the surface.
     * @param d - the drawing surface.
     * @param dt the framse per second.
     */
    public void doOneFrame(DrawSurface d, double dt) {
        this.shoot -= dt;
        //making a copy of the spritecollection before iterating over it.
        SpriteCollection newSpriteCollection = new SpriteCollection(this.sprites);

        try {
            motherShip.timePassed(dt);
            newSpriteCollection.drawAllOn(d);
            newSpriteCollection.notifyAllTimePassed(dt);
        } catch (Exception e) {
            e.printStackTrace();
        }


        //checking if plaer pressed the pause button.
        if (this.keyboard.isPressed("p")) {
            this.runner.run(new KeyPressStoppableAnimation(keyboard, KeyboardSensor.SPACE_KEY, new PauseScreen()));
        }

        if (this.keyboard.isPressed("space") && (this.shoot < 0.0D)) {
                addBall();
        }

        // stopping condition
        if (this.motherShip.didWeWin()) {
            //this.scoreCounter.increase(100);
            this.running = false;
            //this.passedLevel = true;
            this.player.removeFromGame(this);
            int currentSpeed = this.motherShip.getSpeedX();
            initialize();
            this.motherShip.setAddedSpeed(30);


        }

        if (aliensWon || this.player.shouldbeDestroyed()) {
            this.player.setShouldbeDestroyed(false);
            this.player.removeFromGame(this);

            this.liveCounter.decrease(1);
            this.running = false;
            this.motherShip.resetPostition();

            this.aliensWon = false;
            ;
        }
    }

    /**
     * removes a collidable object from the game.
     * @param c - the object we want to remove
     */
    public void removeCollidable(Collidable c) {
        environment.removeCollidable(c);
    }

    /**
     * removes a sprite object from the game.
     * @param s - the object we want to remove
     */
    public void removeSprite(Sprite s) {
        sprites.removeSprite(s);
    }


    /**
     * a fucntion which changes the aliensWon boolean.
     */
    public void setAliensWon() {
        aliensWon = true;
    }
}