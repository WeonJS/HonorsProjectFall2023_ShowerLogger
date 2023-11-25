package cse360project;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class EffortDataHandler {
	
	private Path directoryPath;
	
	// keeps track of efforts that have been altered/created by the user
	private ArrayList<Effort> updatedEfforts = new ArrayList<>();
	private ArrayList<Defect> updatedDefects = new ArrayList<>();
	
	// keeps track of all user efforts
	private ArrayList<Effort> userEfforts = new ArrayList<>();
	private ArrayList<Defect> userDefects = new ArrayList<>();
	
	private ArrayList<Effort> effortsToDeleteOnClose = new ArrayList<>();
	private ArrayList<Defect> defectsToDeleteOnClose = new ArrayList<>();
	
	public EffortDataHandler() {
		directoryPath = EffortLogger.getInstance().getDataPathDirectory();
	}
	
	// retrieves and decrypts all the user's efforts and stores them into userEfforts arraylist.
	public boolean retrieveEfforts() {
		
		// get hashed username
		Login.LoginSession loginSession = EffortLogger.getInstance().getLogin().getLoginSession();
		String hashedUsername = loginSession.getHashedUser();
		
		// navigate to directory for this user's effort logs
		Path userDirectoryPath = Paths.get(directoryPath.toString(), hashedUsername);
		
		try {
			// if the effortlogger data path does not exist, make it
			FileDirectory.createFolder(directoryPath);
			
			// if user doesn't have a directory, make one
			FileDirectory.createFolder(userDirectoryPath);
			
			// populate array of user efforts
			DirectoryStream<Path> directoryStream = Files.newDirectoryStream(userDirectoryPath);
			for (Path filePath : directoryStream) {
				if (isEffortFilePath(filePath)) {
					userEfforts.add(Effort.constructFromCSVFile(filePath));
				}
			}
			System.out.println("Loaded " + userEfforts.size() + " efforts for this user.");
    		return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		return false;
	}
	
	// retrieves and decrypts all the user's efforts and stores them into userEfforts arraylist.
		public boolean retrieveDefects() {
			// navigate to directory for this user's effort logs
			Path userDirectoryPath = getUserDirectoryPath();
			
			try {
				// if the effortlogger data path does not exist, make it
				FileDirectory.createFolder(directoryPath);
				
				// if user doesn't have a directory, make one
				FileDirectory.createFolder(userDirectoryPath);
				
				// populate array of user efforts
				DirectoryStream<Path> directoryStream = Files.newDirectoryStream(userDirectoryPath);
				for (Path filePath : directoryStream) {
					if (isDefectFilePath(filePath)) {
						System.out.println("found");
						userDefects.add(Defect.constructFromCSVFile(filePath));
					}
					
				}
				System.out.println("Loaded " + userDefects.size() + " defects for this user.");
	    		return true;
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
			
			return false;
		}
	
	// stores runtime efforts of user which have been altered/created.
	public boolean storeEfforts() {
		// navigate to directory for this user's effort logs
		Path userDirectoryPath = getUserDirectoryPath();
		
		// store edited/created efforts
		for (Effort effort : updatedEfforts) {
			// uses the effort start date to uniquely identify each effort file name
			String effortIdentifier = effort.getStartTime().toString().replaceAll(":", "_");
			System.out.println(effort.getDuration());
			// "E" flag identifies that it is an effort
			String effortFileName = "E " + effortIdentifier;
			
			// convert class data to CSV string
			String effortCSV = effort.toCSVData();
			
			// write data to path
			Path file = Paths.get(userDirectoryPath.toString(), effortFileName);
			FileDirectory.writeToFile(file, effortCSV);
		}
		
		
		
		// delete the efforts to be deleted on close
		DirectoryStream<Path> directoryStream;
		ArrayList<String> deletedStartTimes = new ArrayList<>();
		for (Effort ef : effortsToDeleteOnClose) {
			deletedStartTimes.add("E " + ef.getStartTime().toString().replaceAll(":", "_"));
			System.out.println("ADDED TO BE DELETED: " + ef);
		}
		
		try {
			directoryStream = Files.newDirectoryStream(userDirectoryPath);
			for (Path filePath : directoryStream) {
				String fileName = filePath.getName(filePath.getNameCount() - 1).toString();
				if (deletedStartTimes.contains(fileName)) {
					FileDirectory.deleteFile(filePath);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return true;
	}
	
	private Path getUserDirectoryPath() {
		Login.LoginSession loginSession = EffortLogger.getInstance().getLogin().getLoginSession();
		
		if (loginSession == null)
			return null;
		
		String hashedUsername = EffortLogger.getInstance().getLogin().getLoginSession().getHashedUser();
		
		// navigate to directory for this user's effort logs
		Path userDirectoryPath = Paths.get(directoryPath.toString(), hashedUsername);
		
		return userDirectoryPath;
	}
	
	public boolean storeDefects() {
		Path userDirectoryPath = getUserDirectoryPath();
		
		
		// store created/updated defects
		for (Defect defect : updatedDefects) {
			// uses the effort start date to uniquely identify each effort file name
			String defectIdentifier = defect.getDefectString();
			// "E" flag identifies that it is an effort
			String defectFileName = "D " + defectIdentifier;
			
			// convert class data to CSV string
			String effortCSV = defect.toCSVData();
			
			// write data to path
			Path file = Paths.get(userDirectoryPath.toString(), defectFileName);
			FileDirectory.writeToFile(file, effortCSV);
		}
		
		// delete the efforts to be deleted on close
		DirectoryStream<Path> directoryStream;
		ArrayList<String> deletedDefectStrings = new ArrayList<>();
		for (Defect defect : defectsToDeleteOnClose) {
			deletedDefectStrings.add("D " + defect.getDefectString());
			System.out.println("ADDED TO BE DELETED: " + defect);
		}
		
		try {
			directoryStream = Files.newDirectoryStream(userDirectoryPath);
			for (Path filePath : directoryStream) {
				String fileName = getFileNameFromPath(filePath);
				if (deletedDefectStrings.contains(fileName)) {
					FileDirectory.deleteFile(filePath);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return true;
	}
	
	private String getFileNameFromPath(Path p) {
		return p.getName(p.getNameCount() - 1).toString();
	}
	
	public void createEffort(Effort e) {
		addToUserEfforts(e);
		addToUpdatedEfforts(e);
	}
	
	void addToUpdatedEfforts(Effort e) {
		updatedEfforts.add(e);
	}
	
	void addToUserEfforts(Effort e) {
		userEfforts.add(e);
	}
	
	public ArrayList<Effort> getUpdatedEfforts() {
		return updatedEfforts;
	}
	
	public ArrayList<Effort> getUserEffortArray(){
		return userEfforts;
	}
	
	public Path getRootDirectory() {
		return directoryPath;
	}
	
	public boolean removeEffort(Effort e) {
		for (Effort effort : userEfforts) {
			if (e.equals(effort)) {
				System.out.println("QUEUED TO BE DELETED " + e);
				effortsToDeleteOnClose.add(effort);
				userEfforts.remove(effort);
				return true;
			}
		}
		return false;
	}
	
	public boolean removeDefect(String defectName) {
		for (Defect defect : userDefects) {
			if (defectName.equals(defect.getDefectString())) {
				System.out.println("QUEUED TO BE DELETED " + defectName);
				defectsToDeleteOnClose.add(defect);
				userDefects.remove(defect);
				return true;
			}
		}
		return false;
	}
	
	public Effort getEffort(LocalDateTime start) {
		for (Effort e : userEfforts) {
			if (e.getStartTime().equals(start)) {
				return e;
			}
		}
		return null;
	}
	
	public void createDefect(Defect d) {
		addToUpdatedDefects(d);
		addToUserDefects(d);
	}
	
	
	private void addToUpdatedDefects(Defect newDefect) {
		updatedDefects.add(newDefect);
	}
	
	private void addToUserDefects(Defect newDefect) {
		userDefects.add(newDefect);
	}
	
	public Defect getDefect(String defect) {
		for (Defect d : userDefects) {
			if (d.getDefectString().equals(defect)) {
				return d;
			}
		}
		return null;
	}
	
	public void replaceDefect(Defect olddefect, Defect newDefect) {
		int index = 0;
		for (Defect d  : userDefects) {
			if (d.getDefectString().equals(olddefect.getDefectString())) {
				userDefects.set(index, newDefect);
			}
			index++;
		}
	}
	
	public ArrayList<Defect> getDefectArray() {
		return userDefects;
	}
	
	public void updateDefect(Defect oldDefect, Defect newDefect) {
		if (updatedDefects.contains(oldDefect)) {
			updatedDefects.remove(oldDefect);
			updatedDefects.add(newDefect);
		} else {
			updatedDefects.add(newDefect);
		}
		
		removeDefect(oldDefect.getDefectString());
		userDefects.add(newDefect);
	}
	
	public void updateEffort(Effort oldEffort, Effort newEffort) {
		
		if (updatedEfforts.contains(oldEffort)) {
			updatedEfforts.remove(oldEffort);
			updatedEfforts.add(newEffort);
		} else {
			updatedEfforts.add(newEffort);
		}
		
		removeEffort(oldEffort);
		userEfforts.add(newEffort);
	}
	
	public void clearDefectLog(String project) {
		Path userDirectoryPath = getUserDirectoryPath();
		
		DirectoryStream<Path> directoryStream;
		try {
			directoryStream = Files.newDirectoryStream(userDirectoryPath);
			for (Path filePath : directoryStream) {
				if (isDefectFilePath(filePath)) {
					Defect d = Defect.constructFromCSVFile(filePath);
					if (d.getProject().equals(project)) {
						removeDefect(d.getDefectString());
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private boolean isDefectFilePath(Path p) {
		return p.getName(p.getNameCount() - 1).toString().charAt(0) == 'D';
	}
	
	private boolean isEffortFilePath(Path p) {
		return p.getName(p.getNameCount() - 1).toString().charAt(0) == 'E';
	}
}
