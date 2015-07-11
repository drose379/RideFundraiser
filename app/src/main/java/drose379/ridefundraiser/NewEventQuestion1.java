package drose379.ridefundraiser;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * Created by drose379 on 6/26/15.
 */
public class NewEventQuestion1 extends Fragment {

    private NewEvent parentActivity;

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        parentActivity = (NewEvent) context;
    }
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup parent,Bundle savedInstance) {
        super.onCreateView(inflater,parent,savedInstance);
        View v = inflater.inflate(R.layout.new_event_question_1,null);

        /*
            * Need to create instance of button, listen for click, evaluate the results of edittext and make callback to NewEvent Activity with results
            * Add options array to AutoCompleteTextView
         */
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        initViewController();
    }

    public void initViewController() {
        final AutoCompleteTextView answerField = (AutoCompleteTextView) getView().findViewById(R.id.question1EditText);

        final RadioGroup measureGroup = (RadioGroup) getView().findViewById(R.id.measureSelection);



        Button nextButton = (Button) getView().findViewById(R.id.nextButton1);

        answerField.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,new String[] {"Running","Walking","Biking"}));
        answerField.setTypeface(TypeHelper.getTypeface(getActivity()));

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String event = answerField.getText().toString().isEmpty() ? null : answerField.getText().toString();
                if (event != null) {
                    RadioButton selectedMeasure = (RadioButton) getView().findViewById(measureGroup.getCheckedRadioButtonId());
                    parentActivity.questionOneCallback(event, (String) selectedMeasure.getText());
                    //selectedMeasure is NOT NULL, move to next point
                    Log.i("measureTest",(String)selectedMeasure.getText());
                }
            }
        });
    }
}
