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

	private Button singleStart;

	@Override
	public void onCreate(Bundle savedInstance) {
		super.onCreate(savedInstance);
         setContentView(R.layout.live_mile_event);

         singleStart = (Button) findViewById(R.id.singleStartButton);
         distanceMeasure = (TextView) findViewById(R.id.distanceText);
         timeMeasure = (TextView) findViewById(R.id.timeText);

         singleStart.setTypeface(TypeHelper.getTypefaceBold(this));
         distanceMeasure.setTypeface(TypeHelper.getTypeface(this));
         timeMeasure.setTypeface(TypeHelper.getTypeface(this));

		eventHelper = getIntent().getBundleExtra("extra").getParcelable("helperInstance");
		gpsHelper = GPSHelper.getInstance(getApplicationContext());
	}

}
