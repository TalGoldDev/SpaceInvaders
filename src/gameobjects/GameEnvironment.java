package gameobjects;

import geometry.Line;
import geometry.Point;
import interfaces.Collidable;

import java.util.ArrayList;
import java.util.List;


public class GameEnvironment {

    //the variables.
    private List<Collidable> list;


    /**
     * the constructor of the game enviroment.
     */
    public GameEnvironment() {
        this.list = new ArrayList<Collidable>();
    }


    /**
     * add the given collidable to the environment.
     * @param c - the newly added collidable object.
     */
    public void addCollidable(Collidable c) {
        list.add(c);
    }


    /**
     * removes the given collidable from the environment.
     * @param c - the removed collidable object.
     */
    public void removeCollidable(Collidable c) {
        list.remove(c);
    }


    /**
     * Assume an object moving from line.start() to line.end().
     * If this object will not collide with any of the collidables
     * in this collection, return null. Else, return the information
     * about the closest collision that is going to occur.
     * @param trajectory - the inputed line
     * @return - the information about the collision.
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        for (Collidable item : list) {
            Point intersection = trajectory.closestIntersectionToStartOfLine(item.getCollisionRectangle());
            if (intersection != null) {
                CollisionInfo information = new CollisionInfo(intersection, item);
                return information;
            }
        }

        return null;
    }

}