package com.que.shot.layer.slant;

import com.que.shot.grid.QueShotLayout;
import com.que.shot.grid.QueShotLine;

public class SevenSlantLayout extends NumberSlantLayout {
    public int getThemeCount() {
        return 2;
    }

    public SevenSlantLayout(SlantCollageLayout slantPuzzleLayout, boolean z) {
        super(slantPuzzleLayout, z);
    }

    public SevenSlantLayout(int i) {
        super(i);
    }

    public void layout() {
        if (this.theme == 0) {
            addLine(0, QueShotLine.Direction.VERTICAL, 0.33333334f);
            addLine(1, QueShotLine.Direction.VERTICAL, 0.5f);
            addLine(0, QueShotLine.Direction.HORIZONTAL, 0.5f, 0.5f);
            addLine(1, QueShotLine.Direction.HORIZONTAL, 0.33f, 0.33f);
            addLine(3, QueShotLine.Direction.HORIZONTAL, 0.5f, 0.5f);
            addLine(2, QueShotLine.Direction.HORIZONTAL, 0.5f, 0.5f);
        }
    }

    public QueShotLayout clone(QueShotLayout quShotLayout) {
        return new SevenSlantLayout((SlantCollageLayout) quShotLayout, true);
    }
}
