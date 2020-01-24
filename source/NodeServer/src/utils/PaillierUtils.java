package utils;

import paillierPrototype.PaillierAlgorithm;

public class PaillierUtils {

	private PaillierUtils(){}
	
    public static PaillierAlgorithm generatePaillier(){
    	
    	PaillierAlgorithm paillier = new PaillierAlgorithm();
    	paillier.initialize(128);// passing the bitLength of
		// key
    	paillier.generateKeyPair();
    	return paillier;
    	
    }
	
    public static PaillierAlgorithm initializePaillier(int force){
		
    	PaillierAlgorithm paillier = new PaillierAlgorithm();
    	paillier.initialize(force);// passing the bitLength of
		// key
    	paillier.generateKeyPair();
    	
    	return paillier;
		
	}
    
}
