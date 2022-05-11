package com.que.shot.activities;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.Guideline;
import androidx.core.content.ContextCompat;
import androidx.exifinterface.media.ExifInterface;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.que.shot.R;
import com.que.shot.adapters.AspectAdapter;
import com.que.shot.adapters.GridToolsAdapter;
import com.que.shot.adapters.FilterAdapter;
import com.que.shot.adapters.GridItemToolsAdapter;
import com.que.shot.adapters.RecyclerTabLayout;
import com.que.shot.adapters.StickerAdapter;
import com.que.shot.adapters.StickerTabAdapter;
import com.que.shot.assets.EffectCodeAsset;
import com.que.shot.assets.FilterCodeAsset;
import com.que.shot.assets.StickersAsset;
import com.que.shot.listener.FilterListener;
import com.que.shot.grid.QueShotGrid;
import com.que.shot.grid.QueShotLayout;
import com.que.shot.grid.QueShotLayoutParser;
import com.que.shot.utils.CollageUtils;
import com.que.shot.queshot.QueShotGridView;
import com.que.shot.adapters.GridAdapter;
import com.que.shot.adapters.BackgroundGridAdapter;
import com.que.shot.event.AlignHorizontallyEvent;
import com.que.shot.event.DeleteIconEvent;
import com.que.shot.event.FlipHorizontallyEvent;
import com.que.shot.event.ZoomIconEvent;
import com.que.shot.fragment.BurnFragment;
import com.que.shot.fragment.CropFragment;
import com.que.shot.fragment.DivideFragment;
import com.que.shot.fragment.DodgeFragment;
import com.que.shot.fragment.FilterFragment;
import com.que.shot.fragment.RotateFragment;
import com.que.shot.queshot.QueShotPickerView;
import com.que.shot.picker.PermissionsUtils;
import com.que.shot.queshot.QueShotStickerIcons;
import com.que.shot.sticker.DrawableSticker;
import com.que.shot.sticker.Sticker;
import com.que.shot.queshot.QueShotStickerView;
import com.que.shot.queshot.QueShotTextView;
import com.que.shot.module.Module;
import com.que.shot.utils.FilterUtils;
import com.que.shot.utils.SaveFileUtils;
import com.que.shot.utils.SystemUtil;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.steelkiwi.cropiwa.AspectRatio;

