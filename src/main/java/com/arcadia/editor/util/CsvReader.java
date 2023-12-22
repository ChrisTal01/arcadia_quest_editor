package com.arcadia.editor.util;

import com.arcadia.editor.entities.*;

import java.io.*;
import java.util.*;

public class CsvReader {

    private final static String MONSTERS_FOLDER_NAME = "/monsters";
    private final static String MONSTERS_CSV_NAME = "Monster.csv";

    private final static String TOKEN_FOLDER_NAME = "/tokens";
    private final static String TOKEN_CSV_NAME = "Token.csv";

    private final static String MAP_FOLDER_NAME = "/mapParts";

    private final static String DATA_SEPARATOR = ";";

    public static Map<Monster, Integer> readMonsterCSV(File path, GameType gameType) {
        File monsterFolder = new File(path,gameType.getFolderName()+MONSTERS_FOLDER_NAME);

        Map<Monster, Integer> monsterMap = new LinkedHashMap<>();
        BufferedReader csvReader = null;
        File csv = new File(monsterFolder,MONSTERS_CSV_NAME);
        if (csv.exists()) {
            try {
                csvReader = new BufferedReader(new FileReader(csv));
                String row;
                boolean skippedFirst = false;
                while ((row = csvReader.readLine()) != null) {
                    if (skippedFirst) {
                        String[] data = row.split(DATA_SEPARATOR);
                        String game = data[0];
                        String objectName = data[1];
                        String mainImagePath = new File(monsterFolder, data[2]).getPath();
                        String type = data[3];
                        MonsterSize size = MonsterSize.toMonsterSize(Integer.parseInt(data[4]));
                        int amount = Integer.parseInt(data[5]);
                        int prefIconWidth = Integer.parseInt(data[6]);
                        int prefIconHeight = Integer.parseInt(data[7]);
                        int prefImageWidth = Integer.parseInt(data[8]);
                        int prefImageHeight = Integer.parseInt(data[9]);

                        monsterMap.put(new Monster(mainImagePath, objectName, gameType,
                                size, type, prefIconWidth, prefIconHeight,
                                prefImageWidth, prefImageHeight), amount);
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

    public static Map<AQ_Object, Integer> readObjectCSV(File path, GameType gameType) {
        File tokenFolder = new File(path,gameType.getFolderName()+TOKEN_FOLDER_NAME);

        Map<AQ_Object, Integer> objectMap = new LinkedHashMap<>();
        BufferedReader csvReader = null;
        File csv = new File(tokenFolder, TOKEN_CSV_NAME);
        if (csv.exists()) {
            try {
                csvReader = new BufferedReader(new FileReader(csv));
                String row;
                boolean skippedFirst = false;
                while ((row = csvReader.readLine()) != null) {
                    if (skippedFirst) {
                        String[] data = row.split(DATA_SEPARATOR);
                        String game = data[0];
                        String objectName = data[1];
                        String mainImagePath = new File(tokenFolder, data[2]).getPath();
                        String secondImagePath = new File(tokenFolder, data[3]).getPath();
                        int amount = Integer.parseInt(data[4]);
                        int prefIconWidth = Integer.parseInt(data[5]);
                        int prefIconHeight = Integer.parseInt(data[6]);
                        int prefImageWidth = Integer.parseInt(data[7]);
                        int prefImageHeight = Integer.parseInt(data[8]);

                        if (data[1].contains("Door")) {
                            // if name contains Tür or Door
                            objectMap.put(new Door(mainImagePath, secondImagePath, objectName, gameType, prefIconWidth,
                                    prefIconHeight, prefImageWidth,prefImageHeight), amount);
                        } else {
                            if(gameType.equals(GameType.FROST_DRAGON)){ // TODO not accounted for FrostToken
                                objectMap.put(new FieldToken(new File(tokenFolder, data[2]), data[1], gameType,
                                        prefIconWidth, prefIconHeight, prefImageWidth,
                                        prefImageHeight), amount);
                            } else {
                                objectMap.put(new AQ_Object(new File(tokenFolder, data[2]), data[1], gameType,
                                        prefIconWidth, prefIconHeight, prefImageWidth,
                                        prefImageHeight), amount);
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

    public static List<MapObject> readMapsCSV(File path, GameType gameType){
        File mapFolder = new File(path,gameType.getFolderName()+MAP_FOLDER_NAME);

        List<MapObject> mapSet = new ArrayList<>();
        File[] maps = mapFolder.listFiles();
        if(maps != null){
            for (File f : maps) {
                mapSet.add(new MapObject(new File (mapFolder, f.getName()).getPath(), f.getName().replace("Map", "Mapteil_"), gameType));
            }
        }
        return mapSet;
    }
}
