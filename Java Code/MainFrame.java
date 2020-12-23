
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainFrame extends JFrame {

    private int mWidth = 1800;
    private int mHeight = 1000;

    private JScrollPane mMapsScrollPane;
    private JScrollPane mMonsterScrollPane;
    private JScrollPane mObjectScrollPane;
    private JScrollPane mMiddleScrollPane;
    private JPanel mPanelMaps; // Contains Maps
    private JPanel mPanelMonsters; // Contains Monsters
    private JPanel mPanelObjects; // Contains Objects
    private JPanel mPanelLeft; // Contains used Objects
    private JPanel mPanelMiddle; // Main Window
    private JTabbedPane mRightTabbedPane;

    private MapPanel[][] mMapPanels;
    private ArrayList<MapLabel> mMapLabels;
    private ArrayList<ObjectLabel> mObjectLabels;
    private ArrayList<ObjectLabel> mMonsterLabels;

    private MapListener listener;

    private Gamebox mArcadiaQuest;

    public MainFrame() {
        listener = new MapListener();
        this.setTitle("Editor");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(mWidth, mHeight);
        this.setLocation(400, 200);
        this.setLayout(new BorderLayout());
        this.setFocusable(true);
        this.requestFocus();
        this.addKeyListener(listener);
        initComponents();
    }

    private void initComponents() {

        // GameBoxes
        String currentPath = "";
        try {
            currentPath = new File(".").getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        mArcadiaQuest = new Gamebox(currentPath + "\\ArcadiaQuestData\\ArcadiaQuest\\", "Arcadia Quest",
                Gamebox.ARCADIA_QUEST);

        // Left Panel
        mPanelLeft = new JPanel();
        mPanelLeft.setBackground(Color.LIGHT_GRAY);
        mPanelLeft.setPreferredSize(new Dimension(200, 100));
        mPanelLeft.setBorder(BorderFactory.createLineBorder(Color.black));
        mPanelLeft.setLayout(null);
        this.add(mPanelLeft, BorderLayout.WEST);

        // Maps Panel
        mPanelMaps = new JPanel();
        mPanelMaps.setBackground(Color.LIGHT_GRAY);
        mPanelMaps.setLayout(new WrapLayout());
        mPanelMaps.setAutoscrolls(true);
        mPanelMaps.addMouseListener(listener);
        mPanelMaps.addMouseMotionListener(listener);

        // MapLabels
        mMapLabels = new ArrayList<MapLabel>();

        for (Map m : mArcadiaQuest.getMaps()) {
            MapLabel label = new MapLabel(m, listener);
            label.setPreferredSize(new Dimension(230, 75));
            mMapLabels.add(label);
        }

        for (MapLabel label : mMapLabels) {
            mPanelMaps.add(label);
        }

        // Maps Scroll Pane
        mMapsScrollPane = new JScrollPane();
        mMapsScrollPane.setBorder(BorderFactory.createLineBorder(Color.black));
        mMapsScrollPane.setPreferredSize(new Dimension(260, 100));
        mMapsScrollPane.setViewportView(mPanelMaps);
        mMapsScrollPane.getVerticalScrollBar().setUnitIncrement(16);

        // Monster Panel
        mPanelMonsters = new JPanel();
        mPanelMonsters.setBackground(Color.LIGHT_GRAY);
        mPanelMonsters.setLayout(new WrapLayout());
        mPanelMonsters.setAutoscrolls(true);
        mPanelMonsters.addMouseListener(listener);
        mPanelMonsters.addMouseMotionListener(listener);

        // Monster Labels
        mMonsterLabels = new ArrayList<ObjectLabel>();

        for (Monster m : mArcadiaQuest.getMonsters()) {
            ObjectLabel label = new ObjectLabel(m, listener);
            label.setPreferredSize(new Dimension(230, 75));
            mMonsterLabels.add(label);
        }

        for (ObjectLabel label : mMonsterLabels) {
            mPanelMonsters.add(label);
        }

        // Monster Scroll Pane
        mMonsterScrollPane = new JScrollPane();
        mMonsterScrollPane.setBorder(BorderFactory.createLineBorder(Color.black));
        mMonsterScrollPane.setPreferredSize(new Dimension(260, 100));
        mMonsterScrollPane.setViewportView(mPanelMonsters);
        mMonsterScrollPane.getVerticalScrollBar().setUnitIncrement(16);

        // Object Panel
        mPanelObjects = new JPanel();
        mPanelObjects.setBackground(Color.LIGHT_GRAY);
        mPanelObjects.setLayout(new WrapLayout());
        mPanelObjects.setAutoscrolls(true);
        mPanelObjects.addMouseListener(listener);
        mPanelObjects.addMouseMotionListener(listener);

        // Monster Labels
        mObjectLabels = new ArrayList<ObjectLabel>();

        for (AQ_Object o : mArcadiaQuest.getAQ_Objects()) {
            ObjectLabel label = new ObjectLabel(o, listener);
            mObjectLabels.add(label);
        }

        for (ObjectLabel label : mObjectLabels) {
            mPanelObjects.add(label);
        }

        // Object Scroll Pane
        mObjectScrollPane = new JScrollPane();
        mObjectScrollPane.setBorder(BorderFactory.createLineBorder(Color.black));
        mObjectScrollPane.setPreferredSize(new Dimension(260, 100));
        mObjectScrollPane.setViewportView(mPanelObjects);
        mObjectScrollPane.getVerticalScrollBar().setUnitIncrement(16);

        // TabbedPane
        mRightTabbedPane = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
        mRightTabbedPane.addTab("Maps", mMapsScrollPane);
        mRightTabbedPane.addTab("Monsters", mMonsterScrollPane);
        mRightTabbedPane.addTab("Objects", mObjectScrollPane);
        mRightTabbedPane.setPreferredSize(new Dimension(300, 100));

        this.add(mRightTabbedPane, BorderLayout.EAST);

        // Middle Panel
        mPanelMiddle = new JPanel();
        mPanelMiddle.setBackground(Color.BLACK);
        mPanelMiddle.setPreferredSize(new Dimension(500, 400));
        mPanelMiddle.setLayout(null);
        mPanelMiddle.setAutoscrolls(true);
        mPanelMiddle.addMouseListener(listener);
        mPanelMiddle.addMouseMotionListener(listener);

        // Middle Scroll Pane
        mMiddleScrollPane = new JScrollPane();
        mMiddleScrollPane.setBorder(BorderFactory.createLineBorder(Color.black));
        mMiddleScrollPane.setPreferredSize(new Dimension(500, 400));
        mMiddleScrollPane.setViewportView(mPanelMiddle);

        // Map Panels
        mMapPanels = new MapPanel[6][4];

        for (int i = 0; i < mMapPanels.length; i++) {
            for (int j = 0; j < mMapPanels[i].length; j++) {
                mMapPanels[i][j] = new MapPanel(listener);

                MapPanel panel = mMapPanels[i][j];
                panel.setBounds(i * MapPanel.getSizes(), j * MapPanel.getSizes(), MapPanel.getSizes(),
                        MapPanel.getSizes());
                panel.setBackground(Color.DARK_GRAY);
                panel.setLayout(new GridLayout(3, 3));
                mPanelMiddle.add(panel);
            }
        }
        this.add(mMiddleScrollPane, BorderLayout.CENTER);

    }
}
