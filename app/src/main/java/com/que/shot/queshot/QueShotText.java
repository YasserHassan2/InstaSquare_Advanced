package com.que.shot.queshot;

import android.graphics.Shader;

public class QueShotText {
    private int quShotBackgroundAlpha;
    private int quShotBackgroundBorder;
    private int quShotBackgroundColor;
    private int quShotBackgroundColorIndex;
    private int quShotFontIndex;
    private String quShotFontName;
    private boolean quShotShowBackground;
    private int paddingHeight;
    private int paddingWidth;
    private String quShotTexts;
    private int quShotTextAlign;
    private int quShotTextAlpha;
    private int quShotTextColor;
    private int quShotTextColorIndex;
    private int quShotTextHeight;
    private Shader quShotTextShader;
    private int quShotTextShaderIndex;
    private int quShotTextShadowIndex;
    private int quShotTextSize;
    private int quShotTextWidth;

    public static QueShotText getDefaultProperties() {
        QueShotText quShotText = new QueShotText();
        quShotText.setQuShotTextSize(30);
        quShotText.setQuShotTextAlign(4);
        quShotText.setQuShotFontName("font.ttf");
        quShotText.setQuShotTextColor(-1);
        quShotText.setQuShotTextAlpha(255);
        quShotText.setQuShotBackgroundAlpha(255);
        quShotText.setPaddingWidth(12);
        quShotText.setQuShotTextShaderIndex(7);
        quShotText.setQuShotBackgroundColorIndex(21);
        quShotText.setQuShotTextColorIndex(16);
        quShotText.setQuShotFontIndex(0);
        quShotText.setQuShotShowBackground(false);
        quShotText.setQuShotBackgroundBorder(8);
        quShotText.setQuShotTextAlign(4);
        return quShotText;
    }

    public int getQuShotTextColorIndex() {
        return this.quShotTextColorIndex;
    }

    public void setQuShotTextColorIndex(int i) {
        this.quShotTextColorIndex = i;
    }

    public int getQuShotTextShaderIndex() {
        return this.quShotTextShaderIndex;
    }

    public void setQuShotTextShaderIndex(int i) {
        this.quShotTextShaderIndex = i;
    }

    public int getQuShotBackgroundColorIndex() {
        return this.quShotBackgroundColorIndex;
    }

    public void setQuShotBackgroundColorIndex(int i) {
        this.quShotBackgroundColorIndex = i;
    }

    public int getQuShotFontIndex() {
        return this.quShotFontIndex;
    }

    public void setQuShotFontIndex(int i) {
        this.quShotFontIndex = i;
    }

    public int getQuShotTextShadowIndex() {
        return this.quShotTextShadowIndex;
    }

    public void setQuShotTextShadowIndex(int i) {
        this.quShotTextShadowIndex = i;
    }

    public int getQuShotBackgroundBorder() {
        return this.quShotBackgroundBorder;
    }

    public void setQuShotBackgroundBorder(int i) {
        this.quShotBackgroundBorder = i;
    }

    public int getQuShotTextHeight() {
        return this.quShotTextHeight;
    }

    public void setQuShotTextHeight(int i) {
        this.quShotTextHeight = i;
    }

    public int getQuShotTextWidth() {
        return this.quShotTextWidth;
    }

    public void setQuShotTextWidth(int i) {
        this.quShotTextWidth = i;
    }

    public int getPaddingWidth() {
        return this.paddingWidth;
    }

    public void setPaddingWidth(int i) {
        this.paddingWidth = i;
    }

    public int getPaddingHeight() {
        return this.paddingHeight;
    }

    public void setPaddingHeight(int i) {
        this.paddingHeight = i;
    }

    public int getQuShotTextSize() {
        return this.quShotTextSize;
    }

    public void setQuShotTextSize(int i) {
        this.quShotTextSize = i;
    }

    public int getQuShotTextColor() {
        return this.quShotTextColor;
    }

    public void setQuShotTextColor(int i) {
        this.quShotTextColor = i;
    }

    public int getQuShotTextAlpha() {
        return this.quShotTextAlpha;
    }

    public void setQuShotTextAlpha(int i) {
        this.quShotTextAlpha = i;
    }

    public Shader getQuShotTextShader() {
        return this.quShotTextShader;
    }

    public void setQuShotTextShader(Shader shader) {
        this.quShotTextShader = shader;
    }

    public String getQuShotTexts() {
        return this.quShotTexts;
    }

    public void setQuShotTexts(String str) {
        this.quShotTexts = str;
    }

    public int getQuShotTextAlign() {
        return this.quShotTextAlign;
    }

    public void setQuShotTextAlign(int i) {
        this.quShotTextAlign = i;
    }

    public String getQuShotFontName() {
        return this.quShotFontName;
    }

    public void setQuShotFontName(String str) {
        this.quShotFontName = str;
    }

    public int getQuShotBackgroundColor() {
        return this.quShotBackgroundColor;
    }

    public void setQuShotBackgroundColor(int i) {
        this.quShotBackgroundColor = i;
    }

    public boolean isQuShotShowBackground() {
        return this.quShotShowBackground;
    }

    public void setQuShotShowBackground(boolean z) {
        this.quShotShowBackground = z;
    }

    public int getQuShotBackgroundAlpha() {
        return this.quShotBackgroundAlpha;
    }


    public void setQuShotBackgroundAlpha(int i) {
        this.quShotBackgroundAlpha = i;
    }
}
