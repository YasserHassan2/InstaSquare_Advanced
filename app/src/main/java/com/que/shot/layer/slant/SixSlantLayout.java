package com.que.shot.layer.slant;

import com.que.shot.grid.QueShotLayout;
import com.que.shot.grid.QueShotLine;

public class SixSlantLayout extends NumberSlantLayout {
    public int getThemeCount() {
        return 2;
    }

    public SixSlantLayout(SlantCollageLayout slantPuzzleLayout, boolean z) {
        super(slantPuzzleLayout, z);
    }

    public SixSlantLayout(int i) {
        super(i);
    }

    public void layout() {
        switch (this.theme) {
            case 0:
                addLine(0, QueShotLine.Direction.VERTICAL, 0.33333334f);
                addLine(1, QueShotLine.Direction.VERTICAL, 0.5f);
                addLine(0, QueShotLine.Direction.HORIZONTAL, 0.7f, 0.7f);
                addLine(1, QueShotLine.Direction.HORIZONTAL, 0.5f, 0.5f);
                addLine(2, QueShotLine.Direction.HORIZONTAL, 0.3f, 0.3f);
                return;
            case 1:
                addLine(0, QueShotLine.Direction.HORIZONTAL, 0.33333334f);
                addLine(1, QueShotLine.Direction.HORIZONTAL, 0.5f);
                addLine(0, QueShotLine.Direction.VERTICAL, 0.3f, 0.3f);
                addLine(2, QueShotLine.Direction.VERTICAL, 0.5f, 0.5f);
                addLine(4, QueShotLine.Direction.VERTICAL, 0.7f, 0.7f);
                return;
            default:
                return;
        }
    }

    public QueShotLayout clone(QueShotLayout quShotLayout) {
        return new SixSlantLayout((SlantCollageLayout) quShotLayout, true);
    }
}
