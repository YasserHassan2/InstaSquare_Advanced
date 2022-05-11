package com.que.shot.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.que.shot.constants.Constants;
import com.que.shot.adapters.AlbumAdapter;
import com.que.shot.adapters.ListAlbumAdapter;
import com.que.shot.model.ImageModel;
import com.que.shot.interfac.OnAlbum;
import com.que.shot.interfac.OnListAlbum;
import com.que.shot.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GridPickerActivity extends AppCompatActivity implements View.OnClickListener, OnAlbum, OnListAlbum {
    public static final String KEY_DATA_RESULT = "KEY_DATA_RESULT";
    public static final String KEY_LIMIT_MAX_IMAGE = "KEY_LIMIT_MAX_IMAGE";
    public static final String KEY_LIMIT_MIN_IMAGE = "KEY_LIMIT_MIN_IMAGE";
    AlbumAdapter albumAdapter;
    ArrayList<ImageModel> dataAlbum = new ArrayList<>();
    ArrayList<ImageModel> dataListPhoto = new ArrayList<>();
    GridView gridViewAlbum;
    GridView gridViewPhotos;
    ScrollView scrollViewSelected;
    LinearLayout linearLayoutSelected;
    int limitImageMax = 9;
    int limitImageMin = 2;
    ListAlbumAdapter listAlbumAdapter;
    ArrayList<ImageModel> listItemSelect = new ArrayList<>();
    private Handler mHandler;
    int itemSelected;
    ArrayList<String> stringArrayListPath = new ArrayList<>();
    private AdView adView;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_grid_picker);
        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.limitImageMax = extras.getInt(KEY_LIMIT_MAX_IMAGE, 9);
            this.limitImageMin = extras.getInt(KEY_LIMIT_MIN_IMAGE, 2);
            if (this.limitImageMin > this.limitImageMax) {
                finish();
            }
            if (this.limitImageMin < 1) {
                finish();
            }
        }
        getSupportActionBar().setTitle(R.string.text_title_activity_album);
        this.gridViewPhotos = findViewById(R.id.gridViewPhotos);
        findViewById(R.id.textViewDone).setOnClickListener(this);
        this.linearLayoutSelected = findViewById(R.id.linearLayoutSelected);
        this.scrollViewSelected = findViewById(R.id.scrollViewSelected);
        this.scrollViewSelected.getLayoutParams().height = this.itemSelected;
        this.gridViewAlbum = findViewById(R.id.gridViewAlbum);
        this.mHandler = new Handler() {
            @SuppressLint("HandlerLeak")
            public void handleMessage(Message message) {
                super.handleMessage(message);

            }
        };
        try {
            Collections.sort(this.dataAlbum, new Comparator<ImageModel>() {
                public int compare(ImageModel imageModel, ImageModel imageModel2) {
                    return imageModel.getName().compareToIgnoreCase(imageModel2.getName());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.albumAdapter = new AlbumAdapter(this, R.layout.item_album, this.dataAlbum);
        this.albumAdapter.setOnItem(this);
        if (isPermissionGranted("android.permission.READ_EXTERNAL_STORAGE")) {
            new getItemAlbum().execute(new Void[0]);
        } else {
            requestPermission("android.permission.READ_EXTERNAL_STORAGE", 1001);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private boolean isPermissionGranted(String string) {
        return ContextCompat.checkSelfPermission(this, string) == 0;
    }

    private void requestPermission(String str, int i) {
        ActivityCompat.shouldShowRequestPermissionRationale(this, str);
        ActivityCompat.requestPermissions(this, new String[]{str}, i);
    }

    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        if (i == 1001) {
            if (iArr.length <= 0 || iArr[0] != 0) {
                finish();
            } else {
                new getItemAlbum().execute(new Void[0]);
            }
        } else if (i == 1002 && iArr.length > 0) {
            int i2 = iArr[0];
        }
    }


    private class getItemAlbum extends AsyncTask<Void, Void, String> {

        public void onPreExecute() {}

        public void onProgressUpdate(Void... voidArr) {}

        private getItemAlbum() {}

        public String doInBackground(Void... voidArr) {
            Cursor cursor = GridPickerActivity.this.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{"_data", "bucket_display_name"}, (String) null, (String[]) null, (String) null);
            if (cursor == null) {
                return "";
            }
            int columnIndexOrThrow = cursor.getColumnIndexOrThrow("_data");
            while (cursor.moveToNext()) {
                String string = cursor.getString(columnIndexOrThrow);
                File file = new File(string);
                if (file.exists()) {
                    boolean access$000 = GridPickerActivity.this.checkFile(file);
                    if (!GridPickerActivity.this.Check(file.getParent(), GridPickerActivity.this.stringArrayListPath) && access$000) {
                        GridPickerActivity.this.stringArrayListPath.add(file.getParent());
                        GridPickerActivity.this.dataAlbum.add(new ImageModel(file.getParentFile().getName(), string, file.getParent()));
                    }
                }
            }
            Collections.sort(GridPickerActivity.this.dataAlbum);
            cursor.close();
            return "";
        }

        public void onPostExecute(String str) {
            GridPickerActivity.this.gridViewAlbum.setAdapter(GridPickerActivity.this.albumAdapter);
        }
    }

    private class GetItemListAlbum extends AsyncTask<Void, Void, String> {
        String pathAlbum;


        public void onPreExecute() {
        }


        public void onProgressUpdate(Void... voidArr) {
        }

        GetItemListAlbum(String str) {
            this.pathAlbum = str;
        }


        public String doInBackground(Void... voidArr) {
            File file = new File(this.pathAlbum);
            if (!file.isDirectory()) {
                return "";
            }
            for (File file2 : file.listFiles()) {
                if (file2.exists()) {
                    boolean access$000 = GridPickerActivity.this.checkFile(file2);
                    if (!file2.isDirectory() && access$000) {
                        GridPickerActivity.this.dataListPhoto.add(new ImageModel(file2.getName(), file2.getAbsolutePath(), file2.getAbsolutePath()));
                        publishProgress(new Void[0]);
                    }
                }
            }
            return "";
        }


        public void onPostExecute(String str) {
            try {
                Collections.sort(GridPickerActivity.this.dataListPhoto, new Comparator<ImageModel>() {
                    @Override
                    public int compare(ImageModel imageModel, ImageModel imageModel2) {
                        File file = new File(imageModel.getPathFolder());
                        File file2 = new File(imageModel2.getPathFolder());
                        if (file.lastModified() > file2.lastModified()) {
                            return -1;
                        }
                        return file.lastModified() < file2.lastModified() ? 1 : 0;
                    }
                });
            } catch (Exception e) {
            }
            GridPickerActivity.this.listAlbumAdapter.notifyDataSetChanged();
        }


    }

    public boolean Check(String string, ArrayList<String> arrayList) {
        return !arrayList.isEmpty() && arrayList.contains(string);
    }

    public void refreshGridViewAlbum() {
        this.albumAdapter = new AlbumAdapter(this, R.layout.item_album, this.dataAlbum);
        this.albumAdapter.setOnItem(this);
        this.gridViewAlbum.setAdapter(this.albumAdapter);
        this.gridViewAlbum.setVisibility(View.GONE);
        this.gridViewAlbum.setVisibility(View.VISIBLE);
    }

    public void refreshGridViewListAlbum() {
        this.listAlbumAdapter = new ListAlbumAdapter(this, R.layout.item_list_album, this.dataListPhoto);
        this.listAlbumAdapter.setOnListAlbum(this);
        this.gridViewPhotos.setAdapter(this.listAlbumAdapter);
        this.gridViewPhotos.setVisibility(View.GONE);
        this.gridViewPhotos.setVisibility(View.VISIBLE);
    }

    public static long getFolderSize(File file) {
        File[] listFiles;
        boolean z;
        if (file == null || !file.exists() || (listFiles = file.listFiles()) == null || listFiles.length <= 0) {
            return 0;
        }
        long j = 0;
        for (File file2 : listFiles) {
            if (file2.isFile()) {
                int i = 0;
                while (true) {
                    if (i >= Constants.FORMAT_IMAGE.size()) {
                        z = false;
                        break;
                    } else if (file2.getName().endsWith(Constants.FORMAT_IMAGE.get(i))) {
                        z = true;
                        break;
                    } else {
                        i++;
                    }
                }
                if (z) {
                    j++;
                }
            }
        }
        return j;
    }

    public void itemSelect(final ImageModel imageModel) {
        imageModel.setId(this.listItemSelect.size());
        this.listItemSelect.add(imageModel);
        final View inflate = View.inflate(this, R.layout.item_album_selected, (ViewGroup) null);
        ImageView image_view_item = (ImageView) inflate.findViewById(R.id.image_view_item);
        image_view_item.setScaleType(ImageView.ScaleType.CENTER_CROP);
        ((RequestBuilder) Glide.with((Activity) this).load(imageModel.getPathFile()).placeholder((int) R.drawable.image_show)).into(image_view_item);
        ((ImageView) inflate.findViewById(R.id.image_view_remove)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                GridPickerActivity.this.linearLayoutSelected.removeView(inflate);
                GridPickerActivity.this.listItemSelect.remove(imageModel);
            }
        });
        this.linearLayoutSelected.addView(inflate);
        inflate.startAnimation(AnimationUtils.loadAnimation(this, R.anim.abc_fade_in));
        sendScroll();
    }

    private void sendScroll() {
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        GridPickerActivity.this.scrollViewSelected.fullScroll(66);
                    }
                });
            }
        }).start();
    }

    public void setListAlbum(String str) {
        getSupportActionBar().setTitle((CharSequence) new File(str).getName());
        this.listAlbumAdapter = new ListAlbumAdapter(this, R.layout.item_list_album, this.dataListPhoto);
        this.listAlbumAdapter.setOnListAlbum(this);
        this.gridViewPhotos.setAdapter(this.listAlbumAdapter);
        this.gridViewPhotos.setVisibility(View.VISIBLE);
        new GetItemListAlbum(str).execute(new Void[0]);
    }

    public void onClick(View view) {
        if (view.getId() == R.id.textViewDone) {
            ArrayList<String> listString = getListString(this.listItemSelect);
            if (listString.size() >= this.limitImageMin) {
                done(listString);
                return;
            }
            Toast.makeText(this, "Please select at lease " + this.limitImageMin + " images", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
         if (menuItem.getItemId() == 16908332) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void done(ArrayList<String> arrayList) {
        Intent intent = new Intent(this, QueShotGridActivity.class);
        intent.putStringArrayListExtra(KEY_DATA_RESULT, arrayList);
        startActivity(intent);
    }


    public ArrayList<String> getListString(ArrayList<ImageModel> arrayList) {
        ArrayList<String> arrayList2 = new ArrayList<>();
        for (int i = 0; i < arrayList.size(); i++) {
            arrayList2.add(arrayList.get(i).getPathFile());
        }
        return arrayList2;
    }


    public boolean checkFile(File file) {
        if (file == null) {
            return false;
        }
        if (!file.isFile()) {
            return true;
        }
        String name = file.getName();
        if (name.startsWith(".") || file.length() == 0) {
            return false;
        }
        for (int i = 0; i < Constants.FORMAT_IMAGE.size(); i++) {
            if (name.endsWith(Constants.FORMAT_IMAGE.get(i))) {
                return true;
            }
        }
        return false;
    }

    public void onBackPressed() {
        if (this.gridViewPhotos.getVisibility() == View.VISIBLE) {
            this.dataListPhoto.clear();
            this.listAlbumAdapter.notifyDataSetChanged();
            this.gridViewPhotos.setVisibility(View.GONE);
            getSupportActionBar().setTitle((CharSequence) getResources().getString(R.string.text_title_activity_album));
            return;
        }
        super.onBackPressed();
    }

    public static DisplayMetrics getDisplayInfo(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindow().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }

    public void OnItemAlbumClick(int i) {
        setListAlbum(this.dataAlbum.get(i).getPathFolder());
    }

    public void OnItemListAlbumClick(ImageModel imageModel) {
        if (this.listItemSelect.size() < this.limitImageMax) {
            itemSelect(imageModel);
            return;
        }
        Toast.makeText(this, "Limit " + this.limitImageMax + " images", Toast.LENGTH_SHORT).show();
    }

    public void doinBackgroundPhoto(final int i) {
        new AsyncTask<String, String, Void>() {

            public void onPreExecute() {
                super.onPreExecute();
            }


            public Void doInBackground(String... strArr) {
                if (i == 0) {
                    try {
                        Collections.sort(GridPickerActivity.this.dataListPhoto, new Comparator<ImageModel>() {
                            public int compare(ImageModel imageModel, ImageModel imageModel2) {
                                return imageModel.getName().compareToIgnoreCase(imageModel2.getName());
                            }
                        });
                        return null;
                    } catch (Exception e) {
                        return null;
                    }
                } else if (i == 1) {
                    Collections.sort(GridPickerActivity.this.dataListPhoto, new Comparator<ImageModel>() {
                        public int compare(ImageModel imageModel, ImageModel imageModel2) {
                            int i = (GridPickerActivity.getFolderSize(new File(imageModel.getPathFolder())) > GridPickerActivity.getFolderSize(new File(imageModel2.getPathFolder())) ? 1 : (GridPickerActivity.getFolderSize(new File(imageModel.getPathFolder())) == GridPickerActivity.getFolderSize(new File(imageModel2.getPathFolder())) ? 0 : -1));
                            if (i > 0) {
                                return -1;
                            }
                            return i < 0 ? 1 : 0;
                        }
                    });
                    return null;
                } else if (i != 2) {
                    return null;
                } else {
                    Collections.sort(GridPickerActivity.this.dataListPhoto, new Comparator<ImageModel>() {
                        public int compare(ImageModel imageModel, ImageModel imageModel2) {
                            File file = new File(imageModel.getPathFolder());
                            File file2 = new File(imageModel2.getPathFolder());
                            if (file.lastModified() > file2.lastModified()) {
                                return -1;
                            }
                            return file.lastModified() < file2.lastModified() ? 1 : 0;
                        }
                    });
                    return null;
                }
            }


            public void onPostExecute(Void voidR) {
                super.onPostExecute(voidR);
                GridPickerActivity.this.refreshGridViewListAlbum();
            }
        }.execute(new String[0]);
    }

    public void doinBackground() {
        new AsyncTask<String, String, Void>() {

            public void onPreExecute() {
                super.onPreExecute();
            }


            public Void doInBackground(String... strArr) {
                Collections.sort(GridPickerActivity.this.dataAlbum, new Comparator<ImageModel>() {
                    @SuppressLint("StaticFieldLeak")
                    public int compare(ImageModel imageModel, ImageModel imageModel2) {
                        File file = new File(imageModel.getPathFolder());
                        File file2 = new File(imageModel2.getPathFolder());
                        if (file.lastModified() > file2.lastModified()) {
                            return -1;
                        }
                        return file.lastModified() < file2.lastModified() ? 1 : 0;
                    }
                });
                return null;
            }


            @SuppressLint("StaticFieldLeak")
            public void onPostExecute(Void voidR) {
                super.onPostExecute(voidR);
                GridPickerActivity.this.refreshGridViewAlbum();
            }
        }.execute(new String[0]);
    }
}
