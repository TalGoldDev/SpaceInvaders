package interfaces;

import gameobjects.Ball;
import geometry.Point;
import geometry.Rectangle;
import geometry.Velocity;

public interface Collidable {

    /**
     * Return the "collision shape" of the object.
     * @return - returning a rectangle
     */
    Rectangle getCollisionRectangle();

    /**
     *  Notify the object that we collided with it at collisionPoint with a given velocity.
     * The return is the new velocity expected after the hit (based on
     * the force the object inflicted on us).
     * @param collisionPoint - the collision point.
     * @param currentVelocity - the current velocity.
     * @param hitter - the ball.
     * @return - returning new velocity.
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
}