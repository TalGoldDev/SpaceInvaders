package levels;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ColorsParser {

    /**
     * Parse color Definition and return the specified Color.
     * @param s - the string to be parsed.
     * @return  -the specified Color.
     */
    public static Color colorFromString(String s) {
        String colorString = s;

        String[] stringArr = s.split("[\\(||\\)]");

        if (stringArr[0].equals("color") && stringArr[1].equals("RGB")) {
            String[] col = stringArr[2].split(",");
            int x = Integer.parseInt(col[0]);
            int y = Integer.parseInt(col[1]);
            int z = Integer.parseInt(col[2]);
            Color c = new Color(x, y, z);
            return c;

        } else if (stringArr[0].equals("color")
                && !stringArr[1].equals("RGB")) {
            if (stringArr[1].equals("red")) {
                return Color.RED;
            } else if (stringArr[1].equals("black")) {
                return Color.BLACK;
            } else if (stringArr[1].equals("blue")) {
                return Color.BLUE;
            } else if (stringArr[1].equals("cyan")) {
                return Color.cyan;
            } else if (stringArr[1].equals("gray")) {
                return Color.GRAY;
            } else if (stringArr[1].equals("lightGray")) {
                return Color.lightGray;
            } else if (stringArr[1].equals("green")) {
                return Color.GREEN;
            } else if (stringArr[1].equals("orange")) {
                return Color.orange;
            } else if (stringArr[1].equals("pink")) {
                return Color.pink;
            } else if (stringArr[1].equals("white")) {
                return Color.white;
            } else if (stringArr[1].equals("yellow")) {
                return Color.YELLOW;
            }
        } else if (stringArr[0].equals("image")) {
            BufferedImage img = null;
            try {
                img = ImageIO.read(new File(stringArr[1]));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
