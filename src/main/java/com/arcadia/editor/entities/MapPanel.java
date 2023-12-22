package com.arcadia.editor.entities;

import com.arcadia.editor.application.IRotatable;
import com.arcadia.editor.application.MapListener;

import javax.swing.JPanel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import java.awt.Graphics;

public class MapPanel extends JPanel implements IRotatable {

    private static final int mSize = 399; //399, 349

    private MapPanel[] mNeighbors = { null, null, null, null };

    private JPopupMenu mPopupMenu;
    private JMenuItem mRotateRight;
    private JMenuItem mRotateLeft;
    private JMenuItem mRotate180;
    private JMenuItem mFlip;
    private JMenuItem removeMap;

    private MapPanel mOppositeMapPanel;
    private MapListener mListener;
    private MapObject mMapObject;

    public MapPanel(MapListener pListener) {
        this.setLayout(null);
        mMapObject = null;
        mListener = pListener;
        this.addMouseListener(mListener);
        this.addMouseMotionListener(mListener);
        initComponents();
    }

    public MapPanel(MapObject pMapObject, MapListener pListener) {
        this.setLayout(null);
        mMapObject = pMapObject;
        mListener = pListener;
        this.addMouseListener(mListener);
        this.addMouseMotionListener(mListener);
        initComponents();
    }

    private void initComponents() {

        // PopUpMenu

        mPopupMenu = new JPopupMenu();

        mRotateRight = new JMenuItem("Rotate right");
        mRotateRight.addActionListener(e -> {
            rotateRight();
        });

        mRotateLeft = new JMenuItem("Rotate left");
        mRotateLeft.addActionListener(e -> {
            rotateLeft();
        });

        mRotate180 = new JMenuItem("Rotate 180");
        mRotate180.addActionListener(e -> {
            rotate180();
        });

        mFlip = new JMenuItem("Flip Map");
        mFlip.addActionListener(e -> {
            System.out.println("Not yet implemented.");
        });

        removeMap = new JMenuItem("Remove Map");
        removeMap.addActionListener(e -> {
            removeMap();
        });

        mPopupMenu.add(mRotateRight);
        mPopupMenu.add(mRotateLeft);
        mPopupMenu.add(mRotate180);
        mPopupMenu.add(mFlip);
        mPopupMenu.add(removeMap);
    }

    /**
     * ////////////////////////////////////////////////////////////////////////////////////////
     * Paint
     * ////////////////////////////////////////////////////////////////////////////////////////
     */

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // draw Background
        if(mMapObject == null){
            return;
        }
        Tile[] tiles = mMapObject.getTiles();

