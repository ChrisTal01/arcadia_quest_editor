
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Image;

public class TilePanel extends JPanel {
    // Top, Right, Bottom, Left
    private TilePanel[] mNeighbors = { null, null, null, null };
    private BufferedImage mSelectedImage;
    private static int size = 5;
    private BufferedImage[] mTileImages = new BufferedImage[size];
    private Tile mTile;
    private boolean mShowDoorOutline = false;
    private Door[] mDoors = { null, null, null, null };

    private ArrayList<AQ_Object> mNormalObjects = new ArrayList<>();
    private AQ_Object mSelectedObject;
    private StoneCard mStoneCard;

    private JPopupMenu mPopupMenu;
    private JMenuItem mGrayTile;
    private JMenuItem mNormalTile;
    private JMenuItem mStartTile;
    private JMenuItem mGreenTile;
    private JMenuItem mVioletTile;

    public static int TOP = 0;
    public static int RIGHT = 1;
    public static int BOTTOM = 2;
    public static int LEFT = 3;

    private int mDoorEdgeSpace = 3;
    private int mDoorHeight = 26;
    private int mDoorWidth;

    public TilePanel(Tile pTile) {
        mTile = pTile;
        mTile.setSelectedPos(Tile.GRAY_IMAGE);
        mSelectedImage = mTile.getCurrentImage();
        mNormalObjects = mTile.getAqObecjts();
        initComponents();
    }

    public TilePanel() {
        mTile = null;
        mSelectedImage = null;
        initComponents();
    }

    private void initComponents() {

        mPopupMenu = new JPopupMenu();

        mNormalTile = new JMenuItem("Use normal tile");
        mNormalTile.addActionListener(e -> {
            setSelectedImage(Tile.NORMAL_IMAGE);
            repaint();
        });

        mGrayTile = new JMenuItem("Use gray tile");
        mGrayTile.addActionListener(e -> {
            setSelectedImage(Tile.GRAY_IMAGE);
            repaint();
        });

        mStartTile = new JMenuItem("Use start tile");
        mStartTile.addActionListener(e -> {
            setSelectedImage(Tile.START_IMAGE);
            repaint();
        });

        mGreenTile = new JMenuItem("Use green tile");
        mGreenTile.addActionListener(e -> {
            setSelectedImage(Tile.GREEN_IMAGE);
            repaint();
        });

        mVioletTile = new JMenuItem("Use violet tile");
        mVioletTile.addActionListener(e -> {
            setSelectedImage(Tile.VIOLET_IMAGE);
            repaint();
        });

        mPopupMenu.add(mNormalTile);
        mPopupMenu.add(mGrayTile);
        mPopupMenu.add(mStartTile);
        mPopupMenu.add(mGreenTile);
        mPopupMenu.add(mVioletTile);

    }

    public void paintBackground(Graphics g) {
        // draw Background first
        if (mSelectedImage != null) {
            mSelectedImage = resize(mSelectedImage, this.getHeight(), this.getWidth());
            g.drawImage(mSelectedImage, 0, 0, mSelectedImage.getWidth(), mSelectedImage.getHeight(), this);
        }

    }

