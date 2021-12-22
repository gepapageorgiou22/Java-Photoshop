package ce326.hw2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author George
 */
public class Histogram {
   private final int []calculate;
   private final int imagesize;
    
    public Histogram(YUVImage img){
        int i,j;
        
        imagesize = img.getHeight()*img.getWidth();
        calculate = new int[236];
        for(i=0; i<236; i++){
            calculate[i] = 0;
        }
        for(i=0; i<img.getHeight(); i++){
            for(j=0; j<img.getWidth(); j++){
                calculate[img.getYUVImage()[i][j].getY()] ++;
            }
        }
    }
    
    
   @Override
    public String toString(){
        StringBuilder str;
        int i, temp,lol;
        int value;
        
        str = new StringBuilder();
        
        for(i=0; i<236; i++){
            value = calculate[i];
            str.append("\n").append(String.format("%3d", i)).append(".").append("(").append(String.format("%4d", value)).append(")\t");
            if(value>=1000){
                lol = value/1000;
                
                for(temp=0; temp<lol; temp++){
                    str.append('#');
                    value += -1000;
                } 
            }
            if(value>=100){
                lol = value/100;
                for(temp=0; temp<lol; temp++){
                    str.append('$');
                    value += -100;
                }  
            }
            if(value>=10){
                lol = value/10;
                for(temp=0; temp<lol; temp++){
                    str.append('@');
                    value += -10;
                }  
            }
            
            if(value>=1){
                lol = value;
                for(temp=0; temp<lol; temp++){
                    str.append('*');
                    value += -1;
                }  
            }
        }
        str.append("\n");
        return str.toString();
    }
    
    public void toFile(File file){
        try{
            //Delete file if exists and create new
            if(file.exists()){
                file.delete();
            }
            
            try ( //Code here to write file
                BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(this.toString());
            }
        }catch (IOException ex){
            System.out.println("An error occurred.");
        }
    }
    
    public void equalize(){
        double []probs; //Probability array
        double []cdf; //Cumulative distribution function array
        int i; //Just a simple counter
        
        probs = new double[236];
        cdf = new double[236];
        
        //Build probability
        for(i=0; i<236; i++){
            probs[i] = (double)calculate[i]/(double)imagesize;
        }
            
        
        //Build cdf here
        cdf[0] = calculate[0];
        for(i=1; i<236; i++){
            cdf[i] = probs[i] + cdf[i-1];
        }
        //Re-build histogram based on cdf
        for(i=0; i<236; i++){
            calculate[i] = (int)(cdf[i]*235);
            if(calculate[i]>235){
                calculate[i]=235;
            }
        }   
    }
    
    public int getEqualizedLuminocity(int luminocity){
        return calculate[luminocity];
    }
}
