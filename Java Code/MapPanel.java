
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

        // Tiles

        // set Neighbors
        initNeighbors();

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

    public void removeTilePanels() {
        this.removeAll();
        this.revalidate();
        this.repaint();
    }

    public void rotateRight() {
        /**
         * [0][1][2] [6][3][0] [3][4][5] -> [7][4][1] [6][7][8] [8][5][2]
         * 
         * pos0 => pos2 => pos8 => pos6 => pos0 || pos1 => pos5 => pos7 => pos3 => pos1
         */
    }

    public void rotate180() {

    }

    public void rotateLeft() {

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

    }

    public void setNeighbors() {

    }

    public void removeNeighbors() {

    }
}
