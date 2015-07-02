package drose379.ridefundraiser;

import android.content.Intent;
import android.content.IntentSender;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.ErrorDialogFragment;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.google.android.gms.plus.model.people.PersonBuffer;

import drose379.ridefundraiser.homeTabs.SlidingTabLayout;
import drose379.ridefundraiser.homeTabs.ViewPagerAdapter;


public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener {

    private GoogleApiClient gApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        gApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .addScope(Plus.SCOPE_PLUS_PROFILE)
                .build();


        findViewById(R.id.googleSignIn).setOnClickListener(this);

    }

    @Override
    public void onStart() {
        super.onStart();
        gApiClient.connect();

    }
    @Override
    public void onStop() {
        super.onStop();
        gApiClient.disconnect();
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        if (Plus.PeopleApi.getCurrentPerson(gApiClient) == null) {
            Log.i("connection","PERSON NULL");
        } else {
            Log.i("connection",Plus.PeopleApi.getCurrentPerson(gApiClient).getDisplayName());
        }
    }


    @Override
    public void onConnectionSuspended(int cause) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i("connection",String.valueOf(result.getErrorCode()));
        if (result.hasResolution()) {
            try {
                result.startResolutionForResult(this,1);
            } catch (IntentSender.SendIntentException e) {
                throw new RuntimeException(e);
            }
        } else {
            Log.i("connection","NO RESOLUTION");
            ErrorDialogFragment errorFrag = new ErrorDialogFragment();
            Bundle arguments = new Bundle();
            arguments.putInt("ERROR",result.getErrorCode());
            errorFrag.setArguments(arguments);
            errorFrag.show(getFragmentManager(),"Error");
        }
    }

    @Override
    public void onActivityResult(int request,int result,Intent data) {
        super.onActivityResult(request,result,data);
        if (request == 1) {
            gApiClient.connect();
        }
    }


    @Override
    public void onClick(View v) {
        //check which button is clicked
        if (v.getId() == R.id.googleSignIn) {
            //gApiClient.connect();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
