package com.que.shot.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.airbnb.lottie.LottieAnimationView;
import com.que.shot.BuildConfig;
import com.que.shot.R;
import com.que.shot.preference.Preference;

public class RateDialog extends Dialog implements View.OnClickListener {
    private final Activity activity;
    private final boolean exit;
    private final ImageView[] imageViewStars = new ImageView[5];
    private ViewGroup linear_layout_RatingBar;
    private int star_number;
    private TextView text_view_submit;
    private LottieAnimationView lottieAnimationView;

    public RateDialog(@NonNull Activity activity, boolean z) {
        super(activity);
        this.activity = activity;
        this.exit = z;
        setContentView(R.layout.dialog_rate);
        initView();
        this.linear_layout_RatingBar.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.shake));
        this.star_number = 0;
    }

    private void initView() {
        this.text_view_submit = findViewById(R.id.text_view_submit);
        TextView text_view_later = findViewById(R.id.text_view_later);
        this.linear_layout_RatingBar = findViewById(R.id.linear_layout_RatingBar);
        this.text_view_submit.setOnClickListener(this);
        text_view_later.setOnClickListener(this);
        this.lottieAnimationView = findViewById(R.id.lottie);
        this.lottieAnimationView.setProgress(1.0f);
        ImageView image_view_star_1 = findViewById(R.id.image_view_star_1);
        ImageView image_view_star_2 = findViewById(R.id.image_view_star_2);
        ImageView image_view_star_3 = findViewById(R.id.image_view_star_3);
        ImageView image_view_star_4 = findViewById(R.id.image_view_star_4);
        ImageView image_view_star_5 = findViewById(R.id.image_view_star_5);
        image_view_star_1.setOnClickListener(this);
        image_view_star_2.setOnClickListener(this);
        image_view_star_3.setOnClickListener(this);
        image_view_star_4.setOnClickListener(this);
        image_view_star_5.setOnClickListener(this);
        this.imageViewStars[0] = image_view_star_1;
        this.imageViewStars[1] = image_view_star_2;
        this.imageViewStars[2] = image_view_star_3;
        this.imageViewStars[3] = image_view_star_4;
        this.imageViewStars[4] = image_view_star_5;
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id != R.id.text_view_later) {
            if (id != R.id.text_view_submit) {
                switch (id) {
                    case R.id.image_view_star_1:
                        this.star_number = 1;
                        setStarBar();
                        return;
                    case R.id.image_view_star_2:
                        this.star_number = 2;
                        setStarBar();
                        return;
                    case R.id.image_view_star_3:
                        this.star_number = 3;
                        setStarBar();
                        return;
                    case R.id.image_view_star_4:
                        this.star_number = 4;
                        setStarBar();
                        return;
                    case R.id.image_view_star_5:
                        this.star_number = 5;
                        setStarBar();
                        return;
                    default:

                }
            } else if (this.star_number >= 4) {
                this.activity.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID)));
                Preference.setRated(this.activity, true);
                dismiss();
            } else if (this.star_number > 0) {
                Intent intent = new Intent("android.intent.action.SEND");
                intent.setType("message/rfc822");
                intent.putExtra("android.intent.extra.EMAIL", new String[]{this.activity.getResources().getString(R.string.email_feedback)});
                intent.putExtra("android.intent.extra.SUBJECT", this.activity.getString(R.string.app_name));
                this.activity.startActivity(Intent.createChooser(intent, "Send Email"));
                if (this.exit) {
                    this.activity.finish();
                } else {
                    dismiss();
                }
            } else {
                this.linear_layout_RatingBar.startAnimation(AnimationUtils.loadAnimation(this.activity, R.anim.shake));
            }
        } else if (this.exit) {
            this.activity.finish();
        } else {
            dismiss();
        }
    }

    private void setStarBar() {
        for (int i = 0; i < this.imageViewStars.length; i++) {
            if (i < this.star_number) {
                this.imageViewStars[i].setImageResource(R.drawable.ic_star);
            } else {
                this.imageViewStars[i].setImageResource(R.drawable.ic_star_blur);
            }
        }
        if (this.star_number < 4) {
            this.text_view_submit.setText(R.string.rating_dialog_feedback_title);
        } else {
            this.text_view_submit.setText(R.string.rating_dialog_submit);
        }
        this.lottieAnimationView.setProgress(((float) this.star_number) / 5.0f);
    }
}
