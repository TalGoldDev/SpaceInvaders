package levels;

public class SetLevel {
    private String key;
    private String title;
    private String levelPath;

    /**
     * a function which sets the key of the file.
     * @param k - the set key.
     */
    public void setKey(String k) {
        this.key = k;
    }

    /**
     * a function which sets the title.
     * @param t - the set tile.
     */
    public void setTitle(String t) {
        this.title = t;
    }

    /**
    * a function which sets the path to read the level.
     * @param path - a string with the level path.
     */
    public void setLevelPath(String path) {
        this.levelPath = path;
    }

    /**
     * a function which thats the string key.
     * @return - a string containing the key.
     */
    public String getKey() {
        return this.key;
    }

    /**
     * a function which returns the title of the level.
     * @return - a string containing the level title.
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * a function which returns a string containing the level path.
     * @return - a string containing the levelPath.
     */
    public String getLevelPath() {
        return this.levelPath;
    }
}