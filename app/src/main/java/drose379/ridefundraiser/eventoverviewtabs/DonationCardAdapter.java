package drose379.ridefundraiser.eventoverviewtabs;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import drose379.ridefundraiser.Donation;

/**
 * Created by Dylan on 7/30/15.
 */

public class DonationCardAdapter extends BaseAdapter {

    private Context context;
    private List<Donation> donations;

    public DonationCardAdapter(Context context,List<Donation> donations) {
        this.context = context;
        this.donations = donations;
    }

    @Override
    public int getCount() {
        return donations.size();
    }
    @Override
    public long getItemId(int item) {
        return 0;
    }
    @Override
    public Donation getItem(int position) {
        return donations.get(position);
    }

    @Override
    public View getView(int position,View recycledView,ViewGroup parent) {
        View v = recycledView;

        if (v == null) {
            
        }

        return v;
    }

}
