package drose379.ridefundraiser;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by dylanrose60 on 7/12/15.
 */
public class LiveMileEvent extends AppCompatActivity {

    public LiveMileEvent() {
        LiveMileEventHelper eventHelper = getIntent().getBundleExtra("extra").getParcelable("helperInstance");

        if (eventHelper == null ) {
            Log.i("eventHelper", "NULL!");
        } else {
            Log.i("eventHelper","NOT NULL!");
        }

    }
}
