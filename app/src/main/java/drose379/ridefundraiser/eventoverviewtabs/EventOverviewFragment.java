package drose379.ridefundraiser.eventoverviewtabs;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import drose379.ridefundraiser.CompleteMileEvent;
import drose379.ridefundraiser.R;
import drose379.ridefundraiser.TypeHelper;

/**
 * Created by Dylan on 7/28/15.
 */
public class EventOverviewFragment extends Fragment {

    /**
     * Instead of using blocks just live LiveEvent activity to display event results (time,speed,etc)
     * Show each item in a card, inside of a ListView, show all data that can gather about each item in this card
     * Show how stats compare to users past events
     */

    private Context context;
    private CompleteMileEvent eventArguments;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.context = activity;
        this.eventArguments = getArguments().getParcelable("eventData");

    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup parent,Bundle savedInstance) {
        super.onCreateView(inflater, parent, savedInstance);
        View v = inflater.inflate(R.layout.event_overview_frag,parent,false);


        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

}
