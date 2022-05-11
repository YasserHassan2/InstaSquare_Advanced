package com.que.shot.queshot;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;

import androidx.annotation.ColorInt;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.que.shot.sticker.Sticker;
import com.que.shot.utils.SystemUtil;

public class QueShotTextView extends Sticker {

    private QueShotText quShotText;

    private int backgroundAlpha;

    private int backgroundBorder;

    private int backgroundColor;

    private BitmapDrawable backgroundDrawable;

    private final Context context;

    private Drawable drawable;

    private boolean isShowBackground;

    private float lineSpacingExtra = 0.0F;

    private float lineSpacingMultiplier = 1.0F;

    private float maxTextSizePixels;

    private float minTextSizePixels;

    private int paddingHeight;

    private int paddingWidth;

    private StaticLayout staticLayout;

    private String text;

    private Layout.Alignment textAlign;

    private int textAlpha;

    private int textColor;

    private int textHeight;

    private final TextPaint textPaint;

    private int textWidth;


    public QueShotTextView(@NonNull Context paramContext, QueShotText quShotText) {
        this.context = paramContext;
        this.quShotText = quShotText;
        this.textPaint = new TextPaint(1);
        QueShotTextView quShotTextView = setTextSize(quShotText.getQuShotTextSize()).setTextWidth(quShotText.getQuShotTextWidth()).setTextHeight(quShotText.getQuShotTextHeight()).setText(quShotText.getQuShotTexts()).setPaddingWidth(SystemUtil.dpToPx(paramContext, quShotText.getPaddingWidth())).setBackgroundBorder(SystemUtil.dpToPx(paramContext, quShotText.getQuShotBackgroundBorder())).setTextColor(quShotText.getQuShotTextColor()).setTextAlpha(quShotText.getQuShotTextAlpha()).setBackgroundColor(quShotText.getQuShotBackgroundColor()).setBackgroundAlpha(quShotText.getQuShotBackgroundAlpha()).setShowBackground(quShotText.isQuShotShowBackground()).setTextColor(quShotText.getQuShotTextColor());
        AssetManager assetManager = paramContext.getAssets();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("fonts/");
        stringBuilder.append(quShotText.getQuShotFontName());
        quShotTextView.setTypeface(Typeface.createFromAsset(assetManager, stringBuilder.toString())).setTextAlign(quShotText.getQuShotTextAlign()).setTextShare(quShotText.getQuShotTextShader()).resizeText();
    }

    private float convertSpToPx(float paramFloat) {
        return paramFloat * (this.context.getResources().getDisplayMetrics()).scaledDensity;
    }

    public void draw(@NonNull Canvas paramCanvas) {
        Matrix matrix = getMatrix();
        paramCanvas.save();
        paramCanvas.concat(matrix);
        if (this.isShowBackground) {
            Paint paint = new Paint();
            if (this.backgroundDrawable != null) {
                paint.setShader(new BitmapShader(this.backgroundDrawable.getBitmap(), Shader.TileMode.MIRROR, Shader.TileMode.MIRROR));
                paint.setAlpha(this.backgroundAlpha);
            } else {
                paint.setARGB(this.backgroundAlpha, Color.red(this.backgroundColor), Color.green(this.backgroundColor), Color.blue(this.backgroundColor));
            }
            paramCanvas.drawRoundRect(0.0F, 0.0F, this.textWidth, this.textHeight, this.backgroundBorder, this.backgroundBorder, paint);
            paramCanvas.restore();
            paramCanvas.save();
            paramCanvas.concat(matrix);
        }
        paramCanvas.restore();
        paramCanvas.save();
        paramCanvas.concat(matrix);
        int i = this.paddingWidth;
        int j = this.textHeight / 2;
        int k = this.staticLayout.getHeight() / 2;
        paramCanvas.translate(i, (j - k));
        this.staticLayout.draw(paramCanvas);
        paramCanvas.restore();
        paramCanvas.save();
        paramCanvas.concat(matrix);
        paramCanvas.restore();
    }

