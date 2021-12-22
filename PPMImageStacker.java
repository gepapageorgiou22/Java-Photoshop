package ce326.hw2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 *
 * @author George
 */
public class PPMImageStacker {
    private List <PPMImage> stackinglist;
    private PPMImage image,temp; //image is the picture after algorithm, temp is temporary
    private int imagestostackcount=0;
    
    
    //Constructor here
    public PPMImageStacker(java.io.File dir) throws UnsupportedFileFormatException, FileNotFoundException  {
        //Check for the directory
        if(!dir.exists()){
            throw new FileNotFoundException("[ERROR] Directory " + dir + "does not exist!\n" );
        }
        if(!dir.isDirectory()){
            throw new UnsupportedFileFormatException("[ERROR] "+ dir +" is not a directory!\n");
        }
        
        //Create list for the pictures
        stackinglist = new ArrayList<>();
        for(File eachfile: dir.listFiles()){
            try{
                String filetype = eachfile.getName().substring(eachfile.getName().length()-4, eachfile.getName().length());
                //Only ppm files not directories etc 
                if(!filetype.equals(".ppm") || eachfile.isDirectory()){
                    continue;
                }
                temp = new PPMImage(eachfile);
                stackinglist.add(temp);
                imagestostackcount++;
            }catch(FileNotFoundException ex){
                throw new FileNotFoundException("[ERROR] File " + eachfile + "does not exist!\n" );
            }catch(UnsupportedFileFormatException ex){
                throw new UnsupportedFileFormatException("[ERROR] "+ eachfile +" has not the correct format!\n");
            }  
        }
    }
    
    
    public void stack(){
        int i, j, imagecount;
        int width,height, colordepth;
        int red,green,blue;
        RGBImage temp1;
        RGBPixel [][] imageforppm;
        RGBPixel pixeltosum;
        PPMImage imagetostack;
        
        //Set width,height
        width = stackinglist.get(0).getWidth();
        height = stackinglist.get(0).getHeight();
        colordepth = stackinglist.get(0).getColorDepth();
        
        //create the new items i need
        temp1 = new RGBImage(width, height, colordepth);
        imageforppm = new RGBPixel[height][width];
        
        for(i=0; i < height; i++){
            for(j=0; j < width; j++){
                red=0;
                green=0;
                blue=0;
                for(imagecount=0; imagecount<imagestostackcount; imagecount++){
                    imagetostack = stackinglist.get(imagecount);
                    pixeltosum = imagetostack.getRGBImage()[i][j];
                    red += pixeltosum.getRed();
                    green += pixeltosum.getGreen();
                    blue += pixeltosum.getBlue();
                }
                
                red = (red/imagestostackcount);
                green = (green/imagestostackcount);
                blue = (blue/imagestostackcount);
                imageforppm[i][j] = new RGBPixel((short)red,(short)green, (short)blue);
            }
        }
        temp1.setRGBImage(imageforppm);
                
        image = new PPMImage(temp1);
    }
    
    public PPMImage getStackedImage(){
        return image;
    }
}
