package drose379.ridefundraiser;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;

/**
 * Created by drose379 on 7/3/15.
 */
public class GSignInController implements GoogleApiClient.OnConnectionFailedListener,GoogleApiClient.ConnectionCallbacks {
    private Context context;
    private GoogleApiClient gClient;

    private boolean shouldInflateAccountPicker = false;

    public GSignInController(Context context) {
        this.context = context;
        gClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .addScope(Plus.SCOPE_PLUS_PROFILE)
                .build();

        gClient.connect();
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        if (Plus.PeopleApi.getCurrentPerson(gClient) != null) {
            //send callback to main activity to skip log in screen, pass persons username (or display name)
        } else {
            throw new RuntimeException("Could not log you in.");
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if (shouldInflateAccountPicker) {
            //call method in MainActivity (UI Thread) to inflate the resulution,
        } else {
            //set should inflate flag to true, for next time the user will be clicking the log in button and it should inflate the account picker
            shouldInflateAccountPicker = true;
        }
    }

    @Override
    public void onConnectionSuspended(int error) {

    }

    public void attemptSignIn() {
        gClient.connect();
    }


}
