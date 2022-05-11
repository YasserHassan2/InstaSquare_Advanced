package com.que.shot.layer.straight;

import com.que.shot.grid.QueShotLayout;
import com.que.shot.grid.QueShotLine;

public class SevenStraightLayout extends NumberStraightLayout {
    public int getThemeCount() {
        return 9;
    }

    public SevenStraightLayout(StraightCollageLayout straightPuzzleLayout, boolean z) {
        super(straightPuzzleLayout, z);
    }

    public SevenStraightLayout(int i) {
        super(i);
    }

    public void layout() {
        switch (this.theme) {
            case 0:
                addLine(0, QueShotLine.Direction.HORIZONTAL, 0.5f);
                cutAreaEqualPart(1, 4, QueShotLine.Direction.VERTICAL);
                cutAreaEqualPart(0, 3, QueShotLine.Direction.VERTICAL);
                return;
            case 1:
                addLine(0, QueShotLine.Direction.VERTICAL, 0.5f);
                cutAreaEqualPart(1, 4, QueShotLine.Direction.HORIZONTAL);
                cutAreaEqualPart(0, 3, QueShotLine.Direction.HORIZONTAL);
                return;
            case 2:
                addLine(0, QueShotLine.Direction.HORIZONTAL, 0.5f);
                cutAreaEqualPart(1, 1, 2);
                return;
            case 3:
                addLine(0, QueShotLine.Direction.HORIZONTAL, 0.6666667f);
                cutAreaEqualPart(1, 3, QueShotLine.Direction.VERTICAL);
                addCross(0, 0.5f);
                return;
            case 4:
                cutAreaEqualPart(0, 3, QueShotLine.Direction.VERTICAL);
                cutAreaEqualPart(2, 3, QueShotLine.Direction.HORIZONTAL);
                cutAreaEqualPart(0, 3, QueShotLine.Direction.HORIZONTAL);
                return;
            case 5:
                addLine(0, QueShotLine.Direction.HORIZONTAL, 0.6666667f);
                addLine(1, QueShotLine.Direction.VERTICAL, 0.75f);
                addLine(0, QueShotLine.Direction.HORIZONTAL, 0.5f);
                addLine(1, QueShotLine.Direction.VERTICAL, 0.4f);
                cutAreaEqualPart(0, 3, QueShotLine.Direction.VERTICAL);
                return;
            case 6:
                addLine(0, QueShotLine.Direction.VERTICAL, 0.6666667f);
                addLine(1, QueShotLine.Direction.HORIZONTAL, 0.75f);
                addLine(0, QueShotLine.Direction.VERTICAL, 0.5f);
                addLine(1, QueShotLine.Direction.HORIZONTAL, 0.4f);
                cutAreaEqualPart(0, 3, QueShotLine.Direction.HORIZONTAL);
                return;
            case 7:
                addLine(0, QueShotLine.Direction.VERTICAL, 0.25f);
                addLine(1, QueShotLine.Direction.VERTICAL, 0.6666667f);
                addLine(2, QueShotLine.Direction.HORIZONTAL, 0.5f);
                addLine(1, QueShotLine.Direction.HORIZONTAL, 0.75f);
                addLine(1, QueShotLine.Direction.HORIZONTAL, 0.33333334f);
                addLine(0, QueShotLine.Direction.HORIZONTAL, 0.5f);
                return;
            case 8:
                addLine(0, QueShotLine.Direction.HORIZONTAL, 0.25f);
                addLine(1, QueShotLine.Direction.HORIZONTAL, 0.6666667f);
                cutAreaEqualPart(2, 3, QueShotLine.Direction.VERTICAL);
                cutAreaEqualPart(0, 3, QueShotLine.Direction.VERTICAL);
                return;
            default:
                return;
        }
    }

    public QueShotLayout clone(QueShotLayout quShotLayout) {
        return new SevenStraightLayout((StraightCollageLayout) quShotLayout, true);
    }
}
