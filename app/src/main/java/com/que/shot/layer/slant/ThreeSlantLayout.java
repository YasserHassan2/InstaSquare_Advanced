package com.que.shot.layer.slant;

import com.que.shot.grid.QueShotLayout;
import com.que.shot.grid.QueShotLine;

public class ThreeSlantLayout extends NumberSlantLayout {
    public int getThemeCount() {
        return 6;
    }

    public ThreeSlantLayout(SlantCollageLayout slantPuzzleLayout, boolean z) {
        super(slantPuzzleLayout, z);
        this.theme = ((NumberSlantLayout) slantPuzzleLayout).getTheme();
    }

    public ThreeSlantLayout(int i) {
        super(i);
    }

    public void layout() {
        switch (this.theme) {
            case 0:
                addLine(0, QueShotLine.Direction.HORIZONTAL, 0.5f);
                addLine(0, QueShotLine.Direction.VERTICAL, 0.56f, 0.44f);
                return;
            case 1:
                addLine(0, QueShotLine.Direction.HORIZONTAL, 0.5f);
                addLine(1, QueShotLine.Direction.VERTICAL, 0.56f, 0.44f);
                return;
            case 2:
                addLine(0, QueShotLine.Direction.VERTICAL, 0.5f);
                addLine(0, QueShotLine.Direction.HORIZONTAL, 0.56f, 0.44f);
                return;
            case 3:
                addLine(0, QueShotLine.Direction.VERTICAL, 0.5f);
                addLine(1, QueShotLine.Direction.HORIZONTAL, 0.56f, 0.44f);
                return;
            case 4:
                addLine(0, QueShotLine.Direction.HORIZONTAL, 0.44f, 0.56f);
                addLine(0, QueShotLine.Direction.VERTICAL, 0.56f, 0.44f);
                return;
            case 5:
                addLine(0, QueShotLine.Direction.VERTICAL, 0.56f, 0.44f);
                addLine(1, QueShotLine.Direction.HORIZONTAL, 0.44f, 0.56f);
                return;
            default:
                return;
        }
    }

    public QueShotLayout clone(QueShotLayout quShotLayout) {
        return new ThreeSlantLayout((SlantCollageLayout) quShotLayout, true);
    }
}
