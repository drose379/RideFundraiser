package drose379.ridefundraiser;

public class TimerFormat {

	/**
	  * First block, just seconds
	  * Seconds block, seconds and minutes
	  * Third block, hours, minutes,seconds
	  * NEED TO GET TIME TO 00:00:00 IN STRING. 
	  * ALSO NEED TO GET 05 INSTEAD OF 5
	  */

	private static int SECONDS_IN_MINUTE = 60;
	private static int SECONDS_IN_HOUR = 3600;

	public static String toTimerFormat(int totalSeconds) {
		int hours = 0;
		int mins = 0;
		int seconds = 0;

		String timerFormat = null;

		if (totalSeconds < SECONDS_IN_MINUTE) {
			seconds = totalSeconds;
		} 
		else if (totalSeconds < SECONDS_IN_HOUR) {
			mins = totalSeconds / SECONDS_IN_MINUTE;
			seconds = totalSeconds % SECONDS_IN_MINUTE;
		} 
		else {
			hours = totalSeconds/3600;

			int remainingSeconds = totalSeconds - (hours * SECONDS_IN_HOUR);

			mins = remainingSeconds / SECONDS_IN_MINUTE;
			seconds = remainingSeconds % SECONDS_IN_MINUTE;

		}

		//look into each value, if its less then 10, put a 0 before it, use ternary
		//hours = hours < 10 ? 0 + hours : hours;

		String hour = hours < 10 ? "0" + hours : String.valueOf(hours);
		String min = mins < 10 ? "0" + mins : String.valueOf(mins);
		String sec = seconds < 10 ? "0" + seconds : String.valueOf(seconds);

		timerFormat = hour + ":" + min + ":" + sec;
        return timerFormat;
	}
}