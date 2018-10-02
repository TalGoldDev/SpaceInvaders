package animation;

import biuoop.DrawSurface;

public class PauseScreen implements Animation {

    //the variables.
    /**
     * the constructor function.
     */
    public PauseScreen() {
    }


    /**
     * a function that draws the screen to the surface and waits for input "key space".
     * @param d - the drawing surface.
     * @param dt - the time passed since last frame.
     */
    public void doOneFrame(DrawSurface d, double dt) {
        d.drawText(10, d.getHeight() / 2, "paused -- press space to continue", 32);

    }

    /**
     * a function that checks if this screen should stop.
     * @return - a boolean.
     */
    public boolean shouldStop() {
        return false;
    }
}

