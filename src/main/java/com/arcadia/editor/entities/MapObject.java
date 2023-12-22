package com.arcadia.editor.entities;

import com.arcadia.editor.application.IRotatable;

import java.io.File;

public class MapObject extends AQ_Object implements IRotatable {

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

        initNeighbors();
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


    private void initNeighbors() {

        mTiles[0].setNeighborAtPos(mTiles[1], Tile.RIGHT);
        mTiles[0].setNeighborAtPos(mTiles[3], Tile.BOTTOM);
        // 1
        mTiles[1].setNeighborAtPos(mTiles[2], Tile.RIGHT);
        mTiles[1].setNeighborAtPos(mTiles[4], Tile.BOTTOM);
        mTiles[1].setNeighborAtPos(mTiles[0], Tile.LEFT);

        // 2
        mTiles[2].setNeighborAtPos(mTiles[5], Tile.BOTTOM);
        mTiles[2].setNeighborAtPos(mTiles[1], Tile.LEFT);

        // 3
        mTiles[3].setNeighborAtPos(mTiles[0], Tile.TOP);
        mTiles[3].setNeighborAtPos(mTiles[4], Tile.RIGHT);
        mTiles[3].setNeighborAtPos(mTiles[6], Tile.BOTTOM);

        // 4
        mTiles[4].setNeighborAtPos(mTiles[1], Tile.TOP);
        mTiles[4].setNeighborAtPos(mTiles[5], Tile.RIGHT);
        mTiles[4].setNeighborAtPos(mTiles[7], Tile.BOTTOM);
        mTiles[4].setNeighborAtPos(mTiles[3], Tile.LEFT);

        // 5
        mTiles[5].setNeighborAtPos(mTiles[2], Tile.TOP);
        mTiles[5].setNeighborAtPos(mTiles[8], Tile.BOTTOM);
        mTiles[5].setNeighborAtPos(mTiles[4], Tile.LEFT);

        // 6
        mTiles[6].setNeighborAtPos(mTiles[3], Tile.TOP);
        mTiles[6].setNeighborAtPos(mTiles[7], Tile.RIGHT);

        // 7
        mTiles[7].setNeighborAtPos(mTiles[4], Tile.TOP);
        mTiles[7].setNeighborAtPos(mTiles[8], Tile.RIGHT);
        mTiles[7].setNeighborAtPos(mTiles[6], Tile.LEFT);

        // 8
        mTiles[8].setNeighborAtPos(mTiles[5], Tile.TOP);
        mTiles[8].setNeighborAtPos(mTiles[7], Tile.LEFT);

    }

    public void drawTileMap(){
        System.out.println(mTiles[0].getTopDoorString() + mTiles[1].getTopDoorString()+ mTiles[2].getTopDoorString());
        System.out.println(mTiles[0].getMiddleDoorString(getStringPosOfTile(0)) + mTiles[1].getMiddleDoorString(getStringPosOfTile(1))+ mTiles[2].getMiddleDoorString(getStringPosOfTile(2)));
        System.out.println(mTiles[0].getBottomDoorString() + mTiles[1].getBottomDoorString()+ mTiles[2].getBottomDoorString());
        System.out.println();
        System.out.println(mTiles[3].getTopDoorString() + mTiles[4].getTopDoorString()+ mTiles[5].getTopDoorString());
        System.out.println(mTiles[3].getMiddleDoorString(getStringPosOfTile(3)) + mTiles[4].getMiddleDoorString(getStringPosOfTile(4))+ mTiles[5].getMiddleDoorString(getStringPosOfTile(5)));
        System.out.println(mTiles[3].getBottomDoorString() + mTiles[4].getBottomDoorString()+ mTiles[6].getBottomDoorString());
        System.out.println();
        System.out.println(mTiles[6].getTopDoorString() + mTiles[7].getTopDoorString()+ mTiles[8].getTopDoorString());
        System.out.println(mTiles[6].getMiddleDoorString(getStringPosOfTile(6)) + mTiles[7].getMiddleDoorString(getStringPosOfTile(7))+ mTiles[8].getMiddleDoorString(getStringPosOfTile(8)));
        System.out.println(mTiles[6].getBottomDoorString() + mTiles[7].getBottomDoorString()+ mTiles[8].getBottomDoorString());
    }

    private String getStringPosOfTile(int i){
        return switch (i) {
            case 0 -> "Top Left";
            case 1 -> "Top";
            case 2 -> "Top Right";
            case 3 -> "Middle Left";
            case 4 -> "Middle";
            case 5 -> "Middle Right";
            case 6 -> "Bottom Left";
            case 7 -> "Bottom";
            case 8 -> "Bottom Right";
            default -> "None";
        };
    }

