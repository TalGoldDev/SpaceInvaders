package gameobjects;

import biuoop.DrawSurface;
import game.GameLevel;
import geometry.Point;
import geometry.Rectangle;
import geometry.Velocity;
import interfaces.Collidable;
import interfaces.Sprite;
import levels.ColorsParser;
import listeners.HitListener;
import listeners.HitNotifier;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Block implements Collidable, Sprite , HitNotifier {

    //the variables.
    private Rectangle block;


    private int healthPoints;
    private List<HitListener> hitListeners;


    private String colorOrImageString;
    private Color strokeColor;
    private HashMap<Integer, String> hashFill;
    private HashMap<Integer, Image> hashImage = new HashMap<Integer, Image>();
    private HashMap<Integer, Color> hashColor = new HashMap<Integer, Color>();

    private Image defaultImage = null;
    private Color defaultColor = null;

    private Color color;

    /**
     * The Constructor for the block.
     * @param r receives the rectangle.
     * @param c receives the color for the block.
     * @param n is the counter of the hits.
     * @param s The color.
     * @param h The HashMap.
     */
    public Block(Rectangle r, String c, int n, Color s,
                 HashMap<Integer, String> h) {
        hashFill = h;
        strokeColor = s;
        block = r;
        colorOrImageString = c;
        this.healthPoints = n;
        this.hitListeners = new ArrayList<HitListener>();
        intializeHashMaps();
        initalizeDefaultColor();
    }


    /**
     * a function that gets the hitpoints of this block.
     * @return an integer.
     */
    public int getHitPoints() {
        return healthPoints;
    }

    /**
     * a function that sets the color of the block.
     * @param chosenColor - the inputted color
     */
    public void setColor(Color chosenColor) {
        this.color = chosenColor;
        this.strokeColor = chosenColor;
    }

    /**
     * constructor method for block.
     * @param upperLeft - upper left point of the block
     * @param width - the width of the block
     * @param height - the height of the block
     * @param hp - the hitpoints of the block
     */
    public Block(Point upperLeft, double width, double height, int hp) {
        this.block = new Rectangle(upperLeft, width, height);
        this.healthPoints = hp;
        color = Color.darkGray;
        defaultColor = Color.darkGray;
        this.hitListeners = new LinkedList<>();
    }


    /**
     * Return the "collision shape" of the object.
     * @return - returning the rectangle
     */
    @Override
    public Rectangle getCollisionRectangle() {
        return block;
    }

    /**
     * update the block.
     * @param newRec - the new rectangle.
     */
    public void updateCollisionRectangle(Rectangle newRec) {
        this.block = newRec;
    }


    /**
     *  Notify the object that we collided with it at collisionPoint with a given velocity.
     * The return is the new velocity expected after the hit (based on
     * the force the object inflicted on us).
     * @param hitter - the ball.
     * @param collisionPoint - the collision point
     * @param currentVelocity - the current velocity
     * @return - returning the new velocity
     */
    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        double valueX, valueY;
        if (this.healthPoints > 0) {
            this.healthPoints -= 1;
        }

        // If the ball hits the corner.
        if (this.block.points().contains(collisionPoint)) {
            this.notifyHit(hitter);
            return new Velocity(Math.round(currentVelocity.getDx()) * -1, Math.round(currentVelocity.getDy()) * -1);
        }

        // if ball hits horizontal object, reverse vertical direction
        if (collisionPoint.onLine(block.getLeftVertical()) || collisionPoint.onLine(block.getRightVertical())) {
            this.notifyHit(hitter);
            return new Velocity(Math.round(currentVelocity.getDx()) * -1, Math.round(currentVelocity.getDy()));
        } else if (collisionPoint.onLine(block.getBottomHorizontal())
                || collisionPoint.onLine(block.getTopHorizontal())) {
            this.notifyHit(hitter);
            return new Velocity(Math.round(currentVelocity.getDx()), Math.round(currentVelocity.getDy()) * -1);
        }
        this.notifyHit(hitter);
        return currentVelocity;
    }

    /**
     *  draw the ball on the given DrawSurface.
     *
     * @param surface - the inputed surface we want to draw the ball on.
     */
    public void drawOn(DrawSurface surface) {
        if (hashFill != null) {
            if (hashImage.containsKey(healthPoints)) {
                surface.drawImage((int) block.getUpperLeft().getX(),
                        (int) block.getUpperLeft().getY(),
                        hashImage.get(healthPoints));
            } else if (hashFill.containsKey(healthPoints)) {
                surface.setColor(color);
                surface.fillRectangle((int) this
                                .getCollisionRectangle().getUpperLeft().getX(),
                        (int) this.getCollisionRectangle().
                                getUpperLeft().getY(),
                        (int) this.getCollisionRectangle().getWidth(),
                        (int) this.getCollisionRectangle().getHeight()
                );
            } else {
                drawDefault(surface);
            }
        } else {
            drawDefault(surface);
        }
    }


    /**
     * Draws the Surface to the Gui.
     *
     * @param d the Surface to be drawn.
     */
    private void drawDefault(DrawSurface d) {
        if (defaultImage != null) {
            d.drawImage((int) block.getUpperLeft().getX(),
                    (int) block.getUpperLeft().getY(), defaultImage);
        } else {
            d.setColor(color);
            d.fillRectangle((int) this
                            .getCollisionRectangle().getUpperLeft().getX(),
                    (int) this.getCollisionRectangle().getUpperLeft().getY(),
                    (int) this.getCollisionRectangle().getWidth(),
                    (int) this.getCollisionRectangle().getHeight()
            );
        }
    }

    /**
     * the time pased function.
     * @param dt frames per second.
     */
    @Override
    public void timePassed(double dt) {

    }

    /**
     * adding the block object to the game.
     * @param object - the current game
     */
    public void addToGame(GameLevel object) {
        object.addCollidable(this);
        object.addSprite(this);
    }

    /**
     * a method to remove this block object from the gameLevel.
     * @param gameLevel - the gameLevel we want to remove this object from.
     */
    public void removeFromGame(GameLevel gameLevel) {
        gameLevel.removeSprite(this);
        gameLevel.removeCollidable(this);
    }

    /**
     * a function that adds a hit listener to the block.
     * @param hl - the hit listener.
     */
    @Override
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    /**
     * a function that removes the hit listener from the block.
     * @param hl - the hit listener.
     */
    @Override
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }

    /**
     * a function that notifies the listeners that a hit has occurred.
     * @param hitter - the ball the collision happened with.
     */
    public void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }

    /**
     * a function which returns the width of the block.
     * @return the blocks width.
     */
    public int getWidth() {
        return (int) block.getWidth();
    }


    /**
     * a function to init the color of the block.
     */
    private void initalizeDefaultColor() {
        if (colorOrImageString != null) {
            String[] stringsArray = colorOrImageString.split("\\(");
            if (stringsArray[0].equals("image")) {
                Image img = null;
                try {
                    String str = stringsArray[1].substring(0,
                            stringsArray[1].length() - 1);
                    img = ImageIO.read(ClassLoader.getSystemClassLoader().
                            getResourceAsStream(str));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                defaultImage = img;
            } else {
                defaultColor = ColorsParser.
                        colorFromString(colorOrImageString);
            }
        }
    }
    /**
     * a function to init the HashMaps.
     */
    private void intializeHashMaps() {
        if (hashFill != null) {
            for (int i = 1; i < 10; i++) {
                if (hashFill.containsKey(i)) {
                    String [] stringsArray  = hashFill.get(i).split("\\(");
                    if (stringsArray[0].equals("image")) {
                        Image img = null;
                        try {
                            String str = stringsArray[1].substring(0,
                                    stringsArray[1].length() - 1);
                            img = ImageIO.read(ClassLoader.
                                    getSystemClassLoader().
                                    getResourceAsStream(str));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        hashImage.put(i, img);
                    } else {
                        hashColor.put(i,
                                ColorsParser.colorFromString(hashFill.get(i)));
                    }
                }
            }
        }
    }




}
