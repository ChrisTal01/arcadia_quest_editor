
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Tile extends AQ_Object {

    private BufferedImage[] mTileImages = new BufferedImage[5];
    private String mPath;
    private int mSelectedImage;
    private ArrayList<AQ_Object> mNormalObjects;

    public static final int NORMAL_IMAGE = 0;
    public static final int GRAY_IMAGE = 1;
    public static final int START_IMAGE = 2;
    public static final int GREEN_IMAGE = 3;
    public static final int VIOLET_IMAGE = 4;
    public static final String[] TILE_TYPES = { "Normal", "Gray", "Start", "Green", "Violet" };
    public static final String FILE_TYPE_JPG = ".jpg";

    public Tile(String pImagePath, String pName, int pGameBox) {
        super(pImagePath + "\\img" + pName + "\\img" + pName + TILE_TYPES[GRAY_IMAGE] + FILE_TYPE_JPG, pName, 1,
                pGameBox, 1, 1);
        mPath = pImagePath + "\\img" + pName + "\\img" + pName;
        mSelectedImage = GRAY_IMAGE;
        mTileImages = readImages(mPath, FILE_TYPE_JPG);
        mNormalObjects = new ArrayList<>();
    }

    public Tile(Tile pTile) {
        super(pTile.getImagePath(), pTile.getName(), pTile.getAmount(), pTile.getGameBox(), 1, 1);
        copy(pTile);
    }

    public static BufferedImage[] readImages(String pPicturePath, String pPictureType) {
        BufferedImage[] images = new BufferedImage[5];
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
     * ////////////////////////////////////////////////////////////////////////////////////////
     * Getter and Setter
     * ////////////////////////////////////////////////////////////////////////////////////////
     */

    public void setImages(BufferedImage[] pTilesImages) {
        mTileImages = pTilesImages;
    }

    public BufferedImage[] getImages() {
        return mTileImages;
    }

    public void setPath(String pPath) {
        mPath = pPath;
    }

    public String getPath() {
        return mPath;
    }

    public BufferedImage[] loadImages() {
        BufferedImage[] newImages = new BufferedImage[5];
        newImages = readImages(mPath, FILE_TYPE_JPG);
        return newImages;
    }

    public BufferedImage getCurrentImage() {
        return mTileImages[mSelectedImage];
    }

    public BufferedImage getImageAtPos(int pPos) {
        return mTileImages[pPos];
    }

    public void setSelectedPos(int pPos) {
        mSelectedImage = pPos;
    }

    public void setImageAtPos(BufferedImage pImg, int pPos) {
        mTileImages[pPos] = pImg;
    }

    public void setAllImages(BufferedImage[] pImg) {
        mTileImages = pImg;
    }

    public int getSelectedPos() {
        return mSelectedImage;
    }

    public void addAqObject(AQ_Object pObject) {
        if (mNormalObjects.size() < 4) {
            if (pObject instanceof Monster) {
                if ((getCurrentSize() + ((Monster) pObject).getSize()) <= 2) {
                    mNormalObjects.add(pObject);
                }
            } else {
                mNormalObjects.add(pObject);
            }
        }
    }

    public void setAqObjects(ArrayList<AQ_Object> pObjects) {
        mNormalObjects = pObjects;
    }

    public void removeAqObject(AQ_Object pObject) {
        if (mNormalObjects.size() >= 1) {
            mNormalObjects.remove(pObject);
        }
    }

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

    public void copy(Tile pTile) {
        BufferedImage[] newImages = new BufferedImage[5];
        for (int i = 0; i < newImages.length; i++) {
            newImages[i] = AQ_Object.deepCopy(pTile.getImageAtPos(i));
        }
        this.setImages(newImages);
        this.setSelectedPos(pTile.getSelectedPos());
        this.setPath(pTile.getPath());
        this.setAqObjects(new ArrayList<>(pTile.getAqObecjts()));
    }

}
