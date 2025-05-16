package com.arcadia.editor.entities;

import java.io.File;

public class Token extends AQ_Object{
    public Token(String pPath, String pName, GameType pGameBox, int pPrefIconWidth, int pPrefIconHeight, int pPrefImageWidth, int pPrefImageHeight) {
        super(new File(pPath), pName, pGameBox, pPrefIconWidth, pPrefIconHeight, pPrefImageWidth, pPrefImageHeight);
    }
}
