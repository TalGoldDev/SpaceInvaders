package levels;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class LevelSpecificationReader {

    /**
     * a function which reads the levels and returns a list of level informations.
     * @param reader - the reader used to read the file.
     * @return - a list of levelinformation.
     */
    public static List<LevelInformation> fromReader(java.io.Reader reader) {
        List<LevelInformation> levelInfoList = new
                LinkedList<LevelInformation>();
        BufferedReader lineReader = (BufferedReader) reader;

        String line = "";
        while (line != null) {
            List<String> tempStringList = new LinkedList<String>();
            List<String> tempString = new ArrayList<String>();
            HashMap<String, String> tempHashMap = new
                    HashMap<String, String>();
            if (line.equals("START_LEVEL")) {
                while (!line.equals("END_LEVEL")) {
                    try {
                        line = lineReader.readLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    tempString.add(line);
                }
                int count = 0;
                while (!tempString.get(count).equals("START_BLOCKS")) {
                    tempHashMap.put(tempString.get(count).split(":")[0],
                            tempString.get(count).split(":")[1]);
                    count++;
                }
                count++;
                while (!tempString.get(count).equals("END_BLOCKS")) {
                    tempStringList.add(tempString.get(count));
                    count++;
                }
                LevelInformation levelInfo = new HashMapLevel(tempHashMap,
                        tempStringList);
                levelInfoList.add(levelInfo);
            }
            try {
                line = lineReader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return levelInfoList;
    }

}
