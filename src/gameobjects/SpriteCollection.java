package gameobjects;

import biuoop.DrawSurface;
import interfaces.Sprite;

import java.util.ArrayList;
import java.util.List;


public class SpriteCollection {

    //the variables.
    private List<Sprite> list;

    /**
     * the constructor method which creates a new sprite list.
     */
    public SpriteCollection() {
        this.list = new ArrayList<Sprite>();
    }

    /**
     * the constructor method which creates a new sprite list from an existing one.
     * @param newCollection - the sprite collection which we are creating the new collection with.
     */
    public SpriteCollection(SpriteCollection newCollection) {
        this.list = new ArrayList<Sprite>(newCollection.list);
    }


    /**
     * a function that adds a sprite to the list.
     * @param s the sprite that we want to add.
     */
    public void addSprite(Sprite s) {
        list.add(s);
    }


    /**
     * a function that removes the sprite from the list.
     * @param s the sprite that we want to remove.
     */
    public void removeSprite(Sprite s) {
        list.remove(s);
    }

    /**
     * call timePassed() on all sprites.
     * @param dt - the time passed since last frame.
     */
    public void notifyAllTimePassed(double dt) {
        for (Sprite item : list) {
            item.timePassed(dt);
        }
    }

    /**
     * call drawOn(d) on all sprites.
     * @param d - the drawing surface
     */
    public void drawAllOn(DrawSurface d) {
        for (Sprite item : list) {
            item.drawOn(d);
        }
    }
}