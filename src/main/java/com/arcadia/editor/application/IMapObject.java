package com.arcadia.editor.application;

import com.arcadia.editor.entities.Tile;

public interface IMapObject {

    Tile getTileAtPos(int pPos);

    void setTiles(Tile[] pTiles);
}
