package com.lingzhi.smart.view.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.lingzhi.smart.R;


public class RoundTextView extends AppCompatTextView {

    public RoundTextView(Context context) {
        this(context, null);
    }

    public RoundTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Typeface font = Typeface.createFromAsset(context.getAssets(), "roundtext.ttf");
        setTypeface(font);
        setTextColor(getResources().getColor(R.color.color_common_text));
    }
}
