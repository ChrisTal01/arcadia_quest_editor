package src.main.java.arcadia.application;

import src.main.java.arcadia.entities.AQ_Object;
import src.main.java.arcadia.entities.Door;
import src.main.java.arcadia.entities.Monster;
import src.main.java.arcadia.entities.Tile;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import java.awt.image.BufferedImage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

public class ObjectLabel extends JLabel {

    private AQ_Object mObject;

    public static final String FILE_TYPE_JPG = ".jpg";

    private MapListener mListener;

    public ObjectLabel(AQ_Object pObject, MapListener pListener) {
        mObject = pObject;
        mListener = pListener;
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        this.setFont(new Font("Arial", Font.PLAIN, 18));
        this.setText(mObject.getName());
        this.setPreferredSize(new Dimension(230, 75));
        this.addMouseListener(mListener);
        this.addMouseMotionListener(mListener);
        BufferedImage img = AQ_Object.readImage(mObject.getImagePath());

        if (pObject instanceof Door) {
            this.setVerticalTextPosition(JLabel.BOTTOM); // set text Top, Center, Bottom of ImgaeIcon
            this.setHorizontalTextPosition(JLabel.CENTER); // set text Left, Right, Center of ImageIcon

            this.setVerticalAlignment(JLabel.CENTER); // set vertical possition of icon and text within Label
            this.setHorizontalAlignment(JLabel.CENTER); // set horizontal possition of icon and text within Label
        }
        img = Tile.resize(img, mObject.getPrefIconWidth(), mObject.getPrefIconHeight());
        this.setIcon(new ImageIcon(img));
    }

    public AQ_Object getAqObject() {
        AQ_Object newObject;
        if (mObject instanceof Monster) {
            newObject = new Monster((Monster) mObject);
        } else if (mObject instanceof Door) {
            newObject = new Door((Door) mObject);
        } else {
            newObject = new AQ_Object(mObject);
        }
        return newObject;
    }

}
