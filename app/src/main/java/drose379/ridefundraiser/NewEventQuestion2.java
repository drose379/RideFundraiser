package drose379.ridefundraiser;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by drose379 on 6/27/15.
 */
public class NewEventQuestion2 extends Fragment {

    private NewEvent parentActivity;

    private String eventName;
    private String eventType;
    private String distanceUnit;

    private final String EVENT_MILE = "MILE";
    private final String EVENT_HOUR = "HOUR";


    private TextView goalDistanceDisplay = (TextView) getView().findViewById(R.id.goalDistanceView);
    private TextView donationRateDisplay = (TextView) getView().findViewById(R.id.donationRate);
    private TextView donatingToDisplay = (TextView) getView().findViewById(R.id.donatingTo);

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        parentActivity = (NewEvent) activity;
        eventType = getArguments().getString("eventType");
        eventName = getArguments().getString("eventName");
    }
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup parent,Bundle savedInstance) {
        super.onCreateView(inflater, parent, savedInstance);
        View v = inflater.inflate(R.layout.new_event_question_2, null);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        switch (eventType) {
            case EVENT_MILE :
                initMileView();
                break;
            case EVENT_HOUR :
                initHourView();
                break;
        }
    }

    /**
     * Create a initMileView method and an initHourView method
     */
    public void initMileView() {
        getView().findViewById(R.id.milesMeasureRoot).setVisibility(View.VISIBLE);

        TextView eventNameDisplay = (TextView) getView().findViewById(R.id.eventName);
        eventNameDisplay.setText(eventName);

        Button doneButton = (Button) getView().findViewById(R.id.doneButton2);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateAndCall();
            }
        });

        initGoalDistanceDisplay();
        initDonationRateDisplay();
        initDonationToDisplay();
    }

    public void initGoalDistanceDisplay() {
        EditText distanceGoal = (EditText) getView().findViewById(R.id.goalDistance);
        final Spinner distanceOptions = (Spinner) getView().findViewById(R.id.distanceUnits);

        final String[] options = getResources().getStringArray(R.array.distance);

        distanceUnit = distanceOptions.getSelectedItem().toString();

        distanceOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                distanceUnit = options[position];
                String[] currentDisplay = goalDistanceDisplay.getText().toString().split("\\s+");
                if (currentDisplay.length > 1) {
                    currentDisplay[1] = null;
                    currentDisplay[1] = distanceUnit;

                    String newDisplay = "";
                    newDisplay = newDisplay.concat(currentDisplay[0]);
                    newDisplay = newDisplay.concat(" " + currentDisplay[1]);

                    goalDistanceDisplay.setText(newDisplay);
                    initDonationRateDisplay();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        distanceGoal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                goalDistanceDisplay.setText(s + " " + distanceUnit);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void initDonationRateDisplay() {
        EditText donationAmount = (EditText) getView().findViewById(R.id.donationAmount);
        final Spinner moneyUnit = (Spinner) getView().findViewById(R.id.moneyUnits);

        if (donationRateDisplay.getText().toString().length() > 1) {
            String[] currentDisplay = donationRateDisplay.getText().toString().split("\\s+");
            currentDisplay[currentDisplay.length-1] = null;
            currentDisplay[currentDisplay.length-1] = distanceUnit.substring(0,distanceUnit.length()-1);
            String newDisplay = "";
            for (String item : currentDisplay) {
                newDisplay = newDisplay.concat(item + " ");
            }
            donationRateDisplay.setText(newDisplay);
        }

        donationAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                donationRateDisplay.setText("$" + s + " " + moneyUnit.getSelectedItem().toString() + " Per " + distanceUnit.substring(0, distanceUnit.length() - 1));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void initDonationToDisplay() {
        Spinner donationTo = (Spinner) getView().findViewById(R.id.organizationOptions);


        final String[] options = getResources().getStringArray(R.array.testOrganizations);

        donationTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView parent, View view, int position, long id) {
                donatingToDisplay.setText(options[position]);
            }

            @Override
            public void onNothingSelected(AdapterView parent) {

            }
        });
    }

    /**
     * End of mile root display
     */

    public void initHourView() {
        getView().findViewById(R.id.hourMeasureRoot).setVisibility(View.VISIBLE);
    }

    public void validateAndCall() {
        Locale locale = Locale.US;
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);


        if (goalDistanceDisplay.getText().toString().length() != 0 && donationRateDisplay.getText().toString().length() != 0) {



            /**
             * Need access to:
             * User * (CurrentUser.user)
             * Event Name * (eventName)
             * Organization (donatingTo)
             * Per mile donation (rate)
             * Goal distance (distance)
             */

            final String donatingTo = (String) donatingToDisplay.getText();
            final float distance = Float.parseFloat(goalDistanceDisplay.getText().toString());
            final float rate = Float.parseFloat(donationRateDisplay.getText().toString());

            float baseDonation = distance * rate;

            MaterialDialog confirmDialog = new MaterialDialog.Builder(getActivity())
                    .title("Confirm")
                    .content("Estimated Base Donation: " + currencyFormat.format(baseDonation))
                    .contentColor(Color.parseColor("#000000"))
                    .positiveText("Confirm")
                    .positiveColorRes(R.color.ColorPrimary)
                    .callback(new MaterialDialog.ButtonCallback() {
                        @Override
                        public void onPositive(MaterialDialog dialog) {
                            parentActivity.questionTwoCallback(eventType,CurrentUser.currentUser,eventName,donatingTo,rate,distance);
                        }
                    })
                    .build();
            confirmDialog.show();
        }

    }
}
