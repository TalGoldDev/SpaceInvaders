package listeners;

import game.Counter;
import game.GameLevel;
import gameobjects.Ball;
import gameobjects.Block;

// a BlockRemover is in charge of removing blocks from the gameLevel, as well as keeping count
// of the number of blocks that remain.
public class BlockRemover implements HitListener {
    private GameLevel gameLevel;
    private Counter remainingBlocks;

    /**
     * the constructor.
     * @param gameLevel - the current game level.
     * @param removedBlocks - the counter of the removed blocks.
     */
    public BlockRemover(GameLevel gameLevel, Counter removedBlocks) {
        this.gameLevel = gameLevel;
        this.remainingBlocks = removedBlocks;
    }

    /**
     * Blocks that are hit and reach 0 hit-points should be removed from the gameLevel.
     * @param beingHit - the block which is being hit.
     * @param hitter - the ball that hit the block.
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        if (beingHit.getHitPoints() == 0) {
            beingHit.removeFromGame(this.gameLevel);
            beingHit.removeHitListener(this);
            this.remainingBlocks.decrease(1);

        }
    }
}
