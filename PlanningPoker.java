package cse360project;


import java.util.ArrayList;
import java.util.Arrays;



// Nicholas Lorenzini 



public class PlanningPoker {
	
	public class PokerSession {
		private String hashedUser;
		private String hashedPass;
		public PokerSession(String ID, String pass) {
			hashedUser = PokerDataHandler.hash(ID);
			hashedPass = PokerDataHandler.hash(pass);
		}
		
		public String getHashedUser() {
			return hashedUser;
		}
		
		public String getHashedPass() {
			return hashedPass;
		}
	}
	
	

	private ArrayList<String> info = new ArrayList<>();
	String[] temp2 = new String[5];
	private PokerSession pokerSession = null;
	private PokerDataHandler dh = new PokerDataHandler();
	private ArrayList<String> newTopics = new ArrayList<>();
	private ArrayList<String> members = new ArrayList<>();
	
	public void getInfo(String ID) {
		String temp = dh.getPokerInfo(ID);
		temp2 = temp.split("-\\s*");
		
	}

	public String getID() {
		return temp2[0];
	}
	public String getTopics() {
		
		return temp2[1];
	}
	public String getMembers() {
		return temp2[2];
	}
	
	public void addMember(String concat) {
		String useTopics = temp2[1];
		String[] topicsArray = useTopics.split(",\\s*");
		
		for (int i = 0; i < topicsArray.length; i++) {
            concat = concat + ":0";
		}

		String members = getMembers();
		char firstChar = members.charAt(0);
		
		if(firstChar == '$') {
			temp2[2] = concat;
		}
		else {
		String temp = temp2[2] + ", " + concat;
		temp2[2] = temp;
		}
		
		String toAlter = temp2[2];
		String ID = temp2[0];
		dh.alterMemberStorage(ID, toAlter);
		
		
		
	}
	
	
	 public boolean handleJoinAttempt(String ID, String password) {
	        // Check the login credentials with the UsernameDatabase
	        boolean isValidLogin = dh.validateLogin(ID, password);

	        if (isValidLogin) {
	            pokerSession = new PokerSession(ID, password);
	            return true;
	        } else {
	          return false;
	        }
	        
	    }
	    
	    public String attemptCreateSession(String newID, String newPassword, String topics, String Members) {
	  
	        // Check if the new username already exists
	        if (dh.existingUsername(newID) ) {
	            return "ID exists. Please enter a new ID";
	        }
	        
	        //check to see if username is valid input
	        else if (!dh.isValidID(newID)) {
	            return "Session Creation Failed\", \"Invalid ID. Please refer to username requirements";
	        }
	        //check to see if password is valid input
	        else if (!dh.isValidPassword(newPassword)) {
	        	return "Session Creation Failed\", \"Invalid Password. Please refer to password requirements";
	        }
	        else {
	            // Add the new ID, Password and populate Topics List
	        	String addMem = "$temp";
	        	dh.addSession(newID, newPassword, topics, addMem);
	        	return "Session created";
	        }
	    }
	    
	    
	    public void setMembers(String toAdd) {
	    	members.add(toAdd);
	    }
	    
	    public PokerSession getPokerSession() {
	    	return pokerSession;
	    }
	    
	    public PokerDataHandler getPokerDataHandler() {
	    	return dh;
	    }
	    
	

	
}