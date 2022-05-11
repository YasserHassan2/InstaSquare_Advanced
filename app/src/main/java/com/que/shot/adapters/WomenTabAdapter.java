package com.que.shot.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.que.shot.R;


public class WomenTabAdapter extends RecyclerTabLayout.Adapter<WomenTabAdapter.ViewHolder> {
    private Context context;
    private PagerAdapter mAdapater = this.mViewPager.getAdapter();

    public WomenTabAdapter(ViewPager viewPager, Context context2) {
        super(viewPager);
        this.context = context2;
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_tab_sticker, viewGroup, false));
    }

    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        switch (i) {
            case 0:
                viewHolder.imageView.setImageDrawable(this.context.getDrawable(R.drawable.ic_crowns));
                break;
            case 1:
                viewHolder.imageView.setImageDrawable(this.context.getDrawable(R.drawable.ic_snsla));
                break;
            case 2:
                viewHolder.imageView.setImageDrawable(this.context.getDrawable(R.drawable.ic_hala9at));
                break;
            case 3:
                viewHolder.imageView.setImageDrawable(this.context.getDrawable(R.drawable.ic_flow));
                break;
            case 4:
                viewHolder.imageView.setImageDrawable(this.context.getDrawable(R.drawable.ic_glasses));
                break;
            case 5:
                viewHolder.imageView.setImageDrawable(this.context.getDrawable(R.drawable.ic_chap));
                break;
            case 6:
                viewHolder.imageView.setImageDrawable(this.context.getDrawable(R.drawable.ic_hairs));
                break;
            case 7:
                viewHolder.imageView.setImageDrawable(this.context.getDrawable(R.drawable.ic_smil));
                break;
            case 8:
                viewHolder.imageView.setImageDrawable(this.context.getDrawable(R.drawable.ic_hjban));
                break;
            case 9:
                viewHolder.imageView.setImageDrawable(this.context.getDrawable(R.drawable.ic_chfer));
                break;
            case 10:
                viewHolder.imageView.setImageDrawable(this.context.getDrawable(R.drawable.ic_zwaq));
                break;
        }
        viewHolder.imageView.setSelected(i == getCurrentIndicatorPosition());
    }

    public int getItemCount() {
        return this.mAdapater.getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            this.imageView = view.findViewById(R.id.image);
            view.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    WomenTabAdapter.this.getViewPager().setCurrentItem(ViewHolder.this.getAdapterPosition());
                }
            });
        }
    }
}
