package levels;

import gameobjects.Block;

public interface BlockCreator {

    /**
     * Creates a block at the given coordinates.
     * @param xpos - x coordinate.
     * @param ypos - y coordinate.
     * @return the newly created block.
     */
    Block create(int xpos, int ypos);
}
