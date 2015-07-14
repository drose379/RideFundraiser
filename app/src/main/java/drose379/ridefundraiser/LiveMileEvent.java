package drose379.ridefundraiser;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.os.Bundle;
import android.content.Intent;


/**
 * Created by dylanrose60 on 7/12/15.
 */
public class LiveMileEvent extends AppCompatActivity {

	private LiveMileEventHelper eventHelper;

	@Override
	public void onCreate(Bundle savedInstance) {
		super.onCreate(savedInstance);
		eventHelper = getIntent().getBundleExtra("extra").getParcelable("helperInstance");
	}
    public LiveMileEvent() {
    	/**
		  * Use GPSHelper class to get callback from distance traveled
		  * Also integrate map functionality with GPSHelper
    	  */
    }
}
