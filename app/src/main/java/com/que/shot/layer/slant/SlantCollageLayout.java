package com.que.shot.layer.slant;

import android.graphics.RectF;
import android.util.Pair;

import com.que.shot.grid.QueShotArea;
import com.que.shot.grid.QueShotLayout;
import com.que.shot.grid.QueShotLine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class SlantCollageLayout implements QueShotLayout {
    private Comparator<SlantArea> areaComparator = new SlantArea.AreaComparator();
    private List<SlantArea> areas = new ArrayList();
    private RectF bounds;
    private int color = -1;
    private List<QueShotLine> lines = new ArrayList();
    private SlantArea outerArea;
    private List<QueShotLine> outerLines = new ArrayList(4);
    private float padding;
    private float radian;
    private ArrayList<Step> steps = new ArrayList<>();

    public abstract void layout();

    protected SlantCollageLayout() {
    }

    protected SlantCollageLayout(SlantCollageLayout slantCollageLayout, boolean z) {
        this.bounds = slantCollageLayout.getBounds();
        this.outerArea = (SlantArea) slantCollageLayout.getOuterArea();
        this.areas = slantCollageLayout.getAreas();
        this.lines = slantCollageLayout.getLines();
        this.outerLines = slantCollageLayout.getOuterLines();
        this.padding = slantCollageLayout.getPadding();
        this.radian = slantCollageLayout.getRadian();
        this.color = slantCollageLayout.getColor();
        this.areaComparator = slantCollageLayout.getAreaComparator();
        this.steps = slantCollageLayout.getSteps();
    }

    public RectF getBounds() {
        return this.bounds;
    }


    public List<SlantArea> getAreas() {
        return this.areas;
    }


    public Comparator<SlantArea> getAreaComparator() {
        return this.areaComparator;
    }


    public ArrayList<Step> getSteps() {
        return this.steps;
    }


    public void setOuterBounds(RectF rectF) {
        reset();
        this.bounds = rectF;
        CrossoverPointF crossoverPointF = new CrossoverPointF(rectF.left, rectF.top);
        CrossoverPointF crossoverPointF2 = new CrossoverPointF(rectF.right, rectF.top);
        CrossoverPointF crossoverPointF3 = new CrossoverPointF(rectF.left, rectF.bottom);
        CrossoverPointF crossoverPointF4 = new CrossoverPointF(rectF.right, rectF.bottom);
        SlantLine slantLine = new SlantLine(crossoverPointF, crossoverPointF3, QueShotLine.Direction.VERTICAL);
        SlantLine slantLine2 = new SlantLine(crossoverPointF, crossoverPointF2, QueShotLine.Direction.HORIZONTAL);
        SlantLine slantLine3 = new SlantLine(crossoverPointF2, crossoverPointF4, QueShotLine.Direction.VERTICAL);
        SlantLine slantLine4 = new SlantLine(crossoverPointF3, crossoverPointF4, QueShotLine.Direction.HORIZONTAL);
        this.outerLines.clear();
        this.outerLines.add(slantLine);
        this.outerLines.add(slantLine2);
        this.outerLines.add(slantLine3);
        this.outerLines.add(slantLine4);
        this.outerArea = new SlantArea();
        this.outerArea.lineLeft = slantLine;
        this.outerArea.lineTop = slantLine2;
        this.outerArea.lineRight = slantLine3;
        this.outerArea.lineBottom = slantLine4;
        this.outerArea.updateCornerPoints();
        this.areas.clear();
        this.areas.add(this.outerArea);
    }

    private void updateLineLimit() {
        for (int i = 0; i < this.lines.size(); i++) {
            QueShotLine line = this.lines.get(i);
            updateUpperLine(line);
            updateLowerLine(line);
        }
    }

    private void updateLowerLine(QueShotLine line) {
        for (int i = 0; i < this.lines.size(); i++) {
            QueShotLine line2 = this.lines.get(i);
            if (line2.direction() == line.direction() && line2.attachStartLine() == line.attachStartLine() && line2.attachEndLine() == line.attachEndLine()) {
                if (line2.direction() == QueShotLine.Direction.HORIZONTAL) {
                    if (line2.minY() > line.lowerLine().maxY() && line2.maxY() < line.minY()) {
                        line.setLowerLine(line2);
                    }
                } else if (line2.minX() > line.lowerLine().maxX() && line2.maxX() < line.minX()) {
                    line.setLowerLine(line2);
                }
            }
        }
    }

    private void updateUpperLine(QueShotLine line) {
        for (int i = 0; i < this.lines.size(); i++) {
            QueShotLine line2 = this.lines.get(i);
            if (line2.direction() == line.direction() && line2.attachStartLine() == line.attachStartLine() && line2.attachEndLine() == line.attachEndLine()) {
                if (line2.direction() == QueShotLine.Direction.HORIZONTAL) {
                    if (line2.maxY() < line.upperLine().minY() && line2.minY() > line.maxY()) {
                        line.setUpperLine(line2);
                    }
                } else if (line2.maxX() < line.upperLine().minX() && line2.minX() > line.maxX()) {
                    line.setUpperLine(line2);
                }
            }
        }
    }

    public int getAreaCount() {
        return this.areas.size();
    }

    public void reset() {
        this.lines.clear();
        this.areas.clear();
        this.areas.add(this.outerArea);
        this.steps.clear();
    }

    public void update() {
        for (int i = 0; i < this.lines.size(); i++) {
            this.lines.get(i).update(width(), height());
        }
        for (int i2 = 0; i2 < this.areas.size(); i2++) {
            this.areas.get(i2).updateCornerPoints();
        }
    }

    public void sortAreas() {
        Collections.sort(this.areas, this.areaComparator);
    }

    public float width() {
        if (this.outerArea == null) {
            return 0.0f;
        }
        return this.outerArea.width();
    }

    public float height() {
        if (this.outerArea == null) {
            return 0.0f;
        }
        return this.outerArea.height();
    }

    public List<QueShotLine> getOuterLines() {
        return this.outerLines;
    }

    public QueShotArea getOuterArea() {
        return this.outerArea;
    }

    public SlantArea getArea(int i) {
        sortAreas();
        return this.areas.get(i);
    }

    public List<QueShotLine> getLines() {
        return this.lines;
    }

    public void setPadding(float f) {
        this.padding = f;
        for (SlantArea padding2 : this.areas) {
            padding2.setPadding(f);
        }
        this.outerArea.lineLeft.startPoint().set(this.bounds.left + f, this.bounds.top + f);
        this.outerArea.lineLeft.endPoint().set(this.bounds.left + f, this.bounds.bottom - f);
        this.outerArea.lineRight.startPoint().set(this.bounds.right - f, this.bounds.top + f);
        this.outerArea.lineRight.endPoint().set(this.bounds.right - f, this.bounds.bottom - f);
        this.outerArea.updateCornerPoints();
        update();
    }

    public float getPadding() {
        return this.padding;
    }

    public float getRadian() {
        return this.radian;
    }

    public void setRadian(float f) {
        this.radian = f;
        for (SlantArea radian2 : this.areas) {
            radian2.setRadian(f);
        }
    }

    public int getColor() {
        return this.color;
    }

    public void setColor(int i) {
        this.color = i;
    }


    public List<SlantArea> addLine(int i, QueShotLine.Direction direction, float f) {
        return addLine(i, direction, f, f);
    }


    public List<SlantArea> addLine(int i, QueShotLine.Direction direction, float f, float f2) {
        SlantArea slantArea = this.areas.get(i);
        this.areas.remove(slantArea);
        SlantLine createLine = SlantUtils.createLine(slantArea, direction, f, f2);
        this.lines.add(createLine);
        List<SlantArea> cutAreaWith = SlantUtils.cutAreaWith(slantArea, createLine);
        this.areas.addAll(cutAreaWith);
        updateLineLimit();
        sortAreas();
        Step step = new Step();
        int i2 = 0;
        step.type = 0;
        if (direction != QueShotLine.Direction.HORIZONTAL) {
            i2 = 1;
        }
        step.direction = i2;
        step.position = i;
        this.steps.add(step);
        return cutAreaWith;
    }


    public void addCross(int i, float f, float f2, float f3, float f4) {
        SlantArea slantArea = this.areas.get(i);
        this.areas.remove(slantArea);
        SlantLine createLine = SlantUtils.createLine(slantArea, QueShotLine.Direction.HORIZONTAL, f, f2);
        SlantLine createLine2 = SlantUtils.createLine(slantArea, QueShotLine.Direction.VERTICAL, f3, f4);
        this.lines.add(createLine);
        this.lines.add(createLine2);
        this.areas.addAll(SlantUtils.cutAreaCross(slantArea, createLine, createLine2));
        sortAreas();
        Step step = new Step();
        step.type = 1;
        step.position = i;
        this.steps.add(step);
    }


    public void cutArea(int i, int i2, int i3) {
        SlantArea slantArea = this.areas.get(i);
        this.areas.remove(slantArea);
        Pair<List<SlantLine>, List<SlantArea>> cutAreaWith = SlantUtils.cutAreaWith(slantArea, i2, i3);
        this.lines.addAll((Collection) cutAreaWith.first);
        this.areas.addAll((Collection) cutAreaWith.second);
        updateLineLimit();
        sortAreas();
        Step step = new Step();
        step.type = 2;
        step.position = i;
        step.hSize = i2;
        step.vSize = i3;
        this.steps.add(step);
    }

    public Info generateInfo() {
        Info info = new Info();
        info.type = 1;
        info.padding = this.padding;
        info.radian = this.radian;
        info.color = this.color;
        info.steps = this.steps;
        ArrayList<LineInfo> arrayList = new ArrayList<>();
        for (QueShotLine lineInfo : this.lines) {
            arrayList.add(new LineInfo(lineInfo));
        }
        info.lineInfos = arrayList;
        info.lines = new ArrayList<>(this.lines);
        info.left = this.bounds.left;
        info.top = this.bounds.top;
        info.right = this.bounds.right;
        info.bottom = this.bounds.bottom;
        return info;
    }
}
