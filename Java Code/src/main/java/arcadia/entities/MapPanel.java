package src.main.java.arcadia.entities;

import src.main.java.arcadia.application.MapListener;

import javax.swing.JPanel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import java.awt.Graphics;

public class MapPanel extends JPanel {

    private static final int mSize = 399; //399, 349

    private Tile[] mTiles = new Tile[9];
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
        /*
        mPopupMenu.add(mFlip);
        mPopupMenu.add(removeMap);
        */
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
        if (mTiles[0] != null) {
            // draw Background and outlines
            for (Tile t : mTiles) {
                t.paintBackground(g);
            }
            // draw Doors
            for (Tile t : mTiles) {
                t.paintDoors(g);
            }

            // draw Objects
            for (Tile t : mTiles) {
                t.paintMonsters(g);
            }
        }
    }

    public void removeTiles() {
        this.removeAll();
        this.revalidate();
        this.repaint();
    }

    /**
     * ////////////////////////////////////////////////////////////////////////////////////////
     * Rotate methods
     * ////////////////////////////////////////////////////////////////////////////////////////
     */

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
        revalidate();
        repaint();

    }

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
        revalidate();
        repaint();
    }

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
        revalidate();
        repaint();
    }

    public void removeMap(){
        mMapObject = null;
        removeTiles();
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

    /**
     * ////////////////////////////////////////////////////////////////////////////////////////
     * Setter
     * ////////////////////////////////////////////////////////////////////////////////////////
     */

    public void setMap(MapObject pMapObject) {
        mMapObject = pMapObject;
        int width = mSize / 3;
        for (int i = 0; i < mTiles.length; i++) {
            mTiles[i] = mMapObject.getTileAtPos(i);
        }

        // init Tile start locations
        int x = 0;
        int y = 0;
        for (int i = 0; i < mTiles.length; i++) {
            mTiles[i].setStartPos(x, y);
            mTiles[i].setSize(width);
            mTiles[i].setMapPanel(this);
            if (i != 0 && (i + 1) % 3 == 0) {
                x = 0;
                y += width;
            } else {
                x += width;
            }
        }
        initNeighbors();
        revalidate();
        repaint();
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
        for (Tile t : mTiles) {
            t.setShowDoorOutline(pState);
        }
        revalidate();
        repaint();
    }

    public Tile getTileAtLocation(int pPosX, int pPosY) {
        if (mMapObject != null) {
            for (int i = 0; i < mTiles.length; i++) {
                if (pPosX >= mTiles[i].getStartPosX() && pPosY >= mTiles[i].getStartPosY() && pPosX <= mTiles[i].getStartPosX() + mTiles[i].getSize()
                        && pPosY <= mTiles[i].getStartPosY() + mTiles[i].getSize()) {
                    System.out.println(getStringPosOfTile(i));
                    drawTileMap();
                    return mTiles[i];
                }
            }
        }
        return null;
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

    private void drawTileMap(){
        System.out.println(mTiles[0].getTopDoorString() + mTiles[1].getTopDoorString()+ mTiles[2].getTopDoorString());
        System.out.println(mTiles[0].getMiddleDoorString(getStringPosOfTile(0)) + mTiles[1].getMiddleDoorString(getStringPosOfTile(1))+ mTiles[2].getMiddleDoorString(getStringPosOfTile(2)));
        System.out.println(mTiles[0].getBottomDoorString() + mTiles[1].getBottomDoorString()+ mTiles[2].getTopDoorString());
        System.out.println();
        System.out.println(mTiles[3].getTopDoorString() + mTiles[4].getTopDoorString()+ mTiles[5].getTopDoorString());
        System.out.println(mTiles[3].getMiddleDoorString(getStringPosOfTile(3)) + mTiles[4].getMiddleDoorString(getStringPosOfTile(4))+ mTiles[5].getMiddleDoorString(getStringPosOfTile(5)));
        System.out.println(mTiles[3].getBottomDoorString() + mTiles[4].getBottomDoorString()+ mTiles[6].getTopDoorString());
        System.out.println();
        System.out.println(mTiles[6].getTopDoorString() + mTiles[7].getTopDoorString()+ mTiles[8].getTopDoorString());
        System.out.println(mTiles[6].getMiddleDoorString(getStringPosOfTile(6)) + mTiles[7].getMiddleDoorString(getStringPosOfTile(7))+ mTiles[8].getMiddleDoorString(getStringPosOfTile(8)));
        System.out.println(mTiles[6].getBottomDoorString() + mTiles[7].getBottomDoorString()+ mTiles[8].getTopDoorString());
    }

    public void setNeighbors() {

    }

    public void removeNeighbors() {

    }
}
