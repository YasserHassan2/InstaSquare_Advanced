package com.que.shot.fragment;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.que.shot.R;
import com.que.shot.adapters.ColorAdapter;
import com.que.shot.listener.BrushColorListener;

public class ColorSplashFragment extends DialogFragment implements BrushColorListener {
    private Bitmap bitmap;
    private ImageView image_view_preview;
    private RecyclerView recycler_view_color_brush;

    public void setBitmap(Bitmap bitmap2) {
        this.bitmap = bitmap2;
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
    }

    public static ColorSplashFragment show(@NonNull AppCompatActivity appCompatActivity, Bitmap bitmap2) {
        ColorSplashFragment colorSplashDialog = new ColorSplashFragment();
        colorSplashDialog.setBitmap(bitmap2);
        colorSplashDialog.show(appCompatActivity.getSupportFragmentManager(), "ColorSplashFragment");
        return colorSplashDialog;
    }

    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(-1, -1);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(-16777216));
        }
    }

    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        getDialog().getWindow().requestFeature(1);
        getDialog().getWindow().setFlags(1024, 1024);
        View inflate = layoutInflater.inflate(R.layout.layout_splash, viewGroup, false);
        this.image_view_preview = inflate.findViewById(R.id.image_view_preview);
        this.recycler_view_color_brush = inflate.findViewById(R.id.recycler_view_color_brush);
        this.image_view_preview.setImageBitmap(this.bitmap);
        this.recycler_view_color_brush.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        this.recycler_view_color_brush.setHasFixedSize(true);
        this.recycler_view_color_brush.setAdapter(new ColorAdapter(getContext(), this));
        return inflate;
    }

    public void onColorChanged(String str) {
        Bitmap createBitmap = Bitmap.createBitmap(this.bitmap.getWidth(), this.bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0.0f);
        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        canvas.drawBitmap(this.bitmap, 0.0f, 0.0f, paint);
        this.image_view_preview.setImageBitmap(createBitmap);
    }
}
