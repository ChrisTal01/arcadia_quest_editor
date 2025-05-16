package com.arcadia.editor.entities;

import com.arcadia.editor.util.CsvReader;
import com.arcadia.editor.util.XmlConverter;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class TileTest {

    private static final File file = new File("src/test/resources/com/arcadia/editor/data");

    @Test
    void addDoorRightSuccess() {

        // Get all Doors
        List<AQ_Object> doors = getAllDoors();
        Door door = (Door) doors.get(0);

        // get Map
        MapObject mapObject = getMapObject();
        assertNotNull(mapObject);
        assertTrue(mapObject.getDoors().isEmpty());


        // Select Tile 1 and 2
        Tile tTopLeft = mapObject.getTileAtPos(0);
        Tile tTopMiddle = mapObject.getTileAtPos(1);

        assertNotNull(tTopLeft);
        assertNotNull(tTopMiddle);

        // Should not have any Doors
        assertTrue(tTopLeft.getDoors().isEmpty());
        assertTrue(tTopMiddle.getDoors().isEmpty());

        // Add Door
        mapObject.setDoorAt(door,tTopLeft,125,51);

        assertFalse(tTopLeft.getDoors().isEmpty());
        assertFalse(tTopMiddle.getDoors().isEmpty());

        assertFalse(mapObject.getDoors().isEmpty());
    }

    @Test
    void addDoorTopSuccess() {

        // Get all Doors
        List<AQ_Object> doors = getAllDoors();
        Door door = (Door) doors.get(0);

        // get Map
        MapObject mapObject = getMapObject();
        assertNotNull(mapObject);
        assertTrue(mapObject.getDoors().isEmpty());

        // Select Tile 1 and 2
        Tile tMiddle = mapObject.getTileAtPos(4);
        Tile tTopMiddle = mapObject.getTileAtPos(1);

        assertNotNull(tMiddle);
        assertNotNull(tTopMiddle);

        // Should not have any Doors
        assertTrue(tMiddle.getDoors().isEmpty());
        assertTrue(tTopMiddle.getDoors().isEmpty());

        // Add Door
        for(int i = 0; i < 208 ; i++){
            for(int j = 0; j < 10 ; j++) {
                System.out.println(i + " ; "+j);
                System.out.println(tMiddle.getDoorPosition(i, j)+ ";");
            }
        }
        mapObject.setDoorAt(door,tMiddle,179,135);

        assertFalse(tMiddle.getDoors().isEmpty());
        assertFalse(tTopMiddle.getDoors().isEmpty());

        assertFalse(mapObject.getDoors().isEmpty());
    }

    private static MapObject getMapObject(){
        List<MapObject> maps = CsvReader.readMapsCSV(file, GameType.ARCADIA_QUEST);
        MapObject mapObject = maps.get(0);
        mapObject.setRow(0);
        mapObject.setColumn(0);
        return  mapObject;
    }

    private static List<AQ_Object> getAllDoors(){
        Map<AQ_Object, Integer> objects = CsvReader.readObjectCSV(file, GameType.ARCADIA_QUEST);
        List<AQ_Object> aqObjects = objects.keySet().stream().toList();

        return aqObjects.stream().filter(o -> o instanceof Door).toList();
    }

    private enum Direction{
        TOP(0),
        RIGHT(1),
        BOTTOM(2),
        LEFT(3);

        private final int pos;

        private Direction(int pos){
            this.pos = pos;
        }

        public int getPos(){
            return pos;
        }
    }
}
