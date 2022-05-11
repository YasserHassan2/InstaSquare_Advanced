package com.que.shot.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.que.shot.R;
import com.que.shot.adapters.SplashSquareAdapter;
import com.que.shot.queshot.QueShotSplashView;
import com.que.shot.assets.StickersAsset;
import com.que.shot.queshot.QueShotSplashSticker;
import com.que.shot.utils.FilterUtils;

public class SplashBlurSquareFragment extends DialogFragment implements SplashSquareAdapter.SplashChangeListener {
    private static final String TAG = "SplashBlurSquareFragment";
    private ImageView image_view_background;
    private RelativeLayout relative_layout_loading;
    public Bitmap bitmap;
    private Bitmap blackAndWhiteBitmap;
    private Bitmap blurBitmap;
    private QueShotSplashSticker blurSticker;
    private FrameLayout frame_layout;
    public boolean isSplashView;
    public RecyclerView recycler_view_splash;
    public TextView image_view_shape;
    public SplashDialogListener splashDialogListener;
    private QueShotSplashSticker splashSticker;
    public QueShotSplashView splashView;
    private ViewGroup viewGroup;
    private ElegantNumberButton blur_number_button;

    public interface SplashDialogListener {
        void onSaveSplash(Bitmap bitmap);
    }

    public void setSplashView(boolean z) {
        this.isSplashView = z;
    }

    public void setBitmap(Bitmap bitmap2) {
        this.bitmap = bitmap2;
    }

    public static SplashBlurSquareFragment show(@NonNull AppCompatActivity appCompatActivity, Bitmap bitmap2, Bitmap bitmap3, Bitmap bitmap4, SplashDialogListener splashDialogListener2, boolean z) {
        SplashBlurSquareFragment splashDialog = new SplashBlurSquareFragment();
        splashDialog.setBlurBitmap(bitmap3);
        splashDialog.setBitmap(bitmap2);
        splashDialog.setBlackAndWhiteBitmap(bitmap4);
        splashDialog.setSplashDialogListener(splashDialogListener2);
        splashDialog.setSplashView(z);
        splashDialog.show(appCompatActivity.getSupportFragmentManager(), TAG);
        return splashDialog;
    }

    public void setBlackAndWhiteBitmap(Bitmap bitmap2) {
        this.blackAndWhiteBitmap = bitmap2;
    }

    public void setBlurBitmap(Bitmap bitmap2) {
        this.blurBitmap = bitmap2;
    }

    public void setSplashDialogListener(SplashDialogListener splashDialogListener2) {
        this.splashDialogListener = splashDialogListener2;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setRetainInstance(true);
    }

