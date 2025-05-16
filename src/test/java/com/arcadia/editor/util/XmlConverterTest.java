package com.arcadia.editor.util;

import com.arcadia.editor.entities.*;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class XmlConverterTest {

    @Test
    void createScenarioXmlSuccess() {
        Scenario scenario = new Scenario("Distrikt der Hämmer");
        File file = new File("src/test/resources/com/arcadia/editor/data");
        List<MapObject> maps = CsvReader.readMapsCSV(file, GameType.ARCADIA_QUEST);
        Map<Monster, Integer> monsters = CsvReader.readMonsterCSV(file, GameType.ARCADIA_QUEST);
        Map<AQ_Object, Integer> objects = CsvReader.readObjectCSV(file, GameType.ARCADIA_QUEST);
        List<AQ_Object> aqObjects = objects.keySet().stream().toList();

        List<AQ_Object> portals = aqObjects.stream().filter(o -> o instanceof Portal).toList();
        Portal blue = (Portal) portals.get(0);

        List<AQ_Object> tokens = aqObjects.stream().filter(o -> o instanceof Token).toList();
        Token brut = (Token) tokens.get(0);

        List<AQ_Object> doors = aqObjects.stream().filter(o -> o instanceof Door).toList();
        Door d = (Door) doors.get(0);


        List<Monster> m = monsters.keySet().stream().toList();
        Monster goblin = m.get(0);
        Monster ork_marodeur = m.get(1);


        MapObject map01 = maps.get(0);
        map01.setRow(0);
        map01.setColumn(0);
        // Add two Goblins to Top-Right
        Tile t = map01.getTileAtPos(0);
        t.addAqObject(goblin);
        t.addAqObject(goblin);
        t.setDoorAtPos(d,1);
        map01.setTileAt(0,t);
        System.out.println(map01.getDoors().size());

        // Add one Brut to Top-Middle
        Tile t2 = map01.getTileAtPos(1);
        t2.addAqObject(brut);
        t2.setDoorAtPos(d,3);
        map01.setTileAt(1,t2);
        // Add one Blue Portal to Middle
        Tile tm = map01.getTileAtPos(4);
        tm.addAqObject(blue);
        tm.addAqObject(ork_marodeur);
        map01.setTileAt(4,tm);


        scenario.addMapToGrid(map01);


        MapObject map02 = maps.get(2);
        map02.setRow(0);
        map02.setColumn(1);
        scenario.addMapToGrid(map02);

        File outputFile = new File("scenario.xml");

        assertDoesNotThrow(() -> {
            XmlConverter.convertScenario(outputFile,scenario);
        });
    }

}