    public void paintMonsters(Graphics g) {
        int halfSize = this.getWidth() / 2;
        if (mSelectedImage != null) {
            // draw Monsters, Tokens, etc.
            if (mNormalObjects != null && mNormalObjects.size() > 0) {
                // draw Object in the center of Tile
                int amount = mNormalObjects.size();
                AQ_Object ob;
                // dummy Image
                BufferedImage img;
                int xStart;
                int yStart;
                int xEnd;
                int yEnd;

                if (amount == 1) {

                    ob = mNormalObjects.get(0);
                    ob.setImage(resize(ob.getImage(), ob.getPrefImageWidth(), ob.getPrefImageHeight()));
                    img = ob.getImage();

                    xStart = halfSize - img.getWidth() / 2;
                    yStart = halfSize - img.getHeight() / 2;
                    xEnd = img.getWidth();
                    yEnd = img.getHeight();

                    g.drawImage(img, xStart, yStart, xEnd, yEnd, this);
                    // Draw Border
                    if (mSelectedObject != null && ob == mSelectedObject) {
                        g.setColor(Color.BLACK);
                        g.drawRect(xStart - 1, yStart - 1, xEnd + 1, yEnd + 1);
                    }

                } else {
                    ////
                    // Top-Right
                    ////

                    ob = mNormalObjects.get(0);
                    ob.setImage(resize(ob.getImage(), ob.getPrefImageWidth(), ob.getPrefImageHeight()));
                    img = ob.getImage();
                    // the added number gives a litte offset
                    xStart = this.getWidth() - img.getWidth() - 5;
                    yStart = 5;
                    xEnd = img.getWidth();
                    yEnd = img.getHeight();
                    g.drawImage(img, xStart, yStart, xEnd, yEnd, this);
                    // Draw Border
                    if (mSelectedObject != null && ob == mSelectedObject) {
                        g.setColor(Color.BLACK);
                        g.drawRect(xStart - 1, yStart - 1, xEnd + 1, yEnd + 1);
                    }

                    ////
                    // Bottom-Left
                    ////

                    ob = mNormalObjects.get(1);
                    ob.setImage(resize(ob.getImage(), ob.getPrefImageWidth(), ob.getPrefImageHeight()));
                    img = ob.getImage();
                    xStart = 5;
                    yStart = this.getWidth() - img.getWidth() - 5;
                    xEnd = img.getWidth();
                    yEnd = img.getHeight();
                    // the added number gives a litte offset
                    g.drawImage(img, xStart, yStart, xEnd, yEnd, this);
                    // Draw Border
                    if (mSelectedObject != null && ob == mSelectedObject) {
                        g.setColor(Color.BLACK);
                        g.drawRect(xStart - 1, yStart - 1, xEnd + 1, yEnd + 1);
                    }

                    if (amount >= 3) {
                        ////
                        // Bottom-Right
                        ////

                        ob = mNormalObjects.get(2);
                        ob.setImage(resize(ob.getImage(), ob.getPrefImageWidth(), ob.getPrefImageHeight()));
                        img = ob.getImage();
                        xStart = this.getWidth() / 2 + 5;
                        yStart = this.getHeight() / 2 + 5;
                        xEnd = img.getWidth();
                        yEnd = img.getHeight();
                        // the added number gives a litte offset
                        g.drawImage(img, xStart, yStart, xEnd, yEnd, this);
                        // Draw Border
                        if (mSelectedObject != null && ob == mSelectedObject) {
                            g.setColor(Color.BLACK);
                            g.drawRect(xStart - 1, yStart - 1, xEnd + 1, yEnd + 1);
                        }
                    }
                    if (amount == 4) {
                        ////
                        // Top-Left
                        ////

                        ob = mNormalObjects.get(3);
                        ob.setImage(resize(ob.getImage(), ob.getPrefImageWidth(), ob.getPrefImageHeight()));
                        img = ob.getImage();
                        xStart = 5;
                        yStart = 5;
                        xEnd = img.getWidth();
                        yEnd = img.getHeight();
                        // the added number gives a litte offset
                        g.drawImage(img, xStart, yStart, xEnd, yEnd, this);
                        // Draw Border
                        if (mSelectedObject != null && ob == mSelectedObject) {
                            g.setColor(Color.BLACK);
                            g.drawRect(xStart - 1, yStart - 1, xEnd + 1, yEnd + 1);
                        }

                    }

                }
            }
        }
    }

