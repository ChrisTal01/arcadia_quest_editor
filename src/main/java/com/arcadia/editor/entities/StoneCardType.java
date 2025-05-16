package com.arcadia.editor.entities;

public enum StoneCardType {

    BRIMSTONE_CARD,
    TOMBSTONE_CARD,
    POISON_DRAGONSTONE_CARD,
    FROST_DRAGONSTONE_CARD,
    FIRE_DRAGONSTONE_CARD,
    CHAOS_DRAGONSTONE_CARD,
    OTHER_STONE_CARD;

    public static StoneCardType toStoneCard(GameType gameType){
        for(StoneCardType sc : values()){
            return switch (gameType) {
                case GameType.BEYOND_THE_GRAVE -> BRIMSTONE_CARD;
                case GameType.INFERNO -> TOMBSTONE_CARD;
                case GameType.POISON_DRAGON -> POISON_DRAGONSTONE_CARD;
                case GameType.CHAOS_DRAGON -> CHAOS_DRAGONSTONE_CARD;
                case GameType.FROST_DRAGON -> FROST_DRAGONSTONE_CARD;
                case GameType.FIRE_DRAGON -> FIRE_DRAGONSTONE_CARD;
                default -> OTHER_STONE_CARD;
            };
        }
        return OTHER_STONE_CARD;
    }
}
