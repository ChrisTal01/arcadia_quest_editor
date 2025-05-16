package com.arcadia.editor.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scenario {

    private String scenarioName;

    private List<MapObject> tileGrid;

    private MapIdentifier[][] mapGrid;

    private Map<AQ_Object,Integer> objectList;

    private List<String> quests;

    private List<String> rewardCards;

    private List<String> specialSetup;

    private List<String> specialRules;

    private List<String> titleAdvantages;

    private String title;

    public Scenario(String name){

        this.scenarioName = name;
        tileGrid = new ArrayList<>();
    }
    public Scenario(){
    }


    public String getScenarioName() {
        return scenarioName;
    }

    public void setScenarioName(String scenarioName) {
        this.scenarioName = scenarioName;
    }

    public List<MapObject>  getTileGrid() {
        return tileGrid;
    }

    public void setTileGrid(List<MapObject> tileGrid) {
        this.tileGrid = tileGrid;
    }

    public void addMapToGrid(MapObject map) {
        this.tileGrid.add(map);
    }


    public MapIdentifier[][] getMapGrid() {
        return mapGrid;
    }

    public void setMapGrid(MapIdentifier[][] mapGrid) {
        this.mapGrid = mapGrid;
    }

    public Map<AQ_Object, Integer> getObjectList() {
        return objectList;
    }

    public void setObjectList(Map<AQ_Object, Integer> objectList) {
        this.objectList = objectList;
    }

    public List<String> getQuests() {
        return quests;
    }

    public void setQuests(List<String> quests) {
        this.quests = quests;
    }

    public List<String> getRewardCards() {
        return rewardCards;
    }

    public void setRewardCards(List<String> rewardCards) {
        this.rewardCards = rewardCards;
    }

    public List<String> getSpecialSetup() {
        return specialSetup;
    }

    public void setSpecialSetup(List<String> specialSetup) {
        this.specialSetup = specialSetup;
    }

    public List<String> getSpecialRules() {
        return specialRules;
    }

    public void setSpecialRules(List<String> specialRules) {
        this.specialRules = specialRules;
    }

    public List<String> getTitleAdvantages() {
        return titleAdvantages;
    }

    public void setTitleAdvantages(List<String> titleAdvantages) {
        this.titleAdvantages = titleAdvantages;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Map<Monster,Integer> getMonsters(){
        Map<Monster, Integer> monsters = new HashMap<>();
        for(MapObject map: tileGrid){
            for(Map.Entry<Monster,Integer> entry : map.getMonsters().entrySet()){
                int count = monsters.getOrDefault(entry.getKey(), 0);
                monsters.put(entry.getKey(), count + entry.getValue());
            }
        }
        return monsters;
    }

    public Map<Portal,Integer> getPortals(){
        Map<Portal, Integer> portals = new HashMap<>();
        for(MapObject map: tileGrid){
            for(Map.Entry<Portal,Integer> entry : map.getPortals().entrySet()){
                int count = portals.getOrDefault(entry.getKey(), 0);
                portals.put(entry.getKey(), count + entry.getValue());
            }
        }
        return portals;
    }

    public Map<Door, Double> getDoors(){
        Map<Door, Double> doors = new HashMap<>();
        for(MapObject map: tileGrid){
            for(Map.Entry<Door,Double> entry : map.getDoors().entrySet()){
                double count = doors.getOrDefault(entry.getKey(), 0.0);
                doors.put(entry.getKey(), count + entry.getValue());
            }
        }
        return doors;
    }

    public Map<StoneCard, Integer> getStoneCards(){
        Map<StoneCard, Integer> stoneCards = new HashMap<>();
        for(MapObject map: tileGrid){
            for(Map.Entry<StoneCard,Integer> entry : map.getStoneCards().entrySet()){
                int count = stoneCards.getOrDefault(entry.getKey(), 0);
                stoneCards.put(entry.getKey(), count + entry.getValue());
            }
        }
        return stoneCards;
    }

    public Map<Token, Integer> getTokens(){
        Map<Token, Integer> tokens = new HashMap<>();
        for(MapObject map: tileGrid){
            for(Map.Entry<Token,Integer> entry : map.getTokens().entrySet()){
                int count = tokens.getOrDefault(entry.getKey(), 0);
                tokens.put(entry.getKey(), count + entry.getValue());
            }
        }
        return tokens;
    }
}
