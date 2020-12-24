
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Gamebox {

    private final String mName;
    private final int mIndex;
    private final String mPath;

    private ArrayList<Map> mMaps;
    private ArrayList<Monster> mMonsters;
    private ArrayList<AQ_Object> mAqObjects;

    public static final int ARCADIA_QUEST = 1;
    public static final int INFERNO = 2;
    public static final int BEYOND_THE_GRAVE = 3;
    public static final int PETS = 4;
    public static final int WHOLE_LOTTA_LAVA = 5;
    public static final int RAIDERS = 6;
    public static final int FALL_OF_ARCADIA = 7;

    public Gamebox(String pPath, String pName, int pIndex) {
        mName = pName;
        mIndex = pIndex;
        mPath = pPath;

        // init Maps
        mMaps = new ArrayList<Map>();
        File mapFile = new File(mPath + "MapParts\\");
        File[] maps = mapFile.listFiles();
        for (File f : maps) {
            mMaps.add(new Map(mPath, f.getName().replace("Map", "Mapteil "), mIndex));
        }

        // init Monsters
        mMonsters = readMonsterCSV(mPath + "Monsters\\", "Monster.csv");

        // initObjects
        mAqObjects = readObjectCSV(mPath + "Tokens\\", "Token.csv");
    }

    private ArrayList<Monster> readMonsterCSV(String pFile, String pName) {
        ArrayList<Monster> newMonster = new ArrayList<Monster>();
        BufferedReader csvReader = null;
        try {
            csvReader = new BufferedReader(new FileReader(new File(pFile + pName)));
            String row;
            boolean skippedFirst = false;
            while ((row = csvReader.readLine()) != null) {
                if (skippedFirst) {
                    String[] data = row.split(";");
                    // (0)Game, (1)Name, (2)Picture, (3)Type, (4)Size, (5)Amount,(6) PrefIconWidht,
                    // (7) PrefIconHeight
                    newMonster.add(new Monster(pFile + data[2], data[1], Integer.parseInt(data[5]), mIndex,
                            Integer.parseInt(data[4]), data[3], Integer.parseInt(data[6]), Integer.parseInt(data[7])));
                } else {
                    skippedFirst = true;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (csvReader != null) {
                try {
                    csvReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return newMonster;
    }

    private ArrayList<AQ_Object> readObjectCSV(String pFile, String pName) {
        ArrayList<AQ_Object> newObject = new ArrayList<AQ_Object>();
        BufferedReader csvReader = null;
        try {
            csvReader = new BufferedReader(new FileReader(new File(pFile + pName)));
            String row;
            boolean skippedFirst = false;
            while ((row = csvReader.readLine()) != null) {
                if (skippedFirst) {
                    String[] data = row.split(";");
                    // (0)Game, (1)Name, (2)Picture,(3)Picture2, (4)Amount, (5)PrefIconWidht,(6)
                    // PrefIconHeight,
                    if (data[1].contains("Tür") || data[1].contains("Door")) {
                        // if name contains Tür or Door
                        newObject.add(new Door(pFile + data[2], pFile + data[3], data[1], Integer.parseInt(data[4]),
                                mIndex, Integer.parseInt(data[5]), Integer.parseInt(data[6])));
                    } else {
                        newObject.add(new AQ_Object(pFile + data[2], data[1], Integer.parseInt(data[4]), mIndex,
                                Integer.parseInt(data[5]), Integer.parseInt(data[6])));
                    }
                } else {
                    skippedFirst = true;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (csvReader != null) {
                try {
                    csvReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return newObject;
    }

    /**
     * ////////////////////////////////////////////////////////////////////////////////////////
     * Getter and Setter
     * ////////////////////////////////////////////////////////////////////////////////////////
     */

    public String getName() {
        return mName;
    }

    public int getIndex() {
        return mIndex;
    }

    public ArrayList<Monster> getMonsters() {
        return mMonsters;
    }

    public ArrayList<Map> getMaps() {
        return mMaps;
    }

    public ArrayList<AQ_Object> getAQ_Objects() {
        return mAqObjects;
    }
}
