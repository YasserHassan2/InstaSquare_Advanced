package com.que.shot.layer.straight;

import com.que.shot.grid.QueShotLayout;
import com.que.shot.grid.QueShotLine;

public class OneStraightLayout extends NumberStraightLayout {
    public int getThemeCount() {
        return 6;
    }

    public OneStraightLayout(StraightCollageLayout straightPuzzleLayout, boolean z) {
        super(straightPuzzleLayout, z);
    }

    public OneStraightLayout(int i) {
        super(i);
    }

    public void layout() {
        switch (this.theme) {
            case 0:
                addLine(0, QueShotLine.Direction.HORIZONTAL, 0.5f);
                return;
            case 1:
                addLine(0, QueShotLine.Direction.VERTICAL, 0.5f);
                return;
            case 2:
                addCross(0, 0.5f);
                return;
            case 3:
                cutAreaEqualPart(0, 2, 1);
                return;
            case 4:
                cutAreaEqualPart(0, 1, 2);
                return;
            case 5:
                cutAreaEqualPart(0, 2, 2);
                return;
            default:
                addLine(0, QueShotLine.Direction.HORIZONTAL, 0.5f);
                return;
        }
    }

    public QueShotLayout clone(QueShotLayout quShotLayout) {
        return new OneStraightLayout((StraightCollageLayout) quShotLayout, true);
    }
}
