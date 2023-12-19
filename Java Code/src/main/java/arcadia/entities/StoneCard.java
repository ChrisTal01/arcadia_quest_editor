package src.main.java.arcadia.entities;

public class StoneCard extends AQ_Object implements ITileObject{

    private StoneCardType mStoneType;

    public StoneCard(String pPath, String pName, GameType pGameBox, StoneCardType pStoneType, int pPrefIconWidth,
                     int pPrefIconHeight, int pPrefImageWidth, int pPrefImageHeight) {
        super(pPath, pName, pGameBox, pPrefIconWidth, pPrefIconHeight, pPrefImageWidth, pPrefImageHeight);
        mStoneType = pStoneType;
    }

    public StoneCard(StoneCard pStoneCard) {
        super(pStoneCard.getImagePath(), pStoneCard.getName(), pStoneCard.getGameBox(),
                pStoneCard.getPrefIconWidth(), pStoneCard.getPrefIconHeight(), pStoneCard.getPrefImageWidth(),
                pStoneCard.getPrefImageHeight());
        copy(pStoneCard);
    }

    public void setStoneType(StoneCardType pStoneType) {
        mStoneType = pStoneType;
    }

    public StoneCardType getStoneType() {
        return mStoneType;
    }

    public void copy(StoneCard pStoneCard) {
        this.setStoneType(pStoneCard.getStoneType());
    }

    @Override
    public int getUsedSpace() {
        return 0;
    }

    @Override
    public int getObjectScale() {
        return 1;
    }
}
