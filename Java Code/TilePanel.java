
import javax.imageio.ImageIO;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Image;

public class TilePanel extends JPanel {
    private BufferedImage mSelectedImage;
    private static int size = 5;
    private BufferedImage[] mTileImages = new BufferedImage[size];
    private Tile mTile;
    private boolean mEntered = false;

    private ArrayList<AQ_Object> mNormalObjects = new ArrayList<>();
    private ArrayList<Door> mDoorObjects = new ArrayList<>();
    private AQ_Object mSelectedObject;
    private StoneCard mStoneCard;

    private JPopupMenu mPopupMenu;
    private JMenuItem mGrayTile;
    private JMenuItem mNormalTile;
    private JMenuItem mStartTile;
    private JMenuItem mGreenTile;
    private JMenuItem mVioletTile;

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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int halfSize = this.getWidth() / 2;

        // draw Background first
        if (mSelectedImage != null) {
            mSelectedImage = resize(mSelectedImage, this.getHeight(), this.getWidth());
            g.drawImage(mSelectedImage, 0, 0, mSelectedImage.getWidth(), mSelectedImage.getHeight(), this);

            // draf Cards
            if (mStoneCard != null) {
                g.drawImage(mStoneCard.getImage(), 0, 0, mStoneCard.getImage().getWidth(),
                        mStoneCard.getImage().getHeight(), this);
            }

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
                    ob.setImage(resize(ob.getImage(), (int) (this.getHeight() / 2.4), (int) (this.getHeight() / 2.4)));
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
                    ob.setImage(resize(ob.getImage(), (int) (this.getHeight() / 2.4), (int) (this.getHeight() / 2.4)));
                    img = ob.getImage();
                    // the added number gives a litte offset
                    xStart = halfSize + 5;
                    yStart = 7;
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
                    ob.setImage(resize(ob.getImage(), (int) (this.getHeight() / 2.4), (int) (this.getHeight() / 2.4)));
                    img = ob.getImage();
                    xStart = 7;
                    yStart = halfSize + 5;
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
                        ob.setImage(
                                resize(ob.getImage(), (int) (this.getHeight() / 2.4), (int) (this.getHeight() / 2.4)));
                        img = ob.getImage();
                        xStart = halfSize + 5;
                        yStart = halfSize + 5;
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
                        ob.setImage(
                                resize(ob.getImage(), (int) (this.getHeight() / 2.4), (int) (this.getHeight() / 2.4)));
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

    public static BufferedImage readImage(String pPicturePath, int i, String pPictureType) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(pPicturePath + i + pPictureType));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return image;
    }

    public static BufferedImage readImage(String pPicturePath) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(pPicturePath));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return image;
    }

    public static BufferedImage[] readImages(String pPicturePath, String pPictureName, int p, String pPictureType) {
        BufferedImage[] images = new BufferedImage[size];
        try {
            for (int i = 0; i < images.length; i++) {
                images[i] = ImageIO
                        .read(new File(pPicturePath + p + "\\" + pPictureName + p + Tile.TILE_TYPES[i] + pPictureType));
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return images;
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

    public boolean hasEntered() {
        return mEntered;
    }

    public void setEntered(boolean pState) {
        mEntered = pState;
    }

    public void addAqObject(AQ_Object pObejct) {
        if (mTile != null) {
            mTile.addAqObject(pObejct);
            mNormalObjects = mTile.getAqObecjts();
            revalidate();
            repaint();
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
            // picture in the center of the Panel
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
            // picture in the top Right Corner
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

    public void deSelectAqObject() {
        mSelectedObject = null;
        repaint();
    }

    public void setTile(Tile pTile) {
        mTile = pTile;
        setSelectedImage(mTile.getSelectedPos());
        revalidate();
        repaint();
    }
}
