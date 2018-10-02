package listeners;

import game.Counter;
import game.GameLevel;
import gameobjects.Ball;
import gameobjects.Block;

public class BallRemover implements HitListener {

    //the variables
    private GameLevel gameLevel;
    private Counter removedBalls;

    /**
     * The constructor.
     * @param gameLevel - the gameLevel level.
     * @param removedBalls - the counter of removed balls.
     */
    public BallRemover(GameLevel gameLevel, Counter removedBalls) {
        this.gameLevel = gameLevel;
        this.removedBalls = removedBalls;
    }

    /**
     * Removes the ball that hit the "death block".
     * @param death  - the bottom block (death block).
     * @param hitter - the ball.
     */
    public void hitEvent(Block death, Ball hitter) {
        hitter.removeFromGame(this.gameLevel);
        this.removedBalls.decrease(1);
    }
}