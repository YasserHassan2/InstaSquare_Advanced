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
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.github.flipzeus.FlipDirection;
import com.github.flipzeus.ImageFlipper;
import com.isseiaoki.simplecropview.CropImageView;
import com.que.shot.R;

public class RotateFragment extends DialogFragment  {
    private static final String TAG = "CropFragments";
    private Bitmap bitmap;
    public CropImageView crop_image_view;
    public OnCropPhoto onCropPhoto;
    private RelativeLayout relative_layout_loading;

    public interface OnCropPhoto {
        void finishCrop(Bitmap bitmap);
    }

    public void setBitmap(Bitmap bitmap2) {
        this.bitmap = bitmap2;
    }

    public static RotateFragment show(@NonNull AppCompatActivity appCompatActivity, OnCropPhoto onCropPhoto2, Bitmap bitmap2) {
        RotateFragment cropDialogFragment = new RotateFragment();
        cropDialogFragment.setBitmap(bitmap2);
        cropDialogFragment.setOnCropPhoto(onCropPhoto2);
        cropDialogFragment.show(appCompatActivity.getSupportFragmentManager(), TAG);
        return cropDialogFragment;
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
    }

    public void setOnCropPhoto(OnCropPhoto onCropPhoto2) {
        this.onCropPhoto = onCropPhoto2;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setRetainInstance(true);
    }

    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(-1, -1);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(-16777216));
        }
    }

    @SuppressLint("WrongConstant")
    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        getDialog().getWindow().requestFeature(1);
        getDialog().getWindow().setFlags(1024, 1024);
        View inflate = layoutInflater.inflate(R.layout.fragment_rotate, viewGroup, false);
        this.crop_image_view = inflate.findViewById(R.id.crop_image_view);
        this.crop_image_view.setCropMode(CropImageView.CropMode.FREE);
        inflate.findViewById(R.id.relativeLayoutRotate).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                RotateFragment.this.crop_image_view.rotateImage(CropImageView.RotateDegrees.ROTATE_90D);
            }
        });
        inflate.findViewById(R.id.relativeLayoutVFlip).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ImageFlipper.flip(crop_image_view, FlipDirection.VERTICAL);
            }
        });

        inflate.findViewById(R.id.relativeLayoutHFlip).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ImageFlipper.flip(crop_image_view, FlipDirection.HORIZONTAL);
            }
        });

        inflate.findViewById(R.id.imageViewSaveRotate).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                new OnSaveCrop().execute(new Void[0]);
            }
        });

        this.relative_layout_loading = inflate.findViewById(R.id.relative_layout_loading);
        this.relative_layout_loading.setVisibility(View.GONE);
        inflate.findViewById(R.id.imageViewCloseRotate).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                RotateFragment.this.dismiss();
            }
        });
        return inflate;
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.crop_image_view = view.findViewById(R.id.crop_image_view);
        this.crop_image_view.setImageBitmap(this.bitmap);
    }


    class OnSaveCrop extends AsyncTask<Void, Bitmap, Bitmap> {
        OnSaveCrop() {
        }

        public void onPreExecute() {
            RotateFragment.this.mLoading(true);
        }

        public Bitmap doInBackground(Void... voidArr) {
            return RotateFragment.this.crop_image_view.getCroppedBitmap();
        }

        public void onPostExecute(Bitmap bitmap) {
            RotateFragment.this.mLoading(false);
            RotateFragment.this.onCropPhoto.finishCrop(bitmap);
            RotateFragment.this.dismiss();
        }
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
