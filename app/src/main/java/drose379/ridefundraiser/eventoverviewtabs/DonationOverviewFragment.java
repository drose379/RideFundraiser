package drose379.ridefundraiser.eventoverviewtabs;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import drose379.ridefundraiser.R;

/**
 * Created by Dylan on 7/29/15.
 */
public class DonationOverviewFragment extends Fragment {

    private Bundle eventInfo;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        eventInfo = getArguments();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup parent,Bundle savedInstance) {
        super.onCreateView(inflater, parent, savedInstance);

        View v = inflater.inflate(R.layout.donation_overview_frag,parent,false);
        TextView headLabel = (TextView) v.findViewById(R.id.headText);

        headLabel.setText("My Donation To - " + eventInfo.getString("organization"));

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        try {
            populateDonationCards();
        } catch (JSONException e) {
            //No donations submitted from other users, just show one card with original users donations
        }
    }

    public void populateDonationCards() throws JSONException {
        JSONObject donations = new JSONObject(eventInfo.getString("donationSummary"));
    }

}
