package geometry;

import biuoop.DrawSurface;
import interfaces.Sprite;

import java.awt.Color;
import java.awt.geom.Line2D;
import java.util.List;

public class Line implements Sprite {

    private Point start;
    private Point end;
    private Color color;

    // constructors

    /**
     * Instantiate Line.
     * <p>
     *
     * @param start start point.
     * @param end   end point.
     */
    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    /**
     * Instantiate Line.
     * <p>
     *
     * @param x1 start point x location.
     * @param y1 start point y location.
     * @param x2 end point x location.
     * @param y2 end point y location.
     */
    public Line(double x1, double y1, double x2, double y2) {
        this.start = new Point(x1, y1);
        this.end = new Point(x2, y2);
    }

    /**
     * returning the starting point of the line.
     *
     * @return - the starting point of the line
     */
    public Point start() {
        return start;
    }

    /**
     * calculates and returns the middle point of the line.
     *
     * @return the middle point of the line.
     */
    public Point middle() {
        return new Point((this.start.getX() + this.end.getX()) / 2, (this.start.getY() + this.end.getY()) / 2);
    }


    /**
     * returing the ending point of the line.
     *
     * @return the end point of the line.
     */
    public Point end() {
        return this.end;
    }

    // methods

    /**
     * the method checks if the line is vertical.
     * @return - a boolean.
     */
    public boolean isVertical() {
        if (this.end.getX() - this.start.getX() == 0) {
            return true;
        }
        return false;
    }

    /**
     * Returns the intersection point if the lines intersect, and null otherwise.
     *
     * @param other - the other line we want to check the intersection with
     * @return - the point of intersection if there is one.
     */
    public Point intersectionWith(Line other) {
        double x, y, slope1, slope2, interception1, interception2;

        slope1 = this.slope();
        slope2 = other.slope();

        // parallel Lines
        if (this.slope() == other.slope()) {
            return null;
        }

        // if one of the lines is vertical
        if (this.isVertical()) {
            return this.verticalIntersection(other);
        }

        if (other.isVertical()) {
            return other.verticalIntersection(this);
        }

        // find interceptions with the y axis
        interception1 = this.start.getY() - (slope1 * this.start.getX());
        interception2 = other.start.getY() - (slope2 * other.start.getX());

        // find intersection point values
        x = (interception2 - interception1) / (slope1 - slope2);
        y = (slope1 * x) + interception1;

        // checking if point is on both lines.
        if ((x >= this.start.getX() && x <= this.end.getX())
                || (x <= this.start.getX() && x >= this.end.getX())) {
            if ((x >= other.start.getX() && x <= other.end.getX())
                    || (x <= other.start.getX() && x >= other.end.getX())) {
                return new Point(x, y);
            }
        }
        return null;
    }

    /**
     * checking if there is a vertical intersection between both lines.
     * @param other - the other line.
     * @return - point of intersection if there is one.
     */
    public Point verticalIntersection(Line other) {
        double x, y, slope, interceptionPoint;

        x = this.start.getX();
        slope = other.slope();
        interceptionPoint = other.start.getY() - slope * other.start.getX();
        y = slope * x + interceptionPoint;

        if ((y >= this.start.getY() && y <= this.end.getY())
                || (y <= this.start.getY() && y >= this.end.getY())) {
            if (y >= other.start.getY() && y <= other.end.getY()
                    || y <= other.start.getY() && y >= other.end.getY()) {
                return new Point(x, y);
            }
        }
        return null;
    }


