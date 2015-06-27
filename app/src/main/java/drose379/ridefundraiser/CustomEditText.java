package drose379.ridefundraiser;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by drose379 on 6/26/15.
 */
public class CustomEditText extends EditText {

    public CustomEditText(Context context) {
        super(context);
        this.setTypeface(TypeHelper.getTypeface(context));
    }
    public CustomEditText(Context context, AttributeSet attrs) {
        super(context,attrs);
    }

}
