package com.example.choco.ui.widget;

import static android.view.Gravity.CENTER;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.airbnb.lottie.LottieAnimationView;
import com.example.choco.R;

public class StateLayout extends LinearLayout {

    private static final int SHOW_PROGRESS_STATE = 1;
    private static final int HIDE_PROGRESS_STATE = 2;
    private static final int SHOW_EMPTY_STATE = 3;
    private static final int HIDDEN = 4;
    private static final int SHOWN = 5;

    private LottieAnimationView lottieAnimation;
    private ProgressBar spinner;
    private TextView title;
    private TextView subtitle;
    private Button reloadBtn;

    int layoutState = HIDDEN;
    boolean showReload;
    String emptyTextValue;

    public StateLayout(Context context) {
        super(context);
    }

    public StateLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StateLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if (visibility == GONE || visibility == INVISIBLE) {
            layoutState = HIDDEN;
        } else {
            layoutState = SHOWN;
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        inflate(getContext(), R.layout.layout_states, this);
        if (isInEditMode()) return;

        lottieAnimation = findViewById(R.id.animation);
        title = findViewById(R.id.title);
        subtitle = findViewById(R.id.subtitle);
        spinner = findViewById(R.id.spinner);
        reloadBtn = findViewById(R.id.reload);

        title.setFreezesText(true);
        setOrientation(VERTICAL);
        setGravity(CENTER);
    }

    public void setLottieAnimationRes(int res) {
        lottieAnimation.setAnimation(res);
        lottieAnimation.setRepeatCount(1);
    }

    public void setLottieImageRes(int res){
        lottieAnimation.setImageResource(res);
    }

    public void setTitleText(int res) {
        emptyTextValue = getResources().getString(res);
        title.setText(res);
    }

    public void setSubtitleText(int res) {
        subtitle.setText(res);
    }

    public void setReloadButtonText(int res) {
        showReload = true;
        reloadBtn.setText(res);
    }

    public void showProgress() {
        layoutState = SHOW_PROGRESS_STATE;
        setVisibility(VISIBLE);
        lottieAnimation.setVisibility(GONE);
        title.setVisibility(GONE);
        subtitle.setVisibility(GONE);
        reloadBtn.setVisibility(GONE);
        spinner.setVisibility(VISIBLE);
    }

    public void hideProgress() {
        layoutState = HIDE_PROGRESS_STATE;
        title.setVisibility(VISIBLE);
        subtitle.setVisibility(VISIBLE);
        lottieAnimation.setVisibility(VISIBLE);
        reloadBtn.setVisibility(VISIBLE);
        spinner.setVisibility(GONE);
        setVisibility(GONE);
    }

    public void setEmptyText(@StringRes int resId) {
        setEmptyText(getResources().getString(resId));
    }

    public void setEmptyText(@NonNull String text) {
        this.emptyTextValue = text;
        title.setText(emptyTextValue);
    }

    public void showEmptyState() {
        hideProgress();
        setVisibility(VISIBLE);
        title.setVisibility(VISIBLE);
        subtitle.setVisibility(VISIBLE);
        lottieAnimation.setVisibility(VISIBLE);
        reloadBtn.setVisibility(showReload ? VISIBLE : GONE);
        layoutState = SHOW_EMPTY_STATE;// last so it override visibility state.
    }

    public void showReloadState() {
        hideProgress();
        setVisibility(VISIBLE);
        title.setVisibility(VISIBLE);
        subtitle.setVisibility(VISIBLE);
        lottieAnimation.setVisibility(VISIBLE);
        reloadBtn.setVisibility(VISIBLE);
        layoutState = SHOW_EMPTY_STATE;// last so it override visibility state.
    }

    public void setOnReloadListener(View.OnClickListener onReloadListener) {
        reloadBtn.setOnClickListener(onReloadListener);
    }

    private void onHandleLayoutState() {
        setEmptyText(emptyTextValue);
        switch (layoutState) {
            case SHOW_PROGRESS_STATE:
                showProgress();
                break;
            case HIDE_PROGRESS_STATE:
                hideProgress();
                break;
            case HIDDEN:
                setVisibility(GONE);
                break;
            case SHOW_EMPTY_STATE:
                showEmptyState();
                break;
            case SHOWN:
                setVisibility(VISIBLE);
//                showReload();
                break;
        }
    }
}
