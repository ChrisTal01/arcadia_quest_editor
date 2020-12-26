
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

    private Map mMap;
    private MapListener mListener;

    public MapLabel(Map pMap, MapListener pListener) {
        mMap = pMap;
        mListener = pListener;
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        this.setFont(new Font("Arial", Font.PLAIN, 20));
        this.setText(mMap.getName());
        this.addMouseListener(mListener);
        this.addMouseMotionListener(mListener);

        BufferedImage img = mMap.getImage();
        img = Tile.resize(img, 75, 75);
        mImage = new ImageIcon(img);
        this.setIcon(mImage);
    }

    public Map getMap() {
        Map newMap = new Map(mMap);
        return newMap;
    }

}
