package drose379.ridefundraiser.eventoverviewtabs;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import drose379.ridefundraiser.R;
import drose379.ridefundraiser.TypeHelper;

/**
 * Created by Dylan on 7/28/15.
 */
public class EventOverviewFragment extends Fragment {

    private Context context;
    private Bundle eventArguments;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.context = activity;
        this.eventArguments = getArguments();

    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup parent,Bundle savedInstance) {
        super.onCreateView(inflater, parent, savedInstance);
        View v = inflater.inflate(R.layout.event_overview_frag,parent,false);

        ImageView mapImage = (ImageView) v.findViewById(R.id.mapImage);
        TextView distanceText = (TextView) v.findViewById(R.id.distanceText);
        TextView timeText = (TextView) v.findViewById(R.id.timeText);
        TextView avgSpeedText = (TextView) v.findViewById(R.id.avgSpeed);
        TextView percentText = (TextView) v.findViewById(R.id.percentReached);

        byte[] mapBytes = eventArguments.getByteArray("map");
        mapImage.setImageBitmap(BitmapFactory.decodeByteArray(mapBytes,0,mapBytes.length));

        distanceText.setTypeface(TypeHelper.getTypeface(context));
        timeText.setTypeface(TypeHelper.getTypeface(context));
        avgSpeedText.setTypeface(TypeHelper.getTypeface(context));
        percentText.setTypeface(TypeHelper.getTypeface(context));

        distanceText.setText(eventArguments.getString("distance"));
        timeText.setText(eventArguments.getString("time"));
        avgSpeedText.setText(eventArguments.getString("averageSpeed"));
        percentText.setText(eventArguments.getString("percentComplete"));

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

}
