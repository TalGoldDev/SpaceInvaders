package gameobjects;

import geometry.Point;
import interfaces.Collidable;

public class CollisionInfo {

    //the variables.
    private Point collisionPoint;
    private Collidable item;

    /**
     * constructor method of the collisioninfo.
     * @param point - the collision point
     * @param e - the collidable object
     */
    public CollisionInfo(Point point, Collidable e) {
        this.collisionPoint = point;
        this.item = e;
    }

    /**
     * the point at which the collision occurs.
     * @return - the collision point.
     */
    public Point collisionPoint() {
        return collisionPoint;

    }

    /**
     * the collidable object involved in the collision.
     * @return - the item invloved in the collision.
     */
    public Collidable collisionObject() {
        return item;
    }
}