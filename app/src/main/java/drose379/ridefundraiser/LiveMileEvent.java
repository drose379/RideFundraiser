package drose379.ridefundraiser;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
        LiveMileEventHelper.LiveEventComm,
        GPSController.LocationCallback,
        TimeKeeper.TimerCallback {

	/**
	  * Create timerHelepr to keep track of timing event
	  * Avg speed and percent way through event will be calculated in GPSController class
	  */

	private LiveMileEventHelper eventHelper;
	private GPSController gpsController;
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

    MaterialDialog globalLoading;

    /**
     * Need flags to tell which buttons are showing at the time.
     * If the exit dialog resume is clicked, and finish and resume buttons are showing, must stay paused until resume button is clicked
     */

	@Override
	public void onCreate(Bundle savedInstance) {
		super.onCreate(savedInstance);
        setContentView(R.layout.live_mile_event);

        eventHelper = getIntent().getBundleExtra("extra").getParcelable("helperInstance");
        eventHelper.updateContext(this);
        gpsController = GPSController.getInstance(this, eventHelper);
        timeKeeper = TimeKeeper.getInstance(this);

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
                        gpsController.finish();
                        timeKeeper.finish();
                        eventHelper.endLiveEvent();
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

				gpsController.startEvent();
				timeKeeper.startClock();
				break;
			case R.id.singlePauseButton :

				hideShow(singlePause,dualButtonLayout);

                    pauseEvent();
				break;
            case R.id.resume :
                resumeEvent();

                hideShow(dualButtonLayout, singlePause);

                break;
            case R.id.finish :
                pauseEvent();

                globalLoading = new MaterialDialog.Builder(this)
                        .title("Thank You!")
                        .customView(R.layout.load_dialog_layout,true)
                        .autoDismiss(false)
                        .build();
                TextView loadText = (TextView) globalLoading.getCustomView().findViewById(R.id.loadText);
                loadText.setTypeface(TypeHelper.getTypefaceBold(this));
                globalLoading.show();

                eventHelper.liveMileEventFinished();

                /**
                 * LiveMileEventHelper must have method to construct a json array of donation summary
                 * Pass the JSONArray of donation summary to the EventFinished activity in string format
                 * Call MileEventFinished Activity with an intent with extra of bundle
                 */
		}


	}

    @Override
    public void finishedEventDataReady(String donationSummaryJson) {
        //globalLoading.dismiss();

        Bundle completeEventData = new Bundle();
        completeEventData.putString("eventName",eventHelper.getEventName());
        completeEventData.putString("distance",distanceMeasure.getText().toString());
        completeEventData.putString("time",timeMeasure.getText().toString());
        completeEventData.putString("averageSpeed",averageSpeedMeasure.getText().toString());
        completeEventData.putString("percentComplete",goalReachedMeasure.getText().toString());
        //completeEventData.putString("donationSummary",donationSummaryJson);

        //Use intent to open activity
        //Be sure to attach bundle to intent

    }

    public void hideShow(View hide,View show) {
        hide.setVisibility(View.GONE);
        show.setVisibility(View.VISIBLE);
    }

    public void pauseEvent() {
        gpsController.updateStatus(false);
        timeKeeper.updateStatus(false);
    }
    public void resumeEvent() {
        gpsController.updateStatus(true);
        timeKeeper.updateStatus(true);
    }

	@Override
	public void onMapReady(GoogleMap map) {
		/**
		  * Set flag in GPSController saying the map is ready, if map is not ready in GPSController, it will not start event
		  * Get a lastKnownLocation from the GPSController to set the map location starting point with map.addMarker(LatLong)
		  */

		liveMap = map;
		gpsController.setMapReady(true);
		if (gpsController.getLastLocation() != null) {
			LatLng lastLoc = new LatLng(gpsController.getLastLocation().getLatitude(), gpsController.getLastLocation().getLongitude());
			polyline = liveMap.addPolyline(new PolylineOptions().add(lastLoc).color(Color.BLUE).width(8).visible(true));

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
		  */
		if (polyline == null) {
			polyline = liveMap.addPolyline(new PolylineOptions().color(Color.BLUE).width(8).visible(true));
			polyline.setPoints(polyPoints);
			liveMap.moveCamera(CameraUpdateFactory.newLatLngZoom(polyPoints.get(polyPoints.size() - 1), 16));
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

    @Override
    public void updateEventInfo() {
        String distance = distanceMeasure.getText().toString();
        String time = timeMeasure.getText().toString();
        String goalReached = goalReachedMeasure.getText().toString();
        String averageSpeed = averageSpeedMeasure.getText().toString();
        String baseRaised = String.valueOf(Integer.getInteger(distance) * Integer.getInteger(eventHelper.getPerMile()));

        eventHelper.updateEventInfo(distance,time,goalReached,averageSpeed,baseRaised);
    }

}
