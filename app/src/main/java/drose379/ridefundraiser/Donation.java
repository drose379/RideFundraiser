package drose379.ridefundraiser;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dylan on 8/1/15.
 */
public class Donation implements Parcelable {

    private String user;
    private String amount;
    private String message;

    public static final Creator<Donation> CREATOR = new Creator<Donation>() {
        @Override
        public Donation[] newArray(int size) {
            return new Donation[size];
        }

        @Override
        public Donation createFromParcel(Parcel in) {
            return new Donation(in);
        }
    };

    public Donation(String user,String amount) {
        this.user = user;
        this.amount = amount;
    }
    public Donation(Parcel in) {
        Bundle data = in.readBundle();
        user = data.getString("user");
        amount = data.getString("amount");
        message = data.getString("message");
    }

    @Override
    public void writeToParcel(Parcel dest,int flags) {
        Bundle data = new Bundle();
        data.putString("user",user);
        data.putString("amount",amount);
        data.putString("message",message);
    }
    @Override
    public int describeContents() {
        return 0;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUser() {
        return user;
    }
    public String getAmount() {
        return amount;
    }
    public String getMessage() {
        return message;
    }
}
