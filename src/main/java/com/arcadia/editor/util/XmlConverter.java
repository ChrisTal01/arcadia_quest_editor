package com.arcadia.editor.util;

import com.arcadia.editor.entities.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.Map;

public class XmlConverter {

    public static void convertScenario(File file, Scenario scenario) throws ParserConfigurationException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        // root elements
        Document doc = docBuilder.newDocument();
        Element rootElement = doc.createElement("scenario");
        doc.appendChild(rootElement);

        Element name = doc.createElement("name");
        name.appendChild(doc.createTextNode(scenario.getScenarioName()));
        rootElement.appendChild(name);

        Element maps = doc.createElement("maps");
        setupMaps(maps, scenario,doc);
        rootElement.appendChild(maps);

        Element mapOverview = doc.createElement("mapOverview");
        for(MapObject m : scenario.getTileGrid()){
            Element map = doc.createElement("mapPart");
            map.setAttribute("name", m.getName());

            mapOverview.appendChild(map);
        }
        rootElement.appendChild(mapOverview);

        Element monsters = doc.createElement("monsters");
        for(Map.Entry<Monster, Integer> entry : scenario.getMonsters().entrySet()){
            Monster m = entry.getKey();
            Element monster = doc.createElement("monster");
            monster.setAttribute("name", m.getName());
            monster.setAttribute("amount", String.valueOf(entry.getValue()));

            monsters.appendChild(monster);
        }
        rootElement.appendChild(monsters);

        Element portals = doc.createElement("portals");
        for(Map.Entry<Portal, Integer> entry : scenario.getPortals().entrySet()){
            Portal p = entry.getKey();
            Element portal = doc.createElement("portal");
            portal.setAttribute("name", p.getName());
            portal.setAttribute("amount", String.valueOf(entry.getValue()));

            portals.appendChild(portal);
        }
        rootElement.appendChild(portals);

        Element tokens = doc.createElement("tokens");
        for(Map.Entry<Token, Integer> entry : scenario.getTokens().entrySet()){
            Token t = entry.getKey();
            Element token = doc.createElement("token");
            token.setAttribute("name", t.getName());
            token.setAttribute("amount", String.valueOf(entry.getValue()));

            tokens.appendChild(token);
        }
        rootElement.appendChild(tokens);

        if(!scenario.getDoors().isEmpty()) {
            Element doors = doc.createElement("doors");
            for (Map.Entry<Door, Double> entry : scenario.getDoors().entrySet()) {
                Door d = entry.getKey();
                Element door = doc.createElement("door");
                door.setAttribute("name", d.getName());
                door.setAttribute("amount", String.valueOf(entry.getValue().intValue()));

                doors.appendChild(door);
            }
            rootElement.appendChild(doors);
        }

        if(!scenario.getStoneCards().isEmpty()){
            Element stoneCards = doc.createElement("stoneCards");
            for(Map.Entry<StoneCard, Integer> entry : scenario.getStoneCards().entrySet()){
                StoneCard st = entry.getKey();
                Element stoneCard = doc.createElement("stoneCard");
                stoneCard.setAttribute("name", st.getName());
                stoneCard.setAttribute("amount", String.valueOf(entry.getValue()));

                stoneCards.appendChild(stoneCard);
            }
            rootElement.appendChild(stoneCards);
        }

