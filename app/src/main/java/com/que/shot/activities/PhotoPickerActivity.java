package com.que.shot.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.que.shot.R;
import com.que.shot.entity.Photo;
import com.que.shot.event.OnItemCheckListener;
import com.que.shot.fragment.ImagePagerFragment;
import com.que.shot.fragment.PhotoPickerFragment;
import com.que.shot.queshot.QueShotPickerView;

import java.util.ArrayList;

public class PhotoPickerActivity extends AppCompatActivity {
    private boolean forwardMain;
    private ImagePagerFragment imagePagerFragment;
    private int maxCount = 9;
    private ArrayList<String> originalPhotos = null;
    private PhotoPickerFragment pickerFragment;
    private boolean showGif = false;
    public PhotoPickerActivity getActivity() {
        return this;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
    private AdView adView;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        boolean booleanExtra = getIntent().getBooleanExtra(QueShotPickerView.EXTRA_SHOW_CAMERA, true);
        boolean booleanExtra2 = getIntent().getBooleanExtra(QueShotPickerView.EXTRA_SHOW_GIF, false);
        boolean booleanExtra3 = getIntent().getBooleanExtra(QueShotPickerView.EXTRA_PREVIEW_ENABLED, true);
        this.forwardMain = getIntent().getBooleanExtra(QueShotPickerView.MAIN_ACTIVITY, false);
        setShowGif(booleanExtra2);
        setContentView(R.layout.activity_photo_picker);
        setSupportActionBar(findViewById(R.id.toolbar));
        setTitle(getResources().getString(R.string.tap_to_select));
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        if (Build.VERSION.SDK_INT >= 21) {
            supportActionBar.setElevation(25.0f);
        }

        this.maxCount = getIntent().getIntExtra(QueShotPickerView.EXTRA_MAX_COUNT, 9);
        int intExtra = getIntent().getIntExtra(QueShotPickerView.EXTRA_GRID_COLUMN, 3);
        this.originalPhotos = getIntent().getStringArrayListExtra(QueShotPickerView.EXTRA_ORIGINAL_PHOTOS);
        this.pickerFragment = (PhotoPickerFragment) getSupportFragmentManager().findFragmentByTag("tag");
        if (this.pickerFragment == null) {
            this.pickerFragment = PhotoPickerFragment.newInstance(booleanExtra, booleanExtra2, booleanExtra3, intExtra, this.maxCount, this.originalPhotos);
            getSupportFragmentManager().beginTransaction().replace(R.id.container, this.pickerFragment, "tag").commit();
            getSupportFragmentManager().executePendingTransactions();
        }
        this.pickerFragment.getPhotoGridAdapter().setOnItemCheckListener(new OnItemCheckListener() {
            public final boolean onItemCheck(int i, Photo photo, int i2) {
                if (!forwardMain) {
                    Intent intent = new Intent(PhotoPickerActivity.this, QueShotEditorActivity.class);
                    intent.putExtra(QueShotPickerView.KEY_SELECTED_PHOTOS, photo.getPath());
                    startActivity(intent);
                    finish();
                    return true;
                }
                QueShotGridActivity.getQueShotGridActivityInstance().replaceCurrentPiece(photo.getPath());
                finish();
                return true;
            }
        });

        adsViews();
    }

    private void adsViews() {
        adView = new AdView(this);
        adView.setAdUnitId(getString(R.string.admob_banner_id));
        adView.setAdSize(getAdSize(this));
        ((FrameLayout) this.findViewById(R.id.bannerContainer)).addView(adView);
        AdRequest build = new AdRequest.Builder().build();
        adView.loadAd(build);
    }

    private static com.google.android.gms.ads.AdSize getAdSize(Activity activity) {
        Display defaultDisplay = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        defaultDisplay.getMetrics(displayMetrics);
        return com.google.android.gms.ads.AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(activity, (int) (((float) displayMetrics.widthPixels) / displayMetrics.density));
    }


    public void onBackPressed() {
        if (this.imagePagerFragment == null || !this.imagePagerFragment.isVisible()) {
            super.onBackPressed();
        } else if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        }
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void setShowGif(boolean z) {
        this.showGif = z;
    }
}
