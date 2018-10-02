package gameobjects;

import biuoop.DrawSurface;
import game.Counter;
import game.GameLevel;
import interfaces.Sprite;

import java.awt.Color;

public class ScoreIndicator implements Sprite {

    // the variables.
    private Counter score;

    /**
     * the constructor.
     * @param score - the score counter.
     */
    public ScoreIndicator(Counter score) {
        this.score = score;
    }

    /**
     * the tostring method which returns the score.
     * @return - a string which displays the score.
     */
    @Override
    public String toString() {
        return "score = " + score.getValue();
    }

    /**
     * a function which returns the score counter.
     * @return - the score counter.
     */
    public Counter getScore() {
        return score;
    }

    /**
     * the draw method which draws the score value on the drawing surface.
     * @param d - the drawing surface.
     */
    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.white);
        d.drawText(700, 20, this.toString(), 15);
    }

    /**
     * the time passed method.
     * @param dt - the time passed since last frame.
     */
    @Override
    public void timePassed(double dt) {

    }

    /**
     * a function which adds the score indicator to the game.
     * @param gameLevel - the current game level.
     */
    public void addToGame(GameLevel gameLevel) {
        gameLevel.addSprite(this);
    }

}
