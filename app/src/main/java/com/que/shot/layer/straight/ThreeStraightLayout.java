package com.que.shot.layer.straight;

import com.que.shot.grid.QueShotLayout;
import com.que.shot.grid.QueShotLine;

public class ThreeStraightLayout extends NumberStraightLayout {
    public int getThemeCount() {
        return 6;
    }

    public ThreeStraightLayout(StraightCollageLayout straightPuzzleLayout, boolean z) {
        super(straightPuzzleLayout, z);
    }

    public ThreeStraightLayout(int i) {
        super(i);
    }

    public void layout() {
        switch (this.theme) {
            case 0:
                addLine(0, QueShotLine.Direction.HORIZONTAL, 0.5f);
                addLine(0, QueShotLine.Direction.VERTICAL, 0.5f);
                return;
            case 1:
                addLine(0, QueShotLine.Direction.HORIZONTAL, 0.5f);
                addLine(1, QueShotLine.Direction.VERTICAL, 0.5f);
                return;
            case 2:
                addLine(0, QueShotLine.Direction.VERTICAL, 0.5f);
                addLine(0, QueShotLine.Direction.HORIZONTAL, 0.5f);
                return;
            case 3:
                addLine(0, QueShotLine.Direction.VERTICAL, 0.5f);
                addLine(1, QueShotLine.Direction.HORIZONTAL, 0.5f);
                return;
            case 4:
                cutAreaEqualPart(0, 3, QueShotLine.Direction.HORIZONTAL);
                return;
            case 5:
                cutAreaEqualPart(0, 3, QueShotLine.Direction.VERTICAL);
                return;
            default:
                cutAreaEqualPart(0, 3, QueShotLine.Direction.HORIZONTAL);
                return;
        }
    }

    public QueShotLayout clone(QueShotLayout quShotLayout) {
        return new ThreeStraightLayout((StraightCollageLayout) quShotLayout, true);
    }
}
