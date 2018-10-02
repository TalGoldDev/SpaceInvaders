package animation;

import biuoop.DrawSurface;
import game.Counter;

public class EndScreen implements Animation {

    //the variables.
    private Counter scoreCounter;
    private boolean playerWon;

    /**
     * the constructor which creates the end screen.
     * @param scoreCounter - the player score counter.
     * @param playerWon - a boolean if the player won or not.
     */
    public EndScreen(Counter scoreCounter, boolean playerWon) {
        this.scoreCounter = scoreCounter;
        this.playerWon = playerWon;
    }

    /**
     * the function draws one frame onto the drawing surface.
     * the function waits for input "space key" to continue.
     * @param d - the drawing surface.
     * @param dt - the time passed since last frame.
     */
    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        if (playerWon) {
            d.drawText(10, d.getHeight() / 2, "You Win! Your score is " + scoreCounter.getValue(), 32);
        } else {
            d.drawText(10, d.getHeight() / 2, "Game Over. Your score is " + scoreCounter.getValue(), 32);
        }
    }

    /**
     * the function checks if the animation should stop.
     * @return - false.
     */
    @Override
    public boolean shouldStop() {
        return false;
    }
}
