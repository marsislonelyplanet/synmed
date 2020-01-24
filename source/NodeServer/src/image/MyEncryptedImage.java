package image;

import java.math.BigInteger;

public class MyEncryptedImage {

	public int width = 0;
	public int height = 0;
		
	public EncryptedPoint[][] points;
	//public List<Point> heightPoints = new ArrayList<Point>();
		
	public MyEncryptedImage(int width, int height) {
			
		this.width = width;
		this.height = height;
					
		points = new EncryptedPoint[width][height];
			
	}

}
