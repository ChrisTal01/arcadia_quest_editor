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
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author muell
 */
public class ResizeImage {

    private static final String PATH = "F:/arcadia_quest_editor/ArcadiaQuestData/PoisonDragon/MapParts";
    public static void main(String[] args) {
        
        File file = new File(PATH);
        File[] mapFolder = file.listFiles();
        for(File mf : mapFolder){
            File[] imgFolder = mf.listFiles();
            for(File imgf: imgFolder){
                File[] images = imgf.listFiles();
                for(File png : images){
                    saveImage(png);
                }
            }
        }
                
    }

    private static BufferedImage resizeImage(BufferedImage originalImage, int type, int IMG_WIDTH, int IMG_HEIGHT) {
        BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
        g.dispose();
        return resizedImage;
    }
    
    private static void saveImage(File filePath){
        try {
            BufferedImage originalImage = ImageIO.read(filePath);//change path to where file is located
            int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
            BufferedImage resizeImageJpg = resizeImage(originalImage, type, 550, 550);
            try {
                ImageIO.write(resizeImageJpg, "jpg", filePath); //change path where you want it saved
            } catch (IOException ex) {
                Logger.getLogger(ResizeImage.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException ex) {
            Logger.getLogger(ResizeImage.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("finished");
    }
}
