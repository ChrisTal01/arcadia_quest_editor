package com.arcadia.editor.entities;

import java.io.File;

public class MapObject extends AQ_Object {

    private final static String MAIN_MAP_FOLDER_NAME = "map/";
    private final static String MAIN_MAP_NORMAL_NAME = "_Normal.png";

    private MapObject mCounterPart;
    private Tile[] mTiles = new Tile[9];

    private String identifier;

    public MapObject(String pPath, String pName, GameType pGameBox) {
        super(new File(pPath ,MAIN_MAP_FOLDER_NAME + pName + MAIN_MAP_NORMAL_NAME), pName.replace("_", " "), pGameBox);

        for (int i = 0; i < mTiles.length; i++) {
            mTiles[i] = new Tile(pPath, String.valueOf(i), pGameBox);
        }
        mCounterPart = null;
        this.identifier = pName.substring(pName.lastIndexOf("_"));
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

    public String getIdentifier(){
        return this.identifier;
    }

    public void copy(MapObject pMapObject) {
        Tile[] newTiles = new Tile[9];
        for (int i = 0; i < newTiles.length; i++) {
            newTiles[i] = new Tile(pMapObject.getTileAtPos(i));
        }
        this.setTiles(newTiles);
        // this.setCounterPart(new Map(pMap.getCounterPart()));
    }
}
