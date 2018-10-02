package gameobjects;

import biuoop.DrawSurface;
import game.Counter;
import game.GameLevel;
import interfaces.Sprite;

import java.awt.Color;

public class LiveIndicator implements Sprite {

    //the variables.
    private Counter lives;

    /**
     * the tostring method which returns the amount of lives left.
     * @return - a string which displays the amount of lives left.
     */
    @Override
    public String toString() {
        return "lives: " + lives.getValue();
    }

    /**
     * a function that returns the lives Counter object.
     * @return - the lives counter object.
     */
    public Counter getLives() {
        return lives;
    }

    /**
     * a cuntructor method.
     * @param livesCounter - the lives counter.
     */
    public LiveIndicator(Counter livesCounter) {
        this.lives = livesCounter;
    }

    /**
     * the draw method which draws the lives value on the drawing surface.
     * @param d - the drawing surface.
     */
    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.white);
        d.drawText(50, 20, this.toString(), 15);
    }

    /**
     * a functiion which adds this object to the game leve.
     * @param gameLevel - the game level.
     */
    public void addToGame(GameLevel gameLevel) {
        gameLevel.addSprite(this);
    }


    /**
     * the time passed method.
     * @param dt - the time passed since last frame.
     */
    @Override
    public void timePassed(double dt) {

    }
}
