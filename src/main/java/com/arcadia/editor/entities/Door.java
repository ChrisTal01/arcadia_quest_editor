package com.arcadia.editor.entities;

import java.awt.image.BufferedImage;
import java.io.File;

public class Door extends AQ_Object {

    private Door mCounterPart;
    private BufferedImage mVerticalImage;

    public Door(String pPath, String pVerticalImagePath, String pName, GameType pGameBox, int pPrefIconWidth,
                int pPrefIconHeight, int pPrefImageWidth, int pPrefImageHeight) {
        super(new File(pPath), pName, pGameBox, pPrefIconWidth, pPrefIconHeight, pPrefImageWidth, pPrefImageHeight);
        mCounterPart = null;
        mVerticalImage = readImage(new File(pVerticalImagePath));
    }

    public Door(Door pDoor) {
        super(pDoor.getImagePath(), pDoor.getName(), pDoor.getGameBox(), pDoor.getPrefIconWidth(),
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
        mVerticalImage = pImg;
    }

    public BufferedImage getVertikalImage() {
        return mVerticalImage;
    }

    public void copy(Door pDoor) {
        this.setVertikalImage(deepCopy(pDoor.getVertikalImage()));
        // this.setCounterPart(new Door(pDoor.getCounterPart()));
    }
}
