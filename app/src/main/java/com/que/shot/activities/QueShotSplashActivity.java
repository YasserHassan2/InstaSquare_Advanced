 package com.que.shot.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.que.shot.R;

public class QueShotSplashActivity extends AppCompatActivity {
    private InterstitialAd interstitial;
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_splash);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.enter_from_bottom);
        Animation animation1 = AnimationUtils.loadAnimation(this, R.anim.enter_from_bottom_delay);
        findViewById(R.id.imageViewLogo).startAnimation(animation);
        findViewById(R.id.textViewTitle).startAnimation(animation1);
        findViewById(R.id.textViewSubTitle).startAnimation(animation1);
        interstitialAdMobAds();
        new Handler().postDelayed(() -> {
            QueShotSplashActivity.this.startToMainActivity();
            if (interstitial != null && interstitial.isLoaded()) {
                interstitial.show();
            }
        }, 2000);
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

    public void onDestroy() {
        super.onDestroy();
        Runtime.getRuntime().gc();
    }

    public void startToMainActivity() {
        startActivity(new Intent(this, QueShotHomeActivity.class));
        finish();
    }
}
