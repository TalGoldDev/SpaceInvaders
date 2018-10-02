package listeners;

import game.Counter;
import gameobjects.Ball;
import gameobjects.Block;

public class ScoreTrackingListener implements HitListener {
    private Counter currentScore;

    /**
     * the constructor.
     * @param scoreCounter the new score counter.
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }

    /**
     * the function which is called upon a collision.
     * @param beingHit - the block which is being hit.
     * @param hitter - the ball that hit the block.
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        //this.currentScore.increase(5);
        if (beingHit.getHitPoints() == 0) {
            this.currentScore.increase(100);
        }
    }
}