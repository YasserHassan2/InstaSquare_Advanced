package com.que.shot.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.que.shot.R;
import com.que.shot.listener.AdjustListener;
import com.que.shot.queshot.QueShotEditor;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class AdjustAdapter extends RecyclerView.Adapter<AdjustAdapter.ViewHolder> {
    public String ADJUST = " @adjust brightness 0 @adjust contrast 1 @adjust saturation 1 @adjust sharpen 0 @adjust exposure 0 @adjust hue 0 ";

    public AdjustListener adjustListener;
    private Context context;
    public List<AdjustModel> adjustModelList;
    public int selectedIndex = 0;

    public class AdjustModel {
        String code;
        Drawable icon;
        public int index;
        public float intensity;
        public float maxValue;
        public float minValue;
        public String name;
        public float originValue;
        public float seekbarIntensity = 0.5f;

        AdjustModel(int index, String name, String code, Drawable icon, float minValue, float originValue, float maxValue) {
            this.index = index;
            this.name = name;
            this.code = code;
            this.icon = icon;
            this.minValue = minValue;
            this.originValue = originValue;
            this.maxValue = maxValue;
        }

        public void setSeekBarIntensity(QueShotEditor photoEditor, float mFloat, boolean mBoolean) {
            if (photoEditor != null) {
                this.seekbarIntensity = mFloat;
                this.intensity = calcIntensity(mFloat);
                photoEditor.setFilterIntensityForIndex(this.intensity, this.index, mBoolean);
            }
        }


        public float calcIntensity(float f) {
            if (f <= 0.0f) {
                return this.minValue;
            }
            if (f >= 1.0f) {
                return this.maxValue;
            }
            if (f <= 0.5f) {
                return this.minValue + ((this.originValue - this.minValue) * f * 2.0f);
            }
            return this.maxValue + ((this.originValue - this.maxValue) * (1.0f - f) * 2.0f);
        }
    }

    public AdjustAdapter(Context mContext, AdjustListener mAdjustListener) {
        this.context = mContext;
        this.adjustListener = mAdjustListener;
        init();
    }

    public void setSelectedAdjust(int i) {
        this.adjustListener.onAdjustSelected(this.adjustModelList.get(i));
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_adjust, viewGroup, false));
    }

    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.text_view_adjust_name.setText(this.adjustModelList.get(i).name);
        viewHolder.image_view_adjust_icon.setImageDrawable(this.selectedIndex != i ? this.adjustModelList.get(i).icon : this.adjustModelList.get(i).icon);
        if (this.selectedIndex == i) {
            viewHolder.text_view_adjust_name.setTextColor(ContextCompat.getColor(context, R.color.white));
            viewHolder.image_view_adjust_icon.setColorFilter(ContextCompat.getColor(context, R.color.white));

        } else {
            viewHolder.text_view_adjust_name.setTextColor(ContextCompat.getColor(context, R.color.tintCol));
            viewHolder.image_view_adjust_icon.setColorFilter(ContextCompat.getColor(context, R.color.tintCol));
        }
    }

    public int getItemCount() {
        return this.adjustModelList.size();
    }

    public String getFilterConfig() {
        String str = this.ADJUST;
        return MessageFormat.format(str,
                this.adjustModelList.get(0).originValue + "",
                this.adjustModelList.get(1).originValue + "",
                this.adjustModelList.get(2).originValue + "",
                this.adjustModelList.get(3).originValue + "",
                this.adjustModelList.get(4).originValue + "",
                Float.valueOf(this.adjustModelList.get(5).originValue) );
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image_view_adjust_icon;
        TextView text_view_adjust_name;

        ViewHolder(View view) {
            super(view);
            this.image_view_adjust_icon = view.findViewById(R.id.image_view_adjust_icon);
            this.text_view_adjust_name = view.findViewById(R.id.text_view_adjust_name);
            view.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    AdjustAdapter.this.selectedIndex = ViewHolder.this.getLayoutPosition();
                    AdjustAdapter.this.adjustListener.onAdjustSelected( AdjustAdapter.this.adjustModelList.get(AdjustAdapter.this.selectedIndex));
                    AdjustAdapter.this.notifyDataSetChanged();
                }
            });
        }
    }

    public AdjustModel getCurrentAdjustModel() {
        return this.adjustModelList.get(this.selectedIndex);
    }

    private void init() {
        this.adjustModelList = new ArrayList();
        this.adjustModelList.add(new AdjustModel(0, this.context.getString(R.string.brightness), "brightness", this.context.getDrawable(R.drawable.ic_brightness), -1.0f, 0.0f, 1.0f));
        this.adjustModelList.add(new AdjustModel(1, this.context.getString(R.string.contrast), "contrast", this.context.getDrawable(R.drawable.ic_contrast), 0.1f, 1.0f, 3.0f));
        this.adjustModelList.add(new AdjustModel(2, this.context.getString(R.string.saturation), "saturation", this.context.getDrawable(R.drawable.ic_saturation),  0.0f, 1.0f, 3.0f));
        this.adjustModelList.add(new AdjustModel(5, this.context.getString(R.string.hue), "hue", this.context.getDrawable(R.drawable.ic_hue), -2.0f, 0.0f, 2.0f));
        this.adjustModelList.add(new AdjustModel(3, this.context.getString(R.string.sharpen), "sharpen", this.context.getDrawable(R.drawable.ic_sharpen), -1.0f, 0.0f, 10.0f));
        this.adjustModelList.add(new AdjustModel(4, this.context.getString(R.string.exposure), "exposure", this.context.getDrawable(R.drawable.ic_exposure), -2.0f, 0.0f, 2.0f));
    }

}
