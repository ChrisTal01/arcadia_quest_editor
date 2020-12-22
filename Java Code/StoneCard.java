package Editor;

import java.io.File;

public class StoneCard extends AQ_Object {

    private final int mStoneType;

    private static final int BRIMSTONE_CARD = 1;
    private static final int TOMBSTONE_CARD = 2;
    private static final int POISON_DRAGONSTONE_CARD = 3;
    private static final int FROST_DRAGONSTONE_CARD = 4;
    private static final int FIRE_DRAGONSTONE_CARD = 5;
    private static final int CHAOS_DRAGONSTONE_CARD = 6;

    private static final int OTHER_STONE_CARD = 7;

    public StoneCard(String pPath, String pName, int pAmount, int pGameBox, int pStoneType) {
        super(pPath, pName, pAmount, pGameBox);
        mStoneType = pStoneType;
    }

    public int getStoneType() {
        return mStoneType;
    }
}
