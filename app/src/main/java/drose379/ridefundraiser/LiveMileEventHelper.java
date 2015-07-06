package drose379.ridefundraiser;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by drose379 on 7/4/15.
 */
public class LiveMileEventHelper {

    private boolean requestSuccess = false;

    private OkHttpClient httpClient = new OkHttpClient();

    private String user;
    private String eventName;
    private String organization;
    private String perMile;
    private String goalDistance;

    public LiveMileEventHelper(String user, String eventName, String organization, String perMile, String goalDistance) {
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
                .url()
                .build();
        Call call = httpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Response response) throws IOException {
                didSucceed.setSavedItem(true);
            }
            @Override
            public void onFailure(Request request,IOException e) {

            }
        });
        return didSucceed.getSavedItem();
    }
}
