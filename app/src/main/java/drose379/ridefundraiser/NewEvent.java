package drose379.ridefundraiser;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;


/**
 * Created by drose379 on 6/25/15.
 */
@SuppressLint("NewApi")
public class NewEvent extends AppCompatActivity {

    /*
        * initViewController needs to be organized
        *  Make all views that need global access private properties of the class?
     */

    FragmentManager fragManager;


    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.new_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fragManager = getSupportFragmentManager();
        initViewController();

    }

    public void initViewController() {
        fragManager.beginTransaction().add(R.id.fragmentContainer,new NewEventQuestion1(),"Question1").commit();

        /*
            * Get callback from fragment one and make callback to next fragment based upon results
         */
    }

    public void questionOneCallback(String eventName,String measure) {
        switch(measure) {
            case "By The Mile" :
                questionTwoTransaction("MILE",eventName);
                break;
            case "By The Hour" :
                questionTwoTransaction("HOUR",eventName);
                break;
        }
    }

    /**
     * Event type should be either by MILE or by HOUR
     * This method must change the UI based on the event type
     * MUST pass argument to NewEventQuestion2 of event type, so it can device in onCreateView which layout to show.
     */
    public void questionTwoTransaction(String eventType,String eventName) {
        NewEventQuestion2 question2 = new NewEventQuestion2();
        Bundle eventInfo = new Bundle();
        eventInfo.putString("eventType",eventType);
        eventInfo.putString("eventName",eventName);
        question2.setArguments(eventInfo);

        FragmentTransaction trans = fragManager.beginTransaction().
                setCustomAnimations(R.anim.abc_fade_in, R.anim.abc_fade_out).
                replace(R.id.fragmentContainer, question2);
        trans.commit();
    }

    public void questionTwoCallback(String type,String user,String eventName,String donatingTo,float rate,float distance) {
        /*
            * need to save new event to DB
            * Need to create Live Event actiivty where the event is traced live while user is in motion
            * Must update "My Activity" tab with completed events
         */
        switch (type) {
            case "HOUR" :
                saveLiveHourEvent();
                break;
            case "MILE" :
                saveLiveMileEvent(user,eventName,donatingTo,rate,distance);
                break;
        }
    }

    public void saveLiveMileEvent(String user,String eventName,String donatingTo,float rate,float distance) {
        /**
         * Save event to db
         * Open up live event activity
         */
    }

    public void saveLiveHourEvent() {

    }

}
