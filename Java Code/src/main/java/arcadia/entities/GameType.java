package src.main.java.arcadia.entities;

public enum GameType {
    ARCADIA_QUEST(1),
    INFERNO(2),
    BEYOND_THE_GRAVE(3),
    PETS(4),
    WHOLE_LOTTA_LAVA(5),
    RAIDERS(6),
    FALL_OF_ARCADIA(7),
    FIRE_DRAGON(8),
    FROST_DRAGON(9),
    POISON_DRAGON(10),
    CHAOS_DRAGON(11),
    OTHER(12);

    private final int index;

    private GameType(int index){
        this.index = index;
    }

    public int getIndex(){
        return index;
    }
}
