package HonorsProjectFall2023_ShowerLogger;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Text;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.List;

public class Controller implements Initializable{

	private String currentTab = "Create Effort";
	
	@FXML
	private ComboBox<String> projectComboBox;
	@FXML
	private ComboBox<String> lifeCycleComboBox;
	@FXML
	private ComboBox<String> effortCatComboBox;
	@FXML
	private ComboBox<String> deliverableComboBox;
	@FXML
	private ComboBox<String> lifeCycleComboBox2;
	@FXML
	private ComboBox<String> effortCatComboBox2;
	@FXML
	private ComboBox<String> editEffortComboBox;
	@FXML
	private ComboBox<String> dropDown_Defects;
	@FXML
	private Label projDefectsLabel;
	@FXML
	private Label errorLabel;
	@FXML
	private Text projStatus; // to be used later cause i aint doin allat yet
	@FXML
	private Label successLabel;
	@FXML
	private TextField defectEntry;
	@FXML 
	private Text defectNum;
	@FXML
	private Text saveStatus;
	@FXML
	private Text defectStatus;
	@FXML
	private TextArea defectInfo;
	@FXML
	private Label editErrorLabel;
	@FXML
	private Label editSuccessLabel;
	@FXML
	private ListView<String> stepsInjected;
	@FXML
	private ListView<String> stepsRemoved;
	@FXML
	private ListView<String> defectCat;
	@FXML
	private TextField editDate;
	@FXML
	private TextField editStartTime;
	@FXML
	private TextField editEndTime;
	@FXML
	private TabPane loggedInView;
	@FXML
	private Pane loginView;
	@FXML
	private TextField usernameField;
	@FXML
	private TextField passwordField;
	@FXML
	private Text loginMessage;
	@FXML
	private Text loginMessage1;
	@FXML
	private Pane createAccountView;
	@FXML
	private TextField usernameField2;
	@FXML
	private PasswordField passwordField2;
	@FXML
	private Pane pokerFirstView;
	@FXML
	private Pane pokerSecondView;
	@FXML
	private TextField IDField;
	@FXML
	private TextField passwordSessionField;
	@FXML
	private TextField IDField2;
	@FXML
	private TextField passwordSessionField2;
	@FXML
	private TextField topicsField;
	@FXML
	private ComboBox<String> activeSessions; 
	@FXML
	private Text createMessage;
	@FXML
	private Text createMessage2;
	@FXML
	private ComboBox<String> selectDefectCombo;
	@FXML
	private Pane pokerViewPane;
	@FXML
	private Text currentTopicText;
    @FXML
    private Text yourVoteText;
    @FXML
    private Text sessionAvgText;
    @FXML
    private Text name1;
    @FXML
    private Text name2;
    @FXML
    private Text name3;
    @FXML
    private Text name4;
    @FXML
    private Text name5;
    @FXML
    private Text name6;
    @FXML
    private Text name7;
    @FXML
    private Text name1left;
    @FXML
    private Text name2left;
    @FXML
    private Text name3left;
    @FXML
    private Text name4left;
    @FXML
    private Text name5left;
    @FXML
    private Text name6left;
    @FXML
    private Text name7left;
    @FXML
    private Button button1;
    @FXML
    private Button button2;
    @FXML
    private Button button3;
    @FXML
    private Button button4;
    @FXML
    private Button button5;
    @FXML
    private Button button6;
    @FXML
    private Button button7;
    @FXML
    private Button button8;
    @FXML
    private Button button9;
    @FXML
    private Button button10;
    @FXML
    private Button bName1;
    @FXML
    private Button bName2;
    @FXML
    private Button bName3;
    @FXML
    private Button bName4;
    @FXML
    private Button bName5;
    @FXML
    private Button bName6;
    @FXML
    private Button bName7;
    @FXML
    private Button next;
    @FXML
    private Button back;
    
    private class ShowerPoint {
		public double x;
		public double y;
		public ShowerLogEntry entry;
		
		public ShowerPoint(double x, double y, ShowerLogEntry entry) {
			this.x = x;
			this.y = y;
			this.entry = entry;
		}
	}
	
	private GraphicsContext gc;
	
	// duration of current shower 
	private int showerDurationSeconds = 1;
	private boolean timerRunning = false;
	private Timer showerTimer = new Timer();
	private final int POINT_RADIUS = 8;
	private ShowerPoint selectedPoint;
	
	// singleton
	private static Controller instance;
	
	private ArrayList<ShowerPoint> showerPoints = new ArrayList<>();
	
	@FXML
	private Button viewHistoryButton;
	
	@FXML 
	private Text timerText;
	
	@FXML
	private Pane showerHistoryPane;
	
	@FXML
	private Pane mainMenuPane;
	
	@FXML 
	private Button backButton;
	
	@FXML
	private Canvas canvas;
	
	@FXML
	private TextArea showerDataView;
	
	@FXML
	private Button startStopBtn;
    
   //---------------------TIMER---------------------------------------
    
    @FXML
    private TextField hoursField;
    @FXML
    private TextField minutesField;
    @FXML
    private TextField secondsField;
    @FXML
    private Button stopButton;
    @FXML
    private Button playButton;
    
    private int secondsElapsed = 0;
    
    /*
     hoursField.setText("0");
     minutesField.setText("0");
     secondsField.setText("0");
     */
  
    
    //----------------------SEARCH EFFORT----------------------------
    @FXML
    private Label searchStartLabel;
    @FXML
    private Label searchEndTime;
    @FXML
    private Label searchDuration;
    @FXML
    private Label searchLifeCycleStep;
    @FXML
    private Label searchProjectType;
    @FXML
    private Label searchEffortCategory;
    @FXML
    private Label searchDeliveryType;
    @FXML
    private ComboBox<String> searchProjectTypeComboBox;
    @FXML
    private ComboBox<String> searchEffortCatComboBox;
    @FXML
    private ComboBox<String> searchLifeCycleComboBox;
    @FXML
    private ComboBox<String> searchDeliveryTypeComboBox;
    @FXML
    private ListView<String> effortList;
    @FXML
    private Tab searchEffortTabButton;
    
    

	
	private boolean effortInProgress = false;

	
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private String newDefect;
	
