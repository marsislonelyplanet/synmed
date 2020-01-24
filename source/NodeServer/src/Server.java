import image.MyEncryptedImage;
import image.MyImage;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.*; 
import java.text.*; 
import java.util.*; 
import java.net.*; 

import matrix.MultiplyMatrix;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import paillierPrototype.PaillierAlgorithm;
import utils.FileUtils;
import utils.JsonUtils;
import utils.PaillierUtils;

import java.util.zip.*; 

// Server class 
public class Server { 
	
	public static void main(String[] args) throws IOException { 
        // server is listening on port 5056 
        ServerSocket ss = new ServerSocket(5056); 
        // running infinite loop for getting 
        // client request 
        while (true) { 
            
        	Socket s = null; 
              
            try { 
                // socket object to receive incoming client requests 
                s = ss.accept(); 
                  
                System.out.println("A new client is connected : " + s); 
                  
                // obtaining input and out streams 
                DataInputStream dis = new DataInputStream(s.getInputStream()); 
                DataOutputStream dos = new DataOutputStream(s.getOutputStream()); 
                  
                System.out.println("Assigning new thread for this client"); 
  
                // create a new thread object 
                Thread t = new ClientHandler(s, dis, dos); 
  
                // Invoking the start() method 
                t.start(); 
                  
            } catch (Exception e) { 
                s.close(); 
                e.printStackTrace(); 
            } 
        } 
    } 
} 
  
// ClientHandler class 
class ClientHandler extends Thread { 
    
	public static MyImage myImage;
	private static PaillierAlgorithm paillier;
	public static String paillierJson;
	
	public static void decryptMyImage(PaillierAlgorithm paillier) {
		
		myImage.decryptedImage = new int[myImage.eI.width][myImage.eI.height];
		
		for (int i = 0; i < myImage.eI.width; i++){
	    	for (int j = 0; j < myImage.eI.height; j++){
	    		try {
	    			int dRed = paillier.decrypt(myImage.eI.points[i][j].red).intValue();
	    			int dGreen = paillier.decrypt(myImage.eI.points[i][j].green).intValue();
	    			int dBlue = paillier.decrypt(myImage.eI.points[i][j].blue).intValue();
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
	    			myImage.decryptedImage[i][j] = c.getRGB();
	    		} catch (Exception e){
	    			e.printStackTrace();
	    		}
	    	}
	    }
	}
	
	DateFormat fordate = new SimpleDateFormat("yyyy/MM/dd"); 
    DateFormat fortime = new SimpleDateFormat("hh:mm:ss"); 
    final DataInputStream dis; 
    final DataOutputStream dos; 
    final Socket s; 
      
  
    // Constructor 
    public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos) { 
        
    	this.s = s; 
        this.dis = dis; 
        this.dos = dos; 
    } 
        
    @Override
    public void run() { 
     
    	paillier = PaillierUtils.initializePaillier(128);
        
    	paillierJson = JsonUtils.getPaillierJson(paillier);
    	ArrayList<MyEncryptedImage> m = new ArrayList<MyEncryptedImage>();
    	ArrayList<String> processing = new ArrayList<String>();
    	
        String fileLocation = "./res/f.png";//"./res/ferrari.jpg"; 
        
        try {
         
        	myImage.readImage(fileLocation);
        	myImage.generateEncryptedImage(paillier);
        
         
        } catch (Exception e){
        	e.printStackTrace();
        }
    	
    	String received; 
        String toreturn; 
        long timeMilli;
        long total = 0;
        boolean flag = false;
        ArrayList<String> imageArrayRepresentation = new ArrayList<String>();
		
        
        while (true) { 
            
        	try { 
  
                // Ask user what he wants 
                dos.writeUTF("What do you want?[Date | Time | Trusted | Untrusted]..\n"+ 
                            "Type Exit to terminate connection."); 
                  
                // receive the answer from client 
                received = dis.readUTF(); 
                  
                if(received.equals("Exit")) {  
                    System.out.println("Client " + this.s + " sends exit..."); 
                    System.out.println("Closing this connection."); 
                    this.s.close(); 
                    System.out.println("Connection closed"); 
                    break; 
                } 
                  
                // creating Date object 
                Date date = new Date(); 
                  
                // write on output stream based on the 
                // answer from the client 
                switch (received) { 
                  
                    case "Date" : 
                        toreturn = fordate.format(date); 
                        dos.writeUTF(toreturn); 
                        break; 
                          
                    case "Time" : 
                        toreturn = fortime.format(date); 
                        dos.writeUTF(toreturn); 
                        break; 
                        
                    case "Trusted" : 
                    	timeMilli = date.getTime();
                    	total = timeMilli;
                    	String outgoingTrusted = "trusted outgoing : " + timeMilli;
                    	processing.add(outgoingTrusted);
                    	FileUtils.writeToFile("client/trusted/paillier", paillierJson);
                    	String temp = JsonUtils.getMyEncryptedImageJson(myImage.eI);
                    	String encryptedImage = "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@";
                    	FileUtils.writeToFile("client/trusted/image", temp);
                    	dos.writeUTF("so, you are trusted: ");
                    	dos.flush();
                    	break;
                    
                    case "Untrusted" : 
                    	timeMilli = date.getTime();
                    	total = timeMilli;
                    	String outgoingUntrusted = "untrusted outgoing : " + timeMilli;
                    	processing.add(outgoingUntrusted);
                    	encryptedImage = "so, you are untrusted: ";
                    	temp = JsonUtils.getMyEncryptedImageJson(myImage.eI);
                    	FileUtils.writeToFile("client/untrusted/image", temp);
                    	dos.writeUTF(encryptedImage);
                    	dos.flush();
                       	break;
                    	
                    default: 
                        //dos.writeUTF("Invalid input"); 
                        if(received.contains("untrusted response: ")){
                        	
                        	String incomingImage = FileUtils.readFile("./res/server/untrusted/image.txt");
                        	m.add(JsonUtils.getMyEncryptedImageFromJson(incomingImage));//(DecompressArray(incomingImage.getBytes()).toString()));
                        	timeMilli = date.getTime();
                        	total -= timeMilli;
                        	total = total * (-1);
                        	String incomingTrusted = "untrusted response : " + timeMilli;
                            processing.add(incomingTrusted);
                            for (int i = 0; i < processing.size(); i++) {
								
                        		System.out.println(processing.get(i));
                        		
							}
                            System.out.println("total time untrusted" + total);
                        	
                        	
                        	
                        } else if(received.contains("trusted response: ")){
                            	
                        	String incomingImage = FileUtils.readFile("./res/server/trusted/image.txt");
                        	m.add(JsonUtils.getMyEncryptedImageFromJson(incomingImage));//(DecompressArray(incomingImage.getBytes()).toString()));
                        	timeMilli = date.getTime();
                        	total -= timeMilli;
                        	total = total * (-1);
                        	String incomingTrusted = "trusted response : " + timeMilli;
                            processing.add(incomingTrusted);
                            for (int i = 0; i < processing.size(); i++) {
								
                        		System.out.println(processing.get(i));
                        		
							}
                            System.out.println("total time trusted" + total);
                            
                        } else if (received.contains("times")){
                        	
                        	for (int i = 0; i < processing.size(); i++) {
								
                        		System.out.println(processing.get(i));
                        		
							}
                        	
                        }
                     
                        break; 
                } 
            
        	} catch (IOException e) { 
                e.printStackTrace(); 
            } 
        } 
          
        try { 
            // closing resources 
            this.dis.close(); 
            this.dos.close(); 
              
        } catch(IOException e){ 
            e.printStackTrace(); 
        } 
    } 
} 
