package gameobjects;

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import game.GameLevel;
import geometry.Point;
import geometry.Rectangle;
import geometry.Velocity;
import interfaces.Collidable;
import interfaces.Sprite;

import java.awt.Color;

public class Paddle implements Sprite, Collidable {

    //the variables.
    private double xValue, yValue, speed, width, height;
    private Rectangle paddle;
    private GameEnvironment gameEnv;

    /**
     * a function which sets the gamelevel variable.
     * @param gameLevel - the game level.
     */
    public void setGamelvl(GameLevel gameLevel) {
        this.gamelvl = gameLevel;
    }

    private GameLevel gamelvl;
    /**
     * a function which returns if the paddle should be destroyed.
     * @return - a boolean.
     */
    public boolean shouldbeDestroyed() {
        return shouldbeDestroyed;
    }

    private boolean shouldbeDestroyed = false;

    /**
     * a function which sets if the paddle should be destroyed.
     * @param destroy - a boolean.
     */
    public void setShouldbeDestroyed(boolean destroy) {
        this.shouldbeDestroyed = destroy;
    }

    /**
     * the constructor of the paddle.
     * @param keyboard - the keyboard sensor.
     * @param gui - the current game interface.
     */
    public Paddle(KeyboardSensor keyboard, GUI gui) {
        speed = 10;
        height = 20;
        width = 150;
        this.gui = gui;
        this.keyboard = keyboard;
        paddle = new Rectangle(new Point(325, 550), width, height);

    }

    /**
     * the paddle constructor.
     * @param keyboard - the keyboard.
     * @param gui - the gui.
     * @param speed - the paddles speed.
     * @param width - the paddles size(width).
     */
    public Paddle(KeyboardSensor keyboard, GUI gui, double speed, double width) {
        this.speed = speed;
        height = 20;
        this.width = width;
        this.gui = gui;
        this.keyboard = keyboard;
        paddle = new Rectangle(new Point(365 - width / 2, 550), this.width, height);

    }

    private KeyboardSensor keyboard;
    private GUI gui;

    /**
     * a function that sets the game environment for this object.
     * @param environment - the current game environment
     */
    public void setGameEnv(GameEnvironment environment) {
        this.gameEnv = environment;
    }

    /**
     * a function that moves left the paddle if there is no collision with the walls.
     * @param dt - time passed since last frame.
     */
    public void moveLeft(double dt) {
        xValue = paddle.getUpperLeft().getX();
        yValue = paddle.getUpperLeft().getY() - paddle.getHeight();

        if (paddle.getUpperLeft().getX() - (speed * dt) > 30) {
            Point newPoint = new Point(paddle.getUpperLeft().getX() - speed * dt, paddle.getUpperLeft().getY());
            this.paddle = new Rectangle(newPoint, width, height);
        }
    }

    /**
     * a function that moves right the paddle if there is no collision with the walls.
     * @param dt - time passed since last frame.
     */
    public void moveRight(double dt) {
        xValue = paddle.getUpperLeft().getX() + paddle.getWidth();
        yValue = paddle.getUpperLeft().getY() - paddle.getHeight();

        if (paddle.getUpperLeft().getX() + width + (speed * dt) < 770) {
            Point newPoint = new Point(paddle.getUpperLeft().getX() + speed * dt, paddle.getUpperLeft().getY());
            this.paddle = new Rectangle(newPoint, width, height);
        }
    }

    /**
     * the time passed method which moves the paddle if specific key is pressed.
     * @param dt - the time passed since last frame.
     */
    public void timePassed(double dt) {
        if (keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            moveLeft(dt);
        }
        if (keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            moveRight(dt);
        }

    }

    /**
     * a function that draws the paddle rectangle on the game scree.
     * @param d - the current drawing surface.
     */
    public void drawOn(DrawSurface d) {
        d.setColor(Color.black);
        d.drawRectangle((int) paddle.getUpperLeft().getX(),
                (int) paddle.getUpperLeft().getY(),
                (int) paddle.getWidth(), (int) paddle.getHeight());
        d.setColor(Color.orange);
        d.fillRectangle((int) paddle.getUpperLeft().getX(),
                (int) paddle.getUpperLeft().getY(),
                (int) paddle.getWidth(), (int) paddle.getHeight());

    }

    /**
     * a function that returns the paddle's rectangle.
     * @return - a rectangle.
     */
    public Rectangle getCollisionRectangle() {
        return paddle;
    }


    /**
     * the hit method which responds to a detected collision and returns a new velocity for the ball.
     * @param hitter - the ball.
     * @param collisionPoint - the collision point.
     * @param currentVelocity - the current velocity.
     * @return - a new velocity.
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        double hitPointX = collisionPoint.getX();
        double x = currentVelocity.getDx();
        double y = currentVelocity.getDy();
        double returnedSpeed = Math.round(Math.sqrt((y * y) + (x * x)));

        double topLeftX = paddle.getUpperLeft().getX();
        double sectionSize = paddle.getWidth() / 5;
        // the x value where the section ends
        double firstSection, secSection, thirdSection, fourthSection, fifthSection;
        if (!hitter.isCreatedByPlayer()) {
            shouldbeDestroyed = true;
            hitter.removeFromGame(gamelvl);
        }
        firstSection = topLeftX + sectionSize;
        secSection = topLeftX + 2 * sectionSize;
        thirdSection = topLeftX + 3 * sectionSize;
        fourthSection = topLeftX + 4 * sectionSize;
        fifthSection = topLeftX + 5 * sectionSize;
        Velocity newVel = new Velocity(currentVelocity.getDx(), -currentVelocity.getDy());
        return newVel;
    }

    /**
     * Add this paddle to the game.
     * @param g - the current game.
     */
    public void addToGame(GameLevel g) {
        g.addCollidable(this);
        g.addSprite(this);
    }


    /**
     * a function which removes the paddle from the game.
     * @param gameLevel - the gamelevel which we want to remove the paddle from.
     */
    public void removeFromGame(GameLevel gameLevel) {
        gameLevel.removeCollidable(this);
        gameLevel.removeSprite(this);
    }

}
