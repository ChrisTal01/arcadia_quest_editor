package com.arcadia.editor.util;

import com.arcadia.editor.entities.AQ_Object;
import com.arcadia.editor.entities.GameType;
import com.arcadia.editor.entities.MapObject;
import com.arcadia.editor.entities.Monster;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CsvReaderTest {

    @Test
    void readMonsterCsvCorrect() {
        File file = new File("src/test/resources/com/arcadia/editor/data");
        assertTrue(file.exists());

        assertDoesNotThrow(() -> {
            Map<Monster, Integer> monsters = CsvReader.readMonsterCSV(file, GameType.ARCADIA_QUEST);
            assertNotNull(monsters);
            assertEquals(10, monsters.size());
        });

    }

    @Test
    void readObjectCsvCorrect() {
        File file = new File("src/test/resources/com/arcadia/editor/data");
        assertTrue(file.exists());
        assertDoesNotThrow(() -> {
            Map<AQ_Object, Integer> tokens = CsvReader.readObjectCSV(file, GameType.ARCADIA_QUEST);
            assertNotNull(tokens);
            assertEquals(20, tokens.size());
        });
    }

    @Test
    void readMapsCsvCorrect() {
        File file = new File("src/test/resources/com/arcadia/editor/data");
        assertTrue(file.exists());
        assertDoesNotThrow(() -> {
            List<MapObject> maps = CsvReader.readMapsCSV(file, GameType.ARCADIA_QUEST);
            assertNotNull(maps);
            assertEquals(18, maps.size());
        });
    }
}