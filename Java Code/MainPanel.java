package Editor;

import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Dimension;

public class MainPanel extends JPanel {

    private MapListener mListener;
    private ArrayList<MapPanel> mMapPanels = new ArrayList<MapPanel>();

    public MainPanel(MapListener listener) {
        this.setBackground(Color.BLACK);
        this.setPreferredSize(new Dimension(500, 400));
        this.setLayout(null);
        this.setAutoscrolls(true);
        this.addMouseListener(listener);
        this.addMouseMotionListener(listener);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
