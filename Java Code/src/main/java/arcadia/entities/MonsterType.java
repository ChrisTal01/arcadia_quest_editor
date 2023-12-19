package src.main.java.arcadia.entities;

public enum MonsterType {

    MINOR_MINON("Minor Minion", 1),

    MAYOR_MINON("Major Minion",2),

    MINOR_VILLAIN("Minor Villion",3),

    MAYOR_VILLAIN("Major Villion",4),

    DRAGON("Dragon",5),

    OTHER("Other Monster",6);

    private final String name;

    public String getName() {
        return name;
    }

    public int getIndex() {
        return index;
    }

    private final int index;

    private MonsterType(String name, int index){
        this.index = index;
        this.name = name;
    }

    public static MonsterType toMonsterType(String name){
        for(MonsterType mt : values()){
            if(mt.getName().equalsIgnoreCase(name)){
                return mt;
            }
        }
        return MonsterType.OTHER;
    }
}
