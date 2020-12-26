
import javax.swing.JPanel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Color;

public class MapPanel extends JPanel {

    private static int mSize = 399;

    private Tile[] mTiles = new Tile[9];
    private MapPanel[] mNeighbors = { null, null, null, null };

    private JPopupMenu mPopupMenu;
    private JMenuItem mRotateRight;
    private JMenuItem mRotateLeft;
    private JMenuItem mRotate180;
    private JMenuItem mFlip;

    private MapPanel mOppositeMapPanel;
    private MapListener mListener;
    private Map mMap;

    public MapPanel(MapListener pListener) {
        this.setLayout(null);
        mMap = null;
        mListener = pListener;
        this.addMouseListener(mListener);
        this.addMouseMotionListener(mListener);
        initComponents();
    }

    public MapPanel(Map pMap, MapListener pListener) {
        this.setLayout(null);
        mMap = pMap;
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

        mPopupMenu.add(mRotateRight);
        mPopupMenu.add(mRotateLeft);
        mPopupMenu.add(mRotate180);
        mPopupMenu.add(mFlip);
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
            System.out.println("Paint Map Panel");
            for (Tile t : mTiles) {
                t.paint(g);
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

    /**
     * ////////////////////////////////////////////////////////////////////////////////////////
     * Getter
     * ////////////////////////////////////////////////////////////////////////////////////////
     */

    public static int getSizes() {
        return mSize;
    }

    public Map getMap() {
        return mMap;
    }

    public MapPanel getNeighborAtPos(int pPos) {
        return mNeighbors[pPos];
    }

    /**
     * ////////////////////////////////////////////////////////////////////////////////////////
     * Setter
     * ////////////////////////////////////////////////////////////////////////////////////////
     */

    public void setMap(Map pMap) {
        mMap = pMap;
        int width = mSize / 3;
        for (int i = 0; i < mTiles.length; i++) {
            mTiles[i] = mMap.getTileAtPos(i);
        }

        // init Tile start locations
        int x = 0;
        int y = 0;
        System.out.println("int start locations");
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
            System.out.println("x = " + mTiles[i].getStartPosX() + "; y = " + mTiles[i].getStartPosY());
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

    public Tile getTileAtLocation(int pPosX, int pPosY) {
        if (mMap != null) {
            for (Tile t : mTiles) {
                if (pPosX >= t.getStartPosX() && pPosY >= t.getStartPosY() && pPosX <= t.getStartPosX() + t.getSize()
                        && pPosY <= t.getStartPosY() + t.getSize()) {
                    return t;
                }
            }
        }
        return null;
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

    public void setNeighbors() {

    }

    public void removeNeighbors() {

    }
}
