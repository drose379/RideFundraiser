package drose379.ridefundraiser;

import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.CameraUpdateFactory;


/**
 * Created by dylanrose60 on 7/12/15.
 */
public class LiveMileEvent extends AppCompatActivity implements
        OnMapReadyCallback,
		View.OnClickListener,
        GPSHelper.LocationCallback,
        TimeKeeper.TimerCallback {

	/**
	  * Create timerHelepr to keep track of timing event
	  * Avg speed and percent way through event will be calculated in GPSHelper class
	  */

	private LiveMileEventHelper eventHelper;
	private GPSHelper gpsHelper;
	private GoogleMap liveMap;

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

        gpsHelper = GPSHelper.getInstance(this,eventHelper);
        eventHelper = getIntent().getBundleExtra("extra").getParcelable("helperInstance");

		SupportMapFragment liveMapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.liveMap);
        liveMapFrag.getMapAsync(this);

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


	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.singleStartButton :
				/**
				  * Switch from START button to PAUSE button, detect clicks for pause in this method also 
				  */
				gpsHelper.startEvent();
				break;
		}
	}

	@Override
	public void onMapReady(GoogleMap map) {
		/**
		  * Set flag in GPSHelper saying the map is ready, if map is not ready in GPSHelper, it will not start event
		  * Get a lastKnownLocation from the GPSHelper to set the map location starting point with map.addMarker(LatLong)
		  */

		liveMap = map;
		gpsHelper.setMapReady(true);
		LatLng lastLoc = new LatLng(gpsHelper.getLastLocation().getLatitude(),gpsHelper.getLastLocation().getLongitude());

		map.addMarker(new MarkerOptions().position(lastLoc));
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(lastLoc,16));
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
	public void liveMapUpdate(List<LatLng> polyPoints) {
		
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
    public void timerUpdate(final String time) {
    	timeMeasure.post(new Runnable() {
            @Override
            public void run() {
                timeMeasure.setText(String.valueOf(time));
            }
        });
    }

}
