package animation;

import biuoop.DrawSurface;

public interface Animation {
    /**
     * a function that calls draws one frame on the surface.
     * @param d - the drawing surface.
     * @param dt - the time passed since last frame.
     */
    void doOneFrame(DrawSurface d, double dt);

    /**
     * a function that checks if the drawing loop should stop.
     * @return - true or false.
     */
    boolean shouldStop();
}