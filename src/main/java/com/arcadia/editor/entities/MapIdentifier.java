package com.arcadia.editor.entities;

public class MapIdentifier {

    private final String mapNumber;

    private final MapSide mapSide;

    public MapIdentifier(String mapNumber, MapSide mapSide){
        this.mapNumber = mapNumber;
        this.mapSide = mapSide;
    }

    public int size(){
        return mapNumber.length() + 1;
    }

    public String getName(){
        return mapNumber+mapSide.toString();
    }

    public enum MapSide {
        A,
        B;
    }
}
