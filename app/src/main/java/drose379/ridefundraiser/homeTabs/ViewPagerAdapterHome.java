package drose379.ridefundraiser.homeTabs;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import drose379.ridefundraiser.R;

/**
 * Created by drose379 on 6/13/15.
 */
public class ViewPagerAdapterHome extends FragmentPagerAdapter {

    private int[] tabIcons = {R.drawable.ic_home_white_24dp,R.drawable.ic_public_white_24dp,R.drawable.ic_group_white_24dp};
    private String[] titles = {"My Activity","Live Activity","Organizations"};

    public ViewPagerAdapterHome(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int item) {
        switch(item) {
            case 0 :
                HomeTab homeTab = new HomeTab();
                return homeTab;
            case 1:
                LiveTab liveTab = new LiveTab();
                return liveTab;
            case 2:
                OrganizationsTab organizationsTab = new OrganizationsTab();
                return organizationsTab;
            default:
                throw new RuntimeException();
        }
    }

    @Override
    public int getCount() {
        return tabIcons.length;
    }

    public int getDrawableId(int position) {
        return tabIcons[position];
    }

    public String getTabTitle(int position) {
        return titles[position];
    }

}
