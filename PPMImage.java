package ce326.hw2;

import java.io.*;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author George
 */
public class PPMImage extends RGBImage{
    String photo = "";
    
    public PPMImage(java.io.File file) throws FileNotFoundException,UnsupportedFileFormatException{
        super(1, 1, 255);
        int i,j, colordepth, width, height;
        RGBPixel [][]image;
        String filetype;
        short red, green, blue;
        
        if(!file.exists()){
            throw new FileNotFoundException();
        }
        filetype = file.getName().substring(file.getName().length()-4, file.getName().length());
        if(!filetype.equals(".ppm")){
            throw new UnsupportedFileFormatException();
        }
        
        Scanner input = new Scanner(file);
        input.next();
        width = input.nextInt();
        height = input.nextInt();
        colordepth = input.nextInt();
        if(colordepth < 0 || colordepth > 255){
            System.out.println("Error at file info colordepth\n");
        }
        image = new RGBPixel [height][width];
        for(i=0; i<height; i++){
            for(j=0; j<width; j++){
                red = input.nextShort();
                green = input.nextShort();
                blue = input.nextShort();
               image[i][j] = new RGBPixel(red,green,blue);
            }
        }
        super.setRGBImage(image);          
        
    }
    
    public PPMImage(RGBImage image) {
        super(image);
    }
    
    //Not yet fixed
    public PPMImage(YUVImage img){
        super(img);
    }

    void toFile(File selectedFile){

        //Delete file if exists and create new
        if(selectedFile.exists()){
            selectedFile.delete();
        }
        try{
            //Code here to write file
            BufferedWriter writer = new BufferedWriter(new FileWriter(selectedFile));
            writer.write(this.toString());
            writer.close();
        }catch (IOException ex){
            System.out.println("An error occurred.");
        }
        
    }

    //Photo to String
    @Override
    public String toString(){
        StringBuilder tophoto;
        int i,j;
        RGBPixel [][]picture;
        RGBPixel pixel;
        
        tophoto = new StringBuilder();
        
        tophoto.append("P3\n");
        tophoto.append(getWidth()).append(" ").append(getHeight()).append("\n");
        tophoto.append(getColorDepth()).append("\n");
        picture = getRGBImage();
        
        for(i=0; i<getHeight(); i++){
            for(j=0; j<getWidth(); j++){
                pixel = picture[i][j];
                tophoto.append(pixel.getRed()).append(" ");
                tophoto.append(pixel.getGreen()).append(" ");
                tophoto.append(pixel.getBlue());
                if(j != getWidth()-1){
                    tophoto.append(" ");
                }
            }
            tophoto.append("\n");
        }
        
        return tophoto.toString();
    }
    
    
}
