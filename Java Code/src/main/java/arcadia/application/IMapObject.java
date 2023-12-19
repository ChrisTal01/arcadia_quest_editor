package src.main.java.arcadia.application;

import src.main.java.arcadia.entities.Tile;

public interface IMapObject {

    Tile getTileAtPos(int pPos);

    void setTiles(Tile[] pTiles);
}
