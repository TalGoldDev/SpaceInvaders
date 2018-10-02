package geometry;


public class Point {

    private double x, y;

    /**
     * the constructor of point class.
     *
     * @param x the x value of the point
     * @param y the y value of the point
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * returns the x value of the point.
     *
     * @return the value of x.
     */
    public double getX() {
        return x;
    }
    /**
     * returns the y value of the point.
     *
     * @return the value of y.
     */
    public double getY() {
        return y;
    }


    /**
     * return the distance of this point to the other point.
     *
     * @param other - the other point
     * @return -a double type,the distance of this point to the other point.
     */
    public double distance(Point other) {
        //sqrt((x2-x1)^2 +(y2-y1)^2) the formula
        double distanceX, distanceY, distance;
        distanceX = this.x - other.getX();
        distanceX = Math.pow(distanceX, 2);
        distanceY = this.y - other.getY();
        distanceY = Math.pow(distanceY, 2);
        distance = distanceX + distanceY;
        distance = Math.sqrt(distance);
        return distance;
    }


    /**
     * Method that will find the mid point of the line.
     *
     * @param p is the point.
     * @return the mid point.
     */
    public Point midpoint(Point p) {
        double ex = (p.getX() + x) / 2;
        double wy = (p.getY() + y) / 2;
        return new Point(ex, wy);
    }

    /**
     * return true is the points are equal, false otherwise.
     *
     * @param other - the other point
     * @return - a boolean value if both x,y values of both points are equal.
     */
    public boolean equals(Point other) {
        if (other.getX() == this.x && other.getY() == this.y) {
            return true;
        }
        return false;
    }



    /**
     * a function to check if a specific point is on line.
     * @param line - the line we want to check if our point is on.
     * @return - true if point is on line ,false otherwise.
     */
    public boolean onLine(Line line) {
        double dxc, dyc, dxl, dyl, cross;
        dxc = this.x - line.start().getX();
        dyc = this.y - line.start().getY();
        dxl = line.end().getX() - line.start().getX();
        dyl = line.end().getY() - line.start().getY();
        cross = dxc * dyl - dyc * dxl;
        if (cross != 0) {
            return false;
        }

        return true;
    }

}
