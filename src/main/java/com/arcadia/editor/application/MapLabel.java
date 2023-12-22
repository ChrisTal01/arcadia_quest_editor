package com.arcadia.editor.application;

import com.arcadia.editor.entities.MapObject;
import com.arcadia.editor.entities.Tile;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.image.BufferedImage;

import java.awt.Color;
import java.awt.Font;

public class MapLabel extends JLabel {

    private ImageIcon mImage;

    public static final String FILE_TYPE_JPG = ".jpg";
    private boolean mUseMapA;

    private MapObject mMapObject;
    private MapListener mListener;

    public MapLabel(MapObject pMapObject, MapListener pListener) {
        mMapObject = pMapObject;
        mListener = pListener;
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        this.setFont(new Font("Arial", Font.PLAIN, 20));
        this.setText(mMapObject.getName());
        this.addMouseListener(mListener);
        this.addMouseMotionListener(mListener);

        BufferedImage img = mMapObject.getImage();
        img = Tile.resize(img, 75, 75);
        mImage = new ImageIcon(img);
        this.setIcon(mImage);
    }

    public MapObject getMap() {
        MapObject newMapObject = new MapObject(mMapObject);
        return newMapObject;
    }

}
