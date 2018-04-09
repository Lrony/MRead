package com.lrony.mread.ui.read;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.lrony.mread.R;
import com.lrony.mread.ui.widget.ShapeView;
import com.zchu.reader.PageView;

/**
 * Created by Lrony on 18-4-9.
 */
public class ReaderSettingDialog extends BottomSheetDialog implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    private PageView mPageView;

    private ImageView readIvLightnessMinus;
    private SeekBar readSbLightnessProgress;
    private ImageView readIvLightnessPlus;
    private TextView readTvLightnessSystem;

    private TextView readTvFontSizeMinus;
    private TextView readTvFontSize;
    private TextView readTvFontSizePlus;
    private TextView readTvFontSizeDefault;
    private TextView readTvFontSetting;

    private TextView readTvFlipOverCover;
    private TextView readTvFlipOverSimulation;
    private TextView readTvFlipOverSlide;
    private TextView readTvFlipOverNone;
    private View[] flipOverViews;

    private ImageView readTvColorSetting;

    private ShapeView readThemeWhite;
    private ShapeView readThemeAmber;
    private ShapeView readThemeGreen;
    private ShapeView readThemeBrown;
    private ShapeView readThemeBlack;
    private View[] themeViews;

    public ReaderSettingDialog(@NonNull Context context, @NonNull PageView pageView) {
        super(context, R.style.Read_Setting_Dialog);
        setOwnerActivity((Activity) context);
        super.setContentView(R.layout.bottom_sheet_read_setting);
        this.mPageView = pageView;
        initView();
        initListener();
        initDisplay();
    }

    private void initView() {
        readIvLightnessMinus = (ImageView) findViewById(R.id.read_iv_lightness_minus);
        readSbLightnessProgress = (SeekBar) findViewById(R.id.read_sb_lightness_progress);
        readIvLightnessPlus = (ImageView) findViewById(R.id.read_iv_lightness_plus);
        readTvLightnessSystem = (TextView) findViewById(R.id.read_tv_lightness_system);

        readTvFontSizeMinus = (TextView) findViewById(R.id.read_tv_font_size_minus);
        readTvFontSize = (TextView) findViewById(R.id.read_tv_font_size);
        readTvFontSizePlus = (TextView) findViewById(R.id.read_tv_font_size_plus);
        readTvFontSizeDefault = (TextView) findViewById(R.id.read_tv_font_size_default);
        readTvFontSetting = (TextView) findViewById(R.id.read_tv_font_setting);

        readTvFlipOverCover = (TextView) findViewById(R.id.read_tv_flip_over_cover);
        readTvFlipOverSimulation = (TextView) findViewById(R.id.read_tv_flip_over_simulation);
        readTvFlipOverSlide = (TextView) findViewById(R.id.read_tv_flip_over_slide);
        readTvFlipOverNone = (TextView) findViewById(R.id.read_tv_flip_over_none);
        readTvColorSetting = (ImageView) findViewById(R.id.read_tv_color_setting);
        flipOverViews = new View[]{
                readTvFlipOverSimulation,
                readTvFlipOverCover,
                readTvFlipOverSlide,
                readTvFlipOverNone
        };

        readThemeWhite = (ShapeView) findViewById(R.id.read_theme_white);
        readThemeAmber = (ShapeView) findViewById(R.id.read_theme_amber);
        readThemeGreen = (ShapeView) findViewById(R.id.read_theme_green);
        readThemeBrown = (ShapeView) findViewById(R.id.read_theme_brown);
        readThemeBlack = (ShapeView) findViewById(R.id.read_theme_black);
        themeViews = new View[]{
                readThemeWhite,
                readThemeAmber,
                readThemeGreen,
                readThemeBrown,
                readThemeBlack
        };
    }

    private void initListener() {
        readIvLightnessMinus.setOnClickListener(this);
        readIvLightnessPlus.setOnClickListener(this);
        readTvLightnessSystem.setOnClickListener(this);
        readTvFontSizeMinus.setOnClickListener(this);
        readTvFontSizePlus.setOnClickListener(this);
        readTvFontSizeDefault.setOnClickListener(this);
        readTvFontSetting.setOnClickListener(this);
        readTvFlipOverCover.setOnClickListener(this);
        readTvFlipOverSimulation.setOnClickListener(this);
        readTvFlipOverSlide.setOnClickListener(this);
        readTvFlipOverNone.setOnClickListener(this);
        readTvColorSetting.setOnClickListener(this);

        readThemeWhite.setOnClickListener(this);
        readThemeAmber.setOnClickListener(this);
        readThemeGreen.setOnClickListener(this);
        readThemeBrown.setOnClickListener(this);
        readThemeBlack.setOnClickListener(this);

        readSbLightnessProgress.setOnSeekBarChangeListener(this);

    }

    private void initDisplay() {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
