package com.que.shot.grid;

import android.graphics.PointF;

public interface QueShotLine {

    enum Direction {
        HORIZONTAL,
        VERTICAL
    }

    QueShotLine attachEndLine();

    QueShotLine attachStartLine();

    boolean contains(float f, float f2, float f3);

    Direction direction();

    PointF endPoint();

    float getEndRatio();

    float getStartRatio();

    float length();

    QueShotLine lowerLine();

    float maxX();

    float maxY();

    float minX();

    float minY();

    boolean move(float f, float f2);

    void offset(float f, float f2);

    void prepareMove();

    void setEndRatio(float f);

    void setLowerLine(QueShotLine line);

    void setStartRatio(float f);

    void setUpperLine(QueShotLine line);

    float slope();

    PointF startPoint();

    void update(float f, float f2);

    QueShotLine upperLine();
}
