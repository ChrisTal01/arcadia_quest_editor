
public class Map extends AQ_Object {

    private static final String IMAGE_PATH = "MapParts\\Map";
    private Map mCounterPart;
    private Tile[] mTiles = new Tile[9];

    public Map(String pPath, String pName, int pGameBox) {
        super(pPath + IMAGE_PATH + pName.substring(pName.lastIndexOf(" ") + 1, pName.length()) + "\\map\\"
                + pName.replace(" ", "_") + "_Normal.png", pName, 1, pGameBox, 1, 1);

        for (int i = 0; i < mTiles.length; i++) {
            mTiles[i] = new Tile(pPath + IMAGE_PATH + pName.substring(pName.lastIndexOf(" ") + 1, pName.length()),
                    String.valueOf(i), pGameBox);
        }
        mCounterPart = null;
    }

    public Map(Map pMap) {
        super(pMap.getImagePath(), pMap.getName(), pMap.getAmount(), pMap.getGameBox(), 1, 1);
        copy(pMap);
    }

    /**
     * ////////////////////////////////////////////////////////////////////////////////////////
     * Getter and Setter
     * ////////////////////////////////////////////////////////////////////////////////////////
     */

    public Map getCounterPart() {
        return mCounterPart;
    }

    public void setCounterPart(Map pMap) {
        mCounterPart = pMap;
    }

    public Tile[] getTiles() {
        return mTiles;
    }

    public Tile getTileAtPos(int pPos) {
        return mTiles[pPos];
    }

    public void setTiles(Tile[] pTiles) {
        mTiles = pTiles;
    }

    public void copy(Map pMap) {
        Tile[] newTiles = new Tile[9];
        for (int i = 0; i < newTiles.length; i++) {
            newTiles[i] = new Tile(pMap.getTileAtPos(i));
        }
        this.setTiles(newTiles);
        // this.setCounterPart(new Map(pMap.getCounterPart()));
    }
}