    /*
     * @Override protected void paintComponent(Graphics g) {
     * super.paintComponent(g); mDoorWidth = this.getWidth() - mDoorEdgeSpace; int
     * halfSize = this.getWidth() / 2;
     * 
     * // draw Background first if (mSelectedImage != null) { mSelectedImage =
     * resize(mSelectedImage, this.getHeight(), this.getWidth());
     * g.drawImage(mSelectedImage, 0, 0, mSelectedImage.getWidth(),
     * mSelectedImage.getHeight(), this);
     * 
     * // draw Doors // Top if (mDoors[0] != null) { Door d = mDoors[0];
     * d.setImage(resize(d.getImage(), mDoorWidth, mDoorHeight)); BufferedImage img
     * = d.getImage(); g.drawImage(img, mDoorEdgeSpace, -mDoorHeight / 2,
     * img.getWidth(), img.getHeight(), this); } // Bottom if (mDoors[2] != null) {
     * Door d = mDoors[2]; d.setImage(resize(d.getImage(), mDoorWidth,
     * mDoorHeight)); BufferedImage img = d.getImage(); g.drawImage(img,
     * mDoorEdgeSpace, this.getHeight() - mDoorHeight / 2, img.getWidth(),
     * img.getHeight(), this); }
     * 
     * // Right if (mDoors[1] != null) { Door d = mDoors[1];
     * d.setVertikalImage(resize(d.getVertikalImage(), mDoorHeight, mDoorWidth));
     * BufferedImage img = d.getVertikalImage(); g.drawImage(img, this.getWidth() -
     * (mDoorHeight / 2), mDoorEdgeSpace, img.getWidth(), img.getHeight(), this); }
     * // Left if (mDoors[3] != null) { Door d = mDoors[3];
     * d.setVertikalImage(resize(d.getVertikalImage(), mDoorHeight, mDoorWidth));
     * BufferedImage img = d.getVertikalImage(); g.drawImage(img, -(mDoorHeight /
     * 2), mDoorEdgeSpace, img.getWidth(), img.getHeight(), this); }
     * 
     * // draw Stone Cards if (mStoneCard != null) {
     * g.drawImage(mStoneCard.getImage(), 0, 0, mStoneCard.getImage().getWidth(),
     * mStoneCard.getImage().getHeight(), this); }
     * 
     * // draw Monsters, Tokens, etc. if (mNormalObjects != null &&
     * mNormalObjects.size() > 0) { // draw Object in the center of Tile int amount
     * = mNormalObjects.size(); AQ_Object ob; // dummy Image BufferedImage img; int
     * xStart; int yStart; int xEnd; int yEnd;
     * 
     * if (amount == 1) {
     * 
     * ob = mNormalObjects.get(0); ob.setImage(resize(ob.getImage(),
     * ob.getPrefImageWidth(), ob.getPrefImageHeight())); img = ob.getImage();
     * 
     * xStart = halfSize - img.getWidth() / 2; yStart = halfSize - img.getHeight() /
     * 2; xEnd = img.getWidth(); yEnd = img.getHeight();
     * 
     * g.drawImage(img, xStart, yStart, xEnd, yEnd, this); // Draw Border if
     * (mSelectedObject != null && ob == mSelectedObject) { g.setColor(Color.BLACK);
     * g.drawRect(xStart - 1, yStart - 1, xEnd + 1, yEnd + 1); }
     * 
     * } else { //// // Top-Right ////
     * 
     * ob = mNormalObjects.get(0); ob.setImage(resize(ob.getImage(),
     * ob.getPrefImageWidth(), ob.getPrefImageHeight())); img = ob.getImage(); //
     * the added number gives a litte offset xStart = this.getWidth() -
     * img.getWidth() - 5; yStart = 5; xEnd = img.getWidth(); yEnd =
     * img.getHeight(); g.drawImage(img, xStart, yStart, xEnd, yEnd, this); // Draw
     * Border if (mSelectedObject != null && ob == mSelectedObject) {
     * g.setColor(Color.BLACK); g.drawRect(xStart - 1, yStart - 1, xEnd + 1, yEnd +
     * 1); }
     * 
     * //// // Bottom-Left ////
     * 
     * ob = mNormalObjects.get(1); ob.setImage(resize(ob.getImage(),
     * ob.getPrefImageWidth(), ob.getPrefImageHeight())); img = ob.getImage();
     * xStart = 5; yStart = this.getWidth() - img.getWidth() - 5; xEnd =
     * img.getWidth(); yEnd = img.getHeight(); // the added number gives a litte
     * offset g.drawImage(img, xStart, yStart, xEnd, yEnd, this); // Draw Border if
     * (mSelectedObject != null && ob == mSelectedObject) { g.setColor(Color.BLACK);
     * g.drawRect(xStart - 1, yStart - 1, xEnd + 1, yEnd + 1); }
     * 
     * if (amount >= 3) { //// // Bottom-Right ////
     * 
     * ob = mNormalObjects.get(2); ob.setImage(resize(ob.getImage(),
     * ob.getPrefImageWidth(), ob.getPrefImageHeight())); img = ob.getImage();
     * xStart = this.getWidth() - img.getWidth() - 5; yStart = this.getHeight() -
     * img.getHeight() - 5; xEnd = img.getWidth(); yEnd = img.getHeight(); // the
     * added number gives a litte offset g.drawImage(img, xStart, yStart, xEnd,
     * yEnd, this); // Draw Border if (mSelectedObject != null && ob ==
     * mSelectedObject) { g.setColor(Color.BLACK); g.drawRect(xStart - 1, yStart -
     * 1, xEnd + 1, yEnd + 1); } } if (amount == 4) { //// // Top-Left ////
     * 
     * ob = mNormalObjects.get(3); ob.setImage(resize(ob.getImage(),
     * ob.getPrefImageWidth(), ob.getPrefImageHeight())); img = ob.getImage();
     * xStart = 5; yStart = 5; xEnd = img.getWidth(); yEnd = img.getHeight(); // the
     * added number gives a litte offset g.drawImage(img, xStart, yStart, xEnd,
     * yEnd, this); // Draw Border if (mSelectedObject != null && ob ==
     * mSelectedObject) { g.setColor(Color.BLACK); g.drawRect(xStart - 1, yStart -
     * 1, xEnd + 1, yEnd + 1); }
     * 
     * }
     * 
     * } }
     * 
     * if (mShowDoorOutline) {
     * 
     * int xStart; int yStart; int xEnd; int yEnd; // draw Door outline // Top if
     * (mNeighbors[0] != null && mDoors[0] == null) { xStart = 5; yStart = -10; xEnd
     * = this.getWidth() - 2 * xStart; yEnd = mDoorHeight; g.setColor(Color.BLUE);
     * g.drawRect(xStart, yStart, xEnd, yEnd); }
     * 
     * // Right if (mNeighbors[1] != null && mDoors[1] == null) { xStart =
     * this.getHeight() - 10; yStart = 15; xEnd = 20; yEnd = this.getHeight() - 2 *
     * yStart; g.setColor(Color.BLUE); g.drawRect(xStart, yStart, xEnd, yEnd); }
     * 
     * // Bottom if (mNeighbors[2] != null && mDoors[2] == null) { xStart = 5;
     * yStart = this.getHeight() - mDoorHeight / 2; xEnd = this.getWidth() - 2 *
     * xStart; yEnd = mDoorHeight; g.setColor(Color.BLUE); g.drawRect(xStart,
     * yStart, xEnd, yEnd); }
     * 
     * // Left if (mNeighbors[3] != null && mDoors[3] == null) { xStart = -10;
     * yStart = 15; xEnd = 20; yEnd = this.getHeight() - 2 * yStart;
     * g.setColor(Color.BLUE); g.drawRect(xStart, yStart, xEnd, yEnd); } } } }
     */
    public static BufferedImage resize(BufferedImage img, int width, int height) {
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }

