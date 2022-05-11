package com.que.shot.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.que.shot.R;
import com.que.shot.module.Module;

import java.util.ArrayList;
import java.util.List;

public class QueShotToolsAdapter extends RecyclerView.Adapter<QueShotToolsAdapter.ViewHolder> {

    public OnQuShotItemSelected onQuShotItemSelected;
    public List<ModuleModel> toolModelArrayList = new ArrayList<>();

    public interface OnQuShotItemSelected {
        void onQuShotToolSelected(Module module);
    }

    public QueShotToolsAdapter(OnQuShotItemSelected onItemSelected) {
        this.onQuShotItemSelected = onItemSelected;
        this.toolModelArrayList.add(new ModuleModel("Crop", R.drawable.ic_crop, Module.CROP));
        this.toolModelArrayList.add(new ModuleModel("Filter", R.drawable.ic_filter, Module.FILTER));
        this.toolModelArrayList.add(new ModuleModel("Adjust", R.drawable.ic_adjust, Module.ADJUST));
        this.toolModelArrayList.add(new ModuleModel("Overlay", R.drawable.ic_overlay, Module.OVERLAY));
        this.toolModelArrayList.add(new ModuleModel("Ratio", R.drawable.ic_ratio, Module.RATIO));
        this.toolModelArrayList.add(new ModuleModel("Text", R.drawable.ic_text, Module.TEXT));
        this.toolModelArrayList.add(new ModuleModel("Sticker", R.drawable.ic_sticker, Module.EMOJI));
        this.toolModelArrayList.add(new ModuleModel("Blur", R.drawable.ic_blur, Module.BLUR));
        this.toolModelArrayList.add(new ModuleModel("Rotate", R.drawable.ic_rotate, Module.ROTATE));
        this.toolModelArrayList.add(new ModuleModel("s-Splash", R.drawable.ic_splash_square, Module.SQUARESPLASH));
        this.toolModelArrayList.add(new ModuleModel("Draw", R.drawable.ic_paint, Module.DRAW));
        this.toolModelArrayList.add(new ModuleModel("Frame", R.drawable.ic_frame, Module.BACKGROUND));
        this.toolModelArrayList.add(new ModuleModel("Splash", R.drawable.ic_splash, Module.SPLASH));
        this.toolModelArrayList.add(new ModuleModel("s-Blur", R.drawable.ic_blur_square, Module.SQUAEBLUR));
    }

    class ModuleModel {
        public int toolIcon;
        public String toolName;
        public Module toolType;

        ModuleModel(String str, int i, Module toolType) {
            this.toolName = str;
            this.toolIcon = i;
            this.toolType = toolType;
        }
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_editing, viewGroup, false));
    }

    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ModuleModel toolModel = this.toolModelArrayList.get(i);
        viewHolder.text_view_tool_name.setText(toolModel.toolName);
        viewHolder.image_view_tool_icon.setImageResource(toolModel.toolIcon);
    }

    public int getItemCount() {
        return this.toolModelArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image_view_tool_icon;
        TextView text_view_tool_name;
        RelativeLayout relative_layout_wrapper_tool;

        ViewHolder(View view) {
            super(view);
            this.image_view_tool_icon = view.findViewById(R.id.image_view_tool_icon);
            this.text_view_tool_name = view.findViewById(R.id.text_view_tool_name);
            this.relative_layout_wrapper_tool = view.findViewById(R.id.relative_layout_wrapper_tool);
            this.relative_layout_wrapper_tool.setOnClickListener(view1 ->
                    QueShotToolsAdapter.this.onQuShotItemSelected.onQuShotToolSelected((QueShotToolsAdapter.this.toolModelArrayList.get(ViewHolder.this.getLayoutPosition())).toolType));
        }
    }
}
