package geometry;

public class Velocity {

    private double dx, dy;

    /**
     * the constructor function which uses x and y values.
     * @param dx - the x value.
     * @param dy - the y value.
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * a getter function for the x value of the velocity.
     *
     * @return the x value
     */
    public double getDx() {
        return dx;
    }

    /**
     * a getter function for the y value of the velocity.
     *
     * @return the y value
     */
    public double getDy() {
        return dy;
    }



    /**
     * a function which converts the angle and speed.
     * into x and y values for the constructor of velocity.
     * @param angle - the angle of change.
     * @param speed - the speed of the change.
     * @return - the new velocity we created using the angle and speed.
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        double dx = speed * Math.cos((angle - 90) * Math.PI / 180);;
        double dy = speed * Math.sin((angle - 90) * Math.PI / 180);;
        return new Velocity(dx, dy);
    }

    /**
     * Take a point with position (x,y) and return a new point.
     * with position (x+dx, y+dy).
     * @param p - the inputed point.
     * @param dt - the time passed since last frame.
     * @return - the new point with the new x,y values.
     */
    public Point applyToPoint(Point p, double dt) {
        return new Point(p.getX() + this.dx * dt, p.getY() + this.dy * dt);
    }

}