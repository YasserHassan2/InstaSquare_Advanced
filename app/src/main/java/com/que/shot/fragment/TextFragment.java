package com.que.shot.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.BitmapShader;
import android.graphics.Color;
import android.graphics.Shader;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.que.shot.R;
import com.que.shot.adapters.FontAdapter;
import com.que.shot.queshot.QueShotText;
import com.que.shot.queshot.QueShotEditText;
import com.que.shot.picker.QuShotCarouselPicker;
import com.que.shot.assets.FontAsset;
import com.que.shot.preference.Preference;
import com.que.shot.utils.SystemUtil;

import java.util.ArrayList;
import java.util.List;

public class TextFragment extends DialogFragment implements View.OnClickListener, FontAdapter.ItemClickListener{
    public static final String EXTRA_COLOR_CODE = "extra_color_code";
    public static final String EXTRA_INPUT_TEXT = "extra_input_text";
    public static final String TAG = "TextFragment";
    LinearLayout linear_layout_edit_text_tools;

    public QueShotText quShotText;
    ImageView image_view_background;
    ImageView image_view_text_texture;
    SeekBar seekbar_radius;
    QuShotCarouselPicker background_carousel_picker;
    SeekBar seekbar_height;
    SeekBar seekbar_background_opacity;
    SeekBar seekbar_width;
    ImageView image_view_align;
    ImageView image_view_color;
    ScrollView scroll_view_change_color_layout;
    ImageView image_view_fonts;
    ImageView image_view_adjust;
    ScrollView scroll_view_change_font_adjust;
    ScrollView scroll_view_change_font_layout;
    ImageView image_view_color_down;

    public List<QuShotCarouselPicker.PickerItem> colorItems;
    QuShotCarouselPicker color_carousel_picker;
    private FontAdapter fontAdapter;
    View view_highlight_background_color;
    View view_highlight_text_color;
    View view_highlight_texture;
    LinearLayout linear_layout_preview;
    RecyclerView recycler_view_fonts;
    QueShotEditText add_text_edit_text;
    private InputMethodManager inputMethodManager;
    TextView seekbar_text_opacity;
    ImageView image_view_save_change;
    ImageView image_view_keyboard;
    CheckBox checkbox_background;
    private TextEditor textEditor;
    private List<ImageView> textFunctions;
    SeekBar seekbar_text_size;
    public List<QuShotCarouselPicker.PickerItem> textTextureItems;
    QuShotCarouselPicker texture_carousel_picker;
    SeekBar textTransparent;

    public interface TextEditor {
        void onBackButton();

        void onDone(QueShotText addTextProperties);
    }

    public void onItemClick(View view, int i) {
        FontAsset.setFontByName(requireContext(), this.seekbar_text_opacity, FontAsset.getListFonts().get(i));
        this.quShotText.setQuShotFontName(FontAsset.getListFonts().get(i));
        this.quShotText.setQuShotFontIndex(i);
    }

