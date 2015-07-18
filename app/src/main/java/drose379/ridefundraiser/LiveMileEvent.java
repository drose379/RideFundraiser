package drose379.ridefundraiser;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


/**
 * Created by dylanrose60 on 7/12/15.
 */
public class LiveMileEvent extends AppCompatActivity implements View.OnClickListener,GPSHelper.LocationCallback,TimeKeeper.TimerCallback {

	/**
	  * Create timerHelepr to keep track of timing event
	  * Avg speed and percent way through event will be calculated in GPSHelper class
	  */

	private LiveMileEventHelper eventHelper;
	private GPSHelper gpsHelper;

	TextView distanceMeasure;
	TextView timeMeasure;
	TextView averageSpeedMeasure;
	TextView goalReachedMeasure;

	Button singleStart;

	boolean isRunning = false;

	@Override
	public void onCreate(Bundle savedInstance) {
		super.onCreate(savedInstance);
        setContentView(R.layout.live_mile_event);

        singleStart = (Button) findViewById(R.id.singleStartButton);
        distanceMeasure = (TextView) findViewById(R.id.distanceText);
        timeMeasure = (TextView) findViewById(R.id.timeText);
        averageSpeedMeasure = (TextView) findViewById(R.id.avgSpeed);
        goalReachedMeasure = (TextView) findViewById(R.id.percentReached);

        singleStart.setTypeface(TypeHelper.getTypefaceBold(this));
        distanceMeasure.setTypeface(TypeHelper.getTypeface(this));
        timeMeasure.setTypeface(TypeHelper.getTypeface(this));
        averageSpeedMeasure.setTypeface(TypeHelper.getTypeface(this));
        goalReachedMeasure.setTypeface(TypeHelper.getTypeface(this));

        singleStart.setOnClickListener(this); 

		eventHelper = getIntent().getBundleExtra("extra").getParcelable("helperInstance");
		//EventHelper needs getter methods in order to gain access to goal distance value from this activity (used for goal percent)
		gpsHelper = GPSHelper.getInstance(this,eventHelper);

	}

	@Override
	public void updateStatus(boolean isRunning) {
		this.isRunning = isRunning;
	}

	@Override
	public void distanceUpdate(String distance) {
		distanceMeasure.setText(distance + " Miles");
	}

	@Override
	public void averageSpeedUpdate(String speed) {
		averageSpeedMeasure.setText(speed + " M/S");
	}

    @Override
    public void goalReachedUpdate(String percentReached) {
    	goalReachedMeasure.setText(percentReached + "%");
    }

    @Override
    public void timerUpdate(final int time) {
    	timeMeasure.post(new Runnable() {
            @Override
            public void run() {
                timeMeasure.setText(String.valueOf(time));
            }
        });
    }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.singleStartButton :
				/**
				  * Switch from START button to PAUSE button, detect clicks in this method also 
				  */
				gpsHelper.startLocationUpdates();
				break;
		}
	}



}
