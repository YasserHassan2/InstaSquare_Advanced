package com.que.shot.utils;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;

import com.que.shot.queshot.QueShotGridView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class SaveFileUtils {

    public static File saveBitmapFileEditor(Context context, Bitmap bitmap, String name) throws IOException {
        if (Build.VERSION.SDK_INT >= 30) {
            ContentResolver resolver = context.getContentResolver();
            ContentValues contentValues = new ContentValues();
            contentValues.put("_display_name", name);
            contentValues.put("mime_type", "image/png");
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES);
            OutputStream fos = resolver.openOutputStream(resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues));
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            String imagesDir = Environment.getExternalStorageDirectory().toString() + File.separator + Environment.DIRECTORY_PICTURES;
            File file = new File(imagesDir);
            if (!file.exists()) {
                file.mkdir();
            }
            return new File(imagesDir, name + ".png");
        }
        String imagesDir2 = Environment.getExternalStorageDirectory().toString() + File.separator + Environment.DIRECTORY_PICTURES;
        File file2 = new File(imagesDir2);
        if (!file2.exists()) {
            file2.mkdir();
        }

        File image = new File(imagesDir2, name + ".png");
        OutputStream fos2 = new FileOutputStream(image);
        MediaScannerConnection.scanFile(context, new String[]{image.getAbsolutePath()}, (String[]) null, new MediaScannerConnection.MediaScannerConnectionClient() {
            public void onMediaScannerConnected() {
            }

            public void onScanCompleted(String path, Uri uri) {
            }
        });
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos2);
        fos2.flush();
        fos2.close();
        return image;
    }

    public static File saveBitmapFileCollage(Context context, Bitmap bitmap, String name) throws IOException {
        if (Build.VERSION.SDK_INT >= 30) {
            ContentResolver resolver = context.getContentResolver();
            ContentValues contentValues = new ContentValues();
            contentValues.put("_display_name", name);
            contentValues.put("mime_type", "image/png");
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES);
            OutputStream fos = resolver.openOutputStream(resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues));
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            String imagesDir = Environment.getExternalStorageDirectory().toString() + File.separator + Environment.DIRECTORY_PICTURES;
            File file = new File(imagesDir);
            if (!file.exists()) {
                file.mkdir();
            }
            return new File(imagesDir, name + ".png");
        }
        String imagesDir2 = Environment.getExternalStorageDirectory().toString() + File.separator + Environment.DIRECTORY_PICTURES;
        File file2 = new File(imagesDir2);
        if (!file2.exists()) {
            file2.mkdir();
        }
        File image = new File(imagesDir2, name + ".png");
        OutputStream fos2 = new FileOutputStream(image);
        MediaScannerConnection.scanFile(context, new String[]{image.getAbsolutePath()}, (String[]) null, new MediaScannerConnection.MediaScannerConnectionClient() {
            public void onMediaScannerConnected() {
            }

            public void onScanCompleted(String path, Uri uri) {
            }
        });
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos2);
        fos2.flush();
        fos2.close();
        return image;
    }

    public static Bitmap createBitmap(QueShotGridView quShotCollageView, int i) {
        quShotCollageView.clearHandling();
        quShotCollageView.invalidate();
        Bitmap createBitmap = Bitmap.createBitmap(i, (int) (((float) i) / (((float) quShotCollageView.getWidth()) / ((float) quShotCollageView.getHeight()))), Bitmap.Config.ARGB_8888);
        quShotCollageView.draw(new Canvas(createBitmap));
        return createBitmap;
    }

    public static Bitmap createBitmap(QueShotGridView quShotCollageView) {
        quShotCollageView.clearHandling();
        quShotCollageView.invalidate();
        Bitmap createBitmap = Bitmap.createBitmap(quShotCollageView.getWidth(), quShotCollageView.getHeight(), Bitmap.Config.ARGB_8888);
        quShotCollageView.draw(new Canvas(createBitmap));
        return createBitmap;
    }

}
