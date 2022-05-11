package com.que.shot.queshot;

import android.content.Context;
import android.util.AttributeSet;

public class QueShotSquareView extends QueShotGridView {
    public QueShotSquareView(Context context) {
        super(context);
    }

    public QueShotSquareView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public QueShotSquareView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }


    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        if (measuredWidth > measuredHeight) {
            measuredWidth = measuredHeight;
        }
        setMeasuredDimension(measuredWidth, measuredWidth);
    }
}
