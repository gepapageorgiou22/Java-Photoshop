package ce326.hw2;

/**
 *
 * @author George
 */

public class RGBPixel {
    private int rgbcombined;
    short red, green, blue;
    
    //Constructor here to create RGB pixel
    public RGBPixel(short red, short green, short blue){
        
        //Build number by shifting and addidng
          this.red = red;
          this.green = green;
          this.blue = blue;
          rgbcombined = red;
          rgbcombined = (rgbcombined<<8) + green;
          rgbcombined = (rgbcombined<<8) + blue;
          
    }
    
    //Constructor here to make a copy of the previous constructor
    public RGBPixel(RGBPixel pixel){
        this(pixel.getRed(), pixel.getGreen(), pixel.getBlue()); 
    }

    
    //Constructor to create YUV pixel from RGB
    public RGBPixel(YUVPixel pixel){
        short C,D,E;
        
        C = (short)(pixel.getY()-16);
        D = (short)(pixel.getU()-128);
        E = (short)(pixel.getV()-128);
        
       red = clip((short)(( 298 * C + 409 * E + 128) >> 8));
       green = clip((short)(( 298 * C - 100 * D - 208 * E + 128) >> 8));
       blue = clip((short)(( 298 * C + 516 * D + 128) >> 8));
       
       rgbcombined = red;
       rgbcombined = (rgbcombined<<8) + green;
       rgbcombined = (rgbcombined<<8) + blue;
    }
    
    //Method to get red value(0-255) from rgbcombined 
    public short getRed(){
        return red;
    }
    
    //Method to get green value(0-255) from rgbcombined
    public short getGreen(){        
        return green;
    }
    
    //Method to get blue value(0-255) from rgbcombined
    public short getBlue(){     
        return blue;
    }
    
    //Setting colors
    public void setRed(short red){
        this.red = red;
    }
    public void setGreen(short green){
        this.green = green;
    }
    public void setBlue(short blue){
        this.blue = blue;
    }
    
    //Get RGB integer
    public int getRGB(){
        return rgbcombined;
    }
    
    //Set RGB collors from integer
    public void setRGB(int value){
        int tempvalue;
        
        tempvalue = value;
        tempvalue = tempvalue << 8;
        tempvalue = tempvalue >> 24;
        this.red = (short)tempvalue;
        tempvalue = value;
        tempvalue = tempvalue << 16;
        tempvalue = tempvalue >> 24;
        this.green = (short)tempvalue;
        tempvalue = value;
        tempvalue = tempvalue << 24;
        tempvalue = tempvalue >> 24;
        this.blue = (short)tempvalue;
    }
    
    //Set values based on red,green,blue
    public final void setRGB(short red, short green, short blue){
        setRed(red);
        setGreen(green);
        setBlue(blue);
    }
    
    //String with RGB numbers
//    @Override
//    public String toString(){
//        String rgbstring;
//        rgbstring = String.valueOf(red) + " " + String.valueOf(green) + " " + String.valueOf(blue);
//        
//        return rgbstring;
//    }
    
    //Clip method to gurantee [0,255]
    public final short clip(short number){
        
        if(number < 0){
            return 0;
        }
        else if(number > 255){
            return 255;
        }
        
        return number;
    }
}