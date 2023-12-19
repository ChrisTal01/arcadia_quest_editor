package src.main.java.arcadia.entities;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IGameBox {

    Map<Monster,Integer> getMonsters();

    int getMonsterAmount(Monster monster);

    Set<MapObject> getMaps();

    List<AQ_Object> getAQ_Objects();
    int getAqObjectAmount(AQ_Object object);

    GameType getmGameType();
}
