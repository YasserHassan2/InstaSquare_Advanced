package com.que.shot.layer.straight;

import com.que.shot.grid.QueShotLayout;
import com.que.shot.grid.QueShotLine;

public class FiveStraightLayout extends NumberStraightLayout {
    public int getThemeCount() {
        return 17;
    }

    public FiveStraightLayout() {
    }

    public FiveStraightLayout(StraightCollageLayout straightPuzzleLayout, boolean z) {
        super(straightPuzzleLayout, z);
    }

    public FiveStraightLayout(int i) {
        super(i);
    }

    public void layout() {
        switch (this.theme) {
            case 0:
                addLine(0, QueShotLine.Direction.VERTICAL, 0.25f);
                addLine(1, QueShotLine.Direction.VERTICAL, 0.6666667f);
                addLine(0, QueShotLine.Direction.HORIZONTAL, 0.5f);
                addLine(2, QueShotLine.Direction.HORIZONTAL, 0.5f);
                return;
            case 1:
                addLine(0, QueShotLine.Direction.HORIZONTAL, 0.6f);
                cutAreaEqualPart(0, 3, QueShotLine.Direction.VERTICAL);
                addLine(3, QueShotLine.Direction.VERTICAL, 0.5f);
                return;
            case 2:
                addLine(0, QueShotLine.Direction.VERTICAL, 0.4f);
                cutAreaEqualPart(0, 3, QueShotLine.Direction.HORIZONTAL);
                addLine(1, QueShotLine.Direction.HORIZONTAL, 0.5f);
                return;
            case 3:
                addLine(0, QueShotLine.Direction.VERTICAL, 0.4f);
                cutAreaEqualPart(1, 3, QueShotLine.Direction.HORIZONTAL);
                addLine(0, QueShotLine.Direction.HORIZONTAL, 0.5f);
                return;
            case 4:
                addLine(0, QueShotLine.Direction.HORIZONTAL, 0.75f);
                cutAreaEqualPart(1, 4, QueShotLine.Direction.VERTICAL);
                return;
            case 5:
                addLine(0, QueShotLine.Direction.HORIZONTAL, 0.25f);
                cutAreaEqualPart(0, 4, QueShotLine.Direction.VERTICAL);
                return;
            case 6:
                addLine(0, QueShotLine.Direction.VERTICAL, 0.75f);
                cutAreaEqualPart(1, 4, QueShotLine.Direction.HORIZONTAL);
                return;
            case 7:
                addLine(0, QueShotLine.Direction.VERTICAL, 0.25f);
                cutAreaEqualPart(0, 4, QueShotLine.Direction.HORIZONTAL);
                return;
            case 8:
                addLine(0, QueShotLine.Direction.HORIZONTAL, 0.25f);
                addLine(1, QueShotLine.Direction.HORIZONTAL, 0.6666667f);
                addLine(0, QueShotLine.Direction.VERTICAL, 0.5f);
                addLine(3, QueShotLine.Direction.VERTICAL, 0.5f);
                return;
            case 9:
                addLine(0, QueShotLine.Direction.HORIZONTAL, 0.4f);
                addLine(0, QueShotLine.Direction.VERTICAL, 0.5f);
                cutAreaEqualPart(2, 3, QueShotLine.Direction.VERTICAL);
                return;
            case 10:
                addCross(0, 0.33333334f);
                addLine(2, QueShotLine.Direction.HORIZONTAL, 0.5f);
                return;
            case 11:
                addCross(0, 0.6666667f);
                addLine(1, QueShotLine.Direction.HORIZONTAL, 0.5f);
                return;
            case 12:
                addCross(0, 0.33333334f, 0.6666667f);
                addLine(3, QueShotLine.Direction.HORIZONTAL, 0.5f);
                return;
            case 13:
                addCross(0, 0.6666667f, 0.33333334f);
                addLine(0, QueShotLine.Direction.HORIZONTAL, 0.5f);
                return;
            case 14:
                cutSpiral(0);
                return;
            case 15:
                cutAreaEqualPart(0, 5, QueShotLine.Direction.HORIZONTAL);
                return;
            case 16:
                cutAreaEqualPart(0, 5, QueShotLine.Direction.VERTICAL);
                return;
            default:
                cutAreaEqualPart(0, 5, QueShotLine.Direction.HORIZONTAL);
                return;
        }
    }

    public QueShotLayout clone(QueShotLayout quShotLayout) {
        return new FiveStraightLayout((StraightCollageLayout) quShotLayout, true);
    }
}
