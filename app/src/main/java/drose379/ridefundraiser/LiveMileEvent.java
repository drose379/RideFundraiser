package drose379.ridefundraiser;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;


/**
 * Created by dylanrose60 on 7/12/15.
 */
public class LiveMileEvent extends AppCompatActivity {

	/**
	  * Need to design the layout for the activity and create GPSHelper accordingly 
	  */

	private LiveMileEventHelper eventHelper;
	private GPSHelper gpsHelper;

	private TextView distanceMeasure;
	private TextView timeMeasure;
	private TextView averageSpeedMeasure;
	private TextView goalReachedMeasure;

	private Button singleStart;

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

		eventHelper = getIntent().getBundleExtra("extra").getParcelable("helperInstance");
		//EventHelper needs getter methods in order to gain access to goal distance value from this activity (used for goal percent)
		gpsHelper = GPSHelper.getInstance(getApplicationContext());
	}

}
