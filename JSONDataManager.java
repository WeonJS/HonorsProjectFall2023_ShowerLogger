package HonorsProjectFall2023_ShowerLogger;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.swing.filechooser.FileSystemView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class JSONDataManager {
	public static final int JSON_INDENT_SIZE = 4;
	private String dataJSONDirectory;
	private String dataFileName = "data.json";
	private String jsonPathString;
	
	public JSONDataManager() {
		dataJSONDirectory = FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + "\\ShowerLogger\\data\\";
		jsonPathString = dataJSONDirectory + dataFileName;
	}
	
	public void storeShowersDataList() {
		ArrayList<ShowerLogEntry> showers = EffortLogger.getInstance().getShowers();
		try {
			// write default json to new file for later use
			FileWriter writer = new FileWriter(jsonPathString);
			JSONArray showersJSONArray = new JSONArray();
			for (int i = 0; i < showers.size(); i++) {
				ShowerLogEntry shower = showers.get(i);
				String startTimeStr = shower.getStartTime().toString();
				String endTimeStr = shower.getEndTime().toString();
				int duration = shower.getDuration();
				float galsUsed = shower.getGallonsUsed();
				
				JSONObject showerObj = new JSONObject();
				showerObj.put("startTime", startTimeStr);
				showerObj.put("endTime", endTimeStr);
				showerObj.put("duration", duration);
				showerObj.put("gallonsUsed", galsUsed);
				showersJSONArray.put(showerObj);
			}
			System.out.println("Storing " + showers.size() + " showers.");
			JSONObject parentJSON = new JSONObject();
			
			
			parentJSON.put("showers", showersJSONArray);
			
			writer.write(parentJSON.toString(JSON_INDENT_SIZE));
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<ShowerLogEntry> retrieveShowersDataList() {
		ArrayList<ShowerLogEntry> showers = new ArrayList<>();
		try {
			Path jsonPath = Paths.get(jsonPathString);
			LinkOption[] linkOptions = new LinkOption[] { LinkOption.NOFOLLOW_LINKS };
			if (Files.exists(jsonPath, linkOptions)) {
				
				// read in the shower data
				FileReader reader = new FileReader(jsonPathString);
				JSONTokener tokener = new JSONTokener(reader);
				JSONObject jsonObject = new JSONObject(tokener);
				JSONArray showerObjList = jsonObject.getJSONArray("showers");
				for (int i = 0; i < showerObjList.length(); i++) {
					JSONObject shower = showerObjList.getJSONObject(i);
					String startTimeStr = shower.getString("startTime");
					String endTimeStr = shower.getString("endTime");
					int duration = shower.getInt("duration");
					float gallonsUsed = shower.getFloat("gallonsUsed");
					
					LocalDateTime startTime = LocalDateTime.parse(startTimeStr);
					LocalDateTime endTime = LocalDateTime.parse(endTimeStr);
					ShowerLogEntry e = new ShowerLogEntry(startTime, endTime);
					e.setDuration(duration);
					e.setGallonsUsed(gallonsUsed);
					
					showers.add(e);
				}
				System.out.println("Loaded " + showers.size() + " showers.");
				reader.close();
			} else {
				// create dir
				Path dir = Paths.get(dataJSONDirectory);
				Files.createDirectories(dir);
				
				// create file
				Path file = Paths.get(jsonPathString);
				Files.createFile(file);
				
				// write default json to new file for later use
				FileWriter writer = new FileWriter(jsonPathString);
				JSONObject defaultJSON = new JSONObject();
				defaultJSON.put("showers", new JSONArray());
				writer.write(defaultJSON.toString(JSON_INDENT_SIZE));
				writer.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return showers;
	}
}
