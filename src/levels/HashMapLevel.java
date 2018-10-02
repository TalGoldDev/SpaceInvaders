package levels;

import gameobjects.Block;
import geometry.Velocity;
import interfaces.Sprite;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HashMapLevel implements LevelInformation {

    //the variables.
    private HashMap<String, String> hashMap;
    private List<Velocity> ballVelocities;
    private List<String> blocks;

    /**
     * the Constructor function.
     * @param hashMap  -  the HashMap.
     * @param blocksList - the list of Blocks of the level.
     */
    public HashMapLevel(HashMap<String, String> hashMap,
                        List<String> blocksList) {
        this.hashMap = hashMap;
        this.blocks = blocksList;
        this.ballVelocities = new ArrayList<Velocity>();
    }

    /**
     * a function which returns the amount of velocities = the amount of balls in the level.
     * @return - the amount of balls in the current level.
     */
    @Override
    public int numberOfBalls() {
        return this.hashMap.get("ball_velocities").split(" ").length;
    }

    /**
     * A function which returns list of all the ball velocities.
     * @return - a list of the ball velocities.
     */

    @Override
    public List<Velocity> initialBallVelocities() {
        String[] velocityArray = this.hashMap.get(
                "ball_velocities").split(" ");
        for (int i = 0; i < velocityArray.length; i++) {
            String[] velocity = velocityArray[i].split(",");
            Velocity currentVelocity = Velocity.
                    fromAngleAndSpeed(Integer.parseInt(velocity[0]),
                            Integer.parseInt(velocity[1]));
            ballVelocities.add(currentVelocity);
        }
        return ballVelocities;
    }

    /**
     * a function which returns the Paddle Speed.
     * @return - the paddle speed.
     */
    @Override
    public int paddleSpeed() {
        return Integer.parseInt(hashMap.get("paddle_speed"));
    }

    /**
     * a function which returns the paddles width for the specific level.
     * @return - the width of the paddle.
     */
    @Override
    public int paddleWidth() {
        return Integer.parseInt(hashMap.get("paddle_width"));
    }

    /**
     * a function which returns the name of the level.
     * @return - a string of the level name.
     */
    @Override
    public String levelName() {
        return hashMap.get("level_name");
    }

    /**
    * a function which returns the background of the level.
     * @return - the Level background.
     */
    @Override
    public Sprite getBackground() {
        return new ImageBackground(hashMap.get("background"));
    }

    /**
     * a function which returns a list of the blocks for the level.
     * @return - the list of blocks.
     */
    @Override
    public List<Block> blocks() {
        String blockFilePath = "block_definitions";
        int xPos = Integer.parseInt(hashMap.get("blocks_start_x"));
        int yPos = Integer.parseInt(hashMap.get("blocks_start_y"));
        List<Block> returnBlocks = new ArrayList<Block>();
        BlocksFromSymbolsFactory factory = null;
        try {
            factory = BlocksDefinitionReader.fromReader(new
                    BufferedReader(new InputStreamReader(ClassLoader.
                    getSystemClassLoader().getResourceAsStream(hashMap.
                    get("block_definitions")))));

        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < blocks.size(); i++) {
            char[] line = blocks.get(i).toCharArray();
            for (int j = 0; j < line.length; j++) {
                if (factory.isBlockSymbol(Character.toString(line[j]))) {
                    Block tempBlock = factory.getBlock(Character.
                            toString(line[j]), xPos, yPos);
                    returnBlocks.add(tempBlock);
                    xPos += tempBlock.getWidth();

                } else if (factory.isSpaceSymbol(Character.toString(line[j]))) {
                    xPos += factory.getSpaceWidth(Character.toString(line[j]));
                }
            }
            yPos += Integer.parseInt(hashMap.get("row_height"));
            xPos = Integer.parseInt(hashMap.get("blocks_start_x"));
        }
        return returnBlocks;
    }

    /**
     * a function which returns the amount of blocks needed to finish the level.
     * @return - the amount of removable blocks.
     */
    @Override
    public int numberOfBlocksToRemove() {
        return Integer.parseInt(hashMap.get("num_blocks"));
    }
}