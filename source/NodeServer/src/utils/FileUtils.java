package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class FileUtils {

	private FileUtils(){}
	
	public static String readFile(String filename) throws IOException {
    	
    	FileInputStream inputStream = null;
    	Scanner sc = null;
    	String line = "";
    	try {
    	    inputStream = new FileInputStream(filename);
    	    sc = new Scanner(inputStream, "UTF-8");
    	   
    	    while (sc.hasNextLine()) {
    	        line += sc.nextLine();
    	        // System.out.println(line);
    	    }
    	    // note that Scanner suppresses exceptions
    	    if (sc.ioException() != null) {
    	        throw sc.ioException();
    	    }
    	    
    	} finally {
    	    if (inputStream != null) {
    	        inputStream.close();
    	    }
    	    if (sc != null) {
    	        sc.close();
    	    }
    	}
    	return line;
    }
    
    public static String readFile1(String filename) {
		
    	String returnLine = "";
    	
    	try(BufferedReader br = new BufferedReader(new FileReader(filename))) {
    	    for(String line; (line = br.readLine()) != null; ) {
    	        returnLine += line;
    	    }
    	    return returnLine;
    	} catch (Exception e) {
			// TODO: handle exception
		}
		return returnLine;
    	
	}
    
    public static void writeToFile(String filename, ArrayList<String> json){
    	try {
    		String temp = "./res/" + filename + ".txt";
    		PrintWriter out = new PrintWriter(temp);
			out.write("");
    		for(int i = 0; i < json.size(); i++){
    			System.out.println("line: " + i);
    			out.append(json.get(i));
    		}
    		out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    	
    }
    
    public static void writeToFile(String filename, String json){
    	try {
    		String temp = "./res/" + filename + ".txt";
    		PrintWriter out = new PrintWriter(temp);
			out.write(json);
    		out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    	
    }
	
	public static String[] getFilesList(String pathToFolder){
		
		File file = new File(pathToFolder);
		String[] files = file.list(new FilenameFilter() {
		  public boolean accept(File current, String name) {
		    return new File(current, name).isFile();
		  }
		});
		return files;
	}

}