        // write dom document to a file
        try (FileOutputStream output =
                     new FileOutputStream(file)) {
            writeXml(doc, output);
        } catch (IOException | TransformerException e) {
            e.printStackTrace();
        }
    }

    private static void setupMaps(Element maps, Scenario scenario,Document doc){
        for(MapObject mapObject : scenario.getTileGrid()){
            Element map = doc.createElement("map");
            map.setAttribute("column", String.valueOf(mapObject.getColumn()));
            map.setAttribute("row", String.valueOf(mapObject.getRow()));

            Element imagePath = doc.createElement("imagePath");
            imagePath.appendChild(doc.createTextNode(mapObject.getImagePath().getPath()));
            map.appendChild(imagePath);

            Element name = doc.createElement("name");
            name.appendChild(doc.createTextNode(mapObject.getName()));
            map.appendChild(name);

            Element game = doc.createElement("game");
            game.appendChild(doc.createTextNode(mapObject.getGameBox().getName()));
            map.appendChild(game);

            Element path = doc.createElement("path");
            path.appendChild(doc.createTextNode(mapObject.getPath()));
            map.appendChild(path);

            Element tiles = doc.createElement("tiles");
            setupTiles(tiles,mapObject,doc);
            map.appendChild(tiles);

            maps.appendChild(map);
        }
    }

    private static void setupTiles(Element tiles, MapObject mapObject, Document doc){
        for(Tile t: mapObject.getTiles()){
            Element tile = doc.createElement("tile");
            Element imagePath = doc.createElement("imagePath");
            imagePath.appendChild(doc.createTextNode(t.getImagePath().getPath()));
            tile.appendChild(imagePath);

            Element name = doc.createElement("name");
            name.appendChild(doc.createTextNode(t.getName()));
            tile.appendChild(name);

            Element game = doc.createElement("game");
            game.appendChild(doc.createTextNode(t.getGameBox().getName()));
            tile.appendChild(game);

            Element path = doc.createElement("path");
            path.appendChild(doc.createTextNode(t.getPath()));
            tile.appendChild(path);
            // Add Monsters
            Element monsters = doc.createElement("monsters");
            setupMonsters(monsters,t,doc);
            tile.appendChild(monsters);
            // Add Portals
            Element portals = doc.createElement("portals");
            setupPortals(portals,t,doc);
            tile.appendChild(portals);
            // Add Tokens
            Element tokens = doc.createElement("tokens");
            setupTokens(tokens,t,doc);
            tile.appendChild(tokens);
            // Add StoneCards
            Element stoneCards = doc.createElement("stoneCards");
            setupStoneCards(stoneCards,t,doc);
            tile.appendChild(stoneCards);
            // Add Doors
            Element doors = doc.createElement("doors");
            setupDoors(doors,t,doc);
            tile.appendChild(doors);

            tiles.appendChild(tile);
        }
    }

    private static void setupMonsters(Element monsters, Tile tile, Document doc){
        for(Map.Entry<Monster, Integer> entry : tile.getMonsters().entrySet()){
            Monster m = entry.getKey();
            Element monster = doc.createElement("monster");
            Element name = doc.createElement("name");
            name.appendChild(doc.createTextNode(m.getName()));
            monster.appendChild(name);

            Element amount = doc.createElement("amount");
            amount.appendChild(doc.createTextNode(String.valueOf(entry.getValue())));
            monster.appendChild(amount);

            monsters.appendChild(monster);
        }
    }

    private static void setupPortals(Element portals, Tile tile, Document doc){
        for(Map.Entry<Portal, Integer> entry : tile.getPortals().entrySet()){
            Portal p = entry.getKey();
            Element portal = doc.createElement("portal");
            Element name = doc.createElement("name");
            name.appendChild(doc.createTextNode(p.getName()));
            portal.appendChild(name);

            Element amount = doc.createElement("amount");
            amount.appendChild(doc.createTextNode(String.valueOf(entry.getValue())));
            portal.appendChild(amount);

            portals.appendChild(portal);
        }
    }

    private static void setupTokens(Element tokens, Tile tile, Document doc){
        for(Map.Entry<Token, Integer> entry : tile.getTokens().entrySet()){
            Token t = entry.getKey();
            Element token = doc.createElement("tokens");
            Element name = doc.createElement("name");
            name.appendChild(doc.createTextNode(t.getName()));
            token.appendChild(name);

            Element amount = doc.createElement("amount");
            amount.appendChild(doc.createTextNode(String.valueOf(entry.getValue())));
            token.appendChild(amount);

            tokens.appendChild(token);
        }
    }

    private static void setupStoneCards(Element stoneCards, Tile tile, Document doc){
        for(Map.Entry<StoneCard, Integer> entry : tile.getStoneCards().entrySet()){
            StoneCard st = entry.getKey();
            Element stoneCard = doc.createElement("stoneCards");
            Element name = doc.createElement("name");
            name.appendChild(doc.createTextNode(st.getName()));
            stoneCard.appendChild(name);

            Element amount = doc.createElement("amount");
            amount.appendChild(doc.createTextNode(String.valueOf(entry.getValue())));
            stoneCard.appendChild(amount);

            stoneCards.appendChild(stoneCard);
        }
    }

    private static void setupDoors(Element doors, Tile tile, Document doc){
        for(Map.Entry<Door, Double> entry : tile.getDoors().entrySet()){
            Door d = entry.getKey();

            Element door = doc.createElement("doors");
            Element name = doc.createElement("name");
            name.appendChild(doc.createTextNode(d.getName()));
            door.appendChild(name);
            Element amount = doc.createElement("amount");
            amount.appendChild(doc.createTextNode(String.valueOf(entry.getValue())));
            door.appendChild(amount);
            doors.appendChild(door);

        }
    }


    // write doc to output stream
    private static void writeXml(Document doc,
                                 OutputStream output)
            throws TransformerException {

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        // pretty print XML
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(output);

        transformer.transform(source, result);

    }
}
