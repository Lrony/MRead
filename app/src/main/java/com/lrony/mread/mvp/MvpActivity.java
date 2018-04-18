package com.lrony.mread.mvp;

import android.content.Context;
import android.support.annotation.NonNull;

import com.lrony.mread.base.BaseActivity;
import com.lrony.mread.util.ToastUtil;

/**
 * Created by lrony on 2018/4/9.
 */
public abstract class MvpActivity<P extends MvpPresenter> extends BaseActivity implements MvpView {

    private P mPresenter;

    @Override
    public void showToast(String message) {
        ToastUtil.showToast(message);
    }

    @Override
    public Context provideContext() {
        return this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.destroy();
            mPresenter = null;
        }
    }

    @NonNull
    public abstract P createPresenter();

    /**
     * Subclass can get the bound presenter by calling this method.
     *
     * @return Bound presenter
     */
    protected P getPresenter() {
        if (mPresenter == null) {
            mPresenter = createPresenter();
            mPresenter.attachView(this);
        }
        return mPresenter;
    }
}
