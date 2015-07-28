package drose379.ridefundraiser.eventoverviewtabs;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import drose379.ridefundraiser.R;

/**
 * Created by Dylan on 7/28/15.
 */
public class EventOverviewFragment extends Fragment {

    private Context context;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.context = activity;

        //must grab bitmap of live event (as bytearray)
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup parent,Bundle savedInstance) {
        super.onCreateView(inflater,parent,savedInstance);
        View v = inflater.inflate(R.layout.event_overview_frag,parent,false);

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

}