    public QueShotText getQuShotText() {
        return this.quShotText;
    }

    public int getAlpha() {
        return this.textPaint.getAlpha();
    }


    @NonNull
    public Drawable getDrawable() {
        return this.drawable;
    }

    public int getHeight() {
        return this.textHeight;
    }


    @Nullable
    public String getText() {
        return this.text;
    }

    public int getWidth() {
        return this.textWidth;
    }

    public void release() {
        super.release();
        if (this.drawable != null)
            this.drawable = null;
    }

    @NonNull
    public QueShotTextView resizeText() {
        String str = getText();
        if (str != null) {
            if (str.length() <= 0)
                return this;
           this.textPaint.setTextAlign(Paint.Align.LEFT);
            this.textPaint.setARGB(this.textAlpha, Color.red(this.textColor), Color.green(this.textColor), Color.blue(this.textColor));
            int i = this.textWidth - this.paddingWidth * 2;
            if (i <= 0)
                i = 100;
            StaticLayout.Builder builder = StaticLayout.Builder.obtain(this.text, 0, this.text.length(), textPaint, i);
            this.staticLayout = builder.build();
            return this;
        }
        return this;
    }

    @NonNull
    public QueShotTextView setAlpha(@IntRange(from = 0L, to = 255L) int paramInt) {
        this.textPaint.setAlpha(paramInt);
        return this;
    }

    public QueShotTextView setBackgroundAlpha(int paramInt) {
        this.backgroundAlpha = paramInt;
        return this;
    }

    public QueShotTextView setBackgroundBorder(int paramInt) {
        this.backgroundBorder = paramInt;
        return this;
    }

    public QueShotTextView setBackgroundColor(int paramInt) {
        this.backgroundColor = paramInt;
        return this;
    }


    public QueShotTextView setDrawable(@NonNull Drawable paramDrawable) {
        this.drawable = paramDrawable;
        return this;
    }


    public QueShotTextView setPaddingWidth(int paramInt) {
        this.paddingWidth = paramInt;
        return this;
    }


    public QueShotTextView setShowBackground(boolean paramBoolean) {
        this.isShowBackground = paramBoolean;
        return this;
    }

    @NonNull
    public QueShotTextView setText(@Nullable String paramString) {
        this.text = paramString;
        return this;
    }

    @NonNull
    public QueShotTextView setTextAlign(@NonNull int paramInt) {
        switch (paramInt) {
            default:
                return this;
            case 4:
                this.textAlign = Layout.Alignment.ALIGN_CENTER;
                return this;
            case 3:
                this.textAlign = Layout.Alignment.ALIGN_OPPOSITE;
                return this;
            case 2:
                break;
        }
        this.textAlign = Layout.Alignment.ALIGN_NORMAL;
        return this;
    }

    public QueShotTextView setTextAlpha(int paramInt) {
        this.textAlpha = paramInt;
        return this;
    }

    @NonNull
    public QueShotTextView setTextColor(@ColorInt int paramInt) {
        this.textColor = paramInt;
        return this;
    }

    public QueShotTextView setTextHeight(int paramInt) {
        this.textHeight = paramInt;
        return this;
    }

    @NonNull
    public QueShotTextView setTextShare(@Nullable Shader paramShader) {
        this.textPaint.setShader(paramShader);
        return this;
    }

    @NonNull
    public QueShotTextView setTextSize(int paramInt) {
        this.textPaint.setTextSize(convertSpToPx(paramInt));
        return this;
    }

    public QueShotTextView setTextWidth(int paramInt) {
        this.textWidth = paramInt;
        return this;
    }

    @NonNull
    public QueShotTextView setTypeface(@Nullable Typeface paramTypeface) {
        this.textPaint.setTypeface(paramTypeface);
        return this;
    }
}
