package ce326.hw2;

/**
 *
 * @author George
 */
public class YUVPixel {
    private short y,u,v;
    
    public YUVPixel(short y, short u, Short v){
        this.y = y;
        this.u = u;
        this.v = v;
    }
    
    public YUVPixel(YUVPixel pixel){
        this(pixel.getY(), pixel.getU(), pixel.getV());
    }
    
    public YUVPixel(RGBPixel pixel){
        this.y = (short)(((66*pixel.getRed() + 129*pixel.getGreen() + 25*pixel.getBlue() + 128) >> 8) + 16);
        this.u = (short)(((-38*pixel.getRed() - 74*pixel.getGreen() + 112*pixel.getBlue() + 128) >> 8) + 128);
        this.v = (short)(((112*pixel.getRed() - 94*pixel.getGreen() - 18*pixel.getBlue() + 128) >> 8) + 128);
    }
    
    public short getY(){
        return y;
    }
    
    public short getU(){
        return u;
    }
    
    public short getV(){
        return v;
    }
    
    public void setY(short Y){
        this.y = Y;
    }
    
    public void setU(short U){
        this.u = U;
    }
    
    public void setV(short V){
        this.v = V;
    }
    
//    //String with YUV numbers
//    public String YUVtoString(){
//        String yuvstring;
//        yuvstring = String.valueOf(y) + " " + String.valueOf(u) + " " + String.valueOf(v);
//        
//        return yuvstring;
//    }
}
