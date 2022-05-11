package com.que.shot.layer.straight;

import com.que.shot.grid.QueShotLayout;
import com.que.shot.grid.QueShotLine;

public class NineStraightLayout extends NumberStraightLayout {
    public int getThemeCount() {
        return 8;
    }

    public NineStraightLayout(StraightCollageLayout straightPuzzleLayout, boolean z) {
        super(straightPuzzleLayout, z);
    }

    public NineStraightLayout(int i) {
        super(i);
    }

    public void layout() {
        switch (this.theme) {
            case 0:
                cutAreaEqualPart(0, 2, 2);
                return;
            case 1:
                addLine(0, QueShotLine.Direction.VERTICAL, 0.75f);
                addLine(0, QueShotLine.Direction.VERTICAL, 0.33333334f);
                cutAreaEqualPart(2, 4, QueShotLine.Direction.HORIZONTAL);
                cutAreaEqualPart(0, 4, QueShotLine.Direction.HORIZONTAL);
                return;
            case 2:
                addLine(0, QueShotLine.Direction.HORIZONTAL, 0.75f);
                addLine(0, QueShotLine.Direction.HORIZONTAL, 0.33333334f);
                cutAreaEqualPart(2, 4, QueShotLine.Direction.VERTICAL);
                cutAreaEqualPart(0, 4, QueShotLine.Direction.VERTICAL);
                return;
            case 3:
                addLine(0, QueShotLine.Direction.HORIZONTAL, 0.75f);
                addLine(0, QueShotLine.Direction.HORIZONTAL, 0.33333334f);
                cutAreaEqualPart(2, 3, QueShotLine.Direction.VERTICAL);
                addLine(1, QueShotLine.Direction.VERTICAL, 0.75f);
                addLine(1, QueShotLine.Direction.VERTICAL, 0.33333334f);
                cutAreaEqualPart(0, 3, QueShotLine.Direction.VERTICAL);
                return;
            case 4:
                addLine(0, QueShotLine.Direction.VERTICAL, 0.75f);
                addLine(0, QueShotLine.Direction.VERTICAL, 0.33333334f);
                cutAreaEqualPart(2, 3, QueShotLine.Direction.HORIZONTAL);
                addLine(1, QueShotLine.Direction.HORIZONTAL, 0.75f);
                addLine(1, QueShotLine.Direction.HORIZONTAL, 0.33333334f);
                cutAreaEqualPart(0, 3, QueShotLine.Direction.HORIZONTAL);
                return;
            case 5:
                cutAreaEqualPart(0, 3, QueShotLine.Direction.VERTICAL);
                addLine(2, QueShotLine.Direction.HORIZONTAL, 0.75f);
                addLine(2, QueShotLine.Direction.HORIZONTAL, 0.33333334f);
                cutAreaEqualPart(1, 3, QueShotLine.Direction.HORIZONTAL);
                addLine(0, QueShotLine.Direction.HORIZONTAL, 0.75f);
                addLine(0, QueShotLine.Direction.HORIZONTAL, 0.33333334f);
                return;
            case 6:
                cutAreaEqualPart(0, 3, QueShotLine.Direction.HORIZONTAL);
                addLine(2, QueShotLine.Direction.VERTICAL, 0.75f);
                addLine(2, QueShotLine.Direction.VERTICAL, 0.33333334f);
                cutAreaEqualPart(1, 3, QueShotLine.Direction.VERTICAL);
                addLine(0, QueShotLine.Direction.VERTICAL, 0.75f);
                addLine(0, QueShotLine.Direction.VERTICAL, 0.33333334f);
                return;
            case 7:
                addLine(0, QueShotLine.Direction.HORIZONTAL, 0.5f);
                cutAreaEqualPart(1, 1, 3);
                return;
            default:
                return;
        }
    }

    public QueShotLayout clone(QueShotLayout quShotLayout) {
        return new NineStraightLayout((StraightCollageLayout) quShotLayout, true);
    }
}
