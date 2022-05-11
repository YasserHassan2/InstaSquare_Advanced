package com.que.shot.utils;

import com.que.shot.grid.QueShotLayout;
import com.que.shot.layer.slant.SlantLayoutHelper;
import com.que.shot.layer.straight.StraightLayoutHelper;

import java.util.ArrayList;
import java.util.List;

public class CollageUtils {

    private CollageUtils() {}

    public static List<QueShotLayout> getCollageLayouts(int i) {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(SlantLayoutHelper.getAllThemeLayout(i));
        arrayList.addAll(StraightLayoutHelper.getAllThemeLayout(i));
        return arrayList;
    }
}
