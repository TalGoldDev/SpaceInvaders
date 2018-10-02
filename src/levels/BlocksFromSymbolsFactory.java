package levels;

import gameobjects.Block;

import java.util.HashMap;
import java.util.Map;

public class BlocksFromSymbolsFactory {
    private Map<String, Integer> spacerWidths;
    private Map<String, BlockCreator> blockCreators;

    /**
     * the constructor function of the class.
     */
    public BlocksFromSymbolsFactory() {
        this.spacerWidths = new HashMap<String, Integer>();
        this.blockCreators = new HashMap<String, BlockCreator>();
    }

    /**
     * the function adds the block creator.
     * @param s - a string.
     * @param b - the Block creator.
     */
    public void addBlockCreator(String s, BlockCreator b) {
        blockCreators.put(s, b);
    }

    /**
     * the function adds the spacer.
     * @param s - the string.
     * @param i - the size of the spaces.
     */
    public void addSpacer(String s, Integer i) {
        spacerWidths.put(s, i);
    }

    /**
     * a function which returns true if 's' is a valid space symbol.
     * @param s - a string.
     * @return - true or false.
     */
    public boolean isSpaceSymbol(String s) {
        return spacerWidths.containsKey(s);
    }

    /**
     * a function which returns true if 's' is a valid block symbol.
     *
     * @param s - a string.
     * @return - the Block creator.
     */
    public boolean isBlockSymbol(String s) {
        return blockCreators.containsKey(s);
    }

    /**
     * Return a block according to the definitions associated.
     * with symbol s. The block will be located at position (xpos, ypos).
     * @param s - the string.
     * @param xpos - the x - coordinate.
     * @param ypos - the y - coordinate.
     * @return - the block.
     */
    public Block getBlock(String s, int xpos, int ypos) {
        return blockCreators.get(s).create(xpos, ypos);
    }


    /**
     * Returns the width in pixels associated with the given spacer-symbol.
     * @param s - the string.
     * @return - the spacer Width.
     */
    public int getSpaceWidth(String s) {
        return spacerWidths.get(s);
    }
}
