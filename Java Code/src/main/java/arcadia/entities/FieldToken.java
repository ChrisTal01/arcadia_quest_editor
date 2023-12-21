package src.main.java.arcadia.entities;

import java.io.File;

public class FieldToken extends AQ_Object{
    public FieldToken(File pImagePath, String pName, GameType pGameBox, int pPrefIconWidth, int pPrefIconHeight, int pPrefImageWidth, int pPrefImageHeight) {
        super(pImagePath, pName, pGameBox, pPrefIconWidth, pPrefIconHeight, pPrefImageWidth, pPrefImageHeight);
    }

    public FieldToken(File pImagePath, String pName, GameType pGameBox) {
        super(pImagePath, pName, pGameBox);
    }

    public FieldToken(AQ_Object pObject) {
        super(pObject);
    }
}
