package drose379.ridefundraiser;

/**
 * Created by dylan on 8/1/15.
 */
public class Donation {
    private String user;
    private String amount;
    private String message;

    public Donation(String user,String amount) {
        this.user = user;
        this.amount = amount;
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
