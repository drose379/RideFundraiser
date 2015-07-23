package drose379.ridefundraiser;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;


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
	private TimeKeeper timeKeeper;
	private GoogleMap liveMap;
	private Polyline polyline;

	TextView distanceMeasure;
	TextView timeMeasure;
	TextView averageSpeedMeasure;
	TextView goalReachedMeasure;
	LinearLayout dualButtonLayout;

	Button singleStart;
	Button singlePause;
	Button resume;
	Button finish;

    /**
     * Need flags to tell which buttons are showing at the time.
     * If the exit dialog resume is clicked, and finish and resume buttons are showing, must stay paused until resume button is clicked
     * @param savedInstance
     */

	@Override
	public void onCreate(Bundle savedInstance) {
		super.onCreate(savedInstance);
        setContentView(R.layout.live_mile_event);

        gpsHelper = GPSHelper.getInstance(this,eventHelper);
        timeKeeper = TimeKeeper.getInstance(this);
        eventHelper = getIntent().getBundleExtra("extra").getParcelable("helperInstance");

		SupportMapFragment liveMapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.liveMap);
        liveMapFrag.getMapAsync(this);

        distanceMeasure = (TextView) findViewById(R.id.distanceText);
        timeMeasure = (TextView) findViewById(R.id.timeText);
        averageSpeedMeasure = (TextView) findViewById(R.id.avgSpeed);
        goalReachedMeasure = (TextView) findViewById(R.id.percentReached);

        singleStart = (Button) findViewById(R.id.singleStartButton);
        singlePause = (Button) findViewById(R.id.singlePauseButton);
        resume = (Button) findViewById(R.id.resume);
        finish = (Button) findViewById(R.id.finish);
        dualButtonLayout = (LinearLayout) findViewById(R.id.resumeFinishContainer);

        singleStart.setTypeface(TypeHelper.getTypefaceBold(this));
        singlePause.setTypeface(TypeHelper.getTypefaceBold(this));
		resume.setTypeface(TypeHelper.getTypefaceBold(this));
		finish.setTypeface(TypeHelper.getTypefaceBold(this));
        distanceMeasure.setTypeface(TypeHelper.getTypeface(this));
        timeMeasure.setTypeface(TypeHelper.getTypeface(this));
        averageSpeedMeasure.setTypeface(TypeHelper.getTypeface(this));
        goalReachedMeasure.setTypeface(TypeHelper.getTypeface(this));

        singleStart.setOnClickListener(this); 
        singlePause.setOnClickListener(this);
		resume.setOnClickListener(this);
		finish.setOnClickListener(this);

	}

	@Override
	public void onBackPressed() {
        pauseEvent();

        MaterialDialog mDialog = new MaterialDialog.Builder(this)
                .title("Exit Live Event?")
                .content("Would you really like to end this live event now?")
                .positiveText("Exit")
                .negativeText("Resume")
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        gpsHelper.finish();
                        timeKeeper.finish();
                        LiveMileEvent.this.finish();
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        resumeEvent();
                        dialog.dismiss();
                    }
                })
                .build();
       mDialog.show();
	}

	@Override
	public void onPause() {
		super.onPause();

		/**
          * Instead of stopping gps helper, keep it running in background and make event resumable "Running events?"
		  */
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.singleStartButton :

	            hideShow(singleStart,singlePause);

				gpsHelper.startEvent();
				timeKeeper.startClock();
				break;
			case R.id.singlePauseButton :

				hideShow(singlePause,dualButtonLayout);

                    pauseEvent();
				break;
            case R.id.resume :
                resumeEvent();

                hideShow(dualButtonLayout,singlePause);

                break;
            case R.id.finish :
                pauseEvent();
				//Finish, grab all current data, show a results screen, once user confirms the results, send to server, show home tabs
		}


	}

    public void hideShow(View hide,View show) {
        hide.setVisibility(View.GONE);
        show.setVisibility(View.VISIBLE);
    }

    public void pauseEvent() {
        gpsHelper.updateStatus(false);
        timeKeeper.updateStatus(false);
    }
    public void resumeEvent() {
        gpsHelper.updateStatus(true);
        timeKeeper.updateStatus(true);
    }

	@Override
	public void onMapReady(GoogleMap map) {
		/**
		  * Set flag in GPSHelper saying the map is ready, if map is not ready in GPSHelper, it will not start event
		  * Get a lastKnownLocation from the GPSHelper to set the map location starting point with map.addMarker(LatLong)
		  */

		liveMap = map;
		gpsHelper.setMapReady(true);
		if (gpsHelper.getLastLocation() != null) {
			LatLng lastLoc = new LatLng(gpsHelper.getLastLocation().getLatitude(),gpsHelper.getLastLocation().getLongitude());
			polyline = liveMap.addPolyline(new PolylineOptions().add(lastLoc).color(Color.RED).width(5).visible(true));

			map.addMarker(new MarkerOptions().position(lastLoc).title("Starting Point"));
			map.moveCamera(CameraUpdateFactory.newLatLngZoom(lastLoc,16));
		}

	}


	@Override
	public void distanceUpdate(String distance) {
		distanceMeasure.setText(distance + " Miles");
	}

	@Override
	public void liveMapUpdate(List<LatLng> polyPoints) {
		/**
		  * Call setPoints on saved polyline
		  * CHECK IF POLYLINE IS NULL, IF IT IS, CREATE A NEW ONE WITH GOOGLEMAP.ADDPOLYLINE
		  */
		if (polyline == null) {
			polyline = liveMap.addPolyline(new PolylineOptions().color(Color.RED).width(5).visible(true));
			polyline.setPoints(polyPoints);
			liveMap.moveCamera(CameraUpdateFactory.newLatLng(polyPoints.get(polyPoints.size() - 1)));
		} else {
			polyline.setPoints(polyPoints);
       		liveMap.moveCamera(CameraUpdateFactory.newLatLng(polyPoints.get(polyPoints.size() - 1)));
		}

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
