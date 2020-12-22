package Editor;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import java.awt.image.BufferedImage;

import java.awt.Color;
import java.awt.Font;

public class ObjectLabel extends JLabel {

    private AQ_Object mObject;

    public static final String FILE_TYPE_JPG = ".jpg";

    private MapListener mListener;

    public ObjectLabel(AQ_Object pObject, MapListener pListener) {
        mObject = pObject;
        mListener = pListener;
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        this.setFont(new Font("Arial", Font.PLAIN, 20));
        this.setText(mObject.getName());
        this.addMouseListener(mListener);
        this.addMouseMotionListener(mListener);

        this.setText(mObject.getName());
        BufferedImage img = TilePanel.readImage(mObject.getImagePath());
        img = TilePanel.resize(img, 75, 75);
        this.setIcon(new ImageIcon(img));
    }

    public AQ_Object getAqObject() {
        AQ_Object newObject = new AQ_Object(mObject);
        return newObject;
    }

}
