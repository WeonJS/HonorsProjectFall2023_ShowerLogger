package HonorsProjectFall2023_ShowerLogger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;

public class Defect {
	private String logUser;
	private String project;
	private String defectName;
	private String defectInfor;
	private String dStatus;
	private String inject;
	private String removed;
	private String category;
	public Defect(String _project, String _defectName, String _dInfo, String _status, String _inject, String _removed, String _category, String _logUser) {
		logUser = _logUser;
		project = _project;
		defectName = _defectName;
		defectInfor = _dInfo;
		dStatus = _status;
		inject = _inject;
		removed = _removed;
		category = _category;
	}
	
	public String getProject() {
		return project;
	}
	
	public String getDefectString() {
		return defectName;
	}
	
	public String getDefectInfo() {
		return defectInfor;
	}
	
	public String getDefectStatus() {
		return dStatus;
	}
	
	public String getInject() {
		return inject;
	}
	
	public String getRemoved() {
		return removed;
	}
	
	public String getCategory() {
		return category;
	}
	
	public void setProject(String newProject) {
		project = newProject;
	}
	
	public void setDefect(String defect) {
		defectName = defect;
	}
	
	public void setDefectInfo(String info) {
		defectInfor = info;
	}
	
	public void setDefectStatus(String status) {
		dStatus = status;
	}
	
	public void setInject(String _inject) {
		inject = _inject;
	}
	
	public void setRemoved(String _removed) {
		removed = _removed;
	}
	
	public void setCategory(String _category) {
		category = _category;
	}
	
	public String toCSVData() {
		String data = "";
		
		// add all fields as CSV listings
		data += String.format("logUser,%s\n", logUser);
		data += String.format("project,%s\n", project);
		data += String.format("defectName,%s\n",defectName);
		data += String.format("defectInfor,%s\n", defectInfor);
		data += String.format("dStatus,%s\n", dStatus);
		data += String.format("inject,%s\n", inject);
		data += String.format("removed,%s\n", removed);
		data += String.format("category,%s", category);
		
		return data;
	}
	
	// constructs an Effort object from a csv file
		public static Defect constructFromCSVFile(Path csvPath) {
			String _logUser = "";
			String _project = "";
			String _defectName = "";
			String _defectInfor = "";
			String _dStatus = "";
			String _inject = "";
			String _removed = "";
			String _category = "";
			
			try (BufferedReader reader = new BufferedReader(new FileReader(csvPath.toString()))) {
				String line;
				while ((line = reader.readLine()) != null) {
					int commaIndex = line.indexOf(",");
					String field = line.substring(0, commaIndex);
					String value = line.substring(commaIndex + 1, line.length());
					switch (field) {
						case "logUser":
							_logUser = value;
							break;
						case "project":
							_project = value;
						case "defectName":
							_defectName = value;
						case "defectInfor":
							_defectInfor = value;
						case "dStatus":
							_dStatus = value;
						case "inject":
							_inject = value;
						case "removed":
							_removed = value;
						case "category":
							_category = value;
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			Defect defect = new Defect(_project, _defectName, _defectInfor, _dStatus, _inject, _removed, _category, _logUser);
			
			return defect;
		}
	
}
