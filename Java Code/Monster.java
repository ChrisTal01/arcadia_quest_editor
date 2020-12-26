
public class Monster extends AQ_Object {
    private String mMonsterTypeString;
    private int mMonsterType;
    private int mSize;
    // private int mPrefIconWidth;
    // private int mPrefIconHeight;
    // private int mPrefTileWidth;
    // private int mPrefTileHeight;

    public static final int TINY = 0;
    public static final int NORMAL = 1;
    public static final int BIG = 2;

    public static final int MINOR_MINON = 1;
    public static final int MAYOR_MINON = 2;
    public static final int MINOR_VILLAIN = 3;
    public static final int MAYOR_VILLAIN = 4;
    public static final int OTHER = 5;

    public static final String MINOR_MINON_STRING = "Minor Minion";
    public static final String MAYOR_MINON_STRING = "Major Minion";
    public static final String MINOR_VILLAIN_STRING = "Minor Villion";
    public static final String MAYOR_VILLAIN_STRING = "Major Villion";
    public static final String OTHER_MONSTER_STRING = "Other Monster";

    public Monster(String pPath, String pName, int pAmount, int pGameBox, int pSize, int pMonsterType,
            int pPrefIconWidth, int pPrefIconHeight, int pPrefImageWidth, int pPrefImageHeight) {
        super(pPath, pName, pAmount, pGameBox, pPrefIconWidth, pPrefIconHeight, pPrefImageWidth, pPrefImageHeight);
        mSize = pSize;
        mMonsterType = pMonsterType;
        convertToMonsterTypeString(pMonsterType);
    }

    public Monster(String pPath, String pName, int pAmount, int pGameBox, int pSize, String pMonsterTypeString,
            int pPrefIconWidth, int pPrefIconHeight, int pPrefImageWidth, int pPrefImageHeight) {
        super(pPath, pName, pAmount, pGameBox, pPrefIconWidth, pPrefIconHeight, pPrefImageWidth, pPrefImageHeight);
        mSize = pSize;
        mMonsterTypeString = pMonsterTypeString;
        convertToMonsterType(pMonsterTypeString);

    }

    public Monster(Monster pMonster) {
        super(pMonster.getImagePath(), pMonster.getName(), pMonster.getAmount(), pMonster.getGameBox(),
                pMonster.getPrefIconWidth(), pMonster.getPrefIconHeight(), pMonster.getPrefImageWidth(),
                pMonster.getPrefImageHeight());
        copy(pMonster);
    }

    private void convertToMonsterTypeString(int pMonsterType) {
        if (pMonsterType == 1) {
            mMonsterTypeString = MINOR_MINON_STRING;
        } else if (pMonsterType == 2) {
            mMonsterTypeString = MAYOR_MINON_STRING;
        } else if (pMonsterType == 3) {
            mMonsterTypeString = MINOR_VILLAIN_STRING;
        } else if (pMonsterType == 4) {
            mMonsterTypeString = MAYOR_VILLAIN_STRING;
        } else {
            mMonsterTypeString = OTHER_MONSTER_STRING;
        }
    }

    private void convertToMonsterType(String pMonsterTypeString) {
        if (pMonsterTypeString.toUpperCase().equals(MINOR_MINON_STRING.toUpperCase())) {
            mMonsterType = MINOR_MINON;
        } else if (pMonsterTypeString.toUpperCase().equals(MAYOR_MINON_STRING.toUpperCase())) {
            mMonsterType = MAYOR_MINON;
        } else if (pMonsterTypeString.toUpperCase().equals(MINOR_VILLAIN_STRING.toUpperCase())) {
            mMonsterType = MINOR_VILLAIN;
        } else if (pMonsterTypeString.toUpperCase().equals(MAYOR_VILLAIN_STRING.toUpperCase())) {
            mMonsterType = MAYOR_VILLAIN;
        } else {
            mMonsterType = OTHER;
        }
    }

    public void setSize(int pSize) {
        mSize = pSize;
    }

    public void setMonsterType(int pMonsterType) {
        mMonsterType = pMonsterType;
    }

    public void setMonsterTypeString(String pMonsterTypeString) {
        mMonsterTypeString = pMonsterTypeString;
    }

    public int getSize() {
        return mSize;
    }

    public int getMonsterType() {
        return mMonsterType;
    }

    public String getMonsterTypeString() {
        return mMonsterTypeString;
    }

    public void copy(Monster pMonster) {
        this.setMonsterType(pMonster.getMonsterType());
        this.setMonsterTypeString(pMonster.getMonsterTypeString());
        this.setSize(pMonster.getSize());
    }

}