    /**
     * checks if this line is intersecting with the other line.
     *
     * @param other - the other line.
     * @return true if the lines intersect, false otherwise.
     */
    public boolean isIntersecting(Line other) {
        Point start1 = this.start;
        Point end1 = this.end;
        Point start2 = other.start();
        Point end2 = other.end();
        // First find Ax+By=C values for the two lines
        double a1 = end1.getY() - start1.getY();
        double b1 = start1.getX() - end1.getX();
        double c1 = a1 * start1.getX() + b1 * start1.getY();

        double a2 = end2.getY() - start2.getY();
        double b2 = start2.getX() - end2.getX();
        double c2 = a2 * start2.getX() + b2 * start2.getY();

        double det = (a1 * b2) - (a2 * b1);

        if (det == 0) {
            // Lines are either parallel, are collinear (the exact same
            // segment), or are overlapping partially, but not fully
            // To see what the case is, check if the endpoints of one line
            // correctly satisfy the equation of the other (meaning the two
            // lines have the same y-intercept).
            // If no endpoints on 2nd line can be found on 1st, they are
            // parallel.
            // If any can be found, they are either the same segment,
            // overlapping, or two segments of the same line, separated by some
            // distance.
            // Remember that we know they share a slope, so there are no other
            // possibilities

            // Check if the segments lie on the same line
            // (No need to check both points)
            if ((a1 * start2.getX()) + (b1 * start2.getY()) == c1) {
                // They are on the same line, check if they are in the same
                // space
                // We only need to check one axis - the other will follow
                if ((Math.min(start1.getX(), end1.getX()) < start2.getX()) && (Math.max(start1.getX(),
                        end1.getX()) > start2.getX())) {
                    return true;
                }

                // One end point is ok, now check the other
                if ((Math.min(start1.getX(), end1.getX()) < end2.getX()) && (Math.max(start1.getX(),
                        end1.getX()) > end2.getX())) {
                    return true;
                }

                // They are on the same line, but there is distance between them
                return false;
            }

            // They are simply parallel
            return false;
        } else {
            // Lines DO intersect somewhere, but do the line segments intersect?
            double x = (b2 * c1 - b1 * c2) / det;
            double y = (a1 * c2 - a2 * c1) / det;

            // Make sure that the intersection is within the bounding box of
            // both segments
            if ((x >= Math.min(start1.getX(), end1.getX()) && x <= Math.max(start1.getX(), end1.getX()))
                    && (y >= Math.min(start1.getY(), end1.getY()) && y <= Math.max(start1.getY(), end1.getY()))) {
                // We are within the bounding box of the first line segment,
                // so now check second line segment
                if ((x >= Math.min(start2.getX(), end2.getX()) && x <= Math.max(start2.getX(), end2.getX()))
                        && (y >= Math.min(start2.getY(), end2.getY()) && y <= Math.max(start2.getY(), end2.getY()))) {
                    // The line segments do intersect
                    return true;
                }
            }

            // The lines do intersect, but the line segments do not
            return false;
        }
    }

    /**
     * Returns the closest point of intersection to the given object.
     * if there are no intersection, returns null
     * @param rect the object
     * @return the closest point of intersection
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        List intersectionPoints = rect.intersectionPoints(this);
        double bestLength, currentLength;

        if (intersectionPoints.isEmpty()) {
            return null;
        }

        Point closestIntersection = (Point) intersectionPoints.get(0);
        Line closestLine = new Line(start, closestIntersection);

        bestLength = closestLine.length();

        Point currentPoint;
        Line currentLine;
        for (int i = 0; i < intersectionPoints.size(); i++) {
            currentPoint = (Point) intersectionPoints.get(i);
            currentLine = new Line(start, currentPoint);
            currentLength = currentLine.length();
            if (currentLength < bestLength) {
                bestLength = currentLength;
                closestIntersection = currentPoint;
            }

        }
        return closestIntersection;



    }

    /**
     * Return the length of the line.
     *
     * @return the length of the line.
     */
    public double length() {
        double distance = start.distance(end);
        return distance;
    }



    /**
     * Calculate line's slope.
     * @return line's slope.
     */
    public double slope() {
        return (this.end.getY() - this.start.getY()) / (this.end.getX() - this.start.getX());
    }

    @Override
    /**
     * The draw on method.
     */
    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        d.drawLine((int) this.start.getX(), (int) this.start.getY(), (int) this.end.getX(), (int) this.end.getY());
    }

    /**
     * The time passed method.
     * @param dt - the time passed since last frame.
     */
    @Override
    public void timePassed(double dt) {

    }

    /**
     * the set color function which sets the color of the line.
     * @param newColor - the color.
     */
    public void setColor(Color newColor) {
        this.color = newColor;
    }


    /**
     * This Method will check if the points are on the line.
     *
     * @param line - Gets the line to be checked.
     * @param point - Gets the Point to be checked.
     * @return true if the point is on the line
     * and false otherwise.
     */
    public static boolean checkPointOnLine(final Line line,
                                           final Point point) {
        double x1 = line.start().getX();
        double x2 = line.end().getX();
        double y1 = line.start().getY();
        double y2 = line.end().getY();

        if (Line2D.ptSegDist(x1, y1, x2, y2,
                point.getX(), point.getY()) < 0.001) {
            return true;
        }
        return false;
    }

}