package src.main.java.arcadia.application;

public enum TileOptions {
    NORMAL(0),

    GRAY(1),
    START(2),
    GREEN(3),
    VIOLET(4);
    private final int index;
    private TileOptions(int index){
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
