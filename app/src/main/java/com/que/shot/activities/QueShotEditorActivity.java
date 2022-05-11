package com.que.shot.activities;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.Guideline;
import androidx.core.content.ContextCompat;
import androidx.core.internal.view.SupportMenu;
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
import com.que.shot.adapters.AdjustAdapter;
import com.que.shot.adapters.ColorAdapter;
import com.que.shot.adapters.HardmixAdapter;
import com.que.shot.adapters.MenBeautyAdapter;
import com.que.shot.adapters.MenTabAdapter;
import com.que.shot.adapters.QueShotDrawToolsAdapter;
import com.que.shot.adapters.QueShotStickersToolsAdapter;
import com.que.shot.adapters.RecyclerTabLayout;
import com.que.shot.adapters.StickerAdapter;
import com.que.shot.adapters.QueShotToolsAdapter;
import com.que.shot.adapters.StickerTabAdapter;
import com.que.shot.adapters.WomenBeautyAdapter;
import com.que.shot.adapters.WomenTabAdapter;
import com.que.shot.assets.MenBeautyAssets;
import com.que.shot.assets.StickersAsset;
import com.que.shot.fragment.ColoredFragment;
import com.que.shot.fragment.MosaicFragment;
import com.que.shot.preference.KeyboardHeightObserver;
import com.que.shot.preference.KeyboardHeightProvider;
import com.que.shot.queshot.QueShotText;
import com.que.shot.assets.EffectCodeAsset;
import com.que.shot.assets.FilterCodeAsset;
import com.que.shot.assets.WomenBeautyAssets;
import com.que.shot.listener.AdjustListener;
import com.que.shot.listener.BrushColorListener;
import com.que.shot.listener.HardmixListener;
import com.que.shot.listener.FilterListener;
import com.que.shot.fragment.RotateFragment;
import com.que.shot.fragment.FrameFragment;
import com.que.shot.fragment.SplashBlurSquareFragment;
import com.que.shot.preference.Preference;
import com.que.shot.utils.FilterUtils;
import com.que.shot.adapters.FilterAdapter;
import com.que.shot.fragment.TextFragment;
import com.que.shot.fragment.ColorSplashFragment;
import com.que.shot.fragment.CropFragment;
import com.que.shot.fragment.RatioFragment;
import com.que.shot.fragment.SplashFragment;
import com.que.shot.listener.OnQuShotEditorListener;
import com.que.shot.queshot.QueShotEditor;
import com.que.shot.queshot.QueShotView;
import com.que.shot.draw.Drawing;
import com.que.shot.queshot.QueShotPickerView;
import com.que.shot.picker.PermissionsUtils;
import com.que.shot.queshot.QueShotStickerIcons;
import com.que.shot.sticker.DrawableSticker;
import com.que.shot.sticker.Sticker;
import com.que.shot.queshot.QueShotStickerView;
import com.que.shot.queshot.QueShotTextView;
import com.que.shot.event.AlignHorizontallyEvent;
import com.que.shot.event.DeleteIconEvent;
import com.que.shot.event.EditTextIconEvent;
import com.que.shot.event.FlipHorizontallyEvent;
import com.que.shot.event.ZoomIconEvent;
import com.que.shot.module.Module;
import com.que.shot.utils.SaveFileUtils;
import com.que.shot.utils.SystemUtil;

