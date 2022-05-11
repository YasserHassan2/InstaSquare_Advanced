package com.que.shot.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.que.shot.R;
import com.que.shot.custom.RatioCustom;
import com.steelkiwi.cropiwa.AspectRatio;

import java.util.Arrays;
import java.util.List;

public class AspectAdapter extends RecyclerView.Adapter<AspectAdapter.ViewHolder> {
    public int lastSelectedView;
    public OnNewSelectedListener listener;
    public List<RatioCustom> ratios;
    public RatioCustom selectedRatio;

    public interface OnNewSelectedListener {
        void onNewAspectRatioSelected(AspectRatio aspectRatio);
    }

    public AspectAdapter() {
        this.ratios = Arrays.asList(new RatioCustom(10, 10, R.drawable.ic_crop_free),
                new RatioCustom(1, 1, R.drawable.ic_instagram1_1),
                new RatioCustom(4, 3, R.drawable.ic_facebook4_3),
                new RatioCustom(3, 4, R.drawable.ic_crop_3_4),
                new RatioCustom(5, 4, R.drawable.ic_crop_5_4),
                new RatioCustom(4, 5, R.drawable.ic_instagram4_5),
                new RatioCustom(3, 2, R.drawable.ic_crop_3_2),
                new RatioCustom(2, 3, R.drawable.ic_pinterest2_3),
                new RatioCustom(9, 16, R.drawable.ic_crop_9_16),
                new RatioCustom(16, 9, R.drawable.ic_crop_16_9));
        this.selectedRatio = this.ratios.get(0);
    }

    public AspectAdapter(boolean z) {
        this.ratios = Arrays.asList(new RatioCustom(1, 1, R.drawable.ic_instagram1_1),
                new RatioCustom(4, 3, R.drawable.ic_facebook4_3),
                new RatioCustom(3, 4, R.drawable.ic_crop_3_4),
                new RatioCustom(5, 4, R.drawable.ic_crop_5_4),
                new RatioCustom(4, 5, R.drawable.ic_instagram4_5),
                new RatioCustom(3, 2, R.drawable.ic_crop_3_2),
                new RatioCustom(2, 3, R.drawable.ic_pinterest2_3),
                new RatioCustom(9, 16, R.drawable.ic_crop_9_16),
                new RatioCustom(16, 9, R.drawable.ic_crop_16_9));
        this.selectedRatio = this.ratios.get(0);
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_crop, viewGroup, false));
    }

    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        RatioCustom aspectRatioCustom = this.ratios.get(i);
        if (i == this.lastSelectedView) {
            viewHolder.ratioView.setImageResource(aspectRatioCustom.getSelectedIem());
            viewHolder.relativeLayoutCropper.setBackgroundResource(R.drawable.background_crop);
        } else {
            viewHolder.ratioView.setImageResource(aspectRatioCustom.getSelectedIem());
            viewHolder.relativeLayoutCropper.setBackgroundResource(R.drawable.border_transparent_view);
        }
    }


    public void setLastSelectedView(int i) {
        this.lastSelectedView = i;
    }

    public int getItemCount() {
        return this.ratios.size();
    }

    public void setListener(OnNewSelectedListener onNewSelectedListener) {
        this.listener = onNewSelectedListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView ratioView;
        public RelativeLayout relativeLayoutCropper;

        public ViewHolder(View view) {
            super(view);
            this.ratioView = view.findViewById(R.id.image_view_aspect_ratio);
            this.relativeLayoutCropper = view.findViewById(R.id.relativeLayoutCropper);
            this.ratioView.setOnClickListener(this);
        }

        public void onClick(View view) {
            if (AspectAdapter.this.lastSelectedView != getAdapterPosition()) {
                AspectAdapter.this.selectedRatio = AspectAdapter.this.ratios.get(getAdapterPosition());
                AspectAdapter.this.lastSelectedView = getAdapterPosition();
                if (AspectAdapter.this.listener != null) {
                    AspectAdapter.this.listener.onNewAspectRatioSelected(AspectAdapter.this.selectedRatio);
                }
                AspectAdapter.this.notifyDataSetChanged();
            }
        }
    }
}
