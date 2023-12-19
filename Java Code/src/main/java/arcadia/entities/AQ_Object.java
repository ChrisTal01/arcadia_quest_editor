package src.main.java.arcadia.entities;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class AQ_Object {

    private String mName;
    private BufferedImage mImage;
    private GameType mGameBox;
    private String mImagePath;

    private int mPrefIconHeight;
    private int mPrefIconWidth;
    private int mPrefImageHeight;
    private int mPrefImageWidth;

    /**
     * Most basic Object that is the base structure of every Arcadia Quest Object.
     * 
     * @param pImagePath       path to the image of the Object
     * @param pName            name of the Object
     * @param pAmount          amount of Objects that can be used
     * @param pGameBox         number of the src.main.java.arcadia.entities.Gamebox the Object belongs to
     * @param pPrefIconWidth   the prefed icon width the image should have when
     *                         showed in the select menu
     * @param pPrefIconHeight  the prefed icon height the image should have when
     *                         showed in the select menu
     * @param pPrefImageWidth  the prefed image height the image should have when
     *                         showed on the src.main.java.arcadia.entities.Tile
     * @param pPrefImageHeight the prefed image height the image should have when
     *                         showed on the src.main.java.arcadia.entities.Tile
     */
    public AQ_Object(String pImagePath, String pName, GameType pGameBox, int pPrefIconWidth,
                     int pPrefIconHeight, int pPrefImageWidth, int pPrefImageHeight) {
        mImage = readImage(pImagePath);
        mImagePath = pImagePath;
        mName = pName;
        mGameBox = pGameBox;
        mPrefIconWidth = pPrefIconWidth;
        mPrefIconHeight = pPrefIconHeight;
        mPrefImageWidth = pPrefImageWidth;
        mPrefImageHeight = pPrefImageHeight;
    }

    public AQ_Object(String pImagePath, String pName, GameType pGameBox) {
        mImage = readImage(pImagePath);
        mImagePath = pImagePath;
        mName = pName;
        mGameBox = pGameBox;
        mPrefIconWidth = 1;
        mPrefIconHeight = 1;
        mPrefImageWidth = 1;
        mPrefImageHeight = 1;
    }

    public AQ_Object(AQ_Object pObject) {
        copy(pObject);
    }

    public static BufferedImage readImage(String pPath) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(pPath));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return image;
    }

    public BufferedImage getImage() {
        return mImage;
    }

    public String getName() {
        return mName;
    }

    public GameType getGameBox() {
        return mGameBox;
    }

    public String getImagePath() {
        return mImagePath;
    }


    public void setName(String pName) {
        mName = pName;
    }

    public void setImagePath(String pPath) {
        mImagePath = pPath;
    }

    public void setGameBox(GameType pGameBox) {
        mGameBox = pGameBox;
    }

    public void setImage(BufferedImage pImage) {
        mImage = pImage;
    }

    public void setPrefIconWidth(int pPref) {
        mPrefIconWidth = pPref;
    }

    public void setPrefIconHeight(int pPref) {
        mPrefIconHeight = pPref;
    }

    public int getPrefIconWidth() {
        return mPrefIconWidth;
    }

    public int getPrefIconHeight() {
        return mPrefIconHeight;
    }

    public void setPrefImageWidth(int pPref) {
        mPrefImageWidth = pPref;
    }

    public void setPrefImageHeight(int pPref) {
        mPrefImageHeight = pPref;
    }

    public int getPrefImageWidth() {
        return mPrefImageWidth;
    }

    public int getPrefImageHeight() {
        return mPrefImageHeight;
    }

    public static BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

    public void copy(AQ_Object pObject) {
        setName(pObject.getName());
        setImage(deepCopy(pObject.getImage()));
        setGameBox(pObject.getGameBox());
        setImagePath(pObject.getImagePath());
        setPrefIconWidth(pObject.getPrefIconWidth());
        setPrefIconHeight(pObject.getPrefIconHeight());
        setPrefImageWidth(pObject.getPrefImageWidth());
        setPrefImageHeight(pObject.getPrefImageHeight());
    }

}
