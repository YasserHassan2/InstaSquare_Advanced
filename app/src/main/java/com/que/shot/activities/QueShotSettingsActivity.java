package com.que.shot.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.multidex.BuildConfig;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.que.shot.R;
import com.que.shot.dialog.RateDialog;

public class QueShotSettingsActivity extends AppCompatActivity {

    public LinearLayout relativeLayoutShare;
    public LinearLayout relativeLayoutRate;
    public LinearLayout relativeLayoutFeedBack;
    public LinearLayout relativeLayoutPrivacy;
    public LinearLayout relativeLayoutApps;
    private AdView adView;


    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_queshot_settings);
        adsViews();
        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Settings");

        this.relativeLayoutShare = findViewById(R.id.linearLayoutShare);
        this.relativeLayoutRate = findViewById(R.id.linearLayoutRate);
        this.relativeLayoutFeedBack = findViewById(R.id.linearLayoutFeedback);
        this.relativeLayoutPrivacy = findViewById(R.id.linearLayoutPrivacy);
        this.relativeLayoutApps = findViewById(R.id.linearLayoutApps);

        this.relativeLayoutShare.setOnClickListener(view -> {
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType("img_text/plain");
            intent.putExtra("android.intent.extra.SUBJECT", QueShotSettingsActivity.this.getString(R.string.app_name));
            intent.putExtra("android.intent.extra.TEXT", "https://play.google.com/store/apps/details?id=" + "com.all.in.one.photo.editing");
            QueShotSettingsActivity.this.startActivity(Intent.createChooser(intent, "Choose"));
        });

        this.relativeLayoutRate.setOnClickListener(view -> {
            new RateDialog(QueShotSettingsActivity.this, false).show();
        });

        this.relativeLayoutFeedBack.setOnClickListener(view -> {
            Intent intent = new Intent("android.intent.action.SEND");
            intent.putExtra("android.intent.extra.EMAIL", new String[]{getResources().getString(R.string.email_feedback)});
            intent.putExtra("android.intent.extra.SUBJECT", getResources().getString(R.string.app_name) + " feedback : ");
            intent.putExtra("android.intent.extra.TEXT", "");
            intent.setType("message/rfc822");
            QueShotSettingsActivity.this.startActivity(Intent.createChooser(intent, QueShotSettingsActivity.this.getString(R.string.choose_email) + " :"));
        });

        this.relativeLayoutPrivacy.setOnClickListener(view -> {
            try {
                QueShotSettingsActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(getString(R.string.policy_url))));
            } catch (Exception ignored) {
            }
        });

        this.relativeLayoutApps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("qq","moreApp");
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q=pub:" + getString(R.string.developer_name))));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/developer?id=" + getString(R.string.developer_name))));
                }
            }
        });

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


    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }


    @Override
    protected void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }

}
