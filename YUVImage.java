package ce326.hw2;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author George
 */
public class YUVImage {
    private YUVPixel [][]photo;
    private int height, width;
    
    public YUVImage(int width, int height){
        
        this.width = width;
        this.height = height;
        
        photo = new YUVPixel[height][width];
   	
        for (int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
		photo[i][j] = new YUVPixel((short)16, (short)128, (short)128);
            }
	}
    }
    
    public YUVImage(YUVImage copyImg){
        this(copyImg.width,copyImg.height);
        copyImg.photo = this.photo;
    }
    
    public YUVImage(RGBImage RGBImg){
        this(RGBImg.getWidth(), RGBImg.getHeight());
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
		photo[i][j] = new YUVPixel(RGBImg.getRGBImage()[i][j]);
            }
        }   
    }
    
    
    public YUVImage(java.io.File file)throws FileNotFoundException,UnsupportedFileFormatException{
        int i,j;
        String filetype;
        short y, u, v;
        
        if(!file.exists()){
            throw new FileNotFoundException();
        }
        filetype = file.getName().substring(file.getName().length()-4, file.getName().length());
        if(!filetype.equals(".yuv")){
            throw new UnsupportedFileFormatException();
        }
        
        Scanner input = new Scanner(file);
        input.next();
        width = input.nextInt();
        height = input.nextInt();
        photo = new YUVPixel[height][width];
        for(i=0; i<height; i++){
            for(j=0; j<width; j++){
                y = input.nextShort();
                u = input.nextShort();
                v = input.nextShort();
               photo[i][j] = new YUVPixel(y,u,v);
            }
        }
        
    }
    
    public void toFile(java.io.File file){
        
        //Delete file if exists and create new
        if(file.exists()){
            file.delete();
        }
        
        try{
            //Code here to write file
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(this.toString());
            writer.close();
        }catch (IOException ex){
            System.out.println("An error occurred.");
        }
        
    }
    

    @Override
    public String toString(){
        StringBuilder tophoto;
        int i,j;
        
        tophoto = new StringBuilder();
        
        tophoto.append("YUV3\n");
        tophoto.append(width).append(" ").append(height).append("\n");
        
        for(i=0; i<height; i++){
            for(j=0; j<width; j++){
                tophoto.append(photo[i][j].getY()).append(" ");
                tophoto.append(photo[i][j].getU()).append(" ");
                tophoto.append(photo[i][j].getV());
                if (j != width - 1){
                    tophoto.append(" ");
		}
            }
            tophoto.append("\n");
        }
        return tophoto.toString();
    }
    
    //Code here to equalize photo
    public void equalize(){
        Histogram toequalizehistogram;
        int i,j;
        
        toequalizehistogram = new Histogram(this);
        toequalizehistogram.equalize();
          
        for(i=0; i<getHeight(); i++){
            for(j=0; j<getWidth(); j++){
                photo[i][j].setY((short)toequalizehistogram.getEqualizedLuminocity(photo[i][j].getY()));
            }
        }
    }
    
    public YUVPixel getPixel(int row, int col) {
        return photo[row][col];
    }
    
    public int getWidth(){
        return width;
    }
    
    public int getHeight(){
        return height;
    }
    
    public YUVPixel [][] getYUVImage(){
        return photo;
    }
    
}
