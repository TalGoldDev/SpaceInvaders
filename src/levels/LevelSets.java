package levels;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class LevelSets {

    //the variables.
    private List<SetLevel> setLevelList;

    /**
     * the constructor function of the class.
     */
    public LevelSets() {
        this.setLevelList = new ArrayList<SetLevel>();
    }

    /**
     * a function which reads the levelsets from a reader.
     * @param reader - the read which is used to read the file.
     * @return - the LevelSets.
     */
    public static LevelSets fromReader(Reader reader) {
        LevelSets set = new LevelSets();

        SetLevel level = null;
        LineNumberReader lineNumberReader = null;
        String line = "";
        try {
            lineNumberReader = new LineNumberReader(reader);

            while ((line = lineNumberReader.readLine()) != null) {
                if (lineNumberReader.getLineNumber() % 2 == 0) {
                    level.setLevelPath(line.trim());
                    set.addSetLevel(level);
                    level = null;
                } else {
                    level = new SetLevel();
                    String[] linePieces = line.trim().split(":");
                    level.setKey(linePieces[0]);
                    level.setTitle(linePieces[1]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (lineNumberReader != null) {
                try {
                    lineNumberReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return set;
    }

    /**
     * a function which Adds the levels to the LevelSet.
     * @param setLevel - the level to set.
     */
    public void addSetLevel(SetLevel setLevel) {
        this.setLevelList.add(setLevel);
    }


    /**
     * a function to get the SetLevel list.
     * @return - the setlevel List.
     */
    public List<SetLevel> getSetLevelList() {
        return this.setLevelList;
    }
}