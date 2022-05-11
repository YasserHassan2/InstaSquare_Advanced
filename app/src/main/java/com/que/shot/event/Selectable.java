package com.que.shot.event;

import com.que.shot.entity.Photo;

public interface Selectable {

    int getSelectedItemCount();

    boolean isSelected(Photo photo);

}