    @SuppressLint("WrongConstant")
    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup2, @Nullable Bundle bundle) {
        getDialog().getWindow().requestFeature(1);
        getDialog().getWindow().setFlags(1024, 1024);
        View inflate = layoutInflater.inflate(R.layout.fragment_splash_square, viewGroup2, false);
        this.viewGroup = viewGroup2;
        this.relative_layout_loading = inflate.findViewById(R.id.relative_layout_loading);
        this.relative_layout_loading.setVisibility(View.GONE);
        this.image_view_background = inflate.findViewById(R.id.imageViewBackground);
        this.splashView = inflate.findViewById(R.id.splashView);
        this.frame_layout = inflate.findViewById(R.id.frame_layout);
        this.image_view_background.setImageBitmap(this.bitmap);
        this.image_view_shape = inflate.findViewById(R.id.textViewTitle);
        this.blur_number_button = inflate.findViewById(R.id.blur_number_button);
        if (this.isSplashView) {
            this.splashView.setImageBitmap(this.blackAndWhiteBitmap);
            this.blur_number_button.setVisibility(View.GONE);
            this.image_view_shape.setText("SPLASH SQUARE");
        } else {
            this.splashView.setImageBitmap(this.blurBitmap);
            this.image_view_shape.setText("BLUR SQUARE");
            this.blur_number_button.setRange(0, 15);
        }
        this.blur_number_button.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            public void onValueChange(ElegantNumberButton elegantNumberButton, int i, int i2) {
                new LoadBlurBitmap((float) i2).execute(new Void[0]);
            }
        });
        this.recycler_view_splash = inflate.findViewById(R.id.recyclerViewSplashSquare);
        this.recycler_view_splash.setLayoutManager(new LinearLayoutManager(getContext(), 0, false));
        this.recycler_view_splash.setHasFixedSize(true);
        this.recycler_view_splash.setAdapter(new SplashSquareAdapter(getContext(), this, this.isSplashView));
        if (this.isSplashView) {
            this.splashSticker = new QueShotSplashSticker(StickersAsset.loadBitmapFromAssets(getContext(), "square/mask/m1.png"), StickersAsset.loadBitmapFromAssets(getContext(), "square/frame/f1.png"));
            this.splashView.addSticker(this.splashSticker);
        } else {
            this.blurSticker = new QueShotSplashSticker(StickersAsset.loadBitmapFromAssets(getContext(), "square/mask/m1.png"), StickersAsset.loadBitmapFromAssets(getContext(), "square/frame/f1.png"));
            this.splashView.addSticker(this.blurSticker);
        }
        this.splashView.refreshDrawableState();
        this.splashView.setLayerType(2, null);
        this.image_view_shape.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SplashBlurSquareFragment.this.splashView.setcSplashMode(0);
                SplashBlurSquareFragment.this.recycler_view_splash.setVisibility(View.VISIBLE);
                SplashBlurSquareFragment.this.splashView.refreshDrawableState();
                SplashBlurSquareFragment.this.splashView.invalidate();
            }
        });
        inflate.findViewById(R.id.imageViewSaveSplash).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SplashBlurSquareFragment.this.splashDialogListener.onSaveSplash(SplashBlurSquareFragment.this.splashView.getBitmap(SplashBlurSquareFragment.this.bitmap));
                SplashBlurSquareFragment.this.dismiss();
            }
        });
        inflate.findViewById(R.id.imageViewCloseSplash).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SplashBlurSquareFragment.this.dismiss();
            }
        });
        return inflate;
    }


    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
    }

    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(-1, -1);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(-16777216));
        }
    }

    public void onDestroy() {
        super.onDestroy();
        this.splashView.getSticker().release();
        if (this.blurBitmap != null) {
            this.blurBitmap.recycle();
        }
        this.blurBitmap = null;
        if (this.blackAndWhiteBitmap != null) {
            this.blackAndWhiteBitmap.recycle();
        }
        this.blackAndWhiteBitmap = null;
        this.bitmap = null;
    }

    public void onSelected(QueShotSplashSticker splashSticker2) {
        this.splashView.addSticker(splashSticker2);
    }

    class LoadBlurBitmap extends AsyncTask<Void, Bitmap, Bitmap> {
        private float intensity;

        public LoadBlurBitmap(float f) {
            this.intensity = f;
        }

        public void onPreExecute() {
            SplashBlurSquareFragment.this.showLoading(true);
        }

        public Bitmap doInBackground(Void... voidArr) {
            return FilterUtils.getBlurImageFromBitmap(SplashBlurSquareFragment.this.bitmap, this.intensity);
        }

        public void onPostExecute(Bitmap bitmap) {
            SplashBlurSquareFragment.this.showLoading(false);
            SplashBlurSquareFragment.this.splashView.setImageBitmap(bitmap);
        }
    }

    public void showLoading(boolean z) {
        if (z) {
            getActivity().getWindow().setFlags(16, 16);
            this.relative_layout_loading.setVisibility(View.VISIBLE);
            return;
        }
        getActivity().getWindow().clearFlags(16);
        this.relative_layout_loading.setVisibility(View.GONE);
    }

}
