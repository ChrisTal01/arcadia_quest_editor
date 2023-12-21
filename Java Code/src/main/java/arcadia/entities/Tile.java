package src.main.java.arcadia.entities;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class Tile extends AQ_Object {
    /**
     * ////////////////////////////////////////////////////////////////////////////////////////
     * Variables
     * ////////////////////////////////////////////////////////////////////////////////////////
     */

    private BufferedImage[] mTileImages = new BufferedImage[2];
    private String mPath;
    private int mSelectedImage;
    private BufferedImage mCurrentImage;
    private ArrayList<AQ_Object> mNormalObjects;

    private FieldToken fieldToken;
    private Door[] mDoors = { null, null, null, null };
    private Tile[] mNeighbors = { null, null, null, null };
    private MapPanel mMapPanel;
    private AQ_Object mSelectedObject;

    private boolean mShowDoorOutline = false;

    private int mSize = 133;
    private int mStartX;
    private int mStartY;

    public static final int NORMAL_IMAGE = 0;
    public static final int GRAY_IMAGE = 1;
    public static final int START_IMAGE = 2;
    public static final int GREEN_IMAGE = 3;
    public static final int VIOLET_IMAGE = 4;
    public static final String[] TILE_TYPES = { "Normal", "Gray", "Start", "Green", "Violet" };
    public static final String FILE_TYPE_JPG = ".jpg";

    public static final int TOP = 0;
    public static final int RIGHT = 1;
    public static final int BOTTOM = 2;
    public static final int LEFT = 3;

    private JPopupMenu mPopupMenu;
    private JMenuItem mGrayTile;
    private JMenuItem mNormalTile;
    private JMenuItem mStartTile;
    private JMenuItem mGreenTile;
    private JMenuItem mVioletTile;

    /**
     * ////////////////////////////////////////////////////////////////////////////////////////
     * Constructor
     * ////////////////////////////////////////////////////////////////////////////////////////
     */

    public Tile(String pImagePath, String pName, GameType pGameBox) {
        super(new File(pImagePath + "\\img" + pName + "\\img" + pName + TILE_TYPES[GRAY_IMAGE] + FILE_TYPE_JPG), pName,
                pGameBox);
        mPath = pImagePath + "\\img" + pName + "\\img" + pName;
        mSelectedImage = GRAY_IMAGE;
        mTileImages = readImages(mPath, FILE_TYPE_JPG);
        mNormalObjects = new ArrayList<>();
        mCurrentImage = mTileImages[mSelectedImage];
        mMapPanel = null;
        fieldToken = null;
        initComponents();
    }

    public Tile(Tile pTile) {
        super(pTile.getImagePath(), pTile.getName(), pTile.getGameBox(), 1, 1, 1, 1);
        copy(pTile);
        initComponents();
    }

    private void initComponents() {

        mPopupMenu = new JPopupMenu();

        mNormalTile = new JMenuItem("Use normal tile");
        mNormalTile.addActionListener(e -> {
            setSelectedPos(Tile.NORMAL_IMAGE);
            setCurrentImage(mSelectedImage);
            mMapPanel.repaint();
        });

        mGrayTile = new JMenuItem("Use gray tile");
        mGrayTile.addActionListener(e -> {
            setSelectedPos(Tile.GRAY_IMAGE);
            setCurrentImage(mSelectedImage);
            mMapPanel.repaint();
        });


        mStartTile = new JMenuItem("Use start tile");
        mStartTile.addActionListener(e -> {
            setSelectedPos(Tile.START_IMAGE);
            setCurrentImage(mSelectedImage);
            mMapPanel.repaint();
        });

        mGreenTile = new JMenuItem("Use green tile");
        mGreenTile.addActionListener(e -> {
            setSelectedPos(Tile.GREEN_IMAGE);
            setCurrentImage(mSelectedImage);
            mMapPanel.repaint();
        });

        mVioletTile = new JMenuItem("Use violet tile");
        mVioletTile.addActionListener(e -> {
            setSelectedPos(Tile.VIOLET_IMAGE);
            setCurrentImage(mSelectedImage);
            mMapPanel.repaint();
        });

        mPopupMenu.add(mNormalTile);
        mPopupMenu.add(mGrayTile);
        /*
        mPopupMenu.add(mStartTile);
        mPopupMenu.add(mGreenTile);
        mPopupMenu.add(mVioletTile);
        */
    }

    /**
     * ////////////////////////////////////////////////////////////////////////////////////////
     * Paint Methods
     * ////////////////////////////////////////////////////////////////////////////////////////
     */

    public void paintBackground(Graphics g) {
        // only draw if background image is not null
        if (mCurrentImage == null) {
            return;
        }
        // draw background
        mCurrentImage = resize(mCurrentImage, mSize, mSize);
        g.drawImage(mCurrentImage, mStartX, mStartY, mCurrentImage.getWidth(), mCurrentImage.getHeight(), null);

        // only draw door outline if door is selected
        if (!mShowDoorOutline) {
            return;
        }

        // draw Field Token
        if(fieldToken != null){
            int xStart;
            int yStart;

            FieldToken ob = fieldToken;
            ob.setImage(resize(ob.getImage(), ob.getPrefImageWidth(), ob.getPrefImageHeight()));
            BufferedImage img = ob.getImage();
            xStart = mStartX + (mSize / 2 - img.getWidth() / 2);
            yStart = mStartY + (mSize / 2 - img.getHeight() / 2);
            drawObjectAtPos(g, ob, xStart, yStart);
        }

        // draw Door outline
        paintTopDoorOutline(g);
        paintRightDoorOutline(g);
        paintBottomDoorOutline(g);
        paintLeftDoorOutline(g);


    }

    private void paintTopDoorOutline(Graphics g){
        int xStart, yStart, width, height;
        if (mNeighbors[TOP] != null && getTopDoor() == null) {
            xStart = mStartX + (mSize / 10);
            yStart = mStartY - (mSize / 10);
            width = mSize - 2 * (mSize / 10);
            height = 20;

            g.setColor(Color.BLUE);
            g.drawRect(xStart, yStart, width, height);
        }
    }

    private void paintRightDoorOutline(Graphics g){
        int xStart, yStart, width, height;

        if (mNeighbors[RIGHT] != null && getRightDoor() == null) {
            xStart = mStartX + (mSize - (mSize / 10));
            yStart = mStartY + 15;
            width = 20;
            height = mSize - (mSize / 5); // mSize - 2 * ((mSize / 10) + 3);

            g.setColor(Color.BLUE);
            g.drawRect(xStart, yStart, width, height);
        }
    }

    private void paintLeftDoorOutline(Graphics g){
        int xStart, yStart, width, height;
        if (mNeighbors[LEFT] != null && getLeftDoor() == null) {
            xStart = mStartX - (mSize / 10);
            yStart = mStartY + 15;
            width = 20;
            height = mSize - (mSize / 5);

            g.setColor(Color.BLUE);
            g.drawRect(xStart, yStart, width, height);

        }
    }

    private void paintBottomDoorOutline(Graphics g){
        int xStart, yStart, width, height;
        if (mNeighbors[BOTTOM] != null && getBottomDoor() == null) {
            xStart = mStartX + (mSize / 10);
            yStart = mStartY + (mSize - (mSize / 10));
            width = mSize - 2 * (mSize / 10);
            height = 20;

            g.setColor(Color.BLUE);
            g.drawRect(xStart, yStart, width, height);
        }
    }

    public void paintDoors(Graphics g) {
        int xStart, yStart;

        // Top
        if (getTopDoor() != null) {
            xStart = mStartX + (mSize / 13);
            yStart = mStartY - (mSize / 10);
            drawDoorAtPos(g,getTopDoor(), xStart, yStart,false);
        }
        // Right
        if (getRightDoor() != null) {
            xStart = mStartX + (mSize - (mSize / 10));
            yStart = mStartY + (mSize / 13);
            drawDoorAtPos(g,getRightDoor(), xStart, yStart,true);
        }
        // Bottom

        if (getBottomDoor() != null) {
            xStart = mStartX + (mSize / 13);
            yStart = mStartY + (mSize - (mSize / 10));
            drawDoorAtPos(g,getBottomDoor(), xStart, yStart,false);
        }
        // Left
        if (getLeftDoor() != null) {
            xStart = mStartX - (mSize / 10);
            yStart = mStartY + (mSize / 13);
            drawDoorAtPos(g,getLeftDoor(), xStart, yStart,true);

        }

    }

    public void paintMonsters(Graphics g) {
        // only draw if background image is not null
        if (mCurrentImage == null) {
            return;
        }
        // only draw Monsters if Tile has Objects
        if (mNormalObjects == null || mNormalObjects.isEmpty()) {
            return;
        }

        int amount = mNormalObjects.size();
        // dummy Image
        int xStart;
        int yStart;

        AQ_Object ob = mNormalObjects.get(0);
        ob.setImage(resize(ob.getImage(), ob.getPrefImageWidth(), ob.getPrefImageHeight()));
        BufferedImage img = ob.getImage();
        if (amount == 1) {
            // Center of Tile

            xStart = mStartX + (mSize / 2 - img.getWidth() / 2);
            yStart = mStartY + (mSize / 2 - img.getHeight() / 2);
            drawObjectAtPos(g, ob, xStart, yStart);

        } else {
            ////
            // Top-Right
            ////

            xStart = mStartX + (mSize - img.getWidth() - 5);
            yStart = mStartY + 5;
            drawObjectAtPos(g, ob, xStart, yStart);

            ////
            // Bottom-Left
            ////

            ob = mNormalObjects.get(1);
            ob.setImage(resize(ob.getImage(), ob.getPrefImageWidth(), ob.getPrefImageHeight()));
            img = ob.getImage();

            xStart = mStartX + 5;
            yStart = mStartY + (mSize - img.getHeight() - 5);
            drawObjectAtPos(g, ob, xStart, yStart);

            if (amount >= 3) {
                ////
                // Bottom-Right
                ////
                ob = mNormalObjects.get(2);
                ob.setImage(resize(ob.getImage(), ob.getPrefImageWidth(), ob.getPrefImageHeight()));
                img = ob.getImage();

                xStart = mStartX + (mSize - img.getWidth() - 5);
                yStart = mStartY + (mSize - img.getHeight() - 5);
                drawObjectAtPos(g, ob, xStart, yStart);
            }
            if (amount == 4) {
                ////
                // Top-Left
                ////
                ob = mNormalObjects.get(3);
                ob.setImage(resize(ob.getImage(), ob.getPrefImageWidth(), ob.getPrefImageHeight()));
                img = ob.getImage();

                xStart = mStartX + 5;
                yStart = mStartY + 5;
                drawObjectAtPos(g, ob, xStart, yStart);
            }

        }
    }

    /**
     * This method takes an Arcadia Quest Object and the x and y starting location
     * to draw the image of the Object and also draws an outline around the image,
     * if the object is selected.
     * 
     * @param g          graphic Object, used to draw
     * @param pAq_Object Object with image that will be drawn
     * @param pStartX    x starting location, when drawing
     * @param pStartY    y starting location, when drawing
     */
    public void drawObjectAtPos(Graphics g, AQ_Object pAq_Object, int pStartX, int pStartY) {

        BufferedImage img = pAq_Object.getImage();

        int xEnd = img.getWidth();
        int yEnd = img.getHeight();

        g.drawImage(img, pStartX, pStartY, xEnd, yEnd, null);
        // Draw Border
        if (mSelectedObject != null && pAq_Object == mSelectedObject) {
            g.setColor(Color.BLACK);
            g.drawRect(pStartX - 1, pStartY - 1, xEnd + 1, yEnd + 1);
        }
    }

    public void drawDoorAtPos(Graphics g, Door door, int pStartX, int pStartY, boolean vertical) {
        BufferedImage img = vertical ? resize(door.getVertikalImage(), door.getPrefImageHeight(), door.getPrefImageWidth()) :
                resize(door.getImage(), door.getPrefImageWidth(), door.getPrefImageHeight());

        int xEnd = img.getWidth();
        int yEnd = img.getHeight();

        g.drawImage(img, pStartX, pStartY, xEnd, yEnd, null);
        // Draw Border
        if (mSelectedObject != null && door == mSelectedObject) {
            g.setColor(Color.BLACK);
            g.drawRect(pStartX - 1, pStartY - 1, xEnd + 1, yEnd + 1);
        }
    }

    /*
     * ////////////////////////////////////////////////////////////////////////////////////////
     * Static
     * ////////////////////////////////////////////////////////////////////////////////////////
     */

    /**
     * Takes a Path to the file with the images and the used type. Then iterates
     * over all image and stores them in a BufferedImage Array.
     * 
     * @param pPicturePath path to the pictures
     * @param pPictureType type of the pictures (png, jpeg, etc.)
     * @return BufferedImage Array (5)
     */
    public static BufferedImage[] readImages(String pPicturePath, String pPictureType) {
        BufferedImage[] images = new BufferedImage[2];
        try {
            for (int i = 0; i < images.length; i++) {
                images[i] = ImageIO.read(new File(pPicturePath + TILE_TYPES[i] + pPictureType));
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return images;
    }

    /**
     * Takes an Image and resizes it to the given width and height.
     * 
     * @param pImage  Image to resize
     * @param pWidth  new width of the image
     * @param pHeight new height of the image
     * @return resized BufferedImage
     */
    public static BufferedImage resize(BufferedImage pImage, int pWidth, int pHeight) {
        Image tmp = pImage.getScaledInstance(pWidth, pHeight, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(pWidth, pHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }

    /**
     * ////////////////////////////////////////////////////////////////////////////////////////
     * Rotate mehtods
     * ////////////////////////////////////////////////////////////////////////////////////////
     */

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
        for (int i = 0; i < mTileImages.length; i++) {
            BufferedImage rotated = rotate(mTileImages[i], 90);
            mTileImages[i] = rotated;
        }
        setCurrentImage(mSelectedImage);
        updateNeighborsRight();
        updateDoorsRight();
    }

    public void rotateLeft() {
        for (int i = 0; i < mTileImages.length; i++) {
            BufferedImage rotated = rotate(mTileImages[i], -90);
            mTileImages[i] = rotated;
        }
        setCurrentImage(mSelectedImage);
        updateNeighborsLeft();
        updateDoorsLeft();
    }

    public void rotate180() {
        for (int i = 0; i < mTileImages.length; i++) {
            BufferedImage rotated = rotate(mTileImages[i], 180);
            mTileImages[i] = rotated;
        }
        setCurrentImage(mSelectedImage);
        updateNeighbors180();
        updateDoors180();
    }

    private void updateNeighborsRight(){
        Tile[] newNeighbors = new Tile[9];
        newNeighbors[TOP] = mNeighbors[LEFT];
        newNeighbors[LEFT] = mNeighbors[BOTTOM];
        newNeighbors[BOTTOM] = mNeighbors[RIGHT];
        newNeighbors[RIGHT] = mNeighbors[TOP];
        setNeighbors(newNeighbors);
    }

    private void updateDoorsRight(){
        Door[] newDoors = new Door[4];
        newDoors[TOP] = mDoors[LEFT];
        newDoors[LEFT] = mDoors[BOTTOM];
        newDoors[BOTTOM] = mDoors[RIGHT];
        newDoors[RIGHT] = mDoors[TOP];
        setDoors(newDoors);
    }

    private void updateNeighborsLeft(){
        Tile[] newNeighbors = new Tile[9];
        newNeighbors[TOP] = mNeighbors[RIGHT];
        newNeighbors[RIGHT] = mNeighbors[BOTTOM];
        newNeighbors[LEFT] = mNeighbors[TOP];
        newNeighbors[BOTTOM] = mNeighbors[LEFT];
        setNeighbors(newNeighbors);
    }

    private void updateDoorsLeft(){
        Door[] newDoors = new Door[4];
        newDoors[TOP] = mDoors[RIGHT];
        newDoors[RIGHT] = mDoors[BOTTOM];
        newDoors[LEFT] = mDoors[TOP];
        newDoors[BOTTOM] = mDoors[LEFT];
        setDoors(newDoors);
    }

    private void updateNeighbors180(){
        Tile[] newNeighbors = new Tile[9];
        Tile temp = mNeighbors[TOP];
        newNeighbors[TOP] = mNeighbors[BOTTOM];
        newNeighbors[BOTTOM] = temp;
        temp = mNeighbors[LEFT];
        newNeighbors[LEFT] = mNeighbors[RIGHT];
        newNeighbors[RIGHT] = temp;
        setNeighbors(newNeighbors);
    }

    private void updateDoors180(){
        Door[] newDoors = new Door[4];
        Door temp = mDoors[TOP];
        newDoors[TOP] = mDoors[BOTTOM];
        newDoors[BOTTOM] = temp;
        temp = mDoors[LEFT];
        newDoors[LEFT] = mDoors[RIGHT];
        newDoors[RIGHT] = temp;
        setDoors(newDoors);
    }

    /**
     * ////////////////////////////////////////////////////////////////////////////////////////
     * Getter
     * ////////////////////////////////////////////////////////////////////////////////////////
     */

    public BufferedImage[] getImages() {
        return mTileImages;
    }

    public String getPath() {
        return mPath;
    }

    public BufferedImage getCurrentImage() {
        return mTileImages[mSelectedImage];
    }

    public BufferedImage getImageAtPos(int pPos) {
        return mTileImages[pPos];
    }

    public int getSelectedPos() {
        return mSelectedImage;
    }

    public AQ_Object getAQ_ObjectAtLocation(int pPosX, int pPosY) {
        AQ_Object o;
        BufferedImage img;
        int startW;
        int startH;

        DoorOutline top = new DoorOutline(mStartX + (mSize / 10),mStartY - (mSize / 10),mSize - 2 * (mSize / 10),20);

        if (getTopDoor() != null && top.isPositionInDoorOutline(pPosX,pPosY)) {
            mSelectedObject = getTopDoor();
            return getTopDoor();
        }
        DoorOutline right = new DoorOutline(mStartX + (mSize - (mSize / 10)),mStartY + 15,20,mSize - (mSize / 5));

        if (getRightDoor() != null && right.isPositionInDoorOutline(pPosX,pPosY)) {
            mSelectedObject = getRightDoor();
            return getRightDoor();
        }

        DoorOutline bottom = new DoorOutline(mStartX + (mSize / 10),mStartY + (mSize - (mSize / 10)),mSize - 2 * (mSize / 10),20);

        if (getBottomDoor() != null && bottom.isPositionInDoorOutline(pPosX,pPosY)) {
            mSelectedObject = getBottomDoor();
            return getBottomDoor();
        }

        DoorOutline left = new DoorOutline(mStartX - (mSize / 10),mStartY + 15,20,mSize - (mSize / 5));

        if (getLeftDoor() != null && left.isPositionInDoorOutline(pPosX,pPosY)) {
            mSelectedObject = getLeftDoor();
            return getLeftDoor();
        }

        if (mNormalObjects == null) {
            return null;
        }
        if (mNormalObjects.isEmpty()) {
            mSelectedObject = null;
            return null;
        }
        if (mNormalObjects.size() == 1) {
            // picture in the center of the panel
            o = mNormalObjects.get(0);
            img = o.getImage();
            startW = mStartX + (mSize / 2 - img.getWidth() / 2);
            startH = mStartY + (mSize / 2 - img.getHeight() / 2);
            if (pPosX >= startW && pPosY >= startH && pPosX <= startW + img.getWidth()
                    && pPosY <= startH + img.getHeight()) {
                mSelectedObject = o;
                return o;
            }
            mSelectedObject = null;
            return null;
        } else {
            // picture in the top right-hand corner
            o = mNormalObjects.get(0);
            img = o.getImage();
            startW = mStartX + (mSize / 2 + 5);
            startH = mStartY + 7;
            if (pPosX >= startW && pPosY >= startH && pPosX <= startW + img.getWidth()
                    && pPosY <= startH + img.getHeight()) {
                mSelectedObject = o;
                return o;
            }
            // picture in the bottom left-hand corner
            o = mNormalObjects.get(1);
            img = o.getImage();
            startW = mStartX + 7;
            startH = mStartY + (mSize / 2 + 5);
            if (pPosX >= startW && pPosY >= startH && pPosX <= startW + img.getWidth()
                    && pPosY <= startH + img.getHeight()) {
                mSelectedObject = o;
                return o;
            }
            if (mNormalObjects.size() >= 3) {
                // picture in the bottom right-hand Corner
                o = mNormalObjects.get(2);
                img = o.getImage();
                startW = mStartX + (mSize / 2 + 5);
                startH = mStartY + (mSize / 2 + 5);
                if (pPosX >= startW && pPosY >= startH && pPosX <= startW + img.getWidth()
                        && pPosY <= startH + img.getHeight()) {
                    mSelectedObject = o;
                    return o;
                }
                // picture in the top left-hand corner
                if (mNormalObjects.size() == 4) {
                    o = mNormalObjects.get(3);
                    img = o.getImage();
                    startW = mStartX + 5;
                    startH = mStartY + 5;
                    if (pPosX >= startW && pPosY >= startH && pPosX <= startW + img.getWidth()
                            && pPosY <= startH + img.getHeight()) {
                        mSelectedObject = o;
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

    /**
     * Calculates the current size used by all Objects in it. If the size is two
     * then objects with a size more or equal to 1 can not be added to the list.
     * 
     * @return currently used space of the src.main.java.arcadia.entities.Tile
     */
    private int getCurrentSize() {
        int currentSize = 0;
        for (AQ_Object o : mNormalObjects) {
            if (o instanceof Monster) {
                currentSize += ((Monster) o).getSize();
            }
        }
        return currentSize;
    }

    public ArrayList<AQ_Object> getAqObecjts() {
        return mNormalObjects;
    }

    public AQ_Object getAqObecjtAtPos(int pPos) {
        return mNormalObjects.get(pPos);
    }

    public int getStartPosX() {
        return mStartX;
    }

    public int getStartPosY() {
        return mStartY;
    }

    public int getSize() {
        return mSize;
    }

    public Tile[] getNeighbors() {
        return mNeighbors;
    }

    public Door getBottomDoor(){
        return mDoors[BOTTOM];
    }
    public Door getTopDoor(){
        return mDoors[TOP];
    }
    public Door getLeftDoor(){
        return mDoors[LEFT];
    }
    public Door getRightDoor(){
        return mDoors[RIGHT];
    }

    /**
     * ////////////////////////////////////////////////////////////////////////////////////////
     * Setter
     * ////////////////////////////////////////////////////////////////////////////////////////
     */

    public void setDoorAtLocation(Door pDoor, int pPosX, int pPosY) {
        System.out.println("\tStart setDoorAtLocation");

        int xStart, yStart, xEnd, yEnd;

        // Top
        DoorOutline top = new DoorOutline(mStartX + (mSize / 10),mStartY - (mSize / 10),mSize - 2 * (mSize / 10),20);

        if (top.isPositionInDoorOutline(pPosX,pPosY)) {
            System.out.println("\tTile Top");
            mDoors[0] = pDoor;
            if (mNeighbors[TOP] != null) {
                System.out.println("\tAdded door to Top Neighbor at Bottom.");
                mNeighbors[TOP].setDoorAtPos(pDoor, BOTTOM);
            }
        }
        DoorOutline right = new DoorOutline(mStartX + (mSize - (mSize / 10)),mStartY + 15,20,mSize - (mSize / 5));

        if (right.isPositionInDoorOutline(pPosX,pPosY)) {
            System.out.println("\tTile Right");
            mDoors[1] = pDoor;
            if (mNeighbors[RIGHT] != null) {
                System.out.println("\tAdded door to Right Neighbor at Left.");
                mNeighbors[RIGHT].setDoorAtPos(pDoor, LEFT);
            }
        }
        DoorOutline bottom = new DoorOutline(mStartX + (mSize / 10),mStartY + (mSize - (mSize / 10)),mSize - 2 * (mSize / 10),20);

        if (bottom.isPositionInDoorOutline(pPosX,pPosY)) {
            System.out.println("\tTile Bottom");
            mDoors[2] = pDoor;
            if (mNeighbors[BOTTOM] != null) {
                System.out.println("\tAdded door to Bottom Neighbor at Top.");
                mNeighbors[BOTTOM].setDoorAtPos(pDoor, TOP);
            }
        }

        DoorOutline left = new DoorOutline(mStartX - (mSize / 10),mStartY + 15,20,mSize - (mSize / 5));

        if (left.isPositionInDoorOutline(pPosX,pPosY)) {
            System.out.println("\tTile Left");
            mDoors[3] = pDoor;
            if (mNeighbors[LEFT] != null) {
                System.out.println("\tAdded door to Left Neighbor at Right.");
                mNeighbors[LEFT].setDoorAtPos(pDoor, RIGHT);
            }
        }
        mMapPanel.revalidate();
        mMapPanel.repaint();
    }

    public void setDoorAtPos(Door pDoor, int pPos) {
        mDoors[pPos] = pDoor;
    }

    public void setShowDoorOutline(boolean pState) {
        mShowDoorOutline = pState;
    }

    public void setStartPos(int pPosX, int pPosY) {
        mStartX = pPosX;
        mStartY = pPosY;
    }

    public void setImages(BufferedImage[] pTilesImages) {
        mTileImages = pTilesImages;
    }

    public void setPath(String pPath) {
        mPath = pPath;
    }

    public BufferedImage[] loadImages() {
        BufferedImage[] newImages = new BufferedImage[5];
        newImages = readImages(mPath, FILE_TYPE_JPG);
        return newImages;
    }

    public void setSelectedPos(int pPos) {
        mSelectedImage = pPos;
    }

    public void setImageAtPos(BufferedImage pImg, int pPos) {
        mTileImages[pPos] = pImg;
    }

    public void setCurrentImage(BufferedImage pImg) {
        mCurrentImage = pImg;
    }

    public void setCurrentImage(int pPos) {
        mCurrentImage = mTileImages[pPos];
    }

    public void setAllImages(BufferedImage[] pImg) {
        mTileImages = pImg;
    }

    public void setPopupMenuLocation(int pPosX, int pPosY) {
        mPopupMenu.setLocation(pPosX, pPosY);
    }

    public void setShowPopup(boolean pShow) {
        mPopupMenu.setVisible(pShow);
    }

    public void deSelectObject() {
        mSelectedObject = null;
    }

    /**
     * Adds a given Arcadia Quest Object to the list of Objects, if the overall
     * amount of the Tiles Objects does not exceed 4 and the size of all Objects
     * does not exceed 2.
     * 
     * @param pObject AQ_Object to add
     * @see AQ_Object
     */
    public void addAqObject(AQ_Object pObject) {
        if (mNormalObjects.size() < 4) {
            if (pObject instanceof Monster) {
                if ((getCurrentSize() + ((Monster) pObject).getSize()) <= 2) {
                    mNormalObjects.add(pObject);
                }
            } else {
                if(pObject instanceof FieldToken){
                    fieldToken = (FieldToken) pObject;
                }
                else{
                    mNormalObjects.add(pObject);
                }

            }
            mMapPanel.revalidate();
            mMapPanel.repaint();

        }
    }

    public void setAqObjects(ArrayList<AQ_Object> pObjects) {
        mNormalObjects = pObjects;
    }

    /**
     * Removes the given Arcadia Quest Object, if the amount of all Object of the
     * src.main.java.arcadia.entities.Tile is greater than 0.
     * 
     * @param pObject src.main.java.arcadia.entities.AQ_Object to remove
     * @see AQ_Object
     */
    public void removeAqObject(AQ_Object pObject) {

        if(pObject instanceof Door door){
            for(int i = 0; i < mDoors.length; i++){
                if(door.equals(mDoors[i])){
                    if(i == TOP){
                        if (mNeighbors[TOP] != null) {
                            mNeighbors[TOP].setDoorAtPos(null, BOTTOM);
                        }
                    }
                    else if (i == BOTTOM){
                        if (mNeighbors[BOTTOM] != null) {
                            mNeighbors[BOTTOM].setDoorAtPos(null, TOP);
                        }
                    }
                    else if (i == LEFT){
                        if (mNeighbors[LEFT] != null) {
                            mNeighbors[LEFT].setDoorAtPos(null, RIGHT);
                        }
                    }
                    else if (i == RIGHT){
                        if (mNeighbors[RIGHT] != null) {
                            mNeighbors[RIGHT].setDoorAtPos(null, LEFT);
                        }
                    }
                    mDoors[i] = null;
                }
            }
            mMapPanel.revalidate();
            mMapPanel.repaint();
            return;
        }

        if (!mNormalObjects.isEmpty()) {
            mNormalObjects.remove(pObject);
            mMapPanel.revalidate();
            mMapPanel.repaint();
        }
    }

    public void setSize(int pSize) {
        mSize = pSize;
    }

    public void setMapPanel(MapPanel pMapPanel) {
        mMapPanel = pMapPanel;
    }

    public void setNeighborAtPos(Tile pTile, int pPos) {
        mNeighbors[pPos] = pTile;
    }

    public void setNeighbors(Tile[] pNeighbors) {
        mNeighbors = pNeighbors;
    }

    public void setDoors(Door[] doors) {
        mDoors = doors;
    }

    public void copy(Tile pTile) {
        BufferedImage[] newImages = new BufferedImage[2];
        for (int i = 0; i < newImages.length; i++) {
            newImages[i] = AQ_Object.deepCopy(pTile.getImageAtPos(i));
        }
        this.setImages(newImages);
        this.setSelectedPos(pTile.getSelectedPos());
        this.setPath(pTile.getPath());
        this.setAqObjects(new ArrayList<>(pTile.getAqObecjts()));
        this.setCurrentImage(deepCopy(pTile.getCurrentImage()));
        this.setSize(pTile.getSize());
        this.setNeighbors(pTile.getNeighbors());
        this.setStartPos(pTile.getStartPosX(), pTile.getStartPosY());
    }

    private class DoorOutline{
        private final int width;
        private final int height;

        private final int startX;

        private final int startY;

        public DoorOutline(int startX, int startY, int width, int height){
            this.width = width;
            this.height = height;
            this.startX = startX;
            this.startY = startY;
        }

        public boolean isPositionInDoorOutline(int posX, int posY){
            return posX >= startX && posY >= startY && posX <= startX + width && posY <= startY + height;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }

        public int getStartX() {
            return startX;
        }

        public int getStartY() {
            return startY;
        }
    }

    public String getTopDoorString(){
        String top = getTopDoor() != null ? "=====" : "#####";
        return "     " + top + "   \t\t\t";
    }

    public String getMiddleDoorString(String pos){
        if(pos.length() <= 5){
            pos = " "+pos + " ";
        }
        String left = getLeftDoor() != null ? "===" : "###";
        String right = getRightDoor() != null ? "===" : "###";
        return left + " "+ pos +" "+ right + "\t\t";
    }

    public String getBottomDoorString(){
        String bottom = getBottomDoor() != null ? "=====" : "#####";
        return "     " + bottom + "   \t\t\t";
    }
}
