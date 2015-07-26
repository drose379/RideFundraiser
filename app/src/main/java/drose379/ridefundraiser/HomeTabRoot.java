package drose379.ridefundraiser;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import drose379.ridefundraiser.homeTabs.SlidingTabLayout;
import drose379.ridefundraiser.homeTabs.ViewPagerAdapterHome;

public class HomeTabRoot extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_tab_root);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewPagerAdapterHome pagerAdapter = new ViewPagerAdapterHome(getSupportFragmentManager());
        ViewPager pager = (ViewPager) findViewById(R.id.viewPager);
        pager.setAdapter(pagerAdapter);
        pager.setOffscreenPageLimit(3);

        SlidingTabLayout tabLayout = (SlidingTabLayout) findViewById(R.id.tabs);
        tabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.indicator);
            }

            @Override
            public int getDividerColor(int position) {
                return 0;
            }
        });

        tabLayout.setViewPager(pager);
        //stretch tab layout to fill page

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