	private TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
        	if (timerRunning) {
        		updateTimerTextInSeconds(showerDurationSeconds++);
        	}
        }
    };
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if (instance == null) {
    		instance = this;
    	} else
    		return;
    	
    	showerTimer.schedule(timerTask, 0, 1000);
    	
    	showerHistoryPane.setVisible(false);
    	mainMenuPane.setVisible(true);
    	gc = canvas.getGraphicsContext2D();
    	drawGraph();
		
		
	    projectComboBox.setItems(FXCollections.observableArrayList("Development Project", "Business Project"));
	    lifeCycleComboBox.setItems(FXCollections.observableArrayList(
	    		"Problem Understanding",
	    		"Conceptual Design Plan",
	    		"Requirements",
	    		"Concetpual Design",
	    		"Conceptual Design Review",
	    		"Detailed Design Plan",
	    		"Detailed Design/Prototype",
	    		"Detailed Design Review",
	    		"Implementation Plan",
	    		"Test Case Generation",
	    		"Solution Specification",
	    		"Solution Review",
	    		"Solution Implementation",
	    		"Unit/System Test",
	    		"Reflection",
	    		"Repository Update"));
	    effortCatComboBox.setItems(FXCollections.observableArrayList("Plans", "Deliverables", "Interruptions", "Defects", "Others"));
	    deliverableComboBox.setItems(FXCollections.observableArrayList(
	    		"Conceptual Design",
	    		"Detailed Design",
	    		"Test Cases",
	    		"Solution",
	    		"Reflection",
	    		"Outline",
	    		"Draft Report",
	    		"User Defined",
	    		"Other"));
	    dropDown_Defects.setItems(FXCollections.observableArrayList("Development Project", "Business Project"));
	    lifeCycleComboBox2.setItems(FXCollections.observableArrayList(
	    		"Problem Understanding",
	    		"Conceptual Design Plan",
	    		"Requirements",
	    		"Concetpual Design",
	    		"Conceptual Design Review",
	    		"Detailed Design Plan",
	    		"Detailed Design/Prototype",
	    		"Detailed Design Review",
	    		"Implementation Plan",
	    		"Test Case Generation",
	    		"Solution Specification",
	    		"Solution Review",
	    		"Solution Implementation",
	    		"Unit/System Test",
	    		"Reflection",
	    		"Repository Update"));
	    effortCatComboBox2.setItems(FXCollections.observableArrayList("Plans", "Deliverables", "Interruptions", "Defects", "Others"));
	    stepsInjected.setItems(FXCollections.observableArrayList(
	    		"Problem Understanding", 
	    		"Conceptual Design Plan", 
	    		"Requirements", 
	    		"Conceptual Design",
	    		"Conceptual Design Review",
	    		"Conceptual Design Plan",
	    		"Conceptual Design/Prototype",
	    		"Detailed Design Review",
	    		"Implementation Plan",
	    		"Test Case Generation",
	    		"Solution Specification",
	    		"Solution Review",
	    		"Solution Implementation"));
	    stepsRemoved.setItems(FXCollections.observableArrayList(
	    		"Problem Understanding", 
	    		"Conceptual Design Plan", 
	    		"Requirements", 
	    		"Conceptual Design",
	    		"Conceptual Design Review",
	    		"Conceptual Design Plan",
	    		"Conceptual Design/Prototype",
	    		"Detailed Design Review",
	    		"Implementation Plan",
	    		"Test Case Generation",
	    		"Solution Specification",
	    		"Solution Review",
	    		"Solution Implementation"));
	    defectCat.setItems(FXCollections.observableArrayList(
	    		"Not Specified",
	    		"10 Documentation",
	    		"20 Syntax",
	    		"30 Build, Package",
	    		"40 Assignment",
	    		"50 something idk",
	    		"60 Checking",
	    		"70 Data",
	    		"80 Function",
	    		"90 System",
	    		"100 Environment"));
	    loggedInView.setVisible(false);
	    loginView.setVisible(true);
	    createAccountView.setVisible(false);
	    pokerSecondView.setVisible(false);
	    pokerFirstView.setVisible(true);
	    pokerViewPane.setVisible(false);
	    back.setVisible(false);
	    
	    stopButton.setVisible(false);
	    
	    //-----------SEARCH----------
	    searchProjectTypeComboBox.setItems(FXCollections.observableArrayList("Development Project", "Business Project"));
	    searchLifeCycleComboBox.setItems(FXCollections.observableArrayList(
	    		"Problem Understanding",
	    		"Conceptual Design Plan",
	    		"Requirements",
	    		"Concetpual Design",
	    		"Conceptual Design Review",
	    		"Detailed Design Plan",
	    		"Detailed Design/Prototype",
	    		"Detailed Design Review",
	    		"Implementation Plan",
	    		"Test Case Generation",
	    		"Solution Specification",
	    		"Solution Review",
	    		"Solution Implementation",
	    		"Unit/System Test",
	    		"Reflection",
	    		"Repository Update"));
	    searchEffortCatComboBox.setItems(FXCollections.observableArrayList("Plans", "Deliverables", "Interruptions", "Defects", "Others"));
	    searchDeliveryTypeComboBox.setItems(FXCollections.observableArrayList(
	    		"Conceptual Design",
	    		"Detailed Design",
	    		"Test Cases",
	    		"Solution",
	    		"Reflection",
	    		"Outline",
	    		"Draft Report",
	    		"User Defined",
	    		"Other"));
	    
	}
	
	@FXML
    private void onBackButtonClicked(MouseEvent event) {
    	showerHistoryPane.setVisible(false);
    	mainMenuPane.setVisible(true);
    }
    
    @FXML
    private void onViewHistoryButtonClicked(MouseEvent event) {
    	showerHistoryPane.setVisible(true);
    	mainMenuPane.setVisible(false);
    	selectedPoint = null;
    	drawGraph();
    }
	
	
	@FXML
	private void onStartStopShowerButtonClicked(MouseEvent event) throws InterruptedException {
		String currentText = startStopBtn.getText();
		startStopBtn.setText(currentText.equals("Start") ? "Stop" : "Start");
		
		
		EffortLogger instance = EffortLogger.getInstance();
		LocalDateTime start = instance.getStartTime();
		if (start == null) {
			instance.startShower();
			timerRunning = true;
		} else {
			instance.endShower();
			timerRunning = false;
			showerDurationSeconds = 1;
			updateTimerTextInSeconds(0);
		}
	}
	
	private void updateTimerTextInSeconds(int s) {
		int hours = s / 3600;
		int minutes = (s % 3600) / 60;
		int seconds = s % 60;

		String timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        
		timerText.setText(timeString);
	}
	
	@FXML
	private void onCanvasClick(MouseEvent e) {
		double x = e.getX();
		double y = e.getY();
		for (int i = 0; i < showerPoints.size(); i++) {
			ShowerPoint p = showerPoints.get(i);
			double dist = distanceBetween(x, y, p.x, p.y);
			if (dist < POINT_RADIUS) {
				selectPoint(p);
			}
		}
	}
	
	private void selectPoint(ShowerPoint p) {
		selectedPoint = p;
		
		// update textbox
		showerDataView.setText("");
		
		LocalDateTime start = p.entry.getStartTime();
		String dayOfWeek = start.getDayOfWeek().toString();
		String month = start.getMonth().toString();
		String dayOfMonth = ""+start.getDayOfMonth();
		String timeOfDay = String.format("%d:%d %s", start.getHour() >= 12 ? start.getHour() - 12 : start.getHour(), start.getMinute(), start.getHour() >= 12 ? "PM" : "AM");
		showerDataView.appendText(String.format("* Start date: %s, %s %s, %s; %s\n", dayOfWeek, month, dayOfMonth, start.getYear(), timeOfDay));
		showerDataView.appendText("* Duration: "+p.entry.getDuration()+"\n");
		showerDataView.appendText(String.format("* Gallons used: %.2f\n", p.entry.getGallonsUsed()));
		
		drawGraph();
	}
	
	private double distanceBetween(double x1, double y1, double x2, double y2) {
		return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
	}
	
	
	
	private void drawGraph() {
		// draw background
		gc.setFill(Color.WHITE);
		gc.setStroke(Color.BLACK);
		gc.setLineWidth(1);
		double width = canvas.getWidth();
		double height = canvas.getHeight();
		gc.fillRect(0, 0, width, height);
		float graphMargin = 20;
		
		// draw x axis
		gc.strokeLine(graphMargin, height - graphMargin, width - graphMargin, height - graphMargin);
		
		// y axis
		gc.strokeLine(graphMargin, graphMargin, graphMargin, height - graphMargin);
		
		
		ArrayList<ShowerLogEntry> showers = EffortLogger.getInstance().getShowers();
		
		// find max shower duration
		float max = -1;
		for (ShowerLogEntry s : showers) {
			float dur = s.getGallonsUsed();
			if (dur > max) {
				max = dur;
			}
		}
		float xAvg = 0;
		float yAvg = 0;
		gc.setFill(Color.RED);
		double xAxisWidth = width - 2 * graphMargin;
		double yAxisHeight = height - 2 * graphMargin;
		showerPoints.clear();
		int showerCount = showers.size();
		ArrayList<Double> xPoints = new ArrayList<>();
		ArrayList<Double> yPoints = new ArrayList<>();
		for (int i = 0; i < showers.size(); i++) {
			ShowerLogEntry shower = showers.get(i);
			double pointX = ((float)i / (float)showerCount) * xAxisWidth + graphMargin;
			double pointY = (height - graphMargin) - shower.getGallonsUsed() / max * yAxisHeight;
			
			xPoints.add(pointX);
			yPoints.add(pointY);
			
			xAvg += pointX;
			yAvg += pointY;
			
			showerPoints.add(new ShowerPoint(pointX, pointY, shower));
			gc.beginPath();
			gc.fillArc(pointX, pointY, POINT_RADIUS, POINT_RADIUS, 0, 360, ArcType.ROUND);
			gc.closePath();
		}
		
		xAvg /= showerCount;
		yAvg /= showerCount;
		
		float numeratorSum = 0;
		float denominatorSum = 0;
		
		// calculate slope of line
		for (int i = 0; i < showerCount; i++) {
			numeratorSum += (xPoints.get(i) - xAvg)*(yPoints.get(i) - yAvg);
			denominatorSum += (xPoints.get(i) - xAvg) * (xPoints.get(i) - xAvg);
		}
		
		float m = numeratorSum / denominatorSum;
		
		// calculate intercept
		float b = yAvg - m * xAvg;
		
		gc.strokeLine(0, b, width, m * width + b);
		
		if (selectedPoint != null) {
			gc.setStroke(Color.BLUE);
			gc.setLineWidth(2);
			gc.beginPath();
			gc.strokeArc(selectedPoint.x, selectedPoint.y, POINT_RADIUS, POINT_RADIUS, 0, 360, ArcType.OPEN);
			gc.stroke();
		}
	}
	
	public static Controller getInstance() {
		return instance;
	}
	
	public void destroyThread() {
		showerTimer.cancel();
	}
	
	@FXML
	private void populateSearchEffortList() {
		if (loggedInView.getSelectionModel().getSelectedItem().getText().equals("Search Effort") && !currentTab.equals("Search Effort")) {
			currentTab = loggedInView.getSelectionModel().getSelectedItem().getText();
		} else {
			return;
		}
			
		
		effortList.getItems().clear();
		ArrayList<Effort> efforts = EffortLogger.getInstance().getEffortDataHandler().getUserEffortArray();
		for (Effort e : efforts) {
			effortList.getItems().add(e.getStartTime().toString());
		}
	}
	
	
	@FXML
	void startEffort(Event e) {
		if(!effortInProgress) {
			effortInProgress = true;
			startTime = LocalDateTime.now();
			successLabel.setText("Effort Started at " + startTime);
			timeline.setCycleCount(Timeline.INDEFINITE);
			playButton.setVisible(false);
			stopButton.setVisible(true);
			secondsElapsed = 0;
			timeline.play();
		}
		else
			errorLabel.setText("ERROR: Effort Already Started");
	}
	
	@FXML
	void endEffort(Event e) {
		boolean cleanInput = sanitizeCreateEffortData();
		if(effortInProgress && cleanInput) {
			effortInProgress = false;
			endTime = LocalDateTime.now();
			errorLabel.setText("");
			timeline.stop();
			hoursField.setText("0");
		     minutesField.setText("0");
		     secondsField.setText("0");
		     playButton.setVisible(true);
		     stopButton.setVisible(false);
			successLabel.setText("Effort ended at " + endTime);
			//CREATE THE OBJECT
			String loggedUser = EffortLogger.getInstance().getLogin().getLoginSession().getHashedUser(); //identifier of effort creator
			System.out.print(loggedUser);
			Effort newEffort = new Effort(loggedUser, startTime, endTime, lifeCycleComboBox.getValue(), 
										  projectComboBox.getValue(), effortCatComboBox.getValue(), 
										  deliverableComboBox.getValue());
			
			// add the new effort to the updated effort list
			EffortLogger.getInstance().getEffortDataHandler().addToUpdatedEfforts(newEffort);
			EffortLogger.getInstance().getEffortDataHandler().addToUserEfforts(newEffort);
			
		    ArrayList<Effort> userEffort = EffortLogger.getInstance().getEffortDataHandler().getUserEffortArray();
		    ArrayList<String> displayData = new ArrayList<String>();
		    for(Effort i: userEffort) {
		    	displayData.add(i.getStartTime().toString());
		    }
		    
		    editEffortComboBox.setItems(FXCollections.observableArrayList(displayData));
		}
		else {
			if(cleanInput) {
				successLabel.setText("");
				errorLabel.setText("ERROR: No effort started");
			}
		}
	}
	
	@FXML
	private void attemptLogin() {
		String username = usernameField2.getText();
		String password = passwordField2.getText();
		Login login = EffortLogger.getInstance().getLogin();
		boolean success = login.handleLoginAttempt(username, password);
		int loginAttempts = EffortLogger.getInstance().getLogin().getAttempts();
		if (success) {
			successfulLogin();
			loginView.setVisible(false);
			loggedInView.setVisible(true);
		} else if (loginAttempts < Login.MAX_ATTEMPTS) {
			loginMessage1.setText("Login attempt failed. "+(Login.MAX_ATTEMPTS - loginAttempts)+" left.");
		} else {
			loginMessage1.setText("Run out of attempts. Please try again later.");
		}
	}
	
	int topicIndex = 0;
	int testSubmit = 0;
	int allDone = 0;
	
	@FXML
	private void attemptJoin() {
		String ID = activeSessions.getValue();
		String name = IDField2.getText();
		String password = passwordSessionField2.getText();
		PlanningPoker planningPoker = new PlanningPoker();
		boolean success = planningPoker.handleJoinAttempt(ID, password);
		if (success) {
			//DO THINGS FOR PLANNING POKER
			planningPoker.getInfo(ID);
			planningPoker.addMember(name);
			pokerFirstView.setVisible(false);
			pokerViewPane.setVisible(true);
			String topics = planningPoker.getTopics();
			String[] topicsArr = topics.split(",\\s*");
			String memberList = planningPoker.getMembers();
			String[] memberArr = memberList.split(",\\s*");
			ArrayList<String> newArray = new ArrayList<>();
			topicIndex = 0;
			testSubmit = 0;
			allDone = 0;
			back.setVisible(false);
			next.setVisible(true);

			for (String originalString : memberArr) {
	            // Use a regular expression to match and remove all trailing ":0" sequences
	            String modifiedString = originalString.replaceAll(":[0-9]+", "");

	            if (!modifiedString.isEmpty()) {
	                newArray.add(modifiedString);
	            }
	        }
			
			name1left.setText("....");
	    	name1.setText("...");
	    	name2left.setText("....");
	    	name2.setText("...");
	    	name3left.setText("....");
	    	name3.setText("...");
	    	name4left.setText("....");
	    	name4.setText("...");
	    	name5left.setText("....");
	    	name5.setText("...");
	    	name6left.setText("....");
	    	name6.setText("...");
	    	name7left.setText("....");
	    	name7.setText("...");
			
			for (int i = 0; i < memberArr.length && i < 7; i++) {
			   
			    if (i == 0) {
			    	name1left.setText(newArray.get(i));
			    	name1.setText(newArray.get(i));
			    }
			    if (i== 1) {
			    	name2left.setText(newArray.get(i));
			    	name2.setText(newArray.get(i));
			    }
			    if (i== 2) {
			    	name3left.setText(newArray.get(i));
			    	name3.setText(newArray.get(i));
			    }
			    if (i== 3) {
			    	name4left.setText(newArray.get(i));
			    	name4.setText(newArray.get(i));
			    }
			    if (i== 4) {
			    	name5left.setText(newArray.get(i));
			    	name5.setText(newArray.get(i));
			    }
			    if (i== 5) {
			    	name6left.setText(newArray.get(i));
			    	name6.setText(newArray.get(i));
			    }
			    if (i== 6) {
			    	name7left.setText(newArray.get(i));
			    	name7.setText(newArray.get(i));
			    }
			}
			
			currentTopicText.setText(topicsArr[0]);
			bName1.setText("?");
			bName2.setText("?");
			bName3.setText("?");
			bName4.setText("?");
			bName5.setText("?");
			bName6.setText("?");
			bName7.setText("?");
			yourVoteText.setText("#");
			sessionAvgText.setText("?");
			
		} else {
			createMessage2.setText("Invalid Combination");
			
		}
	}
	
	
	@FXML
	private void nextButton() {
		
		if(allDone == 1) {
			testSubmit = 1;
		}
		
		if(testSubmit == 0) {
			return;
		}
		
		
		String ID = activeSessions.getValue();
		PlanningPoker planningPoker = new PlanningPoker();
		planningPoker.getInfo(ID);
		String topics = planningPoker.getTopics();
		String members = planningPoker.getMembers();
		String[] topicsArr = topics.split(",\\s*");
		String[] memberArr = members.split(",\\s*");
		currentTopicText.setText(topicsArr[topicIndex]);
		planningPoker.getInfo(ID);
		members = planningPoker.getMembers();
		memberArr = members.split(",\\s*");
		
		if (topicsArr.length - 2 == topicIndex) {
			next.setVisible(false);
			
		}
		
		topicIndex++;
		
		if(topicIndex == topicsArr.length-1) {
			allDone = 1;
		}
		
		if (topicIndex >= 0 && allDone == 1) {
			back.setVisible(true);
		}
		else {
			back.setVisible(false);
		}
		
		currentTopicText.setText(topicsArr[topicIndex]);
		
		if(allDone == 0) {
		
		
		bName1.setText("?");
		bName2.setText("?");
		bName3.setText("?");
		bName4.setText("?");
		bName5.setText("?");
		bName6.setText("?");
		bName7.setText("?");
		yourVoteText.setText("?");
		sessionAvgText.setText("?");
		testSubmit = 0;
		
		}
		
		if(allDone == 1) {
			planningPoker.getInfo(ID);
			members = planningPoker.getMembers();
			memberArr = members.split(",\\s*");
			
			String[] newArray = new String[memberArr.length];
			
			for (int i = 0; i < memberArr.length; i++) {
				String[] tempArr = memberArr[i].split(":");
				newArray[i] = tempArr[topicIndex+1];
			}
			
			int avg = 0;
			int toDivide = 0;
			for (int i = 0; i < memberArr.length && i < 7; i++) {
				   
			    if (i == 0 && newArray[i] != "0") {
			    	bName1.setText(newArray[i]);
			    	int tempNum = Integer.parseInt(newArray[i]);
			    	avg += tempNum;
			    	toDivide++;
			    }
			    if (i== 1 && newArray[i] != "0") {
			    	bName2.setText(newArray[i]);
			    	int tempNum = Integer.parseInt(newArray[i]);
			    	avg += tempNum;
			    	toDivide++;
			    }
			    if (i== 2 && newArray[i] != "0") {
			    	bName3.setText(newArray[i]);
			    	int tempNum = Integer.parseInt(newArray[i]);
			    	avg += tempNum;
			    	toDivide++;
			    }
			    if (i== 3 && newArray[i] != "0") {
			    	bName4.setText(newArray[i]);
			    	int tempNum = Integer.parseInt(newArray[i]);
			    	avg += tempNum;
			    	toDivide++;
			    }
			    if (i== 4 && newArray[i] != "0") {
			    	bName5.setText(newArray[i]);
			    	int tempNum = Integer.parseInt(newArray[i]);
			    	avg += tempNum;
			    	toDivide++;
			    }
			    if (i== 5 && newArray[i] != "0") {
			    	bName6.setText(newArray[i]);
			    	int tempNum = Integer.parseInt(newArray[i]);
			    	avg += tempNum;
			    	toDivide++;
			    }
			    if (i== 6 && newArray[i] != "0") {
			    	bName7.setText(newArray[i]);
			    	int tempNum = Integer.parseInt(newArray[i]);
			    	avg += tempNum;
			    	toDivide++;
			    }
			}
			
			avg = avg / toDivide;
			sessionAvgText.setText(String.valueOf(avg));
			yourVoteText.setText("#");
		
		}
		
	}
	
	@FXML private void backButton() {
		next.setVisible(true);
		topicIndex--;
		if(topicIndex == 0) {
			next.setVisible(true);
			back.setVisible(false);
		}
		String ID = activeSessions.getValue();
		PlanningPoker planningPoker = new PlanningPoker();
		planningPoker.getInfo(ID);
		String topics = planningPoker.getTopics();
		String members = planningPoker.getMembers();
		String[] topicsArr = topics.split(",\\s*");
		String[] memberArr = members.split(",\\s*");
		currentTopicText.setText(topicsArr[topicIndex]);
		planningPoker.getInfo(ID);
		members = planningPoker.getMembers();
		memberArr = members.split(",\\s*");
		
		String[] newArray = new String[memberArr.length];
		
		for (int i = 0; i < memberArr.length; i++) {
			String[] tempArr = memberArr[i].split(":");
			newArray[i] = tempArr[topicIndex+1];
		}
		
		int avg = 0;
		int toDivide = 0;
		for (int i = 0; i < memberArr.length && i < 7; i++) {
		    if (i == 0 && newArray[i] != "0") {
		    	bName1.setText(newArray[i]);
		    	int tempNum = Integer.parseInt(newArray[i]);
		    	avg += tempNum;
		    	toDivide++;
		    }
		    if (i== 1 && newArray[i] != "0") {
		    	bName2.setText(newArray[i]);
		    	int tempNum = Integer.parseInt(newArray[i]);
		    	avg += tempNum;
		    	toDivide++;
		    }
		    if (i== 2 && newArray[i] != "0") {
		    	bName3.setText(newArray[i]);
		    	int tempNum = Integer.parseInt(newArray[i]);
		    	avg += tempNum;
		    	toDivide++;
		    }
		    if (i== 3 && newArray[i] != "0") {
		    	bName4.setText(newArray[i]);
		    	int tempNum = Integer.parseInt(newArray[i]);
		    	avg += tempNum;
		    	toDivide++;
		    }
		    if (i== 4 && newArray[i] != "0") {
		    	bName5.setText(newArray[i]);
		    	int tempNum = Integer.parseInt(newArray[i]);
		    	avg += tempNum;
		    	toDivide++;
		    }
		    if (i== 5 && newArray[i] != "0") {
		    	bName6.setText(newArray[i]);
		    	int tempNum = Integer.parseInt(newArray[i]);
		    	avg += tempNum;
		    	toDivide++;
		    }
		    if (i== 6 && newArray[i] != "0") {
		    	bName7.setText(newArray[i]);
		    	int tempNum = Integer.parseInt(newArray[i]);
		    	avg += tempNum;
		    	toDivide++;
		    }
		}
		
		avg = avg / toDivide;
		sessionAvgText.setText(String.valueOf(avg));
		yourVoteText.setText("#");
	
	}
	
	
	
	@FXML
	private void createSession() {
		String ID = IDField.getText();
		String password = passwordSessionField.getText();
		String topics = topicsField.getText();
		PlanningPoker planningPoker = new PlanningPoker();
		String msg = planningPoker.attemptCreateSession(ID, password, topics, "temp");
		if (msg == "Session created") {
		activeSessions.getItems().add(ID);
		createMessage.setText(msg);
		}
	}
	
	
	@FXML
	private void submit() {
		testSubmit = 1;
		String ID = activeSessions.getValue();
		PlanningPoker planningPoker = new PlanningPoker();
		PokerDataHandler dh = new PokerDataHandler();
		planningPoker.getInfo(ID);
		String topics = planningPoker.getTopics();
		String members = planningPoker.getMembers();
		String[] topicsArr = topics.split(",\\s*");
		String[] memberArr = members.split(",\\s*");
		String currTopic = currentTopicText.getText();
		String lastElement = "";
		
		
		if (memberArr.length > 0) {
		    lastElement = memberArr[memberArr.length - 1];
		}
		
		String[] nameInfo = lastElement.split(":");	
		nameInfo[topicIndex+1] = yourVoteText.getText();
		if(yourVoteText.getText() == "#") {
			return;
		}
		
		
		
		String newToSet = String.join(":", nameInfo);
		
		//function to store new name info in system.
		dh.alterNameStorage(ID, nameInfo[0], newToSet);
		
		//get all information again
		planningPoker.getInfo(ID);
		members = planningPoker.getMembers();
		memberArr = members.split(",\\s*");
		
		String[] newArray = new String[memberArr.length];
		
		for (int i = 0; i < memberArr.length; i++) {
			String[] tempArr = memberArr[i].split(":");
			newArray[i] = tempArr[topicIndex+1];
		}
		
		int avg = 0;
		int toDivide = 0;
		
		for (int i = 0; i < memberArr.length && i < 7; i++) {
			   
		    if (i == 0 && newArray[i] != "0") {
		    	bName1.setText(newArray[i]);
		    	int tempNum = Integer.parseInt(newArray[i]);
		    	avg += tempNum;
		    	toDivide++;
		    }
		    if (i== 1 && newArray[i] != "0") {
		    	bName2.setText(newArray[i]);
		    	int tempNum = Integer.parseInt(newArray[i]);
		    	avg += tempNum;
		    	toDivide++;
		    }
		    if (i== 2 && newArray[i] != "0") {
		    	bName3.setText(newArray[i]);
		    	int tempNum = Integer.parseInt(newArray[i]);
		    	avg += tempNum;
		    	toDivide++;
		    }
		    if (i== 3 && newArray[i] != "0") {
		    	bName4.setText(newArray[i]);
		    	int tempNum = Integer.parseInt(newArray[i]);
		    	avg += tempNum;
		    	toDivide++;
		    }
		    if (i== 4 && newArray[i] != "0") {
		    	bName5.setText(newArray[i]);
		    	int tempNum = Integer.parseInt(newArray[i]);
		    	avg += tempNum;
		    	toDivide++;
		    }
		    if (i== 5 && newArray[i] != "0") {
		    	bName6.setText(newArray[i]);
		    	int tempNum = Integer.parseInt(newArray[i]);
		    	avg += tempNum;
		    	toDivide++;
		    }
		    if (i== 6 && newArray[i] != "0") {
		    	bName7.setText(newArray[i]);
		    	int tempNum = Integer.parseInt(newArray[i]);
		    	avg += tempNum;
		    	toDivide++;
		    }
		}
		
		avg = avg / toDivide;
		sessionAvgText.setText(String.valueOf(avg));
	
	}
	
	
	
	@FXML
	private void changeToCreateView() {
		
			loginView.setVisible(false);
			createAccountView.setVisible(true);
		
	}
	
	@FXML
	private void changeToLoginView() {
		
		loginView.setVisible(true);
		createAccountView.setVisible(false);
	
    }
	
	@FXML
	private void switchBackToLoginView() {
		
		pokerFirstView.setVisible(true);
		pokerViewPane.setVisible(false);
		topicIndex = 0;
	
    }
	
	
	@FXML
	private void changePokerCreate() {
		
			pokerFirstView.setVisible(false);
			pokerSecondView.setVisible(true);
		
	}
	
	@FXML
	private void changePokerJoin() {
		
		pokerFirstView.setVisible(true);
		pokerSecondView.setVisible(false);
	
    }
	
	
	@FXML
	private void handleCard1() {
		yourVoteText.setText("0");
	}
	@FXML
	private void handleCard2() {
		yourVoteText.setText("1");
	}
	@FXML
	private void handleCard3() {
		yourVoteText.setText("2");
	}
	@FXML
	private void handleCard4() {
		yourVoteText.setText("3");
	}
	@FXML
	private void handleCard5() {
		yourVoteText.setText("5");
	}
	@FXML
	private void handleCard6() {
		yourVoteText.setText("8");
	}
	@FXML
	private void handleCard7() {
		yourVoteText.setText("13");
	}
	@FXML
	private void handleCard8() {
		yourVoteText.setText("20");
	}
	@FXML
	private void handleCard9() {
		yourVoteText.setText("40");
	}
	@FXML
	private void handleCard10() {
		yourVoteText.setText("100");
	}
	
	
	@FXML
	private void createLogin() {
		String username = usernameField.getText();
		String password = passwordField.getText();
		Login login = EffortLogger.getInstance().getLogin();
		String msg = login.attemptCreateAccount(username, password);
		loginMessage.setText(msg);
	}
	
	@FXML
	void editEffort(Event e) {
		if(sanitizeEditEffort()) {	//sanitize user input
			editSuccessLabel.setText("Effort successfully editted");
			editErrorLabel.setText("");
			String startTime = editEffortComboBox.getValue();				//find the effort object by its start time
			LocalDateTime oldStartTime = LocalDateTime.parse(startTime);
			Effort oldEffort = EffortLogger.getInstance().getEffortDataHandler().getEffort(oldStartTime);
			//construct new start time and end time
			String updatedStartTime = editDate.getText() + "T" + editStartTime.getText();
			String updatedEndTime = editDate.getText() + "T" + editEndTime.getText();
			LocalDateTime newStartTime = LocalDateTime.parse(updatedStartTime);
			LocalDateTime newEndTime = LocalDateTime.parse(updatedEndTime);
			//grab comboBox updated info
			String updatedLifeCycleStep = lifeCycleComboBox2.getValue();
			String updatedEffortCat = effortCatComboBox2.getValue();
			
			Effort editedEffort = new Effort(oldEffort.getUserID(), 		//create editted effort object, send to file directory
											  newStartTime, 
											  newEndTime, 
											  updatedLifeCycleStep,
											  oldEffort.getProjectType(),
											  updatedEffortCat,
											  oldEffort.getDeliverableType());
			//edit effort in file directory
			EffortLogger.getInstance().getEffortDataHandler().updateEffort(oldEffort, editedEffort);


			//repopulate combobox with updated information
			ArrayList<Effort> userEffort = EffortLogger.getInstance().getEffortDataHandler().getUserEffortArray();
		    ArrayList<String> displayData = new ArrayList<String>();
		    for(Effort i: userEffort) {
		    	displayData.add(i.getStartTime().toString());
		    }
		    editEffortComboBox.setItems(FXCollections.observableArrayList(displayData));

			
			
		}
	}
	
	private boolean sanitizeCreateEffortData() {
		if(projectComboBox.getValue() == null || 				//empty combobox checks
		   effortCatComboBox.getValue() == null ||
		   lifeCycleComboBox.getValue() == null || 
		   deliverableComboBox.getValue() == null) 
		{
			errorLabel.setText("ERROR: One of the Fields is left blank");
			return false;
		}
		
		return true;
	}
	
	
	boolean sanitizeEditEffort() {
		if(effortCatComboBox2.getValue() == null ||		//empty combobox checks
		   lifeCycleComboBox2.getValue() == null ||
		   editEffortComboBox.getValue() == null ||
		   editDate.getText() == null ||
		   editStartTime.getText() == null ||
		   editEndTime.getText() == null)   
		{
			editSuccessLabel.setText("");
			editErrorLabel.setText("ERROR: One of the fields is left blank");
			return false;
		}
		if(!sanitizeUserInput()) {
			editSuccessLabel.setText("");
			editErrorLabel.setText("ERROR: Invalid Input");
			return false;
		}
		return true;
	}
	
	boolean sanitizeUserInput() {
		final int MAX_DATE_LENGTH = 10;
		final int MAX_TIME_LENGTH = 8;
		String dateValue = editDate.getText();
		String startValue = editStartTime.getText();
		String endValue = editEndTime.getText();
		
		if(dateValue.length() > MAX_DATE_LENGTH ||
		   startValue.length() > MAX_TIME_LENGTH ||
		   endValue.length() > MAX_TIME_LENGTH) 
		{
			return false;
		}
		
		//pattern matching the date and times to make sure it is correct format
		String datePatternRegex = "\\d{4}-\\d{2}-\\d{2}";
		String timePatternRegex = "\\d{2}:\\d{2}:\\d{2}";
		Pattern datePattern = Pattern.compile(datePatternRegex);
		Pattern timePattern = Pattern.compile(timePatternRegex);
		Matcher matcher = datePattern.matcher(dateValue);
		if(!matcher.matches()) {			//authenticate data
			return false;
		}
		matcher = timePattern.matcher(startValue); 
		if(!matcher.matches())
			return false;
		
		matcher = timePattern.matcher(endValue);
		if(!matcher.matches())
			return false;
		
		return true;
	}
	
	@FXML
	private void populateDefects() {
		String project = dropDown_Defects.getSelectionModel().getSelectedItem();
		ArrayList<Defect> defects = EffortLogger.getInstance().getEffortDataHandler().getDefectArray();
		System.out.println("defects: " + defects.size());
		ArrayList<String> defectNamesInProject = new ArrayList<>();
		for (Defect d : defects) {
			if (d.getProject().equals(project)) {
				defectNamesInProject.add(d.getDefectString());
			}
		}
		
		System.out.println("POpulating with " + defectNamesInProject.size());
		
		selectDefectCombo.setItems(FXCollections.observableArrayList(defectNamesInProject));
	}
	
	private void successfulLogin() {
		ArrayList<Effort> userEffort = EffortLogger.getInstance().getEffortDataHandler().getUserEffortArray();
	    ArrayList<String> displayData = new ArrayList<String>();
	    for(Effort i: userEffort) {
	    	displayData.add(i.getStartTime().toString());
	    }
	    editEffortComboBox.setItems(FXCollections.observableArrayList(displayData));
	    List<String> loggerSessions = EffortLogger.getInstance().getPokerDataHandler().returnSessions();
	    activeSessions.setItems(FXCollections.observableList(loggerSessions));
	}
	
	@FXML
	void createDefect(Event e) {
		String userName = EffortLogger.getInstance().getLogin().getLoginSession().getHashedUser();
        Defect def = new Defect(dropDown_Defects.getValue(), "-new defect-", defectInfo.getText(), "Open", " ", " ", " ", userName);
        EffortLogger.getInstance().getEffortDataHandler().createDefect(def);
        ArrayList<Defect> defectArr = EffortLogger.getInstance().getEffortDataHandler().getDefectArray();
        ArrayList<String> defectStrings = new ArrayList<String>();
        for (Defect d : defectArr) {
            defectStrings.add(d.getDefectString());
        }
        selectDefectCombo.setItems(FXCollections.observableArrayList(defectStrings));
        defectStatus.setText(def.getDefectStatus());
		
	}
		
	
	@FXML
	private void updateDefect(Event e) {
		if (!sanitizeUpdateDefect() || defectEntry.getText() == null) {
			return;
		}
		
		String injected;
		String removed;
		String category;
		String status;
		String userName = EffortLogger.getInstance().getLogin().getLoginSession().getHashedUser();
		Defect oldDefect = null;
		if (FileDirectory.fileExistsInDirectoryPath(EffortLogger.getInstance().getDataPathDirectory(), selectDefectCombo.getValue())) {
			 oldDefect = EffortLogger.getInstance().getEffortDataHandler().getDefect(selectDefectCombo.getValue());
		}
		
		if (stepsInjected.getSelectionModel().getSelectedItem() == null) {
			injected = "";
		}
		else {
			injected = stepsInjected.getSelectionModel().getSelectedItem();
		}
		if (stepsRemoved.getSelectionModel().getSelectedItem() == null) {
			removed = "";
		}
		else {
			removed = stepsRemoved.getSelectionModel().getSelectedItem();
		}
		if (defectCat.getSelectionModel().getSelectedItem() == null) {
			category = "";
		}
		else {
			category = defectCat.getSelectionModel().getSelectedItem();
		}
		
		Defect newDef;
		if (oldDefect == null) {
			newDef = new Defect(dropDown_Defects.getValue(), defectEntry.getText(), defectInfo.getText(), "Open", injected, removed, category, userName);
			EffortLogger.getInstance().getEffortDataHandler().createDefect(newDef);
		} else {
			newDef = new Defect(dropDown_Defects.getValue(), defectEntry.getText(), defectInfo.getText(), oldDefect.getDefectStatus(), injected, removed, category, userName);
			EffortLogger.getInstance().getEffortDataHandler().updateDefect(oldDefect, newDef);
		}
		
		
		
		ArrayList<Defect> defects = EffortLogger.getInstance().getEffortDataHandler().getDefectArray();
		ArrayList<String> defectStrings = new ArrayList<>();
		for (Defect d : defects) {
			defectStrings.add(d.getDefectString());
		}
		selectDefectCombo.setItems(FXCollections.observableArrayList(defectStrings));
		EffortLogger.getInstance().getEffortDataHandler().removeDefect(selectDefectCombo.getSelectionModel().getSelectedItem());
		saveStatus.setText("");
		saveStatus.setText("Changes Saved");
	}
	
	@FXML
	private void displayMessage(Event e) {
		saveStatus.setText("");
		saveStatus.setText("Changes Unsaved");
	}
	
	boolean sanitizeUpdateDefect() {
		if (defectEntry.getText().isEmpty() && defectInfo.getText().isEmpty() && stepsInjected.getSelectionModel().getSelectedItem() == null && stepsRemoved.getSelectionModel().getSelectedItem() == null && defectCat.getSelectionModel().getSelectedItem() == null) {
			saveStatus.setText("");
			saveStatus.setText("Fields empty");
			return false;
		}
		return true;
	}
	
	@FXML
	private void clearDefectLogHandler() {
		EffortLogger.getInstance().getEffortDataHandler().clearDefectLog(dropDown_Defects.getSelectionModel().getSelectedItem());
	}
	
	@FXML
	private void getSearchEffortData(Event e) {
		
		if (effortList.getSelectionModel().getSelectedItem() == null)
			return;
		
		LocalDateTime selectedEffortIdentifier = LocalDateTime.parse(effortList.getSelectionModel().getSelectedItem());
		//call data handler to find effort data
		Effort selectedEffort = EffortLogger.getInstance().getEffortDataHandler().getEffort(selectedEffortIdentifier);
		
		//populate labels 
		searchStartLabel.setText(selectedEffort.getStartTime().toString());
		searchEndTime.setText(selectedEffort.getEndTime().toString());
		searchDuration.setText("" + selectedEffort.getDuration());
		searchLifeCycleStep.setText(selectedEffort.getLifeCycleStep());
		searchProjectType.setText(selectedEffort.getProjectType());
		searchEffortCategory.setText(selectedEffort.getEffortCategory());
		searchDeliveryType.setText(selectedEffort.getDeliverableType());
	}
	
	@FXML
	private void deleteDefectButtonHandler() {
		String defectName = selectDefectCombo.getSelectionModel().getSelectedItem();
		EffortLogger.getInstance().getEffortDataHandler().removeDefect(defectName);
		populateDefects();
	}
	
	@FXML
	void clearFilters(Event e) {
		searchProjectTypeComboBox.getSelectionModel().clearSelection();
	    searchEffortCatComboBox.getSelectionModel().clearSelection();
	    searchLifeCycleComboBox.getSelectionModel().clearSelection();
	    searchDeliveryTypeComboBox.getSelectionModel().clearSelection();
	    populateSearchEffortList();
	}
	@FXML
	void filterEffort() {
		ArrayList<Effort> efforts = EffortLogger.getInstance().getEffortDataHandler().getUserEffortArray();
		
		String projectType = searchProjectTypeComboBox.getSelectionModel().getSelectedItem();
		String effortCategory = searchEffortCatComboBox.getSelectionModel().getSelectedItem();
		String lifeCycle = searchLifeCycleComboBox.getSelectionModel().getSelectedItem();
		String deliverable = searchDeliveryTypeComboBox.getSelectionModel().getSelectedItem();
		
		effortList.getItems().clear();
		for (Effort e : efforts) {
			if ((e.getProjectType().equals(projectType) || projectType == null) && 
					(e.getEffortCategory().equals(effortCategory) || effortCategory == null) &&
					(e.getLifeCycleStep().equals(lifeCycle) || lifeCycle == null) &&
					(e.getDeliverableType().equals(deliverable) || deliverable == null)) {
				
				effortList.getItems().add(e.getStartTime().toString());
				
			}
		}
		
		
		
	}
	
	@FXML
	void reopenDefect(Event e) {
		String currDefect = selectDefectCombo.getSelectionModel().getSelectedItem();
		Defect defectToChange = EffortLogger.getInstance().getEffortDataHandler().getDefect(currDefect);
		defectToChange.setDefectStatus("Open");
		System.out.println(defectToChange.getDefectStatus());
		defectStatus.setText(defectToChange.getDefectStatus());
	}
	
	@FXML
	void closeDefect(Event e) {
		ArrayList<Defect> defectArr = EffortLogger.getInstance().getEffortDataHandler().getDefectArray();
		System.out.print(defectArr.size());
		String currDefect = selectDefectCombo.getSelectionModel().getSelectedItem();
		Defect defectToChange = EffortLogger.getInstance().getEffortDataHandler().getDefect(currDefect);
		defectToChange.setDefectStatus("Closed");
		System.out.println(defectToChange.getDefectStatus());
		defectStatus.setText("");
		defectStatus.setText("Closed");
	}
	
	
	@FXML
	Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            secondsElapsed++;
            updateFields();
        }
    }));
	
	
	private void updateFields() {
        Duration duration = Duration.seconds(secondsElapsed);

        long hours = (long) duration.toHours();
        long minutes = (long) (duration.toMinutes() % 60);
        long seconds = (long) (duration.toSeconds() % 60);

        hoursField.setText(String.valueOf(hours));
        minutesField.setText(String.valueOf(minutes));
        secondsField.setText(String.valueOf(seconds));
    }	
}