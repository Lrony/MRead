package com.lrony.mread.presentation.read;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Parcelable;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.lrony.mread.AppManager;
import com.lrony.mread.R;
import com.lrony.mread.data.DBManger;
import com.lrony.mread.data.bean.Book;
import com.lrony.mread.data.db.table.BookTb;
import com.lrony.mread.mvp.MvpActivity;
import com.lrony.mread.pref.AppConfig;
import com.lrony.mread.presentation.book.catalog.BookCatalogAdapter;
import com.lrony.mread.ui.help.RecyclerViewItemDecoration;
import com.lrony.mread.ui.help.ToolbarHelper;
import com.lrony.mread.util.BrightnessUtils;
import com.lrony.mread.util.DataConvertUtil;
import com.lrony.mread.util.DisplayUtil;
import com.lrony.mread.util.SystemBarUtils;
import com.zchu.reader.PageLoaderAdapter;
import com.zchu.reader.PageView;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class ReadActivity extends MvpActivity<ReadContract.Presenter> implements ReadContract.View, View.OnClickListener {

    private static final String TAG = "ReadActivity";

    //适配5.0 以下手机可以正常显示vector图片
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    private static final String K_EXTRA_BOOK_TB = "book_tb";

    private DrawerLayout readDrawer;
    private LinearLayout readSide;
    private RecyclerView readRvSection;
    private PageView readView;
    private AppBarLayout appBar;
    private View readBottom;
    private View readSectionProgress;
    private TextView readTvSectionProgress;
    private SeekBar readSbChapterProgress;
    private TextView readTvPreChapter;
    private TextView readTvNextChapter;
    private TextView readTvCategory;
    private TextView readTvNightMode;
    private TextView readTvSetting;
    private TextView tvSectionName;

    private Animation mTopInAnim;
    private Animation mTopOutAnim;
    private Animation mBottomInAnim;
    private Animation mBottomOutAnim;

    private BottomSheetDialog mReadSettingDialog;

    private boolean canTouch = true;
    private PageLoaderAdapter adapter;

    //控制屏幕常亮
    private PowerManager.WakeLock mWakeLock;
    private boolean isFullScreen = false;

    private boolean isShowCollectionDialog = false;
    private BookTb mBookTb;
    private ReadSectionAdapter sectionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        mBookTb = getIntent().getParcelableExtra(K_EXTRA_BOOK_TB);
        ReaderSettingManager.init(this);
        ToolbarHelper.initToolbar(this, R.id.toolbar, true, mBookTb.getName());
        findView();
        bindOnClickLister(this, readTvPreChapter, readTvNextChapter, readTvCategory, readTvNightMode, readTvSetting);
        readRvSection.setLayoutManager(new LinearLayoutManager(this));
        readRvSection.addItemDecoration(new RecyclerViewItemDecoration.Builder(this)
                .color(Color.argb(77, 97, 97, 97))
                .thickness(1)
                .create());
        if (Build.VERSION.SDK_INT >= 19) {
            appBar.setPadding(0, DisplayUtil.getStateBarHeight(this), 0, 0);
        }
        //半透明化StatusBar
        SystemBarUtils.transparentStatusBar(this);
        //隐藏StatusBar
        appBar.post(new Runnable() {
            @Override
            public void run() {
                hideSystemBar();
            }
        });

        //初始化屏幕常亮类
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "keep bright");
        //设置当前Activity的Bright
        if (ReaderSettingManager.getInstance().isBrightnessAuto()) {
            BrightnessUtils.setUseSystemBrightness(this);
        } else {
            BrightnessUtils.setBrightness(this, ReaderSettingManager.getInstance().getBrightness());
        }
        readView.setOnThemeChangeListener(new PageView.OnThemeChangeListener() {
            @Override
            public void onThemeChange(int textColor, int backgroundColor, int textSize) {
                readRvSection.setBackgroundColor(backgroundColor);
                if (sectionAdapter != null) {
                    sectionAdapter.setTextColor(textColor);
                }
            }
        });
        readView.setTextSize(ReaderSettingManager.getInstance().getTextSize());
        if (AppConfig.isNightMode()) {
            ReaderSettingManager.getInstance().setPageBackground(ReadTheme.NIGHT.getPageBackground());
            ReaderSettingManager.getInstance().setTextColor(ReadTheme.NIGHT.getTextColor());
        }
        readView.setTextColor(ReaderSettingManager.getInstance().getTextColor());
        readView.setPageBackground(ReaderSettingManager.getInstance().getPageBackground());

        readView.setTouchListener(new PageView.TouchListener() {
            @Override
            public void center() {
                toggleMenu(true);
            }

            @Override
            public void cancel() {

            }
        });
        readView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (appBar.getVisibility() == View.VISIBLE) {
                    hideReadMenu();
                    return true;
                }
                return false;
            }
        });
        getPresenter().start();
