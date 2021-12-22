package ce326.hw2;

/**
 *
 * @author George
 */

public class RGBImage implements Image{

    public static final int MAX_COLORDEPTH = 255; //Constant for max colordeapth
    private int height, width, colordeapth;
    private RGBPixel[][] rgbimage;

    //Constructor that does nothing to inharitate PPM Image
    public RGBImage() {
    }
    
    //Constructor to make a new photo array
    public RGBImage(int width, int height, int colordepth) {
        this.width = width;
        this.height = height;
        if (colordepth < 0 || colordepth > 255) {
            System.out.println("Colordepth must be between 0 & 255");
            System.out.println("");
            System.exit(1);
        }
        this.colordeapth = colordepth;

        rgbimage = new RGBPixel[height][width];
    }

    //Constructor to copy 
    public RGBImage(RGBImage copyImg) {
        this(copyImg.width, copyImg.height, copyImg.colordeapth);
        this.rgbimage = copyImg.getRGBImage();
    }

    //Constructor for RGB image from YUV image
    public RGBImage(YUVImage YUVImg){
        this(YUVImg.getWidth(), YUVImg.getHeight(), 255);
	
        RGBPixel [][]yuvtorgb;
        yuvtorgb = new RGBPixel[height][width];
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                yuvtorgb[i][j] = new RGBPixel(YUVImg.getYUVImage()[i][j]);
            }
        }
        rgbimage = yuvtorgb;
    }
    
    //Return private width
    public int getWidth() {
        return width;
    }

    //Return private height
    public int getHeight() {
        return height;
    }

    //return private colordepth
    public int getColorDepth() {
        return colordeapth;
    }
    
    //Return rgbimage
    public RGBPixel [][] getRGBImage() {
        return rgbimage;
    }

    public RGBPixel getPixel(int row, int col) {
        return rgbimage[row][col];
    }

    public void setPixel(int row, int col, RGBPixel pixel) {
        rgbimage[row][col] = pixel;
    }
    
    public void setRGBImage(RGBPixel [][]image){
        height = image.length;
        width = image[0].length;
        rgbimage = new RGBPixel[height][width];
        rgbimage = image; 
    }
    
    //Convert image to grayscale.
    //Method: red*0.3, green*0.59, blue*0.11
    @Override
    public void grayscale() {
        int i,j;
        short gray;
        RGBPixel [][]grayimage;
        
        grayimage = new RGBPixel[height][width];
        
        for(i=0; i < getHeight(); i++){
            for(j=0; j<getWidth(); j++){
                gray =(short)( rgbimage[i][j].getRed()*0.3 + rgbimage[i][j].getGreen()*0.59 + rgbimage[i][j].getBlue()*0.11);
                grayimage[i][j] = new RGBPixel(gray,gray,gray);
            }
        }
        rgbimage = grayimage;
    }
    
    //Double the size of a photo
    @Override
    public void doublesize() {
        RGBPixel [][]doubleimage;
        int newwidth, newheight;
        int i,j;
        short r,g,b;
        
        //set new dimmensions
        newwidth = 2*width;
        newheight = 2*height;
        
        //Create double pic
        doubleimage = new RGBPixel[newheight][newwidth];
        
        for(i=0; i < getHeight(); i++){
            for(j=0; j<getWidth(); j++){
                r = rgbimage[i][j].getRed();
                g = rgbimage[i][j].getGreen();
                b = rgbimage[i][j].getBlue();
                doubleimage[2*i][2*j] = new RGBPixel(r,g,b);
                doubleimage[2*i+1][2*j] = new RGBPixel(r,g,b);
                doubleimage[2*i][2*j+1] = new RGBPixel(r,g,b);
                doubleimage[2*i+1][2*j+1] = new RGBPixel(r,g,b);
            }
        } 
        rgbimage =  new RGBPixel[newheight][newwidth];
        rgbimage = doubleimage;
        width = newwidth;
        height = newheight;
    }  
    
    //Half the photo
    @Override
    public void halfsize() {
        RGBPixel [][] halfimage;
        int newwidth, newheight;
        int i,j;
        short r,g,b;
        
        //set new dimmensions
        newwidth = width/2;
        newheight = height/2;
        
        //Create double pic
        halfimage = new RGBPixel[newheight][newwidth];
        
        for(i=0; i < newheight; i++){
            for(j=0; j<newwidth; j++){
                r = (short)((rgbimage[2*i][2*j].getRed() + rgbimage[2*i+1][2*j].getRed() + rgbimage[2*i][2*j+1].getRed() + rgbimage[2*i+1][2*j+1].getRed())/4); 
                g = (short)((rgbimage[2*i][2*j].getGreen() + rgbimage[2*i+1][2*j].getGreen() + rgbimage[2*i][2*j+1].getGreen() + rgbimage[2*i+1][2*j+1].getGreen())/4);
                b = (short)((rgbimage[2*i][2*j].getBlue() + rgbimage[2*i+1][2*j].getBlue() + rgbimage[2*i][2*j+1].getBlue() + rgbimage[2*i+1][2*j+1].getBlue())/4);
                halfimage[i][j] = new RGBPixel (r,g,b);
            }
        }
        rgbimage =  new RGBPixel[newheight][newwidth];
        rgbimage = halfimage;
        width = newwidth;
        height = newheight;
       
    }   
        
    //Rotate 90 deg. clockwise    
    @Override
    public void rotateClockwise(){
        int swap, i,j;
        RGBPixel [][]clockwise = new RGBPixel[width][height];
        

        
        for(i=0; i<height; i++){
            for(j=0; j<width; j++){
                clockwise[j][height-i-1] = rgbimage[i][j]; 
            }
        }
        
        // width = height and height = width
        swap = width;
        width = height;
        height = swap;
        
        rgbimage = new RGBPixel[height][width];
        rgbimage = clockwise;
    }
    
}
