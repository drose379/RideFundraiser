package drose379.ridefundraiser.homeTabs;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.melnykov.fab.FloatingActionButton;

import drose379.ridefundraiser.NewEvent;
import drose379.ridefundraiser.R;

/**
 * Created by drose379 on 6/25/15.
 */
public class HomeTab extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstance) {
        super.onCreateView(inflater,container,savedInstance);
        View v = inflater.inflate(R.layout.my_activity,container,false);

        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.newActivityFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),NewEvent.class);
                startActivity(i);
            }
        });

        return v;
    }
}
