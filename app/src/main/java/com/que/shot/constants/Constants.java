package com.que.shot.constants;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.ArrayList;
import java.util.List;

public class Constants {

    public static int BORDER_WIDTH = 4;
    public static ArrayList<String> FORMAT_IMAGE = new ImageTypeList();
    public static String INSTAGRAM = "com.instagram.android";
    public static String MESSEGER = "com.facebook.orca";
    public static String TWITTER = "com.twitter.android";
    public static String WHATSAPP = "com.whatsapp";
    public static String FACEBOOK = "com.facebook.katana";
    public static String GMAIL = "com.google.android.gm";
    public static boolean Rate_app=true;

    public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public static boolean isAvailable(Intent intent, Context context) {
        final PackageManager mgr = context.getPackageManager();
        List<ResolveInfo> list =
                mgr.queryIntentActivities(intent,
                        PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    static class ImageTypeList extends ArrayList<String> {
        ImageTypeList() {
            add(".PNG");
            add(".JPEG");
            add(".jpg");
            add(".png");
            add(".jpeg");
            add(".JPG");
        }
    }
}
