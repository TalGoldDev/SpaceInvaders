package listeners;

import gameobjects.Ball;
import gameobjects.Block;

public class PrintingHitListener implements HitListener {

    /**
     * the function which is being called upon a collision.
     * @param beingHit - the block which is being hit.
     * @param hitter - the ball that hit the block.
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        System.out.println("A Block with " + beingHit.getHitPoints() + " points was hit.");
    }
}