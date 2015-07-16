package drose379.ridefundraiser.homeTabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import drose379.ridefundraiser.R;

/**
 * Created by drose379 on 6/25/15.
 */
public class LiveTab extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstance) {
        super.onCreateView(inflater, container, savedInstance);
        View v = inflater.inflate(R.layout.organizations,container,false);
        return v;
    }
}
