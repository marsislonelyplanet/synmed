package matrix;

import image.EncryptedPoint;
import image.MyImage;

import java.math.BigDecimal;
import java.math.BigInteger;

import paillierPrototype.PaillierAlgorithm;

public class MultiplyMatrix {

	public static float[][] transformMatrix = {{1.0f, 1.0f, 1.0f, 1.0f},
											   {0.0f, 1.0f, 0.0f, 0.0f},
											   {0.0f, 0.0f, 1.0f, 0.0f},
											   {0.0f, 0.0f, 0.0f, 1.0f}};
    public static void main(String[] args) {
        int r1 = 2, c1 = 3;
        int r2 = 3, c2 = 2;
        int[][] firstMatrix = { {3, -2, 5}, {3, 0, 4} };
        int[][] secondMatrix = { {2, 3}, {-9, 0}, {0, 4} };

        // Mutliplying Two matrices
        int[][] product = multiplyMatrices(firstMatrix, secondMatrix, r1, c1, c2);

        // Displaying the result
        displayProduct(product);
    }

    public static int[][] processMatrix(int[][] firstMatrix) {
        
    	int r1 = firstMatrix.length;
    	int c1 = firstMatrix[0].length;
    	
    	//System.out.println("r1 -- " + r1 + " : " + c1);
    	
    	int[][] product = new int[r1][c1];
    	
    	for (int y = 1 ; y < c1-1; y++){
            for (int x = 0 ; x < r1-1; x++){
                int sum = 0 ;
                for (int i = -1 ; i <= 2; i++) {
                    for (int j = -1 ; j <= 2; j++){
                    	//System.out.println("x : " + x + " y : " + y + " i : " + i + " j :" + j);
                    	//System.out.println(firstMatrix[x][y] + " : " + "transform");
                        sum += firstMatrix[x][y] * transformMatrix[i+1][j+1] ;
                    }
                }
                product[x][y] = sum;
            }
    	}
    	
    	/*for(int j = 0; j < c1 - 2; j++) {
            for (int k = 0; k < r1 - 2; k++) {
            	for (int w = 0; w < transformMatrix.length; w++) {
					for (int v = 0; v < transformMatrix[0].length; v++) {
						int Sj = j + w;
						int Sv = k + v;
						sum += (int)(firstMatrix[Sj][Sv] * transformMatrix[w][v]);
					}
				}
            	product[j][k] = sum;
            }
        }*/

        return product;
    }
    