import org.jetbrains.annotations.NotNull;
import org.wysaid.myUtils.MsgUtil;
import org.wysaid.nativePort.CGENativeLibrary;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@SuppressLint("StaticFieldLeak")
public class QueShotEditorActivity extends QueShotBaseActivity implements OnQuShotEditorListener,
        View.OnClickListener, StickerAdapter.OnClickSplashListener, KeyboardHeightObserver,
        MenBeautyAdapter.OnClickBeautyItemListener, WomenBeautyAdapter.OnClickBeautyItemListener,
        CropFragment.OnCropPhoto, RotateFragment.OnCropPhoto, BrushColorListener,
        RatioFragment.RatioSaveListener, FrameFragment.RatioSaveListener,
        SplashFragment.SplashListener, SplashBlurSquareFragment.SplashDialogListener,
        MosaicFragment.MosaicListener, ColoredFragment.ColoredListener,
        QueShotToolsAdapter.OnQuShotItemSelected, QueShotDrawToolsAdapter.OnQuShotDrawItemSelected,
        QueShotStickersToolsAdapter.OnQuShotStickerItemSelected, FilterListener, AdjustListener, HardmixListener {

    private static final String TAG = "QuShotEditorActivity";
    // Tools
    public Module moduleToolsId = Module.NONE;
    // Keyboard
    private KeyboardHeightProvider keyboardProvider;
    // Guideline
    private Guideline guideline;
    private Guideline guidelinePaint;
    // Adapter
    public AdjustAdapter adjustAdapter;
    public ColorAdapter colorAdapter;
    private final QueShotToolsAdapter mEditingToolsAdapter = new QueShotToolsAdapter(this);
    private final QueShotDrawToolsAdapter mEditingEffectToolsAdapter = new QueShotDrawToolsAdapter(this);
    private final QueShotStickersToolsAdapter mEditingStickersToolsAdapter = new QueShotStickersToolsAdapter(this);
    // QuShot
    public QueShotEditor quShotEditor;
    public QueShotView quShotView;
    // BitmapStickerIcon
    QueShotStickerIcons quShotStickerIconClose;
    QueShotStickerIcons quShotStickerIconScale;
    QueShotStickerIcons quShotStickerIconFlip;
    QueShotStickerIcons quShotStickerIconRotate;
    QueShotStickerIcons quShotStickerIconEdit;
    QueShotStickerIcons quShotStickerIconAlign;
    // LinearLayout

    // Fragment
    public TextFragment.TextEditor textEditor;
    public TextFragment addTextFragment;
    // ViewPager
    public ViewPager viewPagerStickers;
    public ViewPager viewPagerWomenSticker;
    public ViewPager viewPagerMenSticker;
    // ConstraintLayout
    private ConstraintLayout constraintLayoutSaveEditing;
    private ConstraintLayout constraintLayoutSave;
    private ConstraintLayout constraintLayoutDraw;
    private ConstraintLayout constraintLayoutSavePaint;
    private ConstraintLayout constraintLayoutSaveNeon;
    private ConstraintLayout constraintLayoutSaveFilter;
    private ConstraintLayout constraintLayoutEmoji;
    private ConstraintLayout constraintLayoutAdjust;
    private ConstraintLayout constraintLayoutEffect;
    private ConstraintLayout constraintLayoutSaveEffect;
    private ConstraintLayout constraintLayoutPaint;
    private ConstraintLayout constraintLayoutNeon;
    public ConstraintLayout constraintLayoutFilter;
    private ConstraintLayout constraintLayoutSticker;
    private ConstraintLayout constraintLayoutStickerMen;
    private ConstraintLayout constraintLayoutStickerWomen;
    private ConstraintLayout constraintLayoutAddText;
    private ConstraintLayout constraintLayoutView;
    // RelativeLayout
    private RelativeLayout relativeLayoutAddText;
    public RelativeLayout relativeLayoutWrapper;
    private RelativeLayout relativeLayoutLoading;
    public LinearLayout linear_layout_wrapper_sticker_women_list;
    public LinearLayout linear_layout_wrapper_sticker_men_list;
    public LinearLayout linear_layout_wrapper_sticker_list;
    // RecyclerView
    public RecyclerView recyclerViewTools;
    public RecyclerView recyclerViewDraw;
    public RecyclerView recyclerViewEmoji;
    public RecyclerView recyclerViewAllFilter;
    public RecyclerView recyclerViewBwFilter;
    public RecyclerView recyclerViewVintageFilter;
    public RecyclerView recyclerViewSmoothFilter;
    public RecyclerView recyclerViewColdFilter;
    public RecyclerView recyclerViewWarmFilter;
    public RecyclerView recyclerViewLegacyFilter;
    private RecyclerView recyclerViewPaintListColor;
    private RecyclerView recyclerViewNeonListColor;
    private RecyclerView recyclerViewAdjust;
    public RecyclerView recyclerViewDodge;
    public RecyclerView recyclerViewOverlay;
    public RecyclerView recyclerViewHardmix;
    public RecyclerView recyclerViewDivide;
    public RecyclerView recyclerViewBurn;
    // ImageView
    private ImageView imageViewCompareAdjust;
    public ImageView imageViewCompareFilter;
    public ImageView imageViewCompareEffect;
    private ImageView imageViewErasePaint;
    private ImageView imageViewEraseNeon;
    private ImageView imageViewRedoPaint;
    private ImageView imageViewCleanPaint;
    private ImageView imageViewCleanNeon;
    private ImageView imageViewRedoNeon;
    private ImageView imageViewUndoPaint;
    private ImageView imageViewUndoNeon;
    private ImageView imageViewNeon;
    private ImageView imageViewColor;

    public ImageView imageViewAddStickerWomen;
    public ImageView imageViewAddStickerMen;
    public ImageView imageViewAddSticker;

    // TextView
    public TextView textViewCancel;
    public TextView textViewDiscard;
    public TextView textViewSaveEditing;
    public ImageView image_view_exit;
    private TextView textViewListAllFilter;
    private TextView textViewListBwFilter;
    private TextView textViewListVintageFilter;
    private TextView textViewListSmoothFilter;
    private TextView textViewListColdFilter;
    private TextView textViewListWarmFilter;
    private TextView textViewListLegacyFilter;
    // Seekbar
    private SeekBar seekbarIntensityAdjust;
    private SeekBar seekbarBrushPaintSize;
    private SeekBar seekbarBrushNeonSize;
    private SeekBar seekbarErasePaintSize;
    private SeekBar seekbarEraseNeonSize;
    public SeekBar seekbarIntensityFilter;
    public SeekBar seekbarIntensityEffect;
    public SeekBar seekbarSticker;
    public SeekBar seekbarStickerWomen;
    public SeekBar seekbarStickerMen;
    // ArrayList & List
    public ArrayList listDodge = new ArrayList<>();
    public ArrayList listHardmix = new ArrayList<>();
    public ArrayList listDivide = new ArrayList<>();
    public ArrayList listOverlay = new ArrayList<>();
    public ArrayList listBurn = new ArrayList<>();
    public ArrayList listAllFilter = new ArrayList<>();
    public ArrayList listBwFilter = new ArrayList<>();
    public ArrayList listVintageFilter = new ArrayList<>();
    public ArrayList listSmoothFilter = new ArrayList<>();
    public ArrayList listColdFilter = new ArrayList<>();
    public ArrayList listWarmFilter = new ArrayList<>();
    public ArrayList listLegacyFilter = new ArrayList<>();
    // Admob Ads
    private InterstitialAd interstitial;
    public AdView adViewAdMob;

    //////
    private LinearLayout linearLayoutOverlay;
    private LinearLayout linearLayoutHardmix;
    private LinearLayout linearLayoutDodge;
    private LinearLayout linearLayoutBurn;
    private LinearLayout linearLayoutDivide;
    private TextView textViewListOverlay;
    private TextView textViewListHardmix;
    private TextView textViewListDivide;
    private TextView textViewListDodge;
    private TextView textViewListBurn;


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

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_queshot_editor);
        CGENativeLibrary.setLoadImageCallback(this.loadImageCallback, null);
        if (Build.VERSION.SDK_INT < 26) {
            getWindow().setSoftInputMode(48);
        }
        keyboardProvider = new KeyboardHeightProvider(this);
        new Handler().post(() -> keyboardProvider.start());
        initView();
        onClickListener();
        setView();
        setBottomToolbar(false);
        interstitialAdMobAds();
        adsViews();
    }

    private void adsViews() {
        adViewAdMob = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adViewAdMob.loadAd(adRequest);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        keyboardProvider.close();
    }

    public void onPause() {
        super.onPause();
        keyboardProvider.setKeyboardHeightObserver(null);
    }

    public void onResume() {
        super.onResume();
        keyboardProvider.setKeyboardHeightObserver(this);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initView() {
        this.relativeLayoutLoading = findViewById(R.id.relative_layout_loading);
        this.relativeLayoutLoading.setVisibility(View.VISIBLE);
        this.quShotView = findViewById(R.id.photo_editor_view);
        this.quShotView.setVisibility(View.INVISIBLE);
        this.recyclerViewTools = findViewById(R.id.recyclerViewTools);
        this.recyclerViewDraw = findViewById(R.id.recyclerViewDraw);
        this.recyclerViewEmoji = findViewById(R.id.recyclerViewemoji);
        this.recyclerViewAllFilter = findViewById(R.id.recycler_view_filter_all);
        this.recyclerViewBwFilter = findViewById(R.id.recycler_view_filter_bw);
        this.recyclerViewVintageFilter = findViewById(R.id.recycler_view_filter_vintage);
        this.recyclerViewSmoothFilter = findViewById(R.id.recycler_view_filter_smooth);
        this.recyclerViewColdFilter = findViewById(R.id.recycler_view_filter_cold);
        this.recyclerViewWarmFilter = findViewById(R.id.recycler_view_filter_warm);
        this.recyclerViewLegacyFilter = findViewById(R.id.recycler_view_filter_legacy);
        this.recyclerViewBwFilter.setVisibility(View.GONE);
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
        this.linearLayoutHardmix = findViewById(R.id.linearLayoutHardmix);
        this.linearLayoutOverlay = findViewById(R.id.linearLayoutOverlay);
        this.linearLayoutBurn = findViewById(R.id.linearLayoutBurn);
        this.linearLayoutDivide = findViewById(R.id.linearLayoutDivide);
        this.linearLayoutDodge = findViewById(R.id.linearLayoutDodge);
        this.textViewListHardmix = findViewById(R.id.text_view_list_hardmix);
        this.textViewListDodge = findViewById(R.id.text_view_list_dodge);
        this.textViewListDivide = findViewById(R.id.text_view_list_divide);
        this.textViewListBurn = findViewById(R.id.text_view_list_burn);
        this.textViewListOverlay = findViewById(R.id.text_view_list_Overlay);

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
        this.recyclerViewDodge = findViewById(R.id.recycler_view_dodge);
        this.recyclerViewHardmix = findViewById(R.id.recycler_view_hardmix);
        this.recyclerViewOverlay = findViewById(R.id.recycler_view_overlay);
        this.recyclerViewDivide = findViewById(R.id.recycler_view_divide);
        this.recyclerViewBurn = findViewById(R.id.recycler_view_burn);
        this.recyclerViewDodge.setVisibility(View.GONE);
        this.recyclerViewDivide.setVisibility(View.GONE);
        this.recyclerViewHardmix.setVisibility(View.GONE);
        this.recyclerViewBurn.setVisibility(View.GONE);
        this.recyclerViewAdjust = findViewById(R.id.recyclerViewAdjust);
        this.constraintLayoutView = findViewById(R.id.constraint_layout_root_view);
        this.constraintLayoutFilter = findViewById(R.id.constraint_layout_filter);
        this.constraintLayoutAdjust = findViewById(R.id.constraintLayoutAdjust);
        this.constraintLayoutEffect = findViewById(R.id.constraint_layout_Effect);
        this.constraintLayoutSticker = findViewById(R.id.constraint_layout_sticker);
        this.constraintLayoutStickerWomen = findViewById(R.id.constraint_layout_sticker_women);
        this.constraintLayoutStickerMen = findViewById(R.id.constraint_layout_sticker_men);
        this.constraintLayoutAddText = findViewById(R.id.constraint_layout_confirm_text);
        this.viewPagerWomenSticker = findViewById(R.id.stickerWomenViewpaper);
        this.viewPagerMenSticker = findViewById(R.id.stickerMenViewpaper);
        this.viewPagerStickers = findViewById(R.id.stickerViewpaper);
        this.linear_layout_wrapper_sticker_women_list = findViewById(R.id.linear_layout_wrapper_sticker_women_list);
        this.linear_layout_wrapper_sticker_men_list = findViewById(R.id.linear_layout_wrapper_sticker_men_list);
        this.linear_layout_wrapper_sticker_list = findViewById(R.id.linear_layout_wrapper_sticker_list);
        this.guidelinePaint = findViewById(R.id.guidelinePaint);
        this.guideline= findViewById(R.id.guideline);
        this.seekbarIntensityFilter = findViewById(R.id.seekbar_filter);
        this.seekbarIntensityEffect = findViewById(R.id.seekbar_effect);
        this.seekbarStickerMen = findViewById(R.id.seekbarStickerMenAlpha);
        this.seekbarStickerMen.setVisibility(View.GONE);
        this.seekbarStickerWomen = findViewById(R.id.seekbarStickerWomenAlpha);
        this.seekbarStickerWomen.setVisibility(View.GONE);
        this.seekbarSticker = findViewById(R.id.seekbarStickerAlpha);
        this.seekbarSticker.setVisibility(View.GONE);
        this.constraintLayoutPaint = findViewById(R.id.constraintLayoutPaint);
        this.recyclerViewPaintListColor = findViewById(R.id.recyclerViewColorPaint);
        this.constraintLayoutNeon = findViewById(R.id.constraintLayoutNeon);
        this.recyclerViewNeonListColor = findViewById(R.id.recyclerViewColorNeon);
        this.imageViewColor = findViewById(R.id.imageViewBrush);
        this.imageViewErasePaint = findViewById(R.id.image_view_erase);
        this.imageViewEraseNeon = findViewById(R.id.image_view_erase_neon);
        this.imageViewUndoPaint = findViewById(R.id.image_view_undo);
        this.imageViewUndoPaint.setVisibility(View.GONE);
        this.imageViewRedoPaint = findViewById(R.id.image_view_redo);
        this.imageViewRedoPaint.setVisibility(View.GONE);
        this.imageViewCleanPaint = findViewById(R.id.image_view_clean);
        this.imageViewCleanPaint.setVisibility(View.GONE);
        this.imageViewCleanNeon = findViewById(R.id.image_view_clean_neon);
        this.imageViewCleanNeon.setVisibility(View.GONE);
        this.imageViewUndoNeon = findViewById(R.id.image_view_undo_neon);
        this.imageViewUndoNeon.setVisibility(View.GONE);
        this.imageViewRedoNeon = findViewById(R.id.image_view_redo_neon);
        this.imageViewRedoNeon.setVisibility(View.GONE);
        this.seekbarBrushPaintSize = findViewById(R.id.seekbarBrushSize);
        this.seekbarEraseNeonSize = findViewById(R.id.seekbarEraseSize);
        this.imageViewNeon = findViewById(R.id.imageViewNeon);
        this.seekbarBrushNeonSize = findViewById(R.id.seekbarBrushSizeNeon);
        this.seekbarErasePaintSize = findViewById(R.id.seekbarEraseSizeNeon);
        this.relativeLayoutWrapper = findViewById(R.id.relative_layout_wrapper_photo);
        this.textViewSaveEditing = findViewById(R.id.text_view_save);
        this.image_view_exit = findViewById(R.id.image_view_exit);
        this.constraintLayoutSaveEditing = findViewById(R.id.constraintLayoutSaveEditing);
        this.constraintLayoutSave = findViewById(R.id.constraintLayoutSave);
        this.constraintLayoutSavePaint = findViewById(R.id.constraint_layout_confirm_save_paint);
        this.constraintLayoutSaveNeon = findViewById(R.id.constraint_layout_confirm_save_neon);
        this.constraintLayoutDraw= findViewById(R.id.constraint_layout_draw);
        this.constraintLayoutEmoji= findViewById(R.id.constraint_layout_emoji);
        this.constraintLayoutSaveEffect = findViewById(R.id.constraint_layout_confirm_save_hardmix);
        this.constraintLayoutSaveFilter = findViewById(R.id.constraint_layout_confirm_save_filter);
        this.imageViewCompareAdjust = findViewById(R.id.imageViewCompareAdjust);
        this.imageViewCompareAdjust.setOnTouchListener(this.onTouchListener);
        this.imageViewCompareAdjust.setVisibility(View.GONE);
        this.imageViewCompareFilter = findViewById(R.id.image_view_compare_filter);
        this.imageViewCompareFilter.setOnTouchListener(this.onTouchListener);
        this.imageViewCompareFilter.setVisibility(View.GONE);
        this.imageViewCompareEffect = findViewById(R.id.image_view_compare_effect);
        this.imageViewCompareEffect.setOnTouchListener(this.onTouchListener);
        this.imageViewCompareEffect.setVisibility(View.GONE);
        this.seekbarIntensityAdjust = findViewById(R.id.seekbarAdjust);
        this.relativeLayoutAddText = findViewById(R.id.relative_layout_add_text);
        this.relativeLayoutAddText.setVisibility(View.GONE);
    }

    private void setOnBackPressDialog() {
        final Dialog dialogOnBackPressed = new Dialog(QueShotEditorActivity.this, R.style.UploadDialog);
        dialogOnBackPressed.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogOnBackPressed.setContentView(R.layout.dialog_exit);
        dialogOnBackPressed.setCancelable(true);
        dialogOnBackPressed.show();
        this.textViewCancel = dialogOnBackPressed.findViewById(R.id.textViewCancel);
        this.textViewDiscard = dialogOnBackPressed.findViewById(R.id.textViewDiscard);
        this.textViewCancel.setOnClickListener(view -> {
            dialogOnBackPressed.dismiss();
        });

        this.textViewDiscard.setOnClickListener(view -> {
                dialogOnBackPressed.dismiss();
                QueShotEditorActivity.this.moduleToolsId = null;
                QueShotEditorActivity.this.finish();
                finish();
        });
    }

    private void setView() {
        this.recyclerViewTools.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        this.recyclerViewTools.setAdapter(this.mEditingToolsAdapter);
        this.recyclerViewTools.setHasFixedSize(true);
        this.recyclerViewDraw.setLayoutManager(new GridLayoutManager(QueShotEditorActivity.this.getApplicationContext(), 4));
        this.recyclerViewDraw.setAdapter(this.mEditingEffectToolsAdapter);
        this.recyclerViewDraw.setHasFixedSize(true);
        this.recyclerViewEmoji.setLayoutManager(new GridLayoutManager(QueShotEditorActivity.this.getApplicationContext(), 3));
        this.recyclerViewEmoji.setAdapter(this.mEditingStickersToolsAdapter);
        this.recyclerViewEmoji.setHasFixedSize(true);
        this.recyclerViewAllFilter.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        this.recyclerViewAllFilter.setHasFixedSize(true);
        this.recyclerViewBwFilter.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        this.recyclerViewBwFilter.setHasFixedSize(true);
        this.recyclerViewVintageFilter.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        this.recyclerViewVintageFilter.setHasFixedSize(true);
        this.recyclerViewSmoothFilter.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        this.recyclerViewSmoothFilter.setHasFixedSize(true);
        this.recyclerViewColdFilter.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        this.recyclerViewColdFilter.setHasFixedSize(true);
        this.recyclerViewWarmFilter.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        this.recyclerViewWarmFilter.setHasFixedSize(true);
        this.recyclerViewLegacyFilter.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        this.recyclerViewLegacyFilter.setHasFixedSize(true);
        this.recyclerViewDodge.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        this.recyclerViewDodge.setHasFixedSize(true);
        this.recyclerViewHardmix.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        this.recyclerViewHardmix.setHasFixedSize(true);
        this.recyclerViewOverlay.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        this.recyclerViewOverlay.setHasFixedSize(true);
        this.recyclerViewDivide.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        this.recyclerViewDivide.setHasFixedSize(true);
        this.recyclerViewBurn.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        this.recyclerViewBurn.setHasFixedSize(true);
        new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        this.recyclerViewAdjust.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        this.recyclerViewAdjust.setHasFixedSize(true);
        this.adjustAdapter = new AdjustAdapter(getApplicationContext(), this);
        this.recyclerViewAdjust.setAdapter(this.adjustAdapter);
        this.recyclerViewPaintListColor.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        this.recyclerViewPaintListColor.setHasFixedSize(true);
        this.recyclerViewPaintListColor.setAdapter(new ColorAdapter(getApplicationContext(), this));
        this.recyclerViewNeonListColor.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        this.recyclerViewNeonListColor.setHasFixedSize(true);
        this.recyclerViewNeonListColor.setAdapter(new ColorAdapter(getApplicationContext(), this));

        viewPagerWomenSticker.setAdapter(new PagerAdapter() {
            public int getCount() {
                return 11;
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
                View inflate = LayoutInflater.from(QueShotEditorActivity.this.getBaseContext()).inflate(R.layout.list_women_beauty, null, false);
                RecyclerView recycler_view_sticker = inflate.findViewById(R.id.recyclerViewSticker);
                recycler_view_sticker.setHasFixedSize(true);
                recycler_view_sticker.setLayoutManager(new GridLayoutManager(QueShotEditorActivity.this.getApplicationContext(), 7));
                switch (i) {
                    case 0:
                        recycler_view_sticker.setAdapter(new StickerAdapter(QueShotEditorActivity.this.getApplicationContext(), WomenBeautyAssets.mListCrown(), i, QueShotEditorActivity.this));
                        break;
                    case 1:
                        recycler_view_sticker.setAdapter(new StickerAdapter(QueShotEditorActivity.this.getApplicationContext(), WomenBeautyAssets.mListSnsla(), i, QueShotEditorActivity.this));
                        break;
                    case 2:
                        recycler_view_sticker.setAdapter(new StickerAdapter(QueShotEditorActivity.this.getApplicationContext(), WomenBeautyAssets.mListHalat(), i, QueShotEditorActivity.this));
                        break;
                    case 3:
                        recycler_view_sticker.setAdapter(new StickerAdapter(QueShotEditorActivity.this.getApplicationContext(), WomenBeautyAssets.mListFlower(), i, QueShotEditorActivity.this));
                        break;
                    case 4:
                        recycler_view_sticker.setAdapter(new StickerAdapter(QueShotEditorActivity.this.getApplicationContext(), WomenBeautyAssets.mListGlass(), i, QueShotEditorActivity.this));
                        break;
                    case 5:
                        recycler_view_sticker.setAdapter(new StickerAdapter(QueShotEditorActivity.this.getApplicationContext(), WomenBeautyAssets.mListChap(), i, QueShotEditorActivity.this));
                        break;
                    case 6:
                        recycler_view_sticker.setAdapter(new StickerAdapter(QueShotEditorActivity.this.getApplicationContext(), WomenBeautyAssets.mListHairs(), i, QueShotEditorActivity.this));
                        break;
                    case 7:
                        recycler_view_sticker.setAdapter(new StickerAdapter(QueShotEditorActivity.this.getApplicationContext(), WomenBeautyAssets.mListSmile(), i, QueShotEditorActivity.this));
                        break;
                    case 8:
                        recycler_view_sticker.setAdapter(new StickerAdapter(QueShotEditorActivity.this.getApplicationContext(), WomenBeautyAssets.mListHjban(), i, QueShotEditorActivity.this));
                        break;
                    case 9:
                        recycler_view_sticker.setAdapter(new StickerAdapter(QueShotEditorActivity.this.getApplicationContext(), WomenBeautyAssets.mListChfer(), i, QueShotEditorActivity.this));
                        break;
                    case 10:
                        recycler_view_sticker.setAdapter(new StickerAdapter(QueShotEditorActivity.this.getApplicationContext(), WomenBeautyAssets.mListZwaq(), i, QueShotEditorActivity.this));
                        break;

                }

                viewGroup.addView(inflate);
                return inflate;
            }
        });
        RecyclerTabLayout recycler_tab_layout_women = findViewById(R.id.recycler_tab_layout_women);
        recycler_tab_layout_women.setUpWithAdapter(new WomenTabAdapter(viewPagerWomenSticker, getApplicationContext()));
        recycler_tab_layout_women.setPositionThreshold(0.5f);
        recycler_tab_layout_women.setBackgroundColor(ContextCompat.getColor(this, R.color.TabColor));

        viewPagerStickers.setAdapter(new PagerAdapter() {
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
                View inflate = LayoutInflater.from(QueShotEditorActivity.this.getBaseContext()).inflate(R.layout.list_women_beauty, null, false);
                RecyclerView recycler_view_sticker = inflate.findViewById(R.id.recyclerViewSticker);
                recycler_view_sticker.setHasFixedSize(true);
                recycler_view_sticker.setLayoutManager(new GridLayoutManager(QueShotEditorActivity.this.getApplicationContext(), 6));
                switch (i) {
                    case 0:
                        recycler_view_sticker.setAdapter(new StickerAdapter(QueShotEditorActivity.this.getApplicationContext(), StickersAsset.mListEmojy(), i, QueShotEditorActivity.this));
                        break;
                    case 1:
                        recycler_view_sticker.setAdapter(new StickerAdapter(QueShotEditorActivity.this.getApplicationContext(), StickersAsset.mListFlag(), i, QueShotEditorActivity.this));
                        break;
                    case 2:
                        recycler_view_sticker.setAdapter(new StickerAdapter(QueShotEditorActivity.this.getApplicationContext(), StickersAsset.mListBoom(), i, QueShotEditorActivity.this));
                        break;

                }

                viewGroup.addView(inflate);
                return inflate;
            }
        });
        RecyclerTabLayout recycler_tab_layout = findViewById(R.id.recycler_tab_layout);
        recycler_tab_layout.setUpWithAdapter(new StickerTabAdapter(viewPagerStickers, getApplicationContext()));
        recycler_tab_layout.setPositionThreshold(0.5f);
        recycler_tab_layout.setBackgroundColor(ContextCompat.getColor(this, R.color.TabColor));

        viewPagerMenSticker.setAdapter(new PagerAdapter() {
            public int getCount() {
                return 8;
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
                View inflate = LayoutInflater.from(QueShotEditorActivity.this.getBaseContext()).inflate(R.layout.list_women_beauty, null, false);
                RecyclerView recycler_view_sticker = inflate.findViewById(R.id.recyclerViewSticker);
                recycler_view_sticker.setHasFixedSize(true);
                recycler_view_sticker.setLayoutManager(new GridLayoutManager(QueShotEditorActivity.this.getApplicationContext(), 6));
                switch (i) {
                    case 0:
                        recycler_view_sticker.setAdapter(new StickerAdapter(QueShotEditorActivity.this.getApplicationContext(), MenBeautyAssets.mListhair(), i, QueShotEditorActivity.this));
                        break;
                    case 1:
                        recycler_view_sticker.setAdapter(new StickerAdapter(QueShotEditorActivity.this.getApplicationContext(), MenBeautyAssets.mListGlasses(), i, QueShotEditorActivity.this));
                        break;
                    case 2:
                        recycler_view_sticker.setAdapter(new StickerAdapter(QueShotEditorActivity.this.getApplicationContext(), MenBeautyAssets.mListMostach(), i, QueShotEditorActivity.this));
                        break;
                    case 3:
                        recycler_view_sticker.setAdapter(new StickerAdapter(QueShotEditorActivity.this.getApplicationContext(), MenBeautyAssets.mListLhya(), i, QueShotEditorActivity.this));
                        break;
                    case 4:
                        recycler_view_sticker.setAdapter(new StickerAdapter(QueShotEditorActivity.this.getApplicationContext(), MenBeautyAssets.mListScarf(), i, QueShotEditorActivity.this));
                        break;
                    case 5:
                        recycler_view_sticker.setAdapter(new StickerAdapter(QueShotEditorActivity.this.getApplicationContext(), MenBeautyAssets.mListTie(), i, QueShotEditorActivity.this));
                        break;
                    case 6:
                        recycler_view_sticker.setAdapter(new StickerAdapter(QueShotEditorActivity.this.getApplicationContext(), MenBeautyAssets.mListTatoo(), i, QueShotEditorActivity.this));
                        break;
                    case 7:
                        recycler_view_sticker.setAdapter(new StickerAdapter(QueShotEditorActivity.this.getApplicationContext(), MenBeautyAssets.mListChain(), i, QueShotEditorActivity.this));
                        break;

                }

                viewGroup.addView(inflate);
                return inflate;
            }
        });
        RecyclerTabLayout recycler_tab_layout_men = findViewById(R.id.recycler_tab_layout_men);
        recycler_tab_layout_men.setUpWithAdapter(new MenTabAdapter(viewPagerMenSticker, getApplicationContext()));
        recycler_tab_layout_men.setPositionThreshold(0.5f);
        recycler_tab_layout_men.setBackgroundColor(ContextCompat.getColor(this, R.color.TabColor));


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            new loadBitmapUri().execute(bundle.getString(QueShotPickerView.KEY_SELECTED_PHOTOS));
        }
        Display display = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        CGENativeLibrary.setLoadImageCallback(this.loadImageCallback, null);
        if (Build.VERSION.SDK_INT < 26) {
            getWindow().setSoftInputMode(48);
        }
        this.quShotEditor = new QueShotEditor.Builder(this, this.quShotView).setPinchTextScalable(true).build();
        this.quShotEditor.setOnPhotoEditorListener(this);

    }

    @Override
    public void onKeyboardHeightChanged(int height, int orientation) {
        Preference.setKeyboard(getApplicationContext(), 0);
        String orientationLabel = orientation == Configuration.ORIENTATION_PORTRAIT ? "portrait" : "landscape";
        Log.i(TAG, "onKeyboardHeightChanged in pixels: " + height + " " + orientationLabel);
        if (height <= 0) {
            Preference.setHeightOfNotch(getApplicationContext(), -height);
        } else if (addTextFragment != null) {
            addTextFragment.updateAddTextBottomToolbarHeight(Preference.getHeightOfNotch(getApplicationContext()) + height);
            Preference.setKeyboard(getApplicationContext(), height + Preference.getHeightOfNotch(getApplicationContext()));
        }
    }

    private void  onClickListener(){
        this.textViewSaveEditing.setOnClickListener(view -> {
            if (interstitial != null && interstitial.isLoaded()) {
                interstitial.show();
            }
            if (PermissionsUtils.checkWriteStoragePermission(QueShotEditorActivity.this)) {
                new SaveEditingBitmap().execute();
            }
        });
        this.image_view_exit.setOnClickListener(view -> QueShotEditorActivity.this.onBackPressed());
        this.imageViewErasePaint.setOnClickListener(view -> QueShotEditorActivity.this.setImageErasePaint());
        this.imageViewColor.setOnClickListener(view -> QueShotEditorActivity.this.setColorPaint());
        this.seekbarEraseNeonSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                QueShotEditorActivity.this.quShotEditor.setBrushEraserSize((float) i);
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                QueShotEditorActivity.this.quShotEditor.brushEraser();
            }
        });
        this.seekbarBrushPaintSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                QueShotEditorActivity.this.quShotEditor.setBrushSize((float) (i + 10));
            }
        });
        this.imageViewEraseNeon.setOnClickListener(view -> QueShotEditorActivity.this.setImageEraseNeon());
        this.imageViewNeon.setOnClickListener(view -> QueShotEditorActivity.this.setColorNeon());
        this.seekbarErasePaintSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                QueShotEditorActivity.this.quShotEditor.setBrushEraserSize((float) i);
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                QueShotEditorActivity.this.quShotEditor.brushEraser();
            }
        });
        this.seekbarBrushNeonSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                QueShotEditorActivity.this.quShotEditor.setBrushSize((float) (i + 10));
            }
        });
        this.linearLayoutAll.setOnClickListener(view -> QueShotEditorActivity.this.setAllFilter());
        this.linearLayoutBw.setOnClickListener(view -> QueShotEditorActivity.this.setBwFilter());
        this.linearLayoutVintage.setOnClickListener(view -> QueShotEditorActivity.this.setVintageFilter());
        this.linearLayoutSmooth.setOnClickListener(view -> QueShotEditorActivity.this.setSmoothFilter());
        this.linearLayoutCold.setOnClickListener(view -> QueShotEditorActivity.this.setColdFilter());
        this.linearLayoutWarm.setOnClickListener(view -> QueShotEditorActivity.this.setWarmFilter());
        this.linearLayoutLegacy.setOnClickListener(view -> QueShotEditorActivity.this.setLegacyFilter());
        this.linearLayoutHardmix.setOnClickListener(view -> QueShotEditorActivity.this.setHardmixEffect());
        this.linearLayoutOverlay.setOnClickListener(view -> QueShotEditorActivity.this.setOverlayEffect());
        this.linearLayoutDodge.setOnClickListener(view -> QueShotEditorActivity.this.setDodgeEffect());
        this.linearLayoutDivide.setOnClickListener(view -> QueShotEditorActivity.this.setDivideEffect());
        this.linearLayoutBurn.setOnClickListener(view -> QueShotEditorActivity.this.setBurnEffect());
        this.seekbarSticker.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                Sticker currentSticker = QueShotEditorActivity.this.quShotView.getCurrentSticker();
                if (currentSticker != null) {
                    currentSticker.setAlpha(i);
                }
            }
        });
        this.seekbarStickerMen.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                Sticker currentSticker = QueShotEditorActivity.this.quShotView.getCurrentSticker();
                if (currentSticker != null) {
                    currentSticker.setAlpha(i);
                }
            }
        });
        this.seekbarStickerWomen.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                Sticker currentSticker = QueShotEditorActivity.this.quShotView.getCurrentSticker();
                if (currentSticker != null) {
                    currentSticker.setAlpha(i);
                }
            }
        });

        this.imageViewAddStickerWomen = findViewById(R.id.imageViewAddStickerWomen);
        this.imageViewAddStickerWomen.setVisibility(View.GONE);
        this.imageViewAddStickerWomen.setOnClickListener(view -> {
            QueShotEditorActivity.this.imageViewAddStickerWomen.setVisibility(View.GONE);
            QueShotEditorActivity.this.linear_layout_wrapper_sticker_women_list.setVisibility(View.VISIBLE);
        });

        this.imageViewAddStickerMen = findViewById(R.id.imageViewAddStickerMen);
        this.imageViewAddStickerMen.setVisibility(View.GONE);
        this.imageViewAddStickerMen.setOnClickListener(view -> {
            QueShotEditorActivity.this.imageViewAddStickerMen.setVisibility(View.GONE);
            QueShotEditorActivity.this.linear_layout_wrapper_sticker_men_list.setVisibility(View.VISIBLE);
        });

        this.imageViewAddSticker = findViewById(R.id.imageViewAddSticker);
        this.imageViewAddSticker.setVisibility(View.GONE);
        this.imageViewAddSticker.setOnClickListener(view -> {
            QueShotEditorActivity.this.imageViewAddSticker.setVisibility(View.GONE);
            QueShotEditorActivity.this.linear_layout_wrapper_sticker_list.setVisibility(View.VISIBLE);
        });

        this.relativeLayoutAddText.setOnClickListener(view -> {
            QueShotEditorActivity.this.quShotView.setHandlingSticker(null);
            QueShotEditorActivity.this.textFragment();
        });
        this.seekbarIntensityAdjust.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                QueShotEditorActivity.this.adjustAdapter.getCurrentAdjustModel().setSeekBarIntensity(QueShotEditorActivity.this.quShotEditor, ((float) i) / ((float) seekBar.getMax()), true);
            }
        });
        quShotStickerIconClose = new QueShotStickerIcons(ContextCompat.getDrawable(this, R.drawable.ic_outline_close), 0, QueShotStickerIcons.DELETE);
        quShotStickerIconClose.setIconEvent(new DeleteIconEvent());
        quShotStickerIconScale = new QueShotStickerIcons(ContextCompat.getDrawable(this, R.drawable.ic_outline_scale), 3, QueShotStickerIcons.SCALE);
        quShotStickerIconScale.setIconEvent(new ZoomIconEvent());
        quShotStickerIconFlip = new QueShotStickerIcons(ContextCompat.getDrawable(this, R.drawable.ic_outline_flip), 1, QueShotStickerIcons.FLIP);
        quShotStickerIconFlip.setIconEvent(new FlipHorizontallyEvent());
        quShotStickerIconRotate = new QueShotStickerIcons(ContextCompat.getDrawable(this, R.drawable.ic_outline_rotate), 3, QueShotStickerIcons.ROTATE);
        quShotStickerIconRotate.setIconEvent(new ZoomIconEvent());
        quShotStickerIconEdit = new QueShotStickerIcons(ContextCompat.getDrawable(this, R.drawable.ic_outline_edit), 1, QueShotStickerIcons.EDIT);
        quShotStickerIconEdit.setIconEvent(new EditTextIconEvent());
        quShotStickerIconAlign = new QueShotStickerIcons(ContextCompat.getDrawable(this, R.drawable.ic_outline_center), 2, QueShotStickerIcons.ALIGN);
        quShotStickerIconAlign.setIconEvent(new AlignHorizontallyEvent());
        this.quShotView.setIcons(Arrays.asList(quShotStickerIconClose, quShotStickerIconScale,
                quShotStickerIconFlip, quShotStickerIconEdit, quShotStickerIconRotate, quShotStickerIconAlign));
        this.quShotView.setBackgroundColor(-16777216);
        this.quShotView.setLocked(false);
        this.quShotView.setConstrained(true);
        this.quShotView.setOnStickerOperationListener(new QueShotStickerView.OnStickerOperationListener() {
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
                QueShotEditorActivity.this.seekbarSticker.setVisibility(View.VISIBLE);
                QueShotEditorActivity.this.seekbarSticker.setProgress(sticker.getAlpha());
                QueShotEditorActivity.this.seekbarStickerMen.setVisibility(View.VISIBLE);
                QueShotEditorActivity.this.seekbarStickerMen.setProgress(sticker.getAlpha());
                QueShotEditorActivity.this.seekbarStickerWomen.setVisibility(View.VISIBLE);
                QueShotEditorActivity.this.seekbarStickerWomen.setProgress(sticker.getAlpha());
            }

            public void onStickerSelected(@NonNull Sticker sticker) {
                if (sticker instanceof QueShotTextView) {
                    ((QueShotTextView) sticker).setTextColor(SupportMenu.CATEGORY_MASK);
                    QueShotEditorActivity.this.quShotView.replace(sticker);
                    QueShotEditorActivity.this.quShotView.invalidate();
                }
                QueShotEditorActivity.this.seekbarSticker.setVisibility(View.VISIBLE);
                QueShotEditorActivity.this.seekbarSticker.setProgress(sticker.getAlpha());
                QueShotEditorActivity.this.seekbarStickerMen.setVisibility(View.VISIBLE);
                QueShotEditorActivity.this.seekbarStickerMen.setProgress(sticker.getAlpha());
                QueShotEditorActivity.this.seekbarStickerWomen.setVisibility(View.VISIBLE);
                QueShotEditorActivity.this.seekbarStickerWomen.setProgress(sticker.getAlpha());
            }

            public void onStickerDeleted(@NonNull Sticker sticker) {
                QueShotEditorActivity.this.seekbarSticker.setVisibility(View.GONE);
                QueShotEditorActivity.this.seekbarStickerMen.setVisibility(View.GONE);
                QueShotEditorActivity.this.seekbarStickerWomen.setVisibility(View.GONE);
            }

            public void onStickerTouchOutside() {
                QueShotEditorActivity.this.seekbarSticker.setVisibility(View.GONE);
                QueShotEditorActivity.this.seekbarStickerMen.setVisibility(View.GONE);
                QueShotEditorActivity.this.seekbarStickerWomen.setVisibility(View.GONE);
            }

            public void onStickerDoubleTap(@NonNull Sticker sticker) {
                if (sticker instanceof QueShotTextView) {
                    sticker.setShow(false);
                    QueShotEditorActivity.this.quShotView.setHandlingSticker( (Sticker) null);
                    QueShotEditorActivity.this.addTextFragment = TextFragment.show(QueShotEditorActivity.this, ((QueShotTextView) sticker).getQuShotText());
                    QueShotEditorActivity.this.textEditor = new TextFragment.TextEditor() {
                        public void onDone(QueShotText addTextProperties) {
                            QueShotEditorActivity.this.quShotView.getStickers().remove(QueShotEditorActivity.this.quShotView.getLastHandlingSticker());
                            QueShotEditorActivity.this.quShotView.addSticker(new QueShotTextView(QueShotEditorActivity.this, addTextProperties));
                        }

                        public void onBackButton() {
                            QueShotEditorActivity.this.quShotView.showLastHandlingSticker();
                        }
                    };
                    QueShotEditorActivity.this.addTextFragment.setOnTextEditorListener(QueShotEditorActivity.this.textEditor);
                }
            }
        });
        this.seekbarIntensityFilter.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                QueShotEditorActivity.this.quShotView.setFilterIntensity(((float) i) / 100.0f);
            }
        });
        this.seekbarIntensityEffect.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                QueShotEditorActivity.this.quShotView.setFilterIntensity(((float) i) / 100.0f);
            }
        });
      }

    public void setOverlayEffect() {
        this.recyclerViewOverlay.setVisibility(View.VISIBLE);
        this.recyclerViewHardmix.setVisibility(View.GONE);
        this.recyclerViewDodge.setVisibility(View.GONE);
        this.recyclerViewDivide.setVisibility(View.GONE);
        this.recyclerViewBurn.setVisibility(View.GONE);
        this.textViewListOverlay.setTextColor(ContextCompat.getColor(this, R.color.white));
        this.textViewListHardmix.setTextColor(ContextCompat.getColor(this, R.color.grayText));
        this.textViewListDodge.setTextColor(ContextCompat.getColor(this, R.color.grayText));
        this.textViewListDivide.setTextColor(ContextCompat.getColor(this, R.color.grayText));
        this.textViewListBurn.setTextColor(ContextCompat.getColor(this, R.color.grayText));
    }

    public void setHardmixEffect() {
        this.recyclerViewHardmix.setVisibility(View.VISIBLE);
        this.recyclerViewDodge.setVisibility(View.GONE);
        this.recyclerViewOverlay.setVisibility(View.GONE);
        this.recyclerViewDivide.setVisibility(View.GONE);
        this.recyclerViewBurn.setVisibility(View.GONE);
        this.textViewListOverlay.setTextColor(ContextCompat.getColor(this, R.color.grayText));
        this.textViewListHardmix.setTextColor(ContextCompat.getColor(this, R.color.white));
        this.textViewListDodge.setTextColor(ContextCompat.getColor(this, R.color.grayText));
        this.textViewListDivide.setTextColor(ContextCompat.getColor(this, R.color.grayText));
        this.textViewListBurn.setTextColor(ContextCompat.getColor(this, R.color.grayText));
    }

    public void setDodgeEffect() {
        this.recyclerViewHardmix.setVisibility(View.GONE);
        this.recyclerViewDodge.setVisibility(View.VISIBLE);
        this.recyclerViewDivide.setVisibility(View.GONE);
        this.recyclerViewOverlay.setVisibility(View.GONE);
        this.recyclerViewBurn.setVisibility(View.GONE);
        this.textViewListOverlay.setTextColor(ContextCompat.getColor(this, R.color.grayText));
        this.textViewListHardmix.setTextColor(ContextCompat.getColor(this, R.color.grayText));
        this.textViewListDodge.setTextColor(ContextCompat.getColor(this, R.color.white));
        this.textViewListDivide.setTextColor(ContextCompat.getColor(this, R.color.grayText));
        this.textViewListBurn.setTextColor(ContextCompat.getColor(this, R.color.grayText));
    }

    public void setDivideEffect() {
        this.recyclerViewHardmix.setVisibility(View.GONE);
        this.recyclerViewDodge.setVisibility(View.GONE);
        this.recyclerViewOverlay.setVisibility(View.GONE);
        this.recyclerViewDivide.setVisibility(View.VISIBLE);
        this.recyclerViewBurn.setVisibility(View.GONE);
        this.textViewListHardmix.setTextColor(ContextCompat.getColor(this, R.color.grayText));
        this.textViewListDodge.setTextColor(ContextCompat.getColor(this, R.color.grayText));
        this.textViewListDivide.setTextColor(ContextCompat.getColor(this, R.color.white));
        this.textViewListBurn.setTextColor(ContextCompat.getColor(this, R.color.grayText));
        this.textViewListOverlay.setTextColor(ContextCompat.getColor(this, R.color.grayText));
    }

    public void setBurnEffect() {
        this.recyclerViewHardmix.setVisibility(View.GONE);
        this.recyclerViewDodge.setVisibility(View.GONE);
        this.recyclerViewOverlay.setVisibility(View.GONE);
        this.recyclerViewDivide.setVisibility(View.GONE);
        this.recyclerViewBurn.setVisibility(View.VISIBLE);
        this.textViewListOverlay.setTextColor(ContextCompat.getColor(this, R.color.grayText));
        this.textViewListHardmix.setTextColor(ContextCompat.getColor(this, R.color.grayText));
        this.textViewListDodge.setTextColor(ContextCompat.getColor(this, R.color.grayText));
        this.textViewListDivide.setTextColor(ContextCompat.getColor(this, R.color.grayText));
        this.textViewListBurn.setTextColor(ContextCompat.getColor(this, R.color.white));
    }

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

    private void setBottomToolbar(boolean z) {
        int mVisibility = !z ? View.GONE : View.VISIBLE;
        this.imageViewColor.setVisibility(mVisibility);
        this.imageViewErasePaint.setVisibility(mVisibility);
        this.imageViewUndoPaint.setVisibility(mVisibility);
        this.imageViewRedoPaint.setVisibility(mVisibility);
        this.imageViewCleanPaint.setVisibility(mVisibility);
        this.imageViewCleanNeon.setVisibility(mVisibility);
        this.imageViewNeon.setVisibility(mVisibility);
        this.imageViewEraseNeon.setVisibility(mVisibility);
        this.imageViewUndoNeon.setVisibility(mVisibility);
        this.imageViewRedoNeon.setVisibility(mVisibility);
    }

    public void setImageErasePaint() {
        this.seekbarBrushPaintSize.setVisibility(View.GONE);
        this.recyclerViewPaintListColor.setVisibility(View.GONE);
        this.seekbarEraseNeonSize.setVisibility(View.VISIBLE);
        this.imageViewErasePaint.setImageResource(R.drawable.ic_erase_selected);
        this.quShotEditor.brushEraser();
        this.seekbarEraseNeonSize.setProgress(20);
    }

    public void setImageEraseNeon() {
        this.seekbarBrushNeonSize.setVisibility(View.GONE);
        this.recyclerViewNeonListColor.setVisibility(View.GONE);
        this.seekbarErasePaintSize.setVisibility(View.VISIBLE);
        this.imageViewEraseNeon.setImageResource(R.drawable.ic_erase_selected);
        this.quShotEditor.brushEraser();
        this.seekbarErasePaintSize.setProgress(20);
    }

    public void setColorNeon() {
        this.seekbarBrushNeonSize.setVisibility(View.VISIBLE);
        this.recyclerViewNeonListColor.setVisibility(View.VISIBLE);
        colorAdapter = (ColorAdapter) this.recyclerViewNeonListColor.getAdapter();
        if (colorAdapter != null) {
            colorAdapter.setSelectedColorIndex(0);
        }
        this.recyclerViewNeonListColor.scrollToPosition(0);
        if (colorAdapter != null) {
            colorAdapter.notifyDataSetChanged();
        }
        this.seekbarErasePaintSize.setVisibility(View.GONE);
        this.imageViewEraseNeon.setImageResource(R.drawable.ic_erase);
        this.quShotEditor.setBrushMode(2);
        this.quShotEditor.setBrushDrawingMode(true);
        this.seekbarBrushNeonSize.setProgress(20);
    }

    public void setColorPaint() {
        this.seekbarBrushPaintSize.setVisibility(View.VISIBLE);
        this.recyclerViewPaintListColor.setVisibility(View.VISIBLE);
        this.recyclerViewPaintListColor.scrollToPosition(0);
        colorAdapter = (ColorAdapter) this.recyclerViewPaintListColor.getAdapter();
        if (colorAdapter != null) {
            colorAdapter.setSelectedColorIndex(0);
        }
        if (colorAdapter != null) {
            colorAdapter.notifyDataSetChanged();
        }
        this.seekbarEraseNeonSize.setVisibility(View.GONE);
        this.imageViewErasePaint.setImageResource(R.drawable.ic_erase);
        this.quShotEditor.setBrushMode(1);
        this.quShotEditor.setBrushDrawingMode(true);
        this.seekbarBrushPaintSize.setProgress(20);
    }

    public CGENativeLibrary.LoadImageCallback loadImageCallback = new CGENativeLibrary.LoadImageCallback() {
        public Bitmap loadImage(String string, Object object) {
            try {
                return BitmapFactory.decodeStream(QueShotEditorActivity.this.getAssets().open(string));
            } catch (IOException ioException) {
                return null;
            }
        }
        public void loadImageOK(Bitmap bitmap, Object object) {
            bitmap.recycle();
        }
    };

    @SuppressLint("ClickableViewAccessibility")
    View.OnTouchListener onTouchListener = (view, motionEvent) -> {
        switch (motionEvent.getAction()) {
            case 0:
                QueShotEditorActivity.this.quShotView.getGLSurfaceView().setAlpha(0.0f);
                return true;
            case 1:
                QueShotEditorActivity.this.quShotView.getGLSurfaceView().setAlpha(1.0f);
                return false;
            default:
                return true;
        }
    };

    public void onRequestPermissionsResult(int i, @NonNull String[] string, @NonNull int[] i2) {
        super.onRequestPermissionsResult(i, string, i2);
    }

    public void onAddViewListener(Drawing viewType, int i) {
        Log.d(TAG, "onAddViewListener() called with: viewType = [" + viewType + "], numberOfAddedViews = [" + i + "]");
    }

    public void onRemoveViewListener(int i) {
        Log.d(TAG, "onRemoveViewListener() called with: numberOfAddedViews = [" + i + "]");
    }

    public void onRemoveViewListener(Drawing viewType, int i) {
        Log.d(TAG, "onRemoveViewListener() called with: viewType = [" + viewType + "], numberOfAddedViews = [" + i + "]");
    }

    public void onStartViewChangeListener(Drawing viewType) {
        Log.d(TAG, "onStartViewChangeListener() called with: viewType = [" + viewType + "]");
    }

    public void onStopViewChangeListener(Drawing viewType) {
        Log.d(TAG, "onStopViewChangeListener() called with: viewType = [" + viewType + "]");
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageViewCloseAdjust:
            case R.id.image_view_close_paint:
            case R.id.image_view_close_neon:
            case R.id.image_view_close_sticker_men:
            case R.id.image_view_close_sticker_women:
            case R.id.image_view_close_sticker:
            case R.id.imageViewCloseText:
            case R.id.image_view_close_filter:
            case R.id.image_view_close_hardmix:
                setVisibleSave();
                onBackPressed();
                return;
            case R.id.imageViewSaveAdjust:
                new SaveFilter().execute();
                this.imageViewCompareAdjust.setVisibility(View.GONE);
                this.constraintLayoutAdjust.setVisibility(View.GONE);
                this.recyclerViewTools.setVisibility(View.VISIBLE);
                this.constraintLayoutSave.setVisibility(View.VISIBLE);
                ConstraintSet constraintsetAdjust = new ConstraintSet();
                constraintsetAdjust.clone(this.constraintLayoutView);
                constraintsetAdjust.connect(this.relativeLayoutWrapper.getId(), 1, this.constraintLayoutView.getId(), 1, 0);
                constraintsetAdjust.connect(this.relativeLayoutWrapper.getId(), 4, this.guideline.getId(), 3, 0);
                constraintsetAdjust.connect(this.relativeLayoutWrapper.getId(), 2, this.constraintLayoutView.getId(), 2, 0);
                constraintsetAdjust.applyTo(this.constraintLayoutView);
                setVisibleSave();
                this.moduleToolsId = Module.NONE;
                return;
            case R.id.image_view_save_paint:
                showLoading(true);
                runOnUiThread(() -> {
                    quShotEditor.setBrushDrawingMode(false);
                    imageViewUndoPaint.setVisibility(View.GONE);
                    imageViewRedoPaint.setVisibility(View.GONE);
                    imageViewCleanPaint.setVisibility(View.GONE);
                    imageViewErasePaint.setVisibility(View.GONE);
                    this.recyclerViewTools.setVisibility(View.VISIBLE);
                    this.constraintLayoutSavePaint.setVisibility(View.GONE);
                    this.constraintLayoutPaint.setVisibility(View.GONE);
                    ConstraintSet constraintSet = new ConstraintSet();
                    constraintSet.clone(constraintLayoutView);
                    constraintSet.connect(relativeLayoutWrapper.getId(), 1, constraintLayoutView.getId(), 1, 0);
                    constraintSet.connect(relativeLayoutWrapper.getId(), 4, guideline.getId(), 3, 0);
                    constraintSet.connect(relativeLayoutWrapper.getId(), 2, constraintLayoutView.getId(), 2, 0);
                    constraintSet.applyTo(constraintLayoutView);
                    quShotView.setImageSource(quShotEditor.getBrushDrawingView().getDrawBitmap(quShotView.getCurrentBitmap()));
                    quShotEditor.clearBrushAllViews();
                    showLoading(false);
                    reloadingLayout();
                });
                setVisibleSave();
                this.moduleToolsId = Module.NONE;
                return;
            case R.id.image_view_save_neon:
                showLoading(true);
                runOnUiThread(() -> {
                    quShotEditor.setBrushDrawingMode(false);
                    imageViewUndoNeon.setVisibility(View.GONE);
                    imageViewRedoNeon.setVisibility(View.GONE);
                    this.recyclerViewTools.setVisibility(View.VISIBLE);
                    this.constraintLayoutSaveNeon.setVisibility(View.GONE);
                    this.constraintLayoutNeon.setVisibility(View.GONE);
                    imageViewEraseNeon.setVisibility(View.GONE);
                    ConstraintSet constraintSet = new ConstraintSet();
                    constraintSet.clone(constraintLayoutView);
                    constraintSet.connect(relativeLayoutWrapper.getId(), 1, constraintLayoutView.getId(), 1, 0);
                    constraintSet.connect(relativeLayoutWrapper.getId(), 4, guideline.getId(), 3, 0);
                    constraintSet.connect(relativeLayoutWrapper.getId(), 2, constraintLayoutView.getId(), 2, 0);
                    constraintSet.applyTo(constraintLayoutView);
                    quShotView.setImageSource(quShotEditor.getBrushDrawingView().getDrawBitmap(quShotView.getCurrentBitmap()));
                    quShotEditor.clearBrushAllViews();
                    showLoading(false);
                    reloadingLayout();
                });
                setVisibleSave();
                this.moduleToolsId = Module.NONE;
                return;
            case R.id.image_view_save_filter:
                new SaveFilter().execute();
                this.imageViewCompareFilter.setVisibility(View.GONE);
                this.constraintLayoutFilter.setVisibility(View.GONE);
                this.constraintLayoutSaveFilter.setVisibility(View.GONE);
                this.recyclerViewTools.setVisibility(View.VISIBLE);
                setVisibleSave();
                ConstraintSet constraintsete = new ConstraintSet();
                constraintsete.clone(this.constraintLayoutView);
                constraintsete.connect(this.relativeLayoutWrapper.getId(), 1, this.constraintLayoutView.getId(), 1, 0);
                constraintsete.connect(this.relativeLayoutWrapper.getId(), 4, this.guideline.getId(), 3, 0);
                constraintsete.connect(this.relativeLayoutWrapper.getId(), 2, this.constraintLayoutView.getId(), 2, 0);
                constraintsete.applyTo(this.constraintLayoutView);
                this.moduleToolsId = Module.NONE;
                return;
            case R.id.image_view_save_hardmix:
                new SaveFilter().execute();
                this.imageViewCompareEffect.setVisibility(View.GONE);
                this.constraintLayoutEffect.setVisibility(View.GONE);
                this.constraintLayoutSaveEffect.setVisibility(View.GONE);
                this.recyclerViewTools.setVisibility(View.VISIBLE);
                this.moduleToolsId = Module.NONE;
                ConstraintSet constraintsetEffect = new ConstraintSet();
                constraintsetEffect.clone(this.constraintLayoutView);
                constraintsetEffect.connect(this.relativeLayoutWrapper.getId(), 1, this.constraintLayoutView.getId(), 1, 0);
                constraintsetEffect.connect(this.relativeLayoutWrapper.getId(), 4, this.guideline.getId(), 3, 0);
                constraintsetEffect.connect(this.relativeLayoutWrapper.getId(), 2, this.constraintLayoutView.getId(), 2, 0);
                constraintsetEffect.applyTo(this.constraintLayoutView);
                setVisibleSave();
                this.moduleToolsId = Module.NONE;
                return;
            case R.id.image_view_save_sticker:
                this.quShotView.setHandlingSticker(null);
                this.quShotView.setLocked(true);
                this.seekbarSticker.setVisibility(View.GONE);
                this.imageViewAddSticker.setVisibility(View.GONE);
                this.recyclerViewTools.setVisibility(View.VISIBLE);
                if (!this.quShotView.getStickers().isEmpty()) {
                    new SaveSticker().execute();
                }
                this.linear_layout_wrapper_sticker_list.setVisibility(View.VISIBLE);
                this.constraintLayoutSticker.setVisibility(View.GONE);
                setVisibleSave();
                this.moduleToolsId = Module.NONE;
                return;
            case R.id.image_view_save_sticker_men:
                this.quShotView.setHandlingSticker(null);
                this.quShotView.setLocked(true);
                this.seekbarStickerMen.setVisibility(View.GONE);
                this.imageViewAddStickerMen.setVisibility(View.GONE);
                this.recyclerViewTools.setVisibility(View.VISIBLE);
                if (!this.quShotView.getStickers().isEmpty()) {
                    new SaveSticker().execute();
                }
                this.linear_layout_wrapper_sticker_men_list.setVisibility(View.VISIBLE);
                this.constraintLayoutStickerMen.setVisibility(View.GONE);
                setVisibleSave();
                this.moduleToolsId = Module.NONE;
                return;
            case R.id.image_view_save_sticker_women:
                this.quShotView.setHandlingSticker(null);
                this.quShotView.setLocked(true);
                this.seekbarStickerWomen.setVisibility(View.GONE);
                this.imageViewAddStickerWomen.setVisibility(View.GONE);
                this.recyclerViewTools.setVisibility(View.VISIBLE);
                if (!this.quShotView.getStickers().isEmpty()) {
                    new SaveSticker().execute();
                }
                this.linear_layout_wrapper_sticker_women_list.setVisibility(View.VISIBLE);
                this.constraintLayoutStickerWomen.setVisibility(View.GONE);
                setVisibleSave();
                this.moduleToolsId = Module.NONE;
                return;
            case R.id.imageViewSaveText:
                this.quShotView.setHandlingSticker(null);
                this.quShotView.setLocked(true);
                this.constraintLayoutAddText.setVisibility(View.GONE);
                this.relativeLayoutAddText.setVisibility(View.GONE);
                if (!this.quShotView.getStickers().isEmpty()) {
                    new SaveSticker().execute();
                }
                ConstraintSet constraintsetText = new ConstraintSet();
                constraintsetText.clone(this.constraintLayoutView);
                constraintsetText.connect(this.relativeLayoutWrapper.getId(), 1, this.constraintLayoutView.getId(), 1, 0);
                constraintsetText.connect(this.relativeLayoutWrapper.getId(), 4, this.guideline.getId(), 3, 0);
                constraintsetText.connect(this.relativeLayoutWrapper.getId(), 2, this.constraintLayoutView.getId(), 2, 0);
                constraintsetText.applyTo(this.constraintLayoutView);
                this.recyclerViewTools.setVisibility(View.VISIBLE);
                setVisibleSave();
                this.moduleToolsId = Module.NONE;
                return;
            case R.id.image_view_redo_neon:
            case R.id.image_view_redo:
                this.quShotEditor.redoBrush();
                return;
            case R.id.image_view_undo_neon:
            case R.id.image_view_undo:
                this.quShotEditor.undoBrush();
                return;
            case R.id.image_view_clean_neon:
            case R.id.image_view_clean:
                this.quShotEditor.clearBrushAllViews();
                return;
            default:
        }
    }

    public void isPermissionGranted(boolean z, String string) {
        if (z) {
            new SaveEditingBitmap().execute();
        }
    }

    public void onFilterSelected(String string) {
        this.quShotEditor.setFilterEffect(string);
        this.seekbarIntensityFilter.setProgress(50);
        this.seekbarIntensityEffect.setProgress(50);
        if (this.moduleToolsId == Module.FILTER) {
            this.quShotView.getGLSurfaceView().setFilterIntensity(0.5f);
        }
        if (this.moduleToolsId == Module.OVERLAY) {
            this.quShotView.getGLSurfaceView().setFilterIntensity(0.5f);
        }

    }

    public void textFragment() {
        this.addTextFragment = TextFragment.show(this);
        this.textEditor = new TextFragment.TextEditor() {
            public void onDone(QueShotText addTextProperties) {
                QueShotEditorActivity.this.quShotView.addSticker(new QueShotTextView(QueShotEditorActivity.this.getApplicationContext(), addTextProperties));
            }

            public void onBackButton() {
                if (QueShotEditorActivity.this.quShotView.getStickers().isEmpty()) {
                    QueShotEditorActivity.this.onBackPressed();
                }
            }
        };
        this.addTextFragment.setOnTextEditorListener(this.textEditor);
    }

    public void onQuShotToolSelected(Module module) {
        this.moduleToolsId = module;
        ConstraintSet constraintSet;
        switch (module) {
            case TEXT:
                setGoneSave();
                this.quShotView.setLocked(false);
                textFragment();
                this.recyclerViewTools.setVisibility(View.GONE);
                this.constraintLayoutDraw.setVisibility(View.GONE);
                this.constraintLayoutEmoji.setVisibility(View.GONE);
                QueShotEditorActivity.this.quShotView.setHandlingSticker(null);
                QueShotEditorActivity.this.quShotView.getStickers().clear();
                QueShotEditorActivity.this.quShotEditor.setFilterEffect("");
                QueShotEditorActivity.this.quShotEditor.clearBrushAllViews();
                QueShotEditorActivity.this.quShotEditor.setBrushDrawingMode(false);
                QueShotEditorActivity.this.quShotView.setHandlingSticker(null);
                QueShotEditorActivity.this.quShotView.getStickers().clear();
                QueShotEditorActivity.this.quShotEditor.setFilterEffect("");
                QueShotEditorActivity.this.quShotEditor.clearBrushAllViews();
                QueShotEditorActivity.this.quShotEditor.setBrushDrawingMode(false);
                ConstraintSet constraintsetEffect = new ConstraintSet();
                constraintsetEffect.clone(this.constraintLayoutView);
                constraintsetEffect.connect(this.relativeLayoutWrapper.getId(), 1, this.constraintLayoutView.getId(), 1, 0);
                constraintsetEffect.connect(this.relativeLayoutWrapper.getId(), 4, this.guideline.getId(), 3, 0);
                constraintsetEffect.connect(this.relativeLayoutWrapper.getId(), 2, this.constraintLayoutView.getId(), 2, 0);
                constraintsetEffect.applyTo(this.constraintLayoutView);
                this.constraintLayoutAddText.setVisibility(View.VISIBLE);
                this.relativeLayoutAddText.setVisibility(View.VISIBLE);
                break;
            case ADJUST:
                setGoneSave();
                this.imageViewCompareAdjust.setVisibility(View.VISIBLE);
                this.constraintLayoutDraw.setVisibility(View.GONE);
                this.constraintLayoutEmoji.setVisibility(View.GONE);
                QueShotEditorActivity.this.constraintLayoutAdjust.setVisibility(View.VISIBLE);
                QueShotEditorActivity.this.constraintLayoutNeon.setVisibility(View.GONE);
                QueShotEditorActivity.this.constraintLayoutFilter.setVisibility(View.GONE);
                QueShotEditorActivity.this.constraintLayoutPaint.setVisibility(View.GONE);
                if (!this.quShotView.getStickers().isEmpty()) {
                    this.quShotView.getStickers().clear();
                    this.quShotView.setHandlingSticker(null);
                }

                ConstraintSet constraintsetAdjust = new ConstraintSet();
                constraintsetAdjust.clone(this.constraintLayoutView);
                constraintsetAdjust.connect(this.relativeLayoutWrapper.getId(), 1, this.constraintLayoutView.getId(), 1, 0);
                constraintsetAdjust.connect(this.relativeLayoutWrapper.getId(), 4, this.guidelinePaint.getId(), 3, 0);
                constraintsetAdjust.connect(this.relativeLayoutWrapper.getId(), 2, this.constraintLayoutView.getId(), 2, 0);
                constraintsetAdjust.applyTo(this.constraintLayoutView);
                this.recyclerViewTools.setVisibility(View.GONE);
                this.constraintLayoutSave.setVisibility(View.GONE);
                this.relativeLayoutAddText.setVisibility(View.GONE);
                this.constraintLayoutAddText.setVisibility(View.GONE);
                this.quShotEditor.clearBrushAllViews();
                this.quShotEditor.setBrushDrawingMode(false);
                this.adjustAdapter = new AdjustAdapter(getApplicationContext(), this);
                this.recyclerViewAdjust.setAdapter(this.adjustAdapter);
                this.adjustAdapter.setSelectedAdjust(0);
                this.quShotEditor.setAdjustFilter(this.adjustAdapter.getFilterConfig());
                break;
            case FILTER:
                setGoneSave();
                if (!this.quShotView.getStickers().isEmpty()) {
                    this.quShotView.getStickers().clear();
                    this.quShotView.setHandlingSticker(null);
                }
                this.relativeLayoutAddText.setVisibility(View.GONE);
                this.constraintLayoutAddText.setVisibility(View.GONE);
                this.constraintLayoutDraw.setVisibility(View.GONE);
                this.constraintLayoutEmoji.setVisibility(View.GONE);
                new allFilters().execute();
                new bwFilters().execute();
                new vintageFilters().execute();
                new smoothFilters().execute();
                new coldFilters().execute();
                new warmFilters().execute();
                new legacyFilters().execute();
                break;
            case EMOJI:
                this.constraintLayoutEmoji.setVisibility(View.VISIBLE);
                this.constraintLayoutDraw.setVisibility(View.GONE);
                break;
            case DRAW:
                this.constraintLayoutDraw.setVisibility(View.VISIBLE);
                this.constraintLayoutEmoji.setVisibility(View.GONE);
                break;
            case OVERLAY:
                setGoneSave();
                new effectOvarlay().execute();
                new effectHardmix().execute();
                new effectDodge().execute();
                new effectBurn().execute();
                new effectDivide().execute();
                this.constraintLayoutDraw.setVisibility(View.GONE);
                this.constraintLayoutEmoji.setVisibility(View.GONE);
                break;
            case RATIO:
                new openBlurFragment().execute();
                this.constraintLayoutDraw.setVisibility(View.GONE);
                this.constraintLayoutEmoji.setVisibility(View.GONE);
                goneLayout();
                break;
            case BACKGROUND:
                new openFrameFragment().execute();
                this.constraintLayoutDraw.setVisibility(View.GONE);
                this.constraintLayoutEmoji.setVisibility(View.GONE);
                goneLayout();
                break;
            case SPLASH:
                new openSplashBrushFragment(true).execute();
                this.constraintLayoutDraw.setVisibility(View.GONE);
                this.constraintLayoutEmoji.setVisibility(View.GONE);
                break;
            case SQUARESPLASH:
                new openSplashSquareFragment(true).execute();
                this.constraintLayoutDraw.setVisibility(View.GONE);
                this.constraintLayoutEmoji.setVisibility(View.GONE);
                break;
            case BLUR:
                new openSplashBrushFragment(false).execute();
                this.constraintLayoutDraw.setVisibility(View.GONE);
                this.constraintLayoutEmoji.setVisibility(View.GONE);
                break;
            case SQUAEBLUR:
                new openSplashSquareFragment(false).execute();
                this.constraintLayoutDraw.setVisibility(View.GONE);
                this.constraintLayoutEmoji.setVisibility(View.GONE);
                break;
            case COLOR:
                ColorSplashFragment.show(this, this.quShotView.getCurrentBitmap());
                this.constraintLayoutDraw.setVisibility(View.GONE);
                this.constraintLayoutEmoji.setVisibility(View.GONE);
                goneLayout();
                break;
            case CROP:
                CropFragment.show(this, this, this.quShotView.getCurrentBitmap());
                this.constraintLayoutDraw.setVisibility(View.GONE);
                this.constraintLayoutEmoji.setVisibility(View.GONE);
                goneLayout();
                break;
            case ROTATE:
                RotateFragment.show(this, this, this.quShotView.getCurrentBitmap());
                this.constraintLayoutDraw.setVisibility(View.GONE);
                this.constraintLayoutEmoji.setVisibility(View.GONE);
                goneLayout();
                break;
        }
        this.quShotView.setHandlingSticker(null);
    }

    public void onQuShotStickerToolSelected(Module module) {
        this.moduleToolsId = module;
        ConstraintSet constraintSet;
        switch (module) {
            case STICKER:
                setGoneSave();
                this.quShotView.setLocked(false);
                QueShotEditorActivity.this.constraintLayoutSticker.setVisibility(View.VISIBLE);
                QueShotEditorActivity.this.constraintLayoutAdjust.setVisibility(View.GONE);
                QueShotEditorActivity.this.constraintLayoutEmoji.setVisibility(View.GONE);
                QueShotEditorActivity.this.constraintLayoutNeon.setVisibility(View.GONE);
                QueShotEditorActivity.this.constraintLayoutFilter.setVisibility(View.GONE);
                QueShotEditorActivity.this.linear_layout_wrapper_sticker_list.setVisibility(View.VISIBLE);
                QueShotEditorActivity.this.quShotEditor.setFilterEffect("");
                QueShotEditorActivity.this.quShotEditor.clearBrushAllViews();
                QueShotEditorActivity.this.quShotEditor.setBrushDrawingMode(false);
                if (!this.quShotView.getStickers().isEmpty()) {
                    this.quShotView.getStickers().clear();
                    this.quShotView.setHandlingSticker(null);
                }
                this.relativeLayoutAddText.setVisibility(View.GONE);
                this.constraintLayoutAddText.setVisibility(View.GONE);
                break;
            case MACKUER:
                setGoneSave();
                this.quShotView.setLocked(false);
                QueShotEditorActivity.this.constraintLayoutStickerMen.setVisibility(View.VISIBLE);
                QueShotEditorActivity.this.constraintLayoutAdjust.setVisibility(View.GONE);
                QueShotEditorActivity.this.constraintLayoutEmoji.setVisibility(View.GONE);
                QueShotEditorActivity.this.constraintLayoutNeon.setVisibility(View.GONE);
                QueShotEditorActivity.this.constraintLayoutFilter.setVisibility(View.GONE);
                QueShotEditorActivity.this.linear_layout_wrapper_sticker_men_list.setVisibility(View.VISIBLE);
                QueShotEditorActivity.this.quShotEditor.setFilterEffect("");
                QueShotEditorActivity.this.quShotEditor.clearBrushAllViews();
                QueShotEditorActivity.this.quShotEditor.setBrushDrawingMode(false);
                if (!this.quShotView.getStickers().isEmpty()) {
                    this.quShotView.getStickers().clear();
                    this.quShotView.setHandlingSticker(null);
                }
                this.relativeLayoutAddText.setVisibility(View.GONE);
                this.constraintLayoutAddText.setVisibility(View.GONE);
                break;
            case BEAUTY:
                setGoneSave();
                this.quShotView.setLocked(false);
                QueShotEditorActivity.this.constraintLayoutStickerWomen.setVisibility(View.VISIBLE);
                QueShotEditorActivity.this.linear_layout_wrapper_sticker_women_list.setVisibility(View.VISIBLE);
                QueShotEditorActivity.this.constraintLayoutAdjust.setVisibility(View.GONE);
                QueShotEditorActivity.this.constraintLayoutEmoji.setVisibility(View.GONE);
                QueShotEditorActivity.this.constraintLayoutNeon.setVisibility(View.GONE);
                QueShotEditorActivity.this.constraintLayoutFilter.setVisibility(View.GONE);
                QueShotEditorActivity.this.quShotEditor.setFilterEffect("");
                QueShotEditorActivity.this.quShotEditor.clearBrushAllViews();
                QueShotEditorActivity.this.quShotEditor.setBrushDrawingMode(false);
                if (!this.quShotView.getStickers().isEmpty()) {
                    this.quShotView.getStickers().clear();
                    this.quShotView.setHandlingSticker(null);
                }
                this.relativeLayoutAddText.setVisibility(View.GONE);
                this.constraintLayoutAddText.setVisibility(View.GONE);
                break;
        }
        this.quShotView.setHandlingSticker(null);
    }

    public void onQuShotDrawToolSelected(Module module) {
        this.moduleToolsId = module;
        ConstraintSet constraintSet;
        switch (module) {
            case PAINT:
                setColorPaint();
                this.quShotEditor.setBrushDrawingMode(true);
                this.constraintLayoutPaint.setVisibility(View.VISIBLE);
                this.recyclerViewTools.setVisibility(View.GONE);
                this.constraintLayoutDraw.setVisibility(View.GONE);
                QueShotEditorActivity.this.constraintLayoutPaint.setVisibility(View.VISIBLE);
                QueShotEditorActivity.this.constraintLayoutAdjust.setVisibility(View.GONE);
                QueShotEditorActivity.this.constraintLayoutFilter.setVisibility(View.GONE);
                QueShotEditorActivity.this.constraintLayoutNeon.setVisibility(View.GONE);
                this.constraintLayoutSavePaint.setVisibility(View.VISIBLE);
                this.relativeLayoutAddText.setVisibility(View.GONE);
                this.constraintLayoutAddText.setVisibility(View.GONE);
                QueShotEditorActivity.this.quShotEditor.setFilterEffect("");
                this.quShotEditor.clearBrushAllViews();
                this.quShotEditor.setBrushDrawingMode(false);
                setGoneSave();
                setBottomToolbar(true);
                constraintSet = new ConstraintSet();
                constraintSet.clone(this.constraintLayoutView);
                constraintSet.connect(this.relativeLayoutWrapper.getId(), 1, this.constraintLayoutView.getId(), 1, 0);
                constraintSet.connect(this.relativeLayoutWrapper.getId(), 4, this.guidelinePaint.getId(), 3, 0);
                constraintSet.connect(this.relativeLayoutWrapper.getId(), 2, this.constraintLayoutView.getId(), 2, 0);
                constraintSet.applyTo(this.constraintLayoutView);
                this.quShotEditor.setBrushMode(1);
                reloadingLayout();
                break;
            case COLORED:
                new openColoredFragment().execute();
                this.constraintLayoutDraw.setVisibility(View.GONE);
                goneLayout();
                break;
            case NEON:
                setColorNeon();
                this.quShotEditor.setBrushDrawingMode(true);
                this.recyclerViewTools.setVisibility(View.GONE);
                this.constraintLayoutSaveNeon.setVisibility(View.VISIBLE);
                this.constraintLayoutDraw.setVisibility(View.GONE);
                QueShotEditorActivity.this.constraintLayoutNeon.setVisibility(View.VISIBLE);
                QueShotEditorActivity.this.constraintLayoutAdjust.setVisibility(View.GONE);
                QueShotEditorActivity.this.constraintLayoutFilter.setVisibility(View.GONE);
                QueShotEditorActivity.this.constraintLayoutPaint.setVisibility(View.GONE);
                this.relativeLayoutAddText.setVisibility(View.GONE);
                this.constraintLayoutAddText.setVisibility(View.GONE);
                QueShotEditorActivity.this.quShotEditor.setFilterEffect("");
                this.quShotEditor.clearBrushAllViews();
                this.quShotEditor.setBrushDrawingMode(false);
                setGoneSave();
                setBottomToolbar(true);
                constraintSet = new ConstraintSet();
                constraintSet.clone(this.constraintLayoutView);
                constraintSet.connect(this.relativeLayoutWrapper.getId(), 1, this.constraintLayoutView.getId(), 1, 0);
                constraintSet.connect(this.relativeLayoutWrapper.getId(), 4, this.constraintLayoutNeon.getId(), 3, 0);
                constraintSet.connect(this.relativeLayoutWrapper.getId(), 2, this.constraintLayoutView.getId(), 2, 0);
                constraintSet.applyTo(this.constraintLayoutView);
                this.quShotEditor.setBrushMode(2);
                reloadingLayout();
                break;
            case MOSAIC:
                new openShapeFragment().execute();
                this.constraintLayoutDraw.setVisibility(View.GONE);
                goneLayout();
                break;
        }
        this.quShotView.setHandlingSticker(null);
    }

    private void goneLayout() {
        setVisibleSave();
    }

    public void setGoneSave() {
        this.constraintLayoutSaveEditing.setVisibility(View.GONE);
    }

    public void setVisibleSave() {
        this.constraintLayoutSaveEditing.setVisibility(View.VISIBLE);
    }

    public void onBackPressed() {
        if (this.moduleToolsId != null) {
            try {
                switch (this.moduleToolsId) {
                    case PAINT:
                        this.constraintLayoutPaint.setVisibility(View.GONE);
                        setVisibleSave();
                        this.imageViewUndoPaint.setVisibility(View.GONE);
                        this.imageViewRedoPaint.setVisibility(View.GONE);
                        this.imageViewCleanPaint.setVisibility(View.GONE);
                        this.imageViewErasePaint.setVisibility(View.GONE);
                        this.recyclerViewTools.setVisibility(View.VISIBLE);
                        this.constraintLayoutSavePaint.setVisibility(View.GONE);
                        this.quShotEditor.setBrushDrawingMode(false);
                        ConstraintSet constraintSet = new ConstraintSet();
                        constraintSet.clone(this.constraintLayoutView);
                        constraintSet.connect(this.relativeLayoutWrapper.getId(), 1, this.constraintLayoutView.getId(), 1, 0);
                        constraintSet.connect(this.relativeLayoutWrapper.getId(), 4, this.guideline.getId(), 3, 0);
                        constraintSet.connect(this.relativeLayoutWrapper.getId(), 2, this.constraintLayoutView.getId(), 2, 0);
                        constraintSet.applyTo(this.constraintLayoutView);
                        this.quShotEditor.clearBrushAllViews();
                        setVisibleSave();
                        this.moduleToolsId = Module.NONE;
                        reloadingLayout();
                        return;
                    case NEON:
                        setVisibleSave();
                        this.imageViewUndoNeon.setVisibility(View.GONE);
                        this.imageViewRedoNeon.setVisibility(View.GONE);
                        this.imageViewEraseNeon.setVisibility(View.GONE);
                        this.constraintLayoutNeon.setVisibility(View.GONE);
                        this.recyclerViewTools.setVisibility(View.VISIBLE);
                        this.constraintLayoutSaveNeon.setVisibility(View.GONE);
                        this.quShotEditor.setBrushDrawingMode(false);
                        ConstraintSet constraintSet1 = new ConstraintSet();
                        constraintSet1.clone(this.constraintLayoutView);
                        constraintSet1.connect(this.relativeLayoutWrapper.getId(), 1, this.constraintLayoutView.getId(), 1, 0);
                        constraintSet1.connect(this.relativeLayoutWrapper.getId(), 4, this.guideline.getId(), 3, 0);
                        constraintSet1.connect(this.relativeLayoutWrapper.getId(), 2, this.constraintLayoutView.getId(), 2, 0);
                        constraintSet1.applyTo(this.constraintLayoutView);
                        this.quShotEditor.clearBrushAllViews();
                        setVisibleSave();
                        this.moduleToolsId = Module.NONE;
                        reloadingLayout();
                        return;
                    case TEXT:
                        if (!this.quShotView.getStickers().isEmpty()) {
                            this.quShotView.getStickers().clear();
                            this.quShotView.setHandlingSticker(null);
                        }
                        this.recyclerViewTools.setVisibility(View.VISIBLE);
                        this.relativeLayoutAddText.setVisibility(View.GONE);
                        this.constraintLayoutAddText.setVisibility(View.GONE);
                        this.quShotView.setHandlingSticker(null);
                        this.quShotView.setLocked(true);
                        ConstraintSet constraintsetEffect = new ConstraintSet();
                        constraintsetEffect.clone(this.constraintLayoutView);
                        constraintsetEffect.connect(this.relativeLayoutWrapper.getId(), 1, this.constraintLayoutView.getId(), 1, 0);
                        constraintsetEffect.connect(this.relativeLayoutWrapper.getId(), 4, this.guideline.getId(), 3, 0);
                        constraintsetEffect.connect(this.relativeLayoutWrapper.getId(), 2, this.constraintLayoutView.getId(), 2, 0);
                        constraintsetEffect.applyTo(this.constraintLayoutView);
                        setVisibleSave();
                        this.moduleToolsId = Module.NONE;
                        return;
                    case ADJUST:
                        this.quShotEditor.setFilterEffect("");
                        this.imageViewCompareAdjust.setVisibility(View.GONE);
                        this.constraintLayoutAdjust.setVisibility(View.GONE);
                        ConstraintSet constraintsetAdjust = new ConstraintSet();
                        constraintsetAdjust.clone(this.constraintLayoutView);
                        constraintsetAdjust.connect(this.relativeLayoutWrapper.getId(), 1, this.constraintLayoutView.getId(), 1, 0);
                        constraintsetAdjust.connect(this.relativeLayoutWrapper.getId(), 4, this.guideline.getId(), 3, 0);
                        constraintsetAdjust.connect(this.relativeLayoutWrapper.getId(), 2, this.constraintLayoutView.getId(), 2, 0);
                        constraintsetAdjust.applyTo(this.constraintLayoutView);
                        this.recyclerViewTools.setVisibility(View.VISIBLE);
                        this.constraintLayoutSave.setVisibility(View.VISIBLE);
                        setVisibleSave();
                        this.moduleToolsId = Module.NONE;
                        return;
                    case FILTER:
                        QueShotEditorActivity.this.constraintLayoutFilter.setVisibility(View.GONE);
                        this.constraintLayoutSaveFilter.setVisibility(View.GONE);
                        this.recyclerViewTools.setVisibility(View.VISIBLE);
                        ConstraintSet constraintsete = new ConstraintSet();
                        constraintsete.clone(this.constraintLayoutView);
                        constraintsete.connect(this.relativeLayoutWrapper.getId(), 1, this.constraintLayoutView.getId(), 1, 0);
                        constraintsete.connect(this.relativeLayoutWrapper.getId(), 4, this.guideline.getId(), 3, 0);
                        constraintsete.connect(this.relativeLayoutWrapper.getId(), 2, this.constraintLayoutView.getId(), 2, 0);
                        constraintsete.applyTo(this.constraintLayoutView);
                        setVisibleSave();
                        this.quShotEditor.setFilterEffect("");
                        this.imageViewCompareFilter.setVisibility(View.GONE);
                        this.listAllFilter.clear();
                        if (this.recyclerViewAllFilter.getAdapter() != null) {
                            this.recyclerViewAllFilter.getAdapter().notifyDataSetChanged();
                        }
                        this.moduleToolsId = Module.NONE;
                        return;
                    case STICKER:
                        if (this.quShotView.getStickers().size() <= 0) {
                            this.linear_layout_wrapper_sticker_list.setVisibility(View.VISIBLE);
                            this.constraintLayoutSticker.setVisibility(View.GONE);
                            this.imageViewAddSticker.setVisibility(View.GONE);
                            this.quShotView.setHandlingSticker(null);
                            this.recyclerViewTools.setVisibility(View.VISIBLE);
                            this.quShotView.setLocked(true);
                            this.moduleToolsId = Module.NONE;
                        } else if (this.imageViewAddSticker.getVisibility() == View.VISIBLE) {
                            this.quShotView.getStickers().clear();
                            this.imageViewAddSticker.setVisibility(View.GONE);
                            this.quShotView.setHandlingSticker(null);
                            this.linear_layout_wrapper_sticker_list.setVisibility(View.VISIBLE);
                            this.constraintLayoutSticker.setVisibility(View.GONE);
                            this.recyclerViewTools.setVisibility(View.VISIBLE);
                            this.moduleToolsId = Module.NONE;
                        } else {
                            this.linear_layout_wrapper_sticker_list.setVisibility(View.GONE);
                            this.imageViewAddSticker.setVisibility(View.VISIBLE);
                        }
                        this.linear_layout_wrapper_sticker_list.setVisibility(View.VISIBLE);
                        this.moduleToolsId = Module.NONE;
                        setVisibleSave();
                        return;
                    case MACKUER:
                        if (this.quShotView.getStickers().size() <= 0) {
                            this.linear_layout_wrapper_sticker_men_list.setVisibility(View.VISIBLE);
                            this.constraintLayoutStickerMen.setVisibility(View.GONE);
                            this.imageViewAddStickerMen.setVisibility(View.GONE);
                            this.quShotView.setHandlingSticker(null);
                            this.recyclerViewTools.setVisibility(View.VISIBLE);
                            this.quShotView.setLocked(true);
                            this.moduleToolsId = Module.NONE;
                        } else if (this.imageViewAddStickerMen.getVisibility() == View.VISIBLE) {
                            this.quShotView.getStickers().clear();
                            this.imageViewAddStickerMen.setVisibility(View.GONE);
                            this.quShotView.setHandlingSticker(null);
                            this.linear_layout_wrapper_sticker_men_list.setVisibility(View.VISIBLE);
                            this.constraintLayoutStickerMen.setVisibility(View.GONE);
                            this.recyclerViewTools.setVisibility(View.VISIBLE);
                            this.moduleToolsId = Module.NONE;
                        } else {
                            this.linear_layout_wrapper_sticker_men_list.setVisibility(View.GONE);
                            this.imageViewAddStickerMen.setVisibility(View.VISIBLE);
                        }
                        this.linear_layout_wrapper_sticker_men_list.setVisibility(View.VISIBLE);
                        this.moduleToolsId = Module.NONE;
                        setVisibleSave();
                        return;
                    case BEAUTY:
                        if (this.quShotView.getStickers().size() <= 0) {
                            this.linear_layout_wrapper_sticker_women_list.setVisibility(View.VISIBLE);
                            this.constraintLayoutStickerWomen.setVisibility(View.GONE);
                            this.imageViewAddStickerWomen.setVisibility(View.GONE);
                            this.quShotView.setHandlingSticker(null);
                            this.recyclerViewTools.setVisibility(View.VISIBLE);
                            this.quShotView.setLocked(true);
                            this.moduleToolsId = Module.NONE;
                        } else if (this.imageViewAddStickerWomen.getVisibility() == View.VISIBLE) {
                            this.quShotView.getStickers().clear();
                            this.imageViewAddStickerWomen.setVisibility(View.GONE);
                            this.quShotView.setHandlingSticker(null);
                            this.linear_layout_wrapper_sticker_women_list.setVisibility(View.VISIBLE);
                            this.constraintLayoutStickerWomen.setVisibility(View.GONE);
                            this.recyclerViewTools.setVisibility(View.VISIBLE);
                            this.moduleToolsId = Module.NONE;
                        } else {
                            this.linear_layout_wrapper_sticker_women_list.setVisibility(View.GONE);
                            this.imageViewAddStickerWomen.setVisibility(View.VISIBLE);
                        }
                        this.linear_layout_wrapper_sticker_women_list.setVisibility(View.VISIBLE);
                        this.moduleToolsId = Module.NONE;
                        setVisibleSave();
                        return;
                    case OVERLAY:
                        this.quShotEditor.setFilterEffect("");
                        this.imageViewCompareEffect.setVisibility(View.GONE);
                        QueShotEditorActivity.this.constraintLayoutEffect.setVisibility(View.GONE);
                        this.listOverlay.clear();
                        if (this.recyclerViewOverlay.getAdapter() != null) {
                            this.recyclerViewOverlay.getAdapter().notifyDataSetChanged();
                        }
                        ConstraintSet constraintsetOverlay = new ConstraintSet();
                        constraintsetOverlay.clone(this.constraintLayoutView);
                        constraintsetOverlay.connect(this.relativeLayoutWrapper.getId(), 1, this.constraintLayoutView.getId(), 1, 0);
                        constraintsetOverlay.connect(this.relativeLayoutWrapper.getId(), 4, this.guideline.getId(), 3, 0);
                        constraintsetOverlay.connect(this.relativeLayoutWrapper.getId(), 2, this.constraintLayoutView.getId(), 2, 0);
                        constraintsetOverlay.applyTo(this.constraintLayoutView);
                        setVisibleSave();
                        this.constraintLayoutSaveEffect.setVisibility(View.GONE);
                        this.recyclerViewTools.setVisibility(View.VISIBLE);
                        this.moduleToolsId = Module.NONE;
                        return;
                    case SPLASH:
                    case BLUR:
                    case SQUARESPLASH:
                    case SQUAEBLUR:
                    case MOSAIC:
                    case COLORED:
                    case ROTATE:
                    case CROP:
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
    }

    public void onAdjustSelected(AdjustAdapter.AdjustModel adjustModel) {
        Log.d("XXXXXXXX", "onAdjustSelected " + adjustModel.seekbarIntensity + " " + this.seekbarIntensityAdjust.getMax());
        this.seekbarIntensityAdjust.setProgress((int) (adjustModel.seekbarIntensity * ((float) this.seekbarIntensityAdjust.getMax())));
    }

    public void addSticker(Bitmap bitmap) {
        this.quShotView.addSticker(new DrawableSticker(new BitmapDrawable(getResources(), bitmap)));
        this.linear_layout_wrapper_sticker_list.setVisibility(View.GONE);
        this.linear_layout_wrapper_sticker_men_list.setVisibility(View.GONE);
        this.linear_layout_wrapper_sticker_women_list.setVisibility(View.GONE);
        this.imageViewAddSticker.setVisibility(View.VISIBLE);
        this.imageViewAddStickerMen.setVisibility(View.VISIBLE);
        this.imageViewAddStickerWomen.setVisibility(View.VISIBLE);
    }

    public void finishCrop(Bitmap bitmap) {
        this.quShotView.setImageSource(bitmap);
        this.moduleToolsId = Module.NONE;
        reloadingLayout();
    }

    public void onColorChanged(String string) {
        this.quShotEditor.setBrushColor(Color.parseColor(string));
    }

    public void ratioSavedBitmap(Bitmap bitmap) {
        this.quShotView.setImageSource(bitmap);
        this.moduleToolsId = Module.NONE;
        reloadingLayout();
    }

    public void onSaveSplash(Bitmap bitmap) {
        this.quShotView.setImageSource(bitmap);
        this.moduleToolsId = Module.NONE;
    }

    public void onSaveMosaic(Bitmap bitmap) {
        this.quShotView.setImageSource(bitmap);
        this.moduleToolsId = Module.NONE;
    }

    class allFilters extends AsyncTask<Void, Void, Void> {
        allFilters() {}

        public void onPreExecute() {
            QueShotEditorActivity.this.showLoading(true);
        }

        public Void doInBackground(Void... voids) {
            QueShotEditorActivity.this.listAllFilter.clear();
            QueShotEditorActivity.this.listAllFilter.addAll(FilterCodeAsset.getListBitmapFilterAll(ThumbnailUtils.extractThumbnail(QueShotEditorActivity.this.quShotView.getCurrentBitmap(), 100, 100)));
            Log.d("XXXXXXXX", "allFilters " + QueShotEditorActivity.this.listAllFilter.size());
            return null;
        }

        public void onPostExecute(Void voids) {
            QueShotEditorActivity.this.recyclerViewAllFilter.setAdapter(new FilterAdapter(QueShotEditorActivity.this.listAllFilter, QueShotEditorActivity.this, QueShotEditorActivity.this.getApplicationContext(), Arrays.asList(FilterCodeAsset.ALL_FILTERS)));
            QueShotEditorActivity.this.imageViewCompareFilter.setVisibility(View.VISIBLE);
            QueShotEditorActivity.this.constraintLayoutFilter.setVisibility(View.VISIBLE);
            QueShotEditorActivity.this.constraintLayoutSaveFilter.setVisibility(View.VISIBLE);
            QueShotEditorActivity.this.recyclerViewTools.setVisibility(View.GONE);
            QueShotEditorActivity.this.quShotView.setHandlingSticker(null);
            QueShotEditorActivity.this.quShotView.getStickers().clear();
            QueShotEditorActivity.this.seekbarIntensityFilter.setProgress(100);
            QueShotEditorActivity.this.showLoading(false);
            QueShotEditorActivity.this.quShotEditor.setFilterEffect("");
            QueShotEditorActivity.this.quShotEditor.clearBrushAllViews();
            QueShotEditorActivity.this.quShotEditor.setBrushDrawingMode(false);

            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(QueShotEditorActivity.this.constraintLayoutView);
            constraintSet.connect(QueShotEditorActivity.this.relativeLayoutWrapper.getId(), 1, QueShotEditorActivity.this.constraintLayoutView.getId(), 1, 0);
            constraintSet.connect(QueShotEditorActivity.this.relativeLayoutWrapper.getId(), 4, QueShotEditorActivity.this.guidelinePaint.getId(), 3, 0);
            constraintSet.connect(QueShotEditorActivity.this.relativeLayoutWrapper.getId(), 2, QueShotEditorActivity.this.constraintLayoutView.getId(), 2, 0);
            constraintSet.applyTo(QueShotEditorActivity.this.constraintLayoutView);
        }
    }

    class bwFilters extends AsyncTask<Void, Void, Void> {
        bwFilters() {}

        public void onPreExecute() {
            QueShotEditorActivity.this.showLoading(true);
        }

        public Void doInBackground(Void... voids) {
            QueShotEditorActivity.this.listBwFilter.clear();
            QueShotEditorActivity.this.listBwFilter.addAll(FilterCodeAsset.getListBitmapFilterBW(ThumbnailUtils.extractThumbnail(QueShotEditorActivity.this.quShotView.getCurrentBitmap(), 100, 100)));
            Log.d("XXXXXXXX", "bwFilters " + QueShotEditorActivity.this.listBwFilter.size());
            return null;
        }

        public void onPostExecute(Void voids) {
            QueShotEditorActivity.this.recyclerViewBwFilter.setAdapter(new FilterAdapter(QueShotEditorActivity.this.listBwFilter, QueShotEditorActivity.this, QueShotEditorActivity.this.getApplicationContext(), Arrays.asList(FilterCodeAsset.BW_FILTERS)));
            QueShotEditorActivity.this.imageViewCompareFilter.setVisibility(View.VISIBLE);
            QueShotEditorActivity.this.seekbarIntensityFilter.setProgress(100);
            QueShotEditorActivity.this.showLoading(false);
        }
    }

    class vintageFilters extends AsyncTask<Void, Void, Void> {
        vintageFilters() {}

        public void onPreExecute() {
            QueShotEditorActivity.this.showLoading(true);
        }

        public Void doInBackground(Void... voids) {
            QueShotEditorActivity.this.listVintageFilter.clear();
            QueShotEditorActivity.this.listVintageFilter.addAll(FilterCodeAsset.getListBitmapFilterVintage(ThumbnailUtils.extractThumbnail(QueShotEditorActivity.this.quShotView.getCurrentBitmap(), 100, 100)));
            Log.d("XXXXXXXX", "vintageFilters " + QueShotEditorActivity.this.listVintageFilter.size());
            return null;
        }

        public void onPostExecute(Void voids) {
            QueShotEditorActivity.this.recyclerViewVintageFilter.setAdapter(new FilterAdapter(QueShotEditorActivity.this.listVintageFilter, QueShotEditorActivity.this, QueShotEditorActivity.this.getApplicationContext(), Arrays.asList(FilterCodeAsset.VINTAGE_FILTERS)));
            QueShotEditorActivity.this.imageViewCompareFilter.setVisibility(View.VISIBLE);
            QueShotEditorActivity.this.seekbarIntensityFilter.setProgress(100);
            QueShotEditorActivity.this.showLoading(false);
        }
    }

    class smoothFilters extends AsyncTask<Void, Void, Void> {
        smoothFilters() {}

        public void onPreExecute() {
            QueShotEditorActivity.this.showLoading(true);
        }

        public Void doInBackground(Void... voids) {
            QueShotEditorActivity.this.listSmoothFilter.clear();
            QueShotEditorActivity.this.listSmoothFilter.addAll(FilterCodeAsset.getListBitmapFilterSmooth(ThumbnailUtils.extractThumbnail(QueShotEditorActivity.this.quShotView.getCurrentBitmap(), 100, 100)));
            Log.d("XXXXXXXX", "smoothFilters " + QueShotEditorActivity.this.listSmoothFilter.size());
            return null;
        }

        public void onPostExecute(Void voids) {
            QueShotEditorActivity.this.recyclerViewSmoothFilter.setAdapter(new FilterAdapter(QueShotEditorActivity.this.listSmoothFilter, QueShotEditorActivity.this, QueShotEditorActivity.this.getApplicationContext(), Arrays.asList(FilterCodeAsset.SMOOTH_FILTERS)));
            QueShotEditorActivity.this.imageViewCompareFilter.setVisibility(View.VISIBLE);
            QueShotEditorActivity.this.seekbarIntensityFilter.setProgress(100);
            QueShotEditorActivity.this.showLoading(false);
        }
    }

    class coldFilters extends AsyncTask<Void, Void, Void> {
        coldFilters() {}

        public void onPreExecute() {
            QueShotEditorActivity.this.showLoading(true);
        }

        public Void doInBackground(Void... voids) {
            QueShotEditorActivity.this.listColdFilter.clear();
            QueShotEditorActivity.this.listColdFilter.addAll(FilterCodeAsset.getListBitmapFilterCold(ThumbnailUtils.extractThumbnail(QueShotEditorActivity.this.quShotView.getCurrentBitmap(), 100, 100)));
            Log.d("XXXXXXXX", "coldFilters " + QueShotEditorActivity.this.listColdFilter.size());
            return null;
        }

        public void onPostExecute(Void voids) {
            QueShotEditorActivity.this.recyclerViewColdFilter.setAdapter(new FilterAdapter(QueShotEditorActivity.this.listColdFilter, QueShotEditorActivity.this, QueShotEditorActivity.this.getApplicationContext(), Arrays.asList(FilterCodeAsset.COLD_FILTERS)));
            QueShotEditorActivity.this.imageViewCompareFilter.setVisibility(View.VISIBLE);
            QueShotEditorActivity.this.seekbarIntensityFilter.setProgress(100);
            QueShotEditorActivity.this.showLoading(false);
        }
    }

    class warmFilters extends AsyncTask<Void, Void, Void> {
        warmFilters() {}

        public void onPreExecute() {
            QueShotEditorActivity.this.showLoading(true);
        }

        public Void doInBackground(Void... voids) {
            QueShotEditorActivity.this.listWarmFilter.clear();
            QueShotEditorActivity.this.listWarmFilter.addAll(FilterCodeAsset.getListBitmapFilterWarm(ThumbnailUtils.extractThumbnail(QueShotEditorActivity.this.quShotView.getCurrentBitmap(), 100, 100)));
            Log.d("XXXXXXXX", "warmFilters " + QueShotEditorActivity.this.listWarmFilter.size());
            return null;
        }

        public void onPostExecute(Void voids) {
            QueShotEditorActivity.this.recyclerViewWarmFilter.setAdapter(new FilterAdapter(QueShotEditorActivity.this.listWarmFilter, QueShotEditorActivity.this, QueShotEditorActivity.this.getApplicationContext(), Arrays.asList(FilterCodeAsset.WARM_FILTERS)));
            QueShotEditorActivity.this.imageViewCompareFilter.setVisibility(View.VISIBLE);
            QueShotEditorActivity.this.seekbarIntensityFilter.setProgress(100);
            QueShotEditorActivity.this.showLoading(false);
        }
    }

    class legacyFilters extends AsyncTask<Void, Void, Void> {
        legacyFilters() { }
        public void onPreExecute() {
            QueShotEditorActivity.this.showLoading(true);
        }

        public Void doInBackground(Void... voids) {
            QueShotEditorActivity.this.listLegacyFilter.clear();
            QueShotEditorActivity.this.listLegacyFilter.addAll(FilterCodeAsset.getListBitmapFilterLegacy(ThumbnailUtils.extractThumbnail(QueShotEditorActivity.this.quShotView.getCurrentBitmap(), 100, 100)));
            Log.d("XXXXXXXX", "legacyFilters " + QueShotEditorActivity.this.listLegacyFilter.size());
            return null;
        }

        public void onPostExecute(Void voids) {
            QueShotEditorActivity.this.recyclerViewLegacyFilter.setAdapter(new FilterAdapter(QueShotEditorActivity.this.listLegacyFilter, QueShotEditorActivity.this, QueShotEditorActivity.this.getApplicationContext(), Arrays.asList(FilterCodeAsset.LEGACY_FILTERS)));
            QueShotEditorActivity.this.imageViewCompareFilter.setVisibility(View.VISIBLE);
            QueShotEditorActivity.this.seekbarIntensityFilter.setProgress(100);
            QueShotEditorActivity.this.showLoading(false);
        }
    }

    class openBlurFragment extends AsyncTask<Void, Bitmap, Bitmap> {
        openBlurFragment() {}

        public void onPreExecute() {
            QueShotEditorActivity.this.showLoading(true);
        }

        public Bitmap doInBackground(Void... voids) {
            return FilterUtils.getBlurImageFromBitmap(QueShotEditorActivity.this.quShotView.getCurrentBitmap(), 5.0f);
        }

        public void onPostExecute(Bitmap bitmap) {
            QueShotEditorActivity.this.showLoading(false);
            RatioFragment.show(QueShotEditorActivity.this, QueShotEditorActivity.this, QueShotEditorActivity.this.quShotView.getCurrentBitmap(), bitmap);
        }
    }

    class openFrameFragment extends AsyncTask<Void, Bitmap, Bitmap> {
        openFrameFragment() {}

        public void onPreExecute() {
            QueShotEditorActivity.this.showLoading(true);
        }

        public Bitmap doInBackground(Void... voids) {
            return FilterUtils.getBlurImageFromBitmap(QueShotEditorActivity.this.quShotView.getCurrentBitmap(), 5.0f);
        }

        public void onPostExecute(Bitmap bitmap) {
            QueShotEditorActivity.this.showLoading(false);
            FrameFragment.show(QueShotEditorActivity.this, QueShotEditorActivity.this, QueShotEditorActivity.this.quShotView.getCurrentBitmap(), bitmap);
        }
    }

    class openShapeFragment extends AsyncTask<Void, List<Bitmap>, List<Bitmap>> {
        openShapeFragment() {}

        public void onPreExecute() {
            QueShotEditorActivity.this.showLoading(true);
        }

        public List<Bitmap> doInBackground(Void... voids) {
            List<Bitmap> arrayList = new ArrayList<>();
            arrayList.add(FilterUtils.cloneBitmap(QueShotEditorActivity.this.quShotView.getCurrentBitmap()));
            arrayList.add(FilterUtils.getBlurImageFromBitmap(QueShotEditorActivity.this.quShotView.getCurrentBitmap(), 8.0f));
            return arrayList;
        }

        public void onPostExecute(List<Bitmap> list) {
            QueShotEditorActivity.this.showLoading(false);
            MosaicFragment.show(QueShotEditorActivity.this, list.get(0), list.get(1), QueShotEditorActivity.this);
        }
    }

    class openColoredFragment extends AsyncTask<Void, List<Bitmap>, List<Bitmap>> {
        openColoredFragment() { }

        public void onPreExecute() {
            QueShotEditorActivity.this.showLoading(true);
        }

        public List<Bitmap> doInBackground(Void... voids) {
            List<Bitmap> arrayList = new ArrayList<>();
            arrayList.add(FilterUtils.cloneBitmap(QueShotEditorActivity.this.quShotView.getCurrentBitmap()));
            arrayList.add(FilterUtils.getBlurImageFromBitmap(QueShotEditorActivity.this.quShotView.getCurrentBitmap(), 8.0f));
            return arrayList;
        }

        public void onPostExecute(List<Bitmap> list) {
            QueShotEditorActivity.this.showLoading(false);
            ColoredFragment.show(QueShotEditorActivity.this, list.get(0), list.get(1), QueShotEditorActivity.this);
        }
    }

    class effectDodge extends AsyncTask<Void, Void, Void> {
        effectDodge() { }

        public void onPreExecute() {
            QueShotEditorActivity.this.showLoading(true);
        }

        public Void doInBackground(Void... voids) {
            QueShotEditorActivity.this.listDodge.clear();
            QueShotEditorActivity.this.listDodge.addAll(EffectCodeAsset.getListBitmapDodgeEffect(ThumbnailUtils.extractThumbnail(QueShotEditorActivity.this.quShotView.getCurrentBitmap(), 100, 100)));
            return null;
        }

        public void onPostExecute(Void voids) {
            QueShotEditorActivity.this.recyclerViewDodge.setAdapter(new HardmixAdapter(QueShotEditorActivity.this.listDodge, QueShotEditorActivity.this, QueShotEditorActivity.this.getApplicationContext(), Arrays.asList(EffectCodeAsset.DODGE_EFFECTS)));
            QueShotEditorActivity.this.imageViewCompareEffect.setVisibility(View.VISIBLE);
            QueShotEditorActivity.this.seekbarIntensityEffect.setProgress(100);
        }
    }

    class effectDivide extends AsyncTask<Void, Void, Void> {
        effectDivide() { }

        public void onPreExecute() {
            QueShotEditorActivity.this.showLoading(true);
        }

        public Void doInBackground(Void... voids) {
            QueShotEditorActivity.this.listDivide.clear();
            QueShotEditorActivity.this.listDivide.addAll(EffectCodeAsset.getListBitmapDivideEffect(ThumbnailUtils.extractThumbnail(QueShotEditorActivity.this.quShotView.getCurrentBitmap(), 100, 100)));
            return null;
        }

        public void onPostExecute(Void voids) {
            QueShotEditorActivity.this.recyclerViewDivide.setAdapter(new HardmixAdapter(QueShotEditorActivity.this.listDivide, QueShotEditorActivity.this, QueShotEditorActivity.this.getApplicationContext(), Arrays.asList(EffectCodeAsset.DIVIDE_EFFECTS)));
            QueShotEditorActivity.this.imageViewCompareEffect.setVisibility(View.VISIBLE);
            QueShotEditorActivity.this.seekbarIntensityEffect.setProgress(100);
        }
    }

    class effectHardmix extends AsyncTask<Void, Void, Void> {
        effectHardmix() { }

        public void onPreExecute() {
            QueShotEditorActivity.this.showLoading(true);
        }

        public Void doInBackground(Void... voids) {
            QueShotEditorActivity.this.listHardmix.clear();
            QueShotEditorActivity.this.listHardmix.addAll(EffectCodeAsset.getListBitmapHardmixEffect(ThumbnailUtils.extractThumbnail(QueShotEditorActivity.this.quShotView.getCurrentBitmap(), 100, 100)));
            return null;
        }

        public void onPostExecute(Void voids) {
            QueShotEditorActivity.this.recyclerViewHardmix.setAdapter(new HardmixAdapter(QueShotEditorActivity.this.listHardmix, QueShotEditorActivity.this, QueShotEditorActivity.this.getApplicationContext(), Arrays.asList(EffectCodeAsset.HARDMIX_EFFECTS)));
            QueShotEditorActivity.this.imageViewCompareEffect.setVisibility(View.VISIBLE);
            QueShotEditorActivity.this.seekbarIntensityEffect.setProgress(100);
        }
    }

    class effectOvarlay extends AsyncTask<Void, Void, Void> {
        effectOvarlay() { }

        public void onPreExecute() {
            QueShotEditorActivity.this.showLoading(true);
        }

        public Void doInBackground(Void... voids) {
            QueShotEditorActivity.this.listOverlay.clear();
            QueShotEditorActivity.this.listOverlay.addAll(EffectCodeAsset.getListBitmapOverlayEffect(ThumbnailUtils.extractThumbnail(QueShotEditorActivity.this.quShotView.getCurrentBitmap(), 100, 100)));
            return null;
        }

        public void onPostExecute(Void voids) {
            QueShotEditorActivity.this.recyclerViewOverlay.setAdapter(new HardmixAdapter(QueShotEditorActivity.this.listOverlay, QueShotEditorActivity.this, QueShotEditorActivity.this.getApplicationContext(), Arrays.asList(EffectCodeAsset.OVERLAY_EFFECTS)));
            QueShotEditorActivity.this.constraintLayoutEffect.setVisibility(View.VISIBLE);
            QueShotEditorActivity.this.constraintLayoutSaveEffect.setVisibility(View.VISIBLE);
            QueShotEditorActivity.this.recyclerViewTools.setVisibility(View.GONE);
            QueShotEditorActivity.this.imageViewCompareEffect.setVisibility(View.VISIBLE);
            QueShotEditorActivity.this.seekbarIntensityEffect.setProgress(100);
            QueShotEditorActivity.this.showLoading(false);

            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(QueShotEditorActivity.this.constraintLayoutView);
            constraintSet.connect(QueShotEditorActivity.this.relativeLayoutWrapper.getId(), 1, QueShotEditorActivity.this.constraintLayoutView.getId(), 1, 0);
            constraintSet.connect(QueShotEditorActivity.this.relativeLayoutWrapper.getId(), 4, QueShotEditorActivity.this.guidelinePaint.getId(), 3, 0);
            constraintSet.connect(QueShotEditorActivity.this.relativeLayoutWrapper.getId(), 2, QueShotEditorActivity.this.constraintLayoutView.getId(), 2, 0);
            constraintSet.applyTo(QueShotEditorActivity.this.constraintLayoutView);
        }
    }

    class effectBurn extends AsyncTask<Void, Void, Void> {
        effectBurn() { }

        public void onPreExecute() {
            QueShotEditorActivity.this.showLoading(true);
        }

        public Void doInBackground(Void... voids) {
            QueShotEditorActivity.this.listBurn.clear();
            QueShotEditorActivity.this.listBurn.addAll(EffectCodeAsset.getListBitmapColorEffect(ThumbnailUtils.extractThumbnail(QueShotEditorActivity.this.quShotView.getCurrentBitmap(), 100, 100)));
            return null;
        }

        public void onPostExecute(Void voids) {
            QueShotEditorActivity.this.recyclerViewBurn.setAdapter(new HardmixAdapter(QueShotEditorActivity.this.listBurn, QueShotEditorActivity.this, QueShotEditorActivity.this.getApplicationContext(), Arrays.asList(EffectCodeAsset.COLOR_EFFECTS)));
            QueShotEditorActivity.this.imageViewCompareEffect.setVisibility(View.VISIBLE);
            QueShotEditorActivity.this.seekbarIntensityEffect.setProgress(100);
        }
    }

    class openSplashBrushFragment extends AsyncTask<Void, List<Bitmap>, List<Bitmap>> {
        boolean isSplashBrush;
        public openSplashBrushFragment(boolean z) {
            this.isSplashBrush = z;
        }

        public void onPreExecute() {
            QueShotEditorActivity.this.showLoading(true);
        }

        public List<Bitmap> doInBackground(Void... voids) {
            Bitmap currentBitmap = QueShotEditorActivity.this.quShotView.getCurrentBitmap();
            List<Bitmap> arrayList = new ArrayList<>();
            arrayList.add(currentBitmap);
            if (this.isSplashBrush) {
                arrayList.add(FilterUtils.getBlackAndWhiteImageFromBitmap(currentBitmap));
            } else {
                arrayList.add(FilterUtils.getBlurImageFromBitmap(currentBitmap, 3.0f));
            }
            return arrayList;
        }

        public void onPostExecute(List<Bitmap> list) {
            if (this.isSplashBrush) {
                SplashFragment.show(QueShotEditorActivity.this, list.get(0), null, list.get(1), QueShotEditorActivity.this, true);
            } else {
                SplashFragment.show(QueShotEditorActivity.this, list.get(0), list.get(1), null, QueShotEditorActivity.this, false);
            }
            QueShotEditorActivity.this.showLoading(false);
        }
    }

    class openSplashSquareFragment extends AsyncTask<Void, List<Bitmap>, List<Bitmap>> {
        boolean isSplashSquared;
        public openSplashSquareFragment(boolean z) {
            this.isSplashSquared = z;
        }

        public void onPreExecute() {
            QueShotEditorActivity.this.showLoading(true);
        }

        public List<Bitmap> doInBackground(Void... voids) {
            Bitmap currentBitmap = QueShotEditorActivity.this.quShotView.getCurrentBitmap();
            List<Bitmap> arrayList = new ArrayList<>();
            arrayList.add(currentBitmap);
            if (this.isSplashSquared) {
                arrayList.add(FilterUtils.getBlackAndWhiteImageFromBitmap(currentBitmap));
            } else {
                arrayList.add(FilterUtils.getBlurImageFromBitmap(currentBitmap, 3.0f));
            }
            return arrayList;
        }

        public void onPostExecute(List<Bitmap> list) {
            if (this.isSplashSquared) {
                SplashBlurSquareFragment.show(QueShotEditorActivity.this, list.get(0), null, list.get(1), QueShotEditorActivity.this, true);
            } else {
                SplashBlurSquareFragment.show(QueShotEditorActivity.this, list.get(0), list.get(1), null, QueShotEditorActivity.this, false);
            }
            QueShotEditorActivity.this.showLoading(false);
        }
    }

    class SaveFilter extends AsyncTask<Void, Void, Bitmap> {
        SaveFilter() {}

        public void onPreExecute() {
            QueShotEditorActivity.this.showLoading(true);
        }

        public Bitmap doInBackground(Void... voids) {
            final Bitmap[] bitmaps = {null};
            QueShotEditorActivity.this.quShotView.saveGLSurfaceViewAsBitmap(bitmap -> bitmaps[0] = bitmap);
            while (bitmaps[0] == null) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return bitmaps[0];
        }

        public void onPostExecute(Bitmap bitmap) {
            QueShotEditorActivity.this.quShotView.setImageSource(bitmap);
            QueShotEditorActivity.this.quShotView.setFilterEffect("");
            QueShotEditorActivity.this.showLoading(false);
        }
    }

    class SaveSticker extends AsyncTask<Void, Void, Bitmap> {
        SaveSticker() {}

        public void onPreExecute() {
            QueShotEditorActivity.this.quShotView.getGLSurfaceView().setAlpha(0.0f);
            QueShotEditorActivity.this.showLoading(true);
        }

        public Bitmap doInBackground(Void... voids) {
            final Bitmap[] bitmaps = {null};
            while (bitmaps[0] == null) {
                try {
                    QueShotEditorActivity.this.quShotEditor.saveStickerAsBitmap(bitmap -> bitmaps[0] = bitmap);
                    while (bitmaps[0] == null) {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                }
            }
            return bitmaps[0];
        }

        public void onPostExecute(Bitmap bitmap) {
            QueShotEditorActivity.this.quShotView.setImageSource(bitmap);
            QueShotEditorActivity.this.quShotView.getStickers().clear();
            QueShotEditorActivity.this.quShotView.getGLSurfaceView().setAlpha(1.0f);
            QueShotEditorActivity.this.showLoading(false);
            QueShotEditorActivity.this.reloadingLayout();
        }
    }

    public void onActivityResult(int i, int i2, @Nullable Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 123) {
            if (i2 == -1) {
                try {
                    InputStream inputStream = getContentResolver().openInputStream(intent.getData());
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    float width = (float) bitmap.getWidth();
                    float height = (float) bitmap.getHeight();
                    float max = Math.max(width / 1280.0f, height / 1280.0f);
                    if (max > 1.0f) {
                        bitmap = Bitmap.createScaledBitmap(bitmap, (int) (width / max), (int) (height / max), false);
                    }
                    if (SystemUtil.rotateBitmap(bitmap, new ExifInterface(inputStream).getAttributeInt(ExifInterface.TAG_ORIENTATION, 1)) != bitmap) {
                        bitmap.recycle();
                        bitmap = null;
                    }
                    this.quShotView.setImageSource(bitmap);
                    reloadingLayout();
                } catch (Exception e) {
                    e.printStackTrace();
                    MsgUtil.toastMsg(this, "Error: Can not open image");
                }
            } else {
                finish();
            }
        }
    }

    class loadBitmapUri extends AsyncTask<String, Bitmap, Bitmap> {
        loadBitmapUri() {}

        public void onPreExecute() {
            QueShotEditorActivity.this.showLoading(true);
        }

        public Bitmap doInBackground(String... string) {
            try {
                Uri fromFile = Uri.fromFile(new File(string[0]));
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(QueShotEditorActivity.this.getContentResolver(), fromFile);
                float width = (float) bitmap.getWidth();
                float height = (float) bitmap.getHeight();
                float max = Math.max(width / 1280.0f, height / 1280.0f);
                if (max > 1.0f) {
                    bitmap = Bitmap.createScaledBitmap(bitmap, (int) (width / max), (int) (height / max), false);
                }
                Bitmap bitmap1 = SystemUtil.rotateBitmap(bitmap, new ExifInterface(QueShotEditorActivity.this.getContentResolver().openInputStream(fromFile)).getAttributeInt(ExifInterface.TAG_ORIENTATION, 1));
                if (bitmap1 != bitmap) {
                    bitmap.recycle();
                }
                return bitmap1;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }


        public void onPostExecute(Bitmap bitmap) {
            QueShotEditorActivity.this.quShotView.setImageSource(bitmap);
            QueShotEditorActivity.this.reloadingLayout();
        }
    }

    public void reloadingLayout() {
        this.quShotView.postDelayed(() -> {
            try {
                Display display = QueShotEditorActivity.this.getWindowManager().getDefaultDisplay();
                Point point = new Point();
                display.getSize(point);
                int i = point.x;
                int height = QueShotEditorActivity.this.relativeLayoutWrapper.getHeight();
                int i2 = QueShotEditorActivity.this.quShotView.getGLSurfaceView().getRenderViewport().width;
                float f = (float) QueShotEditorActivity.this.quShotView.getGLSurfaceView().getRenderViewport().height;
                float f2 = (float) i2;
                if (((int) ((((float) i) * f) / f2)) <= height) {
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(-1, -2);
                    params.addRule(13);
                    QueShotEditorActivity.this.quShotView.setLayoutParams(params);
                    QueShotEditorActivity.this.quShotView.setVisibility(View.VISIBLE);
                } else {
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int) ((((float) height) * f2) / f), -1);
                    params.addRule(13);
                    QueShotEditorActivity.this.quShotView.setLayoutParams(params);
                    QueShotEditorActivity.this.quShotView.setVisibility(View.VISIBLE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            QueShotEditorActivity.this.showLoading(false);
        }, 300);
    }

    class SaveEditingBitmap extends AsyncTask<Void, String, String> {
        SaveEditingBitmap() {}

        public void onPreExecute() {
            QueShotEditorActivity.this.showLoading(true);
        }

        public String doInBackground(Void... voids) {
            try {
                return SaveFileUtils.saveBitmapFileEditor(QueShotEditorActivity.this, QueShotEditorActivity.this.quShotView.getCurrentBitmap(), new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(new Date())).getAbsolutePath();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        public void onPostExecute(String string) {
            QueShotEditorActivity.this.showLoading(false);
            if (string == null) {
                Toast.makeText(QueShotEditorActivity.this.getApplicationContext(), "Oop! Something went wrong", Toast.LENGTH_LONG).show();
                return;
            }
            Intent i = new Intent(QueShotEditorActivity.this, PhotoShareActivity.class);
            i.putExtra("path", string);
            QueShotEditorActivity.this.startActivity(i);
        }

    }

    public void showLoading(boolean z) {
        if (z) {
            getWindow().setFlags(16, 16);
            this.relativeLayoutLoading.setVisibility(View.VISIBLE);
            return;
        }
        getWindow().clearFlags(16);
        this.relativeLayoutLoading.setVisibility(View.GONE);
    }
}
