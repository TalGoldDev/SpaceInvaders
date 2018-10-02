package gameobjects;

import biuoop.DrawSurface;
import game.GameLevel;
import interfaces.Sprite;

import java.awt.Color;

public class LevelNameIndicator implements Sprite {

    // the variables.
    private String levelName;

    /**
     * the constructor.
     * @param lvlName - the name of the level.
     */
    public LevelNameIndicator(String lvlName) {
        this.levelName = lvlName;
    }

    /**
     * the tostring method which returns the lvl name.
     * @return - a string which displays the name of the level.
     */
    @Override
    public String toString() {
        return levelName;
    }

    /**
     * the draw method which draws the name of the level on the drawing surface.
     * @param d - the drawing surface.
     */
    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.white);
        d.drawText(365, 20, this.toString(), 15);
    }

    /**
     * the time passed method.
     * @param dt frames per second.
     */
    @Override
    public void timePassed(double dt) {
    }

    /**
     * a function which adds the level indicator to the game.
     * @param gameLevel - the current game level.
     */
    public void addToGame(GameLevel gameLevel) {
        gameLevel.addSprite(this);
    }
    }