    public static TextFragment show(@NonNull AppCompatActivity appCompatActivity, @NonNull String str, @ColorInt int i) {
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_INPUT_TEXT, str);
        bundle.putInt(EXTRA_COLOR_CODE, i);
        TextFragment addTextFragment = new TextFragment();
        addTextFragment.setArguments(bundle);
        addTextFragment.show(appCompatActivity.getSupportFragmentManager(), TAG);
        return addTextFragment;
    }

    public static TextFragment show(@NonNull AppCompatActivity appCompatActivity, QueShotText addTextProperties) {
        TextFragment addTextFragment = new TextFragment();
        addTextFragment.setQuShotText(addTextProperties);
        addTextFragment.show(appCompatActivity.getSupportFragmentManager(), TAG);
        return addTextFragment;
    }

    public static TextFragment show(@NonNull AppCompatActivity appCompatActivity) {
        return show(appCompatActivity, "Test", ContextCompat.getColor(appCompatActivity, R.color.itemColorWhite));
    }

    public void setQuShotText(QueShotText addTextProperties2) {
        this.quShotText = addTextProperties2;
    }

    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(-1, -1);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
    }

    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        getDialog().getWindow().requestFeature(1);
        getDialog().getWindow().setFlags(1024, 1024);
        return layoutInflater.inflate(R.layout.fragment_add_text, viewGroup, false);
    }

    public void dismissAndShowSticker() {
        if (this.textEditor != null) {
            this.textEditor.onBackButton();
        }
        dismiss();
    }

    public void onActivityCreated(@Nullable Bundle bundle) {
        super.onActivityCreated(bundle);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        initView(view);
        if (this.quShotText == null) {
            this.quShotText = QueShotText.getDefaultProperties();
        }
        this.add_text_edit_text.setTextFragment(this);
        initAddTextLayout();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(new DisplayMetrics());
        this.inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        setDefaultStyleForEdittext();
        this.inputMethodManager.toggleSoftInput(2, 0);
        highlightFunction(this.image_view_keyboard);
        this.recycler_view_fonts.setLayoutManager(new GridLayoutManager(getContext(), 5));
        this.fontAdapter = new FontAdapter(getContext(), FontAsset.getListFonts());
        this.fontAdapter.setClickListener(this);
        this.recycler_view_fonts.setAdapter(this.fontAdapter);
        this.color_carousel_picker.setAdapter(new QuShotCarouselPicker.CarouselViewAdapter(getContext(), this.colorItems, 0));
        this.color_carousel_picker.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int i) {
            }

            public void onPageSelected(int i) {
            }

            public void onPageScrolled(int i, float f, int i2) {
                if (f > 0.0f) {
                    if (TextFragment.this.image_view_color_down.getVisibility() == View.INVISIBLE) {
                        TextFragment.this.image_view_color_down.setVisibility(View.VISIBLE);
                        TextFragment.this.view_highlight_text_color.setVisibility(View.VISIBLE);
                        TextFragment.this.image_view_text_texture.setVisibility(View.INVISIBLE);
                        TextFragment.this.view_highlight_texture.setVisibility(View.GONE);
                    }
                    TextFragment.this.seekbar_text_opacity.getPaint().setShader(null);
                    int i3 = -1;
                    float f2 = ((float) i) + f;
                    if (Math.round(f2) < TextFragment.this.colorItems.size()) {
                        i3 = Color.parseColor((TextFragment.this.colorItems.get(Math.round(f2))).getColor());
                    }
                    TextFragment.this.seekbar_text_opacity.setTextColor(i3);
                    TextFragment.this.quShotText.setQuShotTextColorIndex(Math.round(f2));
                    TextFragment.this.quShotText.setQuShotTextColor(i3);
                    TextFragment.this.quShotText.setQuShotTextShader(null);
                }
            }
        });
        this.texture_carousel_picker.setAdapter(new QuShotCarouselPicker.CarouselViewAdapter(getContext(), this.textTextureItems, 0));
        this.texture_carousel_picker.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int i) {
            }

            public void onPageSelected(int i) {
            }

            public void onPageScrolled(int i, float f, int i2) {
                if (f > 0.0f) {
                    if (TextFragment.this.image_view_text_texture.getVisibility() == View.INVISIBLE) {
                        TextFragment.this.image_view_text_texture.setVisibility(View.VISIBLE);
                        TextFragment.this.view_highlight_texture.setVisibility(View.VISIBLE);
                        TextFragment.this.image_view_color_down.setVisibility(View.INVISIBLE);
                        TextFragment.this.view_highlight_text_color.setVisibility(View.GONE);
                    }
                    float f2 = ((float) i) + f;
                    BitmapShader bitmapShader = new BitmapShader((TextFragment.this.textTextureItems.get(Math.round(f2))).getBitmap(), Shader.TileMode.MIRROR, Shader.TileMode.MIRROR);
                    TextFragment.this.seekbar_text_opacity.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                    TextFragment.this.seekbar_text_opacity.getPaint().setShader(bitmapShader);
                    TextFragment.this.quShotText.setQuShotTextShader(bitmapShader);
                    TextFragment.this.quShotText.setQuShotTextShaderIndex(Math.round(f2));
                }
            }
        });
        this.textTransparent.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                int i2 = 255 - i;
                TextFragment.this.quShotText.setQuShotTextAlpha(i2);
                TextFragment.this.seekbar_text_opacity.setTextColor(Color.argb(i2, Color.red(TextFragment.this.quShotText.getQuShotTextColor()), Color.green(TextFragment.this.quShotText.getQuShotTextColor()), Color.blue(TextFragment.this.quShotText.getQuShotTextColor())));
            }
        });
        this.add_text_edit_text.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                TextFragment.this.seekbar_text_opacity.setText(charSequence.toString());
                TextFragment.this.quShotText.setQuShotTexts(charSequence.toString());
            }
        });
        this.checkbox_background.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                if (!z) {
                    TextFragment.this.quShotText.setQuShotShowBackground(false);
                    TextFragment.this.seekbar_text_opacity.setBackgroundResource(0);
                    TextFragment.this.seekbar_text_opacity.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
                } else if (TextFragment.this.checkbox_background.isPressed() || TextFragment.this.quShotText.isQuShotShowBackground()) {
                    TextFragment.this.quShotText.setQuShotShowBackground(true);
                    TextFragment.this.initPreviewText();
                } else {
                    TextFragment.this.checkbox_background.setChecked(false);
                    TextFragment.this.quShotText.setQuShotShowBackground(false);
                    TextFragment.this.initPreviewText();
                }
            }
        });
        this.background_carousel_picker.setAdapter(new QuShotCarouselPicker.CarouselViewAdapter(getContext(), this.colorItems, 0));
        this.background_carousel_picker.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int i) {
            }

            public void onPageSelected(int i) {
            }

            public void onPageScrolled(int i, float f, int i2) {
                if (f > 0.0f) {
                    int i3 = 0;
                    if (TextFragment.this.image_view_background.getVisibility() == View.INVISIBLE) {
                        TextFragment.this.image_view_background.setVisibility(View.VISIBLE);
                        TextFragment.this.view_highlight_background_color.setVisibility(View.VISIBLE);
                    }
                    TextFragment.this.quShotText.setQuShotShowBackground(true);
                    if (!TextFragment.this.checkbox_background.isChecked()) {
                        TextFragment.this.checkbox_background.setChecked(true);
                    }
                    float f2 = ((float) i) + f;
                    int round = Math.round(f2);
                    if (round >= TextFragment.this.colorItems.size()) {
                        i3 = TextFragment.this.colorItems.size() - 1;
                    } else if (round >= 0) {
                        i3 = round;
                    }
                    int parseColor = Color.parseColor((TextFragment.this.colorItems.get(i3)).getColor());
                    int red = Color.red(parseColor);
                    int green = Color.green(parseColor);
                    int blue = Color.blue(parseColor);
                    GradientDrawable gradientDrawable = new GradientDrawable();
                    gradientDrawable.setColor(Color.argb(TextFragment.this.quShotText.getQuShotBackgroundAlpha(), red, green, blue));
                    gradientDrawable.setCornerRadius((float) SystemUtil.dpToPx(TextFragment.this.requireContext(), TextFragment.this.quShotText.getQuShotBackgroundBorder()));
                    TextFragment.this.seekbar_text_opacity.setBackground(gradientDrawable);
                    TextFragment.this.quShotText.setQuShotBackgroundColor(parseColor);
                    TextFragment.this.quShotText.setQuShotBackgroundColorIndex(Math.round(f2));
                    TextFragment.this.seekbar_radius.setEnabled(true);
                }
            }
        });
        this.seekbar_width.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                TextFragment.this.seekbar_text_opacity.setPadding(SystemUtil.dpToPx(TextFragment.this.requireContext(), i), TextFragment.this.seekbar_text_opacity.getPaddingTop(), SystemUtil.dpToPx(TextFragment.this.getContext(), i), TextFragment.this.seekbar_text_opacity.getPaddingBottom());
                TextFragment.this.quShotText.setPaddingWidth(i);
            }
        });
        this.seekbar_height.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                TextFragment.this.seekbar_text_opacity.setPadding(TextFragment.this.seekbar_text_opacity.getPaddingLeft(), SystemUtil.dpToPx(TextFragment.this.requireContext(), i), TextFragment.this.seekbar_text_opacity.getPaddingRight(), SystemUtil.dpToPx(TextFragment.this.getContext(), i));
                TextFragment.this.quShotText.setPaddingHeight(i);
            }
        });

        this.seekbar_background_opacity.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                TextFragment.this.quShotText.setQuShotBackgroundAlpha(255 - i);
                if (TextFragment.this.quShotText.isQuShotShowBackground()) {
                    int red = Color.red(TextFragment.this.quShotText.getQuShotBackgroundColor());
                    int green = Color.green(TextFragment.this.quShotText.getQuShotBackgroundColor());
                    int blue = Color.blue(TextFragment.this.quShotText.getQuShotBackgroundColor());
                    GradientDrawable gradientDrawable = new GradientDrawable();
                    gradientDrawable.setColor(Color.argb(TextFragment.this.quShotText.getQuShotBackgroundAlpha(), red, green, blue));
                    gradientDrawable.setCornerRadius((float) SystemUtil.dpToPx(TextFragment.this.requireContext(), TextFragment.this.quShotText.getQuShotBackgroundBorder()));
                    TextFragment.this.seekbar_text_opacity.setBackground(gradientDrawable);
                }
            }
        });
        this.seekbar_text_size.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                int i2 = 15;
                if (i >= 15) {
                    i2 = i;
                }
                TextFragment.this.seekbar_text_opacity.setTextSize((float) i2);
                TextFragment.this.quShotText.setQuShotTextSize(i2);
            }
        });
        this.seekbar_radius.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                TextFragment.this.quShotText.setQuShotBackgroundBorder(i);
                if (TextFragment.this.quShotText.isQuShotShowBackground()) {
                    GradientDrawable gradientDrawable = new GradientDrawable();
                    gradientDrawable.setCornerRadius((float) SystemUtil.dpToPx(TextFragment.this.requireContext(), i));
                    gradientDrawable.setColor(Color.argb(TextFragment.this.quShotText.getQuShotBackgroundAlpha(), Color.red(TextFragment.this.quShotText.getQuShotBackgroundColor()), Color.green(TextFragment.this.quShotText.getQuShotBackgroundColor()), Color.blue(TextFragment.this.quShotText.getQuShotBackgroundColor())));
                    TextFragment.this.seekbar_text_opacity.setBackground(gradientDrawable);
                }
            }
        });
        if (Preference.getKeyboard(requireContext()) > 0) {
            updateAddTextBottomToolbarHeight(Preference.getKeyboard(getContext()));
        }
        initPreviewText();
    }

    public void initView(View view) {
        this.add_text_edit_text = view.findViewById(R.id.add_text_edit_text);
        this.image_view_keyboard = view.findViewById(R.id.image_view_keyboard);
        this.image_view_fonts = view.findViewById(R.id.image_view_fonts);
        this.image_view_color = view.findViewById(R.id.image_view_color);
        this.image_view_align = view.findViewById(R.id.image_view_align);
        this.image_view_adjust = view.findViewById(R.id.image_view_adjust);
        this.image_view_save_change = view.findViewById(R.id.image_view_save_change);
        this.scroll_view_change_font_layout = view.findViewById(R.id.scroll_view_change_font_layout);
        this.scroll_view_change_font_adjust = view.findViewById(R.id.scroll_view_change_color_adjust);
        this.linear_layout_edit_text_tools = view.findViewById(R.id.linear_layout_edit_text_tools);
        this.recycler_view_fonts = view.findViewById(R.id.recycler_view_fonts);
        this.scroll_view_change_color_layout = view.findViewById(R.id.scroll_view_change_color_layout);
        this.color_carousel_picker = view.findViewById(R.id.color_carousel_picker);
        this.texture_carousel_picker = view.findViewById(R.id.texture_carousel_picker);
        this.image_view_text_texture = view.findViewById(R.id.image_view_text_texture);
        this.image_view_color_down = view.findViewById(R.id.image_view_color_down);
        this.view_highlight_text_color = view.findViewById(R.id.view_highlight_text_color);
        this.view_highlight_texture = view.findViewById(R.id.view_highlight_texture);
        this.textTransparent = view.findViewById(R.id.seekbar_text_opacity);
        this.seekbar_text_opacity = view.findViewById(R.id.text_view_preview_effect);
        this.linear_layout_preview = view.findViewById(R.id.linear_layout_preview);
        this.checkbox_background = view.findViewById(R.id.checkbox_background);
        this.image_view_background = view.findViewById(R.id.image_view_background);
        this.view_highlight_background_color = view.findViewById(R.id.view_highlight_background_color);
        this.background_carousel_picker = view.findViewById(R.id.background_carousel_picker);
        this.seekbar_width = view.findViewById(R.id.seekbar_width);
        this.seekbar_height = view.findViewById(R.id.seekbar_height);
        this.seekbar_background_opacity = view.findViewById(R.id.seekbar_background_opacity);
        this.seekbar_text_size = view.findViewById(R.id.seekbar_text_size);
        this.seekbar_radius = view.findViewById(R.id.seekbar_radius);
    }

    public void initPreviewText() {
        if (this.quShotText.isQuShotShowBackground()) {
            if (this.quShotText.getQuShotBackgroundColor() != 0) {
                this.seekbar_text_opacity.setBackgroundColor(this.quShotText.getQuShotBackgroundColor());
            }
            if (this.quShotText.getQuShotBackgroundAlpha() < 255) {
                this.seekbar_text_opacity.setBackgroundColor(Color.argb(this.quShotText.getQuShotBackgroundAlpha(), Color.red(this.quShotText.getQuShotBackgroundColor()), Color.green(this.quShotText.getQuShotBackgroundColor()), Color.blue(this.quShotText.getQuShotBackgroundColor())));
            }
            if (this.quShotText.getQuShotBackgroundBorder() > 0) {
                GradientDrawable gradientDrawable = new GradientDrawable();
                gradientDrawable.setCornerRadius((float) SystemUtil.dpToPx(requireContext(), this.quShotText.getQuShotBackgroundBorder()));
                gradientDrawable.setColor(Color.argb(this.quShotText.getQuShotBackgroundAlpha(), Color.red(this.quShotText.getQuShotBackgroundColor()), Color.green(this.quShotText.getQuShotBackgroundColor()), Color.blue(this.quShotText.getQuShotBackgroundColor())));
                this.seekbar_text_opacity.setBackground(gradientDrawable);
            }
        }
        if (this.quShotText.getPaddingHeight() > 0) {
            this.seekbar_text_opacity.setPadding(this.seekbar_text_opacity.getPaddingLeft(), this.quShotText.getPaddingHeight(), this.seekbar_text_opacity.getPaddingRight(), this.quShotText.getPaddingHeight());
            this.seekbar_height.setProgress(this.quShotText.getPaddingHeight());
        }
        if (this.quShotText.getPaddingWidth() > 0) {
            this.seekbar_text_opacity.setPadding(this.quShotText.getPaddingWidth(), this.seekbar_text_opacity.getPaddingTop(), this.quShotText.getPaddingWidth(), this.seekbar_text_opacity.getPaddingBottom());
            this.seekbar_width.setProgress(this.quShotText.getPaddingWidth());
        }
        if (this.quShotText.getQuShotTexts() != null) {
            this.seekbar_text_opacity.setText(this.quShotText.getQuShotTexts());
            this.add_text_edit_text.setText(this.quShotText.getQuShotTexts());
        }
        if (this.quShotText.getQuShotTextShader() != null) {
            this.seekbar_text_opacity.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            this.seekbar_text_opacity.getPaint().setShader(this.quShotText.getQuShotTextShader());
        }
        if (this.quShotText.getQuShotTextAlign() == 4) {
            this.image_view_align.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_format_align_center));
        } else if (this.quShotText.getQuShotTextAlign() == 3) {
            this.image_view_align.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_format_align_right));
        } else if (this.quShotText.getQuShotTextAlign() == 2) {
            this.image_view_align.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_format_align_left));
        }
        this.seekbar_text_opacity.setPadding(SystemUtil.dpToPx(getContext(), this.quShotText.getPaddingWidth()), this.seekbar_text_opacity.getPaddingTop(), SystemUtil.dpToPx(getContext(), this.quShotText.getPaddingWidth()), this.seekbar_text_opacity.getPaddingBottom());
        this.seekbar_text_opacity.setTextColor(this.quShotText.getQuShotTextColor());
        this.seekbar_text_opacity.setTextAlignment(this.quShotText.getQuShotTextAlign());
        this.seekbar_text_opacity.setTextSize((float) this.quShotText.getQuShotTextSize());
        FontAsset.setFontByName(getContext(), this.seekbar_text_opacity, this.quShotText.getQuShotFontName());
        this.seekbar_text_opacity.invalidate();
    }

    private void setDefaultStyleForEdittext() {
        this.add_text_edit_text.requestFocus();
        this.add_text_edit_text.setTextSize(20.0f);
        this.add_text_edit_text.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        this.add_text_edit_text.setTextColor(Color.parseColor("#FFFFFF"));
    }

    private void initAddTextLayout() {
        this.textFunctions = getTextFunctions();
        this.image_view_keyboard.setOnClickListener(this);
        this.image_view_fonts.setOnClickListener(this);
        this.image_view_adjust.setOnClickListener(this);
        this.image_view_color.setOnClickListener(this);
        this.image_view_align.setOnClickListener(this);
        this.image_view_save_change.setOnClickListener(this);
        this.scroll_view_change_font_layout.setVisibility(View.GONE);
        this.scroll_view_change_font_adjust.setVisibility(View.GONE);
        this.scroll_view_change_color_layout.setVisibility(View.GONE);
        this.image_view_text_texture.setVisibility(View.INVISIBLE);
        this.view_highlight_texture.setVisibility(View.GONE);
        this.seekbar_width.setProgress(this.quShotText.getPaddingWidth());
        this.colorItems = getColorItems();
        this.textTextureItems = getTextTextures();
    }

    public void onResume() {
        super.onResume();
        ViewCompat.setOnApplyWindowInsetsListener(getDialog().getWindow().getDecorView(), new OnApplyWindowInsetsListener() {
            public final WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
                return ViewCompat.onApplyWindowInsets(
                        TextFragment.this.getDialog().getWindow().getDecorView(),
                        windowInsetsCompat.inset(windowInsetsCompat.getSystemWindowInsetLeft(), 0, windowInsetsCompat.getSystemWindowInsetRight(), windowInsetsCompat.getSystemWindowInsetBottom()));
            }
        });

    }

    public void updateAddTextBottomToolbarHeight(final int i) {
        new Handler().post(new Runnable() {
            public void run() {
                ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) TextFragment.this.linear_layout_edit_text_tools.getLayoutParams();
                layoutParams.bottomMargin = i;
                TextFragment.this.linear_layout_edit_text_tools.setLayoutParams(layoutParams);
                TextFragment.this.linear_layout_edit_text_tools.invalidate();
                TextFragment.this.scroll_view_change_font_layout.invalidate();
                TextFragment.this.scroll_view_change_font_adjust.invalidate();
                TextFragment.this.scroll_view_change_color_layout.invalidate();
                Log.i("HIHIH", i + "");
            }
        });
    }

    public void setOnTextEditorListener(TextEditor textEditor2) {
        this.textEditor = textEditor2;
    }

    private void highlightFunction(ImageView imageView) {
        for (ImageView next : this.textFunctions) {
            if (next == imageView) {
                imageView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.line));
            } else {
                next.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.line_fake));
            }
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_view_align:
                if (this.quShotText.getQuShotTextAlign() == 4) {
                    this.quShotText.setQuShotTextAlign(3);
                    this.image_view_align.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_format_align_right));
                } else if (this.quShotText.getQuShotTextAlign() == 3) {
                    this.quShotText.setQuShotTextAlign(2);
                    this.image_view_align.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_format_align_left));
                } else if (this.quShotText.getQuShotTextAlign() == 2) {
                    this.quShotText.setQuShotTextAlign(4);
                    this.image_view_align.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_format_align_center));
                }
                this.seekbar_text_opacity.setTextAlignment(this.quShotText.getQuShotTextAlign());
                TextView textView = this.seekbar_text_opacity;
                textView.setText(this.seekbar_text_opacity.getText().toString().trim() + " ");
                this.seekbar_text_opacity.setText(this.seekbar_text_opacity.getText().toString().trim());
                return;
            case R.id.image_view_adjust:
                this.inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                this.scroll_view_change_color_layout.setVisibility(View.GONE);
                this.scroll_view_change_font_adjust.setVisibility(View.VISIBLE);
                this.scroll_view_change_font_layout.setVisibility(View.GONE);
                this.seekbar_background_opacity.setProgress(255 - this.quShotText.getQuShotBackgroundAlpha());
                this.seekbar_text_size.setProgress(this.quShotText.getQuShotTextSize());
                this.seekbar_radius.setProgress(this.quShotText.getQuShotBackgroundBorder());
                this.seekbar_width.setProgress(this.quShotText.getPaddingWidth());
                this.seekbar_height.setProgress(this.quShotText.getPaddingHeight());
                this.textTransparent.setProgress(255 - this.quShotText.getQuShotTextAlpha());
                toggleTextEditEditable(false);
                highlightFunction(this.image_view_adjust);
                return;
            case R.id.image_view_color:
                this.inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                this.scroll_view_change_color_layout.setVisibility(View.VISIBLE);
                this.scroll_view_change_font_adjust.setVisibility(View.GONE);
                toggleTextEditEditable(false);
                highlightFunction(this.image_view_color);
                this.scroll_view_change_font_layout.setVisibility(View.GONE);
                this.add_text_edit_text.setVisibility(View.GONE);
                this.color_carousel_picker.setCurrentItem(this.quShotText.getQuShotTextColorIndex());
                this.texture_carousel_picker.setCurrentItem(this.quShotText.getQuShotTextShaderIndex());
                this.checkbox_background.setChecked(this.quShotText.isQuShotShowBackground());
                this.background_carousel_picker.setCurrentItem(this.quShotText.getQuShotBackgroundColorIndex());
                this.checkbox_background.setChecked(this.quShotText.isQuShotShowBackground());
                if (this.quShotText.getQuShotTextShader() != null && this.image_view_text_texture.getVisibility() == View.INVISIBLE) {
                    this.image_view_text_texture.setVisibility(View.VISIBLE);
                    this.view_highlight_texture.setVisibility(View.VISIBLE);
                    this.image_view_color_down.setVisibility(View.INVISIBLE);
                    this.view_highlight_text_color.setVisibility(View.GONE);
                    return;
                }
                return;
            case R.id.image_view_fonts:
                this.inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                this.scroll_view_change_font_layout.setVisibility(View.VISIBLE);
                this.scroll_view_change_color_layout.setVisibility(View.GONE);
                this.scroll_view_change_font_adjust.setVisibility(View.GONE);
                this.add_text_edit_text.setVisibility(View.GONE);
                toggleTextEditEditable(false);
                highlightFunction(this.image_view_fonts);
                this.fontAdapter.setSelectedItem(this.quShotText.getQuShotFontIndex());
                return;
            case R.id.image_view_save_change:
                if (this.quShotText.getQuShotTexts() == null || this.quShotText.getQuShotTexts().length() == 0) {
                    this.inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    this.textEditor.onBackButton();
                    dismiss();
                    return;
                }
                this.quShotText.setQuShotTextWidth(this.seekbar_text_opacity.getMeasuredWidth());
                this.quShotText.setQuShotTextHeight(this.seekbar_text_opacity.getMeasuredHeight());
                this.inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                this.textEditor.onDone(this.quShotText);
                dismiss();
                return;
            case R.id.image_view_keyboard:
                toggleTextEditEditable(true);
                this.add_text_edit_text.setVisibility(View.VISIBLE);
                this.add_text_edit_text.requestFocus();
                highlightFunction(this.image_view_keyboard);
                this.scroll_view_change_font_layout.setVisibility(View.GONE);
                this.scroll_view_change_color_layout.setVisibility(View.GONE);
                this.scroll_view_change_font_adjust.setVisibility(View.GONE);
                this.linear_layout_edit_text_tools.invalidate();
                this.inputMethodManager.toggleSoftInput(2, 0);
                return;
            default:
        }
    }

    private void toggleTextEditEditable(boolean z) {
        this.add_text_edit_text.setFocusable(z);
        this.add_text_edit_text.setFocusableInTouchMode(z);
        this.add_text_edit_text.setClickable(z);

    }

    private List<ImageView> getTextFunctions() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(this.image_view_keyboard);
        arrayList.add(this.image_view_fonts);
        arrayList.add(this.image_view_color);
        arrayList.add(this.image_view_adjust);
        arrayList.add(this.image_view_align);
        arrayList.add(this.image_view_save_change);
        return arrayList;
    }

    public List<QuShotCarouselPicker.PickerItem> getTextTextures() {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < 42; i++) {
            try {
                AssetManager assets = getContext().getAssets();
                arrayList.add(new QuShotCarouselPicker.DrawableItem(Drawable.createFromStream(assets.open("texture/texture_" + (i + 1) + ".jpg"), (String) null)));
            } catch (Exception e) {
            }
        }
        return arrayList;
    }

    public List<QuShotCarouselPicker.PickerItem> getColorItems() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new QuShotCarouselPicker.ColorItem("#ffffff"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#000000"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#ffebee"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#ffcdd2"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#ef9a9a"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#e57373"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#ef5350"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#f44336"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#e53935"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#d32f2f"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#c62828"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#b71c1c"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#ff8a80"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#ff5252"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#ff1744"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#d50000"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#fce4ec"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#f8bbd0"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#f48fb1"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#f06292"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#ec407a"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#e91e63"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#d81b60"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#c2185b"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#ad1457"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#880e4f"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#ff80ab"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#ff4081"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#f50057"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#c51162"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#f3e5f5"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#e1bee7"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#ce93d8"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#ba68c8"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#ab47bc"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#9c27b0"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#8e24aa"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#7b1fa2"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#6a1b9a"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#4a148c"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#ea80fc"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#e040fb"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#d500f9"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#aa00ff"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#ede7f6"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#d1c4e9"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#b39ddb"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#9575cd"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#7e57c2"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#673ab7"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#5e35b1"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#512da8"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#4527a0"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#311b92"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#b388ff"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#7c4dff"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#651fff"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#6200ea"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#e8eaf6"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#c5cae9"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#9fa8da"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#7986cb"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#5c6bc0"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#3f51b5"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#3949ab"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#303f9f"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#283593"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#1a237e"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#8c9eff"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#536dfe"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#3d5afe"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#304ffe"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#e3f2fd"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#bbdefb"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#90caf9"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#64b5f6"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#42a5f5"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#2196f3"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#1e88e5"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#1976d2"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#1565c0"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#0d47a1"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#82b1ff"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#448aff"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#2979ff"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#2962ff"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#e1f5fe"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#b3e5fc"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#81d4fa"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#4fc3f7"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#29b6f6"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#03a9f4"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#039be5"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#0288d1"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#0277bd"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#01579b"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#80d8ff"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#40c4ff"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#00b0ff"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#0091ea"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#e0f7fa"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#b2ebf2"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#80deea"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#4dd0e1"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#26c6da"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#00bcd4"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#00acc1"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#0097a7"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#00838f"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#006064"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#84ffff"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#18ffff"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#00e5ff"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#00b8d4"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#e0f2f1"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#b2dfdb"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#80cbc4"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#4db6ac"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#26a69a"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#009688"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#00897b"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#00796b"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#00695c"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#004d40"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#a7ffeb"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#64ffda"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#1de9b6"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#00bfa5"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#e8f5e9"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#c8e6c9"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#a5d6a7"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#81c784"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#66bb6a"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#4caf50"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#43a047"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#388e3c"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#2e7d32"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#1b5e20"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#b9f6ca"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#69f0ae"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#00e676"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#00c853"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#f1f8e9"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#dcedc8"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#c5e1a5"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#aed581"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#9ccc65"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#8bc34a"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#7cb342"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#689f38"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#558b2f"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#33691e"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#ccff90"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#b2ff59"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#76ff03"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#64dd17"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#f9fbe7"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#f0f4c3"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#e6ee9c"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#dce775"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#d4e157"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#cddc39"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#c0ca33"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#afb42b"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#9e9d24"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#827717"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#f4ff81"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#eeff41"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#c6ff00"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#aeea00"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#fffde7"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#fff9c4"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#fff59d"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#fff176"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#ffee58"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#ffeb3b"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#fdd835"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#fbc02d"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#f9a825"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#f57f17"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#ffff8d"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#ffff00"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#ffea00"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#ffd600"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#fff8e1"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#ffecb3"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#ffe082"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#ffd54f"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#ffca28"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#ffc107"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#ffb300"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#ffa000"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#ff8f00"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#ff6f00"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#ffe57f"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#ffd740"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#ffc400"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#ffab00"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#fff3e0"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#ffe0b2"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#ffcc80"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#ffb74d"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#ffa726"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#ff9800"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#fb8c00"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#f57c00"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#ef6c00"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#e65100"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#ffd180"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#ffab40"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#ff9100"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#ff6d00"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#fbe9e7"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#ffccbc"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#ffab91"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#ff8a65"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#ff7043"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#ff5722"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#f4511e"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#e64a19"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#d84315"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#bf360c"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#ff9e80"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#ff6e40"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#ff3d00"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#dd2c00"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#efebe9"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#d7ccc8"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#bcaaa4"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#a1887f"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#8d6e63"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#795548"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#6d4c41"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#5d4037"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#4e342e"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#3e2723"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#fafafa"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#f5f5f5"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#eeeeee"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#e0e0e0"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#bdbdbd"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#9e9e9e"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#757575"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#616161"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#424242"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#212121"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#eceff1"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#cfd8dc"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#b0bec5"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#90a4ae"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#78909c"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#607d8b"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#546e7a"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#455a64"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#37474f"));
        arrayList.add(new QuShotCarouselPicker.ColorItem("#263238"));
        return arrayList;
    }
}
