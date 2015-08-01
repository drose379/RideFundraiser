package drose379.ridefundraiser;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.NavUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by dylan on 8/1/15.
 */
public class CompleteMileEvent implements Parcelable {

    /**
     * ******************************************************************************************************************************************************************
     * Must be parcelable in order to pass from LiveMileEvent -> MileEventOverview -> ViewPagerAdapterOverview -> EventOverviewFrag or DonationOverview Frag
     * ******************************************************************************************************************************************************************
     * User name
     * Event Name
     * Organization Name
     * Donation / Mile MUST RETURN AS DOUBLE
     * Goal Distance MUST RETURN AS DOUBLE OR STRING
     * Actual Distance MUST RETURN AS DOUBLE OR STRING
     * Time
     * Average Speed
     * Percent Complete
     * Donation summary (receive JSONArray, put into List<Donation>)
     */

    private String user;
    private String eventName;
    private String organization;
    private String time;
    private String averageSpeed;
    private String percentComplete;

    private double donationRate;
    private double distance;

    private List<Donation> donations;

    NumberFormat currencyFormatUS = NumberFormat.getCurrencyInstance(Locale.US);
    NumberFormat currencyFormatUK = NumberFormat.getCurrencyInstance(Locale.UK);

    DecimalFormat distanceFormat = new DecimalFormat("##.##");

    public static final Parcelable.Creator<CompleteMileEvent> CREATOR = new Parcelable.Creator<CompleteMileEvent>() {
        @Override
        public CompleteMileEvent[] newArray(int size) {
            return new CompleteMileEvent[size];
        }

        @Override
        public CompleteMileEvent createFromParcel(Parcel in) {
            return new CompleteMileEvent(in);
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest,int flags) {
        dest.writeString(user);
        dest.writeString(eventName);
        dest.writeString(organization);

        //create bundle and dest.writeBundle() and grab bindle in CompleteMileEvent(Parcel in) constructor just grab bundle and keys
    }

    public CompleteMileEvent() {

    }

    public CompleteMileEvent(Parcel in) {
        //grab items from parcel with in.readX()


    }

    public CompleteMileEvent setUser(String user) {
        this.user = user;
        return this;
    }
    public CompleteMileEvent setEventName(String eventName) {
        this.eventName = eventName;
        return this;
    }
    public CompleteMileEvent setOrganization(String organization) {
        this.organization = organization;
        return this;
    }
    public CompleteMileEvent setDonationRate(String donationRate) {
        this.donationRate = Double.parseDouble(donationRate);
        return this;
    }
    public CompleteMileEvent setDistance(String distance) {
        this.distance = Double.parseDouble(distance);
        return this;
    }

    public CompleteMileEvent setTime(String time) {
        this.time = time;
        return this;
    }
    public CompleteMileEvent setAverageSpeed(String averageSpeed) {
        this.averageSpeed = averageSpeed;
        return this;
    }
    public CompleteMileEvent setPercentComplete(String percent) {
        percentComplete = percent;
        return this;
    }
    public CompleteMileEvent setDonationSummary(String donationSummary) {
        try {
            JSONArray donationJSON = new JSONArray(donationSummary);

            for (int i = 0;i<donationJSON.length();i++) {

                JSONObject innerItem = donationJSON.getJSONObject(i);
                Donation currentDonation = new Donation(innerItem.getString("user"),innerItem.getString("amount"));
                String message = innerItem.getString("mesasge").equals("null") ? null : innerItem.getString("message");
                if(message != null) {currentDonation.setMessage(message);}

                donations.add(currentDonation);

            }

        } catch (JSONException e) {
            throw new RuntimeException(e.getMessage());
        }

        return this;
    }


    public String getUser() {return user;}
    public String getEventName() {return eventName;}
    public String getOrganization() {return organization;}

    public double getDonationRate() {return donationRate;}
    public String getDonationRateFormatted() {return currencyFormatUS.format(donationRate);}

    public double getDistance() {return distance;}
    public String getDistanceFormatted() {return distanceFormat.format(distance);}

    public List<Donation> getDonations() {return donations;}


}
