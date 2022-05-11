package com.que.shot.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.que.shot.R;

public class ColorSliderUtils extends View {
    private Rect[] mColorFullRects = new Rect[0];
    private Rect[] mColorRects = new Rect[0];
    private int[] mColors = new int[0];
    private OnColorSelectedListener mListener;
    private Paint mPaint;
    private int mSelectedItem;
    private Paint mSelectorPaint;
    public static final int[] ColorSlider = {R.attr.cs_colors, R.attr.cs_from_color, R.attr.cs_hex_colors, R.attr.cs_steps, R.attr.cs_to_color};

    public interface OnColorSelectedListener {
        void onColorChanged(int i, @ColorInt int i2);
    }

    public ColorSliderUtils(Context context) {
        super(context);
        init(context, null);
    }

    public ColorSliderUtils(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet);
    }

    public ColorSliderUtils(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context, attributeSet);
    }

    @SuppressLint("ResourceType")
    private void init(Context context, AttributeSet attributeSet) {
        this.mPaint = new Paint();
        this.mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        this.mSelectorPaint = new Paint();
        this.mSelectorPaint.setStyle(Paint.Style.STROKE);
        this.mSelectorPaint.setColor(ContextCompat.getColor(context,17170446));
        this.mSelectorPaint.setStrokeWidth(2.0f);
        setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return com.que.shot.utils.ColorSliderUtils.this.processTouch(motionEvent);
            }
        });
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, ColorSlider, 0, 0);
            try {
                int resourceId = obtainStyledAttributes.getResourceId(0, 0);
                int resourceId2 = obtainStyledAttributes.getResourceId(2, 0);
                if (resourceId != 0) {
                    int[] intArray = getResources().getIntArray(resourceId);
                    if (intArray.length > 0) {
                        this.mColors = new int[intArray.length];
                        System.arraycopy(intArray, 0, this.mColors, 0, intArray.length);
                    }
                } else if (resourceId2 != 0) {
                    String[] stringArray = getResources().getStringArray(resourceId2);
                    if (stringArray.length > 0) {
                        convertToColors(stringArray);
                    }
                }
                if (this.mColors.length == 0) {
                    int color = obtainStyledAttributes.getColor(1, 0);
                    int color2 = obtainStyledAttributes.getColor(4, 0);
                    int i = obtainStyledAttributes.getInt(3, 21);
                    if (!(color == 0 || color2 == 0 || i == 0)) {
                        calculateColors(color, color2, i);
                    }
                }
            } catch (Exception e) {
                Log.d("ColorSlider", "init: " + e.getLocalizedMessage());
            } catch (Throwable th) {
                obtainStyledAttributes.recycle();
                throw th;
            }
            obtainStyledAttributes.recycle();
        }
        if (this.mColors.length == 0) {
            initDefaultColors();
        }
        this.mColorRects = new Rect[this.mColors.length];
        this.mColorFullRects = new Rect[this.mColors.length];
    }

    private void initDefaultColors() {
        this.mColors = new int[]{Color.parseColor("#F44336"), Color.parseColor("#E91E63"), Color.parseColor("#9C27B0"), Color.parseColor("#673AB7"), Color.parseColor("#3F51B5"), Color.parseColor("#2196F3"), Color.parseColor("#03A9F4"), Color.parseColor("#00BCD4"), Color.parseColor("#009688"), Color.parseColor("#4CAF50"), Color.parseColor("#8BC34A"), Color.parseColor("#CDDC39"), Color.parseColor("#FFEB3B"), Color.parseColor("#FFC107"), Color.parseColor("#FF9800"), Color.parseColor("#FF5722"), Color.parseColor("#795548"), Color.parseColor("#9E9E9E"), Color.parseColor("#607D8B"), Color.parseColor("#FFFFFF")};
    }

    private void convertToColors(String[] strArr) {
        this.mColors = new int[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            this.mColors[i] = Color.parseColor(strArr[i]);
        }
    }


    private void calculateColors(@ColorInt int i, @ColorInt int i2, int i3) {
        float alpha = (float) Color.alpha(i);
        float red = (float) Color.red(i);
        float green = (float) Color.green(i);
        float blue = (float) Color.blue(i);
        float f = (float) i3;
        float alpha2 = (((float) Color.alpha(i2)) - alpha) / f;
        float red2 = (((float) Color.red(i2)) - red) / f;
        float green2 = (((float) Color.green(i2)) - green) / f;
        float blue2 = (((float) Color.blue(i2)) - blue) / f;
        this.mColors = new int[i3];
        for (int i4 = 0; i4 < i3; i4++) {
            float f2 = (float) i4;
            this.mColors[i4] = Color.argb((int) ((alpha2 * f2) + alpha), (int) ((red2 * f2) + red), (int) ((green2 * f2) + green), (int) ((f2 * blue2) + blue));
        }
    }


    public boolean processTouch(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0) {
            return true;
        }
        if (motionEvent.getAction() != 2 && motionEvent.getAction() != 1) {
            return false;
        }
        updateView(motionEvent.getX(), motionEvent.getY());
        return true;
    }

    private void updateView(float f, float f2) {
        int i = 0;
        while (i < this.mColorFullRects.length) {
            Rect rect = this.mColorFullRects[i];
            if (rect == null || !rect.contains((int) f, (int) f2) || i == this.mSelectedItem) {
                i++;
            } else {
                this.mSelectedItem = i;
                notifyChanged();
                invalidate();
                return;
            }
        }
    }

    private void notifyChanged() {
        if (this.mListener != null) {
            this.mListener.onColorChanged(this.mSelectedItem, this.mColors[this.mSelectedItem]);
        }
    }


    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mColorRects.length > 0) {
            drawSlider(canvas);
        }
    }

    private void drawSlider(Canvas canvas) {
        for (int i = 0; i < this.mColorRects.length; i++) {
            this.mPaint.setColor(this.mColors[i]);
            if (i == this.mSelectedItem) {
                canvas.drawRect(this.mColorFullRects[i], this.mPaint);
                canvas.drawRect(this.mColorFullRects[i], this.mSelectorPaint);
            } else {
                canvas.drawRect(this.mColorRects[i], this.mPaint);
            }
        }
    }


    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        setMeasuredDimension(View.MeasureSpec.getSize(i), View.MeasureSpec.getSize(i2));
        calculateRectangles();
    }

    private void calculateRectangles() {
        float measuredHeight = (float) getMeasuredHeight();
        float measuredWidth = ((float) getMeasuredWidth()) / ((float) this.mColors.length);
        this.mColorRects = new Rect[this.mColors.length];
        this.mColorFullRects = new Rect[this.mColors.length];
        float f = 0.1f * measuredHeight;
        int i = 0;
        while (i < this.mColors.length) {
            int i2 = (int) (((float) i) * measuredWidth);
            int i3 = i + 1;
            int i4 = (int) (((float) i3) * measuredWidth);
            this.mColorRects[i] = new Rect(i2, (int) f, i4, (int) (measuredHeight - f));
            this.mColorFullRects[i] = new Rect(i2, 0, i4, (int) measuredHeight);
            i = i3;
        }
    }
}
