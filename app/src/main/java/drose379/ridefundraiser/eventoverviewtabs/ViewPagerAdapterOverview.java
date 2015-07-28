package drose379.ridefundraiser.eventoverviewtabs;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import drose379.ridefundraiser.R;


/**
 * Created by Dylan on 7/26/15.
 */
public class ViewPagerAdapterOverview extends FragmentPagerAdapter {

    public String[] titles = new String[] {"Event Summary","Donation Summary"};
    public int[] icons = new int[] {R.drawable.ic_directions_run_white_24dp,R.drawable.ic_attach_money_white_24dp};

    public ViewPagerAdapterOverview(FragmentManager manager) {super(manager);}

    @Override
    public Fragment getItem(int item) {
        switch (item) {
            case 0 :
                return new Fragment();
            //open event details fragment
            case 1 :
                return new Fragment();
            //open donation summary fragment
            default :
                throw new RuntimeException();
        }
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    public String getTabTitle(int position) {return titles[position];}


    public int getDrawableId(int position) {return icons[position];}


}
