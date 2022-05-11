package com.que.shot.grid;

import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;

import java.util.List;

public interface QueShotArea {
    float bottom();

    float centerX();

    float centerY();

    boolean contains(float f, float f2);

    boolean contains(PointF pointF);

    boolean contains(QueShotLine line);

    Path getAreaPath();

    RectF getAreaRect();

    PointF getCenterPoint();

    PointF[] getHandleBarPoints(QueShotLine line);

    List<QueShotLine> getLines();

    float getPaddingBottom();

    float getPaddingLeft();

    float getPaddingRight();

    float getPaddingTop();

    float height();

    float left();

    float radian();

    float right();

    void setPadding(float f);

    void setPadding(float f, float f2, float f3, float f4);

    void setRadian(float f);

    float top();

    float width();
}
