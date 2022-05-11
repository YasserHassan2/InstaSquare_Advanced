package com.que.shot.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.que.shot.R;
import com.que.shot.utils.FilterUtils;
import com.que.shot.queshot.QueShotSplashView;

public class SplashFragment extends DialogFragment {
    private static final String TAG = "SplashFragment";
    private ImageView image_view_background;
    private RelativeLayout relative_layout_loading;
    public Bitmap bitmap;
    private Bitmap blackAndWhiteBitmap;
    private Bitmap blurBitmap;
    private SeekBar seekbar_brush;
    public TextView image_view_draw;
    public LinearLayout linear_layout_draw;
    private FrameLayout frame_layout;
    public boolean isSplashView;
    private ImageView image_view_redo;
    public SplashListener splashListener;
    public QueShotSplashView quShotSplashView;
    private ImageView image_view_undo;
    private ViewGroup viewGroup;
    private ElegantNumberButton blur_number_button;

    public interface SplashListener {
        void onSaveSplash(Bitmap bitmap);
    }

    public void setQuShotSplashView(boolean z) {
        this.isSplashView = z;
    }

    public void setBitmap(Bitmap bitmap2) {
        this.bitmap = bitmap2;
    }

    public static SplashFragment show(@NonNull AppCompatActivity appCompatActivity, Bitmap bitmap2, Bitmap bitmap3, Bitmap bitmap4, SplashListener splashDialogListener2, boolean z) {
        SplashFragment splashDialog = new SplashFragment();
        splashDialog.setBlurBitmap(bitmap3);
        splashDialog.setBitmap(bitmap2);
        splashDialog.setBlackAndWhiteBitmap(bitmap4);
        splashDialog.setSplashListener(splashDialogListener2);
        splashDialog.setQuShotSplashView(z);
        splashDialog.show(appCompatActivity.getSupportFragmentManager(), TAG);
        return splashDialog;
    }

    public void setBlackAndWhiteBitmap(Bitmap bitmap2) {
        this.blackAndWhiteBitmap = bitmap2;
    }

    public void setBlurBitmap(Bitmap bitmap2) {
        this.blurBitmap = bitmap2;
    }

    public void setSplashListener(SplashListener splashDialogListener2) {
        this.splashListener = splashDialogListener2;
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
        View inflate = layoutInflater.inflate(R.layout.fragment_splash, viewGroup2, false);
        this.viewGroup = viewGroup2;
        this.relative_layout_loading = inflate.findViewById(R.id.relative_layout_loading);
        this.relative_layout_loading.setVisibility(View.GONE);
        this.image_view_background = inflate.findViewById(R.id.imageViewBackground);
        this.quShotSplashView = inflate.findViewById(R.id.splashView);
        this.linear_layout_draw = inflate.findViewById(R.id.linearLayoutSplash);
        this.linear_layout_draw.setVisibility(View.VISIBLE);
        this.frame_layout = inflate.findViewById(R.id.frameLayoutWrapper);
        this.image_view_undo = inflate.findViewById(R.id.imageViewUndo);
        this.image_view_undo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SplashFragment.this.quShotSplashView.undo();
            }
        });
        this.image_view_redo = inflate.findViewById(R.id.imageViewRedo);
        this.image_view_redo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SplashFragment.this.quShotSplashView.redo();
            }
        });
        this.seekbar_brush = inflate.findViewById(R.id.seekbarBrush);
        this.seekbar_brush.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                SplashFragment.this.quShotSplashView.setBrushSize(i + 25);
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                SplashFragment.this.quShotSplashView.updateBrush();
            }
        });

        this.image_view_background.setImageBitmap(this.bitmap);
        this.image_view_draw = inflate.findViewById(R.id.textViewTitle);
        this.blur_number_button = inflate.findViewById(R.id.blur_number_button);
        if (this.isSplashView) {
            this.quShotSplashView.setImageBitmap(this.blackAndWhiteBitmap);
            this.blur_number_button.setVisibility(View.GONE);
            this.image_view_draw.setText("SPLASH");
        } else {
            this.quShotSplashView.setImageBitmap(this.blurBitmap);
            this.image_view_draw.setText("BLUR");
            this.blur_number_button.setRange(0, 15);
        }
        this.blur_number_button.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            public void onValueChange(ElegantNumberButton elegantNumberButton, int i, int i2) {
                new LoadBlurBitmap((float) i2).execute(new Void[0]);
            }
        });
        this.quShotSplashView.refreshDrawableState();
        this.quShotSplashView.setLayerType(1, (Paint) null);
        this.quShotSplashView.setcSplashMode(5);
        inflate.findViewById(R.id.imageViewSaveSplash).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SplashFragment.this.splashListener.onSaveSplash(SplashFragment.this.quShotSplashView.getBitmap(SplashFragment.this.bitmap));
                SplashFragment.this.dismiss();
            }
        });
        inflate.findViewById(R.id.imageViewCloseSplash).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SplashFragment.this.dismiss();
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

    class LoadBlurBitmap extends AsyncTask<Void, Bitmap, Bitmap> {
        private float intensity;

        public LoadBlurBitmap(float f) {
            this.intensity = f;
        }

        public void onPreExecute() {
            SplashFragment.this.showLoading(true);
        }

        public Bitmap doInBackground(Void... voidArr) {
            return FilterUtils.getBlurImageFromBitmap(SplashFragment.this.bitmap, this.intensity);
        }

        public void onPostExecute(Bitmap bitmap) {
            SplashFragment.this.showLoading(false);
            SplashFragment.this.quShotSplashView.setImageBitmap(bitmap);
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
