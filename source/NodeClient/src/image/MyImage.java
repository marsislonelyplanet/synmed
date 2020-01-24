package image;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import paillierPrototype.PaillierAlgorithm;

import matrix.MultiplyMatrix;

public class MyImage{
	
	private static BufferedImage image = null;
	public static ImageRepresentation iR = null;
	public static MyEncryptedImage eI = null;
	public static int[][] imageRGBMatrix = null;
	public static BigInteger[][] imageBigInteger = null;
	public static int[][] decryptedImage = null;
	 
	
	public static BufferedImage getImage() {
		return image;
	}

	public static void setImage(BufferedImage image) {
		MyImage.image = image;
	}
		
	public static void main(String[] args) {
		encryptionTest();
		//test();
	}	
	
	public static int convertFromPointToRGB(Point point) {
		
		Color c = new Color(point.red, point.green, point.blue);
		
		return c.getRGB();
	}
	
	public static void readImage(String fileLocation)throws IOException{
    
	    File f = null;
	    //read image file
	    try {
	    	//f = new File("D:\\Image\\Taj.jpg");
	    	f = new File(fileLocation);
	    	setImage(ImageIO.read(f));
	    } catch(IOException e){
	      System.out.println("Error: "+e);
	    }
	    iR = new ImageRepresentation(image.getWidth(), image.getHeight());
	    //imageRGBMatrix = new int[image.getWidth()][image.getHeight()];
	    
	    for (int i = 0; i < image.getWidth(); i++){
	    	for (int j = 0; j < image.getHeight(); j++){
	    		//System.out.println(image.getRGB(i, j));
	    		Color c = new Color(image.getRGB(i, j));
	    		int red = c.getRed();
	    		int green = c.getGreen();
	    		int blue = c.getBlue();
	    		Point p = new Point(red, green, blue);
	    		iR.points[i][j] = p;
	    		//imageRGBMatrix[i][j] = image.getRGB(i, j);
	    	}
	    }
	}
	
	public static BufferedImage arrayToBufferedImage(int[][] matrix){
		
		BufferedImage returnImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
		
		try {
			
			for(int i = 0; i < matrix.length; i++){
				for(int j = 0; j < matrix[0].length; j++){
					
					returnImage.setRGB(i, j, matrix[i][j]);
					
				}
			}
			return returnImage;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
    public static void writeImage(BufferedImage image, String fileLocation) {
    	//write image
    	try {
    		File f = new File(fileLocation);
    		ImageIO.write(image, "jpg", f);
    	} catch(IOException e){
    		System.out.println("Error: "+e);
    	}
    }
    
    public static void encryptionTest(){
    	
    	PaillierAlgorithm paillier = new PaillierAlgorithm();
    	paillier.initialize(128);// passing the bitLength of
		// key
    	paillier.generateKeyPair();
    	
    	BigInteger n = paillier.getN();
		BigInteger p = paillier.getP();
		BigInteger q = paillier.getQ();
		BigInteger g = paillier.getG();
    	
    	try {
			
			readImage("/home/breno/Desktop/ferrari.jpg");
			
			//imageBigInteger = new BigInteger[iR.width][iR.height];
			
			eI = new MyEncryptedImage(iR.width, iR.height);
			System.out.println("1");
			for (int i = 0; i < iR.width; i++){
		    	for (int j = 0; j < iR.height; j++){
		    		try {
		    			
		    			int red = iR.points[i][j].red;
		    			int green = iR.points[i][j].green;
		    			int blue = iR.points[i][j].blue;
		    			BigInteger bRed = paillier.encrypt(BigInteger.valueOf(red));
		    			BigInteger bGreen = paillier.encrypt(BigInteger.valueOf(green));
		    			BigInteger bBlue = paillier.encrypt(BigInteger.valueOf(blue));
		    			EncryptedPoint eP = new EncryptedPoint(bRed, bGreen, bBlue);
		    			
		    			eI.points[i][j] = eP;
		    			
		    			//imageBigInteger[i][j] = paillier.encrypt(BigInteger.valueOf(imageRGBMatrix[i][j]));
		    		} catch (Exception e){
		    			e.printStackTrace();
		    		}
		    	}
		    	//System.out.println("@@");
		    }
			
			System.out.println("2");
			
			//int[][] intermediary = myRepresentationMatrix(eI);
    		//MultiplyMatrix.processMatrix3(eI.points, paillier);
			MultiplyMatrix.processMatrix3(eI.points);
    		//eI.points = ePoints
			//int[][] processedImageMatrix = MultiplyMatrix.processMatrix(intermediary);
    		System.out.println("2.5");
			decryptedImage = new int[eI.width][eI.height];
			
			
			for (int i = 0; i < eI.width; i++){
		    	for (int j = 0; j < eI.height; j++){
		    		try {
		    			int dRed = paillier.decrypt(eI.points[i][j].red).intValue();
		    			int dGreen = paillier.decrypt(eI.points[i][j].green).intValue();
		    			int dBlue = paillier.decrypt(eI.points[i][j].blue).intValue();
		    			//System.out.println(dRed);
		    			if(dRed > 255) {
		    				dRed = 255;
		    			}
		    			if(dRed < 0) {
		    				dRed = 0;
		    			}
		    			if(dGreen > 255) {
		    				dGreen = 255;
		    			}
		    			if(dGreen < 0) {
		    				dGreen = 0;
		    			}
		    			if(dBlue > 255) {
		    				dBlue = 255;
		    			}
		    			if(dBlue < 0) {
		    				dBlue = 0;
		    			}
		    			
		    			//System.out.println("Colors R - " + dRed + "  G - " + dGreen + " B - " + dBlue);
		    			
		    			Color c = new Color(dRed, dGreen, dBlue);
		    			//System.out.println("COLOR: " + c.getRGB());
		    			decryptedImage[i][j] = c.getRGB();
		    		} catch (Exception e){
		    			e.printStackTrace();
		    		}
		    	}
		    }
			System.out.println("3");
			HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
			
			
			BufferedImage processedImage = arrayToBufferedImage(decryptedImage);
			writeImage(processedImage, "/home/breno/Desktop/ferrari7.jpg");
		
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    }
    
    
    public static int[][] myRepresentationMatrix(ImageRepresentation ir){
    	
    	int[][] returnMatrix = new int[ir.width][ir.height];
    	
    	for (int i = 0; i < ir.width; i++) {
			for (int j = 0; j < ir.height; j++) {
				returnMatrix[i][j] = convertFromPointToRGB(ir.points[i][j]);
			}
		}
    	
    	return returnMatrix;
    }
/*    
    public static int[][] myRepresentationMatrix(MyEncryptedImage ir){
    	
    	int[][] returnMatrix = new int[ir.width][ir.height];
    	
    	for (int i = 0; i < ir.width; i++) {
			for (int j = 0; j < ir.height; j++) {
				
				
				returnMatrix[i][j] = convertFromPointToRGB(ir.points[i][j]);
			}
		}
    	
    	return returnMatrix;
    }
*/    
    
    public static void test(){
    	try {
    		readImage("/home/breno/Desktop/laferrari.jpg");
    		//int[][] processedImageMatrix = MultiplyMatrix.processMatrix(imageRGBMatrix);
    		int[][] intermediary = myRepresentationMatrix(iR);
    		int[][] processedImageMatrix = MultiplyMatrix.processMatrix(intermediary);
    		BufferedImage processedImage = arrayToBufferedImage(processedImageMatrix);
    		writeImage(processedImage, "/home/breno/Desktop/laferrari2.jpg");
    	} catch (Exception e){
    		e.printStackTrace();
    	}
    }
    
}
