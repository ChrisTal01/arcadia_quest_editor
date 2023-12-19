package src.main.java.arcadia.entities;

public class Monster extends AQ_Object implements ITileObject{
    private MonsterType mMonsterType;
    private MonsterSize mSize;
    // private int mPrefIconWidth;
    // private int mPrefIconHeight;
    // private int mPrefTileWidth;
    // private int mPrefTileHeight;


    public Monster(String pPath, String pName, GameType pGameBox, MonsterSize pSize, MonsterType pMonsterType,
                   int pPrefIconWidth, int pPrefIconHeight, int pPrefImageWidth, int pPrefImageHeight) {
        super(pPath, pName, pGameBox, pPrefIconWidth, pPrefIconHeight, pPrefImageWidth, pPrefImageHeight);
        mSize = pSize;
        mMonsterType = pMonsterType;
    }

    public Monster(String pPath, String pName, GameType pGameBox, MonsterSize pSize, String pMonsterTypeString,
                   int pPrefIconWidth, int pPrefIconHeight, int pPrefImageWidth, int pPrefImageHeight) {
        super(pPath, pName, pGameBox, pPrefIconWidth, pPrefIconHeight, pPrefImageWidth, pPrefImageHeight);
        mSize = pSize;
        mMonsterType = MonsterType.toMonsterType(pMonsterTypeString);

    }

    public Monster(Monster pMonster) {
        super(pMonster.getImagePath(), pMonster.getName(), pMonster.getGameBox(),
                pMonster.getPrefIconWidth(), pMonster.getPrefIconHeight(), pMonster.getPrefImageWidth(),
                pMonster.getPrefImageHeight());
        copy(pMonster);
    }

    public void setSize(MonsterSize pSize) {
        mSize = pSize;
    }

    public void setMonsterType(MonsterType pMonsterType) {
        mMonsterType = pMonsterType;
    }

    public int getSize() {
        return mSize.getSlots();
    }

    public MonsterType getMonsterType() {
        return mMonsterType;
    }

    public String getMonsterTypeString() {
        return mMonsterType.getName();
    }

    public void copy(Monster pMonster) {
        this.setMonsterType(pMonster.getMonsterType());
        this.setSize(MonsterSize.toMonsterSize(pMonster.getSize()));
    }

    @Override
    public int getUsedSpace() {
        return mSize.getSlots();
    }

    @Override
    public int getObjectScale() {
        return 1;
    }
}
