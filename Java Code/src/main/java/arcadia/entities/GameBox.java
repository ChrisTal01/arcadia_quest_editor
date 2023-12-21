package src.main.java.arcadia.entities;

import src.main.java.arcadia.util.CsvReader;

import java.io.File;
import java.util.*;

public class GameBox {

    private static final String DATA_FOLDER = "/ArcadiaQuestData/";

    private final String mName;
    private final GameType mGameType;
    private final String mPath;

    private final List<MapObject> mMapObjects;

    private final Map<Monster,Integer> aqMonsterAmount;

    private final Map<AQ_Object,Integer> aqObjectAmount;

    public GameBox(String pPath, String pName, GameType pGameType) {
        mName = pName;
        mGameType = pGameType;
        mPath = pPath;

        // init Maps
        mMapObjects = new ArrayList<>();
        File mapFile = new File(mPath,"MapParts");
        if(mapFile.exists()){
            File[] maps = mapFile.listFiles();
            for (File f : maps) {
                mMapObjects.add(new MapObject(mPath, f.getName().replace("Map", "Mapteil "), mGameType));
            }
        }

        // init Monsters
        aqMonsterAmount = CsvReader.readMonsterCSV(new File(mPath ,"Monsters"), "Monster.csv",mGameType);

        // initObjects
        aqObjectAmount = CsvReader.readObjectCSV(new File(mPath ,"Tokens"), "Token.csv",mGameType);
    }

    public GameBox(String pPath, GameType pGameType) {
        mGameType = pGameType;
        mPath = new File(pPath + DATA_FOLDER + mGameType.getFolderName()).getAbsolutePath();
        mName = mGameType.getName();
        // init Maps
        mMapObjects = new ArrayList<>();
        File mapFile = new File(mPath,  "MapParts");
        if(mapFile.exists()){
            File[] maps = mapFile.listFiles();
            for (File f : maps) {
                mMapObjects.add(new MapObject(mPath, f.getName().replace("Map", "Mapteil "), mGameType));
            }
        }

        // init Monsters
        aqMonsterAmount = CsvReader.readMonsterCSV(new File(mPath ,"Monsters"), "Monster.csv",mGameType);

        // initObjects
        aqObjectAmount = CsvReader.readObjectCSV(new File(mPath ,"Tokens"), "Token.csv",mGameType);
    }

    /**
     * ////////////////////////////////////////////////////////////////////////////////////////
     * Getter and Setter
     * ////////////////////////////////////////////////////////////////////////////////////////
     */

    public String getName() {
        return mName;
    }

    public GameType getmGameType() {
        return mGameType;
    }

    public List<Monster> getMonsters() {
        return aqMonsterAmount.keySet().stream().toList();
    }

    public int getMonsterAmount(Monster monster) {
        return aqMonsterAmount.get(monster);
    }

    public List<MapObject> getMaps() {
        return mMapObjects;
    }

    public List<AQ_Object> getAQ_Objects() {
        return aqObjectAmount.keySet().stream().toList();
    }
    public int getAqObjectAmount(AQ_Object object) {
        return aqObjectAmount.get(object);
    }
}
