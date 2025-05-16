package com.arcadia.editor.entities;

import javax.swing.*;
import java.awt.*;

public class MapOverview extends JPanel{

    private MapPanel[][] mapGrid;

    private final static int MAP_IDENTIFIER_SIZE = 40;
    private final static int TEXT_HEIGHT_OFFSET = 25;
    private final static int TEXT_WIDTH_OFFSET_2_LETTERS = 13;
    private final static int TEXT_WIDTH_OFFSET_3_LETTERS = 10;
    private final static int BOX_SIZE = 30;
    private final static int BOX_OFFSET =5;


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        for (int i = 0; i < mapGrid.length; i++) {
            for (int j = 0; j < mapGrid[i].length; j++) {
                MapObject current = mapGrid[i][j].getMap();
                if(current != null){
                    if(current.getIdentifier() != null){
                        g.drawRect(i*MAP_IDENTIFIER_SIZE+BOX_OFFSET,j*MAP_IDENTIFIER_SIZE+BOX_OFFSET,BOX_SIZE,BOX_SIZE);
                        if(current.getIdentifier().size() == 2){
                            g.drawString(current.getIdentifier().getName(),i*MAP_IDENTIFIER_SIZE+TEXT_WIDTH_OFFSET_2_LETTERS, j*MAP_IDENTIFIER_SIZE+TEXT_HEIGHT_OFFSET);
                        }
                        if(current.getIdentifier().size() == 3){
                            g.drawString(current.getIdentifier().getName(),i*MAP_IDENTIFIER_SIZE+TEXT_WIDTH_OFFSET_3_LETTERS, j*MAP_IDENTIFIER_SIZE+TEXT_HEIGHT_OFFSET);
                        }

                    }
                }

            }
        }

    }

    public void setMaps(MapPanel[][] mapGrid){
        this.mapGrid = mapGrid;
    }
}
