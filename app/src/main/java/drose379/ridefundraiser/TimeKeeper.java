package drose379.ridefundraiser;

import android.content.Context;

import java.util.Timer;
import java.util.TimerTask;


public class TimeKeeper {

	public interface TimerCallback {
		public void timerUpdate(int time);
	}

	//timeAtPause
	TimerCallback callback;
	static TimeKeeper timeKeeper;

	Timer timer = new Timer();

	int totalSeconds  = 0;

	public static TimeKeeper getInstance(Context context) {
		timeKeeper = timeKeeper == null ? new TimeKeeper(context) : timeKeeper;
		return timeKeeper;
	}

	public TimeKeeper(Context context) {
		callback = (TimerCallback) context;
	}

	public void startClock() {
        
		timer.schedule(new TimerTask() {
            @Override
            public void run() {
            	totalSeconds++;
            	callback.timerUpdate(totalSeconds);
            }
        }, 1000L,1000L);
	}

	public void stopClock() {

	}

}