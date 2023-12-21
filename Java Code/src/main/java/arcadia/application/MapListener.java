package src.main.java.arcadia.application;

import src.main.java.arcadia.entities.*;

import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JPanel;

import java.awt.MouseInfo;

public class MapListener implements MouseMotionListener, MouseListener, KeyListener {

    // Current means under the cursor or dragged

    private MapLabel mCurrentMapLabel;
    private MapPanel mCurrentMapPanel;
    private Tile mCurrentTile;
    private ObjectLabel mCurrentObjectLabel;
    private AQ_Object mCurrentAqObject;
    private MapObject mCurrentMapObject;
    private Door mCurrentDoor;
    private JPanel mCurrentMainPanel;

    // Selected means clicked on
    private Tile mSelectedTile;
    private AQ_Object mSelectedAqObject;

    private boolean mShiftPressed;
    private boolean mMenuOpen;

    private Tile mTileMenu;
    private MapPanel mMapPanelMenu;

    private ArrayList<MapPanel> mMapPanels = new ArrayList<>();

    public MapListener() {
        mCurrentMapPanel = null;
        mCurrentTile = null;
        mTileMenu = null;
        mCurrentMapObject = null;
        mMenuOpen = false;
        mCurrentAqObject = null;
        mSelectedTile = null;
        mShiftPressed = false;

    }

    /**
     * ////////////////////////////////////////////////////////////////////////////////////////
     * Mouse Listeners
     * ////////////////////////////////////////////////////////////////////////////////////////
     */

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("###########");
        System.out.println("mousePressed");

        // reset specific objects
        mSelectedAqObject = null;

        if (mTileMenu != null) {
            mTileMenu.setShowPopup(false);
            mMenuOpen = false;
            mTileMenu = null;
        }

        if (mMapPanelMenu != null) {
            mMapPanelMenu.setShowPopup(false);
            mMenuOpen = false;
            mMapPanelMenu = null;
        }

        // Press on ObjectLabel
        if (e.getSource() instanceof ObjectLabel) {
            //System.out.println("Mouse Cliecked ObjectLabel");
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
            //System.out.println("Mouse Cliecked MapLabel");
            mCurrentMapLabel = (MapLabel) e.getSource();
            mCurrentMapObject = mCurrentMapLabel.getMap();
        }
        // Press on MapPanel
        if (e.getSource() instanceof MapPanel) {
            // System.out.println("Mouse Pressed MapPanel");
        }
        // Press on Map Panel with right Click and shift
        if (e.getButton() == MouseEvent.BUTTON3 && mShiftPressed && !mMenuOpen) {
            if (mCurrentMapPanel != null && mCurrentMapPanel.getMap() != null) {
                mCurrentMapPanel.setPopupMenuLocation(e.getXOnScreen(), e.getYOnScreen());
                mCurrentMapPanel.setShowPopup(true);
                mMapPanelMenu = mCurrentMapPanel;
                mMenuOpen = true;
            }
        }
        // Press on Tile with right Click and shift
        if (e.getButton() == MouseEvent.BUTTON3 && !mShiftPressed && !mMenuOpen && e.getSource() instanceof MapPanel) {
            //System.out.println("Pressed right");
            mCurrentMapPanel = (MapPanel) e.getSource();
            mCurrentTile = mCurrentMapPanel.getTileAtLocation(e.getX(), e.getY());
            if (mCurrentTile != null) {
                mCurrentTile.setPopupMenuLocation(e.getXOnScreen(), e.getYOnScreen());
                mCurrentTile.setShowPopup(true);
                mTileMenu = mCurrentTile;
                mMenuOpen = true;
            }
        }

        // Press on Object on Tile
        if (e.getSource() instanceof MapPanel) {
            mCurrentMapPanel = (MapPanel) e.getSource();
            mCurrentTile = mCurrentMapPanel.getTileAtLocation(e.getX(), e.getY());
            if (mSelectedTile != null && mSelectedTile != mCurrentTile) {
                mSelectedTile.deSelectObject();
            }
            mSelectedTile = mCurrentMapPanel.getTileAtLocation(e.getX(), e.getY());
            if (mSelectedTile != null) {
                mSelectedAqObject = mSelectedTile.getAQ_ObjectAtLocation(e.getX(), e.getY());
            }
            mCurrentMapPanel.revalidate();
            mCurrentMapPanel.repaint();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println("###########");
        System.out.println("mouseReleased");
        // Release on MapPanel
        if (mCurrentMapObject != null && mCurrentMapPanel != null) {
            mCurrentMapPanel.setMap(mCurrentMapObject);
            mCurrentMapPanel.setNeighbors();
            mMapPanels.add(mCurrentMapPanel);
        }

        if (mCurrentMapPanel != null) {
            int x = (int) (MouseInfo.getPointerInfo().getLocation().getX()
                    - mCurrentMapPanel.getLocationOnScreen().getX());
            int y = (int) (MouseInfo.getPointerInfo().getLocation().getY()
                    - mCurrentMapPanel.getLocationOnScreen().getY());
            mCurrentTile = mCurrentMapPanel.getTileAtLocation(x, y);

            if (mCurrentAqObject != null && mCurrentTile != null) {
                mCurrentTile.addAqObject(mCurrentAqObject);
                mCurrentMapPanel.revalidate();
                mCurrentMapPanel.repaint();
            }

            if (mCurrentDoor != null && mCurrentTile != null) {
                System.out.println("Drop Door at x:" + x + ", y: "+ y);
                mCurrentTile.setDoorAtLocation(mCurrentDoor, x, y);
                // TODO Implement Door creation for adjacent MapObject/Tiles
            }
        }

        mCurrentMapObject = null;
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
        // Enter MapPanel
        if (e.getSource() instanceof MapPanel) {
            mCurrentMapPanel = (MapPanel) e.getSource();
            mCurrentMainPanel = (JPanel) mCurrentMapPanel.getParent();
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() instanceof MapLabel) {
            mCurrentMapLabel = null;
        }
        if (e.getSource() instanceof Tile || e.getSource() instanceof MapPanel) {
            mCurrentMapPanel = null;
            mCurrentTile = null;
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
            if (mSelectedAqObject != null && mSelectedTile != null) {
                mSelectedTile.removeAqObject(mSelectedAqObject);
                mSelectedTile.deSelectObject();
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
