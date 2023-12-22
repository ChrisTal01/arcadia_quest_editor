/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arcadia.editor.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

public class ImageSpliter {
    static String path = "F:/arcadia_quest_editor/ArcadiaQuestData/PoisonDragon/MapParts";
    static String type = ".png";
    
    public static void main(String[] args) throws IOException {
        //String[] TileTypes = {"Normal","Gray","Start","Green","Violet"};
        String[] TileTypes = {"Normal","Gray"};
        // String[] MapSides = {"A","B"};
        String[] MapSides = {"A"};

        File filePath = new File(path);
        if(!filePath.exists()){
            System.out.println(filePath.getPath() + " path does not exist.");
            return;
        }
        for(File folder : Objects.requireNonNull(filePath.listFiles())){
            int index = Integer.parseInt(folder.getName().substring(3,folder.getName().length()-1));
            System.out.println(index);
            for(String side : MapSides){
                for(String tile : TileTypes){
                    File mapFile = new File(folder,"/map/Mapteil_"+ index + side +"_"+tile+ type);
                    System.out.println(mapFile.getPath());
                    splitImage(mapFile, 3, 3,folder.getPath(), tile);
                }
            }
        }
    }
    
    public static void splitImage(File pFile, int pRows, int pCols, String pPath, String pTile) throws IOException{
        // only works with png (24 bit), if using png
        
        FileInputStream fis = new FileInputStream(pFile);
        BufferedImage image = ImageIO.read(fis); //reading the image file

        System.out.println(image == null);

        int rows = pRows; //You should decide the values for rows and cols variables
        int cols = pCols;
        int chunks = rows * cols;

        int chunkWidth = image.getWidth() / cols; // determines the chunk width and height
        int chunkHeight = image.getHeight() / rows;
        int count = 0;
        BufferedImage imgs[] = new BufferedImage[chunks]; //Image array to hold image chunks
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                //Initialize the image array with image chunks
                imgs[count] = new BufferedImage(chunkWidth, chunkHeight, image.getType());

                // draws the image chunk
                Graphics2D gr = imgs[count++].createGraphics();
                gr.drawImage(image, 0, 0, chunkWidth, chunkHeight, chunkWidth * y, chunkHeight * x, chunkWidth * y + chunkWidth, chunkHeight * x + chunkHeight, null);
                gr.dispose();
            }
        }
        System.out.println("Splitting done");

        //writing mini images into image files
        for (int i = 0; i < imgs.length; i++) {
            File thisPath = new File(pPath, "img"+ i +"/img"+i+ pTile + ".jpg");
            //System.out.println(thisPath.getPath());
            if(imgs[i] != null){
                ImageIO.write(imgs[i], "jpg", thisPath);
            }else {
                System.out.println("Image is null.");
            }

        }
        System.out.println("Mini images created");
    }
}
