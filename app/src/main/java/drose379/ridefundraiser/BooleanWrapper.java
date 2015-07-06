package drose379.ridefundraiser;

/**
 * Created by drose379 on 7/5/15.
 */
public class BooleanWrapper {

    private boolean savedItem;

    public BooleanWrapper(boolean initialBool) {
        savedItem = initialBool;
    }

    public void setSavedItem(boolean bool) {
        savedItem = bool;
    }

    public boolean getSavedItem() {
        return savedItem;
    }

}