import org.jetbrains.annotations.NotNull;
import org.wysaid.nativePort.CGENativeLibrary;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@SuppressLint("StaticFieldLeak")
public class QueShotGridActivity extends QueShotBaseActivity implements GridToolsAdapter.OnItemSelected,
        AspectAdapter.OnNewSelectedListener, StickerAdapter.OnClickSplashListener,
        BackgroundGridAdapter.BackgroundGridListener, FilterListener, CropFragment.OnCropPhoto,
        RotateFragment.OnCropPhoto, FilterFragment.OnFilterSavePhoto, DodgeFragment.OnFilterSavePhoto,
        DivideFragment.OnFilterSavePhoto, BurnFragment.OnFilterSavePhoto, GridItemToolsAdapter.OnPieceFuncItemSelected,
        GridAdapter.OnItemClickListener {
    private static QueShotGridActivity QueShotGridActivityInstance;
    public static QueShotGridActivity QueShotGridActivityCollage;
    public QueShotLayout queShotLayout;
    public QueShotGridView queShotGridView;
    public AspectRatio aspectRatio;
    public BackgroundGridAdapter.SquareView currentBackgroundState;
    private RelativeLayout relativeLayoutLoading;
    public GridToolsAdapter gridToolsAdapter = new GridToolsAdapter(this, true);
    private GridItemToolsAdapter gridItemToolsAdapter = new GridItemToolsAdapter(this);
    public LinearLayout linear_layout_wrapper_sticker_list;
    public Module moduleToolsId;
    public ImageView imageViewAddSticker;
    public float BorderRadius;
    public float Padding;
    private int deviceHeight = 0;
    public int deviceWidth = 0;
    // ConstraintLayout
    public ConstraintLayout constraint_layout_change_background;
    public ConstraintLayout constraint_layout_filter_save;
    public ConstraintLayout constrant_layout_change_Layout;
    public ConstraintLayout constraint_layout_filter_layout;
    private ConstraintLayout constraint_layout_collage_layout;
    private ConstraintLayout constraint_save_control;
    private ConstraintLayout constraint_layout_wrapper_collage_view;
    public ConstraintLayout constraint_layout_sticker;
    // RecyclerView
    public RecyclerView recyclerViewTools;
    public RecyclerView recyclerViewAllFilter;
    public RecyclerView recyclerViewBwFilter;
    public RecyclerView recyclerViewVintageFilter;
    public RecyclerView recyclerViewSmoothFilter;
    public RecyclerView recyclerViewColdFilter;
    public RecyclerView recyclerViewWarmFilter;
    public RecyclerView recyclerViewLegacyFilter;
    private RecyclerView recycler_view_collage;
    private RecyclerView recycler_view_ratio;
    private RecyclerView recycler_view_blur;
    private RecyclerView recycler_view_color;
    private RecyclerView recycler_view_gradient;
    public RecyclerView recyclerViewToolsCollage;
    // ArrayList
    public ArrayList listFilterAll = new ArrayList<>();
    public ArrayList listFilterBW = new ArrayList<>();
    public ArrayList listFilterVintage = new ArrayList<>();
    public ArrayList listFilterSmooth = new ArrayList<>();
    public ArrayList listFilterCold = new ArrayList<>();
    public ArrayList listFilterWarm = new ArrayList<>();
    public ArrayList listFilterLegacy = new ArrayList<>();
    public List<Drawable> drawableList = new ArrayList<>();
    public List<String> stringList;
    public List<Target> targets = new ArrayList();
    // TextView
    private TextView textViewListAllFilter;
    private TextView textViewListBwFilter;
    private TextView textViewListVintageFilter;
    private TextView textViewListSmoothFilter;
    private TextView textViewListColdFilter;
    private LinearLayout linearLayoutBorder;
    private TextView textViewListWarmFilter;
    private TextView textViewListLegacyFilter;
    private TextView text_view_save;
    private TextView textViewTitle;
    public TextView textViewCancel;
    public TextView textViewDiscard;
    public TextView textViewSave;
    // SeekBar
    private SeekBar seekBarRadius;
    private SeekBar seekBarPadding;
    public SeekBar seekbarSticker;

    //
    Guideline guidelineTool;
    Guideline guidelineLayout;
    // Ads
    private AdView adView;
    private InterstitialAd interstitial;

    private LinearLayout linearLayoutLayer;
    private LinearLayout linearLayoutRatio;
    private LinearLayout linearLayoutBorde;
    private TextView textViewListLayer;
    private TextView textViewListRatio;
    private TextView textViewListBorder;
    private View viewCollage;
    private View viewBorder;
    private View viewRatio;

    private LinearLayout linearLayoutColor;
    private LinearLayout linearLayoutGradient;
    private LinearLayout linearLayoutBlur;
    private TextView textViewListColor;
    private TextView textViewListGradient;
    private TextView textViewListBlur;
    private View viewColor;
    private View viewGradient;
    private View viewBlur;

    private View viewAll;
    private View viewCold;
    private View viewBW;
    private View viewVintage;
    private View viewSmooth;
    private View viewWarm;
    private View viewLegacy;

    private LinearLayout linearLayoutAll;
    private LinearLayout linearLayoutCold;
    private LinearLayout linearLayoutBw;
    private LinearLayout linearLayoutVintage;
    private LinearLayout linearLayoutWarm;
    private LinearLayout linearLayoutSmooth;
    private LinearLayout linearLayoutLegacy;

    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setFullScreen();
        setContentView(R.layout.activity_queshot_grid);
        if (Build.VERSION.SDK_INT < 30) {
            getWindow().setSoftInputMode(72);
        }
        adsViews();
        interstitialAdMobAds();
        this.deviceWidth = getResources().getDisplayMetrics().widthPixels;
        this.deviceHeight = getResources().getDisplayMetrics().heightPixels;

        findViewById(R.id.image_view_exit).setOnClickListener(view -> QueShotGridActivity.this.onBackPressed());
        this.queShotGridView = findViewById(R.id.collage_view);
        this.constraint_layout_wrapper_collage_view = findViewById(R.id.constraint_layout_wrapper_collage_view);
        this.constraint_layout_filter_layout = findViewById(R.id.constraint_layout_filter_layout);
        this.constraint_layout_filter_save = findViewById(R.id.constraint_layout_confirm_save_filter);
        this.recyclerViewTools = findViewById(R.id.recycler_view_tools);
        this.recyclerViewTools.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        this.recyclerViewTools.setAdapter(this.gridToolsAdapter);
        this.recyclerViewToolsCollage = findViewById(R.id.recycler_view_tools_collage);
        this.recyclerViewToolsCollage.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        this.recyclerViewToolsCollage.setAdapter(this.gridItemToolsAdapter);
        this.seekBarPadding = findViewById(R.id.seekbar_border);
        this.seekBarPadding.setOnSeekBarChangeListener(this.onSeekBarChangeListener);
        this.seekBarRadius = findViewById(R.id.seekbar_radius);
        this.guidelineTool = findViewById(R.id.guidelineTool);
        this.guidelineLayout = findViewById(R.id.guidelineLayout);
        this.seekBarRadius.setOnSeekBarChangeListener(this.onSeekBarChangeListener);
        this.stringList = getIntent().getStringArrayListExtra(GridPickerActivity.KEY_DATA_RESULT);
        this.relativeLayoutLoading = findViewById(R.id.relative_layout_loading);
        this.recyclerViewAllFilter = findViewById(R.id.recycler_view_filter_all);
        this.recyclerViewBwFilter = findViewById(R.id.recycler_view_filter_bw);
        this.recyclerViewVintageFilter = findViewById(R.id.recycler_view_filter_vintage);
        this.recyclerViewSmoothFilter = findViewById(R.id.recycler_view_filter_smooth);
        this.recyclerViewColdFilter = findViewById(R.id.recycler_view_filter_cold);
        this.recyclerViewWarmFilter = findViewById(R.id.recycler_view_filter_warm);
        this.recyclerViewLegacyFilter = findViewById(R.id.recycler_view_filter_legacy);
        this.recyclerViewBwFilter.setVisibility(View.GONE);
        this.linearLayoutBorder = findViewById(R.id.linearLayoutPadding);
        this.recyclerViewVintageFilter.setVisibility(View.GONE);
        this.recyclerViewSmoothFilter.setVisibility(View.GONE);
        this.recyclerViewColdFilter.setVisibility(View.GONE);
        this.recyclerViewWarmFilter.setVisibility(View.GONE);
        this.recyclerViewLegacyFilter.setVisibility(View.GONE);
        this.viewAll = findViewById(R.id.view_all);
        this.viewCold = findViewById(R.id.view_cold);
        this.viewBW = findViewById(R.id.view_bw);
        this.viewVintage = findViewById(R.id.view_vintage);
        this.viewSmooth = findViewById(R.id.view_smooth);
        this.viewWarm = findViewById(R.id.view_warm);
        this.viewLegacy = findViewById(R.id.view_legacy);
        this.viewBW.setVisibility(View.INVISIBLE);
        this.viewCold.setVisibility(View.INVISIBLE);
        this.viewVintage.setVisibility(View.INVISIBLE);
        this.viewSmooth.setVisibility(View.INVISIBLE);
        this.viewWarm.setVisibility(View.INVISIBLE);
        this.viewLegacy.setVisibility(View.INVISIBLE);
        this.linearLayoutAll = findViewById(R.id.linearLayoutAll);
        this.linearLayoutCold = findViewById(R.id.linearLayoutCold);
        this.linearLayoutBw = findViewById(R.id.linearLayoutBW);
        this.linearLayoutVintage = findViewById(R.id.linearLayoutVintage);
        this.linearLayoutWarm = findViewById(R.id.linearLayoutWarm);
        this.linearLayoutSmooth = findViewById(R.id.linearLayoutSmooth);
        this.linearLayoutLegacy = findViewById(R.id.linearLayoutLegacy);
        this.textViewListAllFilter = findViewById(R.id.text_view_list_all);
        this.textViewListBwFilter = findViewById(R.id.text_view_list_bw);
        this.textViewListVintageFilter = findViewById(R.id.text_view_list_vintage);
        this.textViewListSmoothFilter = findViewById(R.id.text_view_list_smooth);
        this.textViewListColdFilter = findViewById(R.id.text_view_list_cold);
        this.textViewListWarmFilter = findViewById(R.id.text_view_list_warm);
        this.textViewListLegacyFilter = findViewById(R.id.text_view_list_legacy);
        this.queShotLayout = CollageUtils.getCollageLayouts(this.stringList.size()).get(0);
        this.queShotGridView.setQueShotLayout(this.queShotLayout);
        this.queShotGridView.setTouchEnable(true);
        this.queShotGridView.setNeedDrawLine(false);
        this.queShotGridView.setNeedDrawOuterLine(false);
        this.queShotGridView.setLineSize(4);
        this.queShotGridView.setCollagePadding(6.0f);
        this.queShotGridView.setCollageRadian(15.0f);
        this.queShotGridView.setLineColor(ContextCompat.getColor(this, R.color.itemColorBlack));
        this.queShotGridView.setSelectedLineColor(ContextCompat.getColor(this, R.color.mainColor));
        this.queShotGridView.setHandleBarColor(ContextCompat.getColor(this, R.color.mainColor));
        this.queShotGridView.setAnimateDuration(300);

        this.queShotGridView.setOnQueShotSelectedListener((collage, i) -> {
            QueShotGridActivity.this.recyclerViewTools.setVisibility(View.GONE);
            QueShotGridActivity.this.recyclerViewToolsCollage.setVisibility(View.VISIBLE);
            QueShotGridActivity.this.slideUp(QueShotGridActivity.this.recyclerViewToolsCollage);
            QueShotGridActivity.this.setGoneSave();
            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) QueShotGridActivity.this.recyclerViewToolsCollage.getLayoutParams();
            layoutParams.bottomMargin = SystemUtil.dpToPx(QueShotGridActivity.this.getApplicationContext(), 10);
            QueShotGridActivity.this.recyclerViewToolsCollage.setLayoutParams(layoutParams);
            QueShotGridActivity.this.moduleToolsId = Module.COLLAGE;
        });
        this.queShotGridView.setOnQueShotUnSelectedListener(() -> {
            QueShotGridActivity.this.recyclerViewToolsCollage.setVisibility(View.GONE);
            QueShotGridActivity.this.recyclerViewTools.setVisibility(View.VISIBLE);
            setVisibleSave();
            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) recyclerViewToolsCollage.getLayoutParams();
            layoutParams.bottomMargin = 0;
            recyclerViewToolsCollage.setLayoutParams(layoutParams);
            moduleToolsId = Module.NONE;
        });

        this.constraint_save_control = findViewById(R.id.constraint_save_control);
        this.queShotGridView.post(() -> QueShotGridActivity.this.loadPhoto());
        this.linearLayoutAll.setOnClickListener(view -> QueShotGridActivity.this.setAllFilter());
        this.linearLayoutBw.setOnClickListener(view -> QueShotGridActivity.this.setBwFilter());
        this.linearLayoutVintage.setOnClickListener(view -> QueShotGridActivity.this.setVintageFilter());
        this.linearLayoutSmooth.setOnClickListener(view -> QueShotGridActivity.this.setSmoothFilter());
        this.linearLayoutCold.setOnClickListener(view -> QueShotGridActivity.this.setColdFilter());
        this.linearLayoutWarm.setOnClickListener(view -> QueShotGridActivity.this.setWarmFilter());
        this.linearLayoutLegacy.setOnClickListener(view -> QueShotGridActivity.this.setLegacyFilter());
        findViewById(R.id.imageViewSaveLayer).setOnClickListener(this.onClickListener);
        findViewById(R.id.imageViewCloseLayer).setOnClickListener(this.onClickListener);
        findViewById(R.id.imageViewClosebackground).setOnClickListener(this.onClickListener);
        findViewById(R.id.image_view_close_sticker).setOnClickListener(this.onClickListener);
        findViewById(R.id.image_view_save_filter).setOnClickListener(this.onClickListener);
        findViewById(R.id.imageViewSavebackground).setOnClickListener(this.onClickListener);
        findViewById(R.id.image_view_save_sticker).setOnClickListener(this.onClickListener);
        findViewById(R.id.image_view_close_filter).setOnClickListener(this.onClickListener);
        this.linearLayoutLayer = findViewById(R.id.linearLayoutCollage);
        this.linearLayoutBorde = findViewById(R.id.linearLayoutBorder);
        this.linearLayoutRatio = findViewById(R.id.linearLayoutRatio);
        this.textViewListLayer = findViewById(R.id.text_view_collage);
        this.textViewListBorder = findViewById(R.id.text_view_border);
        this.textViewListRatio = findViewById(R.id.text_view_ratio);
        this.viewCollage = findViewById(R.id.view_collage);
        this.viewBorder = findViewById(R.id.view_border);
        this.viewRatio = findViewById(R.id.view_ratio);
        this.linearLayoutLayer.setOnClickListener(view -> QueShotGridActivity.this.setLayer());
        this.linearLayoutBorde.setOnClickListener(view -> QueShotGridActivity.this.setBorder());
        this.linearLayoutRatio.setOnClickListener(view -> QueShotGridActivity.this.setRatio());

        this.linearLayoutColor = findViewById(R.id.linearLayoutColor);
        this.linearLayoutGradient = findViewById(R.id.linearLayoutGradient);
        this.linearLayoutBlur = findViewById(R.id.linearLayoutBlur);
        this.textViewListColor = findViewById(R.id.text_view_color);
        this.textViewListGradient = findViewById(R.id.text_view_gradient);
        this.textViewListBlur = findViewById(R.id.text_view_blur);
        this.viewGradient = findViewById(R.id.view_gradient);
        this.viewBlur = findViewById(R.id.view_blur);
        this.viewColor = findViewById(R.id.view_color);
        this.linearLayoutColor.setOnClickListener(view -> QueShotGridActivity.this.setBackgroundColor());
        this.linearLayoutGradient.setOnClickListener(view -> QueShotGridActivity.this.setBackgroundGradient());
        this.linearLayoutBlur.setOnClickListener(view -> QueShotGridActivity.this.selectBackgroundBlur());

        this.constrant_layout_change_Layout = findViewById(R.id.constrant_layout_change_Layout);
        this.textViewTitle = findViewById(R.id.textViewTitle);
        GridAdapter collageAdapter = new GridAdapter();
        this.recycler_view_collage = findViewById(R.id.recycler_view_collage);
        this.recycler_view_collage.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        this.recycler_view_collage.setAdapter(collageAdapter);
        collageAdapter.refreshData(CollageUtils.getCollageLayouts(this.stringList.size()), null);
        collageAdapter.setOnItemClickListener(this);
        AspectAdapter aspectRatioPreviewAdapter = new AspectAdapter(true);
        aspectRatioPreviewAdapter.setListener(this);
        this.recycler_view_ratio = findViewById(R.id.recycler_view_ratio);
        this.recycler_view_ratio.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        this.recycler_view_ratio.setAdapter(aspectRatioPreviewAdapter);
        this.linear_layout_wrapper_sticker_list = findViewById(R.id.linear_layout_wrapper_sticker_list);
        ViewPager stickerViewPager = findViewById(R.id.sticker_viewpaper);
        this.constraint_layout_sticker = findViewById(R.id.constraint_layout_sticker);
        this.seekbarSticker = findViewById(R.id.seekbar_alpha);
        this.seekbarSticker.setVisibility(View.GONE);
        this.seekbarSticker.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                Sticker currentSticker = QueShotGridActivity.this.queShotGridView.getCurrentSticker();
                if (currentSticker != null) {
                    currentSticker.setAlpha(i);
                }
            }
        });
        this.text_view_save = findViewById(R.id.text_view_save);
        this.text_view_save.setOnClickListener(view -> {
            if (interstitial != null && interstitial.isLoaded()) {
                interstitial.show();
            }
            if (PermissionsUtils.checkWriteStoragePermission(QueShotGridActivity.this)) {
                Bitmap createBitmap = SaveFileUtils.createBitmap(QueShotGridActivity.this.queShotGridView, 1920);
                Bitmap createBitmap2 = QueShotGridActivity.this.queShotGridView.createBitmap();
                new SaveCollageAsFile().execute(new Bitmap[]{createBitmap, createBitmap2});
            }

        });
        this.imageViewAddSticker = findViewById(R.id.relative_layout_add_sticker);
        this.imageViewAddSticker.setVisibility(View.GONE);
        this.imageViewAddSticker.setOnClickListener(view -> {
            imageViewAddSticker.setVisibility(View.GONE);
            linear_layout_wrapper_sticker_list.setVisibility(View.VISIBLE);
        });
        QueShotStickerIcons quShotStickerIconClose = new QueShotStickerIcons(ContextCompat.getDrawable(this, R.drawable.ic_outline_close), 0, QueShotStickerIcons.DELETE);
        quShotStickerIconClose.setIconEvent(new DeleteIconEvent());
        QueShotStickerIcons quShotStickerIconScale = new QueShotStickerIcons(ContextCompat.getDrawable(this, R.drawable.ic_outline_scale), 3, QueShotStickerIcons.SCALE);
        quShotStickerIconScale.setIconEvent(new ZoomIconEvent());
        QueShotStickerIcons quShotStickerIconFlip = new QueShotStickerIcons(ContextCompat.getDrawable(this, R.drawable.ic_outline_flip), 1, QueShotStickerIcons.FLIP);
        quShotStickerIconFlip.setIconEvent(new FlipHorizontallyEvent());
        QueShotStickerIcons quShotStickerIconCenter = new QueShotStickerIcons(ContextCompat.getDrawable(this, R.drawable.ic_outline_center), 2, QueShotStickerIcons.ALIGN);
        quShotStickerIconCenter.setIconEvent(new AlignHorizontallyEvent());
        this.queShotGridView.setIcons(Arrays.asList(new QueShotStickerIcons[]{quShotStickerIconClose, quShotStickerIconScale, quShotStickerIconFlip, quShotStickerIconCenter}));
        this.queShotGridView.setConstrained(true);
        this.queShotGridView.setOnStickerOperationListener(this.onStickerOperationListener);
        stickerViewPager.setAdapter(new PagerAdapter() {
            public int getCount() {
                return 3;
            }

            public boolean isViewFromObject(@NonNull View view, @NonNull Object obj) {
                return view.equals(obj);
            }

            @Override
            public void destroyItem(@NonNull @NotNull ViewGroup container, int position, @NonNull @NotNull Object object) {
                (container).removeView((View) object);

            }

            @NonNull
            public Object instantiateItem(@NonNull ViewGroup viewGroup, int i) {
                View inflate = LayoutInflater.from(QueShotGridActivity.this.getBaseContext()).inflate(R.layout.list_women_beauty, null, false);
                RecyclerView recycler_view_sticker = inflate.findViewById(R.id.recyclerViewSticker);
                recycler_view_sticker.setHasFixedSize(true);
                recycler_view_sticker.setLayoutManager(new GridLayoutManager(QueShotGridActivity.this.getApplicationContext(), 7));
                switch (i) {
                    case 0:
                        recycler_view_sticker.setAdapter(new StickerAdapter(QueShotGridActivity.this.getApplicationContext(), StickersAsset.mListEmojy(), i, QueShotGridActivity.this));
                        break;
                    case 1:
                        recycler_view_sticker.setAdapter(new StickerAdapter(QueShotGridActivity.this.getApplicationContext(), StickersAsset.mListFlag(), i, QueShotGridActivity.this));
                        break;
                    case 2:
                        recycler_view_sticker.setAdapter(new StickerAdapter(QueShotGridActivity.this.getApplicationContext(), StickersAsset.mListBoom(), i, QueShotGridActivity.this));
                        break;

                }

                viewGroup.addView(inflate);
                return inflate;
            }
        });
        RecyclerTabLayout recycler_tab_layout = findViewById(R.id.recycler_tab_layout);
        recycler_tab_layout.setUpWithAdapter(new StickerTabAdapter(stickerViewPager, getApplicationContext()));
        recycler_tab_layout.setPositionThreshold(0.5f);
        recycler_tab_layout.setBackgroundColor(ContextCompat.getColor(this, R.color.TabColor));


        setLoading(false);
        this.constraint_layout_change_background = findViewById(R.id.constrant_layout_change_background);
        this.constraint_layout_collage_layout = findViewById(R.id.constraint_layout_collage_layout);
        this.currentBackgroundState = new BackgroundGridAdapter.SquareView(Color.parseColor("#ffffff"), "", true);
        this.recycler_view_color = findViewById(R.id.recycler_view_color);
        this.recycler_view_color.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false));
        this.recycler_view_color.setHasFixedSize(true);
        this.recycler_view_color.setAdapter(new BackgroundGridAdapter(getApplicationContext(), this));
        this.recycler_view_gradient = findViewById(R.id.recycler_view_gradient);
        this.recycler_view_gradient.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false));
        this.recycler_view_gradient.setHasFixedSize(true);
        this.recycler_view_gradient.setAdapter(new BackgroundGridAdapter(getApplicationContext(), (BackgroundGridAdapter.BackgroundGridListener) this, true));
        this.recycler_view_blur = findViewById(R.id.recycler_view_blur);
        this.recycler_view_blur.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false));
        this.recycler_view_blur.setHasFixedSize(true);
        this.recycler_view_blur.setAdapter(new BackgroundGridAdapter(getApplicationContext(), (BackgroundGridAdapter.BackgroundGridListener) this, true));
        Display defaultDisplay = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getSize(point);
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.queShotGridView.getLayoutParams();
        layoutParams.height = point.x;
        layoutParams.width = point.x;
        this.queShotGridView.setLayoutParams(layoutParams);
        this.aspectRatio = new AspectRatio(1, 1);
        this.queShotGridView.setAspectRatio(new AspectRatio(1, 1));
        QueShotGridActivityCollage = this;
        this.moduleToolsId = Module.NONE;
        CGENativeLibrary.setLoadImageCallback(this.loadImageCallback, (Object) null);
        QueShotGridActivityInstance = this;

        this.recyclerViewToolsCollage.setAlpha(0.0f);
        this.constraint_layout_collage_layout.post(() -> {
            slideDown(recyclerViewToolsCollage);
        });
        new Handler().postDelayed(() -> {
            recyclerViewToolsCollage.setAlpha(1.0f);
        }, 1000);

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
        try {
            this.queShotGridView.reset();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void onPause() {
        super.onPause();
    }

    public void onResume() {
        super.onResume();
    }

    public void slideDown(View view) {
        ObjectAnimator.ofFloat(view, "translationY", 0.0f, (float) view.getHeight()).start();
    }


    public void slideUp(View view) {
        ObjectAnimator.ofFloat(view, "translationY", new float[]{(float) view.getHeight(), 0.0f}).start();
    }

    @SuppressLint("NonConstantResourceId")
    public View.OnClickListener onClickListener = view -> {
        switch (view.getId()) {
            case R.id.imageViewClosebackground:
            case R.id.image_view_close_filter:
            case R.id.imageViewCloseLayer:
            case R.id.image_view_close_sticker:
                QueShotGridActivity.this.setVisibleSave();
                QueShotGridActivity.this.onBackPressed();
                return;
            case R.id.imageViewSavebackground:
                setGuideLineTools();
                this.constraint_layout_change_background.setVisibility(View.GONE);
                this.recyclerViewTools.setVisibility(View.VISIBLE);
                this.recyclerViewToolsCollage.setVisibility(View.VISIBLE);
                QueShotGridActivity.this.setVisibleSave();
                QueShotGridActivity.this.queShotGridView.setLocked(true);
                QueShotGridActivity.this.queShotGridView.setTouchEnable(true);
                if (QueShotGridActivity.this.queShotGridView.getBackgroundResourceMode() == 0) {
                    QueShotGridActivity.this.currentBackgroundState.isColor = true;
                    QueShotGridActivity.this.currentBackgroundState.isBitmap = false;
                    QueShotGridActivity.this.currentBackgroundState.drawableId = ((ColorDrawable) QueShotGridActivity.this.queShotGridView.getBackground()).getColor();
                    QueShotGridActivity.this.currentBackgroundState.drawable = null;
                } else if (QueShotGridActivity.this.queShotGridView.getBackgroundResourceMode() == 1) {
                    QueShotGridActivity.this.currentBackgroundState.isColor = false;
                    QueShotGridActivity.this.currentBackgroundState.isBitmap = false;
                    QueShotGridActivity.this.currentBackgroundState.drawable = QueShotGridActivity.this.queShotGridView.getBackground();
                } else {
                    QueShotGridActivity.this.currentBackgroundState.isColor = false;
                    QueShotGridActivity.this.currentBackgroundState.isBitmap = true;
                    QueShotGridActivity.this.currentBackgroundState.drawable = QueShotGridActivity.this.queShotGridView.getBackground();
                }
                QueShotGridActivity.this.moduleToolsId = Module.NONE;
                return;
            case R.id.image_view_save_filter:
                setGuideLineTools();
                QueShotGridActivity.this.constraint_layout_filter_layout.setVisibility(View.GONE);
                QueShotGridActivity.this.constraint_layout_filter_save.setVisibility(View.GONE);
                this.recyclerViewTools.setVisibility(View.VISIBLE);
                this.recyclerViewToolsCollage.setVisibility(View.VISIBLE);
                QueShotGridActivity.this.moduleToolsId = Module.NONE;
                return;
            case R.id.imageViewSaveLayer:
                setGuideLineTools();
                this.constrant_layout_change_Layout.setVisibility(View.GONE);
                this.recyclerViewTools.setVisibility(View.VISIBLE);
                QueShotGridActivity.this.setVisibleSave();
                this.recyclerViewToolsCollage.setVisibility(View.VISIBLE);
                QueShotGridActivity.this.queShotLayout = QueShotGridActivity.this.queShotGridView.getQueShotLayout();
                QueShotGridActivity.this.BorderRadius = QueShotGridActivity.this.queShotGridView.getCollageRadian();
                QueShotGridActivity.this.Padding = QueShotGridActivity.this.queShotGridView.getCollagePadding();
                QueShotGridActivity.this.queShotGridView.setLocked(true);
                QueShotGridActivity.this.queShotGridView.setTouchEnable(true);
                QueShotGridActivity.this.aspectRatio = QueShotGridActivity.this.queShotGridView.getAspectRatio();
                QueShotGridActivity.this.moduleToolsId = Module.NONE;
                return;
            case R.id.image_view_save_sticker:
                setGuideLineTools();
                QueShotGridActivity.this.queShotGridView.setHandlingSticker(null);
                QueShotGridActivity.this.seekbarSticker.setVisibility(View.GONE);
                QueShotGridActivity.this.imageViewAddSticker.setVisibility(View.GONE);
                QueShotGridActivity.this.constraint_layout_sticker.setVisibility(View.GONE);
                QueShotGridActivity.this.linear_layout_wrapper_sticker_list.setVisibility(View.VISIBLE);
                QueShotGridActivity.this.setVisibleSave();
                QueShotGridActivity.this.queShotGridView.setLocked(true);
                QueShotGridActivity.this.queShotGridView.setTouchEnable(true);
                QueShotGridActivity.this.moduleToolsId = Module.NONE;
                this.linear_layout_wrapper_sticker_list.setVisibility(View.VISIBLE);
                this.recyclerViewTools.setVisibility(View.VISIBLE);
                this.recyclerViewToolsCollage.setVisibility(View.VISIBLE);
                setVisibleSave();
                return;
            default:
        }
    };



    public SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        public void onStopTrackingTouch(SeekBar seekBar) {
        }

        public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
            switch (seekBar.getId()) {
                case R.id.seekbar_border:
                    QueShotGridActivity.this.queShotGridView.setCollagePadding((float) i);
                    break;
                case R.id.seekbar_radius:
                    QueShotGridActivity.this.queShotGridView.setCollageRadian((float) i);
                    break;
            }
            QueShotGridActivity.this.queShotGridView.invalidate();
        }
    };
    QueShotStickerView.OnStickerOperationListener onStickerOperationListener = new QueShotStickerView.OnStickerOperationListener() {
        public void onStickerDrag(@NonNull Sticker sticker) {
        }

        public void onStickerFlip(@NonNull Sticker sticker) {
        }

        public void onStickerTouchedDown(@NonNull Sticker sticker) {
        }

        public void onStickerZoom(@NonNull Sticker sticker) {
        }

        public void onTouchDownBeauty(float f, float f2) {
        }

        public void onTouchDragBeauty(float f, float f2) {
        }

        public void onTouchUpBeauty(float f, float f2) {
        }

        public void onAddSticker(@NonNull Sticker sticker) {
            QueShotGridActivity.this.seekbarSticker.setVisibility(View.VISIBLE);
            QueShotGridActivity.this.seekbarSticker.setProgress(sticker.getAlpha());
        }

        public void onStickerSelected(@NonNull Sticker sticker) {
            QueShotGridActivity.this.seekbarSticker.setVisibility(View.VISIBLE);
            QueShotGridActivity.this.seekbarSticker.setProgress(sticker.getAlpha());
        }

        public void onStickerDeleted(@NonNull Sticker sticker) {
            QueShotGridActivity.this.seekbarSticker.setVisibility(View.GONE);
        }

        public void onStickerTouchOutside() {
            QueShotGridActivity.this.seekbarSticker.setVisibility(View.GONE);
        }

        public void onStickerDoubleTap(@NonNull Sticker sticker) {
            if (sticker instanceof QueShotTextView) {
                sticker.setShow(false);
                QueShotGridActivity.this.queShotGridView.setHandlingSticker(null);
            }
        }
    };


    public static QueShotGridActivity getQueShotGridActivityInstance() {
        return QueShotGridActivityInstance;
    }

    public void isPermissionGranted(boolean z, String str) {
        if (z) {
            Bitmap createBitmap = SaveFileUtils.createBitmap(this.queShotGridView, 1920);
            Bitmap createBitmap2 = this.queShotGridView.createBitmap();
            new SaveCollageAsFile().execute(createBitmap, createBitmap2);
        }
    }

    public CGENativeLibrary.LoadImageCallback loadImageCallback = new CGENativeLibrary.LoadImageCallback() {
        public Bitmap loadImage(String string, Object object) {
            try {
                return BitmapFactory.decodeStream(QueShotGridActivity.this.getAssets().open(string));
            } catch (IOException ioException) {
                return null;
            }
        }
        public void loadImageOK(Bitmap bitmap, Object object) {
            bitmap.recycle();
        }
    };

    public void setAllFilter() {
        this.recyclerViewAllFilter.setVisibility(View.VISIBLE);
        this.recyclerViewBwFilter.setVisibility(View.GONE);
        this.recyclerViewVintageFilter.setVisibility(View.GONE);
        this.recyclerViewSmoothFilter.setVisibility(View.GONE);
        this.recyclerViewColdFilter.setVisibility(View.GONE);
        this.recyclerViewWarmFilter.setVisibility(View.GONE);
        this.recyclerViewLegacyFilter.setVisibility(View.GONE);
        this.textViewListAllFilter.setTextColor(ContextCompat.getColor(this, R.color.white));
        this.textViewListColdFilter.setTextColor(ContextCompat.getColor(this, R.color.grayText));
        this.textViewListBwFilter.setTextColor(ContextCompat.getColor(this, R.color.grayText));
        this.textViewListVintageFilter.setTextColor(ContextCompat.getColor(this, R.color.grayText));
        this.textViewListSmoothFilter.setTextColor(ContextCompat.getColor(this, R.color.grayText));
        this.textViewListWarmFilter.setTextColor(ContextCompat.getColor(this, R.color.grayText));
        this.textViewListLegacyFilter.setTextColor(ContextCompat.getColor(this, R.color.grayText));
        this.viewAll.setVisibility(View.VISIBLE);
        this.viewCold.setVisibility(View.INVISIBLE);
        this.viewBW.setVisibility(View.INVISIBLE);
        this.viewVintage.setVisibility(View.INVISIBLE);
        this.viewSmooth.setVisibility(View.INVISIBLE);
        this.viewWarm.setVisibility(View.INVISIBLE);
        this.viewLegacy.setVisibility(View.INVISIBLE);
    }

    public void setBwFilter() {
        this.recyclerViewAllFilter.setVisibility(View.GONE);
        this.recyclerViewBwFilter.setVisibility(View.VISIBLE);
        this.recyclerViewVintageFilter.setVisibility(View.GONE);
        this.recyclerViewSmoothFilter.setVisibility(View.GONE);
        this.recyclerViewColdFilter.setVisibility(View.GONE);
        this.recyclerViewWarmFilter.setVisibility(View.GONE);
        this.recyclerViewLegacyFilter.setVisibility(View.GONE);
        this.textViewListAllFilter.setTextColor(ContextCompat.getColor(this, R.color.grayText));
        this.textViewListColdFilter.setTextColor(ContextCompat.getColor(this, R.color.grayText));
        this.textViewListBwFilter.setTextColor(ContextCompat.getColor(this, R.color.white));
        this.textViewListVintageFilter.setTextColor(ContextCompat.getColor(this, R.color.grayText));
        this.textViewListSmoothFilter.setTextColor(ContextCompat.getColor(this, R.color.grayText));
        this.textViewListWarmFilter.setTextColor(ContextCompat.getColor(this, R.color.grayText));
        this.textViewListLegacyFilter.setTextColor(ContextCompat.getColor(this, R.color.grayText));
        this.viewAll.setVisibility(View.INVISIBLE);
        this.viewCold.setVisibility(View.INVISIBLE);
        this.viewBW.setVisibility(View.VISIBLE);
        this.viewVintage.setVisibility(View.INVISIBLE);
        this.viewSmooth.setVisibility(View.INVISIBLE);
        this.viewWarm.setVisibility(View.INVISIBLE);
        this.viewLegacy.setVisibility(View.INVISIBLE);
    }

    public void setVintageFilter() {
        this.recyclerViewAllFilter.setVisibility(View.GONE);
        this.recyclerViewBwFilter.setVisibility(View.GONE);
        this.recyclerViewVintageFilter.setVisibility(View.VISIBLE);
        this.recyclerViewSmoothFilter.setVisibility(View.GONE);
        this.recyclerViewColdFilter.setVisibility(View.GONE);
        this.recyclerViewWarmFilter.setVisibility(View.GONE);
        this.recyclerViewLegacyFilter.setVisibility(View.GONE);
        this.textViewListAllFilter.setTextColor(ContextCompat.getColor(this, R.color.grayText));
        this.textViewListColdFilter.setTextColor(ContextCompat.getColor(this, R.color.grayText));
        this.textViewListBwFilter.setTextColor(ContextCompat.getColor(this, R.color.grayText));
        this.textViewListVintageFilter.setTextColor(ContextCompat.getColor(this, R.color.white));
        this.textViewListSmoothFilter.setTextColor(ContextCompat.getColor(this, R.color.grayText));
        this.textViewListWarmFilter.setTextColor(ContextCompat.getColor(this, R.color.grayText));
        this.textViewListLegacyFilter.setTextColor(ContextCompat.getColor(this, R.color.grayText));
        this.viewAll.setVisibility(View.INVISIBLE);
        this.viewCold.setVisibility(View.INVISIBLE);
        this.viewBW.setVisibility(View.INVISIBLE);
        this.viewVintage.setVisibility(View.VISIBLE);
        this.viewSmooth.setVisibility(View.INVISIBLE);
        this.viewWarm.setVisibility(View.INVISIBLE);
        this.viewLegacy.setVisibility(View.INVISIBLE);
    }

    public void setSmoothFilter() {
        this.recyclerViewAllFilter.setVisibility(View.GONE);
        this.recyclerViewBwFilter.setVisibility(View.GONE);
        this.recyclerViewVintageFilter.setVisibility(View.GONE);
        this.recyclerViewSmoothFilter.setVisibility(View.VISIBLE);
        this.recyclerViewColdFilter.setVisibility(View.GONE);
        this.recyclerViewWarmFilter.setVisibility(View.GONE);
        this.recyclerViewLegacyFilter.setVisibility(View.GONE);
        this.textViewListAllFilter.setTextColor(ContextCompat.getColor(this, R.color.grayText));
        this.textViewListColdFilter.setTextColor(ContextCompat.getColor(this, R.color.grayText));
        this.textViewListBwFilter.setTextColor(ContextCompat.getColor(this, R.color.grayText));
        this.textViewListVintageFilter.setTextColor(ContextCompat.getColor(this, R.color.grayText));
        this.textViewListSmoothFilter.setTextColor(ContextCompat.getColor(this, R.color.white));
        this.textViewListWarmFilter.setTextColor(ContextCompat.getColor(this, R.color.grayText));
        this.textViewListLegacyFilter.setTextColor(ContextCompat.getColor(this, R.color.grayText));
        this.viewAll.setVisibility(View.INVISIBLE);
        this.viewCold.setVisibility(View.INVISIBLE);
        this.viewBW.setVisibility(View.INVISIBLE);
        this.viewVintage.setVisibility(View.INVISIBLE);
        this.viewSmooth.setVisibility(View.VISIBLE);
        this.viewWarm.setVisibility(View.INVISIBLE);
        this.viewLegacy.setVisibility(View.INVISIBLE);
    }

    public void setColdFilter() {
        this.recyclerViewAllFilter.setVisibility(View.GONE);
        this.recyclerViewBwFilter.setVisibility(View.GONE);
        this.recyclerViewVintageFilter.setVisibility(View.GONE);
        this.recyclerViewSmoothFilter.setVisibility(View.GONE);
        this.recyclerViewColdFilter.setVisibility(View.VISIBLE);
        this.recyclerViewWarmFilter.setVisibility(View.GONE);
        this.recyclerViewLegacyFilter.setVisibility(View.GONE);
        this.textViewListAllFilter.setTextColor(ContextCompat.getColor(this, R.color.grayText));
        this.textViewListColdFilter.setTextColor(ContextCompat.getColor(this, R.color.white));
        this.textViewListBwFilter.setTextColor(ContextCompat.getColor(this, R.color.grayText));
        this.textViewListVintageFilter.setTextColor(ContextCompat.getColor(this, R.color.grayText));
        this.textViewListSmoothFilter.setTextColor(ContextCompat.getColor(this, R.color.grayText));
        this.textViewListWarmFilter.setTextColor(ContextCompat.getColor(this, R.color.grayText));
        this.textViewListLegacyFilter.setTextColor(ContextCompat.getColor(this, R.color.grayText));
        this.viewAll.setVisibility(View.INVISIBLE);
        this.viewCold.setVisibility(View.VISIBLE);
        this.viewBW.setVisibility(View.INVISIBLE);
        this.viewVintage.setVisibility(View.INVISIBLE);
        this.viewSmooth.setVisibility(View.INVISIBLE);
        this.viewWarm.setVisibility(View.INVISIBLE);
        this.viewLegacy.setVisibility(View.INVISIBLE);
    }

    public void setWarmFilter() {
        this.recyclerViewAllFilter.setVisibility(View.GONE);
        this.recyclerViewBwFilter.setVisibility(View.GONE);
        this.recyclerViewVintageFilter.setVisibility(View.GONE);
        this.recyclerViewSmoothFilter.setVisibility(View.GONE);
        this.recyclerViewColdFilter.setVisibility(View.GONE);
        this.recyclerViewWarmFilter.setVisibility(View.VISIBLE);
        this.recyclerViewLegacyFilter.setVisibility(View.GONE);
        this.textViewListAllFilter.setTextColor(ContextCompat.getColor(this, R.color.grayText));
        this.textViewListColdFilter.setTextColor(ContextCompat.getColor(this, R.color.grayText));
        this.textViewListBwFilter.setTextColor(ContextCompat.getColor(this, R.color.grayText));
        this.textViewListVintageFilter.setTextColor(ContextCompat.getColor(this, R.color.grayText));
        this.textViewListSmoothFilter.setTextColor(ContextCompat.getColor(this, R.color.grayText));
        this.textViewListWarmFilter.setTextColor(ContextCompat.getColor(this, R.color.white));
        this.textViewListLegacyFilter.setTextColor(ContextCompat.getColor(this, R.color.grayText));
        this.viewAll.setVisibility(View.INVISIBLE);
        this.viewCold.setVisibility(View.INVISIBLE);
        this.viewBW.setVisibility(View.INVISIBLE);
        this.viewVintage.setVisibility(View.INVISIBLE);
        this.viewSmooth.setVisibility(View.INVISIBLE);
        this.viewWarm.setVisibility(View.VISIBLE);
        this.viewLegacy.setVisibility(View.INVISIBLE);
    }

    public void setLegacyFilter() {
        this.recyclerViewAllFilter.setVisibility(View.GONE);
        this.recyclerViewBwFilter.setVisibility(View.GONE);
        this.recyclerViewVintageFilter.setVisibility(View.GONE);
        this.recyclerViewSmoothFilter.setVisibility(View.GONE);
        this.recyclerViewColdFilter.setVisibility(View.GONE);
        this.recyclerViewWarmFilter.setVisibility(View.GONE);
        this.recyclerViewLegacyFilter.setVisibility(View.VISIBLE);
        this.textViewListAllFilter.setTextColor(ContextCompat.getColor(this, R.color.grayText));
        this.textViewListColdFilter.setTextColor(ContextCompat.getColor(this, R.color.grayText));
        this.textViewListBwFilter.setTextColor(ContextCompat.getColor(this, R.color.grayText));
        this.textViewListVintageFilter.setTextColor(ContextCompat.getColor(this, R.color.grayText));
        this.textViewListSmoothFilter.setTextColor(ContextCompat.getColor(this, R.color.grayText));
        this.textViewListWarmFilter.setTextColor(ContextCompat.getColor(this, R.color.grayText));
        this.textViewListLegacyFilter.setTextColor(ContextCompat.getColor(this, R.color.white));
        this.viewAll.setVisibility(View.INVISIBLE);
        this.viewCold.setVisibility(View.INVISIBLE);
        this.viewBW.setVisibility(View.INVISIBLE);
        this.viewVintage.setVisibility(View.INVISIBLE);
        this.viewSmooth.setVisibility(View.INVISIBLE);
        this.viewWarm.setVisibility(View.INVISIBLE);
        this.viewLegacy.setVisibility(View.VISIBLE);
    }

    public void setBackgroundColor() {
        this.recycler_view_color.scrollToPosition(0);
        ((BackgroundGridAdapter) this.recycler_view_color.getAdapter()).setSelectedIndex(-1);
        this.recycler_view_color.getAdapter().notifyDataSetChanged();
        this.recycler_view_color.setVisibility(View.VISIBLE);
        this.recycler_view_gradient.setVisibility(View.GONE);
        this.recycler_view_blur.setVisibility(View.GONE);
        this.textViewListColor.setTextColor(ContextCompat.getColor(this, R.color.white));
        this.textViewListGradient.setTextColor(ContextCompat.getColor(this, R.color.grayText));
        this.textViewListBlur.setTextColor(ContextCompat.getColor(this, R.color.grayText));
        this.viewColor.setVisibility(View.VISIBLE);
        this.viewGradient.setVisibility(View.INVISIBLE);
        this.viewBlur.setVisibility(View.INVISIBLE);
    }

    public void setBackgroundGradient() {
        this.recycler_view_gradient.scrollToPosition(0);
        ((BackgroundGridAdapter) this.recycler_view_gradient.getAdapter()).setSelectedIndex(-1);
        this.recycler_view_gradient.getAdapter().notifyDataSetChanged();

        this.recycler_view_color.setVisibility(View.GONE);
        this.recycler_view_gradient.setVisibility(View.VISIBLE);
        this.recycler_view_blur.setVisibility(View.GONE);
        this.textViewListColor.setTextColor(ContextCompat.getColor(this, R.color.grayText));
        this.textViewListGradient.setTextColor(ContextCompat.getColor(this, R.color.white));
        this.textViewListBlur.setTextColor(ContextCompat.getColor(this, R.color.grayText));
        this.viewColor.setVisibility(View.INVISIBLE);
        this.viewGradient.setVisibility(View.VISIBLE);
        this.viewBlur.setVisibility(View.INVISIBLE);
    }

    public void selectBackgroundBlur() {
        ArrayList arrayList = new ArrayList();
        for (QueShotGrid drawable : this.queShotGridView.getQueShotGrids()) {
            arrayList.add(drawable.getDrawable());
        }
        BackgroundGridAdapter backgroundGridAdapter = new BackgroundGridAdapter(getApplicationContext(), this, (List<Drawable>) arrayList);
        backgroundGridAdapter.setSelectedIndex(-1);
        this.recycler_view_blur.setAdapter(backgroundGridAdapter);
        this.recycler_view_color.setVisibility(View.GONE);
        this.recycler_view_gradient.setVisibility(View.GONE);
        this.recycler_view_blur.setVisibility(View.VISIBLE);
        this.textViewListColor.setTextColor(ContextCompat.getColor(this, R.color.grayText));
        this.textViewListGradient.setTextColor(ContextCompat.getColor(this, R.color.grayText));
        this.textViewListBlur.setTextColor(ContextCompat.getColor(this, R.color.white));
        this.viewColor.setVisibility(View.INVISIBLE);
        this.viewGradient.setVisibility(View.INVISIBLE);
        this.viewBlur.setVisibility(View.VISIBLE);
    }


    public void setDown() {
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(QueShotGridActivity.this.constraint_layout_collage_layout);
        //constraintSet.connect(QueShotGridActivity.this.constraint_layout_wrapper_collage_view.getId(), 3, QueShotGridActivity.this.adView.getId(), 4, 0);
        constraintSet.connect(QueShotGridActivity.this.constraint_layout_wrapper_collage_view.getId(), 1, QueShotGridActivity.this.constraint_layout_collage_layout.getId(), 1, 0);
        constraintSet.connect(QueShotGridActivity.this.constraint_layout_wrapper_collage_view.getId(), 4, QueShotGridActivity.this.guidelineTool.getId(), 3, 0);
        constraintSet.connect(QueShotGridActivity.this.constraint_layout_wrapper_collage_view.getId(), 2, QueShotGridActivity.this.constraint_layout_collage_layout.getId(), 2, 0);
        constraintSet.applyTo(QueShotGridActivity.this.constraint_layout_collage_layout);
    }

    public void setLayer() {
        this.recycler_view_collage.setVisibility(View.VISIBLE);
        this.recycler_view_ratio.setVisibility(View.GONE);
        this.linearLayoutBorder.setVisibility(View.GONE);
        this.textViewListLayer.setTextColor(ContextCompat.getColor(this, R.color.white));
        this.textViewListBorder.setTextColor(ContextCompat.getColor(this, R.color.grayText));
        this.textViewListRatio.setTextColor(ContextCompat.getColor(this, R.color.grayText));
        this.viewCollage.setVisibility(View.VISIBLE);
        this.viewBorder.setVisibility(View.INVISIBLE);
        this.viewRatio.setVisibility(View.INVISIBLE);
    }

    public void setBorder() {
        this.recycler_view_collage.setVisibility(View.GONE);
        this.recycler_view_ratio.setVisibility(View.GONE);
        this.linearLayoutBorder.setVisibility(View.VISIBLE);
        this.textViewListLayer.setTextColor(ContextCompat.getColor(this, R.color.grayText));
        this.textViewListBorder.setTextColor(ContextCompat.getColor(this, R.color.white));
        this.textViewListRatio.setTextColor(ContextCompat.getColor(this, R.color.grayText));
        this.viewCollage.setVisibility(View.INVISIBLE);
        this.viewBorder.setVisibility(View.VISIBLE);
        this.viewRatio.setVisibility(View.INVISIBLE);
        this.seekBarPadding.setProgress((int) this.queShotGridView.getCollagePadding());
        this.seekBarRadius.setProgress((int) this.queShotGridView.getCollageRadian());
    }

    public void setRatio() {
        this.recycler_view_collage.setVisibility(View.GONE);
        this.recycler_view_ratio.setVisibility(View.VISIBLE);
        this.linearLayoutBorder.setVisibility(View.GONE);
        this.textViewListLayer.setTextColor(ContextCompat.getColor(this, R.color.grayText));
        this.textViewListBorder.setTextColor(ContextCompat.getColor(this, R.color.grayText));
        this.textViewListRatio.setTextColor(ContextCompat.getColor(this, R.color.white));
        this.viewCollage.setVisibility(View.INVISIBLE);
        this.viewBorder.setVisibility(View.INVISIBLE);
        this.viewRatio.setVisibility(View.VISIBLE);
    }

    public void setGuideLineTools(){
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(this.constraint_layout_collage_layout);
        constraintSet.connect(this.constraint_layout_wrapper_collage_view.getId(), 1, this.constraint_layout_collage_layout.getId(), 1, 0);
        constraintSet.connect(this.constraint_layout_wrapper_collage_view.getId(), 4, this.guidelineTool.getId(), 3, 0);
        constraintSet.connect(this.constraint_layout_wrapper_collage_view.getId(), 2, this.constraint_layout_collage_layout.getId(), 2, 0);
        constraintSet.applyTo(this.constraint_layout_collage_layout);
    }

    public void setGuideLine(){
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(this.constraint_layout_collage_layout);
        constraintSet.connect(this.constraint_layout_wrapper_collage_view.getId(), 1, this.constraint_layout_collage_layout.getId(), 1, 0);
        constraintSet.connect(this.constraint_layout_wrapper_collage_view.getId(), 4, this.guidelineLayout.getId(), 3, 0);
        constraintSet.connect(this.constraint_layout_wrapper_collage_view.getId(), 2, this.constraint_layout_collage_layout.getId(), 2, 0);
        constraintSet.applyTo(this.constraint_layout_collage_layout);
    }

    public void onToolSelected(Module module) {
        this.moduleToolsId = module;
        switch (module) {
            case LAYER:
                setLayer();
                setGuideLine();
                this.constrant_layout_change_Layout.setVisibility(View.VISIBLE);
                this.recyclerViewTools.setVisibility(View.GONE);
                this.recyclerViewToolsCollage.setVisibility(View.GONE);
                this.queShotLayout = this.queShotGridView.getQueShotLayout();
                this.aspectRatio = this.queShotGridView.getAspectRatio();
                this.BorderRadius = this.queShotGridView.getCollageRadian();
                this.Padding = this.queShotGridView.getCollagePadding();
                this.recycler_view_collage.scrollToPosition(0);
                ((GridAdapter) this.recycler_view_collage.getAdapter()).setSelectedIndex(-1);
                this.recycler_view_collage.getAdapter().notifyDataSetChanged();
                this.recycler_view_ratio.scrollToPosition(0);
                ((AspectAdapter) this.recycler_view_ratio.getAdapter()).setLastSelectedView(-1);
                this.recycler_view_ratio.getAdapter().notifyDataSetChanged();
                this.queShotGridView.setLocked(false);
                this.queShotGridView.setTouchEnable(false);
                setGoneSave();
                return;
            case PADDING:
                setBorder();
                setGuideLine();
                this.constrant_layout_change_Layout.setVisibility(View.VISIBLE);
                this.recyclerViewTools.setVisibility(View.GONE);
                this.recyclerViewToolsCollage.setVisibility(View.GONE);
                this.queShotLayout = this.queShotGridView.getQueShotLayout();
                this.aspectRatio = this.queShotGridView.getAspectRatio();
                this.BorderRadius = this.queShotGridView.getCollageRadian();
                this.Padding = this.queShotGridView.getCollagePadding();
                this.recycler_view_collage.scrollToPosition(0);
                ((GridAdapter) this.recycler_view_collage.getAdapter()).setSelectedIndex(-1);
                this.recycler_view_collage.getAdapter().notifyDataSetChanged();
                this.recycler_view_ratio.scrollToPosition(0);
                ((AspectAdapter) this.recycler_view_ratio.getAdapter()).setLastSelectedView(-1);
                this.recycler_view_ratio.getAdapter().notifyDataSetChanged();
                this.queShotGridView.setLocked(false);
                this.queShotGridView.setTouchEnable(false);
                setGoneSave();
                return;
            case RATIO:
                setRatio();
                setGuideLine();
                this.constrant_layout_change_Layout.setVisibility(View.VISIBLE);
                this.recyclerViewTools.setVisibility(View.GONE);
                this.recyclerViewToolsCollage.setVisibility(View.GONE);
                this.queShotLayout = this.queShotGridView.getQueShotLayout();
                this.aspectRatio = this.queShotGridView.getAspectRatio();
                this.BorderRadius = this.queShotGridView.getCollageRadian();
                this.Padding = this.queShotGridView.getCollagePadding();
                this.recycler_view_collage.scrollToPosition(0);
                ((GridAdapter) this.recycler_view_collage.getAdapter()).setSelectedIndex(-1);
                this.recycler_view_collage.getAdapter().notifyDataSetChanged();
                this.recycler_view_ratio.scrollToPosition(0);
                ((AspectAdapter) this.recycler_view_ratio.getAdapter()).setLastSelectedView(-1);
                this.recycler_view_ratio.getAdapter().notifyDataSetChanged();
                this.queShotGridView.setLocked(false);
                this.queShotGridView.setTouchEnable(false);
                setGoneSave();
                return;
            case FILTER:
                if (this.drawableList.isEmpty()) {
                    for (QueShotGrid drawable : this.queShotGridView.getQueShotGrids()) {
                        this.drawableList.add(drawable.getDrawable());
                    }
                }
                new allFilters().execute();
                new bwFilters().execute();
                new vintageFilters().execute();
                new smoothFilters().execute();
                new coldFilters().execute();
                new warmFilters().execute();
                new legacyFilters().execute();
                setGoneSave();
                return;
            case STICKER:
                setGuideLine();
                this.constrant_layout_change_Layout.setVisibility(View.GONE);
                this.constraint_layout_filter_layout.setVisibility(View.GONE);
                this.constraint_layout_change_background.setVisibility(View.GONE);
                this.recyclerViewTools.setVisibility(View.GONE);
                this.recyclerViewToolsCollage.setVisibility(View.GONE);
                this.constraint_layout_sticker.setVisibility(View.VISIBLE);
                this.queShotGridView.updateLayout(this.queShotLayout);
                this.queShotGridView.setCollagePadding(this.Padding);
                this.queShotGridView.setCollageRadian(this.BorderRadius);
                getWindowManager().getDefaultDisplay().getSize(new Point());
                onNewAspectRatioSelected(this.aspectRatio);
                this.queShotGridView.setAspectRatio(this.aspectRatio);
                for (int i = 0; i < this.drawableList.size(); i++) {
                    this.queShotGridView.getQueShotGrids().get(i).setDrawable(this.drawableList.get(i));
                }
                this.queShotGridView.invalidate();
                if (this.currentBackgroundState.isColor) {
                    this.queShotGridView.setBackgroundResourceMode(0);
                    this.queShotGridView.setBackgroundColor(this.currentBackgroundState.drawableId);
                }  else {
                    this.queShotGridView.setBackgroundResourceMode(1);
                    if (this.currentBackgroundState.drawable != null) {
                        this.queShotGridView.setBackground(this.currentBackgroundState.drawable);
                    } else {
                        this.queShotGridView.setBackgroundResource(this.currentBackgroundState.drawableId);
                    }
                }
                setGoneSave();
                this.queShotGridView.setLocked(false);
                this.queShotGridView.setTouchEnable(false);
                this.linear_layout_wrapper_sticker_list.setVisibility(View.VISIBLE);
                return;
            case GRADIENT:
                setGuideLine();
                this.constraint_layout_change_background.setVisibility(View.VISIBLE);
                this.recyclerViewTools.setVisibility(View.GONE);
                this.recyclerViewToolsCollage.setVisibility(View.GONE);
                this.queShotGridView.setLocked(false);
                this.queShotGridView.setTouchEnable(false);
                setGoneSave();
                setBackgroundColor();
                if (this.queShotGridView.getBackgroundResourceMode() == 0) {
                    this.currentBackgroundState.isColor = true;
                    this.currentBackgroundState.isBitmap = false;
                    this.currentBackgroundState.drawableId = ((ColorDrawable) this.queShotGridView.getBackground()).getColor();
                    return;
                } else if (this.queShotGridView.getBackgroundResourceMode() == 2 || (this.queShotGridView.getBackground() instanceof ColorDrawable)) {
                    this.currentBackgroundState.isBitmap = true;
                    this.currentBackgroundState.isColor = false;
                    this.currentBackgroundState.drawable = this.queShotGridView.getBackground();
                    return;
                } else if (this.queShotGridView.getBackground() instanceof GradientDrawable) {
                    this.currentBackgroundState.isBitmap = false;
                    this.currentBackgroundState.isColor = false;
                    this.currentBackgroundState.drawable = this.queShotGridView.getBackground();
                    return;
                } else {
                    return;
                }
            default:
        }
    }


    public void loadPhoto() {
        final int i;
        final ArrayList arrayList = new ArrayList();
        if (this.stringList.size() > this.queShotLayout.getAreaCount()) {
            i = this.queShotLayout.getAreaCount();
        } else {
            i = this.stringList.size();
        }
        for (int i2 = 0; i2 < i; i2++) {
            Target r4 = new Target() {
                public void onBitmapFailed(Exception exc, Drawable drawable) {
                }

                public void onPrepareLoad(Drawable drawable) {
                }

                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {
                    int width = bitmap.getWidth();
                    float f = (float) width;
                    float height = (float) bitmap.getHeight();
                    float max = Math.max(f / f, height / f);
                    if (max > 1.0f) {
                        bitmap = Bitmap.createScaledBitmap(bitmap, (int) (f / max), (int) (height / max), false);
                    }
                    arrayList.add(bitmap);
                    if (arrayList.size() == i) {
                        if (QueShotGridActivity.this.stringList.size() < QueShotGridActivity.this.queShotLayout.getAreaCount()) {
                            for (int i = 0; i < QueShotGridActivity.this.queShotLayout.getAreaCount(); i++) {
                                QueShotGridActivity.this.queShotGridView.addQuShotCollage((Bitmap) arrayList.get(i % i));
                            }
                        } else {
                            QueShotGridActivity.this.queShotGridView.addPieces(arrayList);
                        }
                    }
                    QueShotGridActivity.this.targets.remove(this);
                }
            };
            Picasso picasso = Picasso.get();
            picasso.load("file:///" + this.stringList.get(i2)).resize(this.deviceWidth, this.deviceWidth).centerInside().config(Bitmap.Config.RGB_565).into((Target) r4);
            this.targets.add(r4);
        }
    }

    private void setOnBackPressDialog() {
        final Dialog dialogOnBackPressed = new Dialog(QueShotGridActivity.this, R.style.UploadDialog);
        dialogOnBackPressed.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogOnBackPressed.setContentView(R.layout.dialog_exit);
        dialogOnBackPressed.setCancelable(true);
        dialogOnBackPressed.show();
        this.textViewCancel = dialogOnBackPressed.findViewById(R.id.textViewCancel);
        this.textViewDiscard = dialogOnBackPressed.findViewById(R.id.textViewDiscard);

        this.textViewDiscard.setOnClickListener(view -> {
            dialogOnBackPressed.dismiss();
            QueShotGridActivity.this.moduleToolsId = null;
            QueShotGridActivity.this.finish();
            finish();
        });

        this.textViewCancel.setOnClickListener(view -> {
            dialogOnBackPressed.dismiss();
        });
    }

    public void setGoneSave() {
        this.constraint_save_control.setVisibility(View.GONE);
    }

    public void setVisibleSave() {
        this.constraint_save_control.setVisibility(View.VISIBLE);
    }

    public void onBackPressed() {
        if (this.moduleToolsId == null) {
            super.onBackPressed();
            return;
        }
        try {
            switch (this.moduleToolsId) {
                case PADDING:
                case RATIO:
                case LAYER:
                    setGuideLineTools();
                    this.recyclerViewTools.setVisibility(View.VISIBLE);
                    this.constrant_layout_change_Layout.setVisibility(View.GONE);
                    this.recyclerViewToolsCollage.setVisibility(View.VISIBLE);
                    setVisibleSave();
                    this.queShotGridView.updateLayout(this.queShotLayout);
                    this.queShotGridView.setCollagePadding(this.Padding);
                    this.queShotGridView.setCollageRadian(this.BorderRadius);
                    this.moduleToolsId = Module.NONE;
                    getWindowManager().getDefaultDisplay().getSize(new Point());
                    onNewAspectRatioSelected(this.aspectRatio);
                    this.queShotGridView.setAspectRatio(this.aspectRatio);
                    this.queShotGridView.setLocked(true);
                    this.queShotGridView.setTouchEnable(true);
                    return;
                case FILTER:
                    setGuideLineTools();
                    this.recyclerViewTools.setVisibility(View.VISIBLE);
                    this.constraint_layout_filter_layout.setVisibility(View.GONE);
                    this.recyclerViewToolsCollage.setVisibility(View.VISIBLE);
                    this.queShotGridView.setLocked(true);
                    this.queShotGridView.setTouchEnable(true);
                    for (int i = 0; i < this.drawableList.size(); i++) {
                        this.queShotGridView.getQueShotGrids().get(i).setDrawable(this.drawableList.get(i));
                    }
                    this.queShotGridView.invalidate();
                    setVisibleSave();
                    this.moduleToolsId = Module.NONE;
                    return;
                case STICKER:
                    setGuideLineTools();
                    if (this.queShotGridView.getStickers().size() <= 0) {
                        this.linear_layout_wrapper_sticker_list.setVisibility(View.VISIBLE);
                        this.imageViewAddSticker.setVisibility(View.GONE);
                        this.queShotGridView.setHandlingSticker((Sticker) null);
                        this.recyclerViewTools.setVisibility(View.VISIBLE);
                        this.constraint_layout_sticker.setVisibility(View.GONE);
                        this.recyclerViewToolsCollage.setVisibility(View.VISIBLE);
                        this.queShotGridView.setLocked(true);
                        this.moduleToolsId = Module.NONE;
                    } else if (this.imageViewAddSticker.getVisibility() == View.VISIBLE) {
                        this.queShotGridView.getStickers().clear();
                        this.imageViewAddSticker.setVisibility(View.GONE);
                        this.queShotGridView.setHandlingSticker(null);
                        this.linear_layout_wrapper_sticker_list.setVisibility(View.VISIBLE);
                        this.recyclerViewToolsCollage.setVisibility(View.VISIBLE);
                        this.recyclerViewTools.setVisibility(View.VISIBLE);
                        this.constraint_layout_sticker.setVisibility(View.GONE);
                        this.queShotGridView.setLocked(true);
                        this.queShotGridView.setTouchEnable(true);
                        this.moduleToolsId = Module.NONE;
                    } else {
                        this.linear_layout_wrapper_sticker_list.setVisibility(View.GONE);
                        this.imageViewAddSticker.setVisibility(View.VISIBLE);
                        this.recyclerViewToolsCollage.setVisibility(View.VISIBLE);
                        this.recyclerViewTools.setVisibility(View.VISIBLE);
                    }
                    this.linear_layout_wrapper_sticker_list.setVisibility(View.VISIBLE);
                    this.recyclerViewTools.setVisibility(View.VISIBLE);
                    this.constraint_layout_sticker.setVisibility(View.GONE);
                    setVisibleSave();
                    return;
                case GRADIENT:
                    setGuideLineTools();
                    this.recyclerViewTools.setVisibility(View.VISIBLE);
                    this.constraint_layout_change_background.setVisibility(View.GONE);
                    this.recyclerViewToolsCollage.setVisibility(View.VISIBLE);
                    this.queShotGridView.setLocked(true);
                    this.queShotGridView.setTouchEnable(true);
                    if (this.currentBackgroundState.isColor) {
                        this.queShotGridView.setBackgroundResourceMode(0);
                        this.queShotGridView.setBackgroundColor(this.currentBackgroundState.drawableId);
                    } else if (this.currentBackgroundState.isBitmap) {
                        this.queShotGridView.setBackgroundResourceMode(2);
                        this.queShotGridView.setBackground(this.currentBackgroundState.drawable);
                    } else {
                        this.queShotGridView.setBackgroundResourceMode(1);
                        if (this.currentBackgroundState.drawable != null) {
                            this.queShotGridView.setBackground(this.currentBackgroundState.drawable);
                        } else {
                            this.queShotGridView.setBackgroundResource(this.currentBackgroundState.drawableId);
                        }
                    }
                    setVisibleSave();
                    this.moduleToolsId = Module.NONE;
                    return;
                case COLLAGE:
                    setVisibleSave();
                    setGuideLineTools();
                    this.recyclerViewTools.setVisibility(View.VISIBLE);
                    this.recyclerViewToolsCollage.setVisibility(View.GONE);
                    this.moduleToolsId = Module.NONE;
                    this.queShotGridView.setQueShotGrid(null);
                    this.queShotGridView.setPrevHandlingQueShotGrid(null);
                    this.queShotGridView.invalidate();
                    this.moduleToolsId = Module.NONE;
                    return;
                case NONE:
                    setOnBackPressDialog();
                    return;
                default:
                    super.onBackPressed();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onItemClick(QueShotLayout puzzleLayout2, int i) {
        QueShotLayout parse = QueShotLayoutParser.parse(puzzleLayout2.generateInfo());
        puzzleLayout2.setRadian(this.queShotGridView.getCollageRadian());
        puzzleLayout2.setPadding(this.queShotGridView.getCollagePadding());
        this.queShotGridView.updateLayout(parse);
    }

    public void onNewAspectRatioSelected(AspectRatio aspectRatio) {
        Display defaultDisplay = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getSize(point);
        int[] calculateWidthAndHeight = calculateWidthAndHeight(aspectRatio, point);
        this.queShotGridView.setLayoutParams(new ConstraintLayout.LayoutParams(calculateWidthAndHeight[0], calculateWidthAndHeight[1]));
        this.queShotGridView.setAspectRatio(aspectRatio);
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(this.constraint_layout_wrapper_collage_view);
        constraintSet.connect(this.queShotGridView.getId(), 3, this.constraint_layout_wrapper_collage_view.getId(), 3, 0);
        constraintSet.connect(this.queShotGridView.getId(), 1, this.constraint_layout_wrapper_collage_view.getId(), 1, 0);
        constraintSet.connect(this.queShotGridView.getId(), 4, this.constraint_layout_wrapper_collage_view.getId(), 4, 0);
        constraintSet.connect(this.queShotGridView.getId(), 2, this.constraint_layout_wrapper_collage_view.getId(), 2, 0);
        constraintSet.applyTo(this.constraint_layout_wrapper_collage_view);
    }

    public void replaceCurrentPiece(String str) {
        new OnLoadBitmapFromUri().execute(str);
    }

    private int[] calculateWidthAndHeight(AspectRatio aspectRatio, Point point) {
        int height = this.constraint_layout_wrapper_collage_view.getHeight();
        if (aspectRatio.getHeight() > aspectRatio.getWidth()) {
            int ratio = (int) (aspectRatio.getRatio() * ((float) height));
            if (ratio < point.x) {
                return new int[]{ratio, height};
            }
            return new int[]{point.x, (int) (((float) point.x) / aspectRatio.getRatio())};
        }
        int ratio2 = (int) (((float) point.x) / aspectRatio.getRatio());
        if (ratio2 > height) {
            return new int[]{(int) (((float) height) * aspectRatio.getRatio()), height};
        }
        return new int[]{point.x, ratio2};
    }

    public void addSticker(Bitmap bitmap) {
        this.queShotGridView.addSticker(new DrawableSticker(new BitmapDrawable(getResources(), bitmap)));
        this.linear_layout_wrapper_sticker_list.setVisibility(View.GONE);
        this.imageViewAddSticker.setVisibility(View.VISIBLE);
    }

    public void onBackgroundSelected(final BackgroundGridAdapter.SquareView squareView) {
        if (squareView.isColor) {
            this.queShotGridView.setBackgroundColor(squareView.drawableId);
            this.queShotGridView.setBackgroundResourceMode(0);
        } else if (squareView.drawable != null) {
            this.queShotGridView.setBackgroundResourceMode(2);
            new AsyncTask<Void, Bitmap, Bitmap>() {

                public Bitmap doInBackground(Void... voidArr) {
                    return FilterUtils.getBlurImageFromBitmap(((BitmapDrawable) squareView.drawable).getBitmap(), 5.0f);
                }


                public void onPostExecute(Bitmap bitmap) {
                    QueShotGridActivity.this.queShotGridView.setBackground(new BitmapDrawable(QueShotGridActivity.this.getResources(), bitmap));
                }
            }.execute();
        } else {
            this.queShotGridView.setBackgroundResource(squareView.drawableId);
            this.queShotGridView.setBackgroundResourceMode(1);
        }
    }

    public void onFilterSelected(String str) {

        new LoadBitmapWithFilter().execute(new String[]{str});

    }

    public void finishCrop(Bitmap bitmap) {
        this.queShotGridView.replace(bitmap, "");
    }

    public void onSaveFilter(Bitmap bitmap) {
        this.queShotGridView.replace(bitmap, "");
    }

    @Override
    public void onPieceFuncSelected(Module toolType) {
        switch (toolType) {
            case REPLACE:
                QueShotPickerView.builder().setPhotoCount(1).setPreviewEnabled(false).setShowCamera(false).setForwardMain(true).start(this);
                return;
            case H_FLIP:
                this.queShotGridView.flipHorizontally();
                return;
            case V_FLIP:
                this.queShotGridView.flipVertically();
                return;
            case ROTATE:
                this.queShotGridView.rotate(90.0f);
                return;
            case CROP:
                CropFragment.show(this, this, ((BitmapDrawable) this.queShotGridView.getQueShotGrid().getDrawable()).getBitmap());
                return;
            case FILTER:
                new LoadFilterBitmapForCurrentPiece().execute();
                return;
            case DODGE:
                new LoadDodgeBitmapForCurrentPiece().execute();
                return;
            case DIVIDE:
                new LoadDivideBitmapForCurrentPiece().execute();
                return;
            case BURN:
                new LoadBurnBitmapForCurrentPiece().execute();
                return;
        }
    }

    class allFilters extends AsyncTask<Void, Void, Void> {
        allFilters() { }

        public void onPreExecute() {
            QueShotGridActivity.this.setLoading(true);
        }

        @SuppressLint("WrongThread")
        public Void doInBackground(Void... voidArr) {
            QueShotGridActivity.this.listFilterAll.clear();
            QueShotGridActivity.this.listFilterAll.addAll(FilterCodeAsset.getListBitmapFilterAll(ThumbnailUtils.extractThumbnail(((BitmapDrawable) QueShotGridActivity.this.queShotGridView.getQueShotGrids().get(0).getDrawable()).getBitmap(), 100, 100)));
            return null;
        }

        public void onPostExecute(Void voidR) {
            QueShotGridActivity.this.recyclerViewAllFilter.setAdapter(new FilterAdapter(QueShotGridActivity.this.listFilterAll, QueShotGridActivity.this, QueShotGridActivity.this.getApplicationContext(), Arrays.asList(FilterCodeAsset.ALL_FILTERS)));
            QueShotGridActivity.this.constraint_layout_filter_layout.setVisibility(View.VISIBLE);
            QueShotGridActivity.this.constraint_layout_filter_save.setVisibility(View.VISIBLE);
            QueShotGridActivity.this.recyclerViewTools.setVisibility(View.GONE);
            QueShotGridActivity.this.recyclerViewToolsCollage.setVisibility(View.GONE);
            QueShotGridActivity.this.queShotGridView.setLocked(false);
            QueShotGridActivity.this.queShotGridView.setTouchEnable(false);
            QueShotGridActivity.this.setLoading(false);
            setGuideLine();
        }
    }

    class bwFilters extends AsyncTask<Void, Void, Void> {
        bwFilters() { }

        public void onPreExecute() {
            QueShotGridActivity.this.setLoading(true);
        }

        @SuppressLint("WrongThread")
        public Void doInBackground(Void... voidArr) {
            QueShotGridActivity.this.listFilterBW.clear();
            QueShotGridActivity.this.listFilterBW.addAll(FilterCodeAsset.getListBitmapFilterBW(ThumbnailUtils.extractThumbnail(((BitmapDrawable) QueShotGridActivity.this.queShotGridView.getQueShotGrids().get(0).getDrawable()).getBitmap(), 100, 100)));
            return null;
        }

        public void onPostExecute(Void voidR) {
            QueShotGridActivity.this.recyclerViewBwFilter.setAdapter(new FilterAdapter(QueShotGridActivity.this.listFilterBW, QueShotGridActivity.this, QueShotGridActivity.this.getApplicationContext(), Arrays.asList(FilterCodeAsset.BW_FILTERS)));
            QueShotGridActivity.this.queShotGridView.setLocked(false);
            QueShotGridActivity.this.queShotGridView.setTouchEnable(false);
            QueShotGridActivity.this.setLoading(false);
        }
    }

    class vintageFilters extends AsyncTask<Void, Void, Void> {
        vintageFilters() { }

        public void onPreExecute() {
            QueShotGridActivity.this.setLoading(true);
        }

        @SuppressLint("WrongThread")
        public Void doInBackground(Void... voidArr) {
            QueShotGridActivity.this.listFilterVintage.clear();
            QueShotGridActivity.this.listFilterVintage.addAll(FilterCodeAsset.getListBitmapFilterVintage(ThumbnailUtils.extractThumbnail(((BitmapDrawable) QueShotGridActivity.this.queShotGridView.getQueShotGrids().get(0).getDrawable()).getBitmap(), 100, 100)));
            return null;
        }

        public void onPostExecute(Void voidR) {
            QueShotGridActivity.this.recyclerViewVintageFilter.setAdapter(new FilterAdapter(QueShotGridActivity.this.listFilterVintage, QueShotGridActivity.this, QueShotGridActivity.this.getApplicationContext(), Arrays.asList(FilterCodeAsset.VINTAGE_FILTERS)));
            QueShotGridActivity.this.queShotGridView.setLocked(false);
            QueShotGridActivity.this.queShotGridView.setTouchEnable(false);
            QueShotGridActivity.this.setLoading(false);
        }
    }

    class smoothFilters extends AsyncTask<Void, Void, Void> {
        smoothFilters() { }

        public void onPreExecute() {
            QueShotGridActivity.this.setLoading(true);
        }

        @SuppressLint("WrongThread")
        public Void doInBackground(Void... voidArr) {
            QueShotGridActivity.this.listFilterSmooth.clear();
            QueShotGridActivity.this.listFilterSmooth.addAll(FilterCodeAsset.getListBitmapFilterSmooth(ThumbnailUtils.extractThumbnail(((BitmapDrawable) QueShotGridActivity.this.queShotGridView.getQueShotGrids().get(0).getDrawable()).getBitmap(), 100, 100)));
            return null;
        }

        public void onPostExecute(Void voidR) {
            QueShotGridActivity.this.recyclerViewSmoothFilter.setAdapter(new FilterAdapter(QueShotGridActivity.this.listFilterSmooth, QueShotGridActivity.this, QueShotGridActivity.this.getApplicationContext(), Arrays.asList(FilterCodeAsset.SMOOTH_FILTERS)));
            QueShotGridActivity.this.queShotGridView.setLocked(false);
            QueShotGridActivity.this.queShotGridView.setTouchEnable(false);
            QueShotGridActivity.this.setLoading(false);
        }
    }

    class coldFilters extends AsyncTask<Void, Void, Void> {
        coldFilters() { }

        public void onPreExecute() {
            QueShotGridActivity.this.setLoading(true);
        }

        @SuppressLint("WrongThread")
        public Void doInBackground(Void... voidArr) {
            QueShotGridActivity.this.listFilterCold.clear();
            QueShotGridActivity.this.listFilterCold.addAll(FilterCodeAsset.getListBitmapFilterCold(ThumbnailUtils.extractThumbnail(((BitmapDrawable) QueShotGridActivity.this.queShotGridView.getQueShotGrids().get(0).getDrawable()).getBitmap(), 100, 100)));
            return null;
        }

        public void onPostExecute(Void voidR) {
            QueShotGridActivity.this.recyclerViewColdFilter.setAdapter(new FilterAdapter(QueShotGridActivity.this.listFilterCold, QueShotGridActivity.this, QueShotGridActivity.this.getApplicationContext(), Arrays.asList(FilterCodeAsset.COLD_FILTERS)));
            QueShotGridActivity.this.queShotGridView.setLocked(false);
            QueShotGridActivity.this.queShotGridView.setTouchEnable(false);
            QueShotGridActivity.this.setLoading(false);
        }
    }

    class warmFilters extends AsyncTask<Void, Void, Void> {
        warmFilters() { }

        public void onPreExecute() {
            QueShotGridActivity.this.setLoading(true);
        }

        @SuppressLint("WrongThread")
        public Void doInBackground(Void... voidArr) {
            QueShotGridActivity.this.listFilterWarm.clear();
            QueShotGridActivity.this.listFilterWarm.addAll(FilterCodeAsset.getListBitmapFilterWarm(ThumbnailUtils.extractThumbnail(((BitmapDrawable) QueShotGridActivity.this.queShotGridView.getQueShotGrids().get(0).getDrawable()).getBitmap(), 100, 100)));
            return null;
        }

        public void onPostExecute(Void voidR) {
            QueShotGridActivity.this.recyclerViewWarmFilter.setAdapter(new FilterAdapter(QueShotGridActivity.this.listFilterWarm, QueShotGridActivity.this, QueShotGridActivity.this.getApplicationContext(), Arrays.asList(FilterCodeAsset.WARM_FILTERS)));
            QueShotGridActivity.this.queShotGridView.setLocked(false);
            QueShotGridActivity.this.queShotGridView.setTouchEnable(false);
            QueShotGridActivity.this.setLoading(false);
        }
    }

    class legacyFilters extends AsyncTask<Void, Void, Void> {
        legacyFilters() { }

        public void onPreExecute() {
            QueShotGridActivity.this.setLoading(true);
        }

        @SuppressLint("WrongThread")
        public Void doInBackground(Void... voidArr) {
            QueShotGridActivity.this.listFilterLegacy.clear();
            QueShotGridActivity.this.listFilterLegacy.addAll(FilterCodeAsset.getListBitmapFilterLegacy(ThumbnailUtils.extractThumbnail(((BitmapDrawable) QueShotGridActivity.this.queShotGridView.getQueShotGrids().get(0).getDrawable()).getBitmap(), 100, 100)));
            return null;
        }

        public void onPostExecute(Void voidR) {
            QueShotGridActivity.this.recyclerViewLegacyFilter.setAdapter(new FilterAdapter(QueShotGridActivity.this.listFilterLegacy, QueShotGridActivity.this, QueShotGridActivity.this.getApplicationContext(), Arrays.asList(FilterCodeAsset.LEGACY_FILTERS)));
            QueShotGridActivity.this.queShotGridView.setLocked(false);
            QueShotGridActivity.this.queShotGridView.setTouchEnable(false);
            QueShotGridActivity.this.setLoading(false);
        }
    }

    class LoadFilterBitmapForCurrentPiece extends AsyncTask<Void, List<Bitmap>, List<Bitmap>> {
        LoadFilterBitmapForCurrentPiece() {
        }
        public void onPreExecute() {
            QueShotGridActivity.this.setLoading(true);
        }
        @SuppressLint("WrongThread")
        public List<Bitmap> doInBackground(Void... voidArr) {
            return FilterCodeAsset.getListBitmapFilterAll(ThumbnailUtils.extractThumbnail(((BitmapDrawable) QueShotGridActivity.this.queShotGridView.getQueShotGrid().getDrawable()).getBitmap(), 100, 100));
        }

        public void onPostExecute(List<Bitmap> list) {
            QueShotGridActivity.this.setLoading(false);
            if (QueShotGridActivity.this.queShotGridView.getQueShotGrid() != null) {
                FilterFragment.show(QueShotGridActivity.this, QueShotGridActivity.this, ((BitmapDrawable) QueShotGridActivity.this.queShotGridView.getQueShotGrid().getDrawable()).getBitmap(), list);
            }
        }
    }

    class LoadDodgeBitmapForCurrentPiece extends AsyncTask<Void, List<Bitmap>, List<Bitmap>> {
        LoadDodgeBitmapForCurrentPiece() {
        }
        public void onPreExecute() {
            QueShotGridActivity.this.setLoading(true);
        }
        @SuppressLint("WrongThread")
        public List<Bitmap> doInBackground(Void... voidArr) {
            return EffectCodeAsset.getListBitmapDodgeEffect(ThumbnailUtils.extractThumbnail(((BitmapDrawable) QueShotGridActivity.this.queShotGridView.getQueShotGrid().getDrawable()).getBitmap(), 100, 100));
        }

        public void onPostExecute(List<Bitmap> list) {
            QueShotGridActivity.this.setLoading(false);
            if (QueShotGridActivity.this.queShotGridView.getQueShotGrid() != null) {
                DodgeFragment.show(QueShotGridActivity.this, QueShotGridActivity.this, ((BitmapDrawable) QueShotGridActivity.this.queShotGridView.getQueShotGrid().getDrawable()).getBitmap(), list);
            }
        }
    }

    class LoadDivideBitmapForCurrentPiece extends AsyncTask<Void, List<Bitmap>, List<Bitmap>> {
        LoadDivideBitmapForCurrentPiece() {
        }
        public void onPreExecute() {
            QueShotGridActivity.this.setLoading(true);
        }
        @SuppressLint("WrongThread")
        public List<Bitmap> doInBackground(Void... voidArr) {
            return EffectCodeAsset.getListBitmapDivideEffect(ThumbnailUtils.extractThumbnail(((BitmapDrawable) QueShotGridActivity.this.queShotGridView.getQueShotGrid().getDrawable()).getBitmap(), 100, 100));
        }

        public void onPostExecute(List<Bitmap> list) {
            QueShotGridActivity.this.setLoading(false);
            if (QueShotGridActivity.this.queShotGridView.getQueShotGrid() != null) {
                DivideFragment.show(QueShotGridActivity.this, QueShotGridActivity.this, ((BitmapDrawable) QueShotGridActivity.this.queShotGridView.getQueShotGrid().getDrawable()).getBitmap(), list);
            }
        }
    }

    class LoadBurnBitmapForCurrentPiece extends AsyncTask<Void, List<Bitmap>, List<Bitmap>> {
        LoadBurnBitmapForCurrentPiece() {
        }
        public void onPreExecute() {
            QueShotGridActivity.this.setLoading(true);
        }
        @SuppressLint("WrongThread")
        public List<Bitmap> doInBackground(Void... voidArr) {
            return EffectCodeAsset.getListBitmapColorEffect(ThumbnailUtils.extractThumbnail(((BitmapDrawable) QueShotGridActivity.this.queShotGridView.getQueShotGrid().getDrawable()).getBitmap(), 100, 100));
        }

        public void onPostExecute(List<Bitmap> list) {
            QueShotGridActivity.this.setLoading(false);
            if (QueShotGridActivity.this.queShotGridView.getQueShotGrid() != null) {
                BurnFragment.show(QueShotGridActivity.this, QueShotGridActivity.this, ((BitmapDrawable) QueShotGridActivity.this.queShotGridView.getQueShotGrid().getDrawable()).getBitmap(), list);
            }
        }
    }


    class LoadBitmapWithFilter extends AsyncTask<String, List<Bitmap>, List<Bitmap>> {
        LoadBitmapWithFilter() {
        }

        public void onPreExecute() {
            QueShotGridActivity.this.setLoading(true);
        }

        public List<Bitmap> doInBackground(String... strArr) {
            ArrayList arrayList = new ArrayList();
            for (Drawable drawable : QueShotGridActivity.this.drawableList) {
                arrayList.add(FilterUtils.getBitmapWithFilter(((BitmapDrawable) drawable).getBitmap(), strArr[0]));
            }
            return arrayList;
        }


        public void onPostExecute(List<Bitmap> list) {
            for (int i = 0; i < list.size(); i++) {
                BitmapDrawable bitmapDrawable = new BitmapDrawable(QueShotGridActivity.this.getResources(), list.get(i));
                bitmapDrawable.setAntiAlias(true);
                bitmapDrawable.setFilterBitmap(true);
                QueShotGridActivity.this.queShotGridView.getQueShotGrids().get(i).setDrawable(bitmapDrawable);
            }
            QueShotGridActivity.this.queShotGridView.invalidate();
            QueShotGridActivity.this.setLoading(false);
        }
    }

    class OnLoadBitmapFromUri extends AsyncTask<String, Bitmap, Bitmap> {
        OnLoadBitmapFromUri() {
        }

        public void onPreExecute() {
            QueShotGridActivity.this.setLoading(true);
        }

        public Bitmap doInBackground(String... strArr) {
            try {
                Uri fromFile = Uri.fromFile(new File(strArr[0]));

                Bitmap rotateBitmap = SystemUtil.rotateBitmap(MediaStore.Images.Media.getBitmap(QueShotGridActivity.this.getContentResolver(), fromFile), new ExifInterface(QueShotGridActivity.this.getContentResolver().openInputStream(fromFile)).getAttributeInt(ExifInterface.TAG_ORIENTATION, 1));

                float width = (float) rotateBitmap.getWidth();
                float height = (float) rotateBitmap.getHeight();
                float max = Math.max(width / 1280.0f, height / 1280.0f);
                return max > 1.0f ? Bitmap.createScaledBitmap(rotateBitmap, (int) (width / max), (int) (height / max), false) : rotateBitmap;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        public void onPostExecute(Bitmap bitmap) {
            QueShotGridActivity.this.setLoading(false);
            QueShotGridActivity.this.queShotGridView.replace(bitmap, "");
        }
    }


    class SaveCollageAsFile extends AsyncTask<Bitmap, String, String> {
        SaveCollageAsFile() {}

        public void onPreExecute() {
            QueShotGridActivity.this.setLoading(true);
        }

        public String doInBackground(Bitmap... bitmapArr) {
            Bitmap bitmap = bitmapArr[0];
            Bitmap bitmap2 = bitmapArr[1];
            Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(createBitmap);
            Paint paint = null;
            canvas.drawBitmap(bitmap, (Rect) null, new RectF(0.0f, 0.0f, (float) bitmap.getWidth(), (float) bitmap.getHeight()), paint);
            canvas.drawBitmap(bitmap2, (Rect) null, new RectF(0.0f, 0.0f, (float) bitmap.getWidth(), (float) bitmap.getHeight()), paint);
            bitmap.recycle();
            bitmap2.recycle();
            try {
                File image = SaveFileUtils.saveBitmapFileCollage(QueShotGridActivity.this, createBitmap, new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(new Date()));
                createBitmap.recycle();
                return image.getAbsolutePath();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        public void onPostExecute(String str) {
            QueShotGridActivity.this.setLoading(false);
            Intent intent = new Intent(QueShotGridActivity.this, PhotoShareActivity.class);
            intent.putExtra("path", str);
            QueShotGridActivity.this.startActivity(intent);
        }
    }

    public void setLoading(boolean z) {
        if (z) {
            getWindow().setFlags(16, 16);
            this.relativeLayoutLoading.setVisibility(View.VISIBLE);
            return;
        }
        getWindow().clearFlags(16);
        this.relativeLayoutLoading.setVisibility(View.GONE);
    }

}
