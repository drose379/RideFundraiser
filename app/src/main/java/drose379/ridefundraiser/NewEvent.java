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
        Log.i("measure",measure);
        /*
            * Must account for when people type lower case letters, use String.contains() in the default of switch to ensure that no running,walk,or bike slips through
            * Must sanitize all inputs in newX() methods
        */
        switch (eventName) {
            case "Running" :
                newRun(eventName);
                break;
            case "Walking" :
                newWalk(eventName);
                break;

            case "Biking" :
                newBike(eventName);
                break;

            default :
                if (eventName.contains("walking") || eventName.contains("Walking")) {
                    newWalk(eventName);
                } else if (eventName.contains("running") || eventName.contains("Running")) {
                    newRun(eventName);
                } else if (eventName.contains("biking") || eventName.contains("Biking")) {
                    newBike(eventName);
                } else {
                    newOther(eventName);
                }
                break;
        }
    }

    /*
        * Create fragment transitions here in below methods, make sure to get animations working correctly
            * Try objectAnimator for animations
     */

    public void newWalk(String name) {questionTwoTransaction("WALK",name);}
    public void newRun(String name) {questionTwoTransaction("RUN",name);}
    public void newBike(String name) {questionTwoTransaction("BIKE",name);}
    public void newOther(String name) {questionTwoTransaction("OTHER",name);}

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

    public void questionTwoCallback() {
        /*
            * need to save new event to DB
            * Need to create Live Event actiivty where the event is traced live while user is in motion
            * Must update "My Activity" tab with completed events
         */
    }

}
