package com.lrony.mread.base;

import com.lrony.mread.mvp.MvpBasePresenter;
import com.lrony.mread.mvp.MvpView;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Lrony on 18-4-28.
 */
public class BaseRxPresenter<V extends MvpView> extends MvpBasePresenter<V> {

    //管理所有的Subscription,便于回收资源
    private CompositeSubscription mSubscriptions2Destroy;// 管理Destroy取消订阅者者
    private CompositeSubscription mSubscriptions2Detach;// 管理Detach取消订阅者者


    public BaseRxPresenter() {
        mSubscriptions2Destroy = new CompositeSubscription();
        mSubscriptions2Detach = new CompositeSubscription();
    }

    @Override
    public void attachView(V view) {
        super.attachView(view);

    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscriptions2Destroy != null && mSubscriptions2Detach.hasSubscriptions()) {
            mSubscriptions2Detach.unsubscribe();
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        if (mSubscriptions2Destroy != null && mSubscriptions2Destroy.hasSubscriptions()) {
            mSubscriptions2Destroy.unsubscribe();
        }

    }

    protected void addSubscription2Detach(Subscription subscription) {
        mSubscriptions2Detach.add(subscription);
    }

    protected void addSubscription2Destroy(Subscription subscription) {
        mSubscriptions2Destroy.add(subscription);
    }

    protected void remove(Subscription subscription) {
        mSubscriptions2Detach.remove(subscription);
        mSubscriptions2Destroy.remove(subscription);
    }
}
