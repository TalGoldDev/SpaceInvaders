package levels;

import gameobjects.Block;
import geometry.Velocity;
import interfaces.Sprite;

import java.util.List;

public interface LevelInformation {

    /**
     * returns the number of balls the level should start with.
     * @return - an integer.
     */
    int numberOfBalls();

    /**
     * The initial velocity of each ball.
     * Note that initialBallVelocities().size() == numberOfBalls()
     * @return - a list with the velocities.
     */
    List<Velocity> initialBallVelocities();

    /**
     * the paddle speed.
     * @return - an integer.
     */
    int paddleSpeed();

    /**
     * the paddle width.
     * @return - an integer.
     */
    int paddleWidth();

    /**
     * the level name will be displayed at the top of the screen.
     * @return - a string.
     */
    String levelName();

    /**
     * Returns a sprite with the background of the level.
     * @return - a Sprite.
     */
    Sprite getBackground();

    /**
     * The Blocks that make up this level, each block contains its size, color and location.
     * @return - list of blocks.
     */
    List<Block> blocks();

    /**
     * Number of blocks that should be removed before the level is considered to be "cleared".
     * This number should be <= blocks.size();
     * @return - an integer.
     */
    int numberOfBlocksToRemove();
}
