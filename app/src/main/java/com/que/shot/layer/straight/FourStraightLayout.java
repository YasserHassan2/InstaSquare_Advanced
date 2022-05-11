package com.que.shot.layer.straight;

import com.que.shot.grid.QueShotLayout;
import com.que.shot.grid.QueShotLine;

public class FourStraightLayout extends NumberStraightLayout {
    private static final String TAG = "FourStraightLayout";

    public int getThemeCount() {
        return 8;
    }

    public FourStraightLayout(StraightCollageLayout straightPuzzleLayout, boolean z) {
        super(straightPuzzleLayout, z);
    }

    public FourStraightLayout(int i) {
        super(i);
    }

    public void layout() {
        switch (this.theme) {
            case 0:
                addCross(0, 0.5f);
                return;
            case 1:
                addLine(0, QueShotLine.Direction.HORIZONTAL, 0.33333334f);
                cutAreaEqualPart(0, 3, QueShotLine.Direction.VERTICAL);
                return;
            case 2:
                addLine(0, QueShotLine.Direction.HORIZONTAL, 0.6666667f);
                cutAreaEqualPart(1, 3, QueShotLine.Direction.VERTICAL);
                return;
            case 3:
                addLine(0, QueShotLine.Direction.VERTICAL, 0.33333334f);
                cutAreaEqualPart(0, 3, QueShotLine.Direction.HORIZONTAL);
                return;
            case 4:
                addLine(0, QueShotLine.Direction.VERTICAL, 0.6666667f);
                cutAreaEqualPart(1, 3, QueShotLine.Direction.HORIZONTAL);
                return;
            case 5:
                addLine(0, QueShotLine.Direction.VERTICAL, 0.5f);
                addLine(1, QueShotLine.Direction.HORIZONTAL, 0.6666667f);
                addLine(1, QueShotLine.Direction.HORIZONTAL, 0.33333334f);
                return;
            case 6:
                cutAreaEqualPart(0, 4, QueShotLine.Direction.HORIZONTAL);
                return;
            case 7:
                cutAreaEqualPart(0, 4, QueShotLine.Direction.VERTICAL);
                return;
            default:
                cutAreaEqualPart(0, 4, QueShotLine.Direction.HORIZONTAL);
                return;
        }
    }

    public QueShotLayout clone(QueShotLayout quShotLayout) {
        return new FourStraightLayout((StraightCollageLayout) quShotLayout, true);
    }
}