    public static void processMatrix(EncryptedPoint[][] firstMatrix, PaillierAlgorithm paillier) {
        
    	int r1 = firstMatrix.length;
    	int c1 = firstMatrix[0].length;
    	
    	System.out.println("r1 -- " + r1 + " : " + c1);
    	
    	EncryptedPoint[][] product = new EncryptedPoint[r1][c1];
    	
    	try {
	    	
    		for (int y = 1 ; y < c1-1; y++){
	            for (int x = 0 ; x < r1-1; x++){
	            	BigDecimal temp = BigDecimal.ONE;//BigDecimal.valueOf(1));
	                BigInteger sumR = BigInteger.ZERO;
	                BigInteger sumG = BigInteger.ZERO;
	                BigInteger sumB = BigInteger.ZERO;
	           
	                for (int i = -1 ; i <= 2; i++) {
	                    for (int j = -1 ; j <= 2; j++){
	                    	//System.out.println("x : " + x + " y : " + y + " i : " + i + " j :" + j);
	                    	//System.out.println(firstMatrix[x][y] + " : " + "transform");
	                        temp = new BigDecimal((Float.toString(transformMatrix[i+1][j+1])));
	                        BigInteger temp2 = paillier.encrypt(temp.toBigInteger());
	                    	sumR = sumR.add((firstMatrix[x][y].red.multiply(temp2)));
	                    	sumG = sumG.add((firstMatrix[x][y].green.multiply(temp2)));
	                    	sumB = sumG.add((firstMatrix[x][y].blue.multiply(temp2)));
	                    	
	                    	//sum += firstMatrix[x][y] * transformMatrix[i+1][j+1] ;
	                    }
	                }
	                
	                //sumR = (firstMatrix[x][y].red.multiply(temp));
                    //sumG = (firstMatrix[x][y].green.multiply(temp));
                    //sumB = (firstMatrix[x][y].blue.multiply(temp));
	                
                    //System.out.println(sumR.toString());
                    
	                //EncryptedPoint encryptedPoint = new EncryptedPoint(sumR, sumG, sumB);
	                //System.out.println(encryptedPoint.red.toString());
	                MyImage.eI.points[x][y].red = sumR;
	                MyImage.eI.points[x][y].green = sumG;
	                MyImage.eI.points[x][y].blue = sumB;
	            }
	    	}
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	/*for(int j = 0; j < c1 - 2; j++) {
            for (int k = 0; k < r1 - 2; k++) {
            	for (int w = 0; w < transformMatrix.length; w++) {
					for (int v = 0; v < transformMatrix[0].length; v++) {
						int Sj = j + w;
						int Sv = k + v;
						sum += (int)(firstMatrix[Sj][Sv] * transformMatrix[w][v]);
					}
				}
            	product[j][k] = sum;
            }
        }*/

    }
    
	public static EncryptedPoint[][] processMatrix2(EncryptedPoint[][] firstMatrix, PaillierAlgorithm paillier) {
        
    	int r1 = firstMatrix.length;
    	int c1 = firstMatrix[0].length;
    	
    	System.out.println("r1 -- " + r1 + " : " + c1);
    	
    	EncryptedPoint[][] product = new EncryptedPoint[r1][c1];
    	
    	try {
	    	
    		for (int y = 1 ; y < c1-1; y++){
	            for (int x = 0 ; x < r1-1; x++){
	            	BigDecimal temp = BigDecimal.ONE;//BigDecimal.valueOf(1));
	                BigInteger sumR = BigInteger.ZERO;
	                BigInteger sumG = BigInteger.ZERO;
	                BigInteger sumB = BigInteger.ZERO;
	           
	                for (int i = -1 ; i <= 2; i++) {
	                    for (int j = -1 ; j <= 2; j++){
	                    	//System.out.println("x : " + x + " y : " + y + " i : " + i + " j :" + j);
	                    	//System.out.println(firstMatrix[x][y] + " : " + "transform");
	                        temp = new BigDecimal((Float.toString(transformMatrix[i+1][j+1])));
	                        BigInteger temp2 = paillier.encrypt(temp.toBigInteger());
	                    	sumR = (firstMatrix[x][y].red.multiply(temp2));
	                        sumG = (firstMatrix[x][y].green.multiply(temp2));
	                        sumB = (firstMatrix[x][y].blue.multiply(temp2));
	                    	
	                    	//sum += firstMatrix[x][y] * transformMatrix[i+1][j+1] ;
	                    }
	                }
	                
	                //sumR = (firstMatrix[x][y].red.multiply(temp));
                    //sumG = (firstMatrix[x][y].green.multiply(temp));
                    //sumB = (firstMatrix[x][y].blue.multiply(temp));
	                
                    //System.out.println(sumR.toString());
                    
	                //EncryptedPoint encryptedPoint = new EncryptedPoint(sumR, sumG, sumB);
	                //System.out.println(encryptedPoint.red.toString());
	                MyImage.eI.points[x][y].red = sumR;
	                MyImage.eI.points[x][y].green = sumG;
	                MyImage.eI.points[x][y].blue = sumB;
	            }
	    	}
    		
    		return product;
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	/*for(int j = 0; j < c1 - 2; j++) {
            for (int k = 0; k < r1 - 2; k++) {
            	for (int w = 0; w < transformMatrix.length; w++) {
					for (int v = 0; v < transformMatrix[0].length; v++) {
						int Sj = j + w;
						int Sv = k + v;
						sum += (int)(firstMatrix[Sj][Sv] * transformMatrix[w][v]);
					}
				}
            	product[j][k] = sum;
            }
        }*/

        return product;
    }
	
	public static EncryptedPoint[][] processMatrix3(EncryptedPoint[][] firstMatrix) {
        
    	int r1 = firstMatrix.length;
    	int c1 = firstMatrix[0].length;
    	
    	System.out.println("r1 -- " + r1 + " : " + c1);
    	
    	EncryptedPoint[][] product = new EncryptedPoint[r1][c1];
    	
    	BigInteger scalarR = BigInteger.TEN;
    	BigInteger scalarG = BigInteger.ONE;
    	BigInteger scalarB = BigInteger.ONE;
    	
    	try {
	    	
    		for (int y = 1 ; y < c1-1; y++){
	            for (int x = 0 ; x < r1-1; x++){
	            	//BigDecimal temp = BigDecimal.ONE;//BigDecimal.valueOf(1));
	                BigInteger sumR = BigInteger.ZERO;
	                BigInteger sumG = BigInteger.ZERO;
	                BigInteger sumB = BigInteger.ZERO;
	           
	                
	                //emp = new BigDecimal((Float.toString(transformMatrix[i+1][j+1])));
                    //BigInteger temp2 = paillier.encrypt(temp.toBigInteger());
                	sumR = (firstMatrix[x][y].red.multiply(scalarR));
                    sumG = (firstMatrix[x][y].green.multiply(scalarG));
                    sumB = (firstMatrix[x][y].blue.multiply(scalarB));
	                
	               
	                MyImage.eI.points[x][y].red = sumR;
	                MyImage.eI.points[x][y].green = sumG;
	                MyImage.eI.points[x][y].blue = sumB;
	            }
	    	}
    		
    		return product;
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	/*for(int j = 0; j < c1 - 2; j++) {
            for (int k = 0; k < r1 - 2; k++) {
            	for (int w = 0; w < transformMatrix.length; w++) {
					for (int v = 0; v < transformMatrix[0].length; v++) {
						int Sj = j + w;
						int Sv = k + v;
						sum += (int)(firstMatrix[Sj][Sv] * transformMatrix[w][v]);
					}
				}
            	product[j][k] = sum;
            }
        }*/

        return product;
    }
	
	public static EncryptedPoint[][] processMatrix3(EncryptedPoint[][] firstMatrix, PaillierAlgorithm paillier) {
        
    	int r1 = firstMatrix.length;
    	int c1 = firstMatrix[0].length;
    	
    	System.out.println("r1 -- " + r1 + " : " + c1);
    	
    	EncryptedPoint[][] product = new EncryptedPoint[r1][c1];
    	
    	BigInteger scalarR = BigInteger.TEN;
    	BigInteger scalarG = BigInteger.ONE;
    	BigInteger scalarB = BigInteger.ONE;
    	
    	try {
	    	
    		for (int y = 1 ; y < c1-1; y++){
	            for (int x = 0 ; x < r1-1; x++){
	            	//BigDecimal temp = BigDecimal.ONE;//BigDecimal.valueOf(1));
	                BigInteger sumR = BigInteger.ZERO;
	                BigInteger sumG = BigInteger.ZERO;
	                BigInteger sumB = BigInteger.ZERO;
	           
	                
	                //emp = new BigDecimal((Float.toString(transformMatrix[i+1][j+1])));
                    //BigInteger temp2 = paillier.encrypt(temp.toBigInteger());
                	sumR = (firstMatrix[x][y].red.multiply(scalarR));
                    sumG = (firstMatrix[x][y].green.multiply(scalarG));
                    sumB = (firstMatrix[x][y].blue.multiply(scalarB));
	                
	               
	                MyImage.eI.points[x][y].red = sumR;
	                MyImage.eI.points[x][y].green = sumG;
	                MyImage.eI.points[x][y].blue = sumB;
	            }
	    	}
    		
    		return product;
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	/*for(int j = 0; j < c1 - 2; j++) {
            for (int k = 0; k < r1 - 2; k++) {
            	for (int w = 0; w < transformMatrix.length; w++) {
					for (int v = 0; v < transformMatrix[0].length; v++) {
						int Sj = j + w;
						int Sv = k + v;
						sum += (int)(firstMatrix[Sj][Sv] * transformMatrix[w][v]);
					}
				}
            	product[j][k] = sum;
            }
        }*/

        return product;
    }
    
	public static EncryptedPoint[][] processMatrix4(EncryptedPoint[][] firstMatrix) {
        
    	int r1 = firstMatrix.length;
    	int c1 = firstMatrix[0].length;
    	
    	System.out.println("r1 -- " + r1 + " : " + c1);
    	
    	EncryptedPoint[][] product = new EncryptedPoint[r1][c1];
    	
    	BigInteger scalarR = BigInteger.TEN;
    	BigInteger scalarG = BigInteger.ONE;
    	BigInteger scalarB = BigInteger.ONE;
    	
    	try {
	    	
    		for (int y = 1 ; y < c1-1; y++){
	            for (int x = 0 ; x < r1-1; x++){
	            	//BigDecimal temp = BigDecimal.ONE;//BigDecimal.valueOf(1));
	                BigInteger sumR = BigInteger.ZERO;
	                BigInteger sumG = BigInteger.ZERO;
	                BigInteger sumB = BigInteger.ZERO;
	           
	                
	                //emp = new BigDecimal((Float.toString(transformMatrix[i+1][j+1])));
                    //BigInteger temp2 = paillier.encrypt(temp.toBigInteger());
                	sumR = (firstMatrix[x][y].red.multiply(scalarR));
                    sumG = (firstMatrix[x][y].green.multiply(scalarG));
                    sumB = (firstMatrix[x][y].blue.multiply(scalarB));
	                
	               
	                MyImage.eI.points[x][y].red = sumR;
	                MyImage.eI.points[x][y].green = sumG;
	                MyImage.eI.points[x][y].blue = sumB;
	            }
	    	}
    		
    		return product;
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	/*for(int j = 0; j < c1 - 2; j++) {
            for (int k = 0; k < r1 - 2; k++) {
            	for (int w = 0; w < transformMatrix.length; w++) {
					for (int v = 0; v < transformMatrix[0].length; v++) {
						int Sj = j + w;
						int Sv = k + v;
						sum += (int)(firstMatrix[Sj][Sv] * transformMatrix[w][v]);
					}
				}
            	product[j][k] = sum;
            }
        }*/

        return product;
    }
	
    public static int[][] multiplyMatrices(int[][] firstMatrix, int[][] secondMatrix, int r1, int c1, int c2) {
        int[][] product = new int[r1][c2];
        for(int i = 0; i < r1; i++) {
            for (int j = 0; j < c2; j++) {
                for (int k = 0; k < c1; k++) {
                    product[i][j] += firstMatrix[i][k] * secondMatrix[k][j];
                }
            }
        }

        return product;
    }

    public static void displayProduct(int[][] product) {
        System.out.println("Product of two matrices is: ");
        for(int[] row : product) {
            for (int column : row) {
                System.out.print(column + "    ");
            }
            System.out.println();
        }
    }
}