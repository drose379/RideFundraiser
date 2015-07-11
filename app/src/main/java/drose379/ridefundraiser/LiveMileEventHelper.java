package drose379.ridefundraiser;

import android.content.Context;
import android.util.Log;

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
public class LiveMileEventHelper {
    private Context context;

    private OkHttpClient httpClient = new OkHttpClient();

    private String user;
    private String eventName;
    private String organization;
    private String perMile;
    private String goalDistance;

    public LiveMileEventHelper(Context context,String user, String eventName, String organization, String perMile, String goalDistance) {
        this.context = context;

        this.user = user;
        this.eventName = eventName;
        this.organization = organization;
        this.perMile = perMile;
        this.goalDistance = goalDistance;
    }

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
                    Log.i("serverResp",String.valueOf(response.code()));
                    didSucceed.setSavedItem(true);
                }
            }
            @Override
            public void onFailure(Request request,IOException e) {

            }
        });
        return didSucceed.getSavedItem();
    }
}
