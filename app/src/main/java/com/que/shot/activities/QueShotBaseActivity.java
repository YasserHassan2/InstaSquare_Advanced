package com.que.shot.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class QueShotBaseActivity extends AppCompatActivity {
    public void isPermissionGranted(boolean z, String str) {}

    public void setFullScreen() {
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
    }

    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        if (i == 52) {
            isPermissionGranted(iArr[0] == 0, strArr[0]);
        }
    }

}
