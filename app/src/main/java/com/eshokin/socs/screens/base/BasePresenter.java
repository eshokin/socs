package com.eshokin.socs.screens.base;

import com.arellomobile.mvp.MvpPresenter;
import com.arellomobile.mvp.MvpView;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BasePresenter<View extends MvpView> extends MvpPresenter<View> {

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    protected void unSubscribeOnDestroy(@NonNull Disposable subscription) {
        mCompositeDisposable.add(subscription);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mCompositeDisposable.dispose();
        mCompositeDisposable.clear();
    }
}
