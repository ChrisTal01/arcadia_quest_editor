package src.main.java.arcadia.util;

import src.main.java.arcadia.entities.*;

import java.io.*;
import java.util.*;

public class CsvReader {

    public static Map<Monster, Integer> readMonsterCSV(File path, String pName, GameType pGameType) {
        Map<Monster, Integer> monsterMap = new LinkedHashMap<>();
        BufferedReader csvReader = null;
        File csv = new File(path,pName);
        if (csv.exists()) {
            try {
                csvReader = new BufferedReader(new FileReader(csv));
                String row;
                boolean skippedFirst = false;
                while ((row = csvReader.readLine()) != null) {
                    if (skippedFirst) {
                        String[] data = row.split(";");
                        // (0)Game, (1)Name, (2)Picture, (3)Type, (4)Size, (5)Amount,(6) PrefIconWidth,
                        // (7) PrefIconHeight,(8) PrefImageWidth, (9) PrefImageHeight
                        monsterMap.put(new Monster(new File(path , data[2]).getPath(), data[1], pGameType,
                                MonsterSize.toMonsterSize(Integer.parseInt(data[4])), data[3], Integer.parseInt(data[6]), Integer.parseInt(data[7]),
                                Integer.parseInt(data[8]), Integer.parseInt(data[9])), Integer.parseInt(data[5]));
                    } else {
                        skippedFirst = true;
                    }
                }
            } catch (NumberFormatException | IOException e) {
                e.printStackTrace();
            } finally {
                if (csvReader != null) {
                    try {
                        csvReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return monsterMap;
    }

    public static Map<AQ_Object, Integer> readObjectCSV(File path, String pName, GameType pGameType) {
        Map<AQ_Object, Integer> objectMap = new LinkedHashMap<>();
        BufferedReader csvReader = null;
        //System.out.println(pGameType.getName());
        File csv = new File(path, pName);
        if (csv.exists()) {
            try {
                csvReader = new BufferedReader(new FileReader(csv));
                String row;
                boolean skippedFirst = false;
                while ((row = csvReader.readLine()) != null) {
                    if (skippedFirst) {
                        String[] data = row.split(";");
                        // (0)Game, (1)Name, (2)Picture, (3)Picture2, (4)Amount, (5)PrefIconWidth, (6)
                        // PrefIconHeight, (7) PrefImageWidth, (8) PrefImageHeight
                        if (data[1].contains("Door")) {
                            // if name contains Tür or src.main.java.arcadia.entities.Door
                            objectMap.put(new Door(new File(path, data[2]).getPath(), new File(path, data[3]).getPath(), data[1],
                                    pGameType, Integer.parseInt(data[5]), Integer.parseInt(data[6]), Integer.parseInt(data[7]),
                                    Integer.parseInt(data[8])), Integer.parseInt(data[4]));
                        } else {
                            if(pGameType.equals(GameType.FROST_DRAGON)){ // TODO not accounted for FrostToken
                                objectMap.put(new FieldToken(new File(path, data[2]), data[1], pGameType,
                                        Integer.parseInt(data[5]), Integer.parseInt(data[6]), Integer.parseInt(data[7]),
                                        Integer.parseInt(data[8])), Integer.parseInt(data[4]));
                            } else {
                                objectMap.put(new AQ_Object(new File(path, data[2]), data[1], pGameType,
                                        Integer.parseInt(data[5]), Integer.parseInt(data[6]), Integer.parseInt(data[7]),
                                        Integer.parseInt(data[8])), Integer.parseInt(data[4]));
                            }
                        }
                    } else {
                        skippedFirst = true;
                    }
                }
            } catch (IOException | NumberFormatException e) {
                e.printStackTrace();
            } finally {
                if (csvReader != null) {
                    try {
                        csvReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return objectMap;
    }

    public static List<MapObject> readMapsCSV(String path, GameType gameType){
        List<MapObject> mapSet = new ArrayList<>();
        File mapFile = new File(path + "MapParts\\");
        File[] maps = mapFile.listFiles();
        if(maps != null){
            for (File f : maps) {
                System.out.println(f.getName());
                mapSet.add(new MapObject(path, f.getName().replace("Map", "Mapteil"), gameType));
            }
        }
        return mapSet;
    }
}
