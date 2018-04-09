package com.lrony.mread.ui.read;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.lrony.mread.R;
import com.lrony.mread.ui.base.BaseActivity;
import com.lrony.mread.util.DisplayUtil;
import com.lrony.mread.util.SystemBarUtils;
import com.zchu.reader.PageView;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class ReadActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "ReadActivityActivity";

    //适配5.0 以下手机可以正常显示vector图片
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

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

    //控制屏幕常亮
    private PowerManager.WakeLock mWakeLock;
    private boolean isFullScreen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        findView();
        initView();
        initListener();
    }

    private void findView() {
        readDrawer = (DrawerLayout) findViewById(R.id.read_drawer);
        readSide = (LinearLayout) findViewById(R.id.read_side);
        readRvSection = (RecyclerView) findViewById(R.id.read_rv_section);
        readView = (PageView) findViewById(R.id.pv_read);
        appBar = (AppBarLayout) findViewById(R.id.appbar);
        readBottom = findViewById(R.id.read_bottom);
        readTvPreChapter = (TextView) findViewById(R.id.read_tv_pre_chapter);
        readSbChapterProgress = (SeekBar) findViewById(R.id.read_sb_chapter_progress);
        readTvNextChapter = (TextView) findViewById(R.id.read_tv_next_chapter);
        readTvCategory = (TextView) findViewById(R.id.read_tv_category);
        readTvNightMode = (TextView) findViewById(R.id.read_tv_night_mode);
        readTvSetting = (TextView) findViewById(R.id.read_tv_setting);
        tvSectionName = findViewById(R.id.tv_section_name);
        readSectionProgress = findViewById(R.id.ll_section_progress);
        readTvSectionProgress = findViewById(R.id.tv_section_progress);
        readSbChapterProgress.setEnabled(false);
        bindOnClickLister(this, readTvPreChapter, readTvNextChapter, readTvCategory, readTvNightMode, readTvSetting);
    }

    private void initView() {
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
    }

    private void initListener() {
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        mWakeLock.acquire();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mWakeLock.release();
    }

    private void openReadSetting(Context context) {
        if (mReadSettingDialog == null) {
            mReadSettingDialog = new ReaderSettingDialog(context, readView);
        }
        mReadSettingDialog.show();
    }

    @Override
    public void onBackPressedSupport() {
        if (appBar.getVisibility() == VISIBLE) {
            toggleMenu(true);
            return;
        }
        super.onBackPressedSupport();
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
            boolean isNight = false;
            readTvNightMode.setSelected(isNight);
            readTvNightMode.setText(isNight ? getString(R.string.read_daytime) : getString(R.string.read_night));
            showSystemBar();
        }
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

    private void hideSystemBar() {
        //隐藏
        SystemBarUtils.hideStableStatusBar(this);
        if (isFullScreen) {
            SystemBarUtils.hideStableNavBar(this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.read_tv_setting:
                toggleMenu(true);
                openReadSetting(this);
                break;
        }
    }
}
