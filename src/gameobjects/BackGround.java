package gameobjects;

import biuoop.DrawSurface;
import interfaces.Sprite;

public class BackGround implements Sprite {

    //the variables.
    private SpriteCollection sprites;

    /**
     * the constructor method.
     * @param sprites - the spriteCollection.
     */
    public BackGround(SpriteCollection sprites) {
        this.sprites = sprites;
    }

    /**
     * the draw metod which calls the draw method of the sprite collection.
     * @param d - the drawing surface.
     */
    @Override
    public void drawOn(DrawSurface d) {
        sprites.drawAllOn(d);
    }

    /**
     * notify the sprite that time has passed.
     * @param dt - the time passed since the the last call to this function.
     */
    @Override
    public void timePassed(double dt) {

    }
}
