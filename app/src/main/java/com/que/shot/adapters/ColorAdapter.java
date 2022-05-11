package com.que.shot.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.que.shot.R;
import com.que.shot.listener.BrushColorListener;
import com.que.shot.assets.BrushColorAsset;

import java.util.List;

public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.ViewHolder> {
    public BrushColorListener brushColorListener;
    public List<String> colors = BrushColorAsset.listColorBrush();
    private Context context;
    public int selectedColorIndex;

    public ColorAdapter(Context context2, BrushColorListener brushColorListener2) {
        this.context = context2;
        this.brushColorListener = brushColorListener2;
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_color, viewGroup, false));
    }

    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.squareView.setBackgroundColor(Color.parseColor(this.colors.get(i)));
        if (this.selectedColorIndex == i) {
            viewHolder.constraint_layout_wrapper_square_view.setBackground(this.context.getDrawable(R.drawable.border_view));
        } else {
            viewHolder.constraint_layout_wrapper_square_view.setBackground(this.context.getDrawable(R.drawable.border_transparent_view));
        }
    }

    public int getItemCount() {
        return this.colors.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        View squareView;
        public ConstraintLayout constraint_layout_wrapper_square_view;

        ViewHolder(View view) {
            super(view);
            this.squareView = view.findViewById(R.id.square_view);
            this.constraint_layout_wrapper_square_view = view.findViewById(R.id.constraint_layout_wrapper_square_view);
            this.squareView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    ColorAdapter.this.selectedColorIndex = ViewHolder.this.getLayoutPosition();
                    ColorAdapter.this.brushColorListener.onColorChanged(ColorAdapter.this.colors.get(ColorAdapter.this.selectedColorIndex));
                    ColorAdapter.this.notifyDataSetChanged();
                }
            });

        }
    }

    public void setSelectedColorIndex(int i) {
        this.selectedColorIndex = i;
    }
}
