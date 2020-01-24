package utils;

import paillierPrototype.PaillierAlgorithm;
import image.MyEncryptedImage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonUtils {

	private JsonUtils(){}

	public static String getMyEncryptedImageJson(MyEncryptedImage eI){
	    	
	    Gson gson = new Gson();
		return gson.toJson(eI);
	}
	    
	public static MyEncryptedImage getMyEncryptedImageFromJson(String json){
	    	
	    Gson gson = new Gson();
	    MyEncryptedImage myEncryptedImage = gson.fromJson(json, MyEncryptedImage.class);
		return myEncryptedImage;
	}
	
	public static String getPaillierJson(PaillierAlgorithm paillier){
		
		GsonBuilder builder = new GsonBuilder();
		builder.excludeFieldsWithoutExposeAnnotation();
		Gson gson = builder.create();
		return gson.toJson(paillier);
		
	}
	
	public static PaillierAlgorithm getPaillierFromJson(String json){
		
		Gson gson = new Gson();
		PaillierAlgorithm paillier = gson.fromJson(json, PaillierAlgorithm.class);
		return paillier;
	}

}
