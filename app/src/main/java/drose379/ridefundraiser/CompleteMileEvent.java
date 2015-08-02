package drose379.ridefundraiser;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.NavUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
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
     * Map snapshot byte array
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
    private String distanceFormatted;

    private double donationRate;
    private double distance;

    private byte[] liveMapBytes;

    private ArrayList<Donation> donations;

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
        //create bundle and dest.writeBundle() and grab bindle in CompleteMileEvent(Parcel in) constructor just grab bundle and keys
        Bundle completeInfo = new Bundle();

        completeInfo.putString("user",user);
        completeInfo.putString("eventName",eventName);
        completeInfo.putString("organization",organization);
        completeInfo.putString("time",time);
        completeInfo.putString("averageSpeed",averageSpeed);
        completeInfo.putString("percentComplete",percentComplete);
        completeInfo.putDouble("donationRate", donationRate);
        completeInfo.putDouble("distance",distance);
        completeInfo.putString("distanceString",distanceFormatted);
        completeInfo.putParcelableArrayList("donations",donations);
        completeInfo.putByteArray("imageBytes",liveMapBytes);

        dest.writeBundle(completeInfo);
    }

    public CompleteMileEvent() {

    }

    public CompleteMileEvent(Parcel in) {
        Bundle data = in.readBundle();
        user = data.getString("user");
        eventName = data.getString("eventName");
        organization = data.getString("organization");
        time = data.getString("time");
        averageSpeed = data.getString("averageSpeed");
        percentComplete = data.getString("percentComplete");
        donationRate = data.getDouble("donationRate");
        distance = data.getDouble("distance");
        distanceFormatted = data.getString("distanceString");
        donations = data.getParcelableArrayList("donations");
        liveMapBytes = data.getByteArray("imageBytes");
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
        //need to split into 2 words, grab first
        String[] items = distance.split("\\s");
        Log.i("distanceItems",items.toString());
        this.distance = Double.parseDouble(items[0]);
        this.distanceFormatted = distance;
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

    public void setLiveMapBytes(byte[] imageBytes) {
        liveMapBytes = imageBytes;
    }


    public String getUser() {return user;}
    public String getEventName() {return eventName;}
    public String getOrganization() {return organization;}

    public double getDonationRate() {return donationRate;}
    public String getDonationRateFormatted() {return currencyFormatUS.format(donationRate);}

    public double getDistance() {return distance;}
    public String getDistanceFormatted() {return distanceFormatted; }
    public String getTime() {return time;}
    public String getAverageSpeed() {return averageSpeed;}
    public String getPercentComplete(){return percentComplete;}

    public List<Donation> getDonations() {return donations;}

    public Bitmap getMapImage() {
        Bitmap b = BitmapFactory.decodeByteArray(liveMapBytes,0,liveMapBytes.length);
        return b;
    }


}
