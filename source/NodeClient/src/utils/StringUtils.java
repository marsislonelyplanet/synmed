package utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class StringUtils {

	private StringUtils(){}
	
	public static ArrayList<String> chunk_split(String text, int size){
    	
    	System.out.println("chunk split");
    	ArrayList<String> parts = new ArrayList<>();
        int length = text.length();
        for (int i = 0; i < length; i += size) {
            parts.add(text.substring(i, Math.min(length, i + size)));
        }
        //parts.add("AAAAAAA");
        return parts;
    }
  
    public static ArrayList<String> chunk_split2(String original, int length) throws IOException {
        
    	ByteArrayInputStream bis = new ByteArrayInputStream(original.getBytes());
        int n = 0;
        byte[] buffer = new byte[length];
        String result = "";
        ArrayList<String> returnArray = new ArrayList<String>();
        System.out.println("returnArray");
        while ((n = bis.read(buffer)) > 0) {
            for (byte b : buffer) {
                result += (char) b;
            }
            Arrays.fill(buffer, (byte) 0);
            returnArray.add(result);   
            System.out.println("chunk split exists");
        }
        //returnArray.add("AAAAAAA");
        return returnArray;
    }

}
