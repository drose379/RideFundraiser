package drose379.ridefundraiser;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.os.Bundle;
import android.content.Intent;


/**
 * Created by dylanrose60 on 7/12/15.
 */
public class LiveMileEvent extends AppCompatActivity {

	/**
	  * Need to design the layout for the activity and create GPSHelper accordingly 
	  */

	private LiveMileEventHelper eventHelper;
	private GPSHelper gpsHelper;

	@Override
	public void onCreate(Bundle savedInstance) {
		super.onCreate(savedInstance);
		eventHelper = getIntent().getBundleExtra("extra").getParcelable("helperInstance");
		gpsHelper = GPSHelper.getInstance(getApplicationContext());
	}

}
