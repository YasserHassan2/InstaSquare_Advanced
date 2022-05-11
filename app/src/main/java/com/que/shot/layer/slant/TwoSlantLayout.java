package com.que.shot.layer.slant;

import com.que.shot.grid.QueShotLayout;
import com.que.shot.grid.QueShotLine;

public class TwoSlantLayout extends NumberSlantLayout {
    public int getThemeCount() {
        return 2;
    }


    public TwoSlantLayout(SlantCollageLayout slantPuzzleLayout, boolean z) {
        super(slantPuzzleLayout, z);
    }

    public TwoSlantLayout(int i) {
        super(i);
    }

    public void layout() {
        switch (this.theme) {
            case 0:
                addLine(0, QueShotLine.Direction.HORIZONTAL, 0.56f, 0.44f);
                return;
            case 1:
                addLine(0, QueShotLine.Direction.VERTICAL, 0.56f, 0.44f);
                return;
            default:
                return;
        }
    }

    public QueShotLayout clone(QueShotLayout quShotLayout) {
        return new TwoSlantLayout((SlantCollageLayout) quShotLayout, true);
    }
}
