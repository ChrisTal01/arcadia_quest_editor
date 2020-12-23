package Editor;

import javax.swing.JPanel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import java.awt.Graphics;
import java.awt.Graphics2D;

public class MapPanel extends JPanel {

    private static int mSize = 400;

    private TilePanel[] mTilePanels = new TilePanel[9];
    private boolean mEntered;
    private boolean mPressed;
    private boolean mShiftPressed;

    private JPopupMenu mPopupMenu;
    private JMenuItem mRotateRight;
    private JMenuItem mRotateLeft;
    private JMenuItem mRotate180;
    private JMenuItem mFlip;

    private MapPanel mOppositeMapPanel;

    private TilePanel mSelectedTilePanel;

    private MapListener mListener;
    private Map mMap;

    public MapPanel(MapListener pListener) {
        this.setLayout(null);
        mEntered = false;
        mPressed = false;
        mShiftPressed = false;
        mSelectedTilePanel = null;
        mListener = pListener;
        this.addMouseListener(mListener);
        this.addMouseMotionListener(mListener);
        initComponents();
    }

    public MapPanel(Map pMap, MapListener pListener) {
        this.setLayout(null);
        mMap = pMap;
        mEntered = false;
        mPressed = false;
        mShiftPressed = false;
        mSelectedTilePanel = null;
        mListener = pListener;
        this.addMouseListener(mListener);
        this.addMouseMotionListener(mListener);
        initComponents();
    }

    private void initComponents() {

        // TilePanels
        for (int i = 0; i < mTilePanels.length; i++) {
            mTilePanels[i] = new TilePanel();
            int x = i % 3;
            int y = i / 3;

            mTilePanels[i].setBounds(x * (int) getPreferredSize().getWidth() / 3,
                    y * (int) getPreferredSize().getWidth() / 3, (int) getPreferredSize().getWidth(),
                    (int) getPreferredSize().getHeight());

            mTilePanels[i].addMouseListener(mListener);
            mTilePanels[i].addMouseMotionListener(mListener);
            this.add(mTilePanels[i]);
        }

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
        Tile[] newTiles = new Tile[9];
        if (mTilePanels != null && mTilePanels[0].geTile() != null) {

            // corner
            newTiles[0] = mTilePanels[6].geTile();
            newTiles[6] = mTilePanels[8].geTile();
            newTiles[8] = mTilePanels[2].geTile();
            newTiles[2] = mTilePanels[0].geTile();
            // Center
            newTiles[4] = mTilePanels[4].geTile();
            // middle
            newTiles[1] = mTilePanels[3].geTile();
            newTiles[3] = mTilePanels[7].geTile();
            newTiles[7] = mTilePanels[5].geTile();
            newTiles[5] = mTilePanels[1].geTile();

            for (int i = 0; i < mTilePanels.length; i++) {
                mTilePanels[i].setTile(newTiles[i]);
                mTilePanels[i].rotateRight();
            }
        }
    }

    public void rotate180() {

        Tile[] newTiles = new Tile[9];
        if (mTilePanels != null && mTilePanels[0].geTile() != null) {

            // corner
            newTiles[0] = mTilePanels[8].geTile();
            newTiles[8] = mTilePanels[0].geTile();
            newTiles[2] = mTilePanels[6].geTile();
            newTiles[6] = mTilePanels[2].geTile();
            // Center
            newTiles[4] = mTilePanels[4].geTile();
            // middle
            newTiles[1] = mTilePanels[7].geTile();
            newTiles[7] = mTilePanels[1].geTile();
            newTiles[3] = mTilePanels[5].geTile();
            newTiles[5] = mTilePanels[3].geTile();

            for (int i = 0; i < mTilePanels.length; i++) {
                mTilePanels[i].setTile(newTiles[i]);
                mTilePanels[i].rotate180();
            }
        }
    }

    public void rotateLeft() {
        /**
         * 
         * pos0 <= pos2 <= pos8 <= pos6 <= pos0 || pos1 <= pos5 <= pos7 <= pos3 <= pos1
         */

        Tile[] newTiles = new Tile[9];
        if (mTilePanels != null && mTilePanels[0].geTile() != null) {

            // corner
            newTiles[0] = mTilePanels[2].geTile();
            newTiles[2] = mTilePanels[8].geTile();
            newTiles[8] = mTilePanels[6].geTile();
            newTiles[6] = mTilePanels[0].geTile();
            // Center
            newTiles[4] = mTilePanels[4].geTile();
            // middle
            newTiles[3] = mTilePanels[1].geTile();
            newTiles[7] = mTilePanels[3].geTile();
            newTiles[5] = mTilePanels[7].geTile();
            newTiles[1] = mTilePanels[5].geTile();

            for (int i = 0; i < mTilePanels.length; i++) {
                mTilePanels[i].setTile(newTiles[i]);
                mTilePanels[i].rotateLeft();
            }
        }
    }

    /**
     * ////////////////////////////////////////////////////////////////////////////////////////
     * Getter and Setter Methods
     * ////////////////////////////////////////////////////////////////////////////////////////
     */

    public boolean hasEntered() {
        return mEntered;
    }

    public void setEntered(boolean pState) {
        mEntered = pState;
    }

    public boolean isPressed() {
        return mPressed;
    }

    public void setPressed(boolean pState) {
        mPressed = pState;
    }

    public TilePanel[] getTilePanels() {
        return mTilePanels;
    }

    public static int getSizes() {
        return mSize;
    }

    public void setPopupMenuLocation(int pPosX, int pPosY) {
        mPopupMenu.setLocation(pPosX, pPosY);
    }

    public void setShowPopup(boolean pShow) {
        mPopupMenu.setVisible(pShow);
    }

    public Map getMap() {
        return mMap;
    }

    public void setMap(Map pMap) {
        mMap = pMap;
        for (int i = 0; i < mTilePanels.length; i++) {
            mTilePanels[i].setTile(mMap.getTileAtPos(i));
        }
    }

    public void setTilePanels(TilePanel[] pTilePanels) {
        mTilePanels = pTilePanels;
        this.removeAll();
        for (int i = 0; i < pTilePanels.length; i++) {
            mTilePanels[i] = pTilePanels[i];
            mTilePanels[i].revalidate();
            mTilePanels[i].repaint();
        }
    }
}
