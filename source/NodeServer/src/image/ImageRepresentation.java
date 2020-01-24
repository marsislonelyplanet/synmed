package image;

import java.util.ArrayList;
import java.util.List;

public class ImageRepresentation {

	public int width = 0;
	public int height = 0;
	
	public Point[][] points = null;
	//public List<Point> heightPoints = new ArrayList<Point>();
	
	public ImageRepresentation(int width, int height) {
		
		this.width = width;
		this.height = height;
		
		
		points = new Point[width][height];
		
	}
	
	
}
