package HonorsProjectFall2023_ShowerLogger;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;

public class ShowerLogEntry {
	public static final float AVRG_GALS_PER_SECOND = 0.035f;
	
	// duration of shower in seconds
	private int duration;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private float gallonsUsed;
	
	public ShowerLogEntry(LocalDateTime _startTime, LocalDateTime _endTime) {
		startTime = _startTime;
		endTime = _endTime;
		duration = (int) Duration.between(startTime, _endTime).getSeconds();
		gallonsUsed = duration * AVRG_GALS_PER_SECOND;
	}
	
	public int getDuration() {
		return duration;
	}
	
	public void setDuration(int d) {
		duration = d;
	}
	
	public LocalDateTime getStartTime() {
		return startTime;
	}
	
	public void setStartTime(LocalDateTime t) {
		startTime = t;
	}
	
	public LocalDateTime getEndTime() {
		return endTime;
	}
	
	public void setEndTime(LocalDateTime t) {
		endTime = t;
	}
	
	public float getGallonsUsed() {
		return gallonsUsed;
	}
	
	public void setGallonsUsed(float gals) {
		gallonsUsed = gals;
	}
	
	
}
