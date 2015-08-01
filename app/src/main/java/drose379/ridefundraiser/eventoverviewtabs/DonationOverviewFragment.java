package drose379.ridefundraiser.eventoverviewtabs;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import drose379.ridefundraiser.Donation;
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

        //need to calculate total donation and add to top header
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
            throw new RuntimeException(e.getMessage());
        }
    }

    public void populateDonationCards() throws JSONException {
        JSONArray donations = new JSONArray(eventInfo.getString("donationSummary"));
        List<Donation> donationList = new ArrayList<Donation>();

        for(int i = 0; i < donations.length(); i++) {
            JSONObject inner = donations.getJSONObject(i);
            String user = inner.getString("user");
            String amount = inner.getString("amount");
            String message = inner.getString("message").equals("null") ? null : inner.getString("message");

            Donation currentDonation = new Donation(user,amount);
            if (message != null) {currentDonation.setMessage(message);}

            donationList.add(currentDonation);
        }
        ListView donationCards = (ListView) getView().findViewById(R.id.donationCardContainer);
        DonationCardAdapter adapter = new DonationCardAdapter(getActivity(),donationList);
        donationCards.setAdapter(adapter);
    }

}