    private BufferedImage rotate(BufferedImage pImg, double angle) {
        if (pImg != null) {
            int w = pImg.getWidth();
            int h = pImg.getHeight();

            BufferedImage rotated = new BufferedImage(w, h, pImg.getType());
            Graphics2D graphic = rotated.createGraphics();
            graphic.rotate(Math.toRadians(angle), w / 2, h / 2);
            graphic.drawImage(pImg, null, 0, 0);
            graphic.dispose();
            return rotated;

        }
        return null;
    }

    public void rotateRight() {
        for (int i = 0; i < mTile.getImages().length; i++) {
            BufferedImage rotatetd = rotate(mTile.getImageAtPos(i), 90);
            mTile.setImageAtPos(rotatetd, i);
        }
        setSelectedImage(mTile.getSelectedPos());
    }

    public void rotateLeft() {
        for (int i = 0; i < mTile.getImages().length; i++) {
            BufferedImage rotatetd = rotate(mTile.getImageAtPos(i), -90);
            mTile.setImageAtPos(rotatetd, i);
        }
        setSelectedImage(mTile.getSelectedPos());
    }

    public void rotate180() {
        for (int i = 0; i < mTile.getImages().length; i++) {
            BufferedImage rotatetd = rotate(mTile.getImageAtPos(i), 180);
            mTile.setImageAtPos(rotatetd, i);
        }
        setSelectedImage(mTile.getSelectedPos());
    }

