package cse360project;

import java.util.regex.Pattern;

import javax.swing.filechooser.FileSystemView;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;


// Nichoals Lorenzini

public class PokerDataHandler {
    // Store usernames and corresponding passwords
    private Pattern IDPattern;
    private Pattern passwordPattern;
    private Path PokerDataPath;
    private List<String> secondLines = new ArrayList<>();
    
    // Prototype Database for show-case purposes
    public PokerDataHandler() {
    	// calculate path based on OS
    	if(System.getProperty("os.name").equals("Mac OS X")) {
			PokerDataPath = Paths.get(EffortLogger.getInstance().getRootDirectory() + "/sessions/");
		}
		else 
			PokerDataPath = Paths.get(EffortLogger.getInstance().getRootDirectory() + "\\sessions\\");
    	
    	// create logins folder if it doesnt exist
    	FileDirectory.createFolder(PokerDataPath);
    	
        // username requirements: 
        // between 8 and 16 characters long; 
        // Alphanumeric, numbers, "-", "_" allowed
        IDPattern = Pattern.compile("^[a-zA-Z0-9-_]{8,16}$");
        
        // username requirements: 
        // between 8 and 16 characters long; 
        // Alphanumeric, numbers, "-", "_" allowed
        passwordPattern = Pattern.compile("^[a-zA-Z0-9-_]{4,16}$");
    }
    
 
    
    
public static String hash(String rawID) { //i NEED THE HASHED USERNAME ohion got the user 
    	
    	// hash username
    	String hashedString = "";
		try {
	        MessageDigest md = MessageDigest.getInstance("SHA-512");
	        byte[] hashedBytes = md.digest(rawID.getBytes(StandardCharsets.UTF_8));
	        hashedString = bytesToHex(hashedBytes);
	    } catch (NoSuchAlgorithmException e) {
	        e.printStackTrace();
	    }
		
		return hashedString;
	}
	
	public static String bytesToHex(byte[] bytes) {
        StringBuilder hexStringBuilder = new StringBuilder(2 * bytes.length);
        for (byte b : bytes) {
            hexStringBuilder.append(String.format("%02x", b)); // Convert byte to 2-digit hexadecimal representation
        }
        return hexStringBuilder.toString();
    }
    
    //method to detect validity of username for creating an account
    public boolean isValidID(String ID) {
    	return IDPattern.matcher(ID).matches();
    }

    // method to detect validity of password for creating an account
    public boolean isValidPassword(String password) {
    	return passwordPattern.matcher(password).matches();
    }
    
    public boolean existingUsername(String ID) {
    	Path PokerFilePath = Paths.get(PokerDataPath.toString(), hash(ID));
		return FileDirectory.filePathExists(PokerFilePath);
    }
    
