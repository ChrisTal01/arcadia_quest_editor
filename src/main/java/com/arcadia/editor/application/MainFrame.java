package com.arcadia.editor.application;

import com.arcadia.editor.entities.*;
import com.arcadia.editor.util.XmlConverter;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    private JPanel mPanelLeftTop; // Contains used Objects
    private MapOverview mPanelLeftBottom = new MapOverview(); // Contains Map Overview
    private JPanel mPanelMiddle; // Main Window
    private JTabbedPane mRightTabbedPane;

    private MapPanel[][] mMapPanels;
    private ArrayList<MapLabel> mMapLabels;
    private ArrayList<ObjectLabel> mObjectLabels;
    private ArrayList<ObjectLabel> mMonsterLabels;

    private MapListener listener;

    private List<GameBox> gameBoxes = new ArrayList<>();

    private final File mainPath = new File("src/main/resources/com/arcadia/editor/data");

    private Settings settings;

    public MainFrame() {
        listener = new MapListener(mPanelLeftBottom);
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

    private void setupGameBoxes(){
        gameBoxes.add(new GameBox(mainPath.getPath(), GameType.ARCADIA_QUEST));
        gameBoxes.add(new GameBox(mainPath.getPath(),GameType.BEYOND_THE_GRAVE));
        gameBoxes.add(new GameBox(mainPath.getPath(),GameType.INFERNO));
        gameBoxes.add(new GameBox(mainPath.getPath(),GameType.WHOLE_LOTTA_LAVA));
        gameBoxes.add(new GameBox(mainPath.getPath(),GameType.PETS));
        gameBoxes.add(new GameBox(mainPath.getPath(),GameType.FROST_DRAGON));
        gameBoxes.add(new GameBox(mainPath.getPath(),GameType.POISON_DRAGON));
        gameBoxes.add(new GameBox(mainPath.getPath(),GameType.FIRE_DRAGON));
        gameBoxes.add(new GameBox(mainPath.getPath(),GameType.CHAOS_DRAGON));
        gameBoxes.add(new GameBox(mainPath.getPath(),GameType.RIDERS));
        gameBoxes.add(new GameBox(mainPath.getPath(),GameType.HELL_OF_A_BOX));
    }

    private void initComponents() {

        setupGameBoxes();

        settings = new Settings();

        // Maps Panel
        mPanelMaps = new JPanel();
        mPanelMaps.setBackground(Color.LIGHT_GRAY);
        mPanelMaps.setLayout(new WrapLayout());
        mPanelMaps.setAutoscrolls(true);
        mPanelMaps.addMouseListener(listener);
        mPanelMaps.addMouseMotionListener(listener);

        // MapLabels
        mMapLabels = new ArrayList<>();

        for(GameBox gameBox : gameBoxes){
            for(MapObject m : gameBox.getMaps()){
                MapLabel label = new MapLabel(m, listener);
                label.setPreferredSize(new Dimension(230, 75));
                mMapLabels.add(label);
            }
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
        mMonsterLabels = new ArrayList<>();
        for(GameBox gameBox : gameBoxes) {
            for (Monster m : gameBox.getMonsters()) {
                ObjectLabel label = new ObjectLabel(m, listener);
                label.setPreferredSize(new Dimension(230, 75));
                mMonsterLabels.add(label);
            }
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
        mObjectLabels = new ArrayList<>();

        for(GameBox gameBox : gameBoxes) {
            for (AQ_Object o : gameBox.getAQ_Objects()) {
                ObjectLabel label = new ObjectLabel(o, listener);
                mObjectLabels.add(label);
            }
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

        initMapPanels();


        mPanelLeftTop = new JPanel();
        mPanelLeftTop.setBackground(Color.LIGHT_GRAY);
        mPanelLeftTop.setPreferredSize(new Dimension(200, 200));
        mPanelLeftTop.setBorder(BorderFactory.createLineBorder(Color.black));
        mPanelLeftTop.setLayout(null);


        mPanelLeftBottom.setBackground(Color.LIGHT_GRAY);
        mPanelLeftBottom.setPreferredSize(new Dimension(200, 50));
        mPanelLeftBottom.setBorder(BorderFactory.createLineBorder(Color.black));
        mPanelLeftBottom.setLayout(null);
        mPanelLeftBottom.setMaps(mMapPanels);

        JScrollPane mapOverviewScrollPane = new JScrollPane(mPanelLeftBottom,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED ,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        // Left Panel
        mPanelLeft = new JPanel();
        mPanelLeft.setBackground(Color.LIGHT_GRAY);
        mPanelLeft.setPreferredSize(new Dimension(200, 100));
        mPanelLeft.setBorder(BorderFactory.createLineBorder(Color.black));
        mPanelLeft.setLayout(new BoxLayout(mPanelLeft,BoxLayout.PAGE_AXIS));
        mPanelLeft.add(mPanelLeftTop);
        mPanelLeft.add(mapOverviewScrollPane);
        this.add(mPanelLeft, BorderLayout.WEST);

        initMenu();
    }

    private void initMapPanels(){
        mMapPanels = new MapPanel[6][4];
        // Map Panels

        for (int i = 0; i < mMapPanels.length; i++) {
            for (int j = 0; j < mMapPanels[i].length; j++) {
                mMapPanels[i][j] = new MapPanel(listener,i,j);

                MapPanel panel = mMapPanels[i][j];
                panel.setBounds(i * MapPanel.getSizes(), j * MapPanel.getSizes(), MapPanel.getSizes(),
                        MapPanel.getSizes());
                panel.setBackground(Color.DARK_GRAY);
                panel.setLayout(new GridLayout(3, 3));
                mPanelMiddle.add(panel);
            }
        }
        this.add(mMiddleScrollPane, BorderLayout.CENTER);

        // set Neighbors of Map Panels
        for (int i = 0; i < mMapPanels.length; i++) {
            for (int j = 0; j < mMapPanels[i].length; j++) {
                if (j - 1 >= 0) {
                    mMapPanels[i][j].setNeighborAtPos(mMapPanels[i][j - 1], IRotatable.TOP);
                }
                if (i + 1 < mMapPanels.length) {
                    mMapPanels[i][j].setNeighborAtPos(mMapPanels[i + 1][j], IRotatable.RIGHT);
                }
                if (j + 1 < mMapPanels[i].length) {
                    mMapPanels[i][j].setNeighborAtPos(mMapPanels[i][j + 1], IRotatable.BOTTOM);
                }
                if (i - 1 >= 0) {
                    mMapPanels[i][j].setNeighborAtPos(mMapPanels[i - 1][j], IRotatable.LEFT);
                }
            }
        }
    }

    private void initMenu(){
        JMenu menu = new JMenu("File");
        JMenuItem newScenarioMenu = new JMenuItem("New Scenario");

        newScenarioMenu.addActionListener(e -> {
            settings = new Settings();
            settings.setVisible(true);
        });
        menu.add(newScenarioMenu);

        JMenuItem settingsMenu = new JMenuItem("Settings");
        settingsMenu.addActionListener(e -> settings.setVisible(true));
        menu.add(settingsMenu);

        JMenuItem saveAsMenu = getjMenuItem();
        menu.add(saveAsMenu);

        JMenuBar bar = new JMenuBar();
        bar.add(menu);
        this.setJMenuBar(bar);
    }

    private JMenuItem getjMenuItem() {
        JMenuItem saveAsMenu = new JMenuItem("Save as...");
        saveAsMenu.addActionListener(e -> {
            JFileChooser c = new JFileChooser();
            c.setVisible(true);
            int rVal = c.showSaveDialog(this);
            if (rVal == JFileChooser.APPROVE_OPTION) {
                File file = c.getSelectedFile();
                try {
                    List<MapObject> maps = new ArrayList<>();
                    for (MapPanel[] mapPanel : mMapPanels) {
                        for (MapPanel current : mapPanel) {
                            if(current.getMap() != null){
                                maps.add(current.getMap());
                            }
                        }
                    }
                    settings.getScenario().setTileGrid(maps);
                    XmlConverter.convertScenario(file,settings.getScenario());
                } catch (ParserConfigurationException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        return saveAsMenu;
    }
}
