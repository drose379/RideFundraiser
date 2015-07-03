package drose379.ridefundraiser;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.gms.common.ConnectionResult;

import com.google.android.gms.common.api.GoogleApiClient;

import com.google.android.gms.plus.model.people.Person;


public class MainActivity extends AppCompatActivity implements View.OnClickListener,GSignInController.UICallback {

    private GoogleApiClient gApiClient;
    private GSignInController gController;

    private boolean shouldInflateAccountPicker = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        gController = new GSignInController(this);

        findViewById(R.id.googleSignIn).setOnClickListener(this);

    }

    @Override
    public void signInSuccess(Person currentUser) {
        //skip the log-in screen
        Log.i("gConnect", "SUCCESS " + currentUser.getDisplayName());
        Intent openHome = new Intent(this,HomeTabRoot.class);
        openHome.putExtra("displayName",currentUser.getDisplayName());
        startActivity(openHome);
        finish();
    }

    @Override
    public void showLoginScreen() {
        Log.i("gConnect","Show login screen");
        RelativeLayout loginRoot = (RelativeLayout) findViewById(R.id.loginContainer);
        LinearLayout loadRoot = (LinearLayout) findViewById(R.id.loadRoot);
        loadRoot.setVisibility(View.GONE
        );
        loginRoot.setVisibility(View.VISIBLE);
    }

    @Override
    public void inflateResolution(ConnectionResult result) {
        Log.i("gConnect","Inflate Resolution");
        try {
            result.startResolutionForResult(this,1);
        } catch (IntentSender.SendIntentException e) {

        }
    }


    @Override
    public void onActivityResult(int request,int result,Intent data) {
        super.onActivityResult(request, result, data);
        if (request == 1) {
            gController.attemptSignIn();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.googleSignIn) {
            gController.attemptSignIn();
        }
    }


    @Override
    public void onStop() {
        super.onStop();
        gController.disconnect();
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
