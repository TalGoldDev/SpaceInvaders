package gameobjects;

import biuoop.DrawSurface;
import game.GameLevel;
import geometry.Line;
import geometry.Point;
import geometry.Velocity;
import interfaces.Sprite;

import java.awt.Color;

public class Ball implements Sprite {
    //the variables.
    private Point center;
    private int radius;
    private Color color;
    private Velocity v;
    private GameEnvironment gameEnv;

    /**
     * checking if ball is created by the paddle.
     * @return - an integer.
     */
    public boolean isCreatedByPlayer() {
        return createdByPlayer;
    }

    private boolean createdByPlayer = false;

    /**
     * a function which sets if the ball is created by the player.
     * @param byPlayer - an boolean.
     */
    public void setCreatedByPlayer(boolean byPlayer) {
        this.createdByPlayer = byPlayer;
    }

    /**
     * setting the game environment for the ball.
     * @param environment - the current game environment
     */
    public void setGameEnv(GameEnvironment environment) {
        this.gameEnv = environment;
    }



    /**
     * returning the center point of the ball.
     *
     * @return - the center point
     */
    public Point getCenter() {
        return center;
    }

    /**
     * the constructor function.
     *
     * @param center - the center point of the ball.
     * @param r - the radius of the ball.
     * @param color - the color of the ball.
     */
    public Ball(Point center, int r, Color color) {
        this.center = center;
        this.radius = r;
        this.color = color;
    }

    /**
     * the constructor function with values instead of point.
     *
     * @param x - the x value of the center of the point.
     * @param y - the y value of the center of the point.
     * @param r - the radius of the ball.
     * @param color - the color of the ball.
     */
    public Ball(double x, double y, int r, Color color) {
        double fixedX = x, fixedY = y;
        if (x - r <= 0) {
            fixedX = r;
        }
        if (y - r <= 0) {
            fixedY = r;
        }
        this.center = new Point(fixedX, fixedY);
        this.radius = r;
        this.color = color;
    }


    /**
     * a get function for the x value of the center of the ball.
     *
     * @return - the x value of the center of the ball
     */
    public int getX() {
        return (int) center.getX();
    }

    /**
     * a get function for the y value of the center of the ball.
     *
     * @return - the y value of the center of the ball
     */
    public int getY() {
        return (int) center.getY();
    }

    /**
     * a get function for the raius of the ball.
     *
     * @return - the radius value of the ball.
     */
    public int getSize() {
        return this.radius;
    }

    /**
     * a get function for the color of the ball.
     *
     * @return - the color value of the ball.
     */
    public Color getColor() {
        return this.color;
    }

    /**
     *  draw the ball on the given DrawSurface.
     *
     * @param surface - the inputed surface we want to draw the ball on.
     */
    public void drawOn(DrawSurface surface) {
        surface.setColor(Color.black);
        surface.drawCircle((int) this.center.getX(), (int) this.center.getY(), this.radius);
        surface.setColor(this.color);
        surface.fillCircle((int) this.center.getX(), (int) this.center.getY(), this.radius);
    }


    /**
     * notifing the object of time passed by moving.
     * @param dt - the time passed since the the last call to this function.
     */
    @Override
    public void timePassed(double dt) {
        this.moveOneStep(dt);
    }

    /**
     * a set function for the velocity of the ball.
     *
     * @param velocity - the velocity we want to set
     */
    public void setVelocity(Velocity velocity) {
        this.v = velocity;
    }

    /**
     * a set function for the velocity of the ball.
     *
     * @param dx - the change in x value.
     * @param dy - the change in y value.
     */
    public void setVelocity(double dx, double dy) {
        this.v = new Velocity(dx, dy);
    }

    /**
     * a get function for the velocity of the ball.
     *
     * @return - the velocity object of the ball.
     */
    public Velocity getVelocity() {
        return v;
    }


    /**
     * a function to move the ball one step forward by applying the velocity to the ball.
     * @param dt - the time passed since last frame.
     */
    public void moveOneStep(double dt) {

        Line trajectory;
        CollisionInfo colDetected;
        double valueX, valueY;

        trajectory = findTrajectory(dt);
        colDetected = gameEnv.getClosestCollision(trajectory);

        if (colDetected != null) {
            valueX = Math.round(colDetected.collisionPoint().getX());
            valueY = Math.round(colDetected.collisionPoint().getY());
            Point roundedCol = new Point(valueX, valueY);
            this.setVelocity(colDetected.collisionObject().hit(this, roundedCol, this.v));
        } else {
            this.center = this.getVelocity().applyToPoint(this.center, dt);
        }
    }


    /**
     * Computes the ball's trajectory.
     * @param dt - time passed since last frame.
     * @return trajectory geometry.Line
     */
    public Line findTrajectory(double dt) {
        Line trajectory;
        double trajectoryX, trajectoryY;

        if (this.getVelocity().getDx() < 0) {
            trajectoryX = this.center.getX() + this.getVelocity().getDx() * dt - radius - 2;
        } else {
            trajectoryX = this.center.getX() + this.getVelocity().getDx() * dt + radius + 2;
        }


        if (this.getVelocity().getDy() < 0) {
            trajectoryY = this.center.getY() + this.getVelocity().getDy() * dt - radius - 2;
        } else {
            trajectoryY = this.center.getY() + this.getVelocity().getDy() * dt + radius + 2;
        }

        trajectory = new Line(this.center.getX(), this.center.getY(),
                trajectoryX, trajectoryY);
        return trajectory;

    }


    /**
     * a function to add the current object to the game.
     * @param object - the current game we want to input this object into.
     */
    public void addToGame(GameLevel object) {
        object.addSprite(this);
    }

    /**
     * a function that removes the ball from the gamelevel.
     * @param gameLevel - the game level.
     */
    public void removeFromGame(GameLevel gameLevel) {
        gameLevel.removeSprite(this);
    }
}