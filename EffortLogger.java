package HonorsProjectFall2023_ShowerLogger;
	
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.swing.filechooser.FileSystemView;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class EffortLogger extends Application {
	
	// singleton instance
	private static EffortLogger instance;
	
	private Login loginSession;
	private EffortDataHandler effortDataHandler;
	private PokerDataHandler pokerDataHandler;
	private LocalDateTime startTime;

	private String documentsPath;
	private String effortLoggerRootDirectory;
	private String effortDataPath;
	private ArrayList<ShowerLogEntry> showers = new ArrayList<ShowerLogEntry>();
	private JSONDataManager jsonManager = new JSONDataManager();
	
	@Override
	public void start(Stage primaryStage) {
		if(System.getProperty("os.name").equals("Mac OS X")) {
			documentsPath = FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + "/Documents/";
			effortLoggerRootDirectory = documentsPath + "EffortLogger/";
			effortDataPath = effortLoggerRootDirectory + "data/";
		}
		else {
			documentsPath = FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
			effortLoggerRootDirectory = documentsPath + "\\EffortLogger\\";
			effortDataPath = effortLoggerRootDirectory + "data\\";
		}
		
		if (instance == null)
			instance = this;
		else
			return;
		
		showers = jsonManager.retrieveShowersDataList();
		
		// these should run after the user logs in successfully
		
		loginSession = new Login();
		effortDataHandler = new EffortDataHandler();
		pokerDataHandler = new PokerDataHandler();
		
		try {
			Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				public void handle(WindowEvent we) {
					if (loginSession.getLoginSession() != null) {
						// update/create the updated/created efforts in the file system
						effortDataHandler.storeEfforts();
						effortDataHandler.storeDefects();
						
						jsonManager.storeShowersDataList();
						Controller.getInstance().destroyThread();
					}
	          	}
			});
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void startShower() {
		startTime = LocalDateTime.now();
	}
	
	
	// ==================
	// accessors/mutators
	// ==================
	public LocalDateTime getStartTime() {
		return startTime;
	}
	
	public void setStartTime(LocalDateTime start) {
		startTime = start;
	}
	
	public ArrayList<ShowerLogEntry> getShowers() {
		return showers;
	}
	
	public JSONDataManager getJSONManager() {
		return jsonManager;
	}
	
	public void endShower() {
		ShowerLogEntry entry = new ShowerLogEntry(startTime, LocalDateTime.now());
		showers.add(entry);
		startTime = null;
		System.out.println("Shower duration: " + entry.getDuration());
		System.out.println("Shower gals: " + entry.getGallonsUsed());
	}
	
	public void addShowerEntry(ShowerLogEntry entry) throws IOException {
		// update runtime record of showers
		showers.add(entry);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public static EffortLogger getInstance() {
		return instance;
	}
	
	public Login getLogin() {
		return loginSession;
	}
	
	public PokerDataHandler getPokerDataHandler() {
		return pokerDataHandler;
	}
	
	public EffortDataHandler getEffortDataHandler() {
		return effortDataHandler;
	}
	
	public Path getRootDirectory() {
		return Paths.get(effortLoggerRootDirectory);
	}
	
	public Path getDataPathDirectory() {
		return Paths.get(effortDataPath);
	}
}