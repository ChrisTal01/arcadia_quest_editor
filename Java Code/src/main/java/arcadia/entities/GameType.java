package src.main.java.arcadia.entities;

public enum GameType {
    ARCADIA_QUEST("Arcadia Quest",1),
    INFERNO("Inferno",2),
    BEYOND_THE_GRAVE("Beyond The Grave",3),
    PETS("Pets",4),
    WHOLE_LOTTA_LAVA("Whole Lotta Lava",5),
    RIDERS("Riders",6),
    HELL_OF_A_BOX("Hell Of A Box",7),
    FIRE_DRAGON("Fire Dragon",8),
    FROST_DRAGON("Frost Dragon",9),
    POISON_DRAGON("Poison Dragon",10),
    CHAOS_DRAGON("Chaos Dragon",11),
    OTHER("Other", 12);

    private final int index;

    private final String name;


    private GameType(String name, int index){
        this.name = name;
        this.index = index;
    }

    public String getFolderName(){
        return name.replace(" ", "");
    }

    public String getName() {
        return name;
    }

    public int getIndex(){
        return index;
    }
}
