package com.que.shot.custom;

import com.steelkiwi.cropiwa.AspectRatio;

public class RatioCustom extends AspectRatio {
    private int selectedIem;

    public RatioCustom(int i, int i2,  int i4) {
        super(i, i2);
        this.selectedIem = i4;
    }

    public int getSelectedIem() {
        return this.selectedIem;
    }
}