        if (tiles[0] != null) {

            // draw Background and outlines
            for (Tile t : tiles) {
                t.paintBackground(g);
            }
            // draw Doors
            for (Tile t : tiles) {
                t.paintDoors(g);
            }

            // draw Objects
            for (Tile t : tiles) {
                t.paintMonsters(g);
            }
        }
    }

    /**
     * ////////////////////////////////////////////////////////////////////////////////////////
     * Rotate methods
     * ////////////////////////////////////////////////////////////////////////////////////////
     */

    public void rotateRight() {
        mMapObject.rotateRight();
        update();

    }

    public void rotateLeft() {
        mMapObject.rotateLeft();
        update();
    }

    public void rotate180() {
        mMapObject.rotate180();
        update();
    }

    public void removeMap(){
        detachFromNeighbors();
        mMapObject = null;
        update();
    }

    private void detachFromNeighbors(){
        if(mNeighbors[TOP] != null && mNeighbors[TOP].getMap() != null){
            MapPanel panel = mNeighbors[TOP];
            panel.getMap().removeConnection(TOP);
        }
        if(mNeighbors[RIGHT] != null && mNeighbors[RIGHT].getMap() != null){
            MapPanel panel = mNeighbors[RIGHT];
            panel.getMap().removeConnection(RIGHT);
        }
        if(mNeighbors[BOTTOM] != null && mNeighbors[BOTTOM].getMap() != null){
            MapPanel panel = mNeighbors[BOTTOM];
            panel.getMap().removeConnection(BOTTOM);
        }
        if(mNeighbors[LEFT] != null && mNeighbors[LEFT].getMap() != null){
            MapPanel panel = mNeighbors[LEFT];
            panel.getMap().removeConnection(LEFT);
        }
    }

    /**
     * ////////////////////////////////////////////////////////////////////////////////////////
     * Getter
     * ////////////////////////////////////////////////////////////////////////////////////////
     */

    public static int getSizes() {
        return mSize;
    }

    public MapObject getMap() {
        return mMapObject;
    }

    public MapPanel getNeighborAtPos(int pPos) {
        return mNeighbors[pPos];
    }
    public MapObject getNeighborMapAtPos(int pPos) {
        if(mNeighbors[pPos] != null){
            return mNeighbors[pPos].getMap();
        }
        return null;
    }

    public boolean hasMap(){
        return mMapObject != null;
    }

    public boolean hasNeighbors(){

        return (mNeighbors[0] != null && mNeighbors[0].getMap() != null) ||
                (mNeighbors[1] != null && mNeighbors[1].getMap() != null) ||
                (mNeighbors[2] != null && mNeighbors[2].getMap() != null) ||
                (mNeighbors[3] != null && mNeighbors[3].getMap() != null);
    }

    /**
     * ////////////////////////////////////////////////////////////////////////////////////////
     * Setter
     * ////////////////////////////////////////////////////////////////////////////////////////
     */

    public void setMap(MapObject pMapObject) {
        mMapObject = pMapObject;
        int width = mSize / 3;

        // init Tile start locations
        int x = 0;
        int y = 0;
        for (int i = 0; i < mMapObject.getTiles().length; i++) {
            mMapObject.getTileAtPos(i).setStartPos(x, y);
            mMapObject.getTileAtPos(i).setSize(width);
            mMapObject.getTileAtPos(i).setMapPanel(this);
            if (i != 0 && (i + 1) % 3 == 0) {
                x = 0;
                y += width;
            } else {
                x += width;
            }
        }
        updateNeighbors();
        update();
    }

    public void setPopupMenuLocation(int pPosX, int pPosY) {
        mPopupMenu.setLocation(pPosX, pPosY);
    }

    public void setShowPopup(boolean pShow) {
        mPopupMenu.setVisible(pShow);
    }

    public void setNeighborAtPos(MapPanel pNeighbor, int pPos) {
        mNeighbors[pPos] = pNeighbor;
    }

    public void removeNeighborAtPos(int pPos) {
        mNeighbors[pPos] = null;
    }

    public void setShowDoorOutline(boolean pState) {
        if(mMapObject != null) {
            for (Tile t : mMapObject.getTiles()) {
                if(t != null){
                    t.setShowDoorOutline(pState);
                }
            }
        }
        update();
    }

    public Tile getTileAtLocation(int pPosX, int pPosY) {
        if (mMapObject != null) {
            Tile[] tiles = mMapObject.getTiles();
            for (Tile tile : tiles) {
                if (pPosX >= tile.getStartPosX() && pPosY >= tile.getStartPosY() && pPosX <= tile.getStartPosX() + tile.getSize()
                        && pPosY <= tile.getStartPosY() + tile.getSize()) {
                    return tile;
                }
            }
        }
        return null;
    }

    public void updateNeighbors() {
        // has no Neighbors
        if(!hasNeighbors()){
            return;
        }
        if(mNeighbors[TOP] != null && mNeighbors[TOP].getMap() != null){
            MapObject map = mNeighbors[TOP].getMap();
            mMapObject.setConnection(map,TOP);
            map.setConnection(mMapObject,BOTTOM);
        }
        if(mNeighbors[RIGHT] != null && mNeighbors[RIGHT].getMap() != null){
            MapObject map = mNeighbors[RIGHT].getMap();
            mMapObject.setConnection(map,RIGHT);
            map.setConnection(mMapObject,LEFT);
        }
        if(mNeighbors[BOTTOM] != null && mNeighbors[BOTTOM].getMap() != null){
            MapObject map = mNeighbors[BOTTOM].getMap();
            mMapObject.setConnection(map,BOTTOM);
            map.setConnection(mMapObject,TOP);
        }
        if(mNeighbors[LEFT] != null && mNeighbors[LEFT].getMap() != null){
            MapObject map = mNeighbors[LEFT].getMap();
            mMapObject.setConnection(map,LEFT);
            map.setConnection(mMapObject,RIGHT);
        }
        update();

    }

    public MapPanel[] getNeighbors(){
        return mNeighbors;
    }

    public void update(){
        revalidate();
        repaint();
    }
}
