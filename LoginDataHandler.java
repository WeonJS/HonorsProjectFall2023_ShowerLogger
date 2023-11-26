package HonorsProjectFall2023_ShowerLogger;

import java.util.regex.Pattern;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


import java.util.List;



// Nichoals Lorenzini

public class LoginDataHandler {
    // Store usernames and corresponding passwords
    private Pattern usernamePattern;
    private Pattern passwordPattern;
    private Path loginDataPath;
    
    // Prototype Database for show-case purposes
    public LoginDataHandler() {
    	// calculate path based on OS
    	if(System.getProperty("os.name").equals("Mac OS X")) {
			loginDataPath = Paths.get(EffortLogger.getInstance().getRootDirectory() + "/logins/");
		}
		else 
			loginDataPath = Paths.get(EffortLogger.getInstance().getRootDirectory() + "\\logins\\");
    	
    	// create logins folder if it doesnt exist
    	FileDirectory.createFolder(loginDataPath);
    	
        // username requirements: 
        // between 8 and 16 characters long; 
        // Alphanumeric, numbers, "-", "_" allowed
        usernamePattern = Pattern.compile("^[a-zA-Z0-9-_]{8,16}$");
        
        // password requirements
        // between 8 and 16 characters;
        // (at least 1) Alphanumeric;
        // (at least 1) number;
        // (at least 1) capital letter(s) and special character(s)
        // most special characters allowed
        passwordPattern = Pattern.compile("^(?=.*[a-zA-Z0-9])(?=.*[0-9])(?=.*[A-Z])(?=.*[!@#\\$%^&*()_+\\-=\\[\\]\\{}|;:'\",.<>/?]).{8,16}$");
    }
    
    public static String hash(String rawUser) { //i NEED THE HASHED USERNAME ohion got the user 
    	
    	// hash username
    	String hashedString = "";
		try {
	        MessageDigest md = MessageDigest.getInstance("SHA-512");
	        byte[] hashedBytes = md.digest(rawUser.getBytes(StandardCharsets.UTF_8));
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
    public boolean isValidUsername(String username) {
    	return usernamePattern.matcher(username).matches();
    }

    // method to detect validity of password for creating an account
    public boolean isValidPassword(String password) {
    	return passwordPattern.matcher(password).matches();
    }
    
    public boolean existingUsername(String username) {
    	Path loginFilePath = Paths.get(loginDataPath.toString(), hash(username));
		return FileDirectory.filePathExists(loginFilePath);
    }
    
    //called when creating new acc...
    public boolean addUser(String username, String password) {
    	String hashedUser = hash(username);
    	String hashedPass = hash(password);
    	Path loginFilePath = Paths.get(loginDataPath.toString(), hashedUser);
		
		return FileDirectory.writeToFile(loginFilePath, hashedPass);
    }
    
    //function called on login... validates information
    public boolean validateLogin(String username, String password) {
    	String hashedUsername = hash(username);
    	String hashedPassword = hash(password);
    	Path userLoginPath = Paths.get(loginDataPath.toString(), hashedUsername);
    	
    	// match the login details to the file
    	if (FileDirectory.filePathExists(userLoginPath)) {
    		String storedPassword;
    		List<String> loginFileLines = FileDirectory.getFileLines(userLoginPath);
    		if (loginFileLines.size() > 0) {
        		storedPassword = loginFileLines.get(0);
        		return storedPassword.equals(hashedPassword);
        	}
    	}
    	return false;
    }
    
}