    /**
     * ////////////////////////////////////////////////////////////////////////////////////////
     * Getter and Setter
     * ////////////////////////////////////////////////////////////////////////////////////////
     */

    public BufferedImage[] getTileImages() {
        return mTileImages;
    }

    public Tile geTile() {
        return mTile;
    }

    public void setSelectedImage(int pPos) {
        mTile.setSelectedPos(pPos);
        mNormalObjects = mTile.getAqObecjts();
        mSelectedImage = mTile.getCurrentImage();
    }

    public void setPopupMenuLocation(int pPosX, int pPosY) {
        mPopupMenu.setLocation(pPosX, pPosY);
    }

    public void setShowPopup(boolean pShow) {
        mPopupMenu.setVisible(pShow);
    }

    public ArrayList<AQ_Object> getObecjts() {
        return mNormalObjects;
    }

    public void setShowDoorOutline(boolean pState) {
        mShowDoorOutline = pState;
        revalidate();
        repaint();
    }

    public void addAqObject(AQ_Object pObejct) {
        if (mTile != null) {
            mTile.addAqObject(pObejct);
            mNormalObjects = mTile.getAqObecjts();
            this.getParent().revalidate();
            this.getParent().repaint();
        }
    }

    public void removeAqObject(AQ_Object pObejct) {
        if (mTile != null) {
            mTile.removeAqObject(pObejct);
            mNormalObjects = mTile.getAqObecjts();
            revalidate();
            repaint();
        }
    }

    public AQ_Object getAQ_ObjectAtLocation(int pPosX, int pPosY) {
        int halfPanelSize = this.getWidth() / 2;
        AQ_Object o;
        BufferedImage img;
        int startW;
        int startH;
        if (mNormalObjects == null) {
            return null;
        }
        if (mNormalObjects.size() == 0) {
            mSelectedObject = null;
            return null;
        }
        if (mNormalObjects.size() == 1) {
            // picture in the center of the panel
            o = mNormalObjects.get(0);
            img = o.getImage();
            startW = halfPanelSize - img.getWidth() / 2;
            startH = halfPanelSize - img.getHeight() / 2;
            if (pPosX >= startW && pPosY >= startH && pPosX <= startW + img.getWidth()
                    && pPosY <= startH + img.getHeight()) {
                mSelectedObject = o;
                repaint();
                return o;
            }
            mSelectedObject = null;
            return null;
        } else {
            // picture in the top right-hand corner
            o = mNormalObjects.get(0);
            img = o.getImage();
            startW = halfPanelSize + 5;
            startH = 7;
            if (pPosX >= startW && pPosY >= startH && pPosX <= startW + img.getWidth()
                    && pPosY <= startH + img.getHeight()) {
                mSelectedObject = o;
                repaint();
                return o;
            }
            // picture in the bottom left-hand corner
            o = mNormalObjects.get(1);
            img = o.getImage();
            startW = 7;
            startH = halfPanelSize + 5;
            if (pPosX >= startW && pPosY >= startH && pPosX <= startW + img.getWidth()
                    && pPosY <= startH + img.getHeight()) {
                mSelectedObject = o;
                repaint();
                return o;
            }
            if (mNormalObjects.size() >= 3) {
                // picture in the bottom right-hand Corner
                o = mNormalObjects.get(2);
                img = o.getImage();
                startW = halfPanelSize + 5;
                startH = halfPanelSize + 5;
                if (pPosX >= startW && pPosY >= startH && pPosX <= startW + img.getWidth()
                        && pPosY <= startH + img.getHeight()) {
                    mSelectedObject = o;
                    repaint();
                    return o;
                }
                // picture in the top left-hand corner
                if (mNormalObjects.size() == 4) {
                    o = mNormalObjects.get(3);
                    img = o.getImage();
                    startW = 5;
                    startH = 5;
                    if (pPosX >= startW && pPosY >= startH && pPosX <= startW + img.getWidth()
                            && pPosY <= startH + img.getHeight()) {
                        mSelectedObject = o;
                        repaint();
                        return o;
                    }
                }
            }
            mSelectedObject = null;
            return null;
        }
    }

