package com.que.shot.event;

import android.view.MotionEvent;

import com.que.shot.queshot.QueShotStickerView;

public class DeleteIconEvent implements StickerIconEvent {
    public void onActionDown(QueShotStickerView paramStickerView, MotionEvent paramMotionEvent) {
    }

    public void onActionMove(QueShotStickerView paramStickerView, MotionEvent paramMotionEvent) {
    }

    public void onActionUp(QueShotStickerView paramStickerView, MotionEvent paramMotionEvent) {
        paramStickerView.removeCurrentSticker();
    }
}
