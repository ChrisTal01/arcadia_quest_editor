package src.main.java.arcadia.entities;

import java.io.File;

public class MapObject extends AQ_Object {

    private static final String IMAGE_PATH = "MapParts/Map";
    private MapObject mCounterPart;
    private Tile[] mTiles = new Tile[9];

    public MapObject(String pPath, String pName, GameType pGameBox) {
        super(new File(pPath ,IMAGE_PATH + pName.substring(pName.lastIndexOf(" ") + 1, pName.length()) + "\\map\\"
                + pName.replace(" ", "_") + "_Normal.png"), pName, pGameBox);

        for (int i = 0; i < mTiles.length; i++) {
            mTiles[i] = new Tile(new File(pPath , IMAGE_PATH + pName.substring(pName.lastIndexOf(" ") + 1, pName.length())).getPath(),
                    String.valueOf(i), pGameBox);
        }
        mCounterPart = null;
    }

    public MapObject(MapObject pMapObject) {
        super(pMapObject.getImagePath(), pMapObject.getName(), pMapObject.getGameBox());
        copy(pMapObject);
    }

    /**
     * ////////////////////////////////////////////////////////////////////////////////////////
     * Getter and Setter
     * ////////////////////////////////////////////////////////////////////////////////////////
     */

    public MapObject getCounterPart() {
        return mCounterPart;
    }

    public void setCounterPart(MapObject pMapObject) {
        mCounterPart = pMapObject;
    }

    public Tile[] getTiles() {
        return mTiles;
    }

    public Tile getTileAtPos(int pPos) {
        return mTiles[pPos];
    }

    public void setTiles(Tile[] pTiles) {
        mTiles = pTiles;
    }

    public void copy(MapObject pMapObject) {
        Tile[] newTiles = new Tile[9];
        for (int i = 0; i < newTiles.length; i++) {
            newTiles[i] = new Tile(pMapObject.getTileAtPos(i));
        }
        this.setTiles(newTiles);
        // this.setCounterPart(new src.main.java.arcadia.entities.Map(pMap.getCounterPart()));
    }
}
