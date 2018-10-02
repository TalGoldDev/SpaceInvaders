package gameobjects;


import biuoop.DrawSurface;
import interfaces.Sprite;

import java.awt.Color;

public class Circle implements Sprite {

        //the variables.
        private int r;
        private int x;
        private int y;
        private Color color;
        private boolean drawFullCircle;


    /**
     * a function that checks if we want to print the circle filled with color.
     * @param fillInWithColor - a boolean.
     */
    public void setFillInWithColor(boolean fillInWithColor) {
        this.drawFullCircle = fillInWithColor;
    }

        /**
         * the circle constructor.
         * @param x - the x point
         * @param y - the y point.
         * @param r - the radius.
         * @param c - the circle color.
         */
        public Circle(int x, int y, int r, Color c) {
            this.x = x;
            this.y = y;
            this.r = r;
            this.color = c;
            this.drawFullCircle = false;
        }

        /**
         * The draw on function which draws the circle to the surface.
         * @param d the draw surface.
         */
        public void drawOn(DrawSurface d) {
            d.setColor(this.color);
            d.drawCircle(this.x, this.y, this.r);
            if (drawFullCircle) {
                d.fillCircle(this.x, this.y, this.r);
            }
        }

    /**
     * the timepassed method.
     * @param dt frames per second.
     */
    @Override
    public void timePassed(double dt) {

    }
}
