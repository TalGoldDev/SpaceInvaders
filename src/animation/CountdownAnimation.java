package animation;

import biuoop.DrawSurface;
import biuoop.Sleeper;
import game.Counter;
import gameobjects.SpriteCollection;

import java.awt.Color;

public class CountdownAnimation implements Animation {

    //the variables.
    private double numOfSeconds;
    private Counter countFrom;
    private SpriteCollection gameScreen;
    private double count;

    /**
     * the constructor which creates the animation.
     * @param numOfSeconds - the number of seconds we want to watch the animation.
     * @param countFrom - the number we want to countdown from.
     * @param gameScreen - the current gamescreen which we want to display this over.
     */
    public CountdownAnimation(double numOfSeconds, int countFrom, SpriteCollection gameScreen) {
        this.numOfSeconds = numOfSeconds;
        this.count = countFrom;
        this.countFrom = new Counter(countFrom);
        this.gameScreen = gameScreen;
    }

    /**
     * the function draws one frame onto the drawing surface.
     * @param d - the drawing surface.
     * @param dt the framse per second.
     */
    public void doOneFrame(DrawSurface d, double dt) {
        this.gameScreen.drawAllOn(d);

        d.setColor(Color.CYAN);
        d.drawText(350, 350, String.valueOf(this.countFrom.getValue()), 100);
        this.countFrom.decrease(1);
        Sleeper sleeper = new Sleeper();
        sleeper.sleepFor((long) numOfSeconds / (long) this.count);
    }

    /**
     * a function that checks if the drawing loop should stop.
     * @return - true or false.
     */
    public boolean shouldStop() {
        return this.countFrom.getValue() == -1;
    }
}