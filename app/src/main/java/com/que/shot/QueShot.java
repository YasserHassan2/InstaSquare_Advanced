package com.que.shot;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.StrictMode;

import com.google.android.gms.ads.MobileAds;

public class QueShot extends Application {
    private static QueShot queShot;

    public void onCreate() {
        super.onCreate();
        queShot = this;
        MobileAds.initialize(this,  getResources().getString(R.string.admob_app_id));
        if (Build.VERSION.SDK_INT >= 26) {
            try {
                StrictMode.class.getMethod("disableDeathOnFileUriExposure", new Class[0]).invoke( null, new Object[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Context getContext() {
        return queShot.getContext();
    }

}
