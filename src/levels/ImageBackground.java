package levels;

import biuoop.DrawSurface;
import interfaces.Sprite;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.IOException;

public class ImageBackground implements Sprite {

    //the variables
    private String str;


    /**
     * the Constructor function of the class.
     * @param s - the String.
     */
    public ImageBackground(String s) {
        this.str = s;
    }

    /**
     * the method which calls the drawon of the lame to the drawing surface.
     * @param d - the drawing surface.
     */
    @Override
    public void drawOn(DrawSurface d) {
        draw(str, d);
    }


    /**
     * the time passed method.
     * @param dt - the time passed since last frame.
     */
    @Override
    public void timePassed(double dt) {
    }

    /**
     * the drawing function which draws the image on the surface itself.
     * @param s - the String of the image.
     * @param d - the surface to be drawn.
     */
    private void draw(String s, DrawSurface d) {
        String [] imageBitsArray  = s.split("\\(");
        if (imageBitsArray[0].equals("image")) {
            Image image = null;
            try {

                String stringToImage = imageBitsArray[1].substring(
                        0, imageBitsArray[1].length() - 1);
                image = ImageIO.read(ClassLoader.getSystemClassLoader().
                        getResourceAsStream(stringToImage));
            } catch (IOException e) {
                e.printStackTrace();
            }
            d.drawImage(0, 0, image);
        } else {
            d.setColor(ColorsParser.colorFromString(s));
            d.fillRectangle(0, 0, 800, 600);
        }
    }

}