//        getPresenter().loadData();
        getPresenter().loadSectionList();
    }

    @NonNull
    @Override
    public ReadContract.Presenter createPresenter() {
        return new ReadPresenter(this, mBookTb);
    }

    public static Intent newIntent(Context context, Book book, Integer sectionIndex, String sectionId) {
        Intent intent = new Intent(context, ReadActivity.class);
        BookTb bookTb = DataConvertUtil.book2BookTb(book, null);
        bookTb.setLatestReadSection(sectionIndex);
        bookTb.setLatestReadSectionId(sectionId);
        intent.putExtra(K_EXTRA_BOOK_TB, bookTb);
        return intent;
    }

    public static Intent newIntent(Context context, BookTb bookTb) {
        BookTb dbBookTb = DBManger.getInstance().loadBookTbById(bookTb.getId());
        if (dbBookTb != null) {//优先使用数据库中的bookTb
            bookTb = dbBookTb;
        }
        Intent intent = new Intent(context, ReadActivity.class);
        intent.putExtra(K_EXTRA_BOOK_TB, (Parcelable) bookTb);
        return intent;
    }

    private void findView() {
        readDrawer = findViewById(R.id.read_drawer);
        readSide = findViewById(R.id.read_side);
        readRvSection = findViewById(R.id.read_rv_section);
        readView = findViewById(R.id.pv_read);
        appBar = findViewById(R.id.appbar);
        readBottom = findViewById(R.id.read_bottom);
        readTvPreChapter = findViewById(R.id.read_tv_pre_chapter);
        readSbChapterProgress = findViewById(R.id.read_sb_chapter_progress);
        readTvNextChapter = findViewById(R.id.read_tv_next_chapter);
        readTvCategory = findViewById(R.id.read_tv_category);
        readTvNightMode = findViewById(R.id.read_tv_night_mode);
        readTvSetting = findViewById(R.id.read_tv_setting);
        tvSectionName = findViewById(R.id.tv_section_name);
        readSectionProgress = findViewById(R.id.ll_section_progress);
        readTvSectionProgress = findViewById(R.id.tv_section_progress);
        readSbChapterProgress.setEnabled(false);
    }

    private void hideSystemBar() {
        //隐藏
        SystemBarUtils.hideStableStatusBar(this);
        if (isFullScreen) {
            SystemBarUtils.hideStableNavBar(this);
        }
    }

    /**
     * 隐藏阅读界面的菜单显示
     *
     * @return 是否隐藏成功
     */
    private boolean hideReadMenu() {
        hideSystemBar();
        if (appBar.getVisibility() == VISIBLE) {
            toggleMenu(true);
            return true;
        }
        return false;
    }

    //初始化菜单动画
    private void initMenuAnim() {
        if (mTopInAnim != null) return;

        mTopInAnim = AnimationUtils.loadAnimation(this, R.anim.slide_top_in);
        mTopOutAnim = AnimationUtils.loadAnimation(this, R.anim.slide_top_out);
        mBottomInAnim = AnimationUtils.loadAnimation(this, R.anim.slide_bottom_in);
        mBottomInAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                readView.setCanTouch(false);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mBottomOutAnim = AnimationUtils.loadAnimation(this, R.anim.slide_bottom_out);
        mBottomOutAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                readView.setCanTouch(true);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        //退出的速度要快
        mTopOutAnim.setDuration(200);
        mBottomOutAnim.setDuration(200);
    }

    private void showSystemBar() {
        //显示
        SystemBarUtils.showUnStableStatusBar(this);
        if (isFullScreen) {
            SystemBarUtils.showUnStableNavBar(this);
        }
    }

    /**
     * 切换菜单栏的可视状态
     * 默认是隐藏的
     */
    private void toggleMenu(boolean hideStatusBar) {
        initMenuAnim();

        if (appBar.getVisibility() == VISIBLE) {
            //关闭
            appBar.startAnimation(mTopOutAnim);
            readBottom.startAnimation(mBottomOutAnim);
            appBar.setVisibility(GONE);
            readBottom.setVisibility(GONE);

            if (hideStatusBar) {
                hideSystemBar();
            }

        } else {
            appBar.setVisibility(VISIBLE);
            readBottom.setVisibility(VISIBLE);
            appBar.startAnimation(mTopInAnim);
            readBottom.startAnimation(mBottomInAnim);
            boolean isNight = ReadTheme.getReadTheme(readView.getPageBackground(), readView.getTextColor()) == ReadTheme.NIGHT;
            readTvNightMode.setSelected(isNight);
            readTvNightMode.setText(isNight ? getString(R.string.read_daytime) : getString(R.string.read_night));
            showSystemBar();
        }
    }

    private void toggleNightMode(boolean isOpen) {
        if (isOpen) {
            readTvNightMode.setText(getString(R.string.read_daytime));
            readTvNightMode.setSelected(true);
            readView.setPageBackground(ReadTheme.NIGHT.getPageBackground());
            readView.setTextColor(ReadTheme.NIGHT.getTextColor());
            readView.refreshPage();
            ReaderSettingManager.getInstance().setPageBackground(readView.getPageBackground());
            ReaderSettingManager.getInstance().setTextColor(readView.getTextColor());
        } else {
            readTvNightMode.setText(getString(R.string.read_night));
            readTvNightMode.setSelected(false);
            readView.setPageBackground(ReadTheme.DEFAULT.getPageBackground());
            readView.setTextColor(ReadTheme.DEFAULT.getTextColor());
            readView.refreshPage();
            ReaderSettingManager.getInstance().setPageBackground(readView.getPageBackground());
            ReaderSettingManager.getInstance().setTextColor(readView.getTextColor());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.read_tv_pre_chapter:
//                getPresenter().prevSection();
                break;
            case R.id.read_tv_next_chapter:
//                getPresenter().nextSection();
                break;
            case R.id.read_tv_category:
                readDrawer.openDrawer(readSide);
                break;
            case R.id.read_tv_night_mode:
                boolean nightModeSelected = !readTvNightMode.isSelected();
                toggleNightMode(nightModeSelected);
                ReaderSettingManager.getInstance().setNightMode(nightModeSelected);
                AppConfig.setNightMode(nightModeSelected);

                Object[] activityArray = AppManager.getInstance().getActivityArray();
                for (Object appCompatActivity : activityArray) {
                    if (appCompatActivity != this) {
                        AppCompatDelegate delegate = ((AppCompatActivity) appCompatActivity).getDelegate();
                        if (nightModeSelected) {
                            delegate.setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

                        } else {
                            delegate.setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        }
                    }
                }

                break;
            case R.id.read_tv_setting:
//                toggleMenu(true);
//                openReadSetting(this);
                break;

        }
    }

    @Override
    public void setSectionListAdapter(final ReadSectionAdapter adapter) {
        sectionAdapter = adapter;
        sectionAdapter.setTextColor(readView.getTextColor());
        readRvSection.setAdapter(adapter);
        readSbChapterProgress.setEnabled(true);
        readSbChapterProgress.setMax(adapter.getItemCount());
        readSbChapterProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int section = seekBar.getProgress() - 1;
                if (section < 1) {
                    section = 1;
                }
                readTvSectionProgress.setText(section + "/" + adapter.getItemCount());

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                readSectionProgress.setVisibility(View.VISIBLE);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                readSectionProgress.setVisibility(View.GONE);
                int section = seekBar.getProgress() - 1;
                if (section < 0) {
                    section = 0;
                }
//                getPresenter().openSection(section);
            }
        });
    }
}
