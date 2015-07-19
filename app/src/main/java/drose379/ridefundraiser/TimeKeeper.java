package drose379.ridefundraiser;

import android.content.Context;

import java.util.Timer;
import java.util.TimerTask;



public class TimeKeeper {

	public interface TimerCallback {
		public void timerUpdate(String time);
	}

	//timeAtPause
	TimerCallback callback;
	private static TimeKeeper timeKeeper;

	Timer timer = new Timer();

	int totalSeconds = 0;

	public static TimeKeeper getInstance(Context context) {
		timeKeeper = timeKeeper == null ? new TimeKeeper(context) : timeKeeper;
		return timeKeeper;
	}

	public static void finish() {
		timeKeeper = null;
	}

	public TimeKeeper(Context context) {
		callback = (TimerCallback) context;
	}

	public void startClock() {
        
		timer.schedule(new TimerTask() {
            @Override
            public void run() {
            	totalSeconds++;
            	String secondsFormatted = TimerFormat.toTimerFormat(totalSeconds);
            	callback.timerUpdate(secondsFormatted);
            }
        }, 1000L,1000L);
	}

	public void stopClock() {

	}

}