package com.que.shot.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.que.shot.R;
import com.que.shot.constants.Constants;
import com.que.shot.queshot.QueShotSplashSticker;
import com.que.shot.assets.StickersAsset;
import com.que.shot.utils.SystemUtil;
import com.github.siyamed.shapeimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

public class SplashSquareAdapter extends RecyclerView.Adapter<SplashSquareAdapter.ViewHolder> {
    private int borderWidth;
    private Context context;

    public int selectedSquareIndex;

    public SplashChangeListener splashChangeListener;

    public List<SplashItem> splashList = new ArrayList();

    public interface SplashChangeListener {
        void onSelected(QueShotSplashSticker splashSticker);
    }

    public SplashSquareAdapter(Context context2, SplashChangeListener splashChangeListener, boolean z) {
        this.context = context2;
        this.splashChangeListener = splashChangeListener;
        this.borderWidth = SystemUtil.dpToPx(context2, Constants.BORDER_WIDTH);
        if (z) {
            this.splashList.add(new SplashItem(new QueShotSplashSticker(StickersAsset.loadBitmapFromAssets(context2, "square/mask/m1.png"), StickersAsset.loadBitmapFromAssets(context2, "square/frame/f1.png")), R.drawable.num_1));
            this.splashList.add(new SplashItem(new QueShotSplashSticker(StickersAsset.loadBitmapFromAssets(context2, "square/mask/m2.png"), StickersAsset.loadBitmapFromAssets(context2, "square/frame/f2.png")), R.drawable.num_2));
            this.splashList.add(new SplashItem(new QueShotSplashSticker(StickersAsset.loadBitmapFromAssets(context2, "square/mask/m3.png"), StickersAsset.loadBitmapFromAssets(context2, "square/frame/f3.png")), R.drawable.num_3));
            this.splashList.add(new SplashItem(new QueShotSplashSticker(StickersAsset.loadBitmapFromAssets(context2, "square/mask/m4.png"), StickersAsset.loadBitmapFromAssets(context2, "square/frame/f4.png")), R.drawable.num_4));
            this.splashList.add(new SplashItem(new QueShotSplashSticker(StickersAsset.loadBitmapFromAssets(context2, "square/mask/m5.png"), StickersAsset.loadBitmapFromAssets(context2, "square/frame/f5.png")), R.drawable.num_5));
            this.splashList.add(new SplashItem(new QueShotSplashSticker(StickersAsset.loadBitmapFromAssets(context2, "square/mask/m6.png"), StickersAsset.loadBitmapFromAssets(context2, "square/frame/f6.png")), R.drawable.num_6));
            this.splashList.add(new SplashItem(new QueShotSplashSticker(StickersAsset.loadBitmapFromAssets(context2, "square/mask/m7.png"), StickersAsset.loadBitmapFromAssets(context2, "square/frame/f7.png")), R.drawable.num_7));
            this.splashList.add(new SplashItem(new QueShotSplashSticker(StickersAsset.loadBitmapFromAssets(context2, "square/mask/m8.png"), StickersAsset.loadBitmapFromAssets(context2, "square/frame/f8.png")), R.drawable.num_8));
            this.splashList.add(new SplashItem(new QueShotSplashSticker(StickersAsset.loadBitmapFromAssets(context2, "square/mask/m9.png"), StickersAsset.loadBitmapFromAssets(context2, "square/frame/f9.png")), R.drawable.num_9));
            this.splashList.add(new SplashItem(new QueShotSplashSticker(StickersAsset.loadBitmapFromAssets(context2, "square/mask/m10.png"), StickersAsset.loadBitmapFromAssets(context2, "square/frame/f10.png")), R.drawable.num_10));
            this.splashList.add(new SplashItem(new QueShotSplashSticker(StickersAsset.loadBitmapFromAssets(context2, "square/mask/m11.png"), StickersAsset.loadBitmapFromAssets(context2, "square/frame/f11.png")), R.drawable.num_11));
            this.splashList.add(new SplashItem(new QueShotSplashSticker(StickersAsset.loadBitmapFromAssets(context2, "square/mask/m12.png"), StickersAsset.loadBitmapFromAssets(context2, "square/frame/f12.png")), R.drawable.num_12));
            this.splashList.add(new SplashItem(new QueShotSplashSticker(StickersAsset.loadBitmapFromAssets(context2, "square/mask/m13.png"), StickersAsset.loadBitmapFromAssets(context2, "square/frame/f13.png")), R.drawable.num_13));
            this.splashList.add(new SplashItem(new QueShotSplashSticker(StickersAsset.loadBitmapFromAssets(context2, "square/mask/m14.png"), StickersAsset.loadBitmapFromAssets(context2, "square/frame/f14.png")), R.drawable.num_14));
            this.splashList.add(new SplashItem(new QueShotSplashSticker(StickersAsset.loadBitmapFromAssets(context2, "square/mask/m15.png"), StickersAsset.loadBitmapFromAssets(context2, "square/frame/f15.png")), R.drawable.num_15));
            this.splashList.add(new SplashItem(new QueShotSplashSticker(StickersAsset.loadBitmapFromAssets(context2, "square/mask/m16.png"), StickersAsset.loadBitmapFromAssets(context2, "square/frame/f16.png")), R.drawable.num_16));
            this.splashList.add(new SplashItem(new QueShotSplashSticker(StickersAsset.loadBitmapFromAssets(context2, "square/mask/m17.png"), StickersAsset.loadBitmapFromAssets(context2, "square/frame/f17.png")), R.drawable.num_17));
            this.splashList.add(new SplashItem(new QueShotSplashSticker(StickersAsset.loadBitmapFromAssets(context2, "square/mask/m18.png"), StickersAsset.loadBitmapFromAssets(context2, "square/frame/f18.png")), R.drawable.num_18));
            this.splashList.add(new SplashItem(new QueShotSplashSticker(StickersAsset.loadBitmapFromAssets(context2, "square/mask/m19.png"), StickersAsset.loadBitmapFromAssets(context2, "square/frame/f19.png")), R.drawable.num_19));
            this.splashList.add(new SplashItem(new QueShotSplashSticker(StickersAsset.loadBitmapFromAssets(context2, "square/mask/m20.png"), StickersAsset.loadBitmapFromAssets(context2, "square/frame/f20.png")), R.drawable.num_20));
            this.splashList.add(new SplashItem(new QueShotSplashSticker(StickersAsset.loadBitmapFromAssets(context2, "square/mask/m21.png"), StickersAsset.loadBitmapFromAssets(context2, "square/frame/f21.png")), R.drawable.num_21));
            this.splashList.add(new SplashItem(new QueShotSplashSticker(StickersAsset.loadBitmapFromAssets(context2, "square/mask/m22.png"), StickersAsset.loadBitmapFromAssets(context2, "square/frame/f22.png")), R.drawable.num_22));
            this.splashList.add(new SplashItem(new QueShotSplashSticker(StickersAsset.loadBitmapFromAssets(context2, "square/mask/m23.png"), StickersAsset.loadBitmapFromAssets(context2, "square/frame/f23.png")), R.drawable.num_23));
            this.splashList.add(new SplashItem(new QueShotSplashSticker(StickersAsset.loadBitmapFromAssets(context2, "square/mask/m24.png"), StickersAsset.loadBitmapFromAssets(context2, "square/frame/f24.png")), R.drawable.num_24));
            this.splashList.add(new SplashItem(new QueShotSplashSticker(StickersAsset.loadBitmapFromAssets(context2, "square/mask/m25.png"), StickersAsset.loadBitmapFromAssets(context2, "square/frame/f25.png")), R.drawable.num_25));
            this.splashList.add(new SplashItem(new QueShotSplashSticker(StickersAsset.loadBitmapFromAssets(context2, "square/mask/m26.png"), StickersAsset.loadBitmapFromAssets(context2, "square/frame/f26.png")), R.drawable.num_26));
            this.splashList.add(new SplashItem(new QueShotSplashSticker(StickersAsset.loadBitmapFromAssets(context2, "square/mask/m27.png"), StickersAsset.loadBitmapFromAssets(context2, "square/frame/f27.png")), R.drawable.num_27));
            this.splashList.add(new SplashItem(new QueShotSplashSticker(StickersAsset.loadBitmapFromAssets(context2, "square/mask/m28.png"), StickersAsset.loadBitmapFromAssets(context2, "square/frame/f28.png")), R.drawable.num_28));
            this.splashList.add(new SplashItem(new QueShotSplashSticker(StickersAsset.loadBitmapFromAssets(context2, "square/mask/m29.png"), StickersAsset.loadBitmapFromAssets(context2, "square/frame/f29.png")), R.drawable.num_29));
            this.splashList.add(new SplashItem(new QueShotSplashSticker(StickersAsset.loadBitmapFromAssets(context2, "square/mask/m30.png"), StickersAsset.loadBitmapFromAssets(context2, "square/frame/f30.png")), R.drawable.num_30));
            this.splashList.add(new SplashItem(new QueShotSplashSticker(StickersAsset.loadBitmapFromAssets(context2, "square/mask/m31.png"), StickersAsset.loadBitmapFromAssets(context2, "square/frame/f31.png")), R.drawable.num_31));
            this.splashList.add(new SplashItem(new QueShotSplashSticker(StickersAsset.loadBitmapFromAssets(context2, "square/mask/m32.png"), StickersAsset.loadBitmapFromAssets(context2, "square/frame/f32.png")), R.drawable.num_32));
            this.splashList.add(new SplashItem(new QueShotSplashSticker(StickersAsset.loadBitmapFromAssets(context2, "square/mask/m33.png"), StickersAsset.loadBitmapFromAssets(context2, "square/frame/f33.png")), R.drawable.num_33));
            this.splashList.add(new SplashItem(new QueShotSplashSticker(StickersAsset.loadBitmapFromAssets(context2, "square/mask/m34.png"), StickersAsset.loadBitmapFromAssets(context2, "square/frame/f34.png")), R.drawable.num_34));
            this.splashList.add(new SplashItem(new QueShotSplashSticker(StickersAsset.loadBitmapFromAssets(context2, "square/mask/m35.png"), StickersAsset.loadBitmapFromAssets(context2, "square/frame/f35.png")), R.drawable.num_35));

            return;
        }
        this.splashList.add(new SplashItem(new QueShotSplashSticker(StickersAsset.loadBitmapFromAssets(context2, "square/mask/m1.png"), StickersAsset.loadBitmapFromAssets(context2, "square/frame/f1.png")), R.drawable.num_1));
        this.splashList.add(new SplashItem(new QueShotSplashSticker(StickersAsset.loadBitmapFromAssets(context2, "square/mask/m2.png"), StickersAsset.loadBitmapFromAssets(context2, "square/frame/f2.png")), R.drawable.num_2));
        this.splashList.add(new SplashItem(new QueShotSplashSticker(StickersAsset.loadBitmapFromAssets(context2, "square/mask/m3.png"), StickersAsset.loadBitmapFromAssets(context2, "square/frame/f3.png")), R.drawable.num_3));
        this.splashList.add(new SplashItem(new QueShotSplashSticker(StickersAsset.loadBitmapFromAssets(context2, "square/mask/m4.png"), StickersAsset.loadBitmapFromAssets(context2, "square/frame/f4.png")), R.drawable.num_4));
        this.splashList.add(new SplashItem(new QueShotSplashSticker(StickersAsset.loadBitmapFromAssets(context2, "square/mask/m5.png"), StickersAsset.loadBitmapFromAssets(context2, "square/frame/f5.png")), R.drawable.num_5));
        this.splashList.add(new SplashItem(new QueShotSplashSticker(StickersAsset.loadBitmapFromAssets(context2, "square/mask/m6.png"), StickersAsset.loadBitmapFromAssets(context2, "square/frame/f6.png")), R.drawable.num_6));
        this.splashList.add(new SplashItem(new QueShotSplashSticker(StickersAsset.loadBitmapFromAssets(context2, "square/mask/m7.png"), StickersAsset.loadBitmapFromAssets(context2, "square/frame/f7.png")), R.drawable.num_7));
        this.splashList.add(new SplashItem(new QueShotSplashSticker(StickersAsset.loadBitmapFromAssets(context2, "square/mask/m8.png"), StickersAsset.loadBitmapFromAssets(context2, "square/frame/f8.png")), R.drawable.num_8));
        this.splashList.add(new SplashItem(new QueShotSplashSticker(StickersAsset.loadBitmapFromAssets(context2, "square/mask/m9.png"), StickersAsset.loadBitmapFromAssets(context2, "square/frame/f9.png")), R.drawable.num_9));
        this.splashList.add(new SplashItem(new QueShotSplashSticker(StickersAsset.loadBitmapFromAssets(context2, "square/mask/m10.png"), StickersAsset.loadBitmapFromAssets(context2, "square/frame/f10.png")), R.drawable.num_10));
        this.splashList.add(new SplashItem(new QueShotSplashSticker(StickersAsset.loadBitmapFromAssets(context2, "square/mask/m11.png"), StickersAsset.loadBitmapFromAssets(context2, "square/frame/f11.png")), R.drawable.num_11));
        this.splashList.add(new SplashItem(new QueShotSplashSticker(StickersAsset.loadBitmapFromAssets(context2, "square/mask/m12.png"), StickersAsset.loadBitmapFromAssets(context2, "square/frame/f12.png")), R.drawable.num_12));
        this.splashList.add(new SplashItem(new QueShotSplashSticker(StickersAsset.loadBitmapFromAssets(context2, "square/mask/m13.png"), StickersAsset.loadBitmapFromAssets(context2, "square/frame/f13.png")), R.drawable.num_13));
        this.splashList.add(new SplashItem(new QueShotSplashSticker(StickersAsset.loadBitmapFromAssets(context2, "square/mask/m14.png"), StickersAsset.loadBitmapFromAssets(context2, "square/frame/f14.png")), R.drawable.num_14));
        this.splashList.add(new SplashItem(new QueShotSplashSticker(StickersAsset.loadBitmapFromAssets(context2, "square/mask/m15.png"), StickersAsset.loadBitmapFromAssets(context2, "square/frame/f15.png")), R.drawable.num_15));
        this.splashList.add(new SplashItem(new QueShotSplashSticker(StickersAsset.loadBitmapFromAssets(context2, "square/mask/m16.png"), StickersAsset.loadBitmapFromAssets(context2, "square/frame/f16.png")), R.drawable.num_16));
        this.splashList.add(new SplashItem(new QueShotSplashSticker(StickersAsset.loadBitmapFromAssets(context2, "square/mask/m17.png"), StickersAsset.loadBitmapFromAssets(context2, "square/frame/f17.png")), R.drawable.num_17));
        this.splashList.add(new SplashItem(new QueShotSplashSticker(StickersAsset.loadBitmapFromAssets(context2, "square/mask/m18.png"), StickersAsset.loadBitmapFromAssets(context2, "square/frame/f18.png")), R.drawable.num_18));
        this.splashList.add(new SplashItem(new QueShotSplashSticker(StickersAsset.loadBitmapFromAssets(context2, "square/mask/m19.png"), StickersAsset.loadBitmapFromAssets(context2, "square/frame/f19.png")), R.drawable.num_19));
        this.splashList.add(new SplashItem(new QueShotSplashSticker(StickersAsset.loadBitmapFromAssets(context2, "square/mask/m20.png"), StickersAsset.loadBitmapFromAssets(context2, "square/frame/f20.png")), R.drawable.num_20));
        this.splashList.add(new SplashItem(new QueShotSplashSticker(StickersAsset.loadBitmapFromAssets(context2, "square/mask/m21.png"), StickersAsset.loadBitmapFromAssets(context2, "square/frame/f21.png")), R.drawable.num_21));
        this.splashList.add(new SplashItem(new QueShotSplashSticker(StickersAsset.loadBitmapFromAssets(context2, "square/mask/m22.png"), StickersAsset.loadBitmapFromAssets(context2, "square/frame/f22.png")), R.drawable.num_22));
        this.splashList.add(new SplashItem(new QueShotSplashSticker(StickersAsset.loadBitmapFromAssets(context2, "square/mask/m23.png"), StickersAsset.loadBitmapFromAssets(context2, "square/frame/f23.png")), R.drawable.num_23));
        this.splashList.add(new SplashItem(new QueShotSplashSticker(StickersAsset.loadBitmapFromAssets(context2, "square/mask/m24.png"), StickersAsset.loadBitmapFromAssets(context2, "square/frame/f24.png")), R.drawable.num_24));
        this.splashList.add(new SplashItem(new QueShotSplashSticker(StickersAsset.loadBitmapFromAssets(context2, "square/mask/m25.png"), StickersAsset.loadBitmapFromAssets(context2, "square/frame/f25.png")), R.drawable.num_25));
        this.splashList.add(new SplashItem(new QueShotSplashSticker(StickersAsset.loadBitmapFromAssets(context2, "square/mask/m26.png"), StickersAsset.loadBitmapFromAssets(context2, "square/frame/f26.png")), R.drawable.num_26));
        this.splashList.add(new SplashItem(new QueShotSplashSticker(StickersAsset.loadBitmapFromAssets(context2, "square/mask/m27.png"), StickersAsset.loadBitmapFromAssets(context2, "square/frame/f27.png")), R.drawable.num_27));
        this.splashList.add(new SplashItem(new QueShotSplashSticker(StickersAsset.loadBitmapFromAssets(context2, "square/mask/m28.png"), StickersAsset.loadBitmapFromAssets(context2, "square/frame/f28.png")), R.drawable.num_28));
        this.splashList.add(new SplashItem(new QueShotSplashSticker(StickersAsset.loadBitmapFromAssets(context2, "square/mask/m29.png"), StickersAsset.loadBitmapFromAssets(context2, "square/frame/f29.png")), R.drawable.num_29));
        this.splashList.add(new SplashItem(new QueShotSplashSticker(StickersAsset.loadBitmapFromAssets(context2, "square/mask/m30.png"), StickersAsset.loadBitmapFromAssets(context2, "square/frame/f30.png")), R.drawable.num_30));
        this.splashList.add(new SplashItem(new QueShotSplashSticker(StickersAsset.loadBitmapFromAssets(context2, "square/mask/m31.png"), StickersAsset.loadBitmapFromAssets(context2, "square/frame/f31.png")), R.drawable.num_31));
        this.splashList.add(new SplashItem(new QueShotSplashSticker(StickersAsset.loadBitmapFromAssets(context2, "square/mask/m32.png"), StickersAsset.loadBitmapFromAssets(context2, "square/frame/f32.png")), R.drawable.num_32));
        this.splashList.add(new SplashItem(new QueShotSplashSticker(StickersAsset.loadBitmapFromAssets(context2, "square/mask/m33.png"), StickersAsset.loadBitmapFromAssets(context2, "square/frame/f33.png")), R.drawable.num_33));
        this.splashList.add(new SplashItem(new QueShotSplashSticker(StickersAsset.loadBitmapFromAssets(context2, "square/mask/m34.png"), StickersAsset.loadBitmapFromAssets(context2, "square/frame/f34.png")), R.drawable.num_34));
        this.splashList.add(new SplashItem(new QueShotSplashSticker(StickersAsset.loadBitmapFromAssets(context2, "square/mask/m35.png"), StickersAsset.loadBitmapFromAssets(context2, "square/frame/f35.png")), R.drawable.num_35));
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_splash, viewGroup, false));
    }

    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.splash.setImageResource(this.splashList.get(i).drawableId);
        if (this.selectedSquareIndex == i) {
            viewHolder.splash.setBorderColor(ContextCompat.getColor(context, R.color.colorAccent));
            viewHolder.splash.setBorderWidth(this.borderWidth);
            return;
        }
        viewHolder.splash.setBorderColor(0);
        viewHolder.splash.setBorderWidth(0);
    }

    public int getItemCount() {
        return this.splashList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public RoundedImageView splash;

        public ViewHolder(View view) {
            super(view);
            this.splash = view.findViewById(R.id.round_image_view_splash_item);
            view.setOnClickListener(this);
        }

        public void onClick(View view) {
            SplashSquareAdapter.this.selectedSquareIndex = getAdapterPosition();
            if (SplashSquareAdapter.this.selectedSquareIndex < 0) {
                SplashSquareAdapter.this.selectedSquareIndex = 0;
            }
            if (SplashSquareAdapter.this.selectedSquareIndex >= SplashSquareAdapter.this.splashList.size()) {
                SplashSquareAdapter.this.selectedSquareIndex = SplashSquareAdapter.this.splashList.size() - 1;
            }
            SplashSquareAdapter.this.splashChangeListener.onSelected((SplashSquareAdapter.this.splashList.get(SplashSquareAdapter.this.selectedSquareIndex)).splashSticker);
            SplashSquareAdapter.this.notifyDataSetChanged();
        }
    }

    class SplashItem {
        int drawableId;
        QueShotSplashSticker splashSticker;

        SplashItem(QueShotSplashSticker splashSticker2, int i) {
            this.splashSticker = splashSticker2;
            this.drawableId = i;
        }
    }
}
