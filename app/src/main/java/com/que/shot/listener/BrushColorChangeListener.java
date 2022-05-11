package com.que.shot.listener;

import com.que.shot.draw.BrushDrawingView;

public interface BrushColorChangeListener {
    void onStartDrawing();

    void onStopDrawing();

    void onViewAdd(BrushDrawingView brushDrawingView);

    void onViewRemoved(BrushDrawingView brushDrawingView);
}
