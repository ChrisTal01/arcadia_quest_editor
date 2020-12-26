
import java.awt.image.BufferedImage;

public class Door extends AQ_Object {

    private Door mCounterPart;
    private BufferedImage mVertikalImage;

    public Door(String pPath, String pVertikalImagePath, String pName, int pAmount, int pGameBox, int pPrefIconWidth,
            int pPrefIconHeight, int pPrefImageWidth, int pPrefImageHeight) {
        super(pPath, pName, pAmount, pGameBox, pPrefIconWidth, pPrefIconHeight, pPrefImageWidth, pPrefImageHeight);
        mCounterPart = null;
        mVertikalImage = AQ_Object.readImage(pVertikalImagePath);
    }

    public Door(Door pDoor) {
        super(pDoor.getImagePath(), pDoor.getName(), pDoor.getAmount(), pDoor.getGameBox(), pDoor.getPrefIconWidth(),
                pDoor.getPrefIconHeight(), pDoor.getPrefImageWidth(), pDoor.getPrefImageHeight());
        copy(pDoor);
    }

    public void setCounterPart(Door pDoor) {
        mCounterPart = pDoor;
    }

    public Door getCounterPart() {
        return mCounterPart;
    }

    public void setVertikalImage(BufferedImage pImg) {
        mVertikalImage = pImg;
    }

    public BufferedImage getVertikalImage() {
        return mVertikalImage;
    }

    public void copy(Door pDoor) {
        this.setVertikalImage(AQ_Object.deepCopy(pDoor.getVertikalImage()));
        // this.setCounterPart(new Door(pDoor.getCounterPart()));
    }

}
