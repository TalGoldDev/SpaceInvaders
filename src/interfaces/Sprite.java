package interfaces;

import biuoop.DrawSurface;

public interface Sprite {

    /**
     * Draw the sprite to the screen.
     * @param d - the drawing surface.
     */
    void drawOn(DrawSurface d);

    /**
     * notify the sprite that time has passed.
     * @param dt - the time passed since last frame.
     */
    void timePassed(double dt);
}