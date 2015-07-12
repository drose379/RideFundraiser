package drose379.ridefundraiser;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;

import java.io.IOException;
import java.util.Arrays;

/**
 * Created by drose379 on 7/4/15.
 */
public class LiveMileEventHelper implements Parcelable {
    private Context context;

    private OkHttpClient httpClient = new OkHttpClient();

    private String user;
    private String eventName;
    private String organization;
    private String perMile;
    private String goalDistance;

    public static final Parcelable.Creator<LiveMileEventHelper> CREATOR = new Parcelable.Creator<LiveMileEventHelper>() {
        public LiveMileEventHelper createFromParcel(Parcel in) {
            return new LiveMileEventHelper(in);
        }

        public LiveMileEventHelper[] newArray(int size) {
            return new LiveMileEventHelper[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    public LiveMileEventHelper(Context context,String user, String eventName, String organization, String perMile, String goalDistance) {
        this.context = context;

        this.user = user;
        this.eventName = eventName;
        this.organization = organization;
        this.perMile = perMile;
        this.goalDistance = goalDistance;
    }

    /**
     * Use the parcel passed in to grab data from parcel
     * @param in to pull data form
     */
    public LiveMileEventHelper(Parcel in) {
        String[] eventData = in.createStringArray();
        user = eventData[0];
        eventName = eventData[1];
        organization = eventData[2];
        perMile = eventData[3];
        goalDistance = eventData[4];
    }

    /**
     * Used to write existing data to parcel for use later
     * @param dest
     * @param flags
     */
    @Override
    public void writeToParcel(Parcel dest,int flags) {
        dest.writeStringArray(new String[]{user,eventName,organization,perMile,goalDistance});
    }


    /**
     * Called to create a new live mile event
     * @return Request success boolean
     */
    public boolean createLiveEvent() {
        final BooleanWrapper didSucceed = new BooleanWrapper(false);

        JSONArray values = new JSONArray(Arrays.asList(user,eventName,organization,perMile,goalDistance));

        RequestBody rBody = RequestBody.create(MediaType.parse("text/plain"),values.toString());
        final Request request = new Request.Builder()
                .post(rBody)
                .url("http://104.236.15.47/RideFundraiserAPI/index.php/newLiveEvent")
                .build();
        Call call = httpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Response response) throws IOException {
                if (response.code() == 200) {
                    didSucceed.setSavedItem(true);
                }
            }
            @Override
            public void onFailure(Request request,IOException e) {

            }
        });
        return didSucceed.getSavedItem();
    }

    /**
     * Called when live mile event is complete, used to move record from Live to Completed table in DB
     * @return Transfer from live to completed event boolean
     */
    public boolean liveMileEventFinished() {
        final BooleanWrapper didSucceed = new BooleanWrapper(false);

        //make transfer from live to complete

        return didSucceed.getSavedItem();
    }
}