    //called when creating new acc...
    public boolean addSession(String ID, String password, String Topics, String members) {
    	String hashedID = hash(ID);
    	String hashedPass = hash(password);
    	Path PokerFilePath = Paths.get(PokerDataPath.toString(), hashedID);
    	
    	try {
            String data = String.format("%s\nsessionID, %s\nsessionTopics, %s\nsessionMembers,$%s\n",
                    hashedPass, ID, Topics, members);
            Files.write(PokerFilePath, data.getBytes());
            System.out.print("Wrote: " + data);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    	
    }
    
    public String getPokerInfo(String ID) {
    	String hashedID = hash(ID);
    	Path PokerFilePath = Paths.get(PokerDataPath.toString(), hashedID);
    	 if (FileDirectory.filePathExists(PokerFilePath)) {
             try {
                 List<String> pokerFileLines = Files.readAllLines(PokerFilePath);
                 
                 if (!pokerFileLines.isEmpty()) {
                     String sessionID = pokerFileLines.get(1).substring("sessionID, ".length());
                     String sessionTopics = pokerFileLines.get(2).substring("sessionTopics, ".length());
                     String sessionMembers = pokerFileLines.get(3).substring("sessionMembers, ".length());
                     return sessionID + "-" + sessionTopics + "-" + sessionMembers;
                 }
             } catch (IOException e) {
                 e.printStackTrace();
             }
         }
    	
         return null;
    }
    
    public void retrieveSessions() {
		// get hashed username
    	Path folderPath = Paths.get(PokerDataPath.toString());

        try {
            

            // List all files in the folder using a DirectoryStream
            try (DirectoryStream<Path> files = Files.newDirectoryStream(folderPath)) {
                for (Path filePath : files) {
                    // Read the second line of each file
                    List<String> lines = Files.readAllLines(filePath);
                    if (lines.size() >= 2) {
                        secondLines.add(lines.get(1).substring("sessionID, ".length()));
                    }
                }
            }


            // If you want to store the second lines in an array
           
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
    
    
    public void alterMemberStorage(String ID, String Members) {
    	String hashedID = hash(ID);
    	Path PokerFilePath = Paths.get(PokerDataPath.toString(), hashedID);
    	if (FileDirectory.filePathExists(PokerFilePath)) {
            try {
                // Read the file into memory
                List<String> pokerFileLines = Files.readAllLines(PokerFilePath);

                // Find the line that starts with "sessionMembers"
                for (int i = 0; i < pokerFileLines.size(); i++) {
                    String line = pokerFileLines.get(i);
                    if (line.startsWith("sessionMembers")) {
                        // Modify the content after "sessionMembers"
                        String modifiedContent = "sessionMembers, " + Members; // Modify as needed
                        pokerFileLines.set(i, modifiedContent);
                    }
                }

                // Write the modified content back to the file
                Files.write(PokerFilePath, pokerFileLines, StandardCharsets.UTF_8, StandardOpenOption.WRITE);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
    }
    
    
    
    public void alterNameStorage(String ID, String name, String newName) {
    	String hashedID = hash(ID);
    	Path PokerFilePath = Paths.get(PokerDataPath.toString(), hashedID);
    	  if (FileDirectory.filePathExists(PokerFilePath)) {
    	        try {
    	            // Read the file into memory
    	            List<String> pokerFileLines = Files.readAllLines(PokerFilePath);

    	            // Find the line that starts with "sessionMembers"
    	            for (int i = 0; i < pokerFileLines.size(); i++) {
    	                String line = pokerFileLines.get(i);

    	                if (line.startsWith("sessionMembers")) {
    	                    // Split the line to work with individual name entries
    	                    String[] members = line.split(", ");
    	                    for (int j = 1; j < members.length; j++) {
    	                        String memberData = members[j];
    	                        if (memberData.startsWith(name + ":")) {
    	                            // Modify the data for the specified name
    	                            members[j] = newName;
    	                        }
    	                    }

    	                    // Reconstruct the line with modified member data
    	                    pokerFileLines.set(i, String.join(", ", members));
    	                }
    	            }

    	            // Write the modified content back to the file
    	            Files.write(PokerFilePath, pokerFileLines, StandardCharsets.UTF_8, StandardOpenOption.WRITE);

    	        } catch (IOException e) {
    	            e.printStackTrace();
    	        }
    	    }
        
    }
    
    
    
    
    public List<String> returnSessions(){
    	return secondLines;
    }
    
    //function called on login... validates information
    public boolean validateLogin(String ID, String password) {
    	String hashedID = hash(ID);
    	String hashedPassword = hash(password);
    	Path userPokerPath = Paths.get(PokerDataPath.toString(), hashedID);
    	
    	// match the login details to the file
    	if (FileDirectory.filePathExists(userPokerPath)) {
    		String storedPassword;
    		List<String> pokerFileLines = FileDirectory.getFileLines(userPokerPath);

    		for (String item: pokerFileLines) {
    			System.out.println(item);
    		}
    		if (!pokerFileLines.isEmpty()) {
        		storedPassword = pokerFileLines.get(0);
        		return storedPassword.equals(hashedPassword);
        	}
    	}
    	return false;
    }
    
}









