package src.main.java.arcadia.entities;

public enum MonsterSize {

    TINY(1,0),

    NORMAL(1,1),

    BIG(1,2),

    GARGANTUAN(2,4),

    OTHER(0,-1);

    private final int usedTiles;

    private final int slots;

    public int getUsedTiles() {
        return usedTiles;
    }

    public int getSlots() {
        return slots;
    }

    private MonsterSize(int usedTiles, int slots){
        this.usedTiles = usedTiles;
        this.slots = slots;
    }

    public static MonsterSize toMonsterSize(int slots){
        for(MonsterSize ms : values()){
            if(ms.getSlots() == slots){
                return ms;
            }
        }
        return MonsterSize.OTHER;
    }
}
