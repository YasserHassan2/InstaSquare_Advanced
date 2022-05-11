package com.que.shot.adapters;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.que.shot.R;
import com.que.shot.grid.QueShotLayout;
import com.que.shot.queshot.QueShotSquareView;
import com.que.shot.layer.slant.NumberSlantLayout;
import com.que.shot.layer.straight.NumberStraightLayout;

import java.util.ArrayList;
import java.util.List;

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.GridViewHolder> {
    private List<Bitmap> bitmapData = new ArrayList();
    private List<QueShotLayout> layoutData = new ArrayList();

    public OnItemClickListener onItemClickListener;

    public int selectedIndex = 0;

    public interface OnItemClickListener {
        void onItemClick(QueShotLayout puzzleLayout, int i);
    }

    public GridViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new GridViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_grid, viewGroup, false));
    }

    public void setSelectedIndex(int i) {
        this.selectedIndex = i;
    }

    public void onBindViewHolder(GridViewHolder collageViewHolder, final int i) {
        final QueShotLayout collageLayout = this.layoutData.get(i);
        collageViewHolder.square_collage_view.setNeedDrawLine(true);
        collageViewHolder.square_collage_view.setNeedDrawOuterLine(true);
        collageViewHolder.square_collage_view.setTouchEnable(false);
        collageViewHolder.square_collage_view.setLineSize(6);
        collageViewHolder.square_collage_view.setQueShotLayout(collageLayout);
        if (this.selectedIndex == i) {
            collageViewHolder.square_collage_view.setBackgroundColor(Color.parseColor("#555555"));
        } else {
            collageViewHolder.square_collage_view.setBackgroundColor(0);
        }
        collageViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (GridAdapter.this.onItemClickListener != null) {
                    int i = 0;
                    if (collageLayout instanceof NumberSlantLayout) {
                        i = ((NumberSlantLayout) collageLayout).getTheme();
                    } else if (collageLayout instanceof NumberStraightLayout) {
                        i = ((NumberStraightLayout) collageLayout).getTheme();
                    }
                    GridAdapter.this.onItemClickListener.onItemClick(collageLayout, i);
                }
                GridAdapter.this.selectedIndex = i;
                GridAdapter.this.notifyDataSetChanged();
            }
        });
        if (this.bitmapData != null) {
            int size = this.bitmapData.size();
            if (collageLayout.getAreaCount() > size) {
                for (int i2 = 0; i2 < collageLayout.getAreaCount(); i2++) {
                    collageViewHolder.square_collage_view.addQuShotCollage(this.bitmapData.get(i2 % size));
                }
                return;
            }
            collageViewHolder.square_collage_view.addPieces(this.bitmapData);
        }
    }

    public int getItemCount() {
        if (this.layoutData == null) {
            return 0;
        }
        return this.layoutData.size();
    }

    public void refreshData(List<QueShotLayout> list, List<Bitmap> list2) {
        this.layoutData = list;
        this.bitmapData = list2;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener2) {
        this.onItemClickListener = onItemClickListener2;
    }

    public static class GridViewHolder extends RecyclerView.ViewHolder {
        QueShotSquareView square_collage_view;

        public GridViewHolder(View view) {
            super(view);
            this.square_collage_view = view.findViewById(R.id.squareCollageView);
        }
    }
}
