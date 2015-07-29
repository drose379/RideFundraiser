package drose379.ridefundraiser;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import drose379.ridefundraiser.eventoverviewtabs.SlidingTabLayout;
import drose379.ridefundraiser.eventoverviewtabs.ViewPagerAdapterOverview;


/**
 * Created by Dylan on 7/23/15.
 * Show confirmation screen for event.
 * Use json string of donation summary to create a JSONObject to loop over and display donation summary
 * Display bitmap of generated map (decode from ByteAray)
 * Display event statistics (dist, avg.speed,time, etc)
 */

public class MileEventOverview extends AppCompatActivity {

    private Bundle eventData;

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.mile_event_overview);

        eventData = getIntent().getBundleExtra("eventData");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(eventData.getString("eventName") + " Overview");
        setSupportActionBar(toolbar);



        ViewPager pager = (ViewPager) findViewById(R.id.viewPager);
        ViewPagerAdapterOverview pagerAdapter = new ViewPagerAdapterOverview(getSupportFragmentManager(),eventData);
        pager.setAdapter(pagerAdapter);

        SlidingTabLayout tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setViewPager(pager);

    }

}
