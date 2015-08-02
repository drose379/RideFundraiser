package drose379.ridefundraiser.eventoverviewtabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import drose379.ridefundraiser.CompleteMileEvent;
import drose379.ridefundraiser.R;


/**
 * Created by Dylan on 7/26/15.
 */
public class ViewPagerAdapterOverview extends FragmentPagerAdapter {

    private CompleteMileEvent eventData;

    public String[] titles = new String[] {"Event","Donation"};
    public int[] icons = new int[] {R.drawable.ic_directions_run_white_24dp,R.drawable.ic_attach_money_white_24dp};

    public ViewPagerAdapterOverview(FragmentManager manager,CompleteMileEvent eventData) {
        super(manager);
        this.eventData = eventData;
    }

    @Override
    public Fragment getItem(int item) {

        Bundle data = new Bundle();
        data.putParcelable("eventData",eventData);

        switch (item) {
            case 0 :
                EventOverviewFragment eventOverview = new EventOverviewFragment();
                eventOverview.setArguments(data);
                return eventOverview;
            case 1 :
                DonationOverviewFragment donationOverview = new DonationOverviewFragment();
                donationOverview.setArguments(data);
                return donationOverview;
            default :
                throw new RuntimeException();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    public String getTabTitle(int position) {return titles[position];}


    public int getDrawableId(int position) {return icons[position];}


}