    public Door getDoorAtLocation(int pPosX, int pPosY) {
        Door d;
        // TODO
        return null;
    }

    public void setDoorAtLocation(Door pDoor, int pPosX, int pPosY) {
        int xStart;
        int yStart;
        int xEnd;
        int yEnd;

        // Top
        xStart = 5;
        yStart = 0;
        xEnd = this.getWidth() - 2 * xStart;
        yEnd = mDoorHeight;
        if (pPosX >= xStart && pPosY >= yStart && pPosX <= xEnd && pPosY <= yEnd) {
            System.out.println("Top");
            mDoors[0] = pDoor;
            if (mNeighbors[TOP] != null) {
                mNeighbors[TOP].setDoorAtPos(pDoor, BOTTOM);
            }
        }

        // Right
        xStart = this.getWidth() - mDoorHeight / 2;
        yStart = mDoorEdgeSpace;
        xEnd = this.getWidth();
        yEnd = this.getHeight() - mDoorEdgeSpace;
        if (pPosX >= xStart && pPosY >= yStart && pPosX <= xEnd && pPosY <= yEnd) {
            System.out.println("Right");
            mDoors[1] = pDoor;
            if (mNeighbors[RIGHT] != null) {
                System.out.println("Test");
                mNeighbors[RIGHT].setDoorAtPos(pDoor, LEFT);
            }
        }

        // Bottom
        xStart = 5;
        yStart = this.getHeight() - mDoorHeight / 2;
        xEnd = this.getWidth() - xStart;
        yEnd = this.getHeight();
        System.out.println("xEnd = " + xEnd + "; yEnd = " + yEnd);
        if (pPosX >= xStart && pPosY >= yStart && pPosX <= xEnd && pPosY <= yEnd) {
            System.out.println("Bottom");
            mDoors[2] = pDoor;
            if (mNeighbors[BOTTOM] != null) {
                mNeighbors[BOTTOM].setDoorAtPos(pDoor, TOP);
            }
        }

        // Left
        xStart = 0;
        yStart = mDoorEdgeSpace;
        xEnd = mDoorHeight / 2;
        yEnd = this.getHeight() - mDoorEdgeSpace;
        if (pPosX >= xStart && pPosY >= yStart && pPosX <= xEnd && pPosY <= yEnd) {
            System.out.println("Left");
            mDoors[3] = pDoor;
            if (mNeighbors[LEFT] != null) {
                System.out.println("Test");
                mNeighbors[LEFT].setDoorAtPos(pDoor, RIGHT);
            }
        }
        revalidate();
        repaint();
    }

    public void deSelectAqObject() {
        mSelectedObject = null;
        // repaint();
    }

    public void setNeighborAtPos(TilePanel pTilePanel, int pPos) {
        mNeighbors[pPos] = pTilePanel;
    }

    public TilePanel getNeighborAtPos(int pPos) {
        return mNeighbors[pPos];
    }

    public void setDoorAtPos(Door pDoor, int pPos) {
        mDoors[pPos] = pDoor;
    }

    public void setTile(Tile pTile) {
        mTile = pTile;
        setSelectedImage(mTile.getSelectedPos());
        revalidate();
        repaint();
    }

    public BufferedImage getSelectedImage() {
        return mSelectedImage;
    }

    public boolean TileHasObjects() {
        return mNormalObjects.size() > 0;
    }
}
