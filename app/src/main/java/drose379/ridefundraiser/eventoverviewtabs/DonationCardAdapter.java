package drose379.ridefundraiser.eventoverviewtabs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import drose379.ridefundraiser.Donation;
import drose379.ridefundraiser.R;

/**
 * Created by Dylan on 7/30/15.
 */

public class DonationCardAdapter extends BaseAdapter {

    private Context context;
    private List<Donation> donations;

    LayoutInflater inflater;

    public DonationCardAdapter(Context context,List<Donation> donations) {
        this.context = context;
        this.donations = donations;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

        Donation currentDonation = donations.get(position);

        if (v == null) {
            v = inflater.inflate(R.layout.donation_card,parent,false);

            TextView user = (TextView) v.findViewById(R.id.nameText);
            TextView messageText = (TextView) v.findViewById(R.id.messageText);
            TextView amountText = (TextView) v.findViewById(R.id.amountText);

            user.setText(currentDonation.getUser());
            if (currentDonation.getMessage() != null) {messageText.setText(currentDonation.getMessage());}
            amountText.setText(currentDonation.getAmount())
        }

        return v;
    }

}