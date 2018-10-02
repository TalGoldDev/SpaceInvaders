package geometry;

import java.util.ArrayList;
import java.util.List;

public class Rectangle {


    private Point topLeft, topRight, bottomLeft, bottomRight;
    private double width, height;
    private Line topHorizontal, bottomHorizontal, leftVertical, rightVertical;

    /**
     * Create a new rectangle with location and width/height.
     *
     * @param upperLeft - the upper left point of the rectangle
     * @param width - the width value
     * @param height - the height value
     */
    public Rectangle(Point upperLeft, double width, double height) {
        topLeft = upperLeft;
        this.width = width;
        this.height = height;
        setBorders();
        makePoints();
    }

    /**
     * a function to set the border lines of the rectangle.
     */
    private void setBorders() {
        topHorizontal = new Line(topLeft.getX(), topLeft.getY(), topLeft.getX() + width, topLeft.getY());
        bottomHorizontal = new Line(topLeft.getX(), topLeft.getY() + height,
                topLeft.getX() + width, topLeft.getY() + height);
        leftVertical = new Line(topLeft.getX(), topLeft.getY(), topLeft.getX(), topLeft.getY() + height);
        rightVertical = new Line(topLeft.getX() + width, topLeft.getY(),
                topLeft.getX() + width, topLeft.getY() + height);
    }

    /**
     * setting the top left point of the rectangle.
     * @param point - the rectangle's new top left point
     */
    public void setTopLeft(Point point) {
        this.topLeft = point;
    }

    /**
     * setting the width of the rectangle.
     * @param newWidth - the rectangle's new width
     */
    public void setWidth(double newWidth) {
        this.width = newWidth;
    }

    /**
     * the function sets the height of the rectangle.
     * @param newHeight - the rectangle's new height
     */
    public void setHeight(double newHeight) {
        this.height = newHeight;
    }

    /**
     * the function returns the top border of the rectangle.
     * @return - the line that represents the top border
     */
    public Line getTopHorizontal() {
        return topHorizontal;
    }

    /**
     * the function returns the bottom border of the rectangle.
     * @return - the line that represents the bottom border
     */
    public Line getBottomHorizontal() {
        return bottomHorizontal;
    }

    /**
     * the function returns the Left border of the rectangle.
     * @return - the line that represents the Left border
     */
    public Line getLeftVertical() {
        return leftVertical;
    }

    /**
     * the function returns the Right border of the rectangle.
     * @return - the line that represents the Right border
     */
    public Line getRightVertical() {
        return rightVertical;
    }


    /**
     * Return a (possibly empty) List of intersection points.
     * with the specified line.
     * @param line - line to check intersection points with
     * @return - returns a list of intersection points
     */
    public List<Point> intersectionPoints(Line line) {
        List<Point> intersectionPoints;
        intersectionPoints = new ArrayList<>();

        //checking for intersection
        if (line.intersectionWith(topHorizontal) != null) {
            intersectionPoints.add(line.intersectionWith(topHorizontal));
        }

        if (line.intersectionWith(bottomHorizontal) != null) {
            intersectionPoints.add(line.intersectionWith(bottomHorizontal));
        }
        if (line.intersectionWith(leftVertical) != null) {
            intersectionPoints.add(line.intersectionWith(leftVertical));
        }
        if (line.intersectionWith(rightVertical) != null) {
            intersectionPoints.add(line.intersectionWith(rightVertical));
        }
        return intersectionPoints;
    }

    /**
     * returns the width of the rectangle.
     * @return - the width
     */
    public double getWidth() {
        return this.width;
    }

    /**
     * returns the height of the rectangle.
     * @return - returns the rectangle's height
     */
    public double getHeight() {
        return this.height;
    }

    /**
     * Returns the upper-left point of the rectangle.
     * @return - returns the upper left point of the rectangle.
     */
    public Point getUpperLeft() {
        return this.topLeft;
    }

    /**
     * Finds and assignees points ot the rectangle from the width, height and upper left point.
     */
    public void makePoints() {
        this.bottomLeft = new Point(this.topLeft.getX(), this.topLeft.getY() + this.height);
        this.bottomRight = new Point(this.topLeft.getX() + this.width, this.topLeft.getY() + this.height);
        this.topRight = new Point(this.topLeft.getX() + this.width, this.topLeft.getY());
    }


    /**
     * Returns a list of the 4 points consisting the rectangle.
     * @return points, a list of the 4 points consisting the rectangle.
     */
    public List<Point> points() {
        List<Point> points;
        points = new ArrayList<>();
        points.add(topLeft);
        points.add(bottomRight);
        points.add(topRight);
        points.add(bottomLeft);
        return points;
    }
}
