package com.que.shot.activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.que.shot.R;
import com.que.shot.picker.PermissionsUtils;
import com.que.shot.utils.AdsUtils;
import com.que.shot.dialog.RateDialog;
import com.que.shot.dialog.DetailsDialog;
import com.que.shot.queshot.QueShotPickerView;
import com.que.shot.picker.ImageCaptureManager;
import com.que.shot.preference.Preference;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.IOException;
import java.util.List;

public class QueShotHomeActivity extends QueShotBaseActivity {
    private ImageCaptureManager imageCaptureManager;
    private InterstitialAd interstitial;
    private ImageView ImageViewSettings;

    View.OnClickListener onClickListener = view -> {
        switch (view.getId()) {
            case R.id.relative_layout_edit_camera:
            case R.id.relatve_layout_camera:
                if (PermissionsUtils.checkCameraPermission(this) && PermissionsUtils.checkWriteStoragePermission(this)) {
                    takePhotoFromCamera();
                }
                return;
            case R.id.relative_layout_edit_collage:
            case R.id.relatve_layout_collage:
                Dexter.withContext(QueShotHomeActivity.this).withPermissions("android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE").withListener(new MultiplePermissionsListener() {
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                        if (multiplePermissionsReport.areAllPermissionsGranted()) {
                            Intent intent = new Intent(QueShotHomeActivity.this, GridPickerActivity.class);
                            intent.putExtra(GridPickerActivity.KEY_LIMIT_MAX_IMAGE, 9);
                            intent.putExtra(GridPickerActivity.KEY_LIMIT_MIN_IMAGE, 2);
                            startActivityForResult(intent, 1001);
                            if (interstitial != null && interstitial.isLoaded()) {
                                interstitial.show();
                            }
                        }
                        if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied()) {
                            DetailsDialog.showDetailsDialog(QueShotHomeActivity.this);
                        }
                    }

                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).withErrorListener(dexterError -> Toast.makeText(QueShotHomeActivity.this, "Error occurred! ", Toast.LENGTH_SHORT).show()).onSameThread().check();
                return;
            case R.id.relative_layout_edit_photo:
            case R.id.relatve_layout_edit:
                Dexter.withContext(QueShotHomeActivity.this).withPermissions("android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE").withListener(new MultiplePermissionsListener() {
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                        if (multiplePermissionsReport.areAllPermissionsGranted()) {
                            QueShotPickerView.builder().setPhotoCount(1).setPreviewEnabled(false).setShowCamera(false).start(QueShotHomeActivity.this);
                            if (interstitial != null && interstitial.isLoaded()) {
                                interstitial.show();
                            }
                        }
                        if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied()) {
                            DetailsDialog.showDetailsDialog(QueShotHomeActivity.this);
                        }
                    }

                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).withErrorListener(dexterError -> Toast.makeText(QueShotHomeActivity.this, "Error occurred! ", Toast.LENGTH_SHORT).show()).onSameThread().check();
                return;
            default:
        }

    };

    public void onCreate(Bundle bundle) {
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(bundle);
        setFullScreen();
        MobileAds.initialize(this,  getResources().getString(R.string.admob_app_id));
        interstitialAdMobAds();
        setContentView(R.layout.activity_home_queshot);
        (findViewById(R.id.relative_layout_edit_photo)).setOnClickListener(this.onClickListener);
        findViewById(R.id.relatve_layout_edit).setOnClickListener(this.onClickListener);
        (findViewById(R.id.relative_layout_edit_collage)).setOnClickListener(this.onClickListener);
        findViewById(R.id.relatve_layout_collage).setOnClickListener(this.onClickListener);
        (findViewById(R.id.relative_layout_edit_camera)).setOnClickListener(this.onClickListener);
        findViewById(R.id.relatve_layout_camera).setOnClickListener(this.onClickListener);
        this.imageCaptureManager = new ImageCaptureManager(this);
        this.ImageViewSettings = findViewById(R.id.imageViewSettings);
        this.ImageViewSettings.setOnClickListener(view -> {
            Intent intent = new Intent(QueShotHomeActivity.this, QueShotSettingsActivity.class);
            startActivity(intent);
        });
    }

    public void interstitialAdMobAds(){
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {}
        });

        interstitial = new InterstitialAd(this);
        interstitial.setAdUnitId(getResources().getString(R.string.admob_inter_id));
        interstitial.loadAd(new AdRequest.Builder().build());

        interstitial.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
            }

            @Override
            public void onAdClicked() {

                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the interstitial ad is closed.
            }
        });

    }

    public void onPostCreate(@Nullable Bundle bundle) {
        super.onPostCreate(bundle);
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        if (i2 != -1) {
            super.onActivityResult(i, i2, intent);
        } else if (i == 1) {
            if (this.imageCaptureManager == null) {
                this.imageCaptureManager = new ImageCaptureManager(this);
            }
            new Handler().post(new Runnable() {
                public final void run() {
                    imageCaptureManager.galleryAddPic();
                }
            });
            Intent intent2 = new Intent(getApplicationContext(), QueShotEditorActivity.class);
            intent2.putExtra(QueShotPickerView.KEY_SELECTED_PHOTOS, this.imageCaptureManager.getCurrentPhotoPath());
            startActivity(intent2);
        }
    }

    public void onResume() {
        super.onResume();
        if (AdsUtils.isNetworkAvailabel(getApplicationContext())) {
            if (!Preference.isRated(getApplicationContext()) && Preference.getCounter(getApplicationContext()) % 6 == 0) {
                new RateDialog(this, false).show();
            }
            Preference.increateCounter(getApplicationContext());
        }
    }

    public void takePhotoFromCamera() {
        try {
            startActivityForResult(this.imageCaptureManager.dispatchTakePictureIntent(), 1);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ActivityNotFoundException unused) {
        }
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public void onBackPressed() {
        finish();
    }

}
