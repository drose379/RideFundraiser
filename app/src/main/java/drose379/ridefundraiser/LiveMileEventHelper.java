package drose379.ridefundraiser;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;

/**
 * Created by drose379 on 7/4/15.
 */
public class LiveMileEventHelper implements Parcelable {

    public interface NetworkCallback {
        void onRequestSuccess();
    }
    public interface LiveEventComm {
        void finishedEventDataReady(String donationSummary);
    }

    private Context context;

    private NetworkCallback callback;
    private LiveEventComm liveCallback;

    private OkHttpClient httpClient = new OkHttpClient();

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

    public LiveMileEventHelper (Context context) {
        this.context = context;
        callback = (NetworkCallback) context;
    }

    public void updateContext(Context context) {
        this.context = context;
        liveCallback = (LiveEventComm) context;
    }

    public void setValues(String eventName, String organization, String perMile, String goalDistance) {
        this.eventName = eventName;
        this.organization = organization;
        this.perMile = perMile;
        this.goalDistance = goalDistance;
    }

    public String getGoalDistance() {
        return this.goalDistance;
    }
    public String getEventName() {return this.eventName;}
    public String getPerMile() { return perMile;}

    //Need a getvalues

    /**
     * Use the parcel passed in to grab data from parcel
     * @param in to pull data form
     */
    public LiveMileEventHelper(Parcel in) {
        String[] eventData = in.createStringArray();
        eventName = eventData[0];
        organization = eventData[1];
        perMile = eventData[2];
        goalDistance = eventData[3];
    }

    /**
     * Used to write existing data to parcel for use later
     * @param dest
     * @param flags
     */
    @Override
    public void writeToParcel(Parcel dest,int flags) {
        dest.writeStringArray(new String[]{eventName, organization, perMile, goalDistance});
    }

    public String generateJSONArray(String... value) {
        JSONArray vals = new JSONArray();
        for (String val : value) {
            vals.put(val);
        }
        return vals.toString();
    }


    /**
     * Called to create a new live mile event
     * @return Request success boolean
     */
    public void createLiveEvent() {
        //JSONArray values = new JSONArray(Arrays.asList(user,eventName,organization,perMile,goalDistance));
        String jsonVals = generateJSONArray(CurrentUser.currentUser,eventName,organization,perMile,goalDistance);
        RequestBody rBody = RequestBody.create(MediaType.parse("text/plain"),jsonVals);
        final Request request = new Request.Builder()
                .post(rBody)
                .url("http://104.236.15.47/RideFundraiserAPI/index.php/newLiveEvent")
                .build();
        Call call = httpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Response response) throws IOException {
                if (response.code() == 200) {
                    callback.onRequestSuccess();
                }
            }

            @Override
            public void onFailure(Request request, IOException e) {

            }
        });
    }

    /**
     * Called each time distance is updated in UI, used to keep other users who want to donate to this event updated on progress
     */
    public void updateEventInfo(String distance,String time,String goalReached,String averageSpeed,String baseRaised) {
        String jsonEncodedArray = generateJSONArray(CurrentUser.currentUser,eventName,distance,time,goalReached,averageSpeed,baseRaised);
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"),jsonEncodedArray);
        Request request = new Request.Builder()
                .post(body)
                .url("http://104.236.15.47/RideFundraiserAPI/index.php/updateLiveEvent")
                .build();
        Call c = httpClient.newCall(request);
        c.enqueue(new Callback() {
            @Override
            public void onFailure(Request r,IOException e) {
                //keep count of failures, if more then 10 happen, throw an exception which will be caught with a no internet connection dialog
            }
            @Override
            public void onResponse(Response response) throws IOException {
                //reset count of failed attempts
            }
        });
    }

    public void endLiveEvent() {
        RequestBody rBody = RequestBody.create(MediaType.parse("text/plain"),generateJSONArray(CurrentUser.currentUser,eventName));
        Request request = new Request.Builder()
                .post(rBody)
                .url("http://104.236.15.47/RideFundraiserAPI/index.php/removeLiveEvent")
                .build();
        Call call = httpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                //callback to LiveEvent activity to remove loading dialog and dismiss
            }
        });
    }


    /**
     * Called when live mile event is complete, used to move record from Live to Completed table in DB
     * @return Transfer from live to completed event boolean
     */
    public void liveMileEventFinished() {

        //make transfer from live to complete
        //get response from script of donation summary json, pass with callback
        //liveCallback.finishedEventDataReady(donationSummary)

        //on response of request, pass the json string of donation summary to the callback liveCallback

        RequestBody rBody = RequestBody.create(MediaType.parse("text/plain"),generateJSONArray(CurrentUser.currentUser,eventName));
        Request request = new Request.Builder()
                .post(rBody)
                .url("http://104.236.15.47/RideFundraiserAPI/index.php/liveEventFinished")
                .build();
        Call newCall = httpClient.newCall(request);
        newCall.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                //callback to live event activity with donation summary
                liveCallback.finishedEventDataReady(response.body().string());
            }
        });

    }
}