    @Override
    public void rotateRight() {
        /**
         * [0][1][2] [6][3][0] [3][4][5] -> [7][4][1] [6][7][8] [8][5][2]
         *
         * pos0 => pos2 => pos8 => pos6 => pos0 || pos1 => pos5 => pos7 => pos3 => pos1
         */

        Tile[] newTiles = new Tile[9];
        if (mTiles[0] != null) {
            int[][] positions = new int[9][2];
            for (int i = 0; i < mTiles.length; i++) {
                positions[i][0] = mTiles[i].getStartPosX();
                positions[i][1] = mTiles[i].getStartPosY();
            }
            // corner
            newTiles[0] = mTiles[6];
            newTiles[6] = mTiles[8];
            newTiles[8] = mTiles[2];
            newTiles[2] = mTiles[0];
            // Center
            newTiles[4] = mTiles[4];
            // middle
            newTiles[1] = mTiles[3];
            newTiles[3] = mTiles[7];
            newTiles[7] = mTiles[5];
            newTiles[5] = mTiles[1];

            for (int i = 0; i < positions.length; i++) {
                newTiles[i].setStartPos(positions[i][0], positions[i][1]);
            }
            for (int i = 0; i < mTiles.length; i++) {
                mTiles[i] = newTiles[i];
                mTiles[i].rotateRight();
            }
        }
    }

    @Override
    public void rotateLeft() {
        /**
         *
         * pos0 <= pos2 <= pos8 <= pos6 <= pos0 || pos1 <= pos5 <= pos7 <= pos3 <= pos1
         */

        Tile[] newTiles = new Tile[9];
        if (mTiles[0] != null) {
            int[][] positions = new int[9][2];
            for (int i = 0; i < mTiles.length; i++) {
                positions[i][0] = mTiles[i].getStartPosX();
                positions[i][1] = mTiles[i].getStartPosY();
            }

            // corner
            newTiles[0] = mTiles[2];
            newTiles[2] = mTiles[8];
            newTiles[8] = mTiles[6];
            newTiles[6] = mTiles[0];
            // Center
            newTiles[4] = mTiles[4];
            // middle
            newTiles[3] = mTiles[1];
            newTiles[7] = mTiles[3];
            newTiles[5] = mTiles[7];
            newTiles[1] = mTiles[5];

            for (int i = 0; i < positions.length; i++) {
                newTiles[i].setStartPos(positions[i][0], positions[i][1]);
            }

            for (int i = 0; i < mTiles.length; i++) {
                mTiles[i] = newTiles[i];
                mTiles[i].rotateLeft();
            }
        }

    }

    @Override
    public void rotate180() {
        Tile[] newTiles = new Tile[9];
        if (mTiles[0] != null) {
            int[][] positions = new int[9][2];
            for (int i = 0; i < mTiles.length; i++) {
                positions[i][0] = mTiles[i].getStartPosX();
                positions[i][1] = mTiles[i].getStartPosY();
            }

            // corner
            newTiles[0] = mTiles[8];
            newTiles[8] = mTiles[0];
            newTiles[2] = mTiles[6];
            newTiles[6] = mTiles[2];
            // Center
            newTiles[4] = mTiles[4];
            // middle
            newTiles[1] = mTiles[7];
            newTiles[7] = mTiles[1];
            newTiles[3] = mTiles[5];
            newTiles[5] = mTiles[3];

            for (int i = 0; i < positions.length; i++) {
                newTiles[i].setStartPos(positions[i][0], positions[i][1]);
            }

            for (int i = 0; i < mTiles.length; i++) {
                mTiles[i] = newTiles[i];
                mTiles[i].rotate180();
            }
        }
    }

    public void removeConnection(int pos){
        if(pos == TOP){
            mTiles[6].removeNeighborAt(BOTTOM);
            mTiles[7].removeNeighborAt(BOTTOM);
            mTiles[8].removeNeighborAt(BOTTOM);
        }
        if(pos == RIGHT){
            mTiles[0].removeNeighborAt(LEFT);
            mTiles[3].removeNeighborAt(LEFT);
            mTiles[6].removeNeighborAt(LEFT);
        }
        if(pos == BOTTOM){
            mTiles[0].removeNeighborAt(TOP);
            mTiles[1].removeNeighborAt(TOP);
            mTiles[2].removeNeighborAt(TOP);
        }
        if(pos == LEFT){
            mTiles[2].removeNeighborAt(RIGHT);
            mTiles[5].removeNeighborAt(RIGHT);
            mTiles[8].removeNeighborAt(RIGHT);
        }
    }

    public void setConnection(MapObject map, int pos){
        if(pos == TOP){
            mTiles[0].setNeighborAtPos(map.getTileAtPos(6),TOP);
            mTiles[1].setNeighborAtPos(map.getTileAtPos(7),TOP);
            mTiles[2].setNeighborAtPos(map.getTileAtPos(8),TOP);
        }
        if(pos == RIGHT){
            mTiles[2].setNeighborAtPos(map.getTileAtPos(0),RIGHT);
            mTiles[5].setNeighborAtPos(map.getTileAtPos(3),RIGHT);
            mTiles[8].setNeighborAtPos(map.getTileAtPos(6),RIGHT);
        }
        if(pos == BOTTOM){
            mTiles[6].setNeighborAtPos(map.getTileAtPos(0),BOTTOM);
            mTiles[7].setNeighborAtPos(map.getTileAtPos(1),BOTTOM);
            mTiles[8].setNeighborAtPos(map.getTileAtPos(2),BOTTOM);
        }
        if(pos == LEFT){
            mTiles[0].setNeighborAtPos(map.getTileAtPos(2),LEFT);
            mTiles[3].setNeighborAtPos(map.getTileAtPos(5),LEFT);
            mTiles[6].setNeighborAtPos(map.getTileAtPos(8),LEFT);
        }
    }

}
