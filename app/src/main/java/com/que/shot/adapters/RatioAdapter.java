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
import com.que.shot.assets.BrushColorAsset;

import java.util.ArrayList;
import java.util.List;

public class RatioAdapter extends RecyclerView.Adapter<RatioAdapter.ViewHolder> {
    public RatioListener ratioListener;
    private Context context;
    public int selectedIndex;
    public List<SquareView> squareViews = new ArrayList();

    public interface RatioListener {
        void onBackgroundSelected(SquareView squareView);
    }

    public RatioAdapter(Context context2, RatioListener ratioListener) {
        this.context = context2;
        this.ratioListener = ratioListener;
        List<String> lstColorForBrush = BrushColorAsset.listColorBrush();
        for (int i = 0; i < lstColorForBrush.size() - 2; i++) {
            this.squareViews.add(new SquareView(Color.parseColor(lstColorForBrush.get(i)), "", true));
        }
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_square, viewGroup, false));
    }

    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        SquareView squareView = this.squareViews.get(i);
        if (squareView.isColor) {
            viewHolder.squareView.setBackgroundColor(squareView.drawableId);
        } else {
            viewHolder.squareView.setBackgroundResource(squareView.drawableId);
        }
        if (this.selectedIndex == i) {
            viewHolder.wrapSquareView.setBackground(this.context.getDrawable(R.drawable.border_view));
        } else {
            viewHolder.wrapSquareView.setBackground(this.context.getDrawable(R.drawable.border_transparent_view));
        }
    }

    public int getItemCount() {
        return this.squareViews.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public View squareView;

        public ConstraintLayout wrapSquareView;

        public ViewHolder(View view) {
            super(view);
            this.squareView = view.findViewById(R.id.square_view);
            this.wrapSquareView = view.findViewById(R.id.constraint_layout_wrapper_square_view);
            view.setOnClickListener(this);
        }

        public void onClick(View view) {
            RatioAdapter.this.selectedIndex = getAdapterPosition();
            RatioAdapter.this.ratioListener.onBackgroundSelected((SquareView) RatioAdapter.this.squareViews.get(RatioAdapter.this.selectedIndex));
            RatioAdapter.this.notifyDataSetChanged();
        }
    }

    public class SquareView {
        public int drawableId;
        public boolean isColor;
        public String text;

        SquareView(int i, String str) {
            this.drawableId = i;
            this.text = str;
        }

        SquareView(int i, String str, boolean z) {
            this.drawableId = i;
            this.text = str;
            this.isColor = z;
        }
    }
}
