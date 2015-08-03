package drose379.ridefundraiser.eventoverviewtabs;

import android.app.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;

import drose379.ridefundraiser.R;

/**
 * Created by dylan on 8/2/15.
 */
public class MapOverviewFragment extends Fragment {

    /**
     * Must grab eventBus with Polyline object inside of it
     * Use polyline and add it to our livemap (must add GoogleMap fragment to xml map_oveview_frag)
     */

    private GoogleMap overviewMap;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup parent,Bundle savedInstance) {
        super.onCreateView(inflater, parent, savedInstance);

        View v = inflater.inflate(R.layout.map_overview_frag,parent,false);
        
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
