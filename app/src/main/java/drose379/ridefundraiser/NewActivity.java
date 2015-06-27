package drose379.ridefundraiser;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

/**
 * Created by drose379 on 6/25/15.
 */
@SuppressLint("NewApi")
public class NewActivity extends AppCompatActivity {

    /*
        * initViewController needs to be organized
        *  Make all views that need global access private properties of the class?
     */
    


    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.new_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initViewController();

    }

    public void initViewController() {
        final RelativeLayout question1Root = (RelativeLayout) findViewById(R.id.questionLayout1);
        final RelativeLayout question2Root = (RelativeLayout) findViewById(R.id.questionLayout2);

        final AutoCompleteTextView question1 = (AutoCompleteTextView) findViewById(R.id.question1EditText);

        final LinearLayout statusLayout = (LinearLayout) findViewById(R.id.activityInfo);
        final CustomTextView statusText = (CustomTextView) findViewById(R.id.updatesText);

        Button nextButton = (Button) findViewById(R.id.nextButton1);


        String[] autoValues = {"Running","Walking","Biking"};
        ArrayAdapter<String> optionsAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,autoValues);
        question1.setAdapter(optionsAdapter);
        question1.setTypeface(TypeHelper.getTypeface(this));

        nextButton.setTypeface(TypeHelper.getTypefaceBold(this));
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                    * need to check value of question1 (EditText)
                    * Check if it is walking running or biking
                    * If not, show RelativeLayout specific to "OTHER" type of events
                    * If Yes, show relative layout correspondig to that type of action
                    * See G-O-D for more details
                 */

                question1Root.animate()
                        .alpha(0f)
                        .setDuration(375)
                        .setListener(new AnimatorListenerAdapter() {
                           @Override
                            public void onAnimationEnd(Animator animation) {
                               question1Root.setVisibility(View.GONE);
                               question2Root.setVisibility(View.VISIBLE);
                               statusLayout.setVisibility(View.VISIBLE);
                               statusText.setText(question1.getText().toString());
                           }
                        });

            }
        });
    }

}
