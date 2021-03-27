package com.fuckcoolapk.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;

public class FuckCheckBox extends CheckBox {

    {
        //this.setButtonDrawable(null);
        // this.setCompoundDrawables(null,null,this.getContext().getDrawable(android.R.attr.listChoiceIndicatorMultiple),null);
    }

    public FuckCheckBox(Context context) {
        super(context);
    }

    public FuckCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FuckCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public FuckCheckBox(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
