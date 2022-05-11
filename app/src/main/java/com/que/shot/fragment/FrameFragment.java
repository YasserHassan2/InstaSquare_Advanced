package com.que.shot.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.que.shot.R;
import com.que.shot.adapters.FrameAdapter;
import com.que.shot.listener.BrushColorListener;
import com.que.shot.utils.FilterUtils;
import com.que.shot.utils.SystemUtil;

public class FrameFragment extends DialogFragment implements
        FrameAdapter.FrameListener, BrushColorListener {
    private static final String TAG = "FrameFragment";
    private RelativeLayout relative_layout_loading;
    private Bitmap bitmap;
    private Bitmap blurBitmap;
    public RatioSaveListener ratioSaveListener;
    public ImageView image_view_ratio;
    private ConstraintLayout constraint_layout_ratio;
    public RecyclerView recycler_view_background;
    public FrameLayout frame_layout_wrapper;

    public interface RatioSaveListener {
        void ratioSavedBitmap(Bitmap bitmap);
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public static FrameFragment show(@NonNull AppCompatActivity appCompatActivity, RatioSaveListener ratioSaveListener, Bitmap mBitmap, Bitmap iBitmap) {
        FrameFragment ratioFragment = new FrameFragment();
        ratioFragment.setBitmap(mBitmap);
        ratioFragment.setBlurBitmap(iBitmap);
        ratioFragment.setRatioSaveListener(ratioSaveListener);
        ratioFragment.show(appCompatActivity.getSupportFragmentManager(), TAG);
        return ratioFragment;
    }

    public void setBlurBitmap(Bitmap bitmap2) {
        this.blurBitmap = bitmap2;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setRetainInstance(true);
    }

    public void setRatioSaveListener(RatioSaveListener iRatioSaveListener) {
        this.ratioSaveListener = iRatioSaveListener;
    }

    @SuppressLint("WrongConstant")
    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        getDialog().getWindow().requestFeature(1);
        getDialog().getWindow().setFlags(1024, 1024);
        View inflate = layoutInflater.inflate(R.layout.fragment_frame, viewGroup, false);
        this.relative_layout_loading = inflate.findViewById(R.id.relative_layout_loading);
        this.relative_layout_loading.setVisibility(View.GONE);
        this.recycler_view_background = inflate.findViewById(R.id.recyclerViewFrame);
        this.recycler_view_background.setLayoutManager(new LinearLayoutManager(getContext(), 0, false));
        this.recycler_view_background.setAdapter(new FrameAdapter(getContext(), this));
        this.recycler_view_background.setVisibility(View.VISIBLE);

        ((SeekBar) inflate.findViewById(R.id.seekbarFrame)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                int dpToPx = SystemUtil.dpToPx(FrameFragment.this.getContext(), i);
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) FrameFragment.this.image_view_ratio.getLayoutParams();
                layoutParams.setMargins(dpToPx, dpToPx, dpToPx, dpToPx);
                FrameFragment.this.image_view_ratio.setLayoutParams(layoutParams);
            }
        });
        this.image_view_ratio = inflate.findViewById(R.id.imageViewFrame);
        this.image_view_ratio.setImageBitmap(this.bitmap);
        this.image_view_ratio.setAdjustViewBounds(true);
        Display defaultDisplay = getActivity().getWindowManager().getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getSize(point);
        this.constraint_layout_ratio = inflate.findViewById(R.id.constraintLayoutFrame);
        this.frame_layout_wrapper = inflate.findViewById(R.id.frameLayoutWrapper);
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(this.constraint_layout_ratio);
        ConstraintSet constraintSet2 = constraintSet;
        constraintSet2.connect(this.frame_layout_wrapper.getId(), 3, this.constraint_layout_ratio.getId(), 3, 0);
        constraintSet2.connect(this.frame_layout_wrapper.getId(), 1, this.constraint_layout_ratio.getId(), 1, 0);
        constraintSet2.connect(this.frame_layout_wrapper.getId(), 4, this.constraint_layout_ratio.getId(), 4, 0);
        constraintSet2.connect(this.frame_layout_wrapper.getId(), 2, this.constraint_layout_ratio.getId(), 2, 0);
        constraintSet.applyTo(this.constraint_layout_ratio);
        inflate.findViewById(R.id.imageViewCloseFrame).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                FrameFragment.this.dismiss();
            }
        });
        inflate.findViewById(R.id.imageViewSaveFrame).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                new SaveRatioView().execute(getBitmapFromView(FrameFragment.this.frame_layout_wrapper));
            }
        });
        return inflate;
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
    }

    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(-1, -1);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(-16777216));
        }
    }

    public void onStop() {
        super.onStop();
    }

    class SaveRatioView extends AsyncTask<Bitmap, Bitmap, Bitmap> {
        SaveRatioView() {
        }

        public void onPreExecute() {
            FrameFragment.this.mLoading(true);
        }

        public Bitmap doInBackground(Bitmap... bitmapArr) {
            Bitmap cloneBitmap = FilterUtils.cloneBitmap(bitmapArr[0]);
            bitmapArr[0].recycle();
            bitmapArr[0] = null;
            return cloneBitmap;
        }

        public void onPostExecute(Bitmap bitmap) {
            FrameFragment.this.mLoading(false);
            FrameFragment.this.ratioSaveListener.ratioSavedBitmap(bitmap);
            FrameFragment.this.dismiss();
        }
    }

    public void onBackgroundSelected(FrameAdapter.SquareView squareView) {
        if (squareView.isColor) {
            this.frame_layout_wrapper.setBackgroundColor(squareView.drawableId);
        }  else {
            this.frame_layout_wrapper.setBackgroundResource(squareView.drawableId);
        }
        this.frame_layout_wrapper.invalidate();
    }

    public void onDestroyView() {
        super.onDestroyView();
        if (this.blurBitmap != null) {
            this.blurBitmap.recycle();
            this.blurBitmap = null;
        }
        this.bitmap = null;
    }

    public void onColorChanged(String str) {
        this.image_view_ratio.setBackgroundColor(Color.parseColor(str));
        if (!str.equals("#00000000")) {
            int dpToPx = SystemUtil.dpToPx(getContext(), 35);
            this.image_view_ratio.setPadding(dpToPx, dpToPx, dpToPx, dpToPx);
            return;
        }
        this.image_view_ratio.setPadding(0, 0, 0, 0);
    }

    public Bitmap getBitmapFromView(View view)
    {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    public void mLoading(boolean z) {
        if (z) {
            getActivity().getWindow().setFlags(16, 16);
            this.relative_layout_loading.setVisibility(View.VISIBLE);
            return;
        }
        getActivity().getWindow().clearFlags(16);
        this.relative_layout_loading.setVisibility(View.GONE);
    }
}
