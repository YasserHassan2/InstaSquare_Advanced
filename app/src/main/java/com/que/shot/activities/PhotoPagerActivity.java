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
import com.que.shot.fragment.ImagePagerFragment;
import com.que.shot.queshot.QueShotPickerView;
import com.que.shot.queshot.QueShotPreview;

import java.util.ArrayList;

public class PhotoPagerActivity extends AppCompatActivity {
    public ActionBar actionBar;
    private ImagePagerFragment fragment_photo_pager;
    public boolean setDelete;
    private AdView adView;
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_photo_pager);
        int intExtra = getIntent().getIntExtra(QueShotPreview.EXTRA_CURRENT_ITEM, 0);
        ArrayList<String> stringArrayListExtra = getIntent().getStringArrayListExtra(QueShotPreview.EXTRA_PHOTOS);
        this.setDelete = getIntent().getBooleanExtra(QueShotPreview.EXTRA_SHOW_DELETE, true);
        if (this.fragment_photo_pager == null) {
            this.fragment_photo_pager = (ImagePagerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_photo_pager);
        }
        this.fragment_photo_pager.setPhotos(stringArrayListExtra, intExtra);
        setSupportActionBar(findViewById(R.id.toolbar));
        this.actionBar = getSupportActionBar();
        if (this.actionBar != null) {
            this.actionBar.setDisplayHomeAsUpEnabled(true);
            if (Build.VERSION.SDK_INT >= 21) {
                this.actionBar.setElevation(25.0f);
            }
        }
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
        Intent intent = new Intent();
        intent.putExtra(QueShotPickerView.KEY_SELECTED_PHOTOS, this.fragment_photo_pager.getPaths());
        setResult(-1, intent);
        finish();
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        }
        onBackPressed();
        return true;
    }
}
