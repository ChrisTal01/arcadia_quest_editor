/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.main.java.arcadia.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ImageSpliter {
    static String path = "C:\\Users\\muell\\Desktop\\ArcadiaQuestEditor\\JenseitsDerGruft\\MapParts\\Map";
    static String type = ".png";
    
    public static void main(String[] args) throws IOException {
        //String[] TileTypes = {"Normal","Gray","Start","Green","Violet"};
        String[] TileTypes = {"Normal","Gray"};
        String[] MapSides = {"A","B"};
        for(int i= 10; i < 13 ; i++){
            for(String side : MapSides){
                for(String tile : TileTypes){
                    String fullPath = path + i+ side +"\\map\\Mapteil_"+ i + side +"_"+tile+ type;
                    System.out.println(fullPath);
                    File file = new File(fullPath);
                    splitImage(file, 3, 3,path + i+ side+"\\img", tile);
                }
            }
        }
    }
    
    public static void splitImage(File pFile, int pRows, int pCols, String pPath, String pTile) throws FileNotFoundException, IOException{
        // only works with png (24 bit), if using png
        
        FileInputStream fis = new FileInputStream(pFile);
        BufferedImage image = ImageIO.read(fis); //reading the image file

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
            String thisPath = pPath + i +"\\img"+i+ pTile + ".jpg";
            //System.out.println(thisPath);
            ImageIO.write(imgs[i], "jpg", new File(thisPath));
        }
        System.out.println("Mini images created");
    }
}
