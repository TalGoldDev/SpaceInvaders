package levels;

import gameobjects.Block;
import geometry.Point;
import geometry.Rectangle;

import java.awt.Color;
import java.util.HashMap;

public class BlockFactory implements BlockCreator {
    private int height;
    private int width;
    private int hits;
    private String fill;
    private Color strokeColor;
    private HashMap<Integer, String> hashFill;

    /**
     * Constructor method.
     *
     * @param height the height.
     * @param width the width.
     * @param hits the amount of hit points.
     * @param fill what to fill.
     * @param s the Color.
     * @param h the HashMap.
     */
    public BlockFactory(int height, int width, int hits, String fill,
                        Color s, HashMap<Integer, String> h) {
        this.height = height;
        this.width = width;
        this.hits = hits;
        this.fill = fill;
        this.strokeColor = s;
        this.hashFill = h;
    }

    /**
     * te function which creates the blocks.
     * @param xpos - the x coordinate.
     * @param ypos - the y coordinate.
     * @return the newly created block.
     */
    public Block create(int xpos, int ypos) {
        Block block = new Block(new Rectangle(new Point(xpos, ypos),
                width, height), fill, hits, strokeColor, hashFill);
        return block;
    }
}