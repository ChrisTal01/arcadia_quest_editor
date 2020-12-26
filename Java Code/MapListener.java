
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JPanel;

import java.awt.MouseInfo;
import java.awt.PointerInfo;

public class MapListener implements MouseMotionListener, MouseListener, KeyListener {

    // Current means under the cursor or dragged

    private MapLabel mCurrentMapLabel;
    private MapPanel mCurrentMapPanel;
    private TilePanel mCurrentTilePanel;
    private ObjectLabel mCurrentObjectLabel;
    private AQ_Object mCurrentAqObject;
    private Map mCurrentMap;
    private Door mCurrentDoor;
    private JPanel mCurrentMainPanel;

    // Selected means clicked on
    private TilePanel mSelectedTilePanel;
    private AQ_Object mSelectedAqObject;

    private boolean mShiftPressed;
    private boolean mMenuOpen;
    private TilePanel mTilePanelMenu;
    private MapPanel mMapPanelMenu;

    private ArrayList<MapPanel> mMapPanels = new ArrayList<>();

    public MapListener() {
        mCurrentMapPanel = null;
        mCurrentTilePanel = null;
        mTilePanelMenu = null;
        mCurrentMap = null;
        mMenuOpen = false;
        mCurrentAqObject = null;
        mSelectedTilePanel = null;

    }

    /**
     * ////////////////////////////////////////////////////////////////////////////////////////
     * Mouse Listeners
     * ////////////////////////////////////////////////////////////////////////////////////////
     */

    @Override
    public void mousePressed(MouseEvent e) {

        // reset specific objects
        mSelectedAqObject = null;

        if (mTilePanelMenu != null) {
            mTilePanelMenu.setShowPopup(false);
            mMenuOpen = false;
            mTilePanelMenu = null;
        }
        if (mMapPanelMenu != null) {
            mMapPanelMenu.setShowPopup(false);
            mMenuOpen = false;
            mMapPanelMenu = null;
        }

        // Press on ObjectLabel
        if (e.getSource() instanceof ObjectLabel) {
            mCurrentObjectLabel = (ObjectLabel) e.getSource();
            if (mCurrentObjectLabel.getAqObject() instanceof Door) {
                mCurrentDoor = (Door) mCurrentObjectLabel.getAqObject();
                for (MapPanel mp : mMapPanels) {
                    mp.setShowDoorOutline(true);
                }
            } else {
                mCurrentAqObject = mCurrentObjectLabel.getAqObject();
            }
        }
        // Press on MapLabel
        if (e.getSource() instanceof MapLabel) {
            // System.out.println("Clicked MapLabel");
            mCurrentMapLabel = (MapLabel) e.getSource();
            mCurrentMap = mCurrentMapLabel.getMap();
        }
        // Press on MapPanel
        if (e.getSource() instanceof MapPanel) {
            if (mCurrentMapPanel != null && mCurrentMapPanel.getTilePanels()[0] != null) {
                System.out.println("Pressed");
                mCurrentMap = mCurrentMapPanel.getMap();
                mCurrentMapPanel.removeTilePanels();
            }
        }
        // Press on Map Panel with right Click and shift
        if (e.getButton() == MouseEvent.BUTTON3 && mShiftPressed && !mMenuOpen) {
            if (mCurrentMapPanel != null) {
                mCurrentMapPanel.setPopupMenuLocation(e.getXOnScreen(), e.getYOnScreen());
                mCurrentMapPanel.setShowPopup(true);
                mMapPanelMenu = mCurrentMapPanel;
            }
            mMenuOpen = true;
        }

        // Press on Object on Tile Panel
        if (e.getSource() instanceof TilePanel) {
            if (mSelectedTilePanel != null && mSelectedTilePanel != (TilePanel) e.getSource()) {
                mSelectedTilePanel.deSelectAqObject();
            }
            mSelectedTilePanel = (TilePanel) e.getSource();
            mSelectedAqObject = mSelectedTilePanel.getAQ_ObjectAtLocation(e.getX(), e.getY());
        }

        // Press on Tile Panel with right Click
        if (e.getButton() == MouseEvent.BUTTON3 && e.getSource() instanceof TilePanel && !mShiftPressed && !mMenuOpen) {
            mCurrentTilePanel = (TilePanel) e.getSource();
            mCurrentTilePanel.setPopupMenuLocation(e.getXOnScreen(), e.getYOnScreen());
            mCurrentTilePanel.setShowPopup(true);
            mTilePanelMenu = mCurrentTilePanel;
            mMenuOpen = true;
        }

        // repaint aftre click
        if (mCurrentTilePanel != null) {
            mCurrentTilePanel.repaint();
        }
    }

    @Override
    public void mouseDragged(MouseEvent me) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Release on MapPanel
        if (mCurrentMap != null && mCurrentMapPanel != null) {
            mCurrentMapPanel.setMap(mCurrentMap);
            mCurrentMapPanel.setNeighbors();
            mMapPanels.add(mCurrentMapPanel);
        }
        // Release on TilePanel
        if (mCurrentTilePanel != null) {
            if (mCurrentAqObject != null) {
                mCurrentTilePanel.addAqObject(mCurrentAqObject);
            }
            if (mCurrentDoor != null) {
                mCurrentTilePanel.setDoorAtLocation(mCurrentDoor,
                        (int) (MouseInfo.getPointerInfo().getLocation().getX()
                                - mCurrentTilePanel.getLocationOnScreen().getX()),
                        (int) (MouseInfo.getPointerInfo().getLocation().getY()
                                - mCurrentTilePanel.getLocationOnScreen().getY()));
            }
        }
        if (mCurrentTilePanel != null) {

        }
        mCurrentMap = null;
        mCurrentAqObject = null;
        mCurrentDoor = null;
        for (MapPanel mp : mMapPanels) {
            mp.setShowDoorOutline(false);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // Enter ObjectLabel
        if (e.getSource() instanceof ObjectLabel) {
            // System.out.println("ObjectLabel");
        }
        // Enter TilePanel
        if (e.getSource() instanceof TilePanel) {
            TilePanel tile = (TilePanel) e.getSource();
            mCurrentTilePanel = tile;
            mCurrentMapPanel = (MapPanel) tile.getParent();
            mCurrentMainPanel = (JPanel) mCurrentMapPanel.getParent();
        }
        // Enter MapPanel
        if (e.getSource() instanceof MapPanel) {
            mCurrentMapPanel = (MapPanel) e.getSource();
            mCurrentMainPanel = (JPanel) mCurrentMapPanel.getParent();
        }

        // Enter MapLabel
        if (e.getSource() instanceof MapLabel) {
            // System.out.println("MapLabel");
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() instanceof MapLabel) {
            mCurrentMapLabel = null;
        }
        if (e.getSource() instanceof TilePanel || e.getSource() instanceof MapPanel) {
            mCurrentMapPanel = null;
            mCurrentTilePanel = null;
        }
    }

    /**
     * ////////////////////////////////////////////////////////////////////////////////////////
     * Key Listeners
     * ////////////////////////////////////////////////////////////////////////////////////////
     */

    @Override
    public void keyPressed(KeyEvent e) {
        // Backspace or delete (numpad) or delete
        if (e.getKeyCode() == 8 || e.getKeyCode() == 110 || e.getKeyCode() == 127) {
            // remove selected Object from Tile Panel
            if (mSelectedAqObject != null && mSelectedTilePanel != null) {
                mSelectedTilePanel.removeAqObject(mSelectedAqObject);
                mSelectedTilePanel.deSelectAqObject();
                mSelectedAqObject = null;
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
            mShiftPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        mShiftPressed = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
